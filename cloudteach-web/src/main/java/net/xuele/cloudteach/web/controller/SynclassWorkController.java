package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.appCenter.dto.DUnitsDTO;
import net.xuele.appCenter.dto.FlashAddressDTO;
import net.xuele.appCenter.dto.SynClassFlashInDTO;
import net.xuele.appCenter.dto.ViewCourseWaresDTO;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.SynclassWorkService;
import net.xuele.cloudteach.service.SynclassWorkStuService;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.cloudteach.web.common.DateTranslate;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.SynClassFlashInForm;
import net.xuele.cloudteach.web.form.SynclassWorkForm;
import net.xuele.cloudteach.web.wrapper.*;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SynclassWorkController
 * 教师同步课堂作业管理
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
@Controller
@RequestMapping(value = "synclass/work")
public class SynclassWorkController {
    private static Logger logger = LoggerFactory.getLogger(GuidanceWorkController.class);
    @Autowired
    private SynclassWorkService synclassWorkService;
    @Autowired
    private SynclassWorkStuService synclassWorkStuService;
    @Autowired
    private WorkTapeFilesService workTapeFilesService;


    /**
     * @param map 发布同步课堂作业初始化一,获取对应课程下的同步课堂游戏
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/teacher/initWorkPage");

        logger.info("synclass/work/index:发布同步课堂作业初始化一,获取对应课程下的同步课堂游戏");
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
        return "synclass/work/index";
    }

    /**
     * 发布同步作业初始化二，进入发布页面
     *
     * @return
     */
    @RequestMapping(value = "publishSynclass")
    public String publishSynclass() {
        logger.info("synclass/work/publishSynclass:发布同步作业初始化二，进入发布页面");
        return "synclass/work/publish";
    }


    /**
     * @return 获得用户所属的同步课堂课程树信息
     */
    @RequestMapping(value = "/unitList")
    @ResponseBody
    public AjaxResponse<CloudUnitWrapper> unitList() {
        logger.info("synclass/work/unitList:获得用户所属的同步课堂课程树信息");
        AjaxResponse<CloudUnitWrapper> ajaxResponse = new AjaxResponse<CloudUnitWrapper>();
        CloudUnitWrapper cloudUnitWrapper = new CloudUnitWrapper();

        String bookId = GetSessionContentUtil.getInstance().currentBookID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<DUnitsDTO> dtoList = synclassWorkService.getSyncUnit(bookId, schoolId);

        int count = 0;
        List<UnitRecord> unitRecordList = new ArrayList<UnitRecord>();
        if (dtoList != null) {
            count = dtoList.size();
            for (DUnitsDTO dto : dtoList) {
                UnitRecord record = new UnitRecord();
                record.setUnitId(dto.getuCode());
                record.setUnitName(dto.getuName());
                record.setRecord(getChildren(dto.getLesson()));
                unitRecordList.add(record);
            }
        }
        cloudUnitWrapper.setUnitRecordList(unitRecordList);
        cloudUnitWrapper.setTotal(count);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(cloudUnitWrapper);
        return ajaxResponse;
    }

    public List<UnitRecord> getChildren(List<DUnitsDTO> dtoList) {
        List<UnitRecord> list = new ArrayList<UnitRecord>();
        for (DUnitsDTO dto : dtoList) {
            UnitRecord record = new UnitRecord();
            if (dto.getLesson() != null) {
                record.setUnitName(dto.getuName());
                record.setUnitId(dto.getuCode());
                record.setRecord(getChildren(dto.getLesson()));
            } else {
                record.setUnitName(dto.getuName());
                record.setUnitId(dto.getuCode());
            }
            list.add(record);
        }
        return list;
    }

