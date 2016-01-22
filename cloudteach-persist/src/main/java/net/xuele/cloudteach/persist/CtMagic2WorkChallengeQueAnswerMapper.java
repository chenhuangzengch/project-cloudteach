package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagic2WorkChallengeQueAnswer;
import net.xuele.cloudteach.view.Magic2WorkChallengeQueAnswerView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagic2WorkChallengeQueAnswerMapper {
    int deleteByPrimaryKey(@Param("cqaId")String cqaId,@Param("schoolId")String schoolId);

    int insert(CtMagic2WorkChallengeQueAnswer record);

    CtMagic2WorkChallengeQueAnswer selectByPrimaryKey(@Param("cqaId")String cqaId,@Param("schoolId")String schoolId);

    List<CtMagic2WorkChallengeQueAnswer> selectAll();

    int updateByPrimaryKey(CtMagic2WorkChallengeQueAnswer record);

    int batchInsert(@Param("cqId")String cqId,
                    @Param("challengeId")String challengeId,
                    @Param("userId")String userId,
                    @Param("queId")String queId,
                    @Param("schoolId")String schoolId,
                    @Param("challengeQuesAns")List<Magic2WorkChallengeQueAnswerView> challengeQuesAns
    );

    /**
     * 查询题目对应的选项
     * @param schoolId
     * @param challengeId
     * @param queId
     * @return
     */
    List<CtMagic2WorkChallengeQueAnswer> selectQueAnswer(@Param("schoolId")String schoolId,
                    @Param("challengeId")String challengeId,
                    @Param("queId")String queId);
}