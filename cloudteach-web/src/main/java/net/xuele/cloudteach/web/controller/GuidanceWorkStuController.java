package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.service.StudentCloudWorkService;
import net.xuele.cloudteach.service.TeacherWorkStuService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.web.common.DateTranslate;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.workSubmitForm;
import net.xuele.cloudteach.web.wrapper.StudentTeacherWorkCommunicationWrapper;
import net.xuele.cloudteach.web.wrapper.TeacherWorkFinishDetailWrapper;
import net.xuele.cloudteach.web.wrapper.TeacherWorkWriteDetailWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * GuidanceWorkStuController
 * 学生预习作业管理
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
@Controller
@RequestMapping(value = "guidance/work/student")
public class GuidanceWorkStuController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(GuidanceWorkStuController.class);

    @Autowired
    StudentCloudWorkService studentCloudWorkService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    WorkStatisticsService workStatisticsService;

    /**
     * 返回预习作业写作业画面
     *
     * @param map
     * @param workId
     * @return
     */
    @RequestMapping(value = "prepDetail")
    public String queryMyWorkDetail(ModelMap map, String workId) {
        logger.info("[进入预习作业详情页：]");
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String studentId = GetSessionContentUtil.getInstance().currentUserID();

        // 初始化作业开始时间
        workStatisticsService.initStudentGatherStuView(workId, studentId, schoolId);
        // 获取完成的作业信息：
        TeacherWorkFinishDetailViewDTO workFinishDetail = teacherWorkStuService.teacherWorkFinishDetail(workId, studentId, schoolId);
        // 获取作业基本信息：
        BasicWorkInfoViewDTO basicWorkInfo = workFinishDetail.getBasicWorkInfo();
        // 获取教师录音信息
        WorkTapeFilesDTO workTapeFilesDTO = workFinishDetail.getWorkTapeFilesDTO();
        // 获取题目附件信息
        List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailList = workFinishDetail.getTeacherWorkFileDetailViewList();
        // 获取学生回答信息
        TeacherWorkItemAnswerDTO stuWorkItemAnswerDTO = workFinishDetail.getTeacherWorkItemAnswerDTO();
        // 获取学生添加附件信息
        List<TeacherWorkItemAnswerFileDTO> stuWorkItemAnswerFileList = workFinishDetail.getCtTeacherWorkItemAnswerFileList();

        // 计算得到最晚提交时间(1,3,5天)
        long lastCommitRange = basicWorkInfo.getEndTime().getTime() - basicWorkInfo.getPublishTime().getTime();
        long lastCommitDays = lastCommitRange / (24 * 60 * 60 * 1000);
        String subTime = null;
        Date subDate = stuWorkItemAnswerDTO.getSubTime();
        if (subDate != null) {
            subTime = DateTranslate.getFormatStringDate(subDate, "yyyy年MM月dd日 HH:mm");
        }

        map.put("basicWorkInfo", basicWorkInfo);
        map.put("workTapeFilesDTO", workTapeFilesDTO);
        map.put("teacherWorkFileDetailList", teacherWorkFileDetailList);
        map.put("stuWorkItemAnswerDTO", stuWorkItemAnswerDTO);
        map.put("stuWorkItemAnswerFileList", stuWorkItemAnswerFileList);
        map.put("lastCommitDays", lastCommitDays);
        map.put("subTime", subTime);
        return "workmanage/student/writeGuidanceWork";
    }

    /**
     * 返回预习作业 作业交流
     *
     * @param map
     * @param workId
     * @return
     */
    @RequestMapping(value = "guidanceWorkCommunication")
    public String guidanceWorkCommunication(ModelMap map, String workId) {
        logger.info("[预习作业作业交流初始化：]");
        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

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

        return "workmanage/student/guidanceWorkCommunication";
    }

    /**
     * @return 提交预习作业
     * @author hujx
     * @date 2015/07/12
     */
    @RequestMapping(value = "handInMyPrepWork")
    @ResponseBody
    public AjaxResponse handInMyPrepWork(String workId, String answerContext, String workFileJSON) {

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        AjaxResponse response = new AjaxResponse();
        TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = new TeacherWorkItemAnswerDTO();
        List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = new ArrayList<>();

        //String teacherWorkFilesJSON = workSubmitForm.getWorkFilesJSON();

        // 解析json
        if (workFileJSON != null && !workFileJSON.equals("")) {
            JSONArray teacherWorkFilesJSONArray = JSON.parseArray(workFileJSON);
            if (teacherWorkFilesJSONArray.size() != 0 && teacherWorkFilesJSONArray != null) {
                for (int i = 0; i < teacherWorkFilesJSONArray.size(); i++) {
                    JSONObject jsonObject = teacherWorkFilesJSONArray.getJSONObject(i);
                    TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO = new TeacherWorkItemAnswerFileDTO();
                    teacherWorkItemAnswerFileDTO.setUrl(jsonObject.getString("uri"));
                    teacherWorkItemAnswerFileDTO.setFileName(jsonObject.getString("fileName"));
                    teacherWorkItemAnswerFileDTO.setExtension(jsonObject.getString("extension"));
                    teacherWorkItemAnswerFileDTO.setSize(jsonObject.getIntValue("size"));
                    teacherWorkItemAnswerFileDTO.setFileType(jsonObject.getIntValue("type"));
                    teacherWorkItemAnswerFileDTOList.add(teacherWorkItemAnswerFileDTO);
                }
            }
        }

        teacherWorkItemAnswerDTO.setWorkId(workId);
        teacherWorkItemAnswerDTO.setContext(answerContext);
        teacherWorkItemAnswerDTO.setUserId(studentId);
        teacherWorkItemAnswerDTO.setSchoolId(schoolId);
        try {
            logger.info("[学生提交作业，更新作业统计表:]");
            int unSubmitStuAmount = workStatisticsService.updateSubmitStudentNum(workId, schoolId);
            logger.info("[学生提交作业基本处理:]");
            teacherWorkStuService.handInMyTeacherWork(teacherWorkItemAnswerDTO, teacherWorkItemAnswerFileDTOList);
            logger.info("[学生提交作业后，更新作业表作业完成状态:]");
            teacherWorkStuService.updateWorkFinishStatus(unSubmitStuAmount, workId, schoolId);
            response.setStatus("1");
        } catch (CloudteachException e) {
            response.setErrorMsg(e.getMsg());
            response.setStatus("0");
            logger.info(e.getMsg());
        }
        return response;
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return 预习作业，作业交流，我的作业置顶
     * @author hujx
     * @date 2015/07/21
     */
    @RequestMapping(value = "workCommunication")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentTeacherWorkCommunicationWrapper>> queryWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入预习作业作业交流：]");
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String studentId = GetSessionContentUtil.getInstance().currentUserID();

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
     * 对学生的作业回答点赞处理
     *
     * @param answerId
     * @return
     */
    @RequestMapping(value = "praise")
    @ResponseBody
    public AjaxResponse praise(@RequestParam("answerId") String answerId) {

        logger.info("[学生对以下回答点赞, 回答ID: " + answerId + "]");

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        teacherWorkStuService.praise(answerId, studentId, schoolId, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 学生取消点赞
     *
     * @param answerId
     * @return
     */
    @RequestMapping(value = "cancelPraise")
    @ResponseBody
    public AjaxResponse cancelPraise(@RequestParam("answerId") String answerId) {

        logger.info("[学生取消对以下回答的点赞, 回答ID: " + answerId + "]");

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        teacherWorkStuService.cancelPraise(answerId, studentId, schoolId, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 对学生的作业回答评论处理
     *
     * @param answerId
     * @param context
     * @return
     */
    @RequestMapping(value = "comment")
    @ResponseBody
    public AjaxResponse comment(@RequestParam("answerId") String answerId, @RequestParam("context") String context) {

        logger.info("[学生对以下回答评论, 回答ID: " + answerId + "], 评论内容: " + context + "]");

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        String commentId = teacherWorkStuService.comment(answerId, studentId, schoolId, context, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(commentId);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 学生删除自己的评论
     *
     * @param commentId
     * @return
     */
    @RequestMapping(value = "delComment")
    @ResponseBody
    public AjaxResponse delComment(@RequestParam("commentId") String commentId) {

        logger.info("[学生删除对以下回答的评论, 评论ID: +" + commentId + "]");

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        teacherWorkStuService.delComment(commentId, studentId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 获取更多评论
     *
     * @param answerId
     * @return
     */
    @RequestMapping(value = "moreComment")
    @ResponseBody
    public AjaxResponse moreComment(@RequestParam("answerId") String answerId) {

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        List<WorkAnswerCommentViewDTO> moreCommentList = teacherWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

}