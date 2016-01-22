package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.domain.CtMagicWorkChallenge;
import net.xuele.cloudteach.dto.MagicWorkChallengeDTO;
import net.xuele.cloudteach.dto.WorkFinishRateViewDTO;
import net.xuele.cloudteach.dto.WorkSubStatusViewDTO;
import net.xuele.cloudteach.persist.CTIntegrationMapper;
import net.xuele.cloudteach.service.BankItemService;
import net.xuele.cloudteach.service.CTIntegrationService;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.service.TeachCoursewaresService;
import net.xuele.cloudteach.view.WorkFinishRateView;
import net.xuele.cloudteach.view.WorkSubStatusView;
import net.xuele.common.exceptions.CloudteachException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CTIntegrationService
 * <p/>
 * 云教学提供的积分任务服务接口实现
 *
 * @author hujx
 * @date 2015/9/22 0022.
 */
@Service
public class CTIntegrationServiceImpl implements CTIntegrationService {

    @Autowired
    CTIntegrationMapper ctIntegrationMapper;

    @Autowired
    CloudDiskService cloudDiskService;

    @Autowired
    BankItemService bankItemService;

    @Autowired
    TeachCoursewaresService teachCoursewaresService;

    private static Logger logger = LoggerFactory.getLogger(CTIntegrationServiceImpl.class);
    // ***************************** 注：此处为教师任务服务 ***************************** //

    /**
     * 查询用户在任务时间内上传的云盘资源数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 上传次数
     * @throws CloudteachException
     */
    public Long getDiskUploadAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
        return cloudDiskService.getDiskUploadAmount(schoolId, userId, start, end);
    }

    /**
     * 查询用户在任务时间内分享成功的云盘资源数
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
    public Long getDiskSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
        return cloudDiskService.getDiskSharedAmount(schoolId, userId, start, end);
    }

    /**
     * 查询用户在任务时间内分享成功的预习题数
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
    public Long getGuidanceSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
        return bankItemService.getBankItemSharedAmount(schoolId, userId, CloudTeachBankEnum.ITEM_TYPE_GUIDANCE.getCode(), start, end);
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
    public Long getCoursewareSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
        return teachCoursewaresService.getCoursewareSharedAmount(schoolId, userId, start, end);
    }

    /**
     * 根据时间段，查询出教室布置的作业，以及完成率
     */
    @Override
    public List<WorkFinishRateViewDTO> getWorkFinishRate(String teacherId, int workType, Date begin, Date end, String schoolId) throws CloudteachException {

        List<WorkFinishRateViewDTO> rateDTOs = new ArrayList<>();
        List<WorkFinishRateView> rateViews = ctIntegrationMapper.selectWorkFinishRate(teacherId, workType, begin, end, schoolId);

        for (WorkFinishRateView rateView : rateViews) {
            WorkFinishRateViewDTO rateDTO = new WorkFinishRateViewDTO();
            BeanUtils.copyProperties(rateView, rateDTO);
            rateDTOs.add(rateDTO);
        }

        return rateDTOs;
    }

    /**
     * 根据时间段，查询学生提交作业状态
     */
    @Override
    public List<WorkSubStatusViewDTO> getWorkSubStatus(String studentId, int workType, Date begin, Date end, String schoolId) throws CloudteachException {

        List<WorkSubStatusViewDTO> subStatusDTOs = new ArrayList<>();
        List<WorkSubStatusView> subStatusViews = ctIntegrationMapper.selectWorkSubStatus(studentId, workType, begin, end, schoolId);

        for (WorkSubStatusView subStatusView : subStatusViews) {
            WorkSubStatusViewDTO subStatusDTO = new WorkSubStatusViewDTO();
            BeanUtils.copyProperties(subStatusView, subStatusDTO);
            subStatusDTOs.add(subStatusDTO);
        }

        return subStatusDTOs;
    }



    // ***************************** 注：此处为学生任务服务 ***************************** //


    /**
     * 根据时间段获取学生评论次数
     *
     * @return
     * @throws CloudteachException
     */
    @Override
    public long getStuCommentTimes(String studentId, Date begin, Date end, String schoolId) throws CloudteachException {

        return ctIntegrationMapper.selectStuCommentTimes(studentId, schoolId, begin, end);
    }

    /**
     * 获取一周内学生点赞次数t
     *
     * @return
     * @throws CloudteachException
     */
    @Override
    public long getStuPraiseTimes(String studentId, Date begin, Date end, String schoolId) throws CloudteachException {

        return ctIntegrationMapper.selectStuPraiseTimes(studentId, schoolId, begin, end);
    }

    /**
     * 云学习端获取学生挑战记录
     * @author panglx
     * @param studentId
     * @param begin
     * @param end
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    public List<MagicWorkChallengeDTO> getMagicWorkChallengeInfo(String studentId, Date begin, Date end, String schoolId) throws CloudteachException{
        logger.info("获取云学习端提分宝挑战记录列表：getMagicWorkChallengeInfo[studentId="+studentId+",begin="+begin+",end="+end+",schoolId="+schoolId+"]");
        List<CtMagicWorkChallenge> magicWorkChallengeList = ctIntegrationMapper.getMagicWorkChallengeInfo(studentId,schoolId,begin,end);
        logger.info("domain对象转DTO");
        List<MagicWorkChallengeDTO> magicWorkChallengeDTOList = new ArrayList<>();
        MagicWorkChallengeDTO dto;
        if (CollectionUtils.isNotEmpty(magicWorkChallengeList)){
            for(CtMagicWorkChallenge m:magicWorkChallengeList){
                dto = new MagicWorkChallengeDTO();
                BeanUtils.copyProperties(m,dto);
                magicWorkChallengeDTOList.add(dto);
            }
        }
        logger.info("返回DTO列表:"+magicWorkChallengeDTOList);
        return magicWorkChallengeDTOList;
    }

    // ***************************** 注：此处为家长任务服务 ***************************** //
}
