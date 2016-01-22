package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.appCenter.service.EliteSchoolService;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.constant.IdentityIdConstants;
import net.xuele.member.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * ClassReappearController
 * 课堂重现作业详情
 *
 * @author duzg
 * @date 2015/8/20 0002
 */
@Controller
@RequestMapping(value = "classReappear")
public class ClassReappearController {

    private static Logger logger = LoggerFactory.getLogger(ClassReappearController.class);

    @Autowired
    private WorkTapeFilesService workTapeFilesService;
    @Autowired
    private TeacherWorkService teacherWorkService;
    @Autowired
    TeacherWorkStuService teacherWorkStuService;
    @Autowired
    CloudTeachService cloudTeachService;
    @Autowired
    ClassService classService;
    @Autowired
    private MagicWorkStuService magicWorkStuService;
    @Autowired
    private SynclassWorkStuService synclassWorkStuService;
    @Autowired
    private MagicWorkService magicWorkService;
    @Autowired
    private MagicQuestionService magicQuestionService;
    @Autowired
    private SynclassWorkService synclassWorkService;
    @Autowired
    EliteSchoolService eliteSchoolService;


    /**
     * 预习作业详情--完成详情--初始化
     * @param map
     * @param workId 课堂重现作业Id
     * @param classId 课堂重现班级Id
     * @param schoolId   作业所在学校ID
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "guidance/initDetail")
    public String guidanceInitDetail(ModelMap map, @RequestParam("workId") String workId,
                                     String classId,
                                     @RequestParam("schoolId") String schoolId,
                                     String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
        String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String loginuserclasslId = "";
        if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
            loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            studentId = loginuserId;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
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
        String displayStatus = "0";//是否显示学生提交的作业： 0不能看  1能看

        if(loginuseridentityId!=null
                && !loginuseridentityId.equals(IdentityIdConstants.STUDENT)
                && !loginuseridentityId.equals(IdentityIdConstants.PARENT)){
            //学生、家长意外的角色
            displayStatus = "1";
        }else{
            //学生或家长只有在学生做完作业或作业提交截止日期已到期的情况下能看
            TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = teacherWorkService.getStuTeacherWorkAnswer(schoolId, studentId, workId);
            Date nowDate = new Date();
            if(teacherWorkItemAnswerDTO != null
                    && (teacherWorkItemAnswerDTO.getSubStatus().intValue()==1 || teacherWorkDetailViewDTO.getEndTime().getTime()<nowDate.getTime())){
                displayStatus = "1";
            }
        }

        map.put("gudanceWorkItemDTO", gudanceWorkItemDTO);
        map.put("teacherWorkDetailViewDTO", teacherWorkDetailViewDTO);
        map.put("classId",classId);
        map.put("studentId",studentId);
        map.put("displayStatus",displayStatus);
        return "classReappear/guidance/twDetail";
    }

    /**
     * 根据预习作业ID、预习题目ID获取已提交学生作业信息
     * @param workId     作业ID
     * @param workItemId 预习题目ID
     * @param classId    班级ID
     * @param userId     布置作业教师ID
     * @param schoolId   作业所在学校ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "guidance/subedAnswerList")
    @ResponseBody
    public AjaxResponse queryGuidanceSubedAnswerList(@RequestParam("workId") String workId,
                                             @RequestParam("workItemId") String workItemId,
                                             String classId,
                                             @RequestParam("userId") String userId,
                                             @RequestParam("schoolId") String schoolId) {
        if(StringUtils.isEmpty(workId)
                || StringUtils.isEmpty(workItemId)
                || StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                ajaxResponse.setStatus("0");
                ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
                return ajaxResponse;
            }
        }catch (Exception e){
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
            return ajaxResponse;
        }
        List<WorkAnswerViewDTO> subedAnswerList = teacherWorkService.querySubedWorkAnswerList(workId, workItemId, classId, schoolId, userId);

        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 预习作业详情--作业题目
     * @param map
     * @param workId 作业ID
     * @param classId 班级Id（如果指定显示某个班级作业，用于课堂重现与课堂重现）
     * @param schoolId   作业所在学校ID
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "guidance/workItemDetail")
    public String guidanceWorkItemDetail(ModelMap map,@RequestParam("workId") String workId,
                                 String classId,
                                 @RequestParam("schoolId") String schoolId,
                                 String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
                studentId = loginuserId;
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

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
        map.put("classId",classId);
        map.put("studentId",studentId);
        return "classReappear/guidance/twWorkInfo";
    }

    /**
     * 电子作业详情--完成详情--初始化
     * @param map
     * @param workId 课堂重现作业Id
     * @param classId 课堂重现班级Id
     * @param schoolId   作业所在学校ID
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "exercise/initDetail")
    public String exerciseInitDetail(ModelMap map, @RequestParam("workId") String workId,
                                     String classId,
                                     @RequestParam("schoolId") String schoolId,
                                     String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
        String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String loginuserclasslId = "";
        if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
            loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            studentId = loginuserId;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

        //获取作业对应预习题目信息
        List<TeacherWorkItemDTO> exerciseWorkItemList = teacherWorkService.queryTeacherWorkItemList(workId, schoolId);
        //当前业务规定一份作业只包含一个题目
        TeacherWorkItemDTO exerciseWorkItemDTO = new TeacherWorkItemDTO();
        if (exerciseWorkItemList != null && exerciseWorkItemList.size() > 0) {
            exerciseWorkItemDTO = exerciseWorkItemList.get(0);
        }
        //获取电子作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);

        String displayStatus = "0";//是否显示学生提交的作业： 0不能看  1能看

        if(loginuseridentityId!=null
                && !loginuseridentityId.equals(IdentityIdConstants.STUDENT)
                && !loginuseridentityId.equals(IdentityIdConstants.PARENT)){
            //学生、家长意外的角色
            displayStatus = "1";
        }else{
            //学生或家长只有在学生做完作业或作业提交截止日期已到期的情况下能看
            TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = teacherWorkService.getStuTeacherWorkAnswer(schoolId, studentId, workId);
            Date nowDate = new Date();
            if(teacherWorkItemAnswerDTO != null
                    && (teacherWorkItemAnswerDTO.getSubStatus().intValue()==1 || teacherWorkDetailViewDTO.getEndTime().getTime()<nowDate.getTime())){
                displayStatus = "1";
            }
        }

        map.put("exerciseWorkItemDTO", exerciseWorkItemDTO);
        map.put("teacherWorkDetailViewDTO", teacherWorkDetailViewDTO);
        map.put("classId",classId);
        map.put("studentId",studentId);
        map.put("displayStatus",displayStatus);
        return "classReappear/exercise/twDetail";
    }

    /**
     * 根据电子作业ID、电子作业题目ID获取已提交学生作业信息
     * @param workId     作业ID
     * @param workItemId 预习题目ID
     * @param classId    班级ID
     * @param userId     布置作业教师ID
     * @param schoolId   作业所在学校ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "exercise/subedAnswerList")
    @ResponseBody
    public AjaxResponse queryExerciseSubedAnswerList(@RequestParam("workId") String workId,
                                                     @RequestParam("workItemId") String workItemId,
                                                     String classId,
                                                     @RequestParam("userId") String userId,
                                                     @RequestParam("schoolId") String schoolId) {
        if(StringUtils.isEmpty(workId)
                || StringUtils.isEmpty(workItemId)
                || StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                ajaxResponse.setStatus("0");
                ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
                return ajaxResponse;
            }
        }catch (Exception e){
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
            return ajaxResponse;
        }
        List<WorkAnswerViewDTO> subedAnswerList = teacherWorkService.querySubedWorkAnswerList(workId, workItemId, classId, schoolId, userId);
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 电子作业详情--作业题目
     * @param map
     * @param workId 作业ID
     * @param classId 班级Id（如果指定显示某个班级作业，用于课堂重现与课堂重现）
     * @param schoolId   作业所在学校ID
     * @return
     */
    @RequestMapping(value = "exercise/workItemDetail")
    public String exerciseWorkItemDetail(ModelMap map,@RequestParam("workId") String workId,
                                         String classId,
                                         @RequestParam("schoolId") String schoolId,
                                         String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
                studentId = loginuserId;
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

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
        map.put("studentId",studentId);
        map.put("classId",classId);
        return "classReappear/exercise/twWorkInfo";
    }

