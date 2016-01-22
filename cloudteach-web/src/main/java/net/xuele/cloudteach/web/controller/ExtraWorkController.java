package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.BankItemService;
import net.xuele.cloudteach.service.TeacherWorkService;
import net.xuele.cloudteach.service.TeacherWorkStuService;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.cloudteach.web.common.DateEditor;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.AddWorkForm;
import net.xuele.cloudteach.web.form.BankItemCreateForm;
import net.xuele.cloudteach.web.form.BankItemEditForm;
import net.xuele.cloudteach.web.wrapper.ExerciseWorkWrapper;
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
 * Created by hujx on 2015/10/14 0014.
 * 教师课外作业管理
 */
@Controller
@RequestMapping(value = "extra/work")
public class ExtraWorkController {

    private static Logger logger = LoggerFactory.getLogger(ExerciseWorkController.class);

    @Autowired
    private WorkTapeFilesService workTapeFilesService;
    @Autowired
    private TeacherWorkService teacherWorkService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    BankItemService bankItemService;

    /**
     * @param map [请求参数]
     * @return
     */
    @RequestMapping(value = "tonewpublish")
    public String toNewPublish(ModelMap map) {
        logger.info("[extra/work/tonewpublish:新建并发布课外作业初始化]");
        return "/extra/work/newandpublish";
    }


