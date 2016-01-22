package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtCourseReappearClass;
import net.xuele.cloudteach.view.FileInfoView;
import org.apache.ibatis.annotations.Param;

public interface CtCourseReappearClassMapper {
    int deleteByPrimaryKey(String rcId);

    int insert(CtCourseReappearClass record);

    CtCourseReappearClass selectByPrimaryKey(@Param("rcId")String rcId,@Param("schoolId")String schoolId);

    List<CtCourseReappearClass> selectAll(String schoolId);

    List<CtCourseReappearClass> queryCourseReappearClassList(@Param("reappearId")String reappearId);

    int updateByPrimaryKey(CtCourseReappearClass record);

    int batchInsert(@Param("classIdList")List<String> classIdList,@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    int updateByReappearId(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    List<String> getClassList(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    int batchUpdateByClassList(@Param("list")List<String> list,@Param("schoolId")String schoolId);

    List<FileInfoView> getFileInfoViewList(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);
}