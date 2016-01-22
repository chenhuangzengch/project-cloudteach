package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtTeachCoursewaresShareStatistics;

public interface CtTeachCoursewaresShareStatisticsMapper {
    int deleteByPrimaryKey(String shareId);

    int insert(CtTeachCoursewaresShareStatistics record);

    CtTeachCoursewaresShareStatistics selectByPrimaryKey(String shareId);

    List<CtTeachCoursewaresShareStatistics> selectAll();

    int updateByPrimaryKey(CtTeachCoursewaresShareStatistics record);
}