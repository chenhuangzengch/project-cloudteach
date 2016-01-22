package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.BlackboardPublishPageRequest;
import net.xuele.cloudteach.dto.page.CourseReappearPageRequest;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * CourseReappearService
 * 发布课件服务
 *
 * @author cm.wang
 * @date 2015/7/16 0023
 */
public interface CourseReappearService {

    /**
     * 发布课件
     *
     * @param courseReappearInfoDTO
     * @return
     */
    String addCourseReapper(CourseReappearInfoDTO courseReappearInfoDTO, WorkTapeFilesDTO workTapeFilesDTO,String userName,String userIcon);


    /**
     * 通过用户Id分页获得的课堂发布课件(非详细，即没有具体内容)
     *
     * @param courseReappearPageRequest
     * @return
     */
    PageResponse<CourseReappearDTO> getCourseReappearByUserId(CourseReappearPageRequest courseReappearPageRequest, String schoolId);


    /**
     * 通过课堂发布Id获得课件发布信息
     *
     * @param reappearId 课堂发布Id
     * @return
     */
    CourseReappearInfoDTO getCourseReappear(String reappearId, String schoolId);


    /**
     * 通过课件Id获得具体的内容(包含更改已读状态)
     *
     * @param rcId 课件Id
     * @return
     */
    TeachCoursewaresContentDTO getTeachCoursewaresContentByRcId(String userId, String rcId, String reappearId, String schoolId);

    /**
     * 学生查看课件更改已读状态
     *
     * @param userId     学生Id
     * @param rcId       课件发布课件Id
     * @param reappearId 课件发布Id
     * @param schoolId
     * @return
     */
    int checkCourseReappear(String userId, String rcId, String reappearId, String schoolId);

    /**
     * 老师编辑课件
     *
     * @param courseReappearInfoDTO
     * @return
     */
    int editCourseReappear(CourseReappearInfoDTO courseReappearInfoDTO);

    /**
     * 删除课件发布
     *
     * @param reappearId
     * @return
     */
    int delCourseReappear(String reappearId, String schoolId);


}
