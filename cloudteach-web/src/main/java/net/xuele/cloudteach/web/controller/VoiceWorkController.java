package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.TeacherWorkService;
import net.xuele.cloudteach.service.TeacherWorkStuService;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.cloudteach.web.common.DateEditor;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.AddWorkForm;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.constant.IdentityIdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * VoiceWorkController
 * 语音作业管理
 *
 * @author hujx
 * @date 2015/7/27 0027
 */

@Controller
@RequestMapping(value = "voice/work")
public class VoiceWorkController {

    private static Logger logger = LoggerFactory.getLogger(VoiceWorkController.class);
    @Autowired
    private TeacherWorkService teacherWorkService;
    @Autowired
    private WorkTapeFilesService workTapeFilesService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    CloudTeachService cloudTeachService;

    /**
     * @param map      [请求参数]
     * @param itemList [作业题目列表]
     *                 发布口语作业初始化
     * @return
     */
    @RequestMapping(value = "toPublish")
    public String toPublish(ModelMap map, @RequestParam(value = "itemList") List<String> itemList) {
        logger.info("voice/work/toPublish:发布口语作业初始化,itemList:" + itemList);
        for (String itemId : itemList) {
            if (null == itemId || "".equals(itemId)) {
                itemList.remove(itemId);
            }
        }
        map.put("itemList", itemList);
        return "/voice/work/publish";
    }

    /**
     * @param addWorkForm 【请求参数】
     * @return 发布口语作业
     */
    @RequestMapping(value = "addVoiceWork")
    public String addVoiceWork(ModelMap map, AddWorkForm addWorkForm) {
        logger.info("voice/work/addVoiceWork:发布口语作业,addWorkForm:" + addWorkForm);
        String retURL;
        String retString = null;

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userIcon = GetSessionContentUtil.getInstance().currentUserIcon();
        String userName = GetSessionContentUtil.getInstance().currentUserName();
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();
        String positionName = GetSessionContentUtil.getInstance().getPositionName();

        AjaxResponse ajaxResponseAddWork = new AjaxResponse();
        TeacherWorkAddViewDTO teacherWorkAddViewDTO = new TeacherWorkAddViewDTO();

        WorkTapeFilesDTO workTapeFilesDTO = null;
        if (addWorkForm.getWorkTapeFiles() != null) {
            workTapeFilesDTO = new WorkTapeFilesDTO();
            BeanUtils.copyProperties(addWorkForm.getWorkTapeFiles(), workTapeFilesDTO);
        }

        String publishTimeStamp = addWorkForm.getPublishTime();
        int lastCommitRange = addWorkForm.getLastCommitRange();
        Date publishTime = DateEditor.getInstance().getPublishTime(publishTimeStamp);
        Date endTime = DateEditor.getInstance().getEndDime(publishTime, lastCommitRange);

        String unitId = addWorkForm.getUnitId();
        BookDTO book = cloudTeachService.getBookByUnit(unitId);
        // 只允许英语课程布置口语作业(目前只在controller层限制)
        if (book == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        } else {
            String subjectId = book.getSubjectId();
            if (!"030".equals(subjectId)) {
                CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.VOICE_ENGLISH_ONLY);
            }
        }

        teacherWorkAddViewDTO.setAreaId(areaId);
        teacherWorkAddViewDTO.setUserName(userName);
        teacherWorkAddViewDTO.setUserIcon(userIcon);
        teacherWorkAddViewDTO.setPositionName(positionName);
        teacherWorkAddViewDTO.setUserId(userId);
        teacherWorkAddViewDTO.setSchoolId(schoolId);
        teacherWorkAddViewDTO.setUnitId(unitId);
        teacherWorkAddViewDTO.setPublishTime(publishTime);
        teacherWorkAddViewDTO.setEndTime(endTime);
        teacherWorkAddViewDTO.setItemList(addWorkForm.getItemList());
        teacherWorkAddViewDTO.setClassList(addWorkForm.getClassList());
        teacherWorkAddViewDTO.setClassInfoJSON(addWorkForm.getClassJson());