    /**
     * 口语作业详情--完成详情--初始化
     * @param map
     * @param workId 课堂重现作业Id
     * @param classId 课堂重现班级Id
     * @param schoolId   作业所在学校ID
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "voice/initDetail")
    public String voiceInitDetail(ModelMap map, @RequestParam("workId") String workId,
                                     String classId,
                                     @RequestParam("schoolId") String schoolId,
                                     String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
        String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String loginuserclasslId = "";
        if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
            loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            studentId = loginuserId;
        }
        try{
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

        //获取作业对应预习题目信息
        List<TeacherWorkItemDTO> voiceWorkItemList = teacherWorkService.queryTeacherWorkItemList(workId, schoolId);
        //当前业务规定一份作业只包含一个题目
        TeacherWorkItemDTO voiceWorkItemDTO = new TeacherWorkItemDTO();
        if (voiceWorkItemList != null && voiceWorkItemList.size() > 0) {
            voiceWorkItemDTO = voiceWorkItemList.get(0);
        }
        //获取电子作业信息
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = teacherWorkService.queryTeacherWorkDetailByWorkId(workId, schoolId);
        String displayStatus = "0";//是否显示学生提交的作业： 0不能看  1能看

        if(loginuseridentityId!=null
                && !loginuseridentityId.equals(IdentityIdConstants.STUDENT)
                && !loginuseridentityId.equals(IdentityIdConstants.PARENT)){
            //学生、家长意外的角色
            displayStatus = "1";
        }else{
            //学生或家长只有在学生做完作业或作业提交截止日期已到期的情况下能看
            TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = teacherWorkService.getStuTeacherWorkAnswer(schoolId, studentId, workId);
            Date nowDate = new Date();
            if(teacherWorkItemAnswerDTO != null
                    && (teacherWorkItemAnswerDTO.getSubStatus().intValue()==1 || teacherWorkDetailViewDTO.getEndTime().getTime()<nowDate.getTime())){
                displayStatus = "1";
            }
        }
        map.put("voiceWorkItemDTO", voiceWorkItemDTO);
        map.put("teacherWorkDetailViewDTO", teacherWorkDetailViewDTO);
        map.put("classId",classId);
        map.put("studentId",studentId);
        map.put("displayStatus",displayStatus);
        return "classReappear/voice/twDetail";
    }

    /**
     * 根据口语作业ID、口语作业题目ID获取已提交学生作业信息
     * @param workId     作业ID
     * @param workItemId 预习题目ID
     * @param classId    班级ID
     * @param userId     布置作业教师ID
     * @param schoolId   作业所在学校ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "voice/subedAnswerList")
    @ResponseBody
    public AjaxResponse queryVoiceSubedAnswerList(@RequestParam("workId") String workId,
                                                     @RequestParam("workItemId") String workItemId,
                                                     String classId,
                                                     @RequestParam("userId") String userId,
                                                     @RequestParam("schoolId") String schoolId) {
        if(StringUtils.isEmpty(workId)
                || StringUtils.isEmpty(workItemId)
                || StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                ajaxResponse.setStatus("0");
                ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
                return ajaxResponse;
            }
        }catch (Exception e){
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
            return ajaxResponse;
        }
        List<WorkAnswerViewDTO> subedAnswerList = teacherWorkService.querySubedWorkAnswerList(workId, workItemId, classId, schoolId, userId);
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 口语作业详情--作业题目
     * @param map
     * @param workId 作业ID
     * @param classId 班级Id（如果指定显示某个班级作业，用于课堂重现与课堂重现）
     * @param schoolId   作业所在学校ID
     * @return
     */
    @RequestMapping(value = "voice/workItemDetail")
    public String voiceWorkItemDetail(ModelMap map,@RequestParam("workId") String workId,
                                         String classId,
                                         @RequestParam("schoolId") String schoolId,
                                         String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
                studentId = loginuserId;
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

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
        map.put("studentId",studentId);
        map.put("classId",classId);
        return "classReappear/voice/twWorkInfo";
    }

