package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.service.StudentCloudWorkService;
import net.xuele.cloudteach.service.SynclassWorkStuService;
import net.xuele.cloudteach.service.TeacherWorkStuService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.web.common.DateTranslate;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.workSubmitForm;
import net.xuele.cloudteach.web.wrapper.StudentSynclassWorkCommunicationWrapper;
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

/**
 * SynclassWorkStuController
 * 学生同步课堂作业管理
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
@Controller
@RequestMapping(value = "synclass/work/student")
public class SynclassWorkStuController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SynclassWorkStuController.class);

    @Autowired
    StudentCloudWorkService studentCloudWorkService;
    @Autowired
    SynclassWorkStuService synclassWorkStuService;
    @Autowired
    WorkStatisticsService workStatisticsService;

    /**
     * 返回同步课堂写作业
     *
     * @param map
     * @param workId
     * @return
     */
    @RequestMapping(value = "synclassDetail")
    public String queryMyWorkDetail(ModelMap map, String workId) {
        logger.info("[进入同步课堂作业详情页：]");
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String studentId = GetSessionContentUtil.getInstance().currentUserID();

        // 初始化作业开始时间
        workStatisticsService.initStudentGatherStuView(workId, studentId, schoolId);
        // 获取同步课堂作业全部信息
        SynclassWorkFinishDetailViewDTO synclassWorkDetail = synclassWorkStuService.synclassWorkFinishDetail(workId, studentId, schoolId);
        // 同步课堂作业基本信息
        BasicWorkInfoViewDTO basicWorkInfo = synclassWorkDetail.getBasicWorkInfo();
        // 教师录音信息
        WorkTapeFilesDTO workTapeFilesDTO = synclassWorkDetail.getWorkTapeFilesDTO();
        // 同步课堂游戏信息
        List<SynclassWorkGameInfoViewDTO> synclassGameInfo = synclassWorkDetail.getSynclassGameInfo();
        // 学生作业回答信息
        SynclassWorkStudentDTO stuAnswerInfo = synclassWorkDetail.getStuAnswerInfo();
        // 同步课堂，学生完成游戏详细
        List<SynclassWorkGamePlayInfoViewDTO> stuGamePlayResult = synclassWorkDetail.getStuGamePlayResult();

        // 计算得到最晚提交时间(1,3,5天)
        long lastCommitRange = basicWorkInfo.getEndTime().getTime() - basicWorkInfo.getPublishTime().getTime();
        long lastCommitDays = lastCommitRange / (24 * 60 * 60 * 1000);
        String subTime = null;
        Date subDate = stuAnswerInfo.getSubTime();
        if (subDate != null) {
            subTime = DateTranslate.getFormatStringDate(subDate, "yyyy年MM月dd日 HH:mm");
        }

        map.put("basicWorkInfo", basicWorkInfo);
        map.put("workTapeFilesDTO", workTapeFilesDTO);
        map.put("synclassGameInfo", synclassGameInfo);
        map.put("stuAnswerInfo", stuAnswerInfo);
        map.put("stuGamePlayResult", stuGamePlayResult);
        map.put("lastCommitDays", lastCommitDays);
        map.put("subTime", subTime);
        return "workmanage/student/writeSynClassWork";
    }

    /**
     * 返回同步课堂 作业交流
     *
     * @param map
     * @param workId
     * @return
     */
    @RequestMapping(value = "synclassWorkCommunication")
    public String synclassWorkCommunication(ModelMap map, String workId) {
        logger.info("[同步课堂作业作业交流初始化：]");
        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

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

        return "workmanage/student/synclassWorkCommunication";
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return 同步课堂作业，作业交流，我的作业置顶
     * @author hujx
     * @date 2015/07/21
     */
    @RequestMapping(value = "workCommunication")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentSynclassWorkCommunicationWrapper>> queryWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {
        logger.info("[进入同步课堂作业作业交流：]");
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String studentId = GetSessionContentUtil.getInstance().currentUserID();

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
     * @return 提交预习作业
     * @author hujx
     * @date 2015/07/12
     */
    @RequestMapping(value = "handInMySynclassWork")
    @ResponseBody
    public AjaxResponse handInMySynclassWork(String workId, String answerContext, String workFileJSON) {

        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        AjaxResponse response = new AjaxResponse();
        SynclassWorkStudentDTO synclassWorkStudentDTO = new SynclassWorkStudentDTO();

        synclassWorkStudentDTO.setWorkId(workId);
        synclassWorkStudentDTO.setContext(answerContext);
        synclassWorkStudentDTO.setUserId(studentId);
        synclassWorkStudentDTO.setSchoolId(schoolId);
        try {
            synclassWorkStuService.handInMySynclassWork(synclassWorkStudentDTO);
            response.setStatus("1");
        } catch (CloudteachException e) {
            response.setErrorMsg(e.getMsg());
            response.setStatus("0");
            logger.info(e.getMsg());
        }
        return response;
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

        synclassWorkStuService.praise(answerId, studentId, schoolId, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 取消点赞
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

        synclassWorkStuService.cancelPraise(answerId, studentId, schoolId, 2);
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

        String commentId = synclassWorkStuService.comment(answerId, studentId, schoolId, context, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(commentId);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 删除评论
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

        synclassWorkStuService.delComment(commentId, studentId, schoolId);
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

        List<WorkAnswerCommentViewDTO> moreCommentList = synclassWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}