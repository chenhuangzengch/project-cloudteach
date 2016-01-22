package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtWorkStatistics;
import net.xuele.cloudteach.view.WorkStatisticsView;
import org.apache.ibatis.annotations.Param;

public interface CtWorkStatisticsMapper {

    int insert(CtWorkStatistics record);

    CtWorkStatistics selectByPrimaryKey(@Param("workId")String workId,@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtWorkStatistics record);

    WorkStatisticsView selectWorkStatistics(@Param("workId")String workId,@Param("schoolId")String schoolId);
}