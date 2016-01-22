package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswer;
import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswerStatistics;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkItemAnswerStatisticsMapper {
    int insert(CtTeacherWorkItemAnswerStatistics record);

    CtTeacherWorkItemAnswerStatistics selectByPrimaryKey(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkItemAnswerStatistics record);

    List<CtTeacherWorkItemAnswerStatistics> getInitInfo(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int initCtTeacherWorkItemAnswerStat(@Param("initInfoList") List<CtTeacherWorkItemAnswer> initInfoList);

    int deleteTeacherWorkItemAnswerStatistics(@Param("answerList") List<String> answerList, @Param("schoolId") String schoolId);

    int deleteStuWorkItemAnswerStatistics(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkItemAnswerStatistics> getTeacherWorkItemAnswerStatisticsByWorkId(@Param("workId") String workId,
                                                                                       @Param("schoolId") String schoolId);

    Integer selectOthersPraiseTimes(@Param("answerId") String answerId, @Param("schoolId") String schoolId);
}