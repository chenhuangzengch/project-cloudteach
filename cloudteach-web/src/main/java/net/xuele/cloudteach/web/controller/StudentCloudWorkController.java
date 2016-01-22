package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkPageRequest;
import net.xuele.cloudteach.service.StudentCloudWorkService;
import net.xuele.cloudteach.service.TeacherWorkStuService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.StudentPrepListWrapper;
import net.xuele.cloudteach.web.wrapper.StudentToDoListWrapper;
import net.xuele.cloudteach.web.wrapper.StudentWorkListWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;
import net.xuele.member.constant.IdentityIdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentWorkController
 * 学生作业管理
 *
 * @author hujx
 * @date 2015/7/10 0002
 */
@Controller
@RequestMapping(value = "workmanage/student")
public class StudentCloudWorkController {

    private static Logger logger = LoggerFactory.getLogger(StudentCloudWorkController.class);

    @Autowired
    StudentCloudWorkService studentCloudWorkService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    WorkStatisticsService workStatisticsService;

    /**
     * 返回待完成页面
     *
     * @param map
     * @param studentWorkPageRequest
     * @return
     */
    @RequestMapping(value = "initTodoList")
    public String todo(ModelMap map, StudentWorkPageRequest studentWorkPageRequest) {

        String identityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String studentId = studentWorkPageRequest.getStudentId();

        if (studentId == null) {
            studentId = GetSessionContentUtil.getInstance().currentUserID();
            if (identityId.equals(IdentityIdConstants.STUDENT)) {
                studentWorkPageRequest.setStudentId(studentId);
            }
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        studentWorkPageRequest.setSchoolId(schoolId);

        // 获取学生作业的科目信息
        List<SubjectGatherViewDTO> studentWorkSubjectList = workStatisticsService.queryStudentWorkSubjectList(studentId, schoolId);
        logger.info("[获取作业科目信息：" + studentWorkSubjectList + "]");
        
        map.put("studentWorkSubjectList", studentWorkSubjectList);

        return "workmanage/student/myTodoList";
    }

    /**
     * 返回预习页面
     *
     * @param map
     * @param studentWorkPageRequest
     * @return
     */
    @RequestMapping(value = "initPrepList")
    public String prep(ModelMap map, StudentWorkPageRequest studentWorkPageRequest) {

        String identityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String studentId = studentWorkPageRequest.getStudentId();

        if (studentId == null) {
            studentId = GetSessionContentUtil.getInstance().currentUserID();
            if (identityId.equals(IdentityIdConstants.STUDENT)) {
                studentWorkPageRequest.setStudentId(studentId);
            }
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        studentWorkPageRequest.setSchoolId(schoolId);

        // 获取学生作业的科目信息
        List<SubjectGatherViewDTO> studentWorkSubjectList = workStatisticsService.queryStudentWorkSubjectList(studentId, schoolId);
        logger.info("[获取作业科目信息：" + studentWorkSubjectList + "]");

        map.put("studentWorkSubjectList", studentWorkSubjectList);

        return "workmanage/student/myPrepList";
    }

    /**
     * 返回作业页面
     *
     * @param map
     * @param studentWorkPageRequest
     * @return
     */
    @RequestMapping(value = "initWorkList")
    public String work(ModelMap map, StudentWorkPageRequest studentWorkPageRequest) {

        String identityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String studentId = studentWorkPageRequest.getStudentId();

        if (studentId == null) {
            studentId = GetSessionContentUtil.getInstance().currentUserID();
            if (identityId.equals(IdentityIdConstants.STUDENT)) {
                studentWorkPageRequest.setStudentId(studentId);
            }
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        studentWorkPageRequest.setSchoolId(schoolId);

        // 获取学生作业的科目信息
        List<SubjectGatherViewDTO> studentWorkSubjectList = workStatisticsService.queryStudentWorkSubjectList(studentId, schoolId);
        logger.info("[获取作业科目信息：" + studentWorkSubjectList + "]");

        map.put("studentWorkSubjectList", studentWorkSubjectList);

        return "workmanage/student/myWorkList";
    }

    /**
     * @return 待完成列表
     * 分页获取待完成作业列表
     * @author hujx
     * @date 2015/07/10
     */
    @RequestMapping(value = "todoList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentToDoListWrapper>> queryMyToDoList(StudentWorkPageRequest studentWorkPageRequest) {

        String identityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String studentId = studentWorkPageRequest.getStudentId();

        if (studentId == null) {
            studentId = GetSessionContentUtil.getInstance().currentUserID();
            if (identityId.equals(IdentityIdConstants.STUDENT)) {
                studentWorkPageRequest.setStudentId(studentId);
            }
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        studentWorkPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkPageRequest.getPublishTime())) {
            studentWorkPageRequest.setPublishTime("0");
        }
        AjaxResponse<PageResponse<StudentToDoListWrapper>> studentMyWorkListWrapperAjaxResponse = new AjaxResponse<>();
        logger.info("[获取待完成列表：]");
        try {

            PageResponse<StudentWorkListViewDTO> studentWorkListViewDTOPageResponse = studentCloudWorkService.queryToDoWorkList(studentWorkPageRequest);
            PageResponse<StudentToDoListWrapper> studentMyWorkListWrapperPageResponse = new PageResponse<>();

            List<StudentToDoListWrapper> studentToDoListWrapperList = new ArrayList<>();

            // 转换Wrapper
            for (StudentWorkListViewDTO studentWorkListViewDTO : studentWorkListViewDTOPageResponse.getRows()) {
                StudentToDoListWrapper studentToDoListWrapper = new StudentToDoListWrapper();
                BeanUtils.copyProperties(studentWorkListViewDTO, studentToDoListWrapper);
                // end_time - publish_time 得到时间差(毫秒)
                long range = Math.abs(studentWorkListViewDTO.getEndTime().getTime() - studentWorkListViewDTO.getPublishTime().getTime()) / (24 * 60 * 60 * 1000);
                studentToDoListWrapper.setLastCommitDays(range);
                studentToDoListWrapperList.add(studentToDoListWrapper);
            }
            BeanUtils.copyProperties(studentWorkListViewDTOPageResponse, studentMyWorkListWrapperPageResponse);
            studentMyWorkListWrapperPageResponse.setRows(studentToDoListWrapperList);

            studentMyWorkListWrapperAjaxResponse.setStatus("1");
            studentMyWorkListWrapperAjaxResponse.setWrapper(studentMyWorkListWrapperPageResponse);
        } catch (CloudteachException e) {
            studentMyWorkListWrapperAjaxResponse.setErrorMsg(e.getMsg());
            studentMyWorkListWrapperAjaxResponse.setStatus("0");
        }
        return studentMyWorkListWrapperAjaxResponse;
    }


    /**
     * @return 预习列表
     * 分页获取预习列表
     * @author hujx
     * @date 2015/07/10
     */
    @RequestMapping(value = "prepList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentPrepListWrapper>> queryMyPrepList(StudentWorkPageRequest studentWorkPageRequest) {

        String identityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String studentId = studentWorkPageRequest.getStudentId();

        if (studentId == null) {
            studentId = GetSessionContentUtil.getInstance().currentUserID();
            if (identityId.equals(IdentityIdConstants.STUDENT)) {
                studentWorkPageRequest.setStudentId(studentId);
            }
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        studentWorkPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkPageRequest.getPublishTime())) {
            studentWorkPageRequest.setPublishTime("0");
        }

        AjaxResponse<PageResponse<StudentPrepListWrapper>> ajaxResponse = new AjaxResponse<>();
        logger.info("[获取预习列表：]");
        try {
            PageResponse<StudentWorkListViewDTO> studentWorkListViewDTOPageResponse = studentCloudWorkService.queryPrepList(studentWorkPageRequest);
            PageResponse<StudentPrepListWrapper> studentMyWorkListWrapperPageResponse = new PageResponse<>();

            List<StudentPrepListWrapper> studentPrepListWrapperList = new ArrayList<>();

            // 转换Wrapper
            for (StudentWorkListViewDTO studentWorkListViewDTO : studentWorkListViewDTOPageResponse.getRows()) {
                List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = studentWorkListViewDTO.getTeacherWorkItemAnswerFileDTOList();
                WorkTapeFilesDTO workFileInfoDTO = studentWorkListViewDTO.getWorkTapeFilesDTO();
                StudentPrepListWrapper studentPrepListWrapper = new StudentPrepListWrapper();
                BeanUtils.copyProperties(studentWorkListViewDTO, studentPrepListWrapper);
                studentPrepListWrapper.setTeacherWorkItemAnswerFileDTOList(teacherWorkItemAnswerFileDTOList);
                studentPrepListWrapper.setWorkTapeFilesDTO(workFileInfoDTO);
                // end_time - publish_time 得到时间差(毫秒)
                long range = Math.abs(studentWorkListViewDTO.getEndTime().getTime() - studentWorkListViewDTO.getPublishTime().getTime()) / (24 * 60 * 60 * 1000);
                studentPrepListWrapper.setLastCommitDays(range);
                studentPrepListWrapperList.add(studentPrepListWrapper);
            }
            BeanUtils.copyProperties(studentWorkListViewDTOPageResponse, studentMyWorkListWrapperPageResponse);
            studentMyWorkListWrapperPageResponse.setRows(studentPrepListWrapperList);

            ajaxResponse.setStatus("1");
            ajaxResponse.setWrapper(studentMyWorkListWrapperPageResponse);
        } catch (CloudteachException e) {
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMsg());
        }
        return ajaxResponse;
    }

