package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswer;
import net.xuele.cloudteach.domain.CtTeacherWorkStudent;
import net.xuele.cloudteach.domain.CtWorkStudentGather;
import net.xuele.cloudteach.view.LearningInfoStudentView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtWorkStudentGatherMapper {

    int insert(CtWorkStudentGather record);

    CtWorkStudentGather selectByPrimaryKey(@Param("schoolId") String schoolId, @Param("workStudentId") String workStudentId);

    int updateByPrimaryKey(CtWorkStudentGather record);

    /**
     * 用户发布提分宝作业时 学生作业汇总表初始化
     *
     * @param answerList
     * @return
     */
    int initMagicWorkStudentGather(@Param("answerList") List<CtMagicWorkAnswer> answerList);

    /**
     * 删除提分宝作业时逻辑删除作业学生汇总表信息
     *
     * @param workId
     * @param schoolId
     * @return
     */
    int delWorkStudentGather(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 删除某个学生回答时更改作业学生汇总表信息：提交状态，批改状态改为0
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    int updateStuWorkStudentGather(@Param("workId") String workId, @Param("studentId") String studentId, @Param("schoolId") String schoolId);

    int initCtWorkStudentGather(@Param("initInfoList") List<CtTeacherWorkStudent> initInfoList);

    int updateStatusByClassList(@Param("status") int status, @Param("schoolId") String schoolId, @Param("classList") List<String> classList);

    int updateStatusByWorkId(@Param("status") int status, @Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateSubStatusByWorkIdAndUserId(@Param("studentId") String studentId, @Param("schoolId") String schoolId, @Param("workId") String workId);

    /**
     * 教师批改作业后将学生对应作业学生汇总表信息修改为已批改
     *
     * @param workId   作业ID
     * @param userId   学生ID
     * @param schoolId 学校ID
     * @return
     */
    int updateByTeacherCorrect(@Param("workId") String workId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 学生提交作业后更改提交状态为1-已提交
     *
     * @param workId
     * @param userId
     * @param schoolId
     * @return
     */
    int updateByStuSubmit(@Param("workId") String workId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    List<CtWorkStudentGather> getWorkStudentGatherByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    CtWorkStudentGather getWorkStudentGatherByWorkIdAndUserId(@Param("workId") String workId,
                                                              @Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 更改为初始状态
     */
    int updateInitByWorkIdAndUserId(@Param("workId") String workId,
                                    @Param("userId") String userId, @Param("schoolId") String schoolId);

    List<CtWorkStudentGather> getInitInfo(@Param("schoolId") String schoolId,
                                          @Param("classList") List<String> classList, @Param("workId") String workId);

    int batchInsert(List<CtWorkStudentGather> studentGatherList);

    /**
     * 获取某个作业下某个班级的学生总数
     *
     * @param workId
     * @param classId
     * @param schoolId
     * @return
     */
    int getWorkClassStudentNum(@Param("workId") String workId, @Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 获取某个作业已经提交的学生数
     *
     * @param workId
     * @param schoolId
     * @return
     */
    long getSubmitStuCount(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * @param workId
     * @param classId
     * @param schoolId
     * @return
     */
    List<CtWorkStudentGather> getStuGatherByWorkClass(@Param("workId") String workId, @Param("classId") String classId,
                                                      @Param("schoolId") String schoolId);

    /**
     * @param classId
     * @param subStatus
     * @param schoolId
     * @return
     */
    List<LearningInfoStudentView> selectStudentAnwerInfo(@Param("workId") String workId,@Param("classId") String classId,
                                                         @Param("subStatus") Integer subStatus, @Param("schoolId") String schoolId);
}