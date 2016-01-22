package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.constant.CoursearePackStatusEnum;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.TeachCoursewaresService;
import net.xuele.cloudteach.service.mq.MessageQueueAdmin;
import net.xuele.cloudteach.service.mq.PackCoursewareProducer;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.teacheval.constants.AccessAction;
import net.xuele.teacheval.domain.UserAccessAction;
import net.xuele.teacheval.service.WriteAccessLogService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * TeachCoursewaresServiceImpl
 * 授课课件服务
 *
 * @author duzg
 * @date 2015/7/13 0002
 */
@Service
public class TeachCoursewaresServiceImpl implements TeachCoursewaresService {
	@Autowired
	private CtTeachCoursewaresMapper ctTeachCoursewaresMapper;

	@Autowired
	private CtTeachCoursewaresContentMapper ctTeachCoursewaresContentMapper;

	@Autowired
	private CtTeachCoursewaresStatisticsMapper ctTeachCoursewaresStatisticsMapper;

	@Autowired
	private CtTeachCoursewaresShareMapper ctTeachCoursewaresShareMapper;

	@Autowired
	private CtTeachCoursewaresShareStatisticsMapper ctTeachCoursewaresShareStatisticsMapper;

	@Autowired
	private CtTeachCoursewaresShareContentMapper ctTeachCoursewaresShareContentMapper;

	@Autowired
	private CtTeachCoursewaresShareCollectMapper ctTeachcoursewaresShareCollectMapper;

	@Autowired
	private CloudTeachService cloudTeachService;

	@Autowired
	private WriteAccessLogService writeAccessLogService;

    @Autowired
    private PackCoursewareProducer packCoursewareProducer;

	@Autowired
	private MessageQueueAdmin messageQueueAdmin;

	@Autowired
	private CloudTeachRedisService cloudTeachRedisService;

