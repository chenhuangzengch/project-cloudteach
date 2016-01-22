package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.MagicQuestionService;
import net.xuele.cloudteach.service.MagicWorkService;
import net.xuele.cloudteach.service.MagicWorkStuService;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.cloudteach.web.common.DateEditor;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.MagicWorkForm;
import net.xuele.cloudteach.web.wrapper.MagicQuestionDetailWrapper;
import net.xuele.cloudteach.web.wrapper.MagicWorkListWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.constant.IdentityIdConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MagicWorkController
 * 教师提分宝作业管理
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
@Controller
@RequestMapping(value = "magic/work")
public class MagicWorkController {

    private static Logger logger = LoggerFactory.getLogger(MagicWorkController.class);

    @Autowired
    private MagicWorkService magicWorkService;
    @Autowired
    private MagicWorkStuService magicWorkStuService;
    @Autowired
    private MagicQuestionService magicQuestionService;
    @Autowired
    private WorkTapeFilesService workTapeFilesService;


    /**
     * @param map 【请求参数】
     * @return 提分宝首页
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/teacher/initWorkPage");

        logger.info("magic/work/index:提分宝首页");
        map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
        map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
        map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());
        /**
         * 获取教师所授课本是否能布置提分宝作业与同步课堂作业
         * 1小学语数英(可以布置同步课堂) 2初中数理化（可以布置提分宝） 0其他
         */
        map.put("periodType", GetSessionContentUtil.getInstance().getTeacherPeriodType());
        /**
         * 获取教师所授课本对应科目ID
         * 如果是030则显示口语作业  否则不显示
         */
        map.put("subjectId", GetSessionContentUtil.getInstance().getTeachersSubjectId());

