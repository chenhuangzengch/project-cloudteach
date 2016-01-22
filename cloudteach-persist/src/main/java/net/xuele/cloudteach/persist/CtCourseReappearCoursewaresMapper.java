package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtCourseReappearCoursewares;
import org.apache.ibatis.annotations.Param;

public interface CtCourseReappearCoursewaresMapper {
    int deleteByPrimaryKey(String rcId);

    int insert(CtCourseReappearCoursewares record);

    CtCourseReappearCoursewares selectByPrimaryKey(@Param("rcId")String rcId);

    List<CtCourseReappearCoursewares> selectAll(String schoolId);

    List<CtCourseReappearCoursewares> queryCoursewaresListByReappearId(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtCourseReappearCoursewares record);

    int batchInsert(List<CtCourseReappearCoursewares> coursewaresIdList);

    int updateByReappearId(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    int updateBycoursewaresIdList(@Param("coursewaresIdList")List<String> coursewaresIdList,@Param("schoolId")String schoolId);

    List<CtCourseReappearCoursewares> getInitInfo(@Param("list")List<String> coursewaresIdList,
                                                  @Param("reappearId")String reappearId,@Param("schoolId")String schoolId);
}