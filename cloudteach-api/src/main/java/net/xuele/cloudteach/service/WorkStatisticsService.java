package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.SubjectGatherViewDTO;
import net.xuele.cloudteach.dto.WorkStatisticsDTO;
import net.xuele.cloudteach.dto.WorkStatisticsViewDTO;
import net.xuele.cloudteach.dto.WorkStudentGatherDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * WorkStatisticsService
 * 作业汇总统计服务
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
public interface WorkStatisticsService {
    /**
     * @param workId 作业号
     * @return WorkStatisticsDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业号获取作业统计信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    WorkStatisticsDTO queryWorkStatisticsByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workStatisticsDTO 作业统计信息
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 修改作业统计信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int updateWorkStatistics(WorkStatisticsDTO workStatisticsDTO) throws CloudteachException;

    /**
     * 新增作业统计信息
     *
     * @param workStatisticsDTO 作业统计信息
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException
     */
    int addWorkStatistics(WorkStatisticsDTO workStatisticsDTO) throws CloudteachException;

    /**
     * 修改作业统计信息中的已批改学生数
     *
     * @param workId              作业ID
     * @param schoolId            学校ID
     * @param unCorrectStudentNum 未批改学生数
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException
     */
    void updateCorrectStudentNum(String workId, String schoolId, int unCorrectStudentNum) throws CloudteachException;

    /**
     * 更新已提交学生数（用于单个学生提交作业后）
     *
     * @param workId
     * @param schoolId
     * @return 最新已提交学生数
     * @throws CloudteachException
     */
    int updateViewStudentNum(String workId, String schoolId) throws CloudteachException;

    /**
     * 更新已提交学生数（用于单个学生提交作业后）
     *
     * @param workId
     * @param schoolId
     * @return 最新已提交学生数
     * @throws CloudteachException
     */
    int updateSubmitStudentNum(String workId, String schoolId) throws CloudteachException;

    /**
     * 更新已提交学生数与已批改学生数（用于教师删除单个学生作业后）
     *
     * @param workStudentGatherDTO 被删除学生作业汇总信息
     * @throws CloudteachException
     */
    void updateByDelStuWork(WorkStudentGatherDTO workStudentGatherDTO) throws CloudteachException;

    /**
     * 获取教师已布置作业的所有科目类型
     *
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @return
     */
    List<SubjectGatherViewDTO> queryTeacherWorkSubjectList(String userId, String schoolId) throws CloudteachException;

    /**
     * 获取班级对应已布置作业的所有科目类型
     *
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @return
     */
    List<SubjectGatherViewDTO> queryClassWorkSubjectList(String classId, String schoolId) throws CloudteachException;

    /**
     * 获取学生对应已布置作业的所有科目类型
     *
     * @param studentId 学生用户ID
     * @param schoolId  学校ID
     * @return
     */
    List<SubjectGatherViewDTO> queryStudentWorkSubjectList(String studentId, String schoolId) throws CloudteachException;

    /**
     * 获取某个作业提交学生、批改学生数据统计信息
     *
     * @param workId
     * @param schoolId
     * @return
     */
    public WorkStatisticsViewDTO selectWorkStatistics(String workId, String schoolId);

    /**
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     * 获取某个作业下某个班级的学生总数
     */
    public int getWorkClassStudentNum(String workId, String classId, String schoolId);

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @return 0提醒太频繁  1操作成功
     * 记录教师对某个作业最后一次发送提醒通知的时间
     */
    public int updateLastWarnTime(String workId, String classId, String schoolId);

    /**
     * 校验该作业是否是学生自己的
     *
     * @param studentId
     * @param workId
     * @param schoolId
     * @return
     */
    boolean checkWorkOwnerStu(String studentId, String workId, String schoolId);

    /**
     * 校验该作业是不是老师布置的
     *
     * @param teacherId
     * @param workId
     * @param schoolId
     * @return
     */
    boolean checkWorkOwnerTch(String teacherId, String workId, String schoolId);

    /**
     * 通过workId，查出该作业对应的所有班级，已班级ID为key，班级名称为value存入map，供其他方法调用
     *
     * @param workId
     * @return
     */
    Map<String, String> classIdInfoMap(String workId, String schoolId);

    /**
     * 通过workId，查出该作业对应的所有班级，已学生ID为key，班级名称为value存入map，供其他方法调用
     *
     * @param workId
     * @return
     */
    Map<String, String> classUserInfoMap(String workId, String schoolId);

    /**
     * 判断作业是否过期
     *
     * @param workId
     * @param date
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    boolean overdue(String workId, Date date, String schoolId) throws CloudteachException;

    /**
     * 判断学生是否允许进入作业交流
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    boolean allowToWorkCommunication(String workId, String studentId, Date date, String schoolId) throws CloudteachException;

    /**
     * 获取某个作业已经提交的学生数
     *
     * @param workId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    long submitStuCount(String workId, String schoolId) throws CloudteachException;

    /**
     * 通过作业ID，班级ID，获取学生作业完成信息
     *
     * @param workId
     * @param classId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    List<WorkStudentGatherDTO> queryStuFinishedState(String workId, String classId, String schoolId) throws CloudteachException;

    /**
     * 学生进入作业详情页的时候，对查看状态查看时间进行初始化
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    void initStudentGatherStuView(String workId, String studentId, String schoolId) throws CloudteachException;

}