    /**
     * 提分宝作业详情--完成详情--初始化
     *
     * @param map
     * @param workId 课堂重现作业Id
     * @param classId 课堂重现班级Id
     * @param schoolId   作业所在学校ID
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "magic/initDetail")
    public String magciInitDetail(ModelMap map, @RequestParam("workId") String workId,
                             String classId,
                             @RequestParam("schoolId") String schoolId,
                             String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
        String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String loginuserclasslId = "";
        if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
            loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            studentId = loginuserId;
        }
        try{
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        //获取提分宝作业信息
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = magicWorkService.queryMagicWorkDetailByWorkId(workId, schoolId);
        String displayStatus = "0";//是否显示学生提交的作业： 0不能看  1能看

        if(loginuseridentityId!=null
                && !loginuseridentityId.equals(IdentityIdConstants.STUDENT)
                && !loginuseridentityId.equals(IdentityIdConstants.PARENT)){
            //学生、家长意外的角色
            displayStatus = "1";
        }else{
            //学生或家长只有在学生做完作业或作业提交截止日期已到期的情况下能看
            MagicWorkAnswerDTO magicWorkAnswerDTO = magicWorkService.getStuMagicWorkAnswer(workId, studentId, schoolId);
            Date nowDate = new Date();
            if(magicWorkAnswerDTO != null
                    && (magicWorkAnswerDTO.getSubStatus().intValue()==1 || magicWorkDetailViewDTO.getEndTime().getTime()<nowDate.getTime())){
                displayStatus = "1";
            }
        }

        map.put("magicWorkDetailViewDTO",magicWorkDetailViewDTO);
        map.put("workId",workId);
        map.put("classId",classId);
        map.put("schoolId",schoolId);
        map.put("studentId",studentId);
        map.put("displayStatus",displayStatus);
        return "classReappear/magic/twDetail";
    }

    /**
     * 根据提分宝作业ID获取已提交学生作业信息
     * @param workId     作业ID
     * @param classId    班级ID
     * @param userId     布置作业教师ID
     * @param schoolId   作业所在学校ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "magic/subedAnswerList")
    @ResponseBody
    public AjaxResponse queryMagicSubedAnswerList(@RequestParam("workId") String workId,
                                             String classId,
                                             @RequestParam("userId") String userId,
                                             @RequestParam("schoolId") String schoolId) {
        if(StringUtils.isEmpty(workId)
                || StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                ajaxResponse.setStatus("0");
                ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
                return ajaxResponse;
            }
        }catch (Exception e){
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
            return ajaxResponse;
        }

        List<WorkAnswerViewDTO> subedAnswerList = magicWorkService.querySubedWorkAnswerList(workId, classId, userId, schoolId);
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 提分宝作业详情--作业题目
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "magic/workItemDetail")
    public String magicWorkItemDetail(ModelMap map,@RequestParam("workId") String workId,
                                      String classId,
                                      @RequestParam("schoolId") String schoolId,
                                      String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
                studentId = loginuserId;
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                        CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

        //获取提分宝作业信息
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = magicWorkService.queryMagicWorkDetailByWorkId(workId, schoolId);
        //获取提分宝作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId, schoolId);
        //获取提分宝题库信息
        MagicQuestionBankDTO magicQuestionBankDTO = magicQuestionService.selectByPrimaryKey(magicWorkDetailViewDTO.getBankId());
        //获取提分宝作业题目信息
        List<MagicQuestionDTO> magicQuestionDTOList = magicQuestionService.queryMagicQuestionMasterListByBankId(magicWorkDetailViewDTO.getBankId());

        int itemNum = 0;
        if(magicQuestionDTOList!=null){
            itemNum = magicQuestionDTOList.size();
        }

        map.put("magicWorkDetailViewDTO", magicWorkDetailViewDTO);
        map.put("workTapeFilesList", workTapeFilesList);
        map.put("magicQuestionDTOList", magicQuestionDTOList);
        map.put("magicQuestionBankDTO",magicQuestionBankDTO);
        map.put("itemNum", Integer.toString(itemNum));
        map.put("classId",classId);
        map.put("studentId",studentId);
        map.put("schoolId",schoolId);

        return "classReappear/magic/twWorkInfo";
    }

    /**
     * 同步课堂作业详情--完成详情--初始化
     * @param map
     * @param workId 课堂重现作业Id
     * @param classId 课堂重现班级Id
     * @param schoolId   作业所在学校ID
     * @param studentId 学生ID
     * @return
     */
    @RequestMapping(value = "synclass/initDetail")
    public String synclassInitDetail(ModelMap map, @RequestParam("workId") String workId,
                             String classId,
                             @RequestParam("schoolId") String schoolId,
                             String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
        String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
        String loginuserclasslId = "";
        if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
            loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            studentId = loginuserId;
        }
        try{
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        //获取同步课堂作业信息
        SynclassWorkDetailViewDTO synclassWorkDetailViewDTO = synclassWorkService.querySynclassWorkDetailByWorkId(workId,schoolId);
        String displayStatus = "0";//是否显示学生提交的作业： 0不能看  1能看

        if(loginuseridentityId!=null
                && !loginuseridentityId.equals(IdentityIdConstants.STUDENT)
                && !loginuseridentityId.equals(IdentityIdConstants.PARENT)){
            //学生、家长意外的角色
            displayStatus = "1";
        }else{
            //学生或家长只有在学生做完作业或作业提交截止日期已到期的情况下能看
            SynclassWorkStudentDTO synclassWorkStudentDTO = synclassWorkService.getStuSynclassWorkAnswer(workId, studentId, schoolId);
            Date nowDate = new Date();
            if(synclassWorkStudentDTO != null
                    && (synclassWorkStudentDTO.getSubStatus().intValue()==1 || synclassWorkDetailViewDTO.getEndTime().getTime()<nowDate.getTime())){
                displayStatus = "1";
            }
        }
        map.put("synclassWorkDetailViewDTO",synclassWorkDetailViewDTO);
        map.put("workId",workId);
        map.put("classId",classId);
        map.put("schoolId",schoolId);
        map.put("studentId",studentId);
        map.put("displayStatus",displayStatus);
        return "classReappear/synclass/twDetail";
    }

