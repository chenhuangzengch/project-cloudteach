package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagic2WorkChallengeQue;
import net.xuele.cloudteach.view.Magic2FirstQuestView;
import net.xuele.cloudteach.view.QueAndSortView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagic2WorkChallengeQueMapper {
    int deleteByPrimaryKey(@Param("cqId")String cqId,@Param("schoolId")String schoolId);

    int insert(CtMagic2WorkChallengeQue record);

    CtMagic2WorkChallengeQue selectByPrimaryKey(@Param("cqId")String cqId,@Param("schoolId")String schoolId);

    List<CtMagic2WorkChallengeQue> selectAll();

    int updateByPrimaryKey(CtMagic2WorkChallengeQue record);

    /**
     * 获取练习原题id
     * @param practiceId
     * @return
     */
    List<String> selectOriQuestId(@Param("practiceId")String practiceId,@Param("schoolId")String schoolId);

    /**
     * 获取某一课程下某个用户练习过的原题题目
     * @param schoolId
     * @param userId
     * @param unitId
     * @return
     */
    List<String> selectChallengedQuestId(@Param("schoolId")String schoolId,
                                         @Param("userId")String userId,
                                         @Param("unitId")String unitId
                                            );

    List<QueAndSortView> selectOverTimeQuestId(@Param("schoolId")String schoolId,
                                       @Param("userId")String userId,
                                       @Param("unitId")String unitId
    );
    /**
     * 获取某一课程下某个用户练习错误率大于 rate 的题目
     * @param schoolId
     * @param userId
     * @param unitId
     * @param rate
     * @return
     */
    List<String> selectWrongOriQuestId(@Param("schoolId")String schoolId,
                                         @Param("userId")String userId,
                                         @Param("unitId")String unitId,
                                         @Param("rate")double rate
                                         );

    /**
     * 查询第一次挑战题目id、sort
     * @param schoolId
     * @param challengeId
     * @return
     */
    List<Magic2FirstQuestView> selectFirstQuestId(@Param("schoolId")String schoolId,
                                                  @Param("challengeId")String challengeId);

}