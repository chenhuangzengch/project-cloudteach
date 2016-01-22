package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.ClassFbPackageDTO;
import net.xuele.cloudteach.dto.ClassFeedbackDTO;
import net.xuele.cloudteach.dto.ClassFeedbackViewDTO;
import net.xuele.cloudteach.dto.page.ClassFeedbackPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;
import net.xuele.member.dto.StudentManagerDTO;

import java.util.List;

/**
 * Created by duzg on 2015/10/14 0008.
 * 随堂反馈服务抽象
 */
public interface ClassFeedbackService {

    /**
     * 手机端获取用户对应的授课班级及授课课本名称、课程名称
     * @param userId
     * @return
     * @throws net.xuele.common.exceptions.CloudteachException
     */
	ClassFbPackageDTO getCFbClassList(String userId) throws CloudteachException;

    /**
     * 手机端获取班级对应的学生列表
     * @param classId
     * @return
     * @throws CloudteachException
     */
	List<StudentManagerDTO> getCFbStudentList(String classId) throws CloudteachException;

    /**
     * 手机端上传随堂反馈图片
     * 参数：studentId 学生id；unitId 课程id；classId 班级id；className 班级名称；uploadUserId 教师id；
     *       filekey HDFS文件uri；fileName 文件名；extension 扩展名；size 文件大小；
     * @param classFeedbackDTOs
     * @return
     * @throws CloudteachException
     */
	String uploadCFbFile(List<ClassFeedbackDTO> classFeedbackDTOs) throws CloudteachException;

    /**
     * 手机端上传随堂反馈随机点名选中的学生信息
     * @param userId 教师ID
     * @param studentIds 学生ID：多个学生以“:”分隔
     * @return 0设置失败  1设置成功
     * @throws CloudteachException
     */
    String setSelectedStusFB(String userId,String studentIds) throws CloudteachException;

    /**
     * 手机端取消随堂反馈随机点名操作
     * @param userId 教师ID
     * @return 0取消失败  1取消成功
     * @throws CloudteachException
     */
    String cancelSelectedStusFB(String userId) throws CloudteachException;

    /**
     * 分页查询随堂反馈图片
     * @param request
     * @return
     * @throws CloudteachException
     */
    PageResponse<ClassFeedbackViewDTO> queryPageList(ClassFeedbackPageRequest request) throws CloudteachException;

    /**
     * 删除随堂反馈资源
     * @param fbId
     * @param schoolId
     * @throws CloudteachException
     */
    void removeFile(String fbId,String userId,String schoolId) throws CloudteachException ;

    /**
     * 设置教师正在进行随堂反馈的课件ID
     * @param userId
     * @param coursewaresId
     * @return
     */
    boolean setClassFBCoursewaresId(String userId, String coursewaresId);

    /**
     * 获取教师正在进行随堂反馈的课件ID
     * @param userId
     * @return
     */
    String getClassFBCoursewaresId(String userId);

    /**
     * 设置教师正在授课的课件对应的班级
     * @param coursewaresId
     * @param classId
     * @return
     */
    boolean setClassFBClass(String coursewaresId, String classId);

    /**
     * 获取教师正在授课的课件对应的班级
     * @param coursewaresId
     * @return
     */
    String getClassFBClass(String coursewaresId);

    /**
     * 获取随堂反馈列表(获取课件ID对应当天上传的随堂反馈照片)
     * @param userId
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    List<ClassFeedbackDTO> getClassFBList(String userId,String schoolId,String coursewaresId);

    /**
     * 设置教师正在进行随堂反馈的课件是否有新照片
     * app上传照片后需要调用此接口将status修改为1
     * @param coursewaresId
     * @param status（0没有 1有）
     * @return
     */
    boolean setNewClassFBStatus(String coursewaresId,String status);

    /**
     * 获取教师正在进行随堂反馈的课件是否有新照片状态（0没有 1有）
     * @param coursewaresId
     * @return false没有  true有
     */
    boolean getNewClassFBStatus(String coursewaresId);

    /**
     * 设置随堂反馈下是否有随机点名选中的的学生
     * app随机点名后需要调用此接口将status修改为1
     * @param coursewaresId
     * @param status（0没有 1有 -1取消随机点名）
     * @return
     */
    boolean setSelectedStuFBStatus(String coursewaresId,String status);

    /**
     * 获取随堂反馈下是否有随机点名选中的的学生状态
     * @param coursewaresId
     * @return （0没有 1有 -1取消随机点名）
     */
    String getSelectedStuFBStatus(String coursewaresId);

    /**
     * 设置随堂反馈下是否有随机点名选中的的学生ID
     * app随机点名后需要调用此接口记录信息
     * @param coursewaresId
     * @param studentIds（学生ID：多个学生以“:”分隔）
     * @return
     */
    boolean setSelectedStuIdFB(String coursewaresId,String studentIds);

    /**
     * 获取随堂反馈下是否有随机点名选中的的学生ID（学生ID：多个学生以“:”分隔）
     * @param coursewaresId
     * @return
     */
    String getSelectedStuIdFB(String coursewaresId);
}