    /**
     * @param map
     * @param itemId
     * @return
     */
    @RequestMapping(value = "toeditpublish")
    public String toEditPublish(ModelMap map, @RequestParam(value = "itemList", required = false) String itemId) {
        logger.info("[extra/work/toeditpublish:编辑并发布课外作业初始化]");
        if (itemId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }
        BankItemWithAttachmentDTO itemWithFilesDTO = bankItemService.getItemById(GetSessionContentUtil.getInstance().currentSchoolID(), itemId, CloudTeachBankEnum.FILE_TYPE_TEACHERQUE);


        List<CloudDiskReferDTO> fileList = itemWithFilesDTO.getAttachmentList();
        String fileJson = "";
        // 将已有的附件转化成json格式传给页面
        if (CollectionUtils.isNotEmpty(fileList)) {
            StringBuffer sb = new StringBuffer();
            for (CloudDiskReferDTO file : fileList) {
                sb.append("{'fileName':'" + file.getName() + "',");
                sb.append("'extension':'" + file.getExtension() + "',");
                sb.append("'size':'" + file.getSize() + "',");
                sb.append("'uri':'" + file.getFileUri() + "',");
                sb.append("'type':'" + file.getFileType() + "'},");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            fileJson = sb.toString();
        }

        map.put("item", itemWithFilesDTO);
        map.put("fileJson", fileJson);
        return "/extra/work/editandpublish";
    }

    /**
     * @param map
     * @param addWorkForm
     * @param itemCreateForm
     * @return
     */
    @RequestMapping(value = "saveaddextrawork")
    public String saveAndPubExtraWork(ModelMap map, AddWorkForm addWorkForm, BankItemCreateForm itemCreateForm) {
        logger.info("[extra/work/saveaddextrawork:保存并发布课外作业,addWorkForm:{},itemCreateForm:{}]", addWorkForm, itemCreateForm);
        String retURL = "/extra/work/success";

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
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
        teacherWorkAddViewDTO.setUnitId(""); // 课外作业的时候，将课程单元ID设置成""
        teacherWorkAddViewDTO.setPublishTime(publishTime);
        teacherWorkAddViewDTO.setEndTime(endTime);
        teacherWorkAddViewDTO.setItemList(addWorkForm.getItemList());
        teacherWorkAddViewDTO.setClassList(addWorkForm.getClassList());
        teacherWorkAddViewDTO.setClassInfoJSON(addWorkForm.getClassJson());
        teacherWorkAddViewDTO.setSubjectId("");
        teacherWorkAddViewDTO.setSubjectName("");
        teacherWorkAddViewDTO.setWorkType(Constants.WORK_TYPE_EXTRA_WORK);

        BankItemCreateDTO itemCreateDTO = new BankItemCreateDTO();
        itemCreateDTO.setUnitId(""); // 课外作业的时候，将课程单元ID设置成""
        itemCreateDTO.setSubImage(itemCreateForm.getSubImage());
        itemCreateDTO.setSubTape(itemCreateForm.getSubTape());
        itemCreateDTO.setSubVideo(itemCreateForm.getSubVideo());
        itemCreateDTO.setSubOther(itemCreateForm.getSubOther());
        itemCreateDTO.setContext(itemCreateForm.getContext());
        itemCreateDTO.setDiskIds(itemCreateForm.getDiskIds());
        itemCreateDTO.setCreator(userId);
        itemCreateDTO.setSchoolId(schoolId);
        itemCreateDTO.setItemType(CloudTeachBankEnum.ITEM_TYPE_EXTRA.getCode());

        String fileJSON = itemCreateForm.getFileJson();
        List<BankItemAppFilesDTO> filesList = new ArrayList<>();

        // 解析json
        if (fileJSON != null && !fileJSON.equals("")) {
            JSONArray filesJSONArray = JSON.parseArray(fileJSON);
            if (filesJSONArray.size() != 0 && filesJSONArray != null) {
                for (int i = 0; i < filesJSONArray.size(); i++) {
                    JSONObject jsonObject = filesJSONArray.getJSONObject(i);
                    BankItemAppFilesDTO filesDTO = new BankItemAppFilesDTO();
                    filesDTO.setUrl(jsonObject.getString("uri"));
                    filesDTO.setFileName(jsonObject.getString("fileName"));
                    filesDTO.setExtension(jsonObject.getString("extension"));
                    filesDTO.setSize(jsonObject.getIntValue("size"));
                    filesDTO.setFileType(jsonObject.getIntValue("type"));
                    filesList.add(filesDTO);
                }
            }
        }

        try {
            teacherWorkService.saveAndPubExtraWork(teacherWorkAddViewDTO, itemCreateDTO, filesList, workTapeFilesDTO);
            ajaxResponseAddWork.setStatus("1");
            if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
                retURL = "/workmanage/maneger/success";
            } else {
                retURL = "/extra/work/success";
            }
        } catch (CloudteachException e) {
            ajaxResponseAddWork.setErrorMsg(e.getMsg());
            ajaxResponseAddWork.setStatus("0");
            map.put("error", e.getMsg());
            if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
                retURL = "/workmanage/maneger/fail";
            } else {
                retURL = "/extra/work/fail";
            }
        }
        return retURL;
    }

    @RequestMapping(value = "editaddextrawork")
    public String editAndPubExtraWork(ModelMap map, AddWorkForm addWorkForm, BankItemEditForm itemEditForm, @RequestParam(value = "itemId", required = false) String itemId) {
        logger.info("[extra/work/saveaddextrawork:编辑并发布课外作业,addWorkForm:{},temEditForm:{}]", addWorkForm, itemEditForm);
        String retURL = "/extra/work/success";

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
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
        teacherWorkAddViewDTO.setUnitId(""); // 课外作业的时候，将课程单元ID设置成""
        teacherWorkAddViewDTO.setPublishTime(publishTime);
        teacherWorkAddViewDTO.setEndTime(endTime);
        teacherWorkAddViewDTO.setItemList(addWorkForm.getItemList());
        teacherWorkAddViewDTO.setClassList(addWorkForm.getClassList());
        teacherWorkAddViewDTO.setClassInfoJSON(addWorkForm.getClassJson());
        teacherWorkAddViewDTO.setSubjectId("");
        teacherWorkAddViewDTO.setSubjectName("");
        teacherWorkAddViewDTO.setWorkType(Constants.WORK_TYPE_EXTRA_WORK);

        BankItemEditDTO itemEditDTO = new BankItemEditDTO();
        itemEditDTO.setItemId(itemId);
        itemEditDTO.setUnitId(""); // 课外作业的时候，将课程单元ID设置成""
        itemEditDTO.setSubImage(itemEditForm.getSubImage());
        itemEditDTO.setSubTape(itemEditForm.getSubTape());
        itemEditDTO.setSubVideo(itemEditForm.getSubVideo());
        itemEditDTO.setSubOther(itemEditForm.getSubOther());
        itemEditDTO.setContext(itemEditForm.getContext());
        itemEditDTO.setDiskIds(itemEditForm.getDiskIds());
        itemEditDTO.setCreator(userId);
        itemEditDTO.setSchoolId(schoolId);
        itemEditDTO.setItemType(CloudTeachBankEnum.ITEM_TYPE_EXTRA.getCode());

        String fileJSON = itemEditForm.getFileJson();
        List<BankItemAppFilesDTO> filesList = new ArrayList<>();

        // 解析json
        if (fileJSON != null && !fileJSON.equals("")) {
            JSONArray filesJSONArray = JSON.parseArray(fileJSON);
            if (filesJSONArray.size() != 0 && filesJSONArray != null) {
                for (int i = 0; i < filesJSONArray.size(); i++) {
                    JSONObject jsonObject = filesJSONArray.getJSONObject(i);
                    BankItemAppFilesDTO filesDTO = new BankItemAppFilesDTO();
                    filesDTO.setUrl(jsonObject.getString("uri"));
                    filesDTO.setFileName(jsonObject.getString("fileName"));
                    filesDTO.setExtension(jsonObject.getString("extension"));
                    filesDTO.setSize(jsonObject.getIntValue("size"));
                    filesDTO.setFileType(jsonObject.getIntValue("type"));
                    filesList.add(filesDTO);
                }
            }
        }

        itemEditDTO.setFilesList(filesList);

        try {
            teacherWorkService.editAndPubExtraWork(teacherWorkAddViewDTO, itemEditDTO, workTapeFilesDTO, userId, schoolId);
            ajaxResponseAddWork.setStatus("1");
            if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
                retURL = "/workmanage/maneger/success";
            } else {
                retURL = "/extra/work/success";
            }
        } catch (CloudteachException e) {
            ajaxResponseAddWork.setErrorMsg(e.getMsg());
            ajaxResponseAddWork.setStatus("0");
            map.put("error", e.getMsg());
            if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
                retURL = "/workmanage/maneger/fail";
            } else {
                retURL = "/extra/work/fail";
            }
        }
        return retURL;
    }

    /**
     * @param workId 作业ID【请求参数】
     * @return 习题作业删除
     */
    @RequestMapping(value = "delextrawork")
    @ResponseBody
    public AjaxResponse delExtraWork(String workId) {
        logger.info("[extra/work/delextrawork:删除课外作业,workId:{}]", workId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        int i = teacherWorkService.delHomework(workId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(i);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 电子作业详情--完成详情--初始化
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "initDetail")
    public String initDetail(ModelMap map, String workId, String classId) {
        logger.info("[extra/work/initDetail:课外作业详情,workId:{}]", workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取作业对应班级信息
        List<WorkClassViewDTO> extraWorkClassList = teacherWorkService.queryTeacherWorkClassList(workId, schoolId);
        // String classId = "";
        if (extraWorkClassList != null && extraWorkClassList.size() > 0) {
            if (StringUtils.isEmpty(classId)) {
                classId = extraWorkClassList.get(0).getClassId();
            }
        }
        //获取作业对应电子题目信息
        List<TeacherWorkItemDTO> extraWorkItemList = teacherWorkService.queryTeacherWorkItemList(workId, schoolId);

        //当前业务规定一份作业只包含一个题目
        TeacherWorkItemDTO extraWorkItemDTO = new TeacherWorkItemDTO();
        if (extraWorkItemList != null && extraWorkItemList.size() > 0) {
            extraWorkItemDTO = extraWorkItemList.get(0);
        }

        //获取电子作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        if (teacherWorkDetailViewDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业信息为空", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (teacherWorkDetailViewDTO.getEndTime().getTime() < (new Date()).getTime()) {
            map.put("workEndStatus", 1);
        } else {
            map.put("workEndStatus", 0);
        }

        map.put("extraWorkClassList", extraWorkClassList);
        map.put("extraWorkItemDTO", extraWorkItemDTO);
        map.put("classId", classId);
        map.put("workId", workId);
        return "extra/work/twmDetail";
    }

    /**
     * 根据电子作业ID获取未提交作业回答的学生信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "unSubStudentList")
    @ResponseBody
    public AjaxResponse queryUnSubStudentList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("extra/work/unSubStudentList:获取未提交作业回答的学生信息,workId:" + workId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkUnSubStudentViewDTO> unSubStudentList = teacherWorkService.selectUnSubStudentList(workId, classId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(unSubStudentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 根据电子作业ID、电子题目ID获取已提交学生作业信息
     *
     * @param workId     作业ID
     * @param workItemId 电子题目ID
     * @param classId    班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "subedAnswerList")
    @ResponseBody
    public AjaxResponse querySubedAnswerList(@RequestParam("workId") String workId,
                                             @RequestParam("workItemId") String workItemId,
                                             String classId) {
        logger.info("extra/work/subedAnswerList:获取已提交学生作业信息,workId:" + workId + ",workItemId:" + workItemId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerViewDTO> subedAnswerList = teacherWorkService.querySubedWorkAnswerList(workId, workItemId, classId, schoolId, userId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }


    /**
     * 获取电子作业下某个班级的统计信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "teacherWorkSR")
    public String queryTeacherWorkSR(ModelMap map, @RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("extra/work/teacherWorkSR:获取课外作业下某个班级的统计信息,workId:" + workId + ",classId" + classId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        SRTeacherWorkDTO sRTeacherWorkDTO = teacherWorkService.getTeacherWorkSR(schoolId, classId, workId, Constants.WORK_TYPE_EXTRA_WORK);

        //获取作业对应班级信息
        List<WorkClassViewDTO> extraWorkClassList = teacherWorkService.queryTeacherWorkClassList(workId, schoolId);

        map.put("sRTeacherWorkDTO", sRTeacherWorkDTO);
        map.put("extraWorkClassList", extraWorkClassList);
        map.put("workId", workId);
        map.put("classId", classId);
        return "extra/work/teacherWorkSR";
    }

    /**
     * 电子作业详情--作业题目
     *
     * @param map
     * @param workId  作业ID
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "workItemDetail")
    public String workItemDetail(ModelMap map, String workId, String classId) {
        logger.info("exercise/work/workItemDetail:获取课外作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
        int userIdentify = 0;
        if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
            userIdentify = 1;
        }
        //获取电子作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        //获取电子作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId, schoolId);
        //获取电子作业题目信息
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
        map.put("userIdentify", userIdentify);
        return "extra/work/twmWorkInfo";
    }

    /**
     * 根据电子作业ID发通知提醒所有未提交作业的学生
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "warnSub")
    @ResponseBody
    public AjaxResponse warnSub(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("extra/work/warnSub:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId:" + classId);
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
        int warnSta = teacherWorkService.warnSub(workId, classId, schoolId, userId, userIcon, userName, Constants.WORK_TYPE_EXTRA_WORK);
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
        logger.info("extra/work/praise:教师点赞学生作业,answerId:" + answerId);
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
        logger.info("extra/work/unPraise:教师取消点赞学生作业,answerId:" + answerId);
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
        logger.info("extra/work/comment:教师点评学生作业,answerId:" + answerId + ",context:" + context);
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
     * 删除电子作业中的某条评论
     */
    @RequestMapping(value = "delComment")
    @ResponseBody
    public AjaxResponse delComment(@RequestParam("commentId") String commentId) {
        logger.info("extra/work/delComment:教师删除评论,commentId:" + commentId);
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
        logger.info("extra/work/moreComment:获取更多评论,answerId:" + answerId);
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
        logger.info("extra/work/score:教师给学生作业打分,answerId:" + answerId + ",score:" + score);
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
        logger.info("extra/work/delStuWork:教师删除学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        teacherWorkService.delStuWork(answerId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

}
