package net.xuele.cloudteach.service;

import net.xuele.appCenter.dto.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * SynclassWorkService
 * 教师同步课堂作业服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public interface SynclassWorkService {

    /**
     * @param synclassWorkDTO 同步课堂作业
     * @param classlist       发布班级列表
     * @param gamelist        同步课堂作业附件列表
     * @throws net.xuele.common.exceptions.CloudteachException 新增同步课堂作业，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    String addSynclassWork(SynclassWorkDTO synclassWorkDTO, List<SynclassWorkClassDTO> classlist,
                         List<SynclassWorkGameDTO> gamelist, String classJson, WorkTapeFilesDTO workTapeFilesDTO) throws CloudteachException;

    /**
     * @param synclassWorkDTO 同步课堂作业
     * @param classlist       发布班级列表
     * @param gamelist        同步课堂作业附件列表
     * @throws net.xuele.common.exceptions.CloudteachException 修改同步课堂作业，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void modifySynclassWork(SynclassWorkDTO synclassWorkDTO, List<SynclassWorkClassDTO> classlist, List<SynclassWorkGameDTO> gamelist, String classJson) throws CloudteachException;

    /**
     * @param workId 作业ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID删除同步课堂作业相关信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void delSynclassWork(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return SynclassWorkDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取同步课堂作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    SynclassWorkDTO querySynclassWork(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return List<SynclassWorkGameDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取同步课堂作业对应游戏列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<SynclassWorkGameDTO> querySynclassWorkGameListByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return List<SynclassWorkClassDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取同步课堂作业对应班级列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<SynclassWorkClassDTO> querySynclassWorkClassListByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return List<SynclassWorkStudentDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取同步课堂作业对应学生列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<SynclassWorkStudentDTO> querySynclassWorkStudentListByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workUserId 同步课堂作业学生ID
     *                   return SynclassWorkStudentDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据同步课堂作业学生ID获取同步课堂作业学生信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    SynclassWorkStudentDTO querySynclassWorkAnswer(String workUserId, String schoolId) throws CloudteachException;

    /**
     * @param workId       作业Id
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 快速批改同步课堂作业，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void quicklyCorrect(String workId, String context, Integer praiseStatus, String userId, String schoolId) throws CloudteachException;

    /**
     * 修改学生练习表
     *
     * @param playDTO
     * @return
     */
    void modifySynclassWorkPlay(SynclassWorkPlayDTO playDTO) throws CloudteachException;

    /**
     * 编辑点赞记录表(没有的话，添加；有的话，修改)
     *
     * @param praiseDTO
     * @throws CloudteachException
     */
    void editSynclassWorkAnswerPraise(SynclassWorkAnswerPraiseDTO praiseDTO) throws CloudteachException;

    /**
     * 新增评论记录表
     *
     * @param commentDTO
     * @throws CloudteachException
     */
    void addSynclassWorkAnswerComment(SynclassWorkAnswerCommentDTO commentDTO) throws CloudteachException;

    /**
     * 删除评论记录表
     *
     * @param commentDTO
     */
    void delSynclassWorkAnswerComment(SynclassWorkAnswerCommentDTO commentDTO);

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取同步课堂作业班级信息
     */
    List<WorkClassViewDTO> querySynclassWorkClassList(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param classId    班级ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取同步课堂作业下未提交作业的学生信息
     */
    List<WorkUnSubStudentViewDTO> selectUnSubStudentList(String workId,String classId, String schoolId) throws CloudteachException;

    /**
     * @param workId      作业Id
     * @param classId     班级ID
     * @param teachUserId 作业布置教师ID
     * @param schoolId    学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 获取某个同步课堂作业下某个班级的学生提交信息
     */
    List<SynclassWorkAnswerViewDTO> querySubedWorkAnswerList(String workId, String classId, String teachUserId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业Id
     * @param classId     班级ID
     * @param schoolId    学校ID
     * @param userId 教师ID
     * @param userIcon 教师头像
     * @param userName 教师名字
     * @param workType 作业类型（1预习 2提分宝 3同步课堂 4电子作业 7口语作业）
     * @return 0提醒太频繁  1提醒成功
     * @throws net.xuele.common.exceptions.CloudteachException 根据同步课堂作业ID发通知提醒所有未提交作业的学生
     */
    int warnSub(String workId,String classId, String schoolId,String userId,String userIcon,String userName,int workType) throws CloudteachException;

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param userId     教师用户ID
     * @param schoolId   学校ID
     * @param userType   用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 点赞answerId对应的作业回答
     */
    void praise(String workUserId, String userId, String schoolId, int userType) throws CloudteachException;

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param userId     教师用户ID
     * @param schoolId   学校ID
     * @param userType   用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 取消点赞answerId对应的作业回答
     */
    void unpraise(String workUserId, String userId, String schoolId, int userType) throws CloudteachException;

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param userId     用户ID
     * @param schoolId   学校ID
     * @param context    评论内容
     * @param userType   用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 评论answerId对应的作业回答
     */
    String comment(String workUserId, String userId, String schoolId, String context, int userType) throws CloudteachException;

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param schoolId   学校ID
     *                   批改answerId对应的作业回答
     */
    void correct(String workUserId, String schoolId) throws CloudteachException;

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
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取同步课堂作业信息
     */
    SynclassWorkDetailViewDTO querySynclassWorkDetailByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据同步课堂作业ID获取对应的游戏附件信息
     */
    List<SynclassWorkGameViewDTO> querySynclassWorkGameList(String workId, String schoolId) throws CloudteachException;

    /**
     * 删除学生提交的作业
     *
     * @param userId   教师ID
     * @param workUserId 学生回答ID
     * @param schoolId
     * @throws CloudteachException
     */
    void delSynclassStuWork(String userId, String workUserId, String schoolId) throws CloudteachException;

    /**
     * 根据课程获得同步课堂课件
     *
     * @param unitCode
     * @return
     */
    List<ViewCourseWaresDTO> queryCourseWare(String unitCode);

    /**
     * 玩游戏附件
     * uType 用户类型(1：老师 2：学生)
     *
     * @return
     */
    FlashAddressDTO getSynClassFlashVars(SynClassFlashInDTO synClassFlashInDTO);

    List<DUnitsDTO> getSyncUnit(String bookId, String schoolId);

    /**
     * @param workId 作业Id
     * @param studentId 学生ID
     * @param schoolId     学校ID
     * 获取同步课堂作业对应某个学生的回答信息
     */
    SynclassWorkStudentDTO getStuSynclassWorkAnswer(String workId, String studentId,String schoolId) throws CloudteachException;

    /**
     * 获取同步课堂作业对应某个班级学生的作业统计信息
     * @param schoolId 学校ID
     * @param classId   班级ID
     * @param workId   作业Id
     * @param workGameId 游戏附件ID
     * @return
     */
    SRTeacherWorkDTO getSynclassWorkSR(String schoolId, String classId, String workId,String workGameId);
}