	@Value("${queues.coursepack.name}")
	private String coursePackQueueName;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TeachCoursewaresServiceImpl.class);

	private static final String PACKINFOREDISKEYBASE = "courseware:pack:";

    private static final long PACKEXP = 5 * 24 * 3600 * 1000;

	@Override
	public List<TeachCoursewaresDTO> queryTeachCoursewaresListByUnitId(String unitId) throws CloudteachException {
		//TODO　可能会在后台管理或统计中用到该接口 延后实现
		return null;
	}

	@Override
	public List<TeachCoursewaresDTO> queryMyTeachCoursewaresList(String schoolId, String uintId, String userId) throws CloudteachException {
		//获取某个单元下我的课件 排序关键字：置顶、时间
		List<TeachCoursewaresDTO> coursewaresDTOList = new ArrayList<>();
		List<CtTeachCoursewares> coursewaresList = ctTeachCoursewaresMapper.selectByUnitId(schoolId, uintId, userId);

		for (CtTeachCoursewares ctTeachCoursewares : coursewaresList) {
			TeachCoursewaresDTO coursewaresDTO = new TeachCoursewaresDTO();
			BeanUtils.copyProperties(ctTeachCoursewares, coursewaresDTO);
			coursewaresDTOList.add(coursewaresDTO);
		}

		return coursewaresDTOList;
	}

	/**
	 * 删除我的课件
	 *
	 * @param schoolId      学校ID
	 * @param coursewaresId 课件ID
	 * @param userId        用户ID
	 */
	@Override
	public void removeTeachCourseware(String schoolId, String coursewaresId, String userId) throws CloudteachException {
		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(schoolId, coursewaresId);
		if (ctTeachCoursewares == null) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (ctTeachCoursewares.getStatus() != null && ctTeachCoursewares.getStatus() != 1) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (!(ctTeachCoursewares.getCreator() != null && ctTeachCoursewares.getCreator().equals(userId))) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_YOURS);
		}

		ctTeachCoursewaresMapper.removeTeachCourseware(schoolId, coursewaresId);

		if (ctTeachCoursewares.getIsCollect() != null && ctTeachCoursewares.getIsCollect() == 1) {
			//更新课件分享计数
			CtTeachCoursewaresShareStatistics ctTeachCoursewaresShareStatistics = new CtTeachCoursewaresShareStatistics();
			ctTeachCoursewaresShareStatistics.setShareId(ctTeachCoursewares.getPid());
			ctTeachCoursewaresShareStatistics.setCollectTimes(-1);
			ctTeachCoursewaresShareStatisticsMapper.updateByPrimaryKey(ctTeachCoursewaresShareStatistics);

			//更新收藏记录
			CtTeachCoursewaresShareCollect ctTeachCoursewaresShareCollect = new CtTeachCoursewaresShareCollect();
			ctTeachCoursewaresShareCollect.setShareId(ctTeachCoursewares.getPid());
			ctTeachCoursewaresShareCollect.setUserId(userId);
			ctTeachCoursewaresShareCollect.setCollectStatus(0);
			ctTeachCoursewaresShareCollect.setCollectTime(new Date());
			ctTeachcoursewaresShareCollectMapper.updateByPrimaryKey(ctTeachCoursewaresShareCollect);
		}
	}

	@Override
	public String addTeachCoursewares(TeachCoursewaresDTO teachCoursewaresDTO, TeachCoursewaresContentDTO teachCoursewaresContentDTO) throws CloudteachException {

		if (teachCoursewaresDTO == null || teachCoursewaresDTO.getUserId() == null || teachCoursewaresDTO.getSchoolId() == null) {
			logger.error("创建课件传入的参数中，userId，schoolId不能为空");
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR,"创建课件传入的参数中，userId，schoolId不能为空");
		}

		TeachCoursewaresStatisticsDTO teachCoursewaresStatisticsDTO = new TeachCoursewaresStatisticsDTO();

		//课件名按规则截取长度
		teachCoursewaresDTO.setTitle(StringUtil.fileNameSubstring(teachCoursewaresDTO.getTitle(), Constants.MAX_FILE_NAME_LENGTH));

		String coursewaresId = (UUID.randomUUID()).toString().replace("-", "");
		teachCoursewaresDTO.setCoursewaresId(coursewaresId);
		teachCoursewaresContentDTO.setCoursewaresId(coursewaresId);
		teachCoursewaresStatisticsDTO.setCoursewaresId(coursewaresId);

		teachCoursewaresStatisticsDTO.setSchoolId(teachCoursewaresDTO.getSchoolId());

		addTeachCoursewaresInfo(teachCoursewaresDTO);
		addTeachCoursewaresContent(teachCoursewaresContentDTO);
		addTeachCoursewaresStatis(teachCoursewaresStatisticsDTO);


		try {
			CoursewareMessageDTO coursewareMessageDTO = new CoursewareMessageDTO();

			coursewareMessageDTO.setSchoolId(teachCoursewaresDTO.getSchoolId());
			coursewareMessageDTO.setUserId(teachCoursewaresDTO.getUserId());
			coursewareMessageDTO.setCoursewateId(teachCoursewaresDTO.getCoursewaresId());

		} catch (Exception e) {
			logger.error("触发课件打包任务失败，", e);
		}

		try {

			//发送日志到teacheval
			writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
					.accessAction(AccessAction.Courseware)
					.userId(teachCoursewaresDTO.getUserId())
					.occurredTime(new Date().getTime())
					.build());

		}catch (Exception e){

			logger.error("发送日志到teacheval出现异常，不会影响到课件创建", e);
		}

		return coursewaresId;
	}

	private void addTeachCoursewaresInfo(TeachCoursewaresDTO teachCoursewaresDTO) {
		CtTeachCoursewares ctTeachCoursewares = new CtTeachCoursewares();
		BeanUtils.copyProperties(teachCoursewaresDTO, ctTeachCoursewares);
		ctTeachCoursewares.setCreator(teachCoursewaresDTO.getUserId());
		ctTeachCoursewares.setIsCollect(0);     //非收藏课件
		ctTeachCoursewares.setShareStatus(0);   //默认为私有状态
		ctTeachCoursewares.setStatus(1);
		ctTeachCoursewares.setCreateTime(new Date());
		ctTeachCoursewares.setUpdateTime(new Date());
		ctTeachCoursewaresMapper.insert(ctTeachCoursewares);

	}

	private void addTeachCoursewaresContent(TeachCoursewaresContentDTO teachCoursewaresContentDTO) {
		CtTeachCoursewaresContent ctTeachCoursewaresContent = new CtTeachCoursewaresContent();
		BeanUtils.copyProperties(teachCoursewaresContentDTO, ctTeachCoursewaresContent);
		ctTeachCoursewaresContent.setBelongType(1);
		ctTeachCoursewaresContentMapper.insert(ctTeachCoursewaresContent);

	}

	private void addTeachCoursewaresStatis(TeachCoursewaresStatisticsDTO teachCoursewaresStatisticsDTO) {
		CtTeachCoursewaresStatistics ctTeachCoursewaresStatistics = new CtTeachCoursewaresStatistics();
		BeanUtils.copyProperties(teachCoursewaresStatisticsDTO, ctTeachCoursewaresStatistics);
		ctTeachCoursewaresStatistics.setReleases(0);
		ctTeachCoursewaresStatistics.setPackStatus(CoursearePackStatusEnum.UNPACK.getStatus());
		ctTeachCoursewaresStatisticsMapper.insert(ctTeachCoursewaresStatistics);
	}

	@Override
	public int updateTeachCoursewares(TeachCoursewaresDTO teachCoursewaresDTO, TeachCoursewaresContentDTO teachCoursewaresContentDTO) throws CloudteachException {

		//课件名按规则截取长度
		teachCoursewaresDTO.setTitle(StringUtil.fileNameSubstring(teachCoursewaresDTO.getTitle(), Constants.MAX_FILE_NAME_LENGTH));

		upateTeachCoursewaresInfo(teachCoursewaresDTO);
		upateTeachCoursewaresContent(teachCoursewaresContentDTO);

		//标记课件更新状态
		TeachCoursewarePackDTO packDTO = new TeachCoursewarePackDTO();
		packDTO.setCoursewaresId(teachCoursewaresDTO.getCoursewaresId());
		packDTO.setSchoolId(teachCoursewaresDTO.getSchoolId());
		packDTO.setUserId(teachCoursewaresDTO.getUserId());
		packDTO.setPackStatus(CoursearePackStatusEnum.OUTDATED.getStatus());//课件包已过期

		updatePackStatus(packDTO);

		return 1;
	}

	@Override
	public CoursewaresResponseDTO queryMyTeachCourse(String coursewaresId, String userId, String schoolId) {
		logger.info("查询课件：coursewaresId=" + coursewaresId + ";userId=" + userId + ";schoolId=" + schoolId);

		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(coursewaresId, userId, schoolId);
		if (null == ctTeachCoursewares) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.OBJECTNOTFOUND);
		}
		CtTeachCoursewaresContent ctTeachCoursewaresContent = ctTeachCoursewaresContentMapper.selectByPrimaryKey(coursewaresId, userId, schoolId);
		if (ctTeachCoursewaresContent == null) {
			ctTeachCoursewaresContent = new CtTeachCoursewaresContent();
		}
		CoursewaresResponseDTO coursewaresResponseDTO = new CoursewaresResponseDTO();
		coursewaresResponseDTO.setContent(ctTeachCoursewaresContent.getContent());
		if (ctTeachCoursewares.getStatus() == null) {
			logger.error("查询课件时发现异常数据，status=null coursewaresId=" + coursewaresId);
		}
		coursewaresResponseDTO.setState(String.valueOf(ctTeachCoursewares.getStatus() == null ? 0 : ctTeachCoursewares.getStatus()));
		coursewaresResponseDTO.setCoursewaresId(ctTeachCoursewares.getCoursewaresId());
		coursewaresResponseDTO.setCoursewaresName(ctTeachCoursewares.getTitle());
		coursewaresResponseDTO.setUnitId(ctTeachCoursewares.getUnitId());
		coursewaresResponseDTO.setUserId(ctTeachCoursewares.getUserId());
		return coursewaresResponseDTO;
	}

	@Override
	public void cancelCollectTeachCourseware(String schoolId, String coursewaresId, String userId) throws CloudteachException {
		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(schoolId, coursewaresId);

		if (ctTeachCoursewares == null) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (ctTeachCoursewares.getStatus() != null && ctTeachCoursewares.getStatus() != 1) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (!(ctTeachCoursewares.getCreator() != null && ctTeachCoursewares.getCreator().equals(userId))) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_YOURS);
		}

		if (ctTeachCoursewares.getIsCollect() != 1 || ctTeachCoursewares.getPid() == null || ctTeachCoursewares.getPid().trim().equals("")) {

			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_COLLECT);
		}

		ctTeachCoursewaresMapper.cancelCollectTeachCourseware(schoolId, coursewaresId);

		//更新课件分享计数
		CtTeachCoursewaresShareStatistics ctTeachCoursewaresShareStatistics = new CtTeachCoursewaresShareStatistics();
		ctTeachCoursewaresShareStatistics.setShareId(ctTeachCoursewares.getPid());
		ctTeachCoursewaresShareStatistics.setCollectTimes(-1);
		ctTeachCoursewaresShareStatisticsMapper.updateByPrimaryKey(ctTeachCoursewaresShareStatistics);

		//更新收藏记录
		CtTeachCoursewaresShareCollect ctTeachCoursewaresShareCollect = new CtTeachCoursewaresShareCollect();
		ctTeachCoursewaresShareCollect.setShareId(ctTeachCoursewares.getPid());
		ctTeachCoursewaresShareCollect.setUserId(userId);
		ctTeachCoursewaresShareCollect.setCollectStatus(0);
		ctTeachCoursewaresShareCollect.setCollectTime(new Date());
		ctTeachcoursewaresShareCollectMapper.updateByPrimaryKey(ctTeachCoursewaresShareCollect);
	}

	/**
	 * 分享课件
	 *
	 * @param userShareInfoDTO
	 */
	@Override
	public void shareCourseware(UserShareInfoDTO userShareInfoDTO) throws CloudteachException {
		//获取要分享的课件，判断是否具备分享的条件
		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(userShareInfoDTO.getSchoolId(), userShareInfoDTO.getItemId());
		if (ctTeachCoursewares == null) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (!(ctTeachCoursewares.getCreator() != null && ctTeachCoursewares.getCreator().equals(userShareInfoDTO.getUserId()))) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_YOURS);
		}

		//是否可分享
		if (ctTeachCoursewares.getIsCollect() != null && ctTeachCoursewares.getIsCollect() == 1) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_IS_COLLECT);
		}

		if (ctTeachCoursewares.getShareStatus() != null && (ctTeachCoursewares.getShareStatus() == 1 || ctTeachCoursewares.getShareStatus() == 2)) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_ALREADY_SHARED);
		}


		CtTeachCoursewaresShare ctTeachCoursewaresShare = toCoursewaresShare(ctTeachCoursewares);

		//初始化数据
		ctTeachCoursewaresShare.setShareId(UUID.randomUUID().toString().replace("-", "").trim());            //分享ID
		ctTeachCoursewaresShare.setShareType(userShareInfoDTO.getShareType());                    //分享类型
		ctTeachCoursewaresShare.setCreator(userShareInfoDTO.getUserId());                         //分享用户
		ctTeachCoursewaresShare.setCreatorName(userShareInfoDTO.getUserName());                   //分享用户名
		ctTeachCoursewaresShare.setSchoolId(userShareInfoDTO.getSchoolId());                      //分享到学校
		ctTeachCoursewaresShare.setSchoolName(userShareInfoDTO.getSchoolName());                  //学校名称
		ctTeachCoursewaresShare.setAreaId(userShareInfoDTO.getAreaId());                          //分享到地区
		ctTeachCoursewaresShare.setAreaName(userShareInfoDTO.getAreaName());                      //地区名称
		ctTeachCoursewaresShare.setStickStatus(0);                                                          //非置顶
		ctTeachCoursewaresShare.setOpinion("");                                                             //审核意见
		ctTeachCoursewaresShare.setStatus(1);                                                               //分享状态
		ctTeachCoursewaresShare.setShareTime(new Date());                                                   //分享时间


		/** 新增一条记录到大家分享的课件中（包括copy课件内容）**/
		ctTeachCoursewaresShareMapper.insert(ctTeachCoursewaresShare);

		//统计表
		CtTeachCoursewaresShareStatistics ctTeachCoursewaresShareStatistics = new CtTeachCoursewaresShareStatistics();
		ctTeachCoursewaresShareStatistics.setShareId(ctTeachCoursewaresShare.getShareId());                 //分享ID
		ctTeachCoursewaresShareStatistics.setCollectTimes(0);                                               //收藏次数
		ctTeachCoursewaresShareStatistics.setPraiseTimes(0);                                                //点赞次数
		ctTeachCoursewaresShareStatistics.setVewingTimes(0);                                                //预览次数
		ctTeachCoursewaresShareStatisticsMapper.insert(ctTeachCoursewaresShareStatistics);


		CtTeachCoursewaresContent ctTeachCoursewaresContent = ctTeachCoursewaresContentMapper.selectByPrimaryKey(userShareInfoDTO.getItemId(), userShareInfoDTO.getSchoolId());
		CtTeachCoursewaresShareContent ctTeachCoursewaresShareContent = new CtTeachCoursewaresShareContent();
		ctTeachCoursewaresShareContent.setCoursewaresId(ctTeachCoursewaresShare.getShareId());
		ctTeachCoursewaresShareContent.setBelongType(2);                                                    //分享表都是2：分享的课件
		ctTeachCoursewaresShareContent.setContent(ctTeachCoursewaresContent.getContent());

		ctTeachCoursewaresShareContentMapper.insert(ctTeachCoursewaresShareContent);

		/** 课件表中分享状态改为1--审核中*/
		ctTeachCoursewaresMapper.shareCourseware(userShareInfoDTO.getSchoolId(), userShareInfoDTO.getItemId());
	}

	@Override
	public void stickyTeachCourseware(String schoolId, String coursewaresId, String userId) throws CloudteachException {
		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(schoolId, coursewaresId);
		if (ctTeachCoursewares == null) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (!(ctTeachCoursewares.getUserId() != null && ctTeachCoursewares.getUserId().equals(userId))) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_YOURS);
		}

		ctTeachCoursewaresMapper.stickyTeachCourseware(schoolId, coursewaresId);
	}

	@Override
	public void cancelStickyTeachCourseware(String schoolId, String coursewaresId, String userId) throws CloudteachException {
		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(schoolId, coursewaresId);
		if (ctTeachCoursewares == null) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_FOUND);
		}

		if (!(ctTeachCoursewares.getUserId() != null && ctTeachCoursewares.getUserId().equals(userId))) {
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.TEACH_COURSEWARES_NOT_YOURS);
		}

		ctTeachCoursewaresMapper.cancelStickyTeachCourseware(schoolId, coursewaresId);
	}

	/**
	 * 查询用户在任务时间内分享成功的课件数
	 * 用于积分接口
	 * 如果开始时间大于结束时间，将被自动交换
	 *
	 * @param schoolId 学校ID【非空】
	 * @param userId   用户ID【非空】
	 * @param start    开始时间【非空】
	 * @param end      结束时间【非空】
	 * @return Long 分享次数
	 * @throws CloudteachException
	 */
	@Override
	public Long getCoursewareSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
		logger.info("查询用户在任务时间内分享成功的课件数，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);

		if (schoolId == null) {
			logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：schoolId为空，校验未通过");
		}

		if (userId == null) {
			logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：userId为空，校验未通过");
		}

		if (start == null) {
			logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：start为空，校验未通过");
		}

		if (end == null) {
			logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
			CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：end为空，校验未通过");
		}

//        如果start>end,二者交换，增加代码健壮性
		if (start.compareTo(end) > 0) {
			logger.warn("传入的开始时间大于结束时间，为保证结果正确性，二者将被交换：start = {} ，end = {} ", start, end);
			Date tmp = end;
			end = start;
			start = tmp;
			logger.warn("开始时间和结束时间已完成交换：start = {} ，end = {} ", start, end);
		}

		Long times = ctTeachCoursewaresMapper.selectSharedCount(schoolId, userId, start, end);

		if (times == null) {
			times = 0L;
		}

		logger.info("用户 {} 在 {} 至 {} 内 分享成功的课件数为 {} ", userId, start, end, times);

		return times;
	}


	/**
	 * 获取单个课件
	 * @param schoolId      学校ID
	 * @param coursewareId  课件ID
	 * @return  TeachCoursewaresDTO
	 * @throws CloudteachException
	 */
	@Override
	public TeachCoursewaresDTO getCourseware(String schoolId, String coursewareId) throws CloudteachException {

		TeachCoursewaresDTO coursewaresDTO = null;

		CtTeachCoursewares ctTeachCoursewares =  ctTeachCoursewaresMapper.selectByPrimaryKey(schoolId,coursewareId);

        coursewaresDTO = toCoursewareDTO(ctTeachCoursewares);

		return coursewaresDTO;
	}

	/**
	 * 更新课件打包状态
	 *
	 * @param schoolId     学校ID
	 * @param coursewareId 课件ID
	 * @param packStatus   打包状态 :0未打包,1打包中,2打包成功,3打包失败
	 * @param fileUri      zip包文件Key
	 * @return int 受影响的行数
	 * @throws CloudteachException
	 */
	@Override
	@Deprecated
	public int updateCoursePackStatus(String schoolId, String coursewareId, Integer packStatus, String fileUri) throws CloudteachException {
		return ctTeachCoursewaresMapper.updateCoursePackStatus(schoolId,coursewareId,packStatus,fileUri);
	}


	//===========================================课件打包接口 start============================================

	/**
	 * 发起打包请求并返回当前打包状态
	 *
	 * @param coursewaresId 课件ID
	 * @param schoolId      学校ID
	 * @param userId
	 * @return TeachCoursewarePackDTO
	 * @throws CloudteachException
	 */
	@Override
	public TeachCoursewarePackDTO pack(String coursewaresId, String schoolId, String userId) throws CloudteachException {
		TeachCoursewarePackDTO coursewarePackDTO = new TeachCoursewarePackDTO();
		Integer packStatus = CoursearePackStatusEnum.UNPACK.getStatus();

		coursewarePackDTO.setCoursewaresId(coursewaresId);
		coursewarePackDTO.setSchoolId(schoolId);
		coursewarePackDTO.setUserId(userId);

		//锁定当前行
		ctTeachCoursewaresStatisticsMapper.lockLine(coursewaresId,schoolId);

		//如果任务已存在,并且文件未失效，不发新的请求{查数据库，不走缓存}
		CtTeachCoursewaresStatistics coursewaresStatistics = ctTeachCoursewaresStatisticsMapper.selectByPrimaryKey(coursewaresId,schoolId);
		if (coursewaresStatistics!=null&&coursewaresStatistics.getPackStatus()!=null){
			packStatus = coursewaresStatistics.getPackStatus();
			if (packStatus == CoursearePackStatusEnum.INQUEUE.getStatus() ||
					packStatus == CoursearePackStatusEnum.PACKING.getStatus() ||
					(packStatus == CoursearePackStatusEnum.SUCCESS.getStatus()&&
							coursewaresStatistics.getPackExpDate() != null &&
							coursewaresStatistics.getPackExpDate().compareTo(new Date()) >= 0)) {
				logger.warn("课件：coursewareId={} 打包任务已在处理中,status={}", coursewaresId,packStatus);

                //返回数据库中结果，并更新到缓存
				coursewarePackDTO = toTeachCoursewarePackDTO(coursewaresStatistics);

                TeachCoursewaresDTO coursewaresDTO = getCourseware(schoolId, coursewaresId);
                UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(coursewaresDTO.getUnitId());

                //过滤非法字符，生成课件包名  ==》 {其中只保留中英文字符，数字，下划线，书名号《》，双引号，点，空白字符}
                String packName = unitsDTO.getUnitName() + "_" + coursewaresDTO.getTitle() + "_" + DateTimeUtil.getDateStr();
                packName = packName.replaceAll("[^a-zA-Z0-9_\\u4e00-\\u9fa5《》\"“”\\.\\s]", "");
                coursewarePackDTO.setName(packName);
                coursewarePackDTO.setExtension("zip");

                //更新缓存
                updatePackStatusToRedis(coursewarePackDTO);
				return coursewarePackDTO;
			}
		}

		//发送mq消息用于触发课件打包操作,并更新打包状态
        try {
            CoursewareMessageDTO coursewareMessageDTO = new CoursewareMessageDTO();

            coursewareMessageDTO.setCoursewateId(coursewaresId);
            coursewareMessageDTO.setSchoolId(schoolId);
            coursewareMessageDTO.setUserId(userId);

            packCoursewareProducer.sendDataToCrQueue(coursewareMessageDTO);
			packStatus = CoursearePackStatusEnum.INQUEUE.getStatus();
			coursewarePackDTO.setPackStatus(packStatus);

			//更新打包状态为排队中
			updatePackStatus(coursewarePackDTO);
        } catch (Exception e) {
			packStatus = CoursearePackStatusEnum.UNPACK.getStatus();
			coursewarePackDTO.setPackStatus(packStatus);
			logger.error("触发课件打包任务失败，", e);
		}

		//查询课件打包队列深度
		int queueDepth = 0;
		try {
			queueDepth = messageQueueAdmin.getQueueCount(coursePackQueueName);
		}catch (Exception e){
			logger.error("查询队列深度失败", e);
		}
		coursewarePackDTO.setQueueDepth(queueDepth);

		logger.warn("当前课件打包状态：coursewarePackDTO={}", coursewarePackDTO);
		return coursewarePackDTO;
	}

	/**
	 * 查询课件打包状态
	 *
	 * @param coursewaresId 课件ID
	 * @param schoolId      学校ID
	 * @param userId
	 * @return TeachCoursewarePackDTO
	 * @throws CloudteachException
	 */
	@Override
	public TeachCoursewarePackDTO getPackStatus(String coursewaresId, String schoolId, String userId) throws CloudteachException {
		TeachCoursewarePackDTO coursewarePackDTO = new TeachCoursewarePackDTO();
		coursewarePackDTO.setCoursewaresId(coursewaresId);
        coursewarePackDTO.setSchoolId(schoolId);
        coursewarePackDTO.setUserId(userId);

		//查询打包状态
        String redisKey = PACKINFOREDISKEYBASE + coursewaresId + ":";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String packDTOStr = cloudTeachRedisService.get(redisKey);
            //如果redis中的记录非空，取redis中的记录
            if (!StringUtils.isEmpty(packDTOStr)){
                coursewarePackDTO = objectMapper.readValue(packDTOStr.getBytes(),TeachCoursewarePackDTO.class);
            }else {
				logger.warn("从缓存未获取到课件打包状态");
				// 缓存中如果不存在，则认为未打包
				coursewarePackDTO.setPackStatus(CoursearePackStatusEnum.UNPACK.getStatus());
			}
        } catch (IOException e) {
            logger.error("从缓存获取课件打包状态失败，coursewaresId ={}, schoolId ={}, userId ={}", coursewaresId, schoolId, userId);
            logger.error("从缓存获取课件打包状态失败", e);
            // 缓存中如果不存在，则认为未打包
            coursewarePackDTO.setPackStatus(CoursearePackStatusEnum.UNPACK.getStatus());

        }

		//查询课件打包队列深度
		int queueDepth = 0;
		try {
			queueDepth = messageQueueAdmin.getQueueCount(coursePackQueueName);
		}catch (Exception e){
			logger.error("查询队列深度失败，", e);
		}
		coursewarePackDTO.setQueueDepth(queueDepth);

		logger.warn("当前课件打包状态：coursewarePackDTO={}", coursewarePackDTO);

		return coursewarePackDTO;
	}

	/**
	 * 更新课件打包状态到缓存和数据库
	 *
	 * @param packDTO 课件打包状态DTO
	 * @return boolean 是否更新成功
	 * @throws CloudteachException
	 */
	@Override
	public boolean updatePackStatus(TeachCoursewarePackDTO packDTO) throws CloudteachException {
        Assert.notNull(packDTO, "课件打包信息不能为空");
        Assert.notNull(packDTO.getCoursewaresId(), "课件ID不能为空");
        Assert.notNull(packDTO.getSchoolId(), "学校ID不能为空");

        //锁定当前行
        ctTeachCoursewaresStatisticsMapper.lockLine(packDTO.getCoursewaresId(),packDTO.getSchoolId());

		//更新缓存中的信息
        updatePackStatusToRedis(packDTO);

		//更新数据库中的信息
        CtTeachCoursewaresStatistics coursewaresStatistics = new CtTeachCoursewaresStatistics();
		coursewaresStatistics.setSchoolId(packDTO.getSchoolId());
		coursewaresStatistics.setCoursewaresId(packDTO.getCoursewaresId());
        coursewaresStatistics.setPackStatus(packDTO.getPackStatus());
        coursewaresStatistics.setPackUri(packDTO.getPackUri());
        coursewaresStatistics.setPackLastUpdate(packDTO.getPackLastUpdate());
        coursewaresStatistics.setPackExpDate(packDTO.getPackExpDate());

        ctTeachCoursewaresStatisticsMapper.updatePackStatus(coursewaresStatistics);

		return true;
	}

    /**
     * 更新缓存中的课件包信息
     *
     * @param packDTO 课件包状态DTO
     */
    private void updatePackStatusToRedis(TeachCoursewarePackDTO packDTO) {
        String redisKey = PACKINFOREDISKEYBASE + packDTO.getCoursewaresId() + ":";
        String packDTOStr = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            packDTOStr = objectMapper.writeValueAsString(packDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        cloudTeachRedisService.set(redisKey.getBytes(),packDTOStr.getBytes(),PACKEXP);
    }

    //===========================================课件打包接口 end  ============================================


	/**
     * 课件domain转DTO
     * @param ctTeachCoursewares 课件domain对象
     * @return TeachCoursewaresDTO
     */
    private TeachCoursewaresDTO toCoursewareDTO(CtTeachCoursewares ctTeachCoursewares) {
        if (ctTeachCoursewares == null) {
            return null;
        }
        TeachCoursewaresDTO teachCoursewaresDTO = new TeachCoursewaresDTO();
        teachCoursewaresDTO.setCoursewaresId(ctTeachCoursewares.getCoursewaresId());
        teachCoursewaresDTO.setCreator(ctTeachCoursewares.getCreator());
        teachCoursewaresDTO.setUnitId(ctTeachCoursewares.getUnitId());
        teachCoursewaresDTO.setIsCollect(ctTeachCoursewares.getIsCollect());
        teachCoursewaresDTO.setUserId(ctTeachCoursewares.getUserId());
        teachCoursewaresDTO.setPid(ctTeachCoursewares.getPid());
        teachCoursewaresDTO.setTitle(ctTeachCoursewares.getTitle());
        teachCoursewaresDTO.setStickStatus(ctTeachCoursewares.getStickStatus());
        teachCoursewaresDTO.setCreateTime(ctTeachCoursewares.getCreateTime());
        teachCoursewaresDTO.setUpdateTime(ctTeachCoursewares.getUpdateTime());
        teachCoursewaresDTO.setShareStatus(ctTeachCoursewares.getShareStatus());
        teachCoursewaresDTO.setSchoolId(ctTeachCoursewares.getSchoolId());
        teachCoursewaresDTO.setStatus(ctTeachCoursewares.getStatus());
        teachCoursewaresDTO.setPackStatus(ctTeachCoursewares.getPackStatus());
        teachCoursewaresDTO.setFileUri(ctTeachCoursewares.getFileUri());
        return teachCoursewaresDTO;
    }


    /**
	 * @param ctTeachCoursewares
	 * @return
	 */
	private CtTeachCoursewaresShare toCoursewaresShare(CtTeachCoursewares ctTeachCoursewares) {
		if (ctTeachCoursewares == null) {
			return null;
		}
		CtTeachCoursewaresShare ctTeachCoursewaresShare = new CtTeachCoursewaresShare();
		ctTeachCoursewaresShare.setCoursewaresId(ctTeachCoursewares.getCoursewaresId());
		ctTeachCoursewaresShare.setCreator(ctTeachCoursewares.getCreator());
		ctTeachCoursewaresShare.setUnitId(ctTeachCoursewares.getUnitId());
		ctTeachCoursewaresShare.setTitle(ctTeachCoursewares.getTitle());
		ctTeachCoursewaresShare.setStickStatus(ctTeachCoursewares.getStickStatus());
		ctTeachCoursewaresShare.setSchoolId(ctTeachCoursewares.getSchoolId());
		return ctTeachCoursewaresShare;
	}

	private void upateTeachCoursewaresInfo(TeachCoursewaresDTO teachCoursewaresDTO) {
		CtTeachCoursewares ctTeachCoursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(teachCoursewaresDTO.getSchoolId(),
				teachCoursewaresDTO.getCoursewaresId());

		ctTeachCoursewares = toCtCoursewares(ctTeachCoursewares, teachCoursewaresDTO);

		ctTeachCoursewaresMapper.updateByPrimaryKey(ctTeachCoursewares);
	}

	private CtTeachCoursewares toCtCoursewares(CtTeachCoursewares ctTeachCoursewares, TeachCoursewaresDTO teachCoursewaresDTO) {
		if (teachCoursewaresDTO == null) {
			return null;
		}
		ctTeachCoursewares.setTitle(teachCoursewaresDTO.getTitle());
		ctTeachCoursewares.setSchoolId(teachCoursewaresDTO.getSchoolId());
		ctTeachCoursewares.setUpdateTime(new Date());
		return ctTeachCoursewares;
	}

	private void upateTeachCoursewaresContent(TeachCoursewaresContentDTO teachCoursewaresContentDTO) {
		CtTeachCoursewaresContent ctTeachCoursewaresContent = new CtTeachCoursewaresContent();
		BeanUtils.copyProperties(teachCoursewaresContentDTO, ctTeachCoursewaresContent);
		ctTeachCoursewaresContent.setBelongType(1);
		ctTeachCoursewaresContentMapper.updateByPrimaryKey(ctTeachCoursewaresContent);
	}

    /**
     * 课件统计domain对象转课件打包状态DTO
     *
     * @param coursewaresStatistics 课件统计domain对象
     * @return TeachCoursewarePackDTO
     */
    private TeachCoursewarePackDTO toTeachCoursewarePackDTO(CtTeachCoursewaresStatistics coursewaresStatistics) {
        if (coursewaresStatistics == null) {
            return null;
        }
        TeachCoursewarePackDTO teachCoursewarePackDTO = new TeachCoursewarePackDTO();
        teachCoursewarePackDTO.setCoursewaresId(coursewaresStatistics.getCoursewaresId());
        teachCoursewarePackDTO.setSchoolId(coursewaresStatistics.getSchoolId());
        teachCoursewarePackDTO.setPackStatus(coursewaresStatistics.getPackStatus());
        teachCoursewarePackDTO.setPackLastUpdate(coursewaresStatistics.getPackLastUpdate());
        teachCoursewarePackDTO.setPackExpDate(coursewaresStatistics.getPackExpDate());
        teachCoursewarePackDTO.setPackUri(coursewaresStatistics.getPackUri());
        return teachCoursewarePackDTO;
    }

}