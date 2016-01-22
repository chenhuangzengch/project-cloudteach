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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GuidanceWorkController
 * 教师预习作业管理
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
@Controller
@RequestMapping(value = "guidance/work")
public class GuidanceWorkController {

    private static Logger logger = LoggerFactory.getLogger(GuidanceWorkController.class);

    @Autowired
    private WorkTapeFilesService workTapeFilesService;
    @Autowired
    private TeacherWorkService teacherWorkService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    CloudTeachService cloudTeachService;

    /**
     * @param map 【请求参数】
     * @return 发布预习作业
     */
//    @RequestMapping(value = "publish")
//    public String publish(ModelMap map) {
//        map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
//        map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
//        map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());
//        return "/guidance/work/publish";
//    }

    /**
     * @param map      [请求参数]
     * @param itemList [作业题目列表]
     *                 发布预习作业初始化
     * @return
     */
    @RequestMapping(value = "toPublish")
    public String toPublish(ModelMap map, @RequestParam(value = "itemList") List<String> itemList) {
        logger.info("guidance/work/toPublish:发布预习作业初始化,itemList:" + itemList);
        for (String itemId : itemList) {
            if (null == itemId || "".equals(itemId)) {
                itemList.remove(itemId);
            }
        }
        map.put("itemList", itemList);
        return "/guidance/work/publish";
    }

    /**
     * GuidanceWorkController
     * 教师布置预习作业
     *
     * @author hujx
     * @date 2015/7/7 0007
     */
    @RequestMapping(value = "addGuidanceWork")
    public String addPrepWork(ModelMap map, AddWorkForm addWorkForm) {
        logger.info("guidance/work/addGuidanceWork:发布预习作业,addWorkForm:" + addWorkForm);
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

        teacherWorkAddViewDTO.setAreaId(areaId);
        teacherWorkAddViewDTO.setUserName(userName);
        teacherWorkAddViewDTO.setUserIcon(userIcon);
        teacherWorkAddViewDTO.setPositionName(positionName);
        teacherWorkAddViewDTO.setUserId(userId);
        teacherWorkAddViewDTO.setSchoolId(schoolId);
        teacherWorkAddViewDTO.setUnitId(addWorkForm.getUnitId());
        teacherWorkAddViewDTO.setPublishTime(publishTime);
        teacherWorkAddViewDTO.setEndTime(endTime);
        teacherWorkAddViewDTO.setItemList(addWorkForm.getItemList());
        teacherWorkAddViewDTO.setClassList(addWorkForm.getClassList());
        teacherWorkAddViewDTO.setClassInfoJSON(addWorkForm.getClassJson());

        try {
            retString = teacherWorkService.addHomework(teacherWorkAddViewDTO, workTapeFilesDTO, null);
            ajaxResponseAddWork.setStatus("1");
            retURL = "/guidance/work/success";
        } catch (CloudteachException e) {
            ajaxResponseAddWork.setErrorMsg(e.getMsg());
            ajaxResponseAddWork.setStatus("0");
            map.put("error", e.getMsg());
            retURL = "/guidance/work/fail";
        }

        if (retString.equals("") || retString == null) {
            retURL = "/guidance/work/success";
        } else {
            map.put("error", retString);
            retURL = "/guidance/work/fail";
        }
        return retURL;
    }

