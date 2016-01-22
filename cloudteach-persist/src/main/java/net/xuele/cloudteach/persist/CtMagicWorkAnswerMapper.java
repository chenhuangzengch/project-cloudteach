package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswer;
import net.xuele.cloudteach.view.StudentWorkCommunicationView;
import net.xuele.cloudteach.view.WorkAnswerView;
import net.xuele.cloudteach.view.WorkUnSubStudentView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkAnswerMapper {

    int insert(CtMagicWorkAnswer record);

    CtMagicWorkAnswer selectByPrimaryKey(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    CtMagicWorkAnswer selectMagicWorkAnswerForUpdate(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    /**
     * 获取作业下所有已提交未批改的学生回答信息
     */
    List<CtMagicWorkAnswer> selectSubedUnCorrectList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 获取某个提分宝作业下某个班级中已提交作业的学生信息
     */
    List<WorkAnswerView> queryClassSubedStuList(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 获取某个提分宝作业下某个班级的学生数
     */
    int queryClassStuCount(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 根据作业ID获取未批改学生信息
     */
    List<CtMagicWorkAnswer> selectUnCorrectStudentList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkAnswer record);

    /**
     * 根据作业ID获取未提交学生信息
     */
    List<WorkUnSubStudentView> selectUnSubStudentList(@Param("workId") String workId, @Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 获取某个提分宝作业中某个题目对应所有学生提交信息
     */
    List<WorkAnswerView> querySubedWorkAnswerList(@Param("workId") String workId, @Param("classId") String classId,
                                                  @Param("teachUserId") String teachUserId, @Param("schoolId") String schoolId);

    /**
     * 逻辑删除提分宝作业对应的回答表记录
     *
     * @param workId
     * @return
     */
    int deleteByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据提分宝作业id查询的回答表记录
     *
     * @param workId
     * @return
     */
    List<String> selectByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 用户发布提分宝作业时 提分宝作业回答表初始化
     *
     * @param answerList
     * @return
     * @author panglx
     */
    int initialize(@Param("answerList") List<CtMagicWorkAnswer> answerList);

    /**
     * 根据班级id，作业id提分宝作业学生回答表逻辑删除
     *
     * @param workId
     * @param classId
     * @return
     */
    int deleteByClassId(@Param("workId") String workId, @Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 查询班级对应的学生回答表id
     *
     * @param workId
     * @param classId
     * @return
     */
    List<String> selectanswerIdListByClassId(@Param("workId") String workId, @Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 根据提分宝作业id查询该作业发布的学生数
     *
     * @param workId
     * @return
     */
    int countByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据作业ID，学生ID，获得该学生对于该提分宝作业回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    CtMagicWorkAnswer getCtMagicWorkAnswerByWorkStudentId(@Param("workId") String workId, @Param("studentId") String studentId,
                                                          @Param("schoolId") String schoolId);

    List<StudentWorkCommunicationView> getBasicMagicWorkAnswerInfo(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                                                   @Param("workId") String workId, @Param("studentId") String studentId,
                                                                   @Param("subTime") String subTime, @Param("firstReq") int firstReq,
                                                                   @Param("schoolId") String schoolId);

    /**
     * 逻辑删除学生的回答记录
     *
     * @param record
     * @return
     */
    int delByPrimaryKey(CtMagicWorkAnswer record);

    /**
     * 获取该作业下，已经提交的学生数
     *
     * @param workId
     * @param schoolId
     * @return
     */
    long getSubmitStuRecCount(@Param("workId") String workId, @Param("schoolId") String schoolId);
}