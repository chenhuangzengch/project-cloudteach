package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * 教师作业布置服务
 *
 * @author hujx
 * @date on 2015/7/25 0025.
 */

public interface TeacherWorkService {

    /**
     * 教师布置作业，根据题目类型，布置不同的作业类型
     *
     * @param teacherWorkAddViewDTO
     * @param workTapeFilesDTO      教师作业录音
     * @param teacherWorkItemDTO    针对移动端特殊情况下布置作业
     * @throws CloudteachException
     */
    String addHomework(TeacherWorkAddViewDTO teacherWorkAddViewDTO, WorkTapeFilesDTO workTapeFilesDTO, TeacherWorkItemDTO teacherWorkItemDTO) throws CloudteachException;


    /**
     * @param workId 作业Id
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 逻辑删除workId对应的作业相关信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int delHomework(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId       作业Id
     * @param context      评语
     * @param praiseStatus 鼓励状态
     * @param score        评分
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 快速批改教师作业，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void quicklyCorrect(String workId, String context, Integer praiseStatus, Integer score, String userId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取预习作业班级信息
     */
    List<WorkClassViewDTO> queryTeacherWorkClassList(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取教师作业题目信息
     */
    List<TeacherWorkItemDTO> queryTeacherWorkItemList(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取预习作业下未提交作业的学生信息
     */
    List<WorkUnSubStudentViewDTO> selectUnSubStudentList(String workId, String classId, String schoolId) throws CloudteachException;

    /**
     * @param workId      作业Id
     * @param workItemId  题目ID
     * @param classId     班级ID
     * @param schoolId    学校ID
     * @param teachUserId 作业布置教师ID
     * @throws net.xuele.common.exceptions.CloudteachException 获取某个教师作业中某个题目下某个班级的学生提交信息
     */
    List<WorkAnswerViewDTO> querySubedWorkAnswerList(String workId, String workItemId, String classId, String schoolId, String teachUserId) throws CloudteachException;


    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取教师作业信息
     */
    TeacherWorkDetailViewDTO queryTeacherWorkDetailByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取教师作业题目信息(包括题目下的附件信息)
     */
    List<TeacherWorkItemViewDTO> queryItemContainFilesList(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param userIcon 教师头像
     * @param userName 教师名字
     * @param workType 作业类型（1预习 2提分宝 3同步课堂 4电子作业 7口语作业）
     * @return 0提醒太频繁  1提醒成功
     * @throws net.xuele.common.exceptions.CloudteachException 根据教师作业ID发通知提醒所有未提交作业的学生
     */
    int warnSub(String workId, String classId, String schoolId, String userId, String userIcon, String userName, int workType) throws CloudteachException;

    /**
     * @param answerId 学生回答ID
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 点赞answerId对应的作业回答
     */
    void praise(String answerId, String userId, String schoolId, int userType) throws CloudteachException;

    /**
     * @param answerId 学生回答ID
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 取消点赞answerId对应的作业回答
     */
    void unpraise(String answerId, String userId, String schoolId, int userType) throws CloudteachException;

    /**
     * @param answerId 学生回答ID
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @param context  评论内容
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 评论answerId对应的作业回答
     */
    String comment(String answerId, String userId, String schoolId, String context, int userType) throws CloudteachException;

    /**
     * @param answerId 学生回答ID
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @param score    评分
     * @throws net.xuele.common.exceptions.CloudteachException 评分answerId对应的作业回答
     */
    void score(String answerId, String userId, String schoolId, Integer score) throws CloudteachException;

    /**
     * @param answerId 学生回答ID
     * @param userId   教师ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 删除单个学生提交的作业
     */
    void delStuWork(String answerId, String userId, String schoolId) throws CloudteachException;

    /**
     * @param answerId 学生回答ID
     * @param schoolId 学校ID
     * @param score    评分
     *                 批改answerId对应的作业回答
     */
    void correct(String answerId, String schoolId, Integer score) throws CloudteachException;

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 学生作业批改后修改作业批改状态、作业统计信息已批改学生数
     */
    void updateWorkGatherAfterCorrect(String workId, String schoolId) throws CloudteachException;

    /**
     * @param commentId 评论ID
     * @param userId    教师用户ID
     * @param schoolId  学校ID
     *                  教师删除自己布置作业学生回答下任何人的评论
     */
    void delCommont(String commentId, String userId, String schoolId) throws CloudteachException;

    /**
     * @param commentId 评论ID
     * @param userId    学生用户ID
     * @param schoolId  学校ID
     *                  学生删除自己的评论
     */
    void delStuCommont(String commentId, String userId, String schoolId) throws CloudteachException;

    /**
     * @param teacherWorkAddViewDTO 发布的作业信息
     * @param bankItemCreateDTO     发布的题目信息
     * @param diskIdList            题目中引用的云盘资源ID
     * @param fileList              题目中引用的本地文件信息
     * @return 0操作失败 1操作成功
     * 保存并发布教师题目（该接口用于移动端发布电子作业、口语作业）
     */
    int saveAndPublishItem(TeacherWorkAddViewDTO teacherWorkAddViewDTO, BankItemCreateDTO bankItemCreateDTO,
                           List<String> diskIdList, List<BankItemAppFilesDTO> fileList,
                           WorkTapeFilesDTO workTapeFilesDTO) throws CloudteachException;

    /**
     * 教师保存并发布课外作业
     *
     * @param extraWorkDTO  发布课外作业信息
     * @param itemCreateDTO 发布课外题目信息
     * @param filesList     本地上传的文件信息
     * @param tapeFilesDTO  附带教师录音信息
     * @return
     * @throws CloudteachException
     */
    int saveAndPubExtraWork(TeacherWorkAddViewDTO extraWorkDTO, BankItemCreateDTO itemCreateDTO,
                            List<BankItemAppFilesDTO> filesList, WorkTapeFilesDTO tapeFilesDTO) throws CloudteachException;

    /**
     * 教师编辑保存并发布课外作业
     *
     * @param extraWorkDTO 发布课外作业信息
     * @param itemEditDTO  发布课外题目信息
     * @param tapeFilesDTO 附带教师录音信息
     * @param userId       用户ID
     * @param schoolId     学校ID
     * @return
     * @throws CloudteachException
     */
    int editAndPubExtraWork(TeacherWorkAddViewDTO extraWorkDTO, BankItemEditDTO itemEditDTO,
                            WorkTapeFilesDTO tapeFilesDTO, String userId, String schoolId) throws CloudteachException;

    /**
     * 获取某个教师某个课程对应某种教师作业（预习、电子、口语）信息,用于授课课件接口
     *
     * @param schoolId 学校ID
     * @param unitId   课程ID
     * @param userId   教师ID
     * @param workType 作业类型（1预习 4电子作业 7口语作业）
     * @return
     */
    List<CoursewareTeacherWorkViewDTO> queryCoursewareTeacherWorkList(String schoolId, String unitId, String userId, Integer workType);

    /**
     * 获取教师作业对应某个学生的回答信息
     *
     * @param schoolId  学校ID
     * @param studentId 学生ID
     * @param workId    作业Id
     * @return
     */
    TeacherWorkItemAnswerDTO getStuTeacherWorkAnswer(String schoolId, String studentId, String workId);

    /**
     * 获取教师作业对应某个班级学生的作业统计信息
     *
     * @param schoolId 学校ID
     * @param classId  班级ID
     * @param workId   作业Id
     * @param workType 作业类型
     * @return
     */
    SRTeacherWorkDTO getTeacherWorkSR(String schoolId, String classId, String workId, int workType);
}
