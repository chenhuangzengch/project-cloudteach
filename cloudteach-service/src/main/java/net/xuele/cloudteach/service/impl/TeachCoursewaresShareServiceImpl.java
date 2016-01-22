package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.CoursewaresResponseDTO;
import net.xuele.cloudteach.dto.TeachCoursewaresShareDTO;
import net.xuele.cloudteach.dto.page.TeachCoursewaresShareRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.TeachCoursewaresShareService;
import net.xuele.cloudteach.view.CoursewaresResponseView;
import net.xuele.cloudteach.view.TeachCoursewaresShareView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 分享的课件实现
 * Created by panglx on 2015/7/17 0017.
 */
@Service
public class TeachCoursewaresShareServiceImpl implements TeachCoursewaresShareService {
	private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresShareServiceImpl.class);

	@Autowired
	private CtTeachCoursewaresMapper coursewaresMapper;//课件Mapper

	@Autowired
	private CtTeachCoursewaresShareMapper coursewaresShareMapper;//分享的课件Mapper

	@Autowired
	private CtTeachCoursewaresShareCollectMapper coursewaresShareCollectMapper;//分享课件收藏Mapper

	@Autowired
	private CtTeachCoursewaresSharePraiseMapper coursewaresSharePraiseMapper;//分享课件点赞Mapper

	@Autowired
	private CtTeachCoursewaresShareStatisticsMapper coursewaresShareStatisticsMapper;//分享课件计数Mapper

	@Autowired
	private CtTeachCoursewaresContentMapper coursewaresContentMapper;//课件内容Mapper

	@Autowired
	private CtTeachCoursewaresShareContentMapper ctTeachCoursewaresShareContentMapper;//分享课件内容
	/**
	 * 分页获取分享的授课课件信息
	 * @param request
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public PageResponse<TeachCoursewaresShareDTO> queryTCoursewaresShareResource(TeachCoursewaresShareRequest request) throws CloudteachException {
		logger.info("调用服务分页获取大家分享的授课课件：queryTeachCoursewaresShareByPage");
		CtTeachCoursewaresShare coursewaresShare = new CtTeachCoursewaresShare();
		coursewaresShare.setUnitId(request.getUnitId());//课程id
		coursewaresShare.setCreator(request.getCreator());//用户id
		coursewaresShare.setSchoolId(request.getSchoolId());//学校id
		coursewaresShare.setAreaId(request.getAreaId());//地区id

		int seltype = request.getSeltype();//筛选类型
		//获取数据
		Page page = PageUtils.buildPage(request);
		page.setPage(request.getPage());
		int pagesize = request.getPageSize();//页面大小
		//大家分享的授课课件
		logger.info("页面大小：pagesize="+pagesize);
		List<TeachCoursewaresShareView> coursewaresShareViews =  coursewaresShareMapper.selectByPage(pagesize, page, coursewaresShare, seltype);

		int rows = coursewaresShareMapper.selectCount(coursewaresShare,seltype);
		logger.info("总记录数：rows="+rows);
		//domain转DTO
		List<TeachCoursewaresShareDTO> coursewaresShareDTOs = new ArrayList<>();
		TeachCoursewaresShareDTO coursewaresShareDTO;
		for (TeachCoursewaresShareView v:coursewaresShareViews){
			coursewaresShareDTO = new TeachCoursewaresShareDTO();
			BeanUtils.copyProperties(v,coursewaresShareDTO);
			coursewaresShareDTOs.add(coursewaresShareDTO);
		}
		//返回PageResponse
		PageResponse<TeachCoursewaresShareDTO> response = new PageResponse<>();
		PageUtils.buldPageResponse(request, response);
		//我分享的总记录数
		response.setRecords(rows);//我的分享总数
		response.setRows(coursewaresShareDTOs);
		return response;
	}

	/**
	 * 分享课件点赞
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException  有可能抛出异常 【已经点赞不能点赞】，【自己分享的不能点赞】
	 */
	@Override
	public int praise(String shareId, String creator) throws CloudteachException {
		CtTeachCoursewaresShare coursewaresShare = coursewaresShareMapper.selectByPrimaryKey(shareId);
		if(null == coursewaresShare){
			//查找的对象不存在
			throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
		} else if( coursewaresShare.getCreator().equals(creator)){
			//自己分享的不能点赞
			throw new CloudteachException(CloudTeachErrorEnum.USERSHARENOPRAISE.getMsg(), CloudTeachErrorEnum.USERSHARENOPRAISE.getCode());
		}
		logger.info("查询点赞记录");
		CtTeachCoursewaresSharePraise coursewaresSharePraise = coursewaresSharePraiseMapper.selectByPrimaryKey(shareId,creator);
		if (null ==coursewaresSharePraise){//如果点赞记录为空，则新增点赞记录
			logger.info("点赞记录为空，则新增点赞记录");
			coursewaresSharePraise = new CtTeachCoursewaresSharePraise();
			coursewaresSharePraise.setUserId(creator);
			coursewaresSharePraise.setShareId(shareId);
			coursewaresSharePraise.setPraiseStatus(1);
			coursewaresSharePraise.setPraiseTime(new Date());
			coursewaresSharePraiseMapper.insert(coursewaresSharePraise);
		}else if (coursewaresSharePraise.getPraiseStatus()==1){
			//已经点赞不能再次点赞
			throw new CloudteachException(CloudTeachErrorEnum.ALREADYPRAISED.getMsg(), CloudTeachErrorEnum.ALREADYPRAISED.getCode());
		}else{
			logger.info("已经存在点赞记录，点赞状态改为1");
			coursewaresSharePraise.setPraiseTime(new Date());
			coursewaresSharePraise.setPraiseStatus(1);
			coursewaresSharePraiseMapper.updateByPrimaryKey(coursewaresSharePraise);
		}
		logger.info("课件分享统计表点赞数+1");
		return this.modifyStatisticsTimes(shareId,1,2);
	}

	/**
	 * 分享课件取消赞
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException 有可能抛出异常 【未点赞不能取消点赞】
	 */
	@Override
	public int cancelPraise(String shareId, String creator) throws CloudteachException {
		logger.info("查询点赞记录");
		CtTeachCoursewaresSharePraise coursewaresSharePraise = coursewaresSharePraiseMapper.selectByPrimaryKey(shareId,creator);
		if (null == coursewaresSharePraise){
			//未点赞不能取消点赞
			throw new CloudteachException(CloudTeachErrorEnum.UNPRAISED.getMsg(), CloudTeachErrorEnum.UNPRAISED.getCode());
		}else if (coursewaresSharePraise.getPraiseStatus()==0){
			//未点赞不能取消点赞
			throw new CloudteachException(CloudTeachErrorEnum.UNPRAISED.getMsg(), CloudTeachErrorEnum.UNPRAISED.getCode());
		}else{
			logger.info("点赞记录表状态改为0--未赞");
			coursewaresSharePraise.setPraiseStatus(0);
			coursewaresSharePraiseMapper.updateByPrimaryKey(coursewaresSharePraise);
			logger.info("课件分享统计表点赞数-1");
			return this.modifyStatisticsTimes(shareId,-1,2);
		}
	}

	/**
	 * 分享课件收藏
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException 可能抛出的异常 【不能收藏自己的资源】【已经收藏】
	 */
	@Override
	public String collectItem(String shareId, String creator,String schoolId) throws CloudteachException {
		CtTeachCoursewaresShare coursewaresShare = coursewaresShareMapper.selectByPrimaryKey(shareId);
		if(null == coursewaresShare){
			//查找的对象不存在
			throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
		} else if( coursewaresShare.getCreator().equals(creator) ){
			//不能收藏自己的资源
			throw new CloudteachException(CloudTeachErrorEnum.CANNOTCOLLECTBYSELF.getMsg(), CloudTeachErrorEnum.CANNOTCOLLECTBYSELF.getCode());
		}
		logger.info("查询收藏记录表");
		CtTeachCoursewaresShareCollect coursewaresShareCollect = coursewaresShareCollectMapper.selectByPrimaryKey(shareId,creator);
		if (null == coursewaresShareCollect){
			logger.info("没有收藏记录，收藏表insert");
			coursewaresShareCollect = new CtTeachCoursewaresShareCollect();
			coursewaresShareCollect.setShareId(shareId);
			coursewaresShareCollect.setCollectStatus(1);
			coursewaresShareCollect.setCollectTime(new Date());
			coursewaresShareCollect.setUserId(creator);
			coursewaresShareCollectMapper.insert(coursewaresShareCollect);
		}else if (coursewaresShareCollect.getCollectStatus()==1){
			//已经收藏
			throw new CloudteachException(CloudTeachErrorEnum.ALREADYCILLECTED.getMsg(), CloudTeachErrorEnum.ALREADYCILLECTED.getCode());
		}else{
			logger.info("有收藏记录 update收藏记录表状态，改为1--已收藏");
			coursewaresShareCollect.setCollectStatus(1);
			coursewaresShareCollect.setCollectTime(new Date());
			coursewaresShareCollectMapper.updateByPrimaryKey(coursewaresShareCollect);
		}
		logger.info("我的授课课件表新增");
		CtTeachCoursewares coursewares = new CtTeachCoursewares();
		coursewares.setUserId(coursewaresShare.getCreator());//收藏来源用户
		coursewares.setCreator(creator);//创建者
		coursewares.setIsCollect(1);//是否收藏
		coursewares.setPid(shareId);//来源id
		coursewares.setStickStatus(0);//置顶标志
		coursewares.setCreateTime(new Date());
		coursewares.setUpdateTime(new Date());
		coursewares.setStatus(1);
		coursewares.setShareStatus(0);//分享状态改为私有状态
		coursewares.setCoursewaresId(UUID.randomUUID().toString().replace("-", ""));//课件id
		coursewares.setTitle(coursewaresShare.getTitle());//文件名
		coursewares.setUnitId(coursewaresShare.getUnitId());//课程id
		coursewares.setSchoolId(schoolId);
		coursewaresMapper.insert(coursewares);
		logger.info("授课课件内容表insert");
		this.copyContent(shareId,coursewares.getCoursewaresId(),schoolId);
		logger.info("分享的授课课件统计表 收藏数+1");
		this.modifyStatisticsTimes(shareId,1,1);
		logger.info("返回我的授课课件id");
		return coursewares.getCoursewaresId();
	}

	/**
	 * 分享课件取消收藏
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException  可能抛出的异常 【未收藏】
	 */
	@Override
	public String cancelCollect(String shareId, String creator,String schoolId) throws CloudteachException {
		logger.info("查询收藏记录表");
		CtTeachCoursewaresShareCollect coursewaresShareCollect = coursewaresShareCollectMapper.selectByPrimaryKey(shareId,creator);
		if (null == coursewaresShareCollect || coursewaresShareCollect.getCollectStatus()==0){//没有收藏记录
			//未收藏
			throw new CloudteachException(CloudTeachErrorEnum.UNCOLLECTED.getMsg(), CloudTeachErrorEnum.UNCOLLECTED.getCode());
		}else {
			logger.info("收藏状态改为0--未收藏");
			coursewaresShareCollect.setCollectStatus(0);
			coursewaresShareCollectMapper.updateByPrimaryKey(coursewaresShareCollect);
		}
		logger.info("我的授课课件表逻辑删除update");
		CtTeachCoursewares coursewares = coursewaresMapper.selectMyCollect(shareId, creator,schoolId);
		coursewares.setStatus(0);
		coursewaresMapper.updateByPrimaryKey(coursewares);
		logger.info("分享的授课课件统计表 收藏次数-1");
		this.modifyStatisticsTimes(shareId,-1,1);
		logger.info("返回取消收藏的授课课件id");
		return coursewares.getCoursewaresId();
	}

	/**
	 * 取消分享
	 * @param shareId
	 * @param creator
	 * @param schoolId
	 * @throws CloudteachException
	 */
	@Override
	public String cancelShare(String shareId,String creator,String schoolId) throws CloudteachException {
		CtTeachCoursewaresShare coursewaresShare = coursewaresShareMapper.selectByPrimaryKey(shareId);
		if (coursewaresShare == null){
			//查找的对象不存在(分享状态不对)
			throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
		}else if (!coursewaresShare.getCreator().equals(creator)){
			//判断是否我分享的(不是自己分享的不能取消分享)
			throw new CloudteachException(CloudTeachErrorEnum.NOTMYSHARE.getMsg(), CloudTeachErrorEnum.NOTMYSHARE.getCode());
		}
		logger.info("分享的授课课件状态改为0");
		coursewaresShareMapper.unShareCourseware(shareId);
		logger.info("授课课件分享状态改为0");
		coursewaresMapper.unShareCourseware(schoolId,coursewaresShare.getCoursewaresId());
		logger.info("返回取消的授课课件id");
		return coursewaresShare.getCoursewaresId();
	}

	/**
	 * 分享的课件预览
	 * @param shareId
	 * @param currentUserId
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public CoursewaresResponseDTO preview(String shareId, String currentUserId) throws CloudteachException {
		CoursewaresResponseView coursewaresResponseView = coursewaresShareMapper.preview(shareId);
		CoursewaresResponseDTO coursewaresResponseDTO = new CoursewaresResponseDTO();
		BeanUtils.copyProperties(coursewaresResponseView,coursewaresResponseDTO);
		//浏览数+1
		if(!coursewaresResponseView.getUserId().equals(currentUserId)){
			this.modifyStatisticsTimes(shareId,1,3);//不是当前用户浏览，浏览数+1
		}

		return coursewaresResponseDTO;
	}
	/*************************************公用方法******************************************************/
	/**
	 * 授课课件内容表insert记录
	 * @param sourceId
	 * @param targetId
	 * @param schoolId
	 */
	private void copyContent(String sourceId,String targetId,String schoolId){
		//查询分享课件的内容信息
		CtTeachCoursewaresShareContent coursewaresContent = ctTeachCoursewaresShareContentMapper.selectByPrimaryKey(sourceId);
		if(null ==coursewaresContent){//没有附件
			//分享的课件内容为空
			throw new CloudteachException(CloudTeachErrorEnum.COURSEWARES_CONTENT_NOT_EXIST.getMsg(), CloudTeachErrorEnum.COURSEWARES_CONTENT_NOT_EXIST.getCode());
		}else{
			CtTeachCoursewaresContent content = new CtTeachCoursewaresContent();
			content.setCoursewaresId(targetId);
			content.setBelongType(1);
			content.setContent(coursewaresContent.getContent());
			content.setSchoolId(schoolId);
			//信息内容信息
			coursewaresContentMapper.insert(content);
		}

	}
	/**
	 * 更改预习项分享统计表数量
	 * @param shareId
	 * @param num
	 * @return
	 */
	private int modifyStatisticsTimes(String shareId,int num,int type){
		logger.info("更改预习项分享统计表数量modifyStatisticsTimes(String shareId,int num,int type)：type==1 收藏数；type ==2 点赞数；type == 3 浏览数");
		CtTeachCoursewaresShareStatistics coursewaresShareStatistics = new CtTeachCoursewaresShareStatistics();
		coursewaresShareStatistics.setShareId(shareId);
		if(type==1){//收藏数
			coursewaresShareStatistics.setCollectTimes(num);
		}else if(type ==2){//点赞数
			coursewaresShareStatistics.setPraiseTimes(num);
		}else if (type == 3){//浏览数
			coursewaresShareStatistics.setVewingTimes(num);
		}
		return coursewaresShareStatisticsMapper.updateByPrimaryKey(coursewaresShareStatistics);
	}

}
