package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswerPraise;
import org.apache.ibatis.annotations.Param;

public interface CtMagicWorkAnswerPraiseMapper {

    int insert(CtMagicWorkAnswerPraise record);

    CtMagicWorkAnswerPraise selectByPrimaryKey(@Param("answerId") String answerId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkAnswerPraise record);

    /**
     * 逻辑删除提分宝作业对应的回答点赞记录
     * @param workId
     * @return
     */
    int deleteByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 逻辑删除提分宝作业对应的回答点赞记录--单条
     * @param answerId
     * @return
     */
    int delete(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    int getPraiseCountByAnswer(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    int getPraiseCountByUNIKEY(@Param("answerId") String answerId, @Param("userId") String userId, @Param("schoolId") String schoolId);
}