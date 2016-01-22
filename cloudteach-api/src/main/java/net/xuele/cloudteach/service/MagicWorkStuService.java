package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * MagicWorkStuService
 * 学生提分宝作业服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public interface MagicWorkStuService {

    /**
     * @param magicWorkSubmitFormDTO
     * @throws CloudteachException
     */
    void submitWork(MagicWorkSubmitFormDTO magicWorkSubmitFormDTO) throws CloudteachException;

    /**
     * 提交提分宝作业
     *
     * @param magicWorkAnswerDTO 学生作业回答dto
     * @param answerFilesDTOs    学生作业回答附件列表
     * @throws CloudteachException
     */
    void submitMagicWork(MagicWorkAnswerDTO magicWorkAnswerDTO, List<MagicWorkAnswerFilesDTO> answerFilesDTOs) throws CloudteachException;

    /**
     * 学生分享作业回答
     *
     * @param challengeId
     * @param schoolId
     * @throws CloudteachException
     */
    int shareWorkAnswer(String challengeId, String schoolId) throws CloudteachException;

    /**
     * @param workId
     * @param bankId
     * @param userId
     * @return
     */
    MagicQuestionViewDTO getMagicWorkItems(String workId, String bankId, String userId, String schoolId) throws CloudteachException;

    /**
     * 获取提分宝题目信息(包括习题讲解)
     *
     * @param workId
     * @param bankId
     * @param userId
     * @param schoolId
     * @param type     1--pc端，2--手機端，3--其他
     * @return
     * @throws CloudteachException
     */
    public MagicWorkQueDetailDTO queryMagicQueDetail(Integer orderNum,String workId, String bankId, String userId, String schoolId, Integer type) throws CloudteachException;

    /**
     * 完成作业并对完答案时新增挑战记录及相关表
     *
     * @param finishWorkFormDTO
     */
    MagicWorkChallengeDTO finishWork(MagicWorkStuFinishWorkFormDTO finishWorkFormDTO) throws CloudteachException;

    /**
     * 获取某个学生提分宝作业的完成信息
     * (包含：作业基本信息，题目详细，学生回答信息，学生提交的挑战记录信息，学生提交的附件信心，学生作业统计信息)
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    MagicWorkFinishDetailViewDTO queryMagicWorkDetail(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 查询学生提交作业后的回答信息报错题目对错
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    public MagicSubmitInfoDTO queryMagicSubmitInfo(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 查询挑战题目及回答信息
     *
     * @param challengeId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    public List<MagicWorkChallengeQueViewDTO> getChallengeAnsInfo(String bankId, Integer orderNum, String challengeId, String schoolId) throws CloudteachException;

    /**
     * @param studentWorkCommunicationPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<StudentWorkCommunicationViewDTO> magicWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) throws CloudteachException;

    /**
     * 获取全部评论
     *
     * @param answerId
     * @param schoolId
     * @return
     */
    List<WorkAnswerCommentViewDTO> getMoreComments(String answerId, String schoolId, int limitRange);


    /**
     * 提分宝挑战记录
     *
     * @param workId
     * @param studentId
     * @param bankId
     * @param entrance  查询入口 1,作业入口，2，应用中心入口
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    List<MagicWorkChallengeDTO> magicWorkChallengeRecord(String workId, String studentId, String bankId, int entrance, String schoolId) throws CloudteachException;

    /**
     * 学生删除自己的作业回答
     *
     * @param answerId
     * @return
     */
    void delWorkAnswer(String answerId);

    /**
     * 点赞
     *
     * @param answerId
     * @param userId
     * @return
     */
    void praise(String answerId, String userId, String schoolId, int userType);

    /**
     * 取消点赞
     *
     * @param answerId
     * @param userId
     * @return
     */
    void cancelPraise(String answerId, String userId, String schoolId, int userType);

    /**
     * 评论
     *
     * @param answerId
     * @param userId
     * @return
     */
    String comment(String answerId, String userId, String schoolId, String context, int userType);

    /**
     * 删除评论：只能是我删除其他人对我的作业的评论
     *
     * @param commentId
     * @param userId
     * @return
     */
    void delComment(String commentId, String userId, String schoolId);

    /**
     * 根据answerId获取学生作业回答信息
     *
     * @param answerId
     * @param schoolId
     * @return
     */
    MagicWorkAnswerDTO getMagicWorkAnswerByAnswerId(String answerId, String schoolId);

    /**
     * 获取某个用户对应某个学生作业的点赞状态信息
     *
     * @param answerId 提分宝作业回答ID
     * @param userId   点赞用户ID
     * @param schoolId 学校ID
     * @return
     */
    public MagicWorkAnswerPraiseDTO getMagicWorkStuPraiseStatusByUserId(String answerId, String userId, String schoolId);

    /**
     * 通过作业ID，学生ID，查找学生回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    MagicWorkAnswerDTO getStuAnswerByWorkStu(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 通过作业ID，学生ID，查找该作业基本信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    BasicWorkInfoViewDTO getBasicWorkInfo(String workId, String studentId, String schoolId) throws CloudteachException;
}