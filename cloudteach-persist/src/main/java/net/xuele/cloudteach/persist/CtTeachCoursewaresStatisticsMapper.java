package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtTeachCoursewaresStatistics;
import org.apache.ibatis.annotations.Param;

public interface CtTeachCoursewaresStatisticsMapper {
    int deleteByPrimaryKey(String coursewaresId);

    int insert(CtTeachCoursewaresStatistics record);

    CtTeachCoursewaresStatistics selectByPrimaryKey(@Param("coursewaresId")String coursewaresId,@Param("schoolId")String schoolId);

    List<CtTeachCoursewaresStatistics> selectAll(String schoolId);

    int updateByPrimaryKey(@Param("coursewaresId")String coursewaresId,@Param("schoolId")String schoolId);

    int batchUpdateReduceReleases(@Param("coursewaresIdList")List<String> coursewaresIdList,@Param("schoolId")String schoolId);

    int batchUpdateIncreaseReleases(@Param("coursewaresIdList")List<String> coursewaresIdList,@Param("schoolId")String schoolId);

    /**
     * 更新课件打包状态
     * @param coursewaresStatistics
     * @return
     */
    int updatePackStatus(@Param("coursewaresStatistics")CtTeachCoursewaresStatistics coursewaresStatistics);

    /**
     * 锁定行
     * @param coursewaresId
     * @param schoolId
     * @return
     */
    int lockLine(@Param("coursewaresId")String coursewaresId,@Param("schoolId")String schoolId);
}