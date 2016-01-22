package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.web.common.DateTranslate;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.MagicWorkStuFinishWorkForm;
import net.xuele.cloudteach.web.form.MagicWorkSubmitForm;
import net.xuele.cloudteach.web.wrapper.StudentMagicWorkCommunicationWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;
import org.slf4j.Logger;
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
 * MagicWorkStuController
 * 学生提分宝作业管理
 *
 * @author panglx
 * @date 2015/7/7 0002
 */
@Controller
@RequestMapping(value = "magic/work/student")
public class MagicWorkStuController {

    private static Logger logger = LoggerFactory.getLogger(MagicWorkStuController.class);

    @Autowired
    StudentCloudWorkService studentCloudWorkService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    WorkStatisticsService workStatisticsService;
    @Autowired
    private MagicWorkStuService magicWorkStuService;//学生提分宝作业服务

    @Autowired
    private MagicWorkService magicWorkService;
    @Autowired
    private WorkTapeFilesService workTapeFilesService;
    @Autowired
    private MagicQuestionService magicQuestionService;

    /**
     * @param map 【请求参数】
     * @return 学生作业
     */
    @RequestMapping(value = "index")
    public String index(ModelMap map) {
        map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
        map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
        map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());
        return "";
    }

    /**
     * 提分宝作业(未交作业前挑战)
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "workDetail")
    public String workItemDetail(ModelMap map, String workId) {
        //workId = "44fb90df727c4944bd6fba10db109f1a";
        //bankId = "7109f5a13ccf11e5b032005056a70232";
        String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        logger.info("[学生进入作业详情页，对查看状态和查看时间初始化：]");
        workStatisticsService.initStudentGatherStuView(workId, userId, schoolId);
        //获取提分宝作业信息
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = magicWorkService.queryMagicWorkDetailByWorkId(workId, schoolId);
        String bankId = magicWorkDetailViewDTO.getBankId();
        //获取提分宝作业录音信息
        WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);
        //获取提分宝作业题目信息
        MagicWorkQueDetailDTO magicWorkQueDetailDTO = magicWorkStuService.queryMagicQueDetail(null, workId, bankId, userId, schoolId, 1);

        long n = DateTranslate.getFormatDate(magicWorkDetailViewDTO.getEndTime(), "yyyy-MM-dd").getTime()
                - DateTranslate.getFormatDate(magicWorkDetailViewDTO.getPublishTime(), "yyyy-MM-dd").getTime();
        long day = n / (1000 * 60 * 60 * 24);//最晚提交时间（几天内）
        map.put("magicWorkQueDetailDTO", magicWorkQueDetailDTO);
        map.put("magicWorkDetailViewDTO", magicWorkDetailViewDTO);
        map.put("workTapeFilesDTO", workTapeFilesDTO);//录音附件信息
        map.put("userId", userId);
        map.put("schoolId", schoolId);
        map.put("day", day);
        map.put("orderNum", magicWorkQueDetailDTO.getMagicQueViewDTOs().get(0).getOrderNum());
        return "workmanage/student/magic";
    }

    /**
     * 提分宝作业(提交作业后挑战)
     *
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "magicChallenge")
    public String magicDetail(ModelMap map, String workId) {
        //workId = "44fb90df727c4944bd6fba10db109f1a";
        // bankId = "7109f5a13ccf11e5b032005056a70232";
        String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //作业回答详情dto
        MagicSubmitInfoDTO magicSubmitInfo = magicWorkStuService.queryMagicSubmitInfo(workId, userId, schoolId);
        //获取提分宝作业信息
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = magicWorkService.queryMagicWorkDetailByWorkId(workId, schoolId);
        String bankId = magicWorkDetailViewDTO.getBankId();
        //获取提分宝作业录音信息
        WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);
        //获取提分宝作业题目信息
        int orderNum = magicSubmitInfo.getOrderNum();
        List<MagicWorkChallengeQueViewDTO> magicQuestionViewDTO = magicWorkStuService.getChallengeAnsInfo(bankId, orderNum, magicSubmitInfo.getStuAnswerInfo().getChallengeId(), schoolId);
        //完成时间
        String finishTime = DateTranslate.getFormatStringDate(magicSubmitInfo.getEndTime(), "yyyy年MM月dd日 HH:mm");
        long n = DateTranslate.getFormatDate(magicWorkDetailViewDTO.getEndTime(), "yyyy-MM-dd").getTime()
                - DateTranslate.getFormatDate(magicWorkDetailViewDTO.getPublishTime(), "yyyy-MM-dd").getTime();
        long day = n / (1000 * 60 * 60 * 24);//最晚提交时间（几天内）
        map.put("magicWorkDetailViewDTO", magicWorkDetailViewDTO);//作业信息
        map.put("workTapeFilesDTO", workTapeFilesDTO);//录音附件信息
        map.put("total", magicQuestionViewDTO.size());//题目数
        map.put("magicSubmitInfo", magicSubmitInfo);//学生回答信息
        map.put("queViewDTOs", magicQuestionViewDTO);//题目信息（包括学生回答对错）
        map.put("userId", userId);
        map.put("schoolId", schoolId);
        map.put("day", day);//最晚提交时间
        map.put("finishTime", finishTime);//完成时间
        map.put("orderNum", orderNum);//题目号
        return "/workmanage/student/magicDetail";
    }

    /**
     * 应用中心提分宝挑战
     *
     * @param map
     * @param bankId 题库id
     * @return
     */
    @RequestMapping(value = "appCenterMagic")
    public String appCenterMagic(ModelMap map, String bankId) {
        String workId = null;
        //bankId = "7109f5a13ccf11e5b032005056a70232";
        String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取提分宝作业题目信息
        MagicWorkQueDetailDTO magicWorkQueDetailDTO = magicWorkStuService.queryMagicQueDetail(null, workId, bankId, userId, schoolId, 1);
        //获取题库信息
        MagicQuestionBankDTO magicQuestionBankDTO = magicQuestionService.selectByPrimaryKey(bankId);

        map.put("magicWorkQueDetailDTO", magicWorkQueDetailDTO);
        map.put("userId", userId);
        map.put("schoolId", schoolId);
        map.put("bankId", bankId);
        map.put("unitId", magicQuestionBankDTO.getUnitId());
        map.put("orderNum", magicWorkQueDetailDTO.getOrderNum());
        return "workmanage/student/appCenterMagic";
    }

    /**
     * 返回提分宝 作业交流页面
     *
     * @param map
     * @param workId
     * @return
     */
    @RequestMapping(value = "magicWorkCommunication")
    public String magicWorkCommunication(ModelMap map, String workId) {
        logger.info("[提分宝作业作业交流初始化：]");
        String studentId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

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

        return "workmanage/student/magicWorkCommunication";
    }


    /**
     * @param studentWorkCommunicationPageRequest
     * @return 提分宝作业，作业交流，我的作业置顶
     * @author hujx
     * @date 2015/07/21
     */
    @RequestMapping(value = "workCommunication")
    @ResponseBody
    public AjaxResponse<PageResponse<StudentMagicWorkCommunicationWrapper>> queryWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) {

        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String studentId = GetSessionContentUtil.getInstance().currentUserID();

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
     * 返回提分宝挑战记录页面
     *
     * @param map
     * @param workId
     * @return
     */
    @RequestMapping(value = "challengeRecords")
    public String magicWorkChallengeRecord(ModelMap map, String workId) {
        logger.info("[提分宝挑战记录初始化：]");
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String studentId = GetSessionContentUtil.getInstance().currentUserID();

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

        return "workmanage/student/magicChallengeRecords";
    }

    /**
     * 学生提交提分宝作业
     *
     * @param magicWorkSubmitForm
     * @return
     */
    @RequestMapping(value = "submitWork")
    @ResponseBody
    public AjaxResponse submitWork(MagicWorkSubmitForm magicWorkSubmitForm) {

        /*************************测试数据**********************************/
        /*String workId = "362a2addb07c49888fdb206a81bae761";//作业id
        String userId = "265143524263";//学生id
        String schoolId = "SchoolIDQATestsm55";//学校id
        String challengeId = "23ba961fab344889ae568296b2fdc4e8";//挑战id
        String context = "我完成这个作业了，请批改！";//回答内容

        String magicWorkAnswerFilesJSON1 = "[{'uri':'http://192.169.1.1/file.jpg':'fileName':'答案1','extension':'jpg','size':1024}]";//学生回答附件json

        magicWorkSubmitForm.setUserId(userId);
        magicWorkSubmitForm.setWorkId(workId);
        magicWorkSubmitForm.setSchoolId(schoolId);
        magicWorkSubmitForm.setChallengeId(challengeId);
        magicWorkSubmitForm.setContext(context);
        magicWorkSubmitForm.setMagicWorkAnswerFilesJSON(magicWorkAnswerFilesJSON1);*/

        /*****************************************************************/
        //String magicWorkAnswerFilesJSON1 = "[{'uri':'926c4886d69e97e988c38a06040f71d8','fileName':'答案1','extension':'jpg','size':1024,'type':'1'}]";//学生回答附件json
        //magicWorkSubmitForm.setMagicWorkAnswerFilesJSON(magicWorkAnswerFilesJSON1);
        String userId = GetSessionContentUtil.getInstance().currentUserID();//用户id
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id
        AjaxResponse response = new AjaxResponse();
        MagicWorkSubmitFormDTO magicWorkSubmitFormDTO = new MagicWorkSubmitFormDTO();
        BeanUtils.copyProperties(magicWorkSubmitForm, magicWorkSubmitFormDTO);

        magicWorkSubmitFormDTO.setUserId(userId);
        magicWorkSubmitFormDTO.setSchoolId(schoolId);
        //调用服务提交提分宝作业
        magicWorkStuService.submitWork(magicWorkSubmitFormDTO);

        response.setStatus("1");
        return response;
    }

    /**
     * 开始作业时展示作业列表
     *
     * @param workId
     * @param bankId
     * @return
     */
    @RequestMapping(value = "beginWork")
    @ResponseBody
    public AjaxResponse beginWork(String workId, String bankId) {
        /*****************************************/
        workId = "091f580501444fb4866400b7c5a2c918";//作业id
        bankId = "12f55d9d35cf11e594d8fcaa14bfabcb";
        String schoolId = "SchoolIDQATestsm55";//学校id
        String userId = "265143512494";//学生id
        /***************************************************/
        AjaxResponse response = new AjaxResponse();
        //调用服务获取作业列表
        MagicQuestionViewDTO magicQuestionViewDTO = magicWorkStuService.getMagicWorkItems(workId, bankId, userId, schoolId);

        response.setStatus("1");
        response.setWrapper(magicQuestionViewDTO);
        return response;
    }

    /**
     * 学生分享作业回答
     *
     * @param challengeId
     * @return
     */
    @RequestMapping(value = "shareWorkAnswer")
    @ResponseBody
    public AjaxResponse shareWorkAnswer(String challengeId) {
        /*****************************************/
        String schoolId = "SchoolIDQATestsm55";//学校id
        /***************************************************/
        AjaxResponse response = new AjaxResponse();
        //调用服务获取作业列表
        int result = magicWorkStuService.shareWorkAnswer(challengeId, schoolId);
        if (result < 1) {
            response.setStatus("0");
        } else {
            response.setStatus("1");
        }

        return response;
    }

    /**
     * 完成作业并对完答案时新增挑战记录及相关表
     *
     * @param finishWorkForm
     * @return
     */
    @RequestMapping(value = "finishWork")
    @ResponseBody
    public AjaxResponse finishWork(MagicWorkStuFinishWorkForm finishWorkForm) {
        /**************************测试数据***********************************/
        /*String unitId = "030003001007038001001";
        String workId = "362a2addb07c49888fdb206a81bae761";
        String bankId = "12f55d9d35cf11e594d8fcaa14bfabcb";
        Integer orderNum = 0;
        String rmQueJSON = "[{'queId':'12f55d9d35cf11e594d8fcaa14bfab01','rwStatus':1},{'queId':'12f55d9d35cf11e594d8fcaa14bfab02','rwStatus':0}]";
        String beginTime1 = "1438736700238";
        String endTime1 = "1438736800890";
        String userId = "265143524263";//学生id
        String schoolId = "SchoolIDQATestsm55";//学校id、*/
        /*******************************************************************/
        String userId = GetSessionContentUtil.getInstance().currentUserID();//用户id
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id

        AjaxResponse response = new AjaxResponse();
        MagicWorkStuFinishWorkFormDTO finishWorkFormDTO = new MagicWorkStuFinishWorkFormDTO();
        BeanUtils.copyProperties(finishWorkForm, finishWorkFormDTO);
        finishWorkFormDTO.setUserId (userId);//用户id
        finishWorkFormDTO.setSchoolId(schoolId);//学校id
        //开始时间
        Date beginTime = new Date(Long.parseLong(finishWorkForm.getBeginTime() == null ? "0" : finishWorkForm.getBeginTime()));
        //完成时间
        Date endTime = new Date(Long.parseLong(finishWorkForm.getEndTime() == null ? "0" : finishWorkForm.getEndTime()));
        finishWorkFormDTO.setBeginTime(beginTime);
        finishWorkFormDTO.setEndTime(endTime);

        //调用服务获取作业列表
        response.setWrapper(magicWorkStuService.finishWork(finishWorkFormDTO));
        response.setStatus("1");
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

        magicWorkStuService.praise(answerId, studentId, schoolId, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 对学生的作业回答取消点赞处理
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

        magicWorkStuService.cancelPraise(answerId, studentId, schoolId, 2);
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

        String commentId = magicWorkStuService.comment(answerId, studentId, schoolId, context, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(commentId);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 对学生的作业回答评论处理
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

        magicWorkStuService.delComment(commentId, studentId, schoolId);
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

        List<WorkAnswerCommentViewDTO> moreCommentList = magicWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

}