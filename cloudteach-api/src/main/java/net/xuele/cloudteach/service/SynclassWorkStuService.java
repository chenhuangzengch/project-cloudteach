package net.xuele.cloudteach.service;

import net.xuele.appCenter.dto.FlashAddressDTO;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * SynclassWorkStuService
 * 学生同步课堂作业服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public interface SynclassWorkStuService {

    /**
     * 通过作业ID，学生ID，查找学生回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    SynclassWorkStudentDTO getStuAnswerByWorkStu(String workId, String studentId, String schoolId) throws CloudteachException;


    /**
     * 通过作业ID，学生ID，查找该作业基本信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    BasicWorkInfoViewDTO getBasicWorkInfo(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 获取某个学生的同步课堂作业详细
     * (作业基本信息，教师录音信息，游戏信息，学生回答信息，学生玩游戏结果信息,学生作业统计信息)
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    public SynclassWorkFinishDetailViewDTO synclassWorkFinishDetail(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 同步课堂作业交流
     *
     * @param studentWorkCommunicationPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<StudentWorkCommunicationViewDTO> synclassWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) throws CloudteachException;

    /**
     * answerId下的所有评论(limit 100)
     *
     * @param workUserId
     * @param schoolId
     * @return
     */
    List<WorkAnswerCommentViewDTO> getMoreComments(String workUserId, String schoolId, int limitRange);


    /**
     * 提交同步课堂作业
     *
     * @param synclassWorkStudentDTO
     */
    void handInMySynclassWork(SynclassWorkStudentDTO synclassWorkStudentDTO) throws CloudteachException;

    /**
     * 同步课堂游戏结果
     */
    void synclassGamePlayResult(String playId, String schoolId, String user_id, int score);

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
     * 根据workUserId获取学生作业回答信息
     *
     * @param workUserId
     * @param schoolId
     * @return
     */
    SynclassWorkStudentDTO getSynclassWorkAnswerByAnswerId(String workUserId, String schoolId);

    /**
     * 获取某个用户对应某个学生作业的点赞状态信息
     *
     * @param workUserId 同步课堂作业学生表ID
     * @param userId     点赞用户ID
     * @param schoolId   学校ID
     * @return
     */
    public SynclassWorkAnswerPraiseDTO getSynclassWorkStuPraiseStatusByUserId(String workUserId, String userId, String schoolId);
}