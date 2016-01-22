package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.TeacherWorkService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.cloudteach.dto.page.AfterClassWorkViewPageRequest;
import net.xuele.cloudteach.dto.page.EffectiveWorkViewPageRequest;
import net.xuele.cloudteach.dto.page.GuidanceWorkViewPageRequest;
import net.xuele.cloudteach.service.TeacherWorkManageService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.web.wrapper.AfterClassWorkViewWrapper;
import net.xuele.cloudteach.web.wrapper.EffectiveWorkViewWrapper;
import net.xuele.cloudteach.web.wrapper.GuidanceWorkViewWrapper;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TeacherWorkManageController
 * 教师作业管理
 * @author duzg
 * @date 2015/7/8 0002
 */
@Controller
@RequestMapping(value = "workmanage/teacher")
public class TeacherWorkManageController {

    @Autowired
    private TeacherWorkManageService teacherWorkManageService;
    @Autowired
    private WorkStatisticsService workStatisticsService;
    @Autowired
    private CloudTeachService cloudTeachService;
    @Autowired
    private TeacherWorkService teacherWorkService;

    private static Logger logger = LoggerFactory.getLogger(TeacherWorkManageController.class);

    /**
     * 进行中的作业列表初始化
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "initMyEffectiveWorkList")
    public String initMyEffectiveWorkList(ModelMap map, EffectiveWorkViewPageRequest request){
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("进行中的作业列表初始化,userId:"+userId+",schoolId:"+schoolId);
        //根据老师用户ID获取教师授课科目列表信息
        //List<SummaryDTO> teachSubjectList = cloudTeachService.selectTeacherSubjectList(userId);

        //根据教师已布置的作业获取科目汇总信息
        List<SubjectGatherViewDTO> subjectGatherViewDTOList = workStatisticsService.queryTeacherWorkSubjectList(userId, schoolId);

        //根据老师用户ID获取教师授课班级列表信息
        List<ClassInfoDTO> teachClassList = cloudTeachService.selectTeacherClassList(userId);

        List<Integer> teachGradeList = cloudTeachService.collectGradeFromClassList(teachClassList);

        map.put("subjectGatherViewDTOList",subjectGatherViewDTOList);
        map.put("teachClassList",teachClassList);
        map.put("teachGradeList",teachGradeList);
        map.put("currPage","myEffective");

        return "workmanage/teacher/myEffectiveWorkList";
    }

    /**
     * @param request 【请求参数】
     * @return 分页获取进行中的作业信息（默认显示全部班级，主授课目下的所有作业，排序方式：发布时间倒序）
     */
    @RequestMapping(value = "effectiveWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<EffectiveWorkViewWrapper>> queryMyEffectiveWork(EffectiveWorkViewPageRequest request) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        request.setUserId(userId);
        request.setSchoolId(schoolId);
        logger.info("分页获取进行中的作业信息,userId:"+userId+",schoolId:"+schoolId);
        //返回对象
        AjaxResponse<PageResponse<EffectiveWorkViewWrapper>> effectiveWorkViewWrapperAjaxResponse = new AjaxResponse<>();

        //request.setUserId(currentUserID());

        //调用服务查询出数据存放在DTO中
        PageResponse<EffectiveWorkViewDTO> effectiveWorkPageResponse = teacherWorkManageService.queryEffectiveWork(request);
        //数据转换目标PageResponse<wrapper>对象
        PageResponse<EffectiveWorkViewWrapper> effectiveWorkViewPageResponse = new PageResponse<>();
        //数据转换中间wrapper对象
        List<EffectiveWorkViewWrapper> effectiveWorkViewWrapperlist = new ArrayList<>();

        //转换Wrapper
        for(EffectiveWorkViewDTO ewvDTO : effectiveWorkPageResponse.getRows()){
            EffectiveWorkViewWrapper wrapperObj = new EffectiveWorkViewWrapper(ewvDTO);
            wrapperObj.setUserName(GetSessionContentUtil.getInstance().currentUserName());//设置用户名称
            wrapperObj.setUserIconUrl(GetSessionContentUtil.getInstance().currentUserIcon());//设置用户头像
            effectiveWorkViewWrapperlist.add(wrapperObj);
        }
        BeanUtils.copyProperties(effectiveWorkPageResponse, effectiveWorkViewPageResponse);
        effectiveWorkViewPageResponse.setRows(effectiveWorkViewWrapperlist);

        effectiveWorkViewWrapperAjaxResponse.setStatus("1");
        effectiveWorkViewWrapperAjaxResponse.setWrapper(effectiveWorkViewPageResponse);
        return effectiveWorkViewWrapperAjaxResponse;
    }

