package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.view.TeacherCloudTeachView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TeacherCloudTeachMapper {
    List<TeacherCloudTeachView> queryTeacherCloudTeach(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                                       @Param("userId") String userId, @Param("classId") String classId,
                                                       @Param("publishTime") String publishTime,@Param("schoolId") String schoolId,
                                                       @Param("unitId") String unitId,@Param("subjectId") String subjectId,
                                                       @Param("onlyWork") int onlyWork);
}