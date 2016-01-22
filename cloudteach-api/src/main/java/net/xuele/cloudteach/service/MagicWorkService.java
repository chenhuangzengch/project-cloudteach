package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * MagicWorkService
 * 教师提分宝作业服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public interface MagicWorkService {

    /**
     * 新增提分宝作业
     *
     * @param magicWorkFormDTO
     * @throws CloudteachException
     * @author panglx
     */
    String addMagicWork(MagicWorkFormDTO magicWorkFormDTO) throws CloudteachException;

    /**
     * 显示云作业题库列表
     *
     * @param unitId
     * @return
     * @throws CloudteachException
     */
    List<MagicWorkListDTO> showMagicWorkList(String unitId,String extraBookId) throws CloudteachException;

    /**
     * 查看题目详情
     *
     * @param queId
     * @return
     */
    MagicQuestionDTO viewDetailQue(String queId);

    /**
     * @param magicWorkDTO 提分宝作业
     * @param classlist    发布班级列表
     * @throws net.xuele.common.exceptions.CloudteachException 修改提分宝作业，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    //void modifyMagicWork(MagicWorkDTO magicWorkDTO, List<String> classlist) throws CloudteachException;

    /**
     * @param workId 作业ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID删除提分宝作业相关信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void delMagicWork(String workId, String schoolId) throws CloudteachException;

    /**
     * 删除指定学生提交作业
     *
     * @param userId
     * @param answerId
     * @param schoolId
     * @throws CloudteachException
     */
    void delStuAnswer(String userId, String answerId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return MagicWorkDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取提分宝作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    MagicWorkDTO queryMagicWork(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return List<CourseReappearClassDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取提分宝作业对应班级列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicWorkClassDTO> queryMagicWorkClassListByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId 作业ID
     *               return List<MagicWorkAnswerDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取提分宝作业对应学生回答列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicWorkAnswerDTO> queryMagicWorkAnswerListByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param answerId 提分宝作业回答ID
     *                 return MagicWorkAnswerDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据提分宝作业回答ID获取提分宝作业回答信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    MagicWorkAnswerDTO queryMagicWorkAnswer(String answerId, String schoolId) throws CloudteachException;

    /**
     * @param workId       作业Id
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 快速批改提分宝作业，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void quicklyCorrect(String workId, String context, Integer praiseStatus, String userId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取提分宝作业班级信息
     */
    List<WorkClassViewDTO> queryMagicWorkClassList(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param classId    班级ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取提分宝作业下未提交作业的学生信息
     */
    List<WorkUnSubStudentViewDTO> selectUnSubStudentList(String workId,String classId, String schoolId) throws CloudteachException;

    /**
     * @param workId      作业Id
     * @param classId     班级ID
     * @param teachUserId 作业布置教师ID
     * @param schoolId    学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 获取某个提分宝作业下某个班级的学生提交信息
     */
    List<WorkAnswerViewDTO> querySubedWorkAnswerList(String workId, String classId, String teachUserId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取提分宝作业信息
     */
    MagicWorkDetailViewDTO queryMagicWorkDetailByWorkId(String workId, String schoolId) throws CloudteachException;

    /**
     * @param workId   作业Id
     * @param classId    班级ID
     * @param schoolId 学校ID
     * @param userId 教师ID
     * @param userIcon 教师头像
     * @param userName 教师名字
     * @param workType 作业类型（1预习 2提分宝 3同步课堂 4电子作业 7口语作业）
     * @return 0提醒太频繁  1提醒成功
     * @throws net.xuele.common.exceptions.CloudteachException 根据提分宝作业ID发通知提醒所有未提交作业的学生
     */
    int warnSub(String workId, String classId,String schoolId,String userId,String userIcon,String userName,int workType) throws CloudteachException;

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
     * @param schoolId 学校ID
     *                 批改answerId对应的作业回答
     */
    void correct(String answerId, String schoolId) throws CloudteachException;

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
     * 查询提分宝袭习题讲解
     * @param qieId 题目id（原题id）
     * @param type 1--pc端，2--手機端，3--其他
     * @return
     * @throws CloudteachException
     */
    public MagicQuestionVideoDTO getMagicQueVideoByQueId(String qieId,Integer type)throws CloudteachException;

    /**
     * @param workId 作业ID
     * @param studentId 学生用户ID
     * @param schoolId 学校ID
     * 获取提分宝作业对应某个学生的回答信息
     */
    MagicWorkAnswerDTO getStuMagicWorkAnswer(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 获取提分宝作业对应某个班级学生的作业统计信息
     * @param schoolId 学校ID
     * @param classId   班级ID
     * @param workId   作业Id
     * @return
     */
    SRTeacherWorkDTO getMagicWorkSR(String schoolId, String classId, String workId);
}