    /**
     * 作业列表初始化
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "initAfterclassWorkList")
    public String initAfterclassWorkList(ModelMap map, AfterClassWorkViewPageRequest request){
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("作业列表初始化,userId:"+userId+",schoolId:"+schoolId);
        //根据老师用户ID获取教师授课科目列表信息
        //List<SummaryDTO> teachSubjectList = cloudTeachService.selectTeacherSubjectList(userId);

        //根据教师已布置的作业获取科目汇总信息
        List<SubjectGatherViewDTO> subjectGatherViewDTOList = workStatisticsService.queryTeacherWorkSubjectList(userId, schoolId);

        //根据老师用户ID获取教师授课班级列表信息
        List<ClassInfoDTO> teachClassList = cloudTeachService.selectTeacherClassList(userId);

        List<Integer> teachGradeList = cloudTeachService.collectGradeFromClassList(teachClassList);

        map.put("subjectGatherViewDTOList",subjectGatherViewDTOList);
        map.put("teachClassList",teachClassList);
        map.put("teachGradeList",teachGradeList);
        map.put("currPage","afterclass");

        return "workmanage/teacher/afterclassWorkList";
    }

    /**
     * @param request 【请求参数】
     * @return 分页获取课后作业信息（默认显示全部班级，主授课目下的所有作业，排序方式：按作业状态排序（进行中、定时未发布、其他））
     */
    @RequestMapping(value = "afterclassWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<AfterClassWorkViewWrapper>> queryMyAfterclassWork(AfterClassWorkViewPageRequest request) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        request.setUserId(userId);
        request.setSchoolId(schoolId);
        logger.info("分页获取课后作业信息,userId:"+userId+",schoolId:"+schoolId);
        //返回对象
        AjaxResponse<PageResponse<AfterClassWorkViewWrapper>> afterClassWorkViewWrapperAjaxResponse = new AjaxResponse<>();

        //request.setUserId(currentUserID());

        //调用服务查询出数据存放在DTO中
        PageResponse<AfterClassWorkViewDTO> afterClassWorkPageResponse = teacherWorkManageService.queryAfterClassWork(request);
        //数据转换目标PageResponse<wrapper>对象
        PageResponse<AfterClassWorkViewWrapper> afterClassWorkViewPageResponse = new PageResponse<>();
        //数据转换中间wrapper对象
        List<AfterClassWorkViewWrapper> afterClassWorkViewWrapperlist = new ArrayList<>();

        //转换Wrapper
        for(AfterClassWorkViewDTO acwvDTO : afterClassWorkPageResponse.getRows()){
            AfterClassWorkViewWrapper wrapperObj = new AfterClassWorkViewWrapper(acwvDTO);
            wrapperObj.setUserName(GetSessionContentUtil.getInstance().currentUserName());//设置用户名称
            wrapperObj.setUserIconUrl(GetSessionContentUtil.getInstance().currentUserIcon());//设置用户头像
            afterClassWorkViewWrapperlist.add(wrapperObj);
        }
        BeanUtils.copyProperties(afterClassWorkPageResponse, afterClassWorkViewPageResponse);
        afterClassWorkViewPageResponse.setRows(afterClassWorkViewWrapperlist);

        afterClassWorkViewWrapperAjaxResponse.setStatus("1");
        afterClassWorkViewWrapperAjaxResponse.setWrapper(afterClassWorkViewPageResponse);
        return afterClassWorkViewWrapperAjaxResponse;
    }

