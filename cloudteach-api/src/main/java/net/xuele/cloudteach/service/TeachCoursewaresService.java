package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.common.exceptions.CloudteachException;

import java.util.Date;
import java.util.List;

/**
 * TeachCoursewaresService
 * 授课课件服务
 *
 * @author duzg
 * @date 2015/7/13 0002
 */
public interface TeachCoursewaresService {

    /**
     * @param unitId 课程号
     * @return List<TeachCoursewaresDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据课程ID获取授课课件列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<TeachCoursewaresDTO> queryTeachCoursewaresListByUnitId(String unitId) throws CloudteachException;

    /**
     * 根据课程ID获取我的课件列表
     *
     * @param schoolId 学校ID
     * @param uintId   课程ID
     * @param userId   用户Id
     * @return
     * @throws CloudteachException
     */
    List<TeachCoursewaresDTO> queryMyTeachCoursewaresList(String schoolId, String uintId, String userId) throws CloudteachException;

    /**
     * 删除我的课件
     *
     * @param schoolId      学校ID
     * @param coursewaresId 课件ID
     * @param userId        用户ID
     * @throws CloudteachException
     */
    void removeTeachCourseware(String schoolId, String coursewaresId, String userId) throws CloudteachException;

    /**
     * 创建课件
     *
     * @param teachCoursewaresDTO        课件DTO
     * @param teachCoursewaresContentDTO 课件内容DTO
     * @return
     * @throws CloudteachException
     */
    String addTeachCoursewares(TeachCoursewaresDTO teachCoursewaresDTO, TeachCoursewaresContentDTO teachCoursewaresContentDTO) throws CloudteachException;

    /**
     * 编辑课件
     *
     * @param teachCoursewaresDTO        课件DTO
     * @param teachCoursewaresContentDTO 课件内容DTO
     * @return
     * @throws CloudteachException
     */
    int updateTeachCoursewares(TeachCoursewaresDTO teachCoursewaresDTO, TeachCoursewaresContentDTO teachCoursewaresContentDTO) throws CloudteachException;

    /**
     * 查询某个课件
     *
     * @param coursewaresId 课件ID
     * @param userId        用户ID
     * @param schoolId      学校ID
     * @return
     */
    CoursewaresResponseDTO queryMyTeachCourse(String coursewaresId, String userId, String schoolId);

    /**
     * 取消收藏课件
     *
     * @param schoolId      学校ID
     * @param coursewaresId 课件ID
     * @param userId        用户ID
     * @throws CloudteachException
     */
    void cancelCollectTeachCourseware(String schoolId, String coursewaresId, String userId) throws CloudteachException;

    /**
     * 分享课件
     *
     * @param userShareInfoDTO 分享相关的用户信息DTO
     * @throws CloudteachException
     */
    void shareCourseware(UserShareInfoDTO userShareInfoDTO) throws CloudteachException;

    /**
     * 置顶我的课件
     *
     * @param schoolID      学校ID
     * @param coursewaresId 课件ID
     * @param userID        用户ID
     * @throws CloudteachException
     */
    void stickyTeachCourseware(String schoolID, String coursewaresId, String userID) throws CloudteachException;

    /**
     * 取消置顶我的课件
     *
     * @param schoolID      学校ID
     * @param coursewaresId 课件ID
     * @param userID        用户ID
     * @throws CloudteachException
     */
    void cancelStickyTeachCourseware(String schoolID, String coursewaresId, String userID) throws CloudteachException;

    /**
     * 查询用户在任务时间内分享成功的课件数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 分享次数
     * @throws CloudteachException
     */
    Long getCoursewareSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;

    /**
     * 获取单个课件
     *
     * @param schoolId     学校ID
     * @param coursewareId 课件ID
     * @return TeachCoursewaresDTO
     * @throws CloudteachException
     */
    TeachCoursewaresDTO getCourseware(String schoolId, String coursewareId) throws CloudteachException;

    /**
     * 更新课件打包状态
     *
     * @param schoolId     学校ID
     * @param coursewareId 课件ID
     * @param packStatus   打包状态 :0未打包,1打包中,2打包成功,3打包失败
     * @param fileUri      zip包文件Key
     * @return int 受影响的行数
     * @throws CloudteachException
     */
    @Deprecated
    int updateCoursePackStatus(String schoolId, String coursewareId, Integer packStatus, String fileUri) throws CloudteachException;


    //===========================================课件打包接口 start============================================

    /**
     * 发起打包请求并返回当前打包状态
     *
     * @param coursewaresId 课件ID
     * @param schoolId      学校ID
     * @param userId
     * @return TeachCoursewarePackDTO
     * @throws CloudteachException
     */
    TeachCoursewarePackDTO pack(String coursewaresId, String schoolId, String userId) throws CloudteachException;

    /**
     * 查询课件打包状态
     *
     * @param coursewaresId 课件ID
     * @param schoolId      学校ID
     * @param userId
     * @return TeachCoursewarePackDTO
     * @throws CloudteachException
     */
    TeachCoursewarePackDTO getPackStatus(String coursewaresId, String schoolId, String userId) throws CloudteachException;

    /**
     * 更新课件打包状态到缓存和数据库
     *
     * @param packDTO 课件打包状态DTO
     * @return boolean 是否更新成功
     * @throws CloudteachException
     */
    boolean updatePackStatus(TeachCoursewarePackDTO packDTO) throws CloudteachException;

    //===========================================课件打包接口 end  ============================================

}