    /**
     * 根据同步课堂作业ID获取已提交学生作业信息
     * @param workId     作业ID
     * @param classId    班级ID
     * @param userId     布置作业教师ID
     * @param schoolId   作业所在学校ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "synclass/subedAnswerList")
    @ResponseBody
    public AjaxResponse querySubedAnswerList(@RequestParam("workId") String workId,
                                             String classId,
                                             @RequestParam("userId") String userId,
                                             @RequestParam("schoolId") String schoolId) {
        if(StringUtils.isEmpty(workId)
                || StringUtils.isEmpty(userId)
                || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                ajaxResponse.setStatus("0");
                ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
                return ajaxResponse;
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
            return ajaxResponse;
        }

        List<SynclassWorkAnswerViewDTO> subedAnswerList = synclassWorkService.querySubedWorkAnswerList(workId, classId,userId,schoolId);
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 同步课堂作业详情--作业题目
     *
     * @param map
     * @param workId 作业ID
     * @param classId 班级Id（如果指定显示某个班级作业，用于课堂重现与课堂重现）
     * @param schoolId   作业所在学校ID
     * @return
     */
    @RequestMapping(value = "synclass/workItemDetail")
    public String synclassWorkItemDetail(ModelMap map,@RequestParam("workId") String workId,
                                         String classId,
                                         @RequestParam("schoolId") String schoolId,
                                         String studentId) {
        if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(classId)){
            classId = null;
        }
        //验证登陆用户是否有权限查看课堂重现
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
            }
        }catch (Exception e){
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

        //获取同步课堂作业信息
        SynclassWorkDetailViewDTO synclassWorkDetailViewDTO = synclassWorkService.querySynclassWorkDetailByWorkId(workId,schoolId);
        //获取同步课堂作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId,schoolId);
        //获取同步课堂作业题目信息
        List<SynclassWorkGameViewDTO> synclassWorkGameViewDTOList = synclassWorkService.querySynclassWorkGameList(workId,schoolId);

        map.put("synclassWorkDetailViewDTO", synclassWorkDetailViewDTO);
        map.put("workTapeFilesList", workTapeFilesList);
        map.put("synclassWorkGameViewDTOList", synclassWorkGameViewDTOList);
        map.put("classId",classId);
        map.put("studentId",studentId);
        map.put("schoolId",schoolId);

        return "classReappear/synclass/twWorkInfo";
    }

    /**
     * @param answerId 作业回答ID
     * @param classId 课堂重现班级ID
     * @param schoolId 课堂重现学校ID
     * @param workType 作业类型:1预习2提分宝3同步课堂4电子作业7口语作业
     * @return AjaxResponse
     * 获取更多评论
     */
    @RequestMapping(value = "moreComment")
    @ResponseBody
    public AjaxResponse moreComment(@RequestParam("answerId") String answerId,
                                    String classId,
                                    @RequestParam("schoolId") String schoolId,
                                    @RequestParam("workType") String workType) {
        if(StringUtils.isEmpty(classId) || StringUtils.isEmpty(answerId) || StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        AjaxResponse ajaxResponse = new AjaxResponse();
        try{
            String loginuserId = GetSessionContentUtil.getInstance().currentUserID();
            String loginuseridentityId = GetSessionContentUtil.getInstance().currentIdentityId();
            String loginuserclasslId = "";
            if(loginuseridentityId!=null && loginuseridentityId.equals(IdentityIdConstants.STUDENT)){
                loginuserclasslId = GetSessionContentUtil.getInstance().currentClassID();
            }
            boolean hasAuthority = eliteSchoolService.isReproduceAuthority(loginuserId, classId, loginuseridentityId, loginuserclasslId);
            if(!hasAuthority){
                //验证失败
                ajaxResponse.setStatus("0");
                ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
                return ajaxResponse;
            }
        }catch (Exception e){
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg("无权限查看该课堂重现内容");
            return ajaxResponse;
        }
        if(workType==null){
            workType = "";
        }
        List<WorkAnswerCommentViewDTO> moreCommentList = new ArrayList<>();
        if(workType.equals("1") || workType.equals("4") || workType.equals("7")){
            moreCommentList = teacherWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        }else if(workType.equals(2)){
            moreCommentList = magicWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        }else if(workType.equals(3)){
            moreCommentList = synclassWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        }

        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}