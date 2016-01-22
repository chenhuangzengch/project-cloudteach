package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswerPraise;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkItemAnswerPraiseMapper {

    int insert(CtTeacherWorkItemAnswerPraise record);

    CtTeacherWorkItemAnswerPraise selectByPrimaryKey(@Param("answerId") String answerId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkItemAnswerPraise record);

    int deleteTeacherWorkItemAnswerPraise(@Param("answerList") List<String> answerList, @Param("schoolId") String schoolId);

    int deleteStuWorkItemAnswerPraise(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    int getPraiseCountByAnswer(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    int getPraiseCountByUNIKEY(@Param("answerId") String answerId, @Param("userId") String userId, @Param("schoolId") String schoolId);

}