package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtCourseReappearStudent;
import org.apache.ibatis.annotations.Param;

public interface CtCourseReappearStudentMapper {
    int deleteByPrimaryKey(String rsId);

    int insert(CtCourseReappearStudent record);

    CtCourseReappearStudent selectByPrimaryKey(@Param("rsId")String rsId,@Param("schoolId")String schoolId);

    List<CtCourseReappearStudent> selectAll(String schoolId);

    List<CtCourseReappearStudent> queryCourseReappearStudentList(@Param("reappearId")String reappearId);

    int updateByPrimaryKey(CtCourseReappearStudent record);

    int updateByRcIdAndUserId(@Param("reappearStudent")CtCourseReappearStudent reappearStudent ,@Param("rcId")String rcId,@Param("schoolId")String schoolId);

    int updateByReappearId(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    int getStudentNumByClassIdList(@Param("list")List<String> list,@Param("schoolId")String schoolId);

    int batchUpdateByClassList(@Param("list")List<String> list,@Param("schoolId")String schoolId);

    int getCountByReappearId(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    List<CtCourseReappearStudent> getInitInfo(@Param("classList")List<String> classList,
                                              @Param("schoolId")String schoolId,@Param("reappearId")String reappearId);

    int batchInsert(List<CtCourseReappearStudent> list);
}