        try {
            retString = teacherWorkService.addHomework(teacherWorkAddViewDTO, workTapeFilesDTO, null);
            ajaxResponseAddWork.setStatus("1");
            retURL = "/voice/work/success";
        } catch (CloudteachException e) {
            ajaxResponseAddWork.setErrorMsg(e.getMsg());
            ajaxResponseAddWork.setStatus("0");
            map.put("error", e.getMsg());
            retURL = "/voice/work/fail";
        }

        if (retString.equals("") || retString == null) {
            retURL = "/voice/work/success";
        } else {
            map.put("error", retString);
            retURL = "/voice/work/fail";
        }
        return retURL;
    }


    @RequestMapping(value = "delVoiceWork")
    @ResponseBody
    public AjaxResponse delVoiceWork(String workId) {
        logger.info("voice/work/delVoiceWork:删除口语作业,workId:" + workId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        int i = teacherWorkService.delHomework(workId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(i);
        ajaxResponse.setStatus("1");
        return ajaxResponse;

    }

    /**
     * 口语作业详情--完成详情--初始化
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "initDetail")
    public String initDetail(ModelMap map, String workId, String classId) {
        logger.info("voice/work/initDetail:口语作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取作业对应班级信息
        List<WorkClassViewDTO> voiceWorkClassList = teacherWorkService.queryTeacherWorkClassList(workId, schoolId);
        //String classId = "";
        if (voiceWorkClassList != null && voiceWorkClassList.size() > 0) {
            if (StringUtils.isEmpty(classId)) {
                classId = voiceWorkClassList.get(0).getClassId();
            }
        }
        //获取作业对应口语题目信息
        List<TeacherWorkItemDTO> voiceWorkItemList = teacherWorkService.queryTeacherWorkItemList(workId, schoolId);

        //当前业务规定一份作业只包含一个题目
        TeacherWorkItemDTO voiceWorkItemDTO = new TeacherWorkItemDTO();
        if (voiceWorkItemList != null && voiceWorkItemList.size() > 0) {
            voiceWorkItemDTO = voiceWorkItemList.get(0);
        }
        //获取口语作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        if (teacherWorkDetailViewDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业信息为空", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (teacherWorkDetailViewDTO.getEndTime().getTime() < (new Date()).getTime()) {
            map.put("workEndStatus", 1);
        } else {
            map.put("workEndStatus", 0);
        }
        map.put("voiceWorkClassList", voiceWorkClassList);
        map.put("voiceWorkItemDTO", voiceWorkItemDTO);
        map.put("classId", classId);
        map.put("workId", workId);
        return "voice/work/twmDetail";
    }

    /**
     * 根据口语作业ID获取未提交作业回答的学生信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "unSubStudentList")
    @ResponseBody
    public AjaxResponse queryUnSubStudentList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("voice/work/unSubStudentList:获取未提交作业回答的学生信息,workId:" + workId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkUnSubStudentViewDTO> unSubStudentList = teacherWorkService.selectUnSubStudentList(workId, classId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(unSubStudentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 根据口语作业ID、口语题目ID获取已提交学生作业信息
     *
     * @param workId     作业ID
     * @param workItemId 口语题目ID
     * @param classId    班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "subedAnswerList")
    @ResponseBody
    public AjaxResponse querySubedAnswerList(@RequestParam("workId") String workId,
                                             @RequestParam("workItemId") String workItemId,
                                             String classId) {
        logger.info("voice/work/subedAnswerList:获取已提交学生作业信息,workId:" + workId + ",workItemId:" + workItemId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerViewDTO> subedAnswerList = teacherWorkService.querySubedWorkAnswerList(workId, workItemId, classId, schoolId, userId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 获取口语作业下某个班级的统计信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "teacherWorkSR")
    public String queryTeacherWorkSR(ModelMap map, @RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("voice/work/teacherWorkSR:获取预习作业下某个班级的统计信息,workId:" + workId + ",classId" + classId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        SRTeacherWorkDTO sRTeacherWorkDTO = teacherWorkService.getTeacherWorkSR(schoolId, classId, workId, 7);

        //获取作业对应班级信息
        List<WorkClassViewDTO> voiceWorkClassList = teacherWorkService.queryTeacherWorkClassList(workId, schoolId);

        map.put("sRTeacherWorkDTO", sRTeacherWorkDTO);
        map.put("voiceWorkClassList", voiceWorkClassList);
        map.put("workId", workId);
        map.put("classId", classId);
        return "voice/work/teacherWorkSR";
    }

    /**
     * 口语作业详情--作业题目
     *
     * @param map
     * @param workId  作业ID
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "workItemDetail")
    public String workItemDetail(ModelMap map, String workId, String classId) {
        logger.info("voice/work/workItemDetail:获取口语作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取口语作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        //获取口语作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId, schoolId);
        //获取口语作业题目信息
        List<TeacherWorkItemViewDTO> itemContainFilesList = teacherWorkService.queryItemContainFilesList(workId, schoolId);

        TeacherWorkItemViewDTO teacherWorkItemViewDTO = new TeacherWorkItemViewDTO();
        if (itemContainFilesList != null && itemContainFilesList.size() > 0) {
            teacherWorkItemViewDTO = itemContainFilesList.get(0);
        }

        WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
        if (workTapeFilesList != null && workTapeFilesList.size() > 0) {
            workTapeFilesDTO = workTapeFilesList.get(0);
        }

        map.put("teacherWorkDetailViewDTO", teacherWorkDetailViewDTO);
        map.put("workTapeFilesDTO", workTapeFilesDTO);
        map.put("teacherWorkItemViewDTO", teacherWorkItemViewDTO);
        map.put("classId", classId);
        map.put("workId", workId);
        return "voice/work/twmWorkInfo";
    }

    /**
     * 根据口语作业ID发通知提醒所有未提交作业的学生
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "warnSub")
    @ResponseBody
    public AjaxResponse warnSub(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("voice/work/warnSub:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId:" + classId);
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
        int warnSta = teacherWorkService.warnSub(workId, classId, schoolId, userId, userIcon, userName, 7);
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
        logger.info("voice/work/praise:教师点赞学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.praise(answerId, userId, schoolId, 1);
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
        logger.info("voice/work/unPraise:教师取消点赞学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.unpraise(answerId, userId, schoolId, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 点评answerId对应的作业回答,点评视为批改作业
     */
    @RequestMapping(value = "comment")
    @ResponseBody
    public AjaxResponse comment(@RequestParam("answerId") String answerId, @RequestParam("context") String context) {
        logger.info("voice/work/comment:教师点评生作业,answerId:" + answerId + ",context:" + context);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String commentId = teacherWorkService.comment(answerId, userId, schoolId, context, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(commentId);
        return ajaxResponse;
    }

    /**
     * @param commentId 评论ID
     * @return AjaxResponse
     * 删除口语作业中的某条评论
     */
    @RequestMapping(value = "delComment")
    @ResponseBody
    public AjaxResponse delComment(@RequestParam("commentId") String commentId) {
        logger.info("voice/work/delComment:教师删除评论,commentId:" + commentId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.delCommont(commentId, userId, schoolId);
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
        logger.info("voice/work/moreComment:获取更多评论,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerCommentViewDTO> moreCommentList = teacherWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @return AjaxResponse
     * 评分answerId对应的作业回答，评分视为批改作业
     */
    @RequestMapping(value = "score")
    @ResponseBody
    public AjaxResponse score(@RequestParam("answerId") String answerId, @RequestParam("score") Integer score) {
        logger.info("voice/work/score:教师给学生作业打分,answerId:" + answerId + ",score" + score);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.score(answerId, userId, schoolId, score);
        AjaxResponse ajaxResponse = new AjaxResponse();
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
        logger.info("voice/work/delStuWork:教师删除学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.delStuWork(answerId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}