    /**
     * 预习作业列表初始化
     *
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "initGuidanceWorkList")
    public String initGuidanceWorkList(ModelMap map, GuidanceWorkViewPageRequest request){
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("预习作业列表初始化,userId:"+userId+",schoolId:"+schoolId);
        //根据老师用户ID获取教师授课科目列表信息
        //List<SummaryDTO> teachSubjectList = cloudTeachService.selectTeacherSubjectList(userId);

        //根据教师已布置的作业获取科目汇总信息
        List<SubjectGatherViewDTO> subjectGatherViewDTOList = workStatisticsService.queryTeacherWorkSubjectList(userId, schoolId);

        //根据老师用户ID获取教师授课班级列表信息
        List<ClassInfoDTO> teachClassList = cloudTeachService.selectTeacherClassList(userId);

        List<Integer> teachGradeList = cloudTeachService.collectGradeFromClassList(teachClassList);

        map.put("subjectGatherViewDTOList",subjectGatherViewDTOList);
        map.put("teachClassList",teachClassList);
        map.put("teachGradeList",teachGradeList);
        map.put("currPage","guidance");

        return "workmanage/teacher/guidanceWorkList";
    }

    /**
     * @param request 【请求参数】
     * @return 分页获取预习作业信息（默认显示全部班级，主授课目下的所有作业，排序方式：（进行中、定时未发布、其他））
     */
    @RequestMapping(value = "guidanceWorkList")
    @ResponseBody
    public AjaxResponse<PageResponse<GuidanceWorkViewWrapper>> queryMyGuidanceWork(GuidanceWorkViewPageRequest request) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        request.setUserId(userId);
        request.setSchoolId(schoolId);
        logger.info("分页获取预习作业信息,userId:"+userId+",schoolId:"+schoolId);
        //返回对象
        AjaxResponse<PageResponse<GuidanceWorkViewWrapper>> guidanceWorkViewWrapperAjaxResponse = new AjaxResponse<>();

        //request.setUserId(currentUserID());

        //调用服务查询出数据存放在DTO中
        PageResponse<GuidanceWorkViewDTO> guidanceWorkPageResponse = teacherWorkManageService.queryGuidanceWork(request);
        //数据转换目标PageResponse<wrapper>对象
        PageResponse<GuidanceWorkViewWrapper> guidanceWorkViewPageResponse = new PageResponse<>();
        //数据转换中间wrapper对象
        List<GuidanceWorkViewWrapper> guidanceWorkViewWrapperlist = new ArrayList<>();

        //转换Wrapper
        for(GuidanceWorkViewDTO gwvDTO : guidanceWorkPageResponse.getRows()){
            GuidanceWorkViewWrapper wrapperObj = new GuidanceWorkViewWrapper(gwvDTO);
            wrapperObj.setUserName(GetSessionContentUtil.getInstance().currentUserName());//设置用户名称
            wrapperObj.setUserIconUrl(GetSessionContentUtil.getInstance().currentUserIcon());//设置用户头像
            guidanceWorkViewWrapperlist.add(wrapperObj);
        }
        BeanUtils.copyProperties(guidanceWorkPageResponse, guidanceWorkViewPageResponse);
        guidanceWorkViewPageResponse.setRows(guidanceWorkViewWrapperlist);

