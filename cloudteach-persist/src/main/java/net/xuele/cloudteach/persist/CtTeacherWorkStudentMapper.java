package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkStudent;
import net.xuele.cloudteach.view.WorkUnSubStudentView;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkStudentMapper {

    int insert(CtTeacherWorkStudent record);

    CtTeacherWorkStudent selectByPrimaryKey(String workUserId);

    int updateByPrimaryKey(CtTeacherWorkStudent record);

    List<CtTeacherWorkStudent> getInitInfo(@Param("workId") String workId, @Param("classList") List<String> classList,
                                           @Param("schoolId") String schoolId);

    int initCtTeacherWorkStudent(@Param("initInfoList") List<CtTeacherWorkStudent> initInfoList);

    int studentCountByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int deleteTeacherWorkStudent(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int deleteStudentWork(@Param("workId") String workId, @Param("schoolId") String schoolId, @Param("userId") String userId);

    /**
     * 将已提交未批改学生作业修改为已批改
     */
    int updateByTeacherCorrect(@Param("workId") String workId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 根据作业ID获取未批改学生信息
     */
    List<CtTeacherWorkStudent> selectUnCorrectStudentList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据作业ID获取未提交未批改学生信息
     */
    List<WorkUnSubStudentView> selectUnSubStudentList(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkStudent> getTeacherWorkStudentByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    CtTeacherWorkStudent getTeacherWorkStudentByStudentId(@Param("workId") String workId, @Param("studentId") String studentId,
                                                          @Param("schoolId") String schoolId);

    /**
     * 获取某个教师作业下某个班级的学生数
     */
    int queryClassStuCount(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

}

