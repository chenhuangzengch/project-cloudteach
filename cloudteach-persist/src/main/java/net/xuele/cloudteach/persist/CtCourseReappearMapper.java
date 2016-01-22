package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtCourseReappear;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

public interface CtCourseReappearMapper {
    int deleteByPrimaryKey(String reappearId);

    int insert(CtCourseReappear record);

    CtCourseReappear selectByPrimaryKey(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);

    List<CtCourseReappear> selectAll(String schoolId);

    int updateByPrimaryKey(CtCourseReappear record);

    long selectCount(@Param("userId")String userId,@Param("schoolId")String schoolId);

    List<CtCourseReappear> selectPage(@Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                                         @Param(value = "userId") String userId,@Param("schoolId")String schoolId);

    int updateByReappearId(@Param("reappearId")String reappearId,@Param("schoolId")String schoolId);
}