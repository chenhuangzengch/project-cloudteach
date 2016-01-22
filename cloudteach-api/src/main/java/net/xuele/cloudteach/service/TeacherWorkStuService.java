package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * Created by hujx on 2015/7/31 0031.
 * 学生教师作业服务
 */
public interface TeacherWorkStuService {

    /**
     * 返回预习作业的作业交流列表
     *
     * @param studentWorkCommunicationPageRequest
     * @return
     * @throws CloudteachException
     */
    PageResponse<StudentWorkCommunicationViewDTO> teacherWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) throws CloudteachException;

    /**
     * 更新作业完成状态(学生提交作业后，需要调用这个接口)
     *
     * @param unSubmitStuAmount  未提交学生人数
     * @param workId             作业ID
     * @param schoolId           学校ID
     * @return
     */
    int updateWorkFinishStatus(int unSubmitStuAmount, String workId, String schoolId);

    /**
     * 提交预习，电子，语音作业
     *
     * @param teacherWorkItemAnswerDTO
     * @param teacherWorkItemAnswerFileDTOList
     */
    void handInMyTeacherWork(TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO, List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList) throws CloudteachException;

    /**
     * 获取教师作业中某个学生提交的作业信息
     * (包含题目基本信息，题目附件信息，教师录音信息，学生回答信息，学生提交附件信息，学生回答统计信息)
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    TeacherWorkFinishDetailViewDTO teacherWorkFinishDetail(String workId, String studentId, String schoolId) throws CloudteachException;

    /**
     * 获取全部评论
     *
     * @param answerId
     * @param schoolId
     * @return
     */
    List<WorkAnswerCommentViewDTO> getMoreComments(String answerId, String schoolId, int limitRange);

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
    TeacherWorkItemAnswerDTO getTeacherWorkItemAnswerByAnswerId(String answerId, String schoolId);

    /**
     * 获取某个用户对应某个学生作业的点赞状态信息
     *
     * @param answerId 提分宝作业回答ID
     * @param userId   点赞用户ID
     * @param schoolId 学校ID
     * @return
     */
    TeacherWorkItemAnswerPraiseDTO getTeacherWorkStuPraiseStatusByUserId(String answerId, String userId, String schoolId);

    /**
     * 通过作业ID，学生ID，查找学生回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    TeacherWorkItemAnswerDTO getStuAnswerByWorkStu(String workId, String studentId, String schoolId) throws CloudteachException;

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
