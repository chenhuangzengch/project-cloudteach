package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.dto.page.StudentWorkPageRequest;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;
import net.xuele.common.security.SessionUtil;
import net.xuele.member.constant.SecurityConstants;
import net.xuele.member.dto.StudentDTO;
import net.xuele.member.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 家长__云作业
 * Created by dj on 2015/7/31 0031.
 */
@Controller
@RequestMapping(value = "studentcloudwork/parent")
public class ParentCloudWorkManagerController {

    private static Logger logger = LoggerFactory.getLogger(ParentCloudWorkManagerController.class);

    @Autowired
    StudentCloudWorkService studentCloudWorkService;
    @Autowired
    WorkStatisticsService workStatisticsService;
    @Autowired
    CloudTeachService cloudTeachService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    MagicWorkStuService magicWorkStuService;
    @Autowired
    SynclassWorkStuService synclassWorkStuService;
    @Autowired
    UserService userService;


    /**
     * 跳转到待完成页面
     */
    @RequestMapping(value = "viewIncompleteWorkListPage")
    public String ViewIncompleteWorkList(ModelMap map) {

        logger.info("[家长待完成列表初始化：");

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        if (userId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        //获取家长孩子列表
        List<StudentDTO> studentDTOList = cloudTeachService.getChildrenList(userId);
        logger.info("[获取孩子信息：" + studentDTOList + "]");
        if (studentDTOList.size() > 0) {
            map.put("firstStudent", studentDTOList.get(0));
        }
        if (studentDTOList.size() == 1) {
            map.put("onlyChild", 1);//只有一个孩子
        }
        map.put("studentDTOList", studentDTOList);
        map.put("user", SessionUtil.getUserSession());

        if (CollectionUtils.isEmpty(studentDTOList)) {
            return "redirect:" + SecurityConstants.MEMBER_URL + "family/noKidPage";
        }
        return "/workmanage/parent/parent-complete-no";
    }

    /**
     * 跳转到作业页面
     */
    @RequestMapping(value = "viewWorkListPage")
    public String ViewWorkList(ModelMap map) {

        logger.info("[家长预习列表初始化：");

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        if (userId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        //获取家长孩子列表
        List<StudentDTO> studentDTOList = cloudTeachService.getChildrenList(userId);
        logger.info("[获取孩子信息：" + studentDTOList + "]");
        if (studentDTOList.size() > 0) {
            map.put("firstStudent", studentDTOList.get(0));
        }
        if (studentDTOList.size() == 1) {
            map.put("onlyChild", 1);//只有一个孩子
        }
        map.put("studentDTOList", studentDTOList);
        map.put("user", SessionUtil.getUserSession());

        if (CollectionUtils.isEmpty(studentDTOList)) {
            return "redirect:" + SecurityConstants.MEMBER_URL + "family/noKidPage";
        }
        return "/workmanage/parent/parent-homework";
    }

    /**
     * 跳转到预习页面
     */
    @RequestMapping(value = "viewGuidanceWorkListPage")
    public String ViewGuidanceWorkList(ModelMap map) {

        logger.info("[家长作业列表初始化：");

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        if (userId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        //获取家长孩子列表
        List<StudentDTO> studentDTOList = cloudTeachService.getChildrenList(userId);
        logger.info("[获取孩子信息：" + studentDTOList + "]");
        if (studentDTOList.size() > 0) {
            map.put("firstStudent", studentDTOList.get(0));
        }
        if (studentDTOList.size() == 1) {
            map.put("onlyChild", 1);//只有一个孩子
        }
        map.put("studentDTOList", studentDTOList);
        map.put("user", SessionUtil.getUserSession());

        if (CollectionUtils.isEmpty(studentDTOList)) {
            return "redirect:" + SecurityConstants.MEMBER_URL + "family/noKidPage";
        }
        return "/workmanage/parent/parent-guide";
    }

    /**
     * 通过studentId判断是不是当前登录者的子女
     */
    public boolean isOwnerChild(String studentId) {
        boolean flag = false;
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        if (userId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        //获取家长孩子列表
        List<StudentDTO> studentDTOList = cloudTeachService.getChildrenList(userId);
        if (studentDTOList.size() > 0) {
            for (StudentDTO stu : studentDTOList) {
                if (stu.getUserId().equals(studentId)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 跳转作业详情页面
     * workType:
     * '1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业',
     */
    @RequestMapping(value = "viewWorkDetail")
    public String viewWorkDetail(ModelMap map, @RequestParam("workId") String workId, @RequestParam("studentId") String studentId,
                                 @RequestParam("workType") String workType) {
        if (!isOwnerChild(studentId)) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.NOPERMISSION);//
        }
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        String url = "";
        if ("1".equals(workType)) {
            //预习作业
            TeacherWorkFinishDetailViewDTO teacherWorkFinishDetailViewDTO = teacherWorkStuService.teacherWorkFinishDetail(workId, studentId, schoolId);
            int day = 0;
            try {
                day = daysBetween(teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getPublishTime(), teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("workDetail", teacherWorkFinishDetailViewDTO);
            map.put("day", day);
            map.put("studentId", studentId);
            if (teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubStatus() == 1) {
                map.put("compliteTime", formateDate(teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubTime()));
            }
            url = "/workmanage/parent/write-guide";
        } else if ("4".equals(workType)) {
            //电子作业
            TeacherWorkFinishDetailViewDTO teacherWorkFinishDetailViewDTO = teacherWorkStuService.teacherWorkFinishDetail(workId, studentId, schoolId);
            int day = 0;
            try {
                day = daysBetween(teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getPublishTime(), teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("workDetail", teacherWorkFinishDetailViewDTO);
            map.put("day", day);
            map.put("studentId", studentId);
            if (teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubStatus() == 1) {
                map.put("compliteTime", formateDate(teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubTime()));
            }
            url = "/workmanage/parent/write-electronic";
        } else if ("2".equals(workType)) {
            //提分宝详情
            MagicWorkFinishDetailViewDTO magicWorkFinishDetailViewDTO = magicWorkStuService.queryMagicWorkDetail(workId, studentId, schoolId);
            int day = 0;
            try {
                day = daysBetween(magicWorkFinishDetailViewDTO.getBasicMagicWorkInfo().getPublishTime(), magicWorkFinishDetailViewDTO.getBasicMagicWorkInfo().getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("workDetail", magicWorkFinishDetailViewDTO);
            map.put("day", day);
            map.put("studentId", studentId);
            if (magicWorkFinishDetailViewDTO.getStuAnswerInfo().getSubStatus() == 1) {
                map.put("compliteTime", formateDate(magicWorkFinishDetailViewDTO.getStuAnswerInfo().getSubTime()));
            }
            url = "/workmanage/parent/write-grade";
        } else if ("3".equals(workType)) {
            //同步课堂详情
            SynclassWorkFinishDetailViewDTO synclassWorkFinishDetailViewDTO = synclassWorkStuService.synclassWorkFinishDetail(workId, studentId, schoolId);
            int day = 0;
            try {
                day = daysBetween(synclassWorkFinishDetailViewDTO.getBasicWorkInfo().getPublishTime(), synclassWorkFinishDetailViewDTO.getBasicWorkInfo().getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("workDetail", synclassWorkFinishDetailViewDTO);
            map.put("day", day);
            map.put("studentId", studentId);
            if (synclassWorkFinishDetailViewDTO.getStuAnswerInfo().getSubStatus() == 1) {
                map.put("compliteTime", formateDate(synclassWorkFinishDetailViewDTO.getStuAnswerInfo().getSubTime()));
            }
            url = "/workmanage/parent/write-sync";
        } else if ("7".equals(workType)) {
            //口语作业的详情
            TeacherWorkFinishDetailViewDTO teacherWorkFinishDetailViewDTO = teacherWorkStuService.teacherWorkFinishDetail(workId, studentId, schoolId);
            int day = 0;
            try {
                day = daysBetween(teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getPublishTime(), teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("workDetail", teacherWorkFinishDetailViewDTO);
            map.put("day", day);
            map.put("studentId", studentId);
            if (teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubStatus() == 1) {
                map.put("compliteTime", formateDate(teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubTime()));
            }
            url = "/workmanage/parent/write-spoken";
        } else if ("8".equals(workType)) {
            // 课外作业
            TeacherWorkFinishDetailViewDTO teacherWorkFinishDetailViewDTO = teacherWorkStuService.teacherWorkFinishDetail(workId, studentId, schoolId);
            int day = 0;
            try {
                day = daysBetween(teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getPublishTime(), teacherWorkFinishDetailViewDTO.getBasicWorkInfo().getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("workDetail", teacherWorkFinishDetailViewDTO);
            map.put("day", day);
            map.put("studentId", studentId);
            if (teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubStatus() == 1) {
                map.put("compliteTime", formateDate(teacherWorkFinishDetailViewDTO.getTeacherWorkItemAnswerDTO().getSubTime()));
            }
            url = "/workmanage/parent/write-extra";
        }
        return url;
    }

    /**
     * 查看学生作业科目类型
     */
    @RequestMapping(value = "querySubjectType")
    @ResponseBody
    public AjaxResponse<List<SubjectTypeWrapper>> querySubjectType(@RequestParam("studentId") String studentId) {
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        List<SubjectGatherViewDTO> subjectGatherViewDTOList = workStatisticsService.queryStudentWorkSubjectList(studentId, schoolId);
        List<SubjectTypeWrapper> subjectTypeWrapperList = new ArrayList<>();
        for (SubjectGatherViewDTO subjectGatherViewDTO : subjectGatherViewDTOList) {
            SubjectTypeWrapper subjectTypeWrapper = new SubjectTypeWrapper();
            BeanUtils.copyProperties(subjectGatherViewDTO, subjectTypeWrapper);
            subjectTypeWrapperList.add(subjectTypeWrapper);
        }
        AjaxResponse<List<SubjectTypeWrapper>> ajaxResponse = new AjaxResponse<>();
        ajaxResponse.setWrapper(subjectTypeWrapperList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }


    /**
     * 查看待完成的作业
     */
    @RequestMapping(value = "queryIncompleteWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentToDoListWrapper>> queryIncompleteWorkList(StudentWorkPageRequest request) {
        String schoolId = userService.getByUserId(request.getStudentId()).getSchoolId();
        request.setSchoolId(schoolId);
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        AjaxResponse<PageResponse<StudentToDoListWrapper>> studentMyWorkListWrapperAjaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkListViewDTO> studentWorkListViewDTOPageResponse = studentCloudWorkService.queryToDoWorkList(request);
        PageResponse<StudentToDoListWrapper> studentMyWorkListWrapperPageResponse = new PageResponse<>();

        List<StudentToDoListWrapper> studentToDoListWrapperList = new ArrayList<>();

        // 转换Wrapper
        for (StudentWorkListViewDTO studentWorkListViewDTO : studentWorkListViewDTOPageResponse.getRows()) {
            StudentToDoListWrapper studentToDoListWrapper = new StudentToDoListWrapper();
            BeanUtils.copyProperties(studentWorkListViewDTO, studentToDoListWrapper);
            // end_time - publish_time 得到时间差(毫秒)
            long range = (studentWorkListViewDTO.getEndTime().getTime() - studentWorkListViewDTO.getPublishTime().getTime()) / (24 * 60 * 60 * 1000);
            studentToDoListWrapper.setLastCommitDays(range);
            studentToDoListWrapperList.add(studentToDoListWrapper);
        }
        BeanUtils.copyProperties(studentWorkListViewDTOPageResponse, studentMyWorkListWrapperPageResponse);
        studentMyWorkListWrapperPageResponse.setRows(studentToDoListWrapperList);
        studentMyWorkListWrapperAjaxResponse.setStatus("1");
        studentMyWorkListWrapperAjaxResponse.setWrapper(studentMyWorkListWrapperPageResponse);
        return studentMyWorkListWrapperAjaxResponse;
    }

    /**
     * 查看作业列表
     */
    @RequestMapping(value = "queryWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentWorkListWrapper>> queryWorkList(StudentWorkPageRequest request) {
        // String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = userService.getByUserId(request.getStudentId()).getSchoolId();
        request.setSchoolId(schoolId);
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        AjaxResponse<PageResponse<StudentWorkListWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkListViewDTO> studentWorkListViewDTOPageResponse = studentCloudWorkService.queryWorkList(request);
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
            long range = (studentWorkListViewDTO.getEndTime().getTime() - studentWorkListViewDTO.getPublishTime().getTime()) / (24 * 60 * 60 * 1000);
            studentWorkListWrapper.setLastCommitDays(range);
            studentWorkListWrapperList.add(studentWorkListWrapper);
        }
        BeanUtils.copyProperties(studentWorkListViewDTOPageResponse, studentWorkListWrapperPageResponse);
        studentWorkListWrapperPageResponse.setRows(studentWorkListWrapperList);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(studentWorkListWrapperPageResponse);
        return ajaxResponse;
    }

    /**
     * 查看预习作业
     */
    @RequestMapping(value = "queryGuidanceWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentWorkListWrapper>> queryGuidanceWorkList(StudentWorkPageRequest request) {
        String schoolId = userService.getByUserId(request.getStudentId()).getSchoolId();
        request.setSchoolId(schoolId);
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        AjaxResponse<PageResponse<StudentWorkListWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkListViewDTO> studentWorkListViewDTOPageResponse = studentCloudWorkService.queryPrepList(request);
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
            long range = (studentWorkListViewDTO.getEndTime().getTime() - studentWorkListViewDTO.getPublishTime().getTime()) / (24 * 60 * 60 * 1000);
            studentWorkListWrapper.setLastCommitDays(range);
            studentWorkListWrapperList.add(studentWorkListWrapper);
        }
        BeanUtils.copyProperties(studentWorkListViewDTOPageResponse, studentWorkListWrapperPageResponse);
        studentWorkListWrapperPageResponse.setRows(studentWorkListWrapperList);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(studentWorkListWrapperPageResponse);
        return ajaxResponse;
    }

    //根据时间获取相差天
    private int daysBetween(Date startDate, Date endDate) throws ParseException {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        return Integer.parseInt(String.valueOf((endTime - startTime) / (24 * 3600 * 1000)));
    }

    private String formateDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        String time = f.format(date);
        return time;
    }

    /**
     * 预习作业--作业交流
     */
    @RequestMapping(value = "guidanceWorkCommunication")
    public String guidanceWorkCommunication(ModelMap map, String workId, String studentId) {
        logger.info("[预习作业作业交流初始化：]");
        //String studentId = GetSessionContentUtil.getInstance().currentUserID();
        //String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        int displayStatus = 0;
        Date now = new Date();
        long subStuNum = workStatisticsService.submitStuCount(workId, schoolId);
        logger.info("[预习作业完成人数：" + subStuNum + "]");

        boolean overdue = workStatisticsService.overdue(workId, now, schoolId);
        logger.info("[作业是否过期：" + overdue + "]");

        if (workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId)) {
            displayStatus = 1;
        }
        logger.info("[是否允许进入作业交流：" + displayStatus + "]");

        map.put("workId", workId);
        map.put("displayStatus", displayStatus);
        map.put("subStuNum", subStuNum);
        map.put("overdue", overdue);
        map.put("studentId", studentId);
        return "workmanage/parent/guidanceWorkCommunication";
    }

    /**
     * 电子作业--作业交流
     */
    @RequestMapping(value = "exerciseWorkCommunication")
    public String exerciseWorkCommunication(ModelMap map, String workId, String studentId) {
        logger.info("[电子作业作业交流初始化：]");
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        int displayStatus = 0;
        Date now = new Date();
        long subStuNum = workStatisticsService.submitStuCount(workId, schoolId);
        logger.info("[电子作业完成人数：" + subStuNum + "]");

        boolean overdue = workStatisticsService.overdue(workId, now, schoolId);
        logger.info("[作业是否过期：" + overdue + "]");

        if (workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId)) {
            displayStatus = 1;
        }
        logger.info("[是否允许进入作业交流：" + displayStatus + "]");

        map.put("workId", workId);
        map.put("displayStatus", displayStatus);
        map.put("subStuNum", subStuNum);
        map.put("overdue", overdue);
        map.put("studentId", studentId);
        return "workmanage/parent/exerciseWorkCommunication";
    }

    /**
     * 口语作业--作业交流
     */
    @RequestMapping(value = "voiceWorkCommunication")
    public String voiceWorkCommunication(ModelMap map, String workId, String studentId) {
        logger.info("[口语作业作业交流初始化：]");
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        int displayStatus = 0;
        Date now = new Date();
        long subStuNum = workStatisticsService.submitStuCount(workId, schoolId);
        logger.info("[口语作业完成人数：" + subStuNum + "]");

        boolean overdue = workStatisticsService.overdue(workId, now, schoolId);
        logger.info("[作业是否过期：" + overdue + "]");

        if (workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId)) {
            displayStatus = 1;
        }
        logger.info("[是否允许进入作业交流：" + displayStatus + "]");

        map.put("workId", workId);
        map.put("displayStatus", displayStatus);
        map.put("subStuNum", subStuNum);
        map.put("overdue", overdue);
        map.put("studentId", studentId);
        return "workmanage/parent/voiceWorkCommunication";
    }

    /**
     * 同步课堂--作业交流
     */
    @RequestMapping(value = "synclassWorkCommunication")
    public String synclassWorkCommunication(ModelMap map, String workId, String studentId) {
        logger.info("[同步课堂作业作业交流初始化：]");
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        int displayStatus = 0;
        Date now = new Date();
        long subStuNum = workStatisticsService.submitStuCount(workId, schoolId);
        logger.info("[同步课堂作业完成人数：" + subStuNum + "]");

        boolean overdue = workStatisticsService.overdue(workId, now, schoolId);
        logger.info("[作业是否过期：" + overdue + "]");

        if (workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId)) {
            displayStatus = 1;
        }
        logger.info("[是否允许进入作业交流：" + displayStatus + "]");

        map.put("ret", true);
        map.put("workId", workId);
        map.put("displayStatus", displayStatus);
        map.put("subStuNum", subStuNum);
        map.put("overdue", overdue);
        map.put("studentId", studentId);
        return "workmanage/parent/synclassWorkCommunication";
    }

    /**
     * 提分宝--作业交流
     */
    @RequestMapping(value = "magicWorkCommunication")
    public String magicWorkCommunication(ModelMap map, String workId, String studentId) {
        logger.info("[提分宝作业作业交流初始化：]");
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();

        int displayStatus = 0;
        String link = null;
        Date now = new Date();
        MagicWorkAnswerDTO stuAnswer = magicWorkStuService.getStuAnswerByWorkStu(workId, studentId, schoolId);
        int subStatus = stuAnswer.getSubStatus();

        long subStuNum = workStatisticsService.submitStuCount(workId, schoolId);
        logger.info("[提分宝作业完成人数：" + subStuNum + "]");

        boolean overdue = workStatisticsService.overdue(workId, now, schoolId);
        logger.info("[作业是否过期：" + overdue + "]");

        if (workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId)) {
            displayStatus = 1;
        }
        logger.info("[是否允许进入作业交流：" + displayStatus + "]");

        if (subStatus == 1) {
            link = "magicChallenge";
        } else {
            link = "workDetail";
        }

        map.put("workId", workId);
        map.put("displayStatus", displayStatus);
        map.put("link", link);
        map.put("subStuNum", subStuNum);
        map.put("overdue", overdue);
        map.put("studentId", studentId);
        return "workmanage/parent/magicWorkCommunication";
    }

    /**
     * 提分宝--挑战记录
     */
    @RequestMapping(value = "challengeRecords")
    public String magicWorkChallengeRecord(ModelMap map, String workId, String studentId) {
        logger.info("[提分宝挑战记录初始化：]");
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();

        String link = null;
        List<MagicWorkChallengeDTO> magicWorkChallengeRecords =
                magicWorkStuService.magicWorkChallengeRecord(workId, studentId, null, 1, schoolId);
        logger.info("[获取提分宝挑战记录：" + magicWorkChallengeRecords + "]");

        MagicWorkAnswerDTO stuAnswer = magicWorkStuService.getStuAnswerByWorkStu(workId, studentId, schoolId);
        int subStatus = stuAnswer.getSubStatus();

        if (subStatus == 1) {
            link = "magicChallenge";
        } else {
            link = "workDetail";
        }

        map.put("link", link);
        map.put("workId", workId);
        map.put("magicWorkChallengeRecords", magicWorkChallengeRecords);
        map.put("studentId", studentId);
        return "workmanage/parent/magicChallengeRecords";
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return 电子作业--作业交流列表
     */
    @RequestMapping(value = "exerciseWorkCommunicationList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> exerciseWorkCommunicationList(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入电子作业作业交流：]");
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        studentWorkCommunicationPageRequest.setStudentId(studentId);
        studentWorkCommunicationPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkCommunicationPageRequest.getSubTime())) {
            studentWorkCommunicationPageRequest.setSubTime("0");
        }

        AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkCommunicationViewDTO> dtoPageResponse = null;

        try {
            dtoPageResponse = teacherWorkStuService.teacherWorkCommunication(studentWorkCommunicationPageRequest);
            PageResponse<StudentTeacherWorkCommunicationWrapper> wrapperPageResponse = new PageResponse<>();
            List<StudentTeacherWorkCommunicationWrapper> studentTeacherWorkCommunicationWrapperList = new ArrayList<>();
            // 转换Wrapper
            for (StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO : dtoPageResponse.getRows()) {
                StudentTeacherWorkCommunicationWrapper studentTeacherWorkCommunicationWrapper = new StudentTeacherWorkCommunicationWrapper();
                BeanUtils.copyProperties(studentWorkCommunicationViewDTO, studentTeacherWorkCommunicationWrapper);
                studentTeacherWorkCommunicationWrapper.setIcon(studentWorkCommunicationViewDTO.getStudentHeadIcon());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerFileDTOList(studentWorkCommunicationViewDTO.getTeacherWorkItemAnswerFileDTOList());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerCommNameViewDTOList(studentWorkCommunicationViewDTO.getWorkAnswerCommentViewDTOList());

                studentTeacherWorkCommunicationWrapperList.add(studentTeacherWorkCommunicationWrapper);
            }

            BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
            wrapperPageResponse.setRows(studentTeacherWorkCommunicationWrapperList);

            ajaxResponse.setWrapper(wrapperPageResponse);
            ajaxResponse.setStatus("1");
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
            logger.info(e.getMsg());
        }
        return ajaxResponse;
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return 预习作业--作业交流列表
     */
    @RequestMapping(value = "guidanceWorkCommunicationList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> guidanceWorkCommunicationList(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入预习作业作业交流：]");
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String schoolId = userService.getByUserId(studentId).getSchoolId();

        studentWorkCommunicationPageRequest.setStudentId(studentId);
        studentWorkCommunicationPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkCommunicationPageRequest.getSubTime())) {
            studentWorkCommunicationPageRequest.setSubTime("0");
        }

        AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkCommunicationViewDTO> dtoPageResponse = null;
        try {
            dtoPageResponse = teacherWorkStuService.teacherWorkCommunication(studentWorkCommunicationPageRequest);
            PageResponse<StudentTeacherWorkCommunicationWrapper> wrapperPageResponse = new PageResponse<>();
            List<StudentTeacherWorkCommunicationWrapper> studentTeacherWorkCommunicationWrapperList = new ArrayList<>();
            // 转换Wrapper
            for (StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO : dtoPageResponse.getRows()) {
                StudentTeacherWorkCommunicationWrapper studentTeacherWorkCommunicationWrapper = new StudentTeacherWorkCommunicationWrapper();
                BeanUtils.copyProperties(studentWorkCommunicationViewDTO, studentTeacherWorkCommunicationWrapper);
                studentTeacherWorkCommunicationWrapper.setIcon(studentWorkCommunicationViewDTO.getStudentHeadIcon());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerFileDTOList(studentWorkCommunicationViewDTO.getTeacherWorkItemAnswerFileDTOList());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerCommNameViewDTOList(studentWorkCommunicationViewDTO.getWorkAnswerCommentViewDTOList());

                studentTeacherWorkCommunicationWrapperList.add(studentTeacherWorkCommunicationWrapper);
            }

            BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
            wrapperPageResponse.setRows(studentTeacherWorkCommunicationWrapperList);

            ajaxResponse.setWrapper(wrapperPageResponse);
            ajaxResponse.setStatus("1");
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
            logger.info(e.getMsg());
        }
        return ajaxResponse;
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return 同步课堂作业--作业交流列表
     */
    @RequestMapping(value = "synclassWorkCommunicationList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentSynclassWorkCommunicationWrapper>> synclassWorkCommunicationList(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入同步课堂作业作业交流：]");
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String schoolId = userService.getByUserId(studentId).getSchoolId();

        studentWorkCommunicationPageRequest.setStudentId(studentId);
        studentWorkCommunicationPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkCommunicationPageRequest.getSubTime())) {
            studentWorkCommunicationPageRequest.setSubTime("0");
        }

        AjaxResponse<PageResponse<StudentSynclassWorkCommunicationWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkCommunicationViewDTO> dtoPageResponse = null;
        try {
            dtoPageResponse = synclassWorkStuService.synclassWorkCommunication(studentWorkCommunicationPageRequest);
            PageResponse<StudentSynclassWorkCommunicationWrapper> wrapperPageResponse = new PageResponse<>();
            List<StudentSynclassWorkCommunicationWrapper> studentTeacherWorkCommunicationWrapperList = new ArrayList<>();
            // 转换Wrapper
            for (StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO : dtoPageResponse.getRows()) {
                StudentSynclassWorkCommunicationWrapper studentSynclassWorkCommunicationWrapper = new StudentSynclassWorkCommunicationWrapper();
                BeanUtils.copyProperties(studentWorkCommunicationViewDTO, studentSynclassWorkCommunicationWrapper);
                studentSynclassWorkCommunicationWrapper.setIcon(studentWorkCommunicationViewDTO.getStudentHeadIcon());
                studentSynclassWorkCommunicationWrapper.setSynclassWorkGamePlayInfoViewDTOList(studentWorkCommunicationViewDTO.getSynclassWorkGamePlayInfoViewDTOList());
                studentSynclassWorkCommunicationWrapper.setSynclassWorkAnswerCommNameViewDTOList(studentWorkCommunicationViewDTO.getWorkAnswerCommentViewDTOList());

                studentTeacherWorkCommunicationWrapperList.add(studentSynclassWorkCommunicationWrapper);
            }

            BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
            wrapperPageResponse.setRows(studentTeacherWorkCommunicationWrapperList);

            ajaxResponse.setWrapper(wrapperPageResponse);
            ajaxResponse.setStatus("1");
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
            logger.info(e.getMsg());
        }
        return ajaxResponse;
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return 语音作业--作业交流列表
     */
    @RequestMapping(value = "voiceWorkCommunicationList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> voiceWorkCommunicationList(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入口语作业作业交流：]");
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String schoolId = userService.getByUserId(studentId).getSchoolId();

        studentWorkCommunicationPageRequest.setStudentId(studentId);
        studentWorkCommunicationPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkCommunicationPageRequest.getSubTime())) {
            studentWorkCommunicationPageRequest.setSubTime("0");
        }

        AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkCommunicationViewDTO> dtoPageResponse = null;
        try {
            dtoPageResponse = teacherWorkStuService.teacherWorkCommunication(studentWorkCommunicationPageRequest);
            PageResponse<StudentTeacherWorkCommunicationWrapper> wrapperPageResponse = new PageResponse<>();
            List<StudentTeacherWorkCommunicationWrapper> studentTeacherWorkCommunicationWrapperList = new ArrayList<>();
            // 转换Wrapper
            for (StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO : dtoPageResponse.getRows()) {
                StudentTeacherWorkCommunicationWrapper studentTeacherWorkCommunicationWrapper = new StudentTeacherWorkCommunicationWrapper();
                BeanUtils.copyProperties(studentWorkCommunicationViewDTO, studentTeacherWorkCommunicationWrapper);
                studentTeacherWorkCommunicationWrapper.setIcon(studentWorkCommunicationViewDTO.getStudentHeadIcon());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerFileDTOList(studentWorkCommunicationViewDTO.getTeacherWorkItemAnswerFileDTOList());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerCommNameViewDTOList(studentWorkCommunicationViewDTO.getWorkAnswerCommentViewDTOList());

                studentTeacherWorkCommunicationWrapperList.add(studentTeacherWorkCommunicationWrapper);
            }

            BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
            wrapperPageResponse.setRows(studentTeacherWorkCommunicationWrapperList);

            ajaxResponse.setWrapper(wrapperPageResponse);
            ajaxResponse.setStatus("1");
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
            logger.info(e.getMsg());
        }
        return ajaxResponse;
    }


    /**
     * @param studentWorkCommunicationPageRequest
     * @return 提分宝作业--作业交流列表
     */
    @RequestMapping(value = "magicWorkCommunicationList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentMagicWorkCommunicationWrapper>> magicWorkCommunicationList(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {

//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String schoolId = userService.getByUserId(studentId).getSchoolId();

        studentWorkCommunicationPageRequest.setStudentId(studentId);
        studentWorkCommunicationPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkCommunicationPageRequest.getSubTime())) {
            studentWorkCommunicationPageRequest.setSubTime("0");
        }

        AjaxResponse<PageResponse<StudentMagicWorkCommunicationWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkCommunicationViewDTO> dtoPageResponse = null;
        logger.info("[进入提分宝作业作业交流：]");
        try {
            dtoPageResponse = magicWorkStuService.magicWorkCommunication(studentWorkCommunicationPageRequest);
            PageResponse<StudentMagicWorkCommunicationWrapper> wrapperPageResponse = new PageResponse<>();
            List<StudentMagicWorkCommunicationWrapper> studentTeacherWorkCommunicationWrapperList = new ArrayList<>();
            // 转换Wrapper
            for (StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO : dtoPageResponse.getRows()) {
                StudentMagicWorkCommunicationWrapper studentMagicWorkCommunicationWrapper = new StudentMagicWorkCommunicationWrapper();
                BeanUtils.copyProperties(studentWorkCommunicationViewDTO, studentMagicWorkCommunicationWrapper);
                studentMagicWorkCommunicationWrapper.setIcon(studentWorkCommunicationViewDTO.getStudentHeadIcon());
                studentMagicWorkCommunicationWrapper.setMagicWorkAnswerFilesDTOList(studentWorkCommunicationViewDTO.getMagicWorkAnswerFilesDTOList());
                studentMagicWorkCommunicationWrapper.setMagicWorkAnswerCommNameViewDTOList(studentWorkCommunicationViewDTO.getWorkAnswerCommentViewDTOList());

                studentTeacherWorkCommunicationWrapperList.add(studentMagicWorkCommunicationWrapper);
            }

            BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
            wrapperPageResponse.setRows(studentTeacherWorkCommunicationWrapperList);

            ajaxResponse.setWrapper(wrapperPageResponse);
            ajaxResponse.setStatus("1");
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
            logger.info(e.getMsg());
        }
        return ajaxResponse;
    }

    /**
     * 电子作业--作业交流
     */
    @RequestMapping(value = "extraWorkCommunication")
    public String extraWorkCommunication(ModelMap map, String workId, String studentId) {
        logger.info("[课外作业作业交流初始化：]");
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        int displayStatus = 0;
        Date now = new Date();
        long subStuNum = workStatisticsService.submitStuCount(workId, schoolId);
        logger.info("[电子作业完成人数：" + subStuNum + "]");

        boolean overdue = workStatisticsService.overdue(workId, now, schoolId);
        logger.info("[作业是否过期：" + overdue + "]");

        if (workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId)) {
            displayStatus = 1;
        }
        logger.info("[是否允许进入作业交流：" + displayStatus + "]");

        map.put("workId", workId);
        map.put("displayStatus", displayStatus);
        map.put("subStuNum", subStuNum);
        map.put("overdue", overdue);
        map.put("studentId", studentId);
        return "workmanage/parent/extraWorkCommunication";
    }

