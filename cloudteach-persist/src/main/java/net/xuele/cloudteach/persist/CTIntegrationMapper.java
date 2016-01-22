package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkChallenge;
import net.xuele.cloudteach.view.WorkFinishRateView;
import net.xuele.cloudteach.view.WorkSubStatusView;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/9/22 0022.
 */
public interface CTIntegrationMapper {

    /**
     * 获取时间段内布置的作业完成率
     */
    List<WorkFinishRateView> selectWorkFinishRate(@Param("teacherId") String teacherId, @Param("workType") int workType,
                                                  @Param("begin") Date begin, @Param("end") Date end,
                                                  @Param("schoolId") String schoolId);

    /**
     * 获取时间段内布置的作业提交状态
     */
    List<WorkSubStatusView> selectWorkSubStatus(@Param("studentId") String studentId, @Param("workType") int workType,
                                                @Param("begin") Date begin, @Param("end") Date end,
                                                @Param("schoolId") String schoolId);

    /**
     * 获取时间段内学生的评论次数
     */
    long selectStuCommentTimes(@Param("studentId") String studentId, @Param("schoolId") String schoolId,
                               @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 获取时间段内学生的点赞次数
     */
    long selectStuPraiseTimes(@Param("studentId") String studentId, @Param("schoolId") String schoolId,
                              @Param("begin") Date begin, @Param("end") Date end);

    List<CtMagicWorkChallenge> getMagicWorkChallengeInfo(@Param("studentId") String studentId, @Param("schoolId") String schoolId,
                                                         @Param("begin") Date begin, @Param("end") Date end);
}
