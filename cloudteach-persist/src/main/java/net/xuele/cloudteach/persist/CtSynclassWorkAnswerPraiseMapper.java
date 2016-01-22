package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtSynclassWorkAnswerPraise;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkAnswerPraiseMapper {

    int insert(CtSynclassWorkAnswerPraise record);

    CtSynclassWorkAnswerPraise selectByPrimaryKey(@Param("workUserId") String workUserId,
                                                  @Param("userId") String userId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtSynclassWorkAnswerPraise record);

    int getPraiseCountByAnswer(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

    int deleteByWorkUserId(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

    int getPraiseCountByUNIKEY(@Param("workUserId") String workUserId,
                               @Param("userId") String userId, @Param("schoolId") String schoolId);

}