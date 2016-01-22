package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtSynclassWorkClass;
import net.xuele.cloudteach.domain.CtSynclassWorkStudent;
import net.xuele.cloudteach.view.*;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkStudentMapper {

    int insert(CtSynclassWorkStudent record);

    CtSynclassWorkStudent selectByPrimaryKey(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

    CtSynclassWorkStudent selectSynclassWorkAnswerForUpdate(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

    /**
     * 获取作业下所有已提交未批改的学生回答信息
     */
    List<CtSynclassWorkStudent> selectSubedUnCorrectList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据作业ID获取未批改学生信息
     */
    List<CtSynclassWorkStudent> selectUnCorrectStudentList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 获取某个同步课堂作业下某个班级的学生数
     */
    int queryClassStuCount(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 根据作业ID获取学生信息
     *
     * @param workId
     * @return
     */
    List<CtSynclassWorkStudent> selectStudentListByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtSynclassWorkStudent record);

    int updateByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId, @Param("userId") String userId);

    int batchUpdateStatusByClassId(@Param("classList") List<CtSynclassWorkClass> classList, @Param("schoolId") String schoolId);

    int getStudentNumByClassIdList(@Param("classList") List<CtSynclassWorkClass> classList, @Param("schoolId") String schoolId);

    int getCountByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateSubStatusByWorkId(@Param("workId") String workId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    List<String> selectPrimarykeyByWorkIdAndClass(@Param("workId") String workId, @Param("classList") List<String> classList, @Param("schoolId") String schoolId);


    /**
     * 根据作业ID获取未提交学生信息
     */
    List<WorkUnSubStudentView> selectUnSubStudentList(@Param("workId") String workId, @Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 获取某个同步课堂作业对应所有学生提交信息
     */
    List<SynclassWorkAnswerView> querySubedWorkAnswerList(@Param("workId") String workId, @Param("classId") String classId,
                                                          @Param("teachUserId") String teachUserId, @Param("schoolId") String schoolId);

    /**
     * 通过作业ID，学生ID，得到该学生该作业的回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    CtSynclassWorkStudent getCtSynclassInfoByWorkStudent(@Param("workId") String workId, @Param("studentId") String studentId,
                                                         @Param("schoolId") String schoolId);

    /**
     * 获取该作业下，已经提交的学生数
     *
     * @param workId
     * @param schoolId
     * @return
     */
    long getSubmitStuRecCount(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<StudentWorkCommunicationView> getBasicSynclassWorkStudentAnswerInfo(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                                                             @Param("workId") String workId, @Param("studentId") String studentId,
                                                                             @Param("subTime") String subTime, @Param("firstReq") int firstReq,
                                                                             @Param("schoolId") String schoolId);

    List<CtSynclassWorkStudent> getInitInfo(@Param("classList") List<String> classList,
                                            @Param("schoolId") String schoolId, @Param("workId") String workId);

    int batchInsert(List<CtSynclassWorkStudent> list);

    int selectSubCount(@Param("workId") String workId, @Param("schoolId") String schoolId);
}