        return "/magic/work/index";
    }


    /**
     * 提分宝作业详情--完成详情--初始化
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "initDetail")
    public String initDetail(ModelMap map, String workId,String classId) {
        logger.info("magic/work/initDetail:提分宝作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取作业对应班级信息
        List<WorkClassViewDTO> magicWorkClassList = magicWorkService.queryMagicWorkClassList(workId, schoolId);
        //String classId = "";
        if (magicWorkClassList != null && magicWorkClassList.size() > 0) {
            if (StringUtils.isEmpty(classId)) {
                classId = magicWorkClassList.get(0).getClassId();
            }
        }
        //获取提分宝作业信息
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = magicWorkService.queryMagicWorkDetailByWorkId(workId, schoolId);
        if (magicWorkDetailViewDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业信息为空", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (magicWorkDetailViewDTO.getEndTime().getTime() < (new Date()).getTime()) {
            map.put("workEndStatus", 1);
        } else {
            map.put("workEndStatus", 0);
        }

        map.put("magicWorkClassList", magicWorkClassList);
        map.put("workId", workId);
        map.put("classId", classId);
        map.put("workId", workId);
        return "magic/work/twmDetail";
    }

    /**
     * 根据提分宝作业ID获取未提交作业回答的学生信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "unSubStudentList")
    @ResponseBody
    public AjaxResponse queryUnSubStudentList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("magic/work/unSubStudentList:获取未提交作业回答的学生信息,workId:" + workId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkUnSubStudentViewDTO> unSubStudentList = magicWorkService.selectUnSubStudentList(workId, classId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(unSubStudentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 根据提分宝作业ID获取已提交学生作业信息
     *
     * @param workId  作业ID【请求参数】
     * @param classId 班级ID【请求参数】
     * @return AjaxResponse
     */
    @RequestMapping(value = "subedAnswerList")
    @ResponseBody
    public AjaxResponse querySubedAnswerList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("magic/work/subedAnswerList:获取已提交学生作业信息,workId:" + workId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerViewDTO> subedAnswerList = magicWorkService.querySubedWorkAnswerList(workId, classId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 获取提分宝作业下某个班级的统计信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "teacherWorkSR")
    public String queryTeacherWorkSR(ModelMap map, @RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("magic/work/teacherWorkSR:获取预习作业下某个班级的统计信息,workId:" + workId + ",classId" + classId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        SRTeacherWorkDTO sRTeacherWorkDTO = magicWorkService.getMagicWorkSR(schoolId, classId, workId);

        //获取作业对应班级信息
        List<WorkClassViewDTO> magicWorkClassList = magicWorkService.queryMagicWorkClassList(workId, schoolId);

        map.put("sRTeacherWorkDTO", sRTeacherWorkDTO);
        map.put("magicWorkClassList", magicWorkClassList);
        map.put("workId", workId);
        map.put("classId", classId);
        return "magic/work/teacherWorkSR";
    }

    /**
     * 提分宝作业详情--作业题目
     *
     * @param map
     * @param workId  作业ID
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "workItemDetail")
    public String workItemDetail(ModelMap map, String workId, String classId) {
        logger.info("magic/work/subedAnswerList:获取提分宝作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取提分宝作业信息
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = magicWorkService.queryMagicWorkDetailByWorkId(workId, schoolId);
        //获取提分宝作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId, schoolId);
        //获取提分宝题库信息
        MagicQuestionBankDTO magicQuestionBankDTO = magicQuestionService.selectByPrimaryKey(magicWorkDetailViewDTO.getBankId());
        //获取提分宝作业题目信息
        List<MagicQuestionDTO> magicQuestionDTOList = magicQuestionService.queryMagicQuestionMasterListByBankId(magicWorkDetailViewDTO.getBankId());

        WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
        if (workTapeFilesList != null && workTapeFilesList.size() > 0) {
            workTapeFilesDTO = workTapeFilesList.get(0);
        }

        int itemNum = 0;
        if (magicQuestionDTOList != null) {
            itemNum = magicQuestionDTOList.size();
        }

        map.put("magicWorkDetailViewDTO", magicWorkDetailViewDTO);
        map.put("workTapeFilesDTO", workTapeFilesDTO);
        map.put("magicQuestionDTOList", magicQuestionDTOList);
        map.put("magicQuestionBankDTO", magicQuestionBankDTO);
        map.put("itemNum", Integer.toString(itemNum));
        map.put("classId", classId);
        map.put("workId", workId);
        return "magic/work/twmWorkInfo";
    }

    /**
     * 根据提分宝作业ID发通知提醒所有未提交作业的学生
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "warnSub")
    @ResponseBody
    public AjaxResponse warnSub(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("magic/work/warnSub:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId:" + classId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String userIcon = GetSessionContentUtil.getInstance().currentUserIcon();
        String userName = GetSessionContentUtil.getInstance().currentUserName();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
        if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
            userName = "学校管理员";
        } else {
            userName = userName + "老师";
        }
        int warnSta = magicWorkService.warnSub(workId, classId, schoolId, userId, userIcon, userName, 2);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if (warnSta == 0) {
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(Constants.WARN_SUB_WORK_BUSY);
        }
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 教师点赞answerId对应的作业回答
     */
    @RequestMapping(value = "praise")
    @ResponseBody
    public AjaxResponse praise(@RequestParam("answerId") String answerId) {
        logger.info("magic/work/praise:教师点赞学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        magicWorkService.praise(answerId, userId, schoolId, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 教师取消点赞answerId对应的作业回答
     */
    @RequestMapping(value = "unPraise")
    @ResponseBody
    public AjaxResponse unPraise(@RequestParam("answerId") String answerId) {
        logger.info("magic/work/unPraise:教师取消点赞学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        magicWorkService.unpraise(answerId, userId, schoolId, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 教师点评answerId对应的作业回答,点评视为批改作业
     */
    @RequestMapping(value = "comment")
    @ResponseBody
    public AjaxResponse comment(@RequestParam("answerId") String answerId, @RequestParam("context") String context) {
        logger.info("magic/work/comment:教师点评学生作业,answerId:" + answerId + ",context:" + context);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String commentId = magicWorkService.comment(answerId, userId, schoolId, context, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(commentId);
        return ajaxResponse;
    }

    /**
     * @param commentId 评论ID
     * @return AjaxResponse
     * 删除预习作业中的某条评论
     */
    @RequestMapping(value = "delComment")
    @ResponseBody
    public AjaxResponse delComment(@RequestParam("commentId") String commentId) {
        logger.info("magic/work/delComment:教师删除评论,commentId:" + commentId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        magicWorkService.delCommont(commentId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 获取更多评论
     */
    @RequestMapping(value = "moreComment")
    @ResponseBody
    public AjaxResponse moreComment(@RequestParam("answerId") String answerId) {
        logger.info("magic/work/moreComment:获取更多评论,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerCommentViewDTO> moreCommentList = magicWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /***************************************布置提分宝作业涉及controller****************************************************/
    /**
     * 发布提分宝作业
     *
     * @param magicWorkForm
     * @return
     */
    @RequestMapping(value = "addMagicWork")
    public String addMagicWork(ModelMap map, MagicWorkForm magicWorkForm) {
        logger.info("magic/work/addMagicWork:发布提分宝作业,magicWorkForm");
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String result = "/magic/work/success";
        String msgTitle = "发送成功";
        String msgContent = "作业已成功发送给指定班级，您可以在作业管理中检查学生作业";

        MagicWorkDTO magicWorkDTO = new MagicWorkDTO();//提分宝作业dto
        try {
            BeanUtils.copyProperties(magicWorkForm, magicWorkDTO);
            String time = magicWorkForm.getPublishTime();
            //发布时间
            Date publishTime = DateEditor.getInstance().getPublishTime(time.isEmpty() ? "0" : time);

            magicWorkDTO.setPublishTime(publishTime);//发布时间
            //计算作业最晚提交时间
            magicWorkDTO.setEndTime(DateEditor.getInstance().getEndDime(
                    publishTime,//作业发布时间
                    magicWorkForm.getLastCommitRange())//最晚提交时间（1,3,5）
            );
            magicWorkDTO.setUserId(userId);
            magicWorkDTO.setSchoolId(schoolId);//学校id

            List<String> classList = magicWorkForm.getClassList();//班级

            WorkTapeFilesDTO tapeFilesDTO = null;
            if (magicWorkForm.getWorkTapeFiles() != null) {
                tapeFilesDTO = new WorkTapeFilesDTO();//教师录音附件
                BeanUtils.copyProperties(magicWorkForm.getWorkTapeFiles(), tapeFilesDTO);
            }
            //service参数封装
            MagicWorkFormDTO magicWorkFormDTO = new MagicWorkFormDTO();
            magicWorkFormDTO.setMagicWorkDTO(magicWorkDTO);
            magicWorkFormDTO.setClassList(classList);
            magicWorkFormDTO.setTapeFilesDTO(tapeFilesDTO);
            magicWorkFormDTO.setClassJosn(magicWorkForm.getClassJson());
            //调用服务布置提分宝作业
            logger.info("[PC-WEB端==>]布置提分宝作业：" + magicWorkFormDTO);
            String response = magicWorkService.addMagicWork(magicWorkFormDTO);
            response = StringUtils.isEmpty(response) ? null : response;
            if (response != null) {
                result = "/magic/work/failed";
                msgTitle = "发送失败";
                msgContent = response + "，请重新发送";
            }
        } catch (Exception e) {
            result = "/magic/work/failed";
            msgTitle = "发送失败";
            msgContent = "发送时遇到一个小问题，请重新发送";
        }

        map.put("msgTitle", msgTitle);
        map.put("msgContent", msgContent);


        return result;
    }

    /**
     * 提分宝展示某一课程下的题库
     *
     * @param unitId
     * @return
     */
    @RequestMapping(value = "showMagicWorkList")
    @ResponseBody
    public AjaxResponse<List<MagicWorkListWrapper>> showMagicWorkList(String unitId) {
        logger.info("magic/work/showMagicWorkList:提分宝展示某一课程下的题库,unitId=" + unitId);
        String extraBookId = GetSessionContentUtil.getInstance().currentExtraBookId();
        AjaxResponse<List<MagicWorkListWrapper>> ajaxResponse = new AjaxResponse<>();
        //调用服务查询对应课程下的题库信息
        List<MagicWorkListDTO> magicWorkListDTOs = magicWorkService.showMagicWorkList(unitId, extraBookId);
        //DTO转wrapper
        List<MagicWorkListWrapper> magicWorkListWrappers = new ArrayList<>();//题库信息列表
        MagicWorkListWrapper wrapper;
        for (MagicWorkListDTO dto : magicWorkListDTOs) {
            wrapper = new MagicWorkListWrapper();
            BeanUtils.copyProperties(dto, wrapper);
            //题目信息DTO
            List<MagicQuestionDTO> questionDTOs = dto.getMagicQuestionDTO();
            //题目信息wrapper
            List<MagicQuestionDetailWrapper> detailWrappers = new ArrayList<>();
            MagicQuestionDetailWrapper detailWrapper;
            //提分宝习题讲解信息
            MagicQuestionVideoDTO magicQuestionVideoDTO;
            for (MagicQuestionDTO q : questionDTOs) {
                detailWrapper = new MagicQuestionDetailWrapper();
                BeanUtils.copyProperties(q, detailWrapper);
                if (q.getOrderNum() == 0) {
                    magicQuestionVideoDTO = magicWorkService.getMagicQueVideoByQueId(q.getQueId(), 1);
                } else {
                    magicQuestionVideoDTO = magicWorkService.getMagicQueVideoByQueId(q.getOriginalQuestionId(), 1);
                }
                if (magicQuestionVideoDTO != null) {//如果有视频讲解
                    detailWrapper.setVideoDesc(magicQuestionVideoDTO.getVideoDesc());
                    detailWrapper.setUrl(magicQuestionVideoDTO.getVideoFileKey());
                } else {
                    detailWrapper.setVideoDesc("");
                    detailWrapper.setUrl("");
                }
                detailWrappers.add(detailWrapper);
            }
            wrapper.setMagicQuestionDetailWrappers(detailWrappers);
            magicWorkListWrappers.add(wrapper);
        }
        ajaxResponse.setWrapper(magicWorkListWrappers);
        ajaxResponse.setStatus("1");//成功返回1
        return ajaxResponse;
    }

    /**
     * 布置作业
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "publish")
    public String publish(ModelMap map, String bankId, String classes, int releases) {
        logger.info("magic/work/publish:跳到布置作业页面,bankId=" + bankId + ",classes=" + classes + ",releases=" + releases);
        //调用服务获取某个题库下的题目信息
        List<MagicQuestionDTO> magicQuestionDTOs = magicQuestionService.queryMagicQuestionMasterListByBankId(bankId);
        List<MagicQuestionDetailWrapper> magicQuestionDetailWrappers = new ArrayList<>();
        for (MagicQuestionDTO dto : magicQuestionDTOs) {
            MagicQuestionDetailWrapper detailWrapper = new MagicQuestionDetailWrapper();
            BeanUtils.copyProperties(dto, detailWrapper);
            magicQuestionDetailWrappers.add(detailWrapper);
        }
        MagicWorkListWrapper wrapper = new MagicWorkListWrapper();

        wrapper.setBankId(bankId);
        wrapper.setClasses(classes);

        wrapper.setMagicQuestionDetailWrappers(magicQuestionDetailWrappers);
        wrapper.setReleases(releases);
        map.put("magicWorkListWrapper", wrapper);
        return "/magic/work/publish";
    }

    /**
     * 查看题目详情
     *
     * @param queId
     * @return
     */
    @RequestMapping(value = "viewDetailQue")
    @ResponseBody
    public AjaxResponse<MagicQuestionDetailWrapper> viewDetailQue(String queId) {
        logger.info("magic/work/viewDetailQue:查看题目详情,queId=" + queId);
        AjaxResponse<MagicQuestionDetailWrapper> ajaxResponse = new AjaxResponse<>();
        //调用服务获取题目信息dto
        MagicQuestionDTO detailDTO = magicWorkService.viewDetailQue(queId);
        //DTO转wrpper
        MagicQuestionDetailWrapper wrapper = new MagicQuestionDetailWrapper();
        BeanUtils.copyProperties(detailDTO, wrapper);

        ajaxResponse.setWrapper(wrapper);
        ajaxResponse.setStatus("1");//成功返回1
        return ajaxResponse;
    }

    /**
     * 编辑发布的提分宝作业
     *
     * @param magicWorkDTO
     * @param classList
     * @return
     */
    @RequestMapping(value = "modifyMagicWork")
    @ResponseBody
    public AjaxResponse modifyMagicWork(MagicWorkDTO magicWorkDTO, List<String> classList) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        //调用服务编辑提分宝作业
        //magicWorkService.modifyMagicWork(magicWorkDTO, classList);

        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 删除发布的提分宝作业
     *
     * @param workId
     * @return
     */
    @RequestMapping(value = "delMagicWork")
    @ResponseBody
    public AjaxResponse delMagicWork(String workId) {
        logger.info("magic/work/delMagicWork:删除发布的提分宝作业,workId=" + workId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //String schoolId = "SchoolIDQATestsm55";
        AjaxResponse ajaxResponse = new AjaxResponse();
        //调用服务删除发布的提分宝作业
        magicWorkService.delMagicWork(workId, schoolId);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 删除单个学生提交的作业
     */
    @RequestMapping(value = "delStuWork")
    @ResponseBody
    public AjaxResponse delStuWork(@RequestParam("answerId") String answerId) {
        logger.info("magic/work/delMagicWork:删除单个学生提交的作业,answerId=" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        magicWorkService.delStuAnswer(userId, answerId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}