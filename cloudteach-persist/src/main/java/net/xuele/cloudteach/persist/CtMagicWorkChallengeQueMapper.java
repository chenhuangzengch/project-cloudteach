package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkChallengeQue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkChallengeQueMapper {
    int insert(CtMagicWorkChallengeQue record);

    CtMagicWorkChallengeQue selectByPrimaryKey(String cqId);

    int updateByPrimaryKey(CtMagicWorkChallengeQue record);

    /**
     * 根据作业id删除对应的学生挑战题目表记录
     * @param schoolId
     * @param workId
     * @return
     */
    int delMagicWorkChallengeQue (@Param("workId") String workId,@Param("schoolId") String schoolId );

    /**
     * 根据作业id、学生id删除某个学生挑战题目表记录
     * @param workId
     * @param userId
     * @param schoolId
     * @return
     */
    int delStuMagicWorkChallengeQue(@Param("workId") String workId,@Param("userId") String userId,@Param("schoolId") String schoolId );

    /**
     * 查询某一次挑战的题目信息
     * @param challengeId
     * @param schoolId
     * @return
     */
    List<CtMagicWorkChallengeQue> selectByChallengeId(@Param("challengeId") String challengeId,@Param("schoolId") String schoolId);
}