    /**
     * @return 预习列表
     * 分页获取预习列表
     * @author hujx
     * @date 2015/07/10
     */
    @RequestMapping(value = "workList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentWorkListWrapper>> queryMyWorkList(StudentWorkPageRequest studentWorkPageRequest) {

        String identityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String studentId = studentWorkPageRequest.getStudentId();

        if (studentId == null) {
            studentId = GetSessionContentUtil.getInstance().currentUserID();
            if (identityId.equals(IdentityIdConstants.STUDENT)) {
                studentWorkPageRequest.setStudentId(studentId);
            }
        }

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        studentWorkPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkPageRequest.getPublishTime())) {
            studentWorkPageRequest.setPublishTime("0");
        }

        AjaxResponse<PageResponse<StudentWorkListWrapper>> ajaxResponse = new AjaxResponse<>();
        logger.info("[获取作业列表：]");
        try {
            PageResponse<StudentWorkListViewDTO> studentWorkListViewDTOPageResponse = studentCloudWorkService.queryWorkList(studentWorkPageRequest);
            PageResponse<StudentWorkListWrapper> studentWorkListWrapperPageResponse = new PageResponse<>();

            List<StudentWorkListWrapper> studentWorkListWrapperList = new ArrayList<>();

            // 转换Wrapper
            for (StudentWorkListViewDTO studentWorkListViewDTO : studentWorkListViewDTOPageResponse.getRows()) {

                // 附件信息
                List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = studentWorkListViewDTO.getTeacherWorkItemAnswerFileDTOList();
                // 录音信息
                WorkTapeFilesDTO workFileInfoDTO = studentWorkListViewDTO.getWorkTapeFilesDTO();
                // 提分宝挑战记录
                MagicWorkChallengeTimesViewDTO magicWorkChallengeTimesViewDTO = studentWorkListViewDTO.getMagicWorkChallengeTimesViewDTO();
                // 提分宝附件
                List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList = studentWorkListViewDTO.getMagicWorkAnswerFilesDTOList();
                // 同步课堂挑战记录
                List<SynclassWorkPlayViewDTO> synclassWorkPlayViewDTOList = studentWorkListViewDTO.getSynclassWorkPlayViewDTOList();

                StudentWorkListWrapper studentWorkListWrapper = new StudentWorkListWrapper();
                BeanUtils.copyProperties(studentWorkListViewDTO, studentWorkListWrapper);

                studentWorkListWrapper.setTeacherWorkItemAnswerFileDTOList(teacherWorkItemAnswerFileDTOList);
                studentWorkListWrapper.setMagicWorkChallengeTimesViewDTO(magicWorkChallengeTimesViewDTO);
                studentWorkListWrapper.setMagicWorkAnswerFilesDTOList(magicWorkAnswerFilesDTOList);
                studentWorkListWrapper.setSynclassWorkPlayViewDTOList(synclassWorkPlayViewDTOList);
                studentWorkListWrapper.setWorkTapeFilesDTO(workFileInfoDTO);
                // end_time - publish_time 得到时间差(毫秒)
                long range = Math.abs(studentWorkListViewDTO.getEndTime().getTime() - studentWorkListViewDTO.getPublishTime().getTime()) / (24 * 60 * 60 * 1000);
                studentWorkListWrapper.setLastCommitDays(range);
                studentWorkListWrapperList.add(studentWorkListWrapper);
            }
            BeanUtils.copyProperties(studentWorkListViewDTOPageResponse, studentWorkListWrapperPageResponse);
            studentWorkListWrapperPageResponse.setRows(studentWorkListWrapperList);

            ajaxResponse.setStatus("1");
            ajaxResponse.setWrapper(studentWorkListWrapperPageResponse);
        } catch (CloudteachException e) {
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMsg());
        }
        return ajaxResponse;
    }

}
