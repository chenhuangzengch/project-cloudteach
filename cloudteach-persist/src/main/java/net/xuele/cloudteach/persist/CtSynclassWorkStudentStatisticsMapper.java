package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtSynclassWorkStudentStatistics;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkStudentStatisticsMapper {

    int insert(CtSynclassWorkStudentStatistics record);

    CtSynclassWorkStudentStatistics selectByPrimaryKey(@Param("workUserId")String workUserId,@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtSynclassWorkStudentStatistics record);

    int initStudentStatistics(@Param("workUserIdList")List<String> workUserIdList,@Param("schoolId")String schoolId);

    int updateCommentTimes(@Param("time")int time,@Param("workUserId")String workUserId,@Param("schoolId")String schoolId);

    int updatePraiseTimes(@Param("time")int time,@Param("workUserId")String workUserId,@Param("schoolId")String schoolId);

    int updateStatusByPrimaryKey(@Param("workUserId")String workUserId,@Param("schoolId")String schoolId);
}