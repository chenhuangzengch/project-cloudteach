package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.MagicWorkChallengeDTO;
import net.xuele.cloudteach.dto.WorkFinishRateViewDTO;
import net.xuele.cloudteach.dto.WorkSubStatusViewDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.Date;
import java.util.List;

/**
 * CTIntegrationService
 * <p/>
 * 云教学提供的积分任务服务接口
 *
 * @author hujx
 * @date 2015/9/22 0022.
 */
public interface CTIntegrationService {

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
    Long getDiskUploadAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;

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
    Long getDiskSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;

    /**
     * 查询用户在任务时间内分享成功的题目数
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
    Long getGuidanceSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;


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
    Long getCoursewareSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;


    /**
     * 根据时间段，查询出教室布置的作业，以及完成率
     *
     * @param teacherId
     * @param workType
     * @param begin
     * @param end
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    List<WorkFinishRateViewDTO> getWorkFinishRate(String teacherId, int workType, Date begin, Date end, String schoolId) throws CloudteachException;


    // ***************************** 注：此处为学生任务服务 ***************************** //

    /**
     * 根据时间段，查询学生提交作业状态
     *
     * @param studentId
     * @param workType
     * @param begin
     * @param end
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    List<WorkSubStatusViewDTO> getWorkSubStatus(String studentId, int workType, Date begin, Date end, String schoolId) throws CloudteachException;

    /**
     * 根据时间段获取学生评论次数
     *
     * @return
     * @throws CloudteachException
     */
    long getStuCommentTimes(String studentId, Date begin, Date end, String schoolId) throws CloudteachException;

    /**
     * 根据时间段获取学生点赞次数
     *
     * @return
     * @throws CloudteachException
     */
    long getStuPraiseTimes(String studentId, Date begin, Date end, String schoolId) throws CloudteachException;

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
    List<MagicWorkChallengeDTO> getMagicWorkChallengeInfo(String studentId, Date begin, Date end, String schoolId) throws CloudteachException;

    // ***************************** 注：此处为家长任务服务 ***************************** //
}