        guidanceWorkViewWrapperAjaxResponse.setStatus("1");
        guidanceWorkViewWrapperAjaxResponse.setWrapper(guidanceWorkViewPageResponse);
        return guidanceWorkViewWrapperAjaxResponse;
    }

    /**
     * @param workId   作业ID
     * @param workType 作业类型 1预习作业，2提分神器作业，3同步课堂作业，4电子作业，7语音评测作业
     * @param context 评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param score 评分
     * @return AjaxResponse
     * 快速批改workId对应的作业
     */
    @RequestMapping(value = "quicklyCorrect")
    @ResponseBody
    public AjaxResponse quicklyCorrect(@RequestParam("workId") String workId,@RequestParam("workType") Integer workType,
                                       @RequestParam("context") String context,@RequestParam("praiseStatus") Integer praiseStatus,
                                       @RequestParam("score") Integer score) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        logger.info("快速批改workId对应的作业,userId:"+userId+",schoolId:"+schoolId+",workId:"+workId+",workType:"+workType
                +",context:"+context+",praiseStatus:"+praiseStatus+",score:"+score);

        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if(StringUtils.isEmpty(userId)){
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }

        try{
            teacherWorkManageService.quicklyCorrect(workId,workType,context,praiseStatus,score,userId,schoolId);
        }catch (CloudteachException e){
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
        }

        return ajaxResponse;
    }

    /**
     * @param workjson  json格式参数包含了workId、workType（例如：[{"0001":1},{"0002":2},{"0003":3}]）
     * @param context 评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @return AjaxResponse
     * 全部快速批改，如果workjson没有信息则默认快速批改userId下所有进行中的作业
     */
    @RequestMapping(value = "quicklyAllCorrect")
    @ResponseBody
    public AjaxResponse quicklyAllCorrect(String workjson,@RequestParam("context") String context,
                                          @RequestParam("praiseStatus") Integer praiseStatus) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        logger.info("快速批改workId对应的作业,userId:"+userId+",schoolId:"+schoolId+",workjson:"+workjson
                +",context:"+context+",praiseStatus:"+praiseStatus);

        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if(StringUtils.isEmpty(userId)){
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }
        //操作失败的wordId
        String failWorkIds = "";
        try {
            failWorkIds = teacherWorkManageService.quicklyAllCorrect(workjson,context,praiseStatus,userId,schoolId);
        } catch (IOException e) {
            ajaxResponse.setErrorMsg(e.getMessage());
            ajaxResponse.setStatus("0");
        }
        ajaxResponse.setErrorMsg(failWorkIds);
        return ajaxResponse;
    }

    /**
     * @param workId   作业ID
     * @param workType 作业类型 1预习作业，2提分神器作业，3同步课堂作业，4电子作业，7口语作业
     * @return AjaxResponse
     * 删除workId对应的作业
     */
    @RequestMapping(value = "delWork")
    @ResponseBody
    public AjaxResponse delWork(@RequestParam("workId") String workId,@RequestParam("workType") Integer workType) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("快速批改workId对应的作业,userId:"+userId+",schoolId:"+schoolId+",workId:"+workId+",workType:"+workType);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if(userId == null || "".equals(userId)){
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }
        try{
            teacherWorkManageService.delWork(workId, workType,userId,schoolId);
        }catch (CloudteachException e){
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
        }
        return ajaxResponse;
    }

    /**
     * @param answerId  作业学生回答ID
     * @param workType 作业类型 1预习作业，2提分神器作业，3同步课堂作业，4电子作业，7口语作业
     * @return AjaxResponse
     * 删除answerId对应的学生作业
     */
    @RequestMapping(value = "delStuWork")
    @ResponseBody
    public AjaxResponse delStuWork(@RequestParam("answerId") String answerId,@RequestParam("workType") Integer workType) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("快速批改workId对应的作业,userId:"+userId+",schoolId:"+schoolId+",answerId:"+answerId+",workType:"+workType);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if(userId == null || "".equals(userId)){
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }
        try{
            teacherWorkManageService.delStuWork(answerId, workType, userId, schoolId);
        }catch (CloudteachException e){
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
        }
        return ajaxResponse;
    }

    /**
     * 获取某个教师某个课程对应某种教师作业（预习、电子、口语）信息,用于授课课件接口
     * @param schoolId
     * @param unitId
     * @param userId
     * @param workType
     * @return
     */
    @RequestMapping(value = "cwTeacherWorkList")
    @ResponseBody
    public AjaxResponse queryCoursewareTeacherWorkList(@RequestParam("schoolId") String schoolId,
                                                       @RequestParam("unitId") String unitId,
                                                       @RequestParam("userId") String userId,
                                                       @RequestParam("workType") Integer workType) {
        logger.info("获取教师某个课程对应某种教师作业,userId:"+userId+",schoolId:"+schoolId+",unitId:"+unitId+",workType:"+workType);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if(userId == null || "".equals(userId)){
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }
        try{
            List<CoursewareTeacherWorkViewDTO> cwtList = teacherWorkService.queryCoursewareTeacherWorkList(schoolId, unitId, userId, workType);
            ajaxResponse.setStatus("1");
            ajaxResponse.setWrapper(cwtList);
        }catch (CloudteachException e){
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
        }
        return ajaxResponse;
    }
}