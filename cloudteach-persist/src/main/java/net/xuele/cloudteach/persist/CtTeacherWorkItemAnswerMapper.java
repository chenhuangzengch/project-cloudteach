package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkItem;
import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswer;
import net.xuele.cloudteach.view.StudentWorkCommunicationView;
import net.xuele.cloudteach.view.WorkAnswerView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkItemAnswerMapper {

    int insert(CtTeacherWorkItemAnswer record);

    CtTeacherWorkItemAnswer selectByPrimaryKey(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    CtTeacherWorkItemAnswer selectTeacherWorkAnswerForUpdate(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkItemAnswer record);

    List<CtTeacherWorkItemAnswer> getInitInfo(@Param("workId") String workId, @Param("workItemId") String workItemId,
                                              @Param("classList") List<String> classList, @Param("schoolId") String schoolId);

    /*List<CtTeacherWorkItemAnswer> getInitInfo(@Param("workId") String workId, @Param("classList") List<String> classList,
                                              @Param("schoolId") String schoolId);*/

    int initCtTeacherWorkItemAnswer(@Param("initInfoList") List<CtTeacherWorkItemAnswer> initInfoList);

    int deleteTeacherWorkItemAnswer(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int deleteStuWorkItemAnswer(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    String getAnswerIdByWorkId(@Param("workId") String workId, @Param("studentId") String studentId, @Param("schoolId") String schoolId);

    List<String> getAnswerListByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkItemAnswer> selectSubedUnCorrectList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    CtTeacherWorkItemAnswer getTeacherWorkItemAnswerByWorkStudentId(@Param("workId") String workId, @Param("studentId") String studentId,
                                                                    @Param("schoolId") String schoolId);

    /**
     * 获取某个教师作业中某个题目对应所有学生提交信息
     */
    List<WorkAnswerView> querySubedWorkAnswerList(@Param("workId") String workId, @Param("workItemId") String workItemId,
                                                  @Param("classId") String classId, @Param("schoolId") String schoolId,
                                                  @Param("teachUserId") String teachUserId);

    /**
     * 获取某个教师作业下某个班级中已提交作业的学生信息
     */
    List<WorkAnswerView> queryClassSubedStuList(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkItemAnswer> getTeacherWorkItemAnswerByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 获取该作业下，已经提交的学生数
     *
     * @param workId
     * @param schoolId
     * @return
     */
    long getSubmitStuRecCount(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<StudentWorkCommunicationView> getBasicTeacherWorkAnswerInfo(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                                                     @Param("workId") String workId, @Param("studentId") String studentId,
                                                                     @Param("subTime") String subTime, @Param("firstReq") int firstReq,
                                                                     @Param("schoolId") String schoolId);

}