        /**
     * @param studentWorkCommunicationPageRequest
     * @return 电子作业--作业交流列表
     */
    @RequestMapping(value = "extraWorkCommunicationList")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> extraWorkCommunicationList(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入课外作业作业交流：]");
//        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
//        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String schoolId = userService.getByUserId(studentId).getSchoolId();
        studentWorkCommunicationPageRequest.setStudentId(studentId);
        studentWorkCommunicationPageRequest.setSchoolId(schoolId);

        if (StringUtils.isEmpty(studentWorkCommunicationPageRequest.getSubTime())) {
            studentWorkCommunicationPageRequest.setSubTime("0");
        }

        AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> ajaxResponse = new AjaxResponse<>();
        PageResponse<StudentWorkCommunicationViewDTO> dtoPageResponse = null;

        try {
            dtoPageResponse = teacherWorkStuService.teacherWorkCommunication(studentWorkCommunicationPageRequest);
            PageResponse<StudentTeacherWorkCommunicationWrapper> wrapperPageResponse = new PageResponse<>();
            List<StudentTeacherWorkCommunicationWrapper> studentTeacherWorkCommunicationWrapperList = new ArrayList<>();
            // 转换Wrapper
            for (StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO : dtoPageResponse.getRows()) {
                StudentTeacherWorkCommunicationWrapper studentTeacherWorkCommunicationWrapper = new StudentTeacherWorkCommunicationWrapper();
                BeanUtils.copyProperties(studentWorkCommunicationViewDTO, studentTeacherWorkCommunicationWrapper);
                studentTeacherWorkCommunicationWrapper.setIcon(studentWorkCommunicationViewDTO.getStudentHeadIcon());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerFileDTOList(studentWorkCommunicationViewDTO.getTeacherWorkItemAnswerFileDTOList());
                studentTeacherWorkCommunicationWrapper.setTeacherWorkItemAnswerCommNameViewDTOList(studentWorkCommunicationViewDTO.getWorkAnswerCommentViewDTOList());

                studentTeacherWorkCommunicationWrapperList.add(studentTeacherWorkCommunicationWrapper);
            }

            BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
            wrapperPageResponse.setRows(studentTeacherWorkCommunicationWrapperList);

            ajaxResponse.setWrapper(wrapperPageResponse);
            ajaxResponse.setStatus("1");
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
            logger.info(e.getMsg());
        }
        return ajaxResponse;
    }

}