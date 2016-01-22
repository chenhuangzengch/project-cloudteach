package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWork;
import net.xuele.cloudteach.view.TeacherWorkDetailView;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkMapper {

    int insert(CtTeacherWork record);

    CtTeacherWork selectByPrimaryKey(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWork record);

    int deleteTeacherWork(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateWorkFinishStatus(@Param("finishStatus") int finishStatus, @Param("workId") String workId, @Param("schoolId") String schoolId);

    TeacherWorkDetailView queryTeacherWorkDetail(@Param("workId") String workId, @Param("schoolId") String schoolId);
}