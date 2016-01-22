package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkChallenge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkChallengeMapper {

    int insert(CtMagicWorkChallenge record);

    CtMagicWorkChallenge selectByPrimaryKey(@Param("challengeId") String challengeId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkChallenge record);

    /**
     * 根据作业id删除对应的学生挑战记录
     *
     * @param schoolId
     * @param workId
     * @return
     */
    int delMagicWorkChallenge(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据作业id学生id删除某个学生挑战记录
     *
     * @param workId
     * @param userId
     * @param schoolId
     * @return
     */
    int delStuMagicWorkChallenge(@Param("workId") String workId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 通过挑战ID得到挑战记录信息
     *
     * @param challengeId
     * @param schoolId
     * @return
     */
    CtMagicWorkChallenge getMagicWorkChallengeInfo(@Param("challengeId") String challengeId, @Param("schoolId") String schoolId);

    /**
     * 得到一个提分宝作业的挑战次数
     *
     * @param workId
     * @param schoolId
     * @return
     */
    int getChallengeTimesByAnswerId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 学生提交作业时更改分享状态:0不分享1分享到作业交流2提交给老师
     *
     * @param shareStatus
     * @param challengeId
     * @param schoolId
     * @return
     */
    int updateShareStatus(@Param("shareStatus") int shareStatus, @Param("challengeId") String challengeId, @Param("schoolId") String schoolId);

    /**
     * 获取提分宝挑战记录
     *
     * @param workId   作业入口传入
     * @param bankId   应用中心入口传入
     * @param schoolId
     * @return
     */
    List<CtMagicWorkChallenge> getMagicWorkChallengeRecordByWork(@Param("workId") String workId, @Param("studentId") String studentId,
                                                                 @Param("bankId") String bankId, @Param("schoolId") String schoolId);

    /**
     * 获取提分宝挑战次数
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    int selectStuCount(@Param("workId") String workId, @Param("studentId") String studentId, @Param("schoolId") String schoolId);
}