    /**
     * 教师删除预习作业
     *
     * @param workId
     * @return
     * @author hujx
     * @date 2015/7/17 0004
     */
    @RequestMapping(value = "deleteGuidanceWork")
    @ResponseBody
    public AjaxResponse delPrepWork(String workId) {
        logger.info("guidance/work/deleteGuidanceWork:删除预习作业,workId:" + workId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        int i = teacherWorkService.delHomework(workId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(i);
        ajaxResponse.setStatus("1");
        return ajaxResponse;

    }

    /**
     * 教师编辑预习作业
     *
     * @param guidanceWorkViewDTO
     * @return
     * @author hujx
     * @date 2015/7/20 0005
     */
    @RequestMapping(value = "editGuidanceWork")
    @ResponseBody
    public AjaxResponse editPrepWork(TeacherWorkAddViewDTO guidanceWorkViewDTO) {
        logger.info("guidance/work/editGuidanceWork:编辑预习作业,guidanceWorkViewDTO:" + guidanceWorkViewDTO);
        /*// 测试用, begin
        List<String> newClassList = new ArrayList<>();
        newClassList.add("130cb64ee77d4fb9aecc7426bd0f8f0d");
        List<String> newGuidanceList = new ArrayList<>();
        newGuidanceList.add("00056a85260411e58cbbfcaa14bdabcb");
        guidanceWorkViewDTO.setWorkId("0be970b956bf41f3bd7600706621e0dd");
        guidanceWorkViewDTO.setUserId("143624154393");
        guidanceWorkViewDTO.setContext("我把作业改掉了，大家请注意！");
        guidanceWorkViewDTO.setSchoolId("school1");
        guidanceWorkViewDTO.setUnitId("010001001001001001001");
        guidanceWorkViewDTO.setWorkType(0);
        guidanceWorkViewDTO.setClassIdList(newClassList);
        guidanceWorkViewDTO.setGuidanceItemList(newGuidanceList);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = null;
        try {
            endDate = format.parse("2015-08-01 08:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        guidanceWorkViewDTO.setEndTime(endDate);
        guidanceWorkViewDTO.setCreateTime(new Date());
        guidanceWorkViewDTO.setUpdateTime(new Date());
        guidanceWorkViewDTO.setPublishTime(new Date());
        guidanceWorkViewDTO.setFinishStatus(0);
        guidanceWorkViewDTO.setCorrectStatus(0);
        guidanceWorkViewDTO.setStatus(1);
        // end*/

//        guidanceWorkViewDTO.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
//        int i = guidanceWorkService.editGuidanceWork(guidanceWorkViewDTO);
        AjaxResponse ajaxResponse = new AjaxResponse();
//        ajaxResponse.setWrapper(i);
//        ajaxResponse.setStatus("1");
        return ajaxResponse;

    }


    /**
     * 预习作业详情--完成详情--初始化
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "initDetail")
    public String initDetail(ModelMap map, String workId, String classId) {
        logger.info("guidance/work/initDetail:预习作业详情,workId:" + workId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取作业对应班级信息
        List<WorkClassViewDTO> gudanceWorkClassList = teacherWorkService.queryTeacherWorkClassList(workId, schoolId);
        if (StringUtils.isEmpty(classId) && gudanceWorkClassList != null && gudanceWorkClassList.size() > 0) {
            classId = gudanceWorkClassList.get(0).getClassId();
        }
        //获取作业对应预习题目信息
        List<TeacherWorkItemDTO> gudanceWorkItemList = teacherWorkService.queryTeacherWorkItemList(workId, schoolId);

        //当前业务规定一份作业只包含一个题目
        TeacherWorkItemDTO gudanceWorkItemDTO = new TeacherWorkItemDTO();
        if (gudanceWorkItemList != null && gudanceWorkItemList.size() > 0) {
            gudanceWorkItemDTO = gudanceWorkItemList.get(0);
        }

        //获取预习作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        if (teacherWorkDetailViewDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业信息为空", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (teacherWorkDetailViewDTO.getEndTime().getTime() < (new Date()).getTime()) {
            map.put("workEndStatus", 1);
        } else {
            map.put("workEndStatus", 0);
        }

        map.put("gudanceWorkClassList", gudanceWorkClassList);
        map.put("gudanceWorkItemDTO", gudanceWorkItemDTO);
        map.put("classId", classId);
        map.put("workId", workId);

        return "guidance/work/twmDetail";
    }

    /**
     * 根据预习作业ID获取未提交作业回答的学生信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "unSubStudentList")
    @ResponseBody
    public AjaxResponse queryUnSubStudentList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("guidance/work/unSubStudentList:获取未提交作业回答的学生信息,workId:" + workId + ",classId" + classId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkUnSubStudentViewDTO> unSubStudentList = teacherWorkService.selectUnSubStudentList(workId, classId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(unSubStudentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 根据预习作业ID、预习题目ID获取已提交学生作业信息
     *
     * @param workId     作业ID
     * @param workItemId 预习题目ID
     * @param classId    班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "subedAnswerList")
    @ResponseBody
    public AjaxResponse querySubedAnswerList(@RequestParam("workId") String workId,
                                             @RequestParam("workItemId") String workItemId,
                                             String classId) {
        logger.info("guidance/work/subedAnswerList:获取已提交学生作业信息,workId:" + workId + ",workItemId:" + workItemId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerViewDTO> subedAnswerList = teacherWorkService.querySubedWorkAnswerList(workId, workItemId, classId, schoolId, userId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 获取预习作业下某个班级的统计信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "teacherWorkSR")
    public String queryTeacherWorkSR(ModelMap map, @RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("guidance/work/teacherWorkSR:获取预习作业下某个班级的统计信息,workId:" + workId + ",classId" + classId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        SRTeacherWorkDTO sRTeacherWorkDTO = teacherWorkService.getTeacherWorkSR(schoolId, classId, workId, 1);

        //获取作业对应班级信息
        List<WorkClassViewDTO> gudanceWorkClassList = teacherWorkService.queryTeacherWorkClassList(workId, schoolId);

        map.put("sRTeacherWorkDTO", sRTeacherWorkDTO);
        map.put("gudanceWorkClassList", gudanceWorkClassList);
        map.put("workId", workId);
        map.put("classId", classId);
        return "guidance/work/teacherWorkSR";
    }

    /**
     * 预习作业详情--作业题目
     *
     * @param map
     * @param workId  作业ID
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "workItemDetail")
    public String workItemDetail(ModelMap map, String workId, String classId) {
        logger.info("guidance/work/workItemDetail:获取预习作业详情,workId:" + workId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取预习作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        //获取预习作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId, schoolId);
        //获取预习作业题目信息
        List<TeacherWorkItemViewDTO> itemContainFilesList = teacherWorkService.queryItemContainFilesList(workId, schoolId);

        WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
        if (workTapeFilesList != null && workTapeFilesList.size() > 0) {
            workTapeFilesDTO = workTapeFilesList.get(0);
        }

        TeacherWorkItemViewDTO teacherWorkItemViewDTO = new TeacherWorkItemViewDTO();
        if (itemContainFilesList != null && itemContainFilesList.size() > 0) {
            teacherWorkItemViewDTO = itemContainFilesList.get(0);
        }

        map.put("teacherWorkDetailViewDTO", teacherWorkDetailViewDTO);
        map.put("workTapeFilesDTO", workTapeFilesDTO);
        map.put("teacherWorkItemViewDTO", teacherWorkItemViewDTO);
        map.put("classId", classId);
        map.put("workId", workId);

        return "guidance/work/twmWorkInfo";
    }

    /**
     * 根据预习作业ID发通知提醒所有未提交作业的学生
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "warnSub")
    @ResponseBody
    public AjaxResponse warnSub(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("guidance/work/warnSub:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId" + classId);
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
        int warnSta = teacherWorkService.warnSub(workId, classId, schoolId, userId, userIcon, userName, 1);
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
        logger.info("guidance/work/praise:教师点赞学生作业,answerId:" + answerId);
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
        logger.info("guidance/work/unPraise:教师取消点赞学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.unpraise(answerId, userId, schoolId, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业回答ID
     * @param context  评语
     * @return AjaxResponse
     * 教师点评answerId对应的作业回答,点评视为批改作业
     */
    @RequestMapping(value = "comment")
    @ResponseBody
    public AjaxResponse comment(@RequestParam("answerId") String answerId, @RequestParam("context") String context) {
        logger.info("guidance/work/comment:教师点评学生作业,answerId:" + answerId + ",context:" + context);
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
     * 删除预习作业中的某条评论
     */
    @RequestMapping(value = "delComment")
    @ResponseBody
    public AjaxResponse delComment(@RequestParam("commentId") String commentId) {
        logger.info("guidance/work/delComment:教师删除评论,commentId:" + commentId);
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
        logger.info("guidance/work/moreComment:获取更多评论,answerId:" + answerId);
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
        logger.info("guidance/work/score:教师给学生作业打分,answerId:" + answerId + ",score:" + score);
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
        logger.info("guidance/work/delStuWork:教师删除单个学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.delStuWork(answerId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

//    /**
//     * 模拟移动端发布电子作业、口语作业
//     *
//     * @return
//     */
//    @RequestMapping(value = "saveAndPublishItem")
//    @ResponseBody
//    public AjaxResponse saveAndPublishItem() {
//        logger.info("guidance/work/saveAndPublishItem:模拟移动端发布电子作业、口语作业");
//        AjaxResponse ajaxResponse = new AjaxResponse<>();
//        TeacherWorkAddViewDTO teacherWorkAddViewDTO = new TeacherWorkAddViewDTO();
//        List<String> classList = new ArrayList<>();
//        classList.add("6f9af892a1a34482afb3b328073808a3");
//        classList.add("a6d30f3c4a7a44d2a7dc3fa1f0794627");
//        teacherWorkAddViewDTO.setClassList(classList);
//        teacherWorkAddViewDTO.setClassInfoJSON("[{\"classId\":\"6f9af892a1a34482afb3b328073808a3\",\"className\":\"一（二）班\"},{\"classId\":\"a6d30f3c4a7a44d2a7dc3fa1f0794627\",\"className\":\"一（三）班\"}]");
//        teacherWorkAddViewDTO.setUserId("265143510212");
//        teacherWorkAddViewDTO.setSchoolId("SchoolIDQATestsm55");
//        teacherWorkAddViewDTO.setUnitId("010003002001001007005");
//        teacherWorkAddViewDTO.setWorkType(4);
//        teacherWorkAddViewDTO.setPublishTime(new Date());
//        teacherWorkAddViewDTO.setEndTime(new Date());
//
//        BankItemCreateDTO bankItemCreateDTO = new BankItemCreateDTO();
//        bankItemCreateDTO.setCreator("265143510212");
//        bankItemCreateDTO.setSchoolId("SchoolIDQATestsm55");
//        bankItemCreateDTO.setUnitId("010003002001001007005");
//        bankItemCreateDTO.setItemType(4);
//        bankItemCreateDTO.setSubImage(1);
//        bankItemCreateDTO.setSubTape(0);
//        bankItemCreateDTO.setSubVideo(0);
//        bankItemCreateDTO.setContext("仔细做作业!");
//
//        List<String> diskIdList = new ArrayList<>();
//        diskIdList.add("0efb7bc2260411e58cbbfcaa14bfabcb");
//
//        List<BankItemAppFilesDTO> fileList = new ArrayList<>();
//        BankItemAppFilesDTO bankItemAppFilesDTO = new BankItemAppFilesDTO();
//        bankItemAppFilesDTO.setExtension("doc");
//        bankItemAppFilesDTO.setUrl("123123123123123");
//        bankItemAppFilesDTO.setSize(123123);
//        bankItemAppFilesDTO.setFileName("作业");
//        fileList.add(bankItemAppFilesDTO);
//
//        WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
//        workTapeFilesDTO.setUrl("3123123");
//        workTapeFilesDTO.setFileName("abc");
//        workTapeFilesDTO.setExtension("mp3");
//        workTapeFilesDTO.setSize(10000);
//
//        teacherWorkService.saveAndPublishItem(teacherWorkAddViewDTO, bankItemCreateDTO, diskIdList, fileList, workTapeFilesDTO);
//        ajaxResponse.setStatus("1");//分享成功
//        return ajaxResponse;
//    }


}