    /**
     * 发布同步课堂作业
     *
     * @param synclassWorkForm
     * @return
     */
    @RequestMapping(value = "/addSynclassWork")
    public String addSynclassWork(ModelMap map, SynclassWorkForm synclassWorkForm) {
        logger.info("synclass/work/addSynclassWork:发布同步课堂作业,synclassWorkForm:" + synclassWorkForm);
        String classJson = synclassWorkForm.getClassJson();
        String context = synclassWorkForm.getContext();
        if (context == null || context.equals("")) {
            throw new CloudteachException(CloudTeachErrorEnum.SYNCLASS_CONTENT_NOT_EXIST.getMsg(),
                    CloudTeachErrorEnum.SYNCLASS_CONTENT_NOT_EXIST.getCode());
        }
        //班级不为空
        if (classJson == null || classJson.equals("[]")) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_CLASS_NULL.getMsg(),
                    CloudTeachErrorEnum.WORK_CLASS_NULL.getCode());
        }

        SynclassWorkDTO synclassWorkDTO = new SynclassWorkDTO();

        List<SynclassWorkClassDTO> classDTOList = new ArrayList<SynclassWorkClassDTO>();
        List<SynclassWorkGameDTO> gameDTOList = new ArrayList<SynclassWorkGameDTO>();


        entitySynclassWorkFormToDTO(synclassWorkForm, synclassWorkDTO, classDTOList, gameDTOList);

        String url = null;
        String response = null;
        try {
            response = synclassWorkService.addSynclassWork(synclassWorkDTO, classDTOList, gameDTOList, classJson, null);
        } catch (CloudteachException e) {
            return url = "/synclass/work/fail";
        }

        if ("".equals(response)) {
            url = "/synclass/work/success";
        } else {
            url = "/synclass/work/fail";
            map.put("error", response);
        }

        return url;
    }

    /**
     * 老师查看游戏附件
     *
     * @param flashInForm
     * @return
     */
    @RequestMapping(value = "/checkGame")
    @ResponseBody
    public AjaxResponse<FlashAddressWrapper> checkGame(SynClassFlashInForm flashInForm) {
        logger.info("synclass/work/checkGame:老师查看游戏附件,flashInForm:" + flashInForm);
        AjaxResponse ajaxResponse = new AjaxResponse();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        //TODO 老师不需要修改这个东西
        String returnString = "http://cloudteach.app.xuele.net/synclass/work/modifySynclassWorkPlay";
        flashInForm.setReturnString(returnString);
        flashInForm.setuID(userId);
        flashInForm.setuType(Constants.TEACHER_TYPE);
        SynClassFlashInDTO synClassFlashInDTO = new SynClassFlashInDTO();
        BeanUtils.copyProperties(flashInForm, synClassFlashInDTO);
        FlashAddressDTO flashAddressDTO = synclassWorkService.getSynClassFlashVars(synClassFlashInDTO);

        FlashAddressWrapper wrapper = new FlashAddressWrapper();
        BeanUtils.copyProperties(flashAddressDTO, wrapper);

        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(wrapper);
        return ajaxResponse;
    }

    /**
     * 根据课程获得同步课堂课件
     *
     * @param unitId
     * @return
     */
    @RequestMapping(value = "/queryCourseWare")
    @ResponseBody
    public AjaxResponse<List<SynclassWorkGameWrapper>> queryCourseWare(String unitId) {
        logger.info("synclass/work/queryCourseWare:根据课程获得同步课堂课件,unitId:" + unitId);
        AjaxResponse ajaxResponse = new AjaxResponse();

        List<ViewCourseWaresDTO> dtoList = synclassWorkService.queryCourseWare(unitId);
        List<SynclassWorkGameWrapper> wrapperList = new ArrayList<SynclassWorkGameWrapper>();

        String uID = "-1";
        for (ViewCourseWaresDTO dto : dtoList) {
            SynclassWorkGameWrapper wrapper = new SynclassWorkGameWrapper();
            wrapper.setcReal(dto.getcReal());
            wrapper.setGameFileId(dto.getUcId());
            wrapper.setGameId(dto.getUcId());
            wrapper.setGameName(dto.getcTitle());
            wrapper.setCkName(dto.getCkName());
            String gameUrl = "http://www.xueleyun.com/appcenter/synClassFlash/cloudteachPlay?ucID=" + dto.getUcId() +
                    "&cReal=" + dto.getcReal() + "&uType=" + Constants.TEACHER_TYPE + "&uID=" + uID;
            //学生加载课堂作业有用
//            String url ="http://resource.xuelecn.com/gameicons/"+游戏ID+".jpg";
            wrapper.setGamePicUrl(dto.getUrlAddress());
            wrapper.setGameUrl(gameUrl);
            wrapperList.add(wrapper);
        }
        ajaxResponse.setWrapper(wrapperList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 修改同步课堂作业
     *
     * @param synclassWorkForm
     * @return
     */
    @RequestMapping(value = "/modifySynclassWork")
    @ResponseBody
    public AjaxResponse modifySynclassWork(SynclassWorkForm synclassWorkForm) {
        logger.info("synclass/work/modifySynclassWork:修改同步课堂作业,synclassWorkForm:" + synclassWorkForm);
        SynclassWorkDTO synclassWorkDTO = new SynclassWorkDTO();
        List<SynclassWorkClassDTO> classDTOList = new ArrayList<SynclassWorkClassDTO>();
        List<SynclassWorkGameDTO> gameDTOList = new ArrayList<SynclassWorkGameDTO>();
        AjaxResponse response = new AjaxResponse();
        String classJson = synclassWorkForm.getClassJson();

        entitySynclassWorkFormToDTO(synclassWorkForm, synclassWorkDTO, classDTOList, gameDTOList);
        synclassWorkService.modifySynclassWork(synclassWorkDTO, classDTOList, gameDTOList, classJson);
        response.setStatus("1");
        return response;
    }

    /**
     * 获取同步课堂作业信息
     *
     * @param workId
     * @return
     */
    @RequestMapping(value = "/querySynclassWork")
    @ResponseBody
    public AjaxResponse<SynclassWorkWrapper> querySynclassWork(String workId) {
        logger.info("synclass/work/querySynclassWork:获取同步课堂作业信息,workId:" + workId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        SynclassWorkDTO workDTO = synclassWorkService.querySynclassWork(workId, schoolId);
        List<SynclassWorkGameViewDTO> gameViewDTOList = synclassWorkService.querySynclassWorkGameList(workId, schoolId);
        SynclassWorkWrapper workWrapper = new SynclassWorkWrapper();
        BeanUtils.copyProperties(workDTO, workWrapper);
        List<SynclassWorkGameWrapper> gameWrapperList = new ArrayList<SynclassWorkGameWrapper>();
        for (SynclassWorkGameViewDTO dto : gameViewDTOList) {
            SynclassWorkGameWrapper workGameWrapper = new SynclassWorkGameWrapper();
            BeanUtils.copyProperties(dto, workGameWrapper);
            gameWrapperList.add(workGameWrapper);
        }
        workWrapper.setWrapperList(gameWrapperList);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(workWrapper);
        return ajaxResponse;
    }

    /**
     * 获取同步课堂作业中的游戏信息
     *
     * @param workId
     * @return
     */
    @RequestMapping(value = "/querySynclassWorkGameListByWorkId")
    @ResponseBody
    public AjaxResponse<List<SynclassWorkGameWrapper>> querySynclassWorkGameListByWorkId(String workId) {
        logger.info("synclass/work/querySynclassWorkGameListByWorkId:获取同步课堂作业中的游戏信息,workId:" + workId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<SynclassWorkGameDTO> gameDTOList = synclassWorkService.querySynclassWorkGameListByWorkId(workId, schoolId);
        List<SynclassWorkGameWrapper> gameWrapperList = new ArrayList<SynclassWorkGameWrapper>();
        if (gameDTOList != null) {
            for (SynclassWorkGameDTO gameDTO : gameDTOList) {
                SynclassWorkGameWrapper gameWrapper = new SynclassWorkGameWrapper();
                BeanUtils.copyProperties(gameDTO, gameWrapper);
                gameWrapperList.add(gameWrapper);
            }
        }
        ajaxResponse.setWrapper(gameWrapperList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 获取同步课堂作业中对应的班级信息
     *
     * @param workId
     * @return
     */
    @RequestMapping(value = "/querySynclassWorkClassListByWorkId")
    @ResponseBody
    public AjaxResponse<List<SynclassWorkClassWrapper>> querySynclassWorkClassListByWorkId(String workId) {
        logger.info("synclass/work/querySynclassWorkGameListByWorkId:获取同步课堂作业中对应的班级信息,workId:" + workId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<SynclassWorkClassDTO> classDTOList = synclassWorkService.querySynclassWorkClassListByWorkId(workId, schoolId);
        List<SynclassWorkClassWrapper> classWrapperList = new ArrayList<SynclassWorkClassWrapper>();
        if (classDTOList != null) {
            for (SynclassWorkClassDTO classDTO : classDTOList) {
                SynclassWorkClassWrapper classWrapper = new SynclassWorkClassWrapper();
                BeanUtils.copyProperties(classDTO, classWrapper);
                classWrapperList.add(classWrapper);
            }
        }
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(classWrapperList);
        return ajaxResponse;
    }

    /**
     * 根据同步课堂获得对应的学生列表
     *
     * @param workId
     * @return
     */
    @RequestMapping(value = "/querySynclassWorkStudentListByWorkId")
    @ResponseBody
    public AjaxResponse<List<SynclassWorkStudentWrapper>> querySynclassWorkStudentListByWorkId(String workId) {
        logger.info("synclass/work/querySynclassWorkStudentListByWorkId:获取同步课堂作业中对应的学生列表,workId:" + workId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<SynclassWorkStudentDTO> studentDTOList = synclassWorkService.querySynclassWorkStudentListByWorkId(workId, schoolId);
        List<SynclassWorkStudentWrapper> wrapperList = new ArrayList<SynclassWorkStudentWrapper>();
        if (studentDTOList != null) {
            for (SynclassWorkStudentDTO studentDTO : studentDTOList) {
                SynclassWorkStudentWrapper studentWrapper = new SynclassWorkStudentWrapper();
                BeanUtils.copyProperties(studentDTO, studentWrapper);
                wrapperList.add(studentWrapper);
            }
        }
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(wrapperList);
        return ajaxResponse;
    }

    /**
     * 获取同步课堂作业学生信息
     *
     * @param workUserId
     * @return
     */
    @RequestMapping(value = "/querySynclassWorkAnswer")
    @ResponseBody
    public AjaxResponse<SynclassWorkStudentWrapper> querySynclassWorkAnswer(String workUserId) {
        logger.info("synclass/work/querySynclassWorkAnswer:获取同步课堂作业学生信息,workUserId:" + workUserId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        SynclassWorkStudentDTO studentDTO = synclassWorkService.querySynclassWorkAnswer(workUserId, schoolId);
        SynclassWorkStudentWrapper studentWrapper = new SynclassWorkStudentWrapper();
        BeanUtils.copyProperties(studentDTO, studentWrapper);
        ajaxResponse.setWrapper(studentWrapper);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 同步作业详情--完成详情--初始化
     *
     * @param map
     * @param workId 作业ID
     * @return
     */
    @RequestMapping(value = "initDetail")
    public String initDetail(ModelMap map, String workId,String classId) {
        logger.info("synclass/work/initDetail:获取同步作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取作业对应班级信息
        List<WorkClassViewDTO> synclassWorkClassList = synclassWorkService.querySynclassWorkClassList(workId, schoolId);
        //String classId = "";
        if (synclassWorkClassList != null && synclassWorkClassList.size() > 0) {
            if (StringUtils.isEmpty(classId)) {
                classId = synclassWorkClassList.get(0).getClassId();
            }
        }
        //获取同步课堂作业信息
        SynclassWorkDetailViewDTO synclassWorkDetailViewDTO = synclassWorkService.querySynclassWorkDetailByWorkId(workId, schoolId);
        if (synclassWorkDetailViewDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业信息为空", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (synclassWorkDetailViewDTO.getEndTime().getTime() < (new Date()).getTime()) {
            map.put("workEndStatus", 1);
        } else {
            map.put("workEndStatus", 0);
        }

        map.put("synclassWorkClassList", synclassWorkClassList);
        map.put("workId", workId);
        map.put("classId", classId);
        map.put("workId", workId);
        return "synclass/work/twmDetail";
    }

    /**
     * 根据同步课堂作业ID获取未提交作业回答的学生信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "unSubStudentList")
    @ResponseBody
    public AjaxResponse queryUnSubStudentList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("synclass/work/unSubStudentList:获取未提交作业回答的学生信息,workId:" + workId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkUnSubStudentViewDTO> unSubStudentList = synclassWorkService.selectUnSubStudentList(workId, classId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(unSubStudentList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 根据同步课堂作业ID获取已提交学生作业信息
     *
     * @param workId  作业ID【请求参数】
     * @param classId 班级ID【请求参数】
     * @return AjaxResponse
     */
    @RequestMapping(value = "subedAnswerList")
    @ResponseBody
    public AjaxResponse querySubedAnswerList(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("synclass/work/subedAnswerList:获取已提交学生作业信息,workId:" + workId + ",classId:" + classId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<SynclassWorkAnswerViewDTO> subedAnswerList = synclassWorkService.querySubedWorkAnswerList(workId, classId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(subedAnswerList);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 获取同步课堂作业下某个班级的统计信息
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @param gameId  游戏附件ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "teacherWorkSR")
    public String queryTeacherWorkSR(ModelMap map, @RequestParam("workId") String workId, @RequestParam("classId") String classId, String gameId) {
        logger.info("synclass/work/teacherWorkSR:获取预习作业下某个班级的统计信息,workId:" + workId + ",classId:" + classId + ",gameId:" + gameId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取同步课堂作业题目信息
        List<SynclassWorkGameViewDTO> synclassWorkGameViewDTOList = synclassWorkService.querySynclassWorkGameList(workId, schoolId);

        if (synclassWorkGameViewDTOList == null || synclassWorkGameViewDTOList.size() <= 0) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":同步课堂作业对应的游戏附件信息不存在",
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        String gameName = "";

        if (StringUtils.isEmpty(gameId)) {
            gameId = synclassWorkGameViewDTOList.get(0).getWorkGameId();
            gameName = synclassWorkGameViewDTOList.get(0).getCkName();
        } else {
            for (SynclassWorkGameViewDTO synclassWorkGameViewDTO : synclassWorkGameViewDTOList) {
                if (synclassWorkGameViewDTO.getWorkGameId().equals(gameId)) {
                    gameName = synclassWorkGameViewDTO.getCkName();
                    break;
                }
            }
        }

        SRTeacherWorkDTO sRTeacherWorkDTO = synclassWorkService.getSynclassWorkSR(schoolId, classId, workId, gameId);

        //获取作业对应班级信息
        List<WorkClassViewDTO> synclassWorkClassList = synclassWorkService.querySynclassWorkClassList(workId, schoolId);

        map.put("sRTeacherWorkDTO", sRTeacherWorkDTO);
        map.put("synclassWorkClassList", synclassWorkClassList);
        map.put("synclassWorkGameViewDTOList", synclassWorkGameViewDTOList);
        map.put("workId", workId);
        map.put("classId", classId);
        map.put("gameId", gameId);
        map.put("gameName", gameName);
        return "synclass/work/teacherWorkSR";
    }

    /**
     * 同步课堂作业详情--作业题目
     *
     * @param map
     * @param workId  作业ID
     * @param classId 班级ID
     * @return
     */
    @RequestMapping(value = "workItemDetail")
    public String workItemDetail(ModelMap map, String workId, String classId) {
        logger.info("synclass/work/workItemDetail:获取同步课堂作业详情,workId:" + workId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        //获取同步课堂作业信息
        SynclassWorkDetailViewDTO synclassWorkDetailViewDTO = synclassWorkService.querySynclassWorkDetailByWorkId(workId, schoolId);
        //获取同步课堂作业录音信息
        List<WorkTapeFilesDTO> workTapeFilesList = workTapeFilesService.queryListByWorkId(workId, schoolId);
        //获取同步课堂作业题目信息
        List<SynclassWorkGameViewDTO> synclassWorkGameViewDTOList = synclassWorkService.querySynclassWorkGameList(workId, schoolId);

        WorkTapeFilesDTO workTapeFilesDTO = new WorkTapeFilesDTO();
        if (workTapeFilesList != null && workTapeFilesList.size() > 0) {
            workTapeFilesDTO = workTapeFilesList.get(0);
        }

        map.put("synclassWorkDetailViewDTO", synclassWorkDetailViewDTO);
        map.put("workTapeFilesDTO", workTapeFilesDTO);
        map.put("synclassWorkGameViewDTOList", synclassWorkGameViewDTOList);
        map.put("classId", classId);
        map.put("workId", workId);
        return "synclass/work/twmWorkInfo";
    }

    /**
     * 根据同步课堂作业ID发通知提醒所有未提交作业的学生
     *
     * @param workId  作业ID
     * @param classId 班级ID
     * @return AjaxResponse
     */
    @RequestMapping(value = "warnSub")
    @ResponseBody
    public AjaxResponse warnSub(@RequestParam("workId") String workId, @RequestParam("classId") String classId) {
        logger.info("synclass/work/warnSub:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId:" + classId);
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
        int warnSta = synclassWorkService.warnSub(workId, classId, schoolId, userId, userIcon, userName, 3);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if (warnSta == 0) {
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(Constants.WARN_SUB_WORK_BUSY);
        }
        return ajaxResponse;
    }

    /**
     * @param workUserId 作业回答ID
     * @return AjaxResponse
     * 教师点赞answerId对应的作业回答
     */
    @RequestMapping(value = "praise")
    @ResponseBody
    public AjaxResponse praise(@RequestParam("answerId") String workUserId) {
        logger.info("synclass/work/praise:教师点赞学生作业,workUserId:" + workUserId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        synclassWorkService.praise(workUserId, userId, schoolId, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param workUserId 作业回答ID
     * @return AjaxResponse
     * 教师取消点赞answerId对应的作业回答
     */
    @RequestMapping(value = "unPraise")
    @ResponseBody
    public AjaxResponse unPraise(@RequestParam("answerId") String workUserId) {
        logger.info("synclass/work/unPraise:教师取消点赞学生作业,workUserId:" + workUserId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        synclassWorkService.unpraise(workUserId, userId, schoolId, 1);
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
        logger.info("synclass/work/comment:教师点评学生作业,answerId:" + answerId + ",context:" + context);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String commentId = synclassWorkService.comment(answerId, userId, schoolId, context, 1);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(commentId);
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
        logger.info("synclass/work/moreComment:获取更多评论,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        List<WorkAnswerCommentViewDTO> moreCommentList = synclassWorkStuService.getMoreComments(answerId, schoolId, Constants.MAX_MORE_COMMENT_NUM);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setWrapper(moreCommentList);
        ajaxResponse.setStatus("1");
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
        logger.info("synclass/work/delComment:教师删除评论,commentId:" + commentId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        synclassWorkService.delCommont(commentId, userId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param answerId 作业学生回答ID
     * @return AjaxResponse
     * 删除单个学生提交的作业
     */
    @RequestMapping(value = "delStuWork")
    @ResponseBody
    public AjaxResponse delStuWork(@RequestParam("answerId") String answerId) {
        logger.info("synclass/work/delComment:教师删除学生作业,answerId:" + answerId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        synclassWorkService.delSynclassStuWork(userId, answerId, schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    public static void entitySynclassWorkFormToDTO(SynclassWorkForm form, SynclassWorkDTO synclassWorkDTO
            , List<SynclassWorkClassDTO> classDTOListist, List<SynclassWorkGameDTO> gameDTOListist) {
        BeanUtils.copyProperties(form, synclassWorkDTO);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String date = DateTranslate.getTsToDateString(form.getPublishTime(), "yyyy-MM-dd");
        Date publishTime = DateTranslate.getFormatDateFromString(date, "yyyy-MM-dd");
        int lastCommitDay = form.getLastCommitDay();

        Date endTime = DateTranslate.getFormatDateFromString(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");


        synclassWorkDTO.setEndTime(DateTranslate.getLastCommitDate(endTime, lastCommitDay));

        //发布时间跟当前时间比较，为1表示发布时间大于当前时间
        if (DateTranslate.compareNowDate(publishTime, "yyyy-MM-dd") == 1) {
            synclassWorkDTO.setWorkType(0); //定时发布
            synclassWorkDTO.setPublishTime(publishTime);
        } else {
            synclassWorkDTO.setWorkType(1); //及时发布
            synclassWorkDTO.setPublishTime(new Date());
        }
        synclassWorkDTO.setUserId(userId);
        synclassWorkDTO.setSchoolId(schoolId);
        if (form.getClassList() != null) {
            for (String classId : form.getClassList()) {
                SynclassWorkClassDTO classDTO = new SynclassWorkClassDTO();
                classDTO.setClassId(classId);
                classDTOListist.add(classDTO);
            }
        }
        if (form.getGameList() != null) {
            for (Integer gameId : form.getGameList()) {
                SynclassWorkGameDTO gameDTO = new SynclassWorkGameDTO();
                gameDTO.setGameId(gameId);
                gameDTOListist.add(gameDTO);
            }
        }
    }
}