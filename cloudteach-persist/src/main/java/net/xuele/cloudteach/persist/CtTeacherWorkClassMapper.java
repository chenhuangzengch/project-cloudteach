package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkClass;
import net.xuele.cloudteach.view.ClassIdNameView;
import net.xuele.cloudteach.view.WorkClassView;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkClassMapper {

    int insert(CtTeacherWorkClass record);

    CtTeacherWorkClass selectByPrimaryKey(@Param("workClassId") String workClassId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkClass record);

    List<CtTeacherWorkClass> getInitInfo(@Param("workId") String workId, @Param("classList") List<String> classList, @Param("schoolId") String schoolId);

    int initCtTeacherWorkClass(@Param("initInfoList") List<CtTeacherWorkClass> initInfoList);

    //ClassIdNameView selectClassIdName(@Param("classId") String classId, @Param("schoolId") String schoolId);

    int deleteTeacherWorkClass(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<WorkClassView> queryTeacherWorkClassList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkClass> getTeacherWorkClassByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);
}