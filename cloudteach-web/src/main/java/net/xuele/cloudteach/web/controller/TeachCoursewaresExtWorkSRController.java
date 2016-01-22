package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.LearningInfoWorkService;
import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * TeachCoursewaresExtWorkSRController
 * 为授课课件提供学情作业统计信息
 *
 * @author duzg
 * @date 2015-12-04
 */
@Controller
@RequestMapping("coursewares/extworksr")
public class TeachCoursewaresExtWorkSRController {

    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresExtWorkSRController.class);

    @Autowired
    LearningInfoWorkService learningInfoWorkService;
    @Autowired
    CloudTeachService cloudTeachService;

    /**
     * @param teacherId
     * @param unitId
     * @param classId
     * @param schoolId
     * @return
     */
    @RequestMapping("learninginfosynclass")
    @ResponseBody
    public AjaxResponse getSynclassWork(String teacherId, String unitId, String classId, String schoolId) {

        logger.warn("[入参:teacherId={},unitId={},classId={},schoolId={}]", teacherId, unitId, classId, schoolId);
        String duserId = CloudTeachEncryptUtil.decrypt(teacherId);
        String dschoolId = CloudTeachEncryptUtil.decrypt(schoolId);
        logger.info("[入参(解密后):duserId={},dschoolId={}]", duserId, dschoolId);

//        String duserId = "1030610004";
//        String dschoolId = "10306";
//        unitId = "010003001001001001001";
//        classId = "be4e755a424b11e5825844a8421dc7b3";

        LearningInfoStuWorkDTO info = learningInfoWorkService.quarySynclassAnswerInfo(duserId, unitId, classId, dschoolId);
        List<LearningInfoWorkDTO> workDTOList = info.getWorkInfo();
//        for (LearningInfoWorkDTO workDTO : workDTOList) {
//
//        }

        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(info);
        return ajaxResponse;
    }

    /**
     * @param unitId
     * @param classId
     * @return
     */
    @RequestMapping("learninginfoexercise")
    @ResponseBody
    public AjaxResponse getExerciseWork(String teacherId, String unitId, String classId, String schoolId) {

        logger.warn("[入参:teacherId={},unitId={},classId={},classId={},schoolId={}]", teacherId, unitId, classId, classId, schoolId);
        String duserId = CloudTeachEncryptUtil.decrypt(teacherId);
        String dschoolId = CloudTeachEncryptUtil.decrypt(schoolId);
        logger.warn("[入参(解密后):duserId={},dschoolId={}]", duserId, dschoolId);

        /*String duserId = "1030610004";
        String dschoolId = "10306";
        unitId = "010003001001001001001";
        classId = "be4e755a424b11e5825844a8421dc7b3";*/

        // 获取课程名称：
        String unitName = "";
        UnitsDTO units = cloudTeachService.getUnitInfo(unitId);
        if (units != null) {
            unitName = units.getUnitName();
        }

        float totalStudentNum = 0;
        float totalSubmitStudentNum = 0;
        float totalPartakeRate = 0;
        List<LearningInfoExerciseWorkWrapper> exerciseWorkWrapperList = new ArrayList<>();
        LearningInfoStuWorkDTO info = learningInfoWorkService.quaryWorkAnswerInfo(duserId, unitId, Constants.WORK_TYPE_EXERCISE_WORK, classId, dschoolId);
        List<LearningInfoWorkDTO> workDTOList = info.getWorkInfo();
        for (LearningInfoWorkDTO workDTO : workDTOList) {
            LearningInfoExerciseWorkWrapper exerciseWorkWrapper = new LearningInfoExerciseWorkWrapper();
            exerciseWorkWrapper.setWorkId(workDTO.getWorkId());
            exerciseWorkWrapper.setContext(workDTO.getContext());
            exerciseWorkWrapper.setAvgWorkTime((long) workDTO.getAverage().getAvgWorkTime());
            // 参与率
            int finishNum = 0;
            int notFinishNum = 0;
            List<LearningInfoStuTeacherWorkDTO> finishInfo = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getFinishedInfo();
            if (CollectionUtils.isNotEmpty(finishInfo)) {
                finishNum = finishInfo.size();
            }
            List<LearningInfoStuTeacherWorkDTO> notFinishInfo = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getNotFinishedInfo();
            if (CollectionUtils.isNotEmpty(notFinishInfo)) {
                notFinishNum = notFinishInfo.size();
            }
            LearningInfoSubmitStatisWrapper subStatis = new LearningInfoSubmitStatisWrapper();
            subStatis.setTotalStuNum(finishNum + notFinishNum);
            subStatis.setSubmitStuNum(finishNum);
            subStatis.setUnsubmitStuNum(notFinishNum);
            // 得分等级归类
            LearningInfoScoreLevelWrapper scoreLevel = new LearningInfoScoreLevelWrapper();
            scoreLevel.setLevelAStuNum(0);
            scoreLevel.setLevelBStuNum(0);
            scoreLevel.setLevelCStuNum(0);
            scoreLevel.setLevelDStuNum(0);
            List<LearningInfoStuTeacherWorkDTO> AList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelA();
            if (CollectionUtils.isNotEmpty(AList)) {
                scoreLevel.setLevelAStuNum(AList.size());
            }
            List<LearningInfoStuTeacherWorkDTO> BList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelB();
            if (CollectionUtils.isNotEmpty(BList)) {
                scoreLevel.setLevelBStuNum(BList.size());
            }
            List<LearningInfoStuTeacherWorkDTO> CList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelC();
            if (CollectionUtils.isNotEmpty(CList)) {
                scoreLevel.setLevelCStuNum(CList.size());
            }
            List<LearningInfoStuTeacherWorkDTO> DList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelD();
            if (CollectionUtils.isNotEmpty(DList)) {
                scoreLevel.setLevelDStuNum(DList.size());
            }
            exerciseWorkWrapper.setLevelStuNum(scoreLevel);
            exerciseWorkWrapper.setPartakeRate(subStatis);
            exerciseWorkWrapper.setSubmitStuWorkInfo(finishInfo);
            exerciseWorkWrapperList.add(exerciseWorkWrapper);
            totalStudentNum = totalStudentNum + finishNum + notFinishNum;
            totalSubmitStudentNum = totalSubmitStudentNum + finishNum;
        }

        if (totalStudentNum != 0) {
            totalPartakeRate = totalSubmitStudentNum / totalStudentNum;
        }

        LearningInfoExerciseInfoWrapper exerciseInfoWrapper = new LearningInfoExerciseInfoWrapper();
        exerciseInfoWrapper.setUnitName(unitName);
        exerciseInfoWrapper.setTotalPartakeRate(totalPartakeRate);
        exerciseInfoWrapper.setWorkInfoList(exerciseWorkWrapperList);

        AjaxResponse response = new AjaxResponse();
        response.setStatus("1");
        response.setWrapper(exerciseInfoWrapper);
        return response;
    }

    /**
     * @param unitId
     * @param classId
     * @return
     */
    @RequestMapping("learninginfopreview")
    @ResponseBody
    public AjaxResponse getPreviewWork(String teacherId, String unitId, String classId, String schoolId) {

        logger.warn("[入参:teacherId={},unitId={},classId={},classId={},schoolId={}]", teacherId, unitId, classId, classId, schoolId);
        String duserId = CloudTeachEncryptUtil.decrypt(teacherId);
        String dschoolId = CloudTeachEncryptUtil.decrypt(schoolId);
        logger.warn("[入参(解密后):duserId={},dschoolId={}]", duserId, dschoolId);

        /*String duserId = "1030610004";
        String dschoolId = "10306";
        unitId = "010003001001001001001";
        classId = "be4e755a424b11e5825844a8421dc7b3";*/

        // 获取课程名称：
        String unitName = "";
        UnitsDTO units = cloudTeachService.getUnitInfo(unitId);
        if (units != null) {
            unitName = units.getUnitName();
        }

        float totalStudentNum = 0;
        float totalSubmitStudentNum = 0;
        float totalPartakeRate = 0;
        List<LearningInfoPreviewWorkWrapper> previewWorkWrapperList = new ArrayList<>();
        LearningInfoStuWorkDTO info = learningInfoWorkService.quaryWorkAnswerInfo(duserId, unitId, Constants.WORK_TYPE_GUIDANCE_WORK, classId, dschoolId);
        List<LearningInfoWorkDTO> workDTOList = info.getWorkInfo();
        for (LearningInfoWorkDTO workDTO : workDTOList) {
            LearningInfoPreviewWorkWrapper previewWorkWrapper = new LearningInfoPreviewWorkWrapper();
            previewWorkWrapper.setWorkId(workDTO.getWorkId());
            previewWorkWrapper.setContext(workDTO.getContext());
            previewWorkWrapper.setAvgWorkTime((long) workDTO.getAverage().getAvgWorkTime());
            // 参与率
            int finishNum = 0;
            int notFinishNum = 0;
            int totalCommentStuNum = 0;
            List<LearningInfoStuTeacherWorkDTO> finishInfo = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getFinishedInfo();
            if (CollectionUtils.isNotEmpty(finishInfo)) {
                finishNum = finishInfo.size();
                //计算评论总数
                for (LearningInfoStuTeacherWorkDTO stuworkdto : finishInfo) {
                    totalCommentStuNum = totalCommentStuNum + stuworkdto.getCommentOthersTimes();
                }
            }
            List<LearningInfoStuTeacherWorkDTO> notFinishInfo = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getNotFinishedInfo();
            if (CollectionUtils.isNotEmpty(notFinishInfo)) {
                notFinishNum = notFinishInfo.size();
            }
            LearningInfoSubmitStatisWrapper subStatis = new LearningInfoSubmitStatisWrapper();
            subStatis.setTotalStuNum(finishNum + notFinishNum);
            subStatis.setSubmitStuNum(finishNum);
            subStatis.setUnsubmitStuNum(notFinishNum);

            previewWorkWrapper.setTotalCommentStuNum(totalCommentStuNum);
            previewWorkWrapper.setPartakeRate(subStatis);
            previewWorkWrapper.setSubmitStuWorkInfo(finishInfo);
            previewWorkWrapperList.add(previewWorkWrapper);
            totalStudentNum = totalStudentNum + finishNum + notFinishNum;
            totalSubmitStudentNum = totalSubmitStudentNum + finishNum;
        }

        if (totalStudentNum != 0) {
            totalPartakeRate = totalSubmitStudentNum / totalStudentNum;
        }

        LearningInfoPreviewInfoWrapper previewInfoWrapper = new LearningInfoPreviewInfoWrapper();
        previewInfoWrapper.setUnitName(unitName);
        previewInfoWrapper.setTotalPartakeRate(totalPartakeRate);
        previewInfoWrapper.setWorkInfoList(previewWorkWrapperList);

        AjaxResponse response = new AjaxResponse();
        response.setStatus("1");
        response.setWrapper(previewInfoWrapper);
        return response;
    }


    /**
     * @param unitId
     * @param classId
     * @return
     */
    @RequestMapping("learninginfovoice")
    @ResponseBody
    public AjaxResponse getVoiceWork(String teacherId, String unitId, String classId, String schoolId) {

        logger.warn("[入参:teacherId={},unitId={},classId={},classId={},schoolId={}]", teacherId, unitId, classId, classId, schoolId);
        String duserId = CloudTeachEncryptUtil.decrypt(teacherId);
        String dschoolId = CloudTeachEncryptUtil.decrypt(schoolId);
        logger.warn("[入参(解密后):duserId={},dschoolId={}]", duserId, dschoolId);

        /*String duserId = "1030610004";
        String dschoolId = "10306";
        unitId = "030003001007017001001";
        classId = "be4e755a424b11e5825844a8421dc7b3";*/

        // 获取课程名称：
        String unitName = "";
        UnitsDTO units = cloudTeachService.getUnitInfo(unitId);
        if (units != null) {
            unitName = units.getUnitName();
        }

        float totalStudentNum = 0;
        float totalSubmitStudentNum = 0;
        float totalPartakeRate = 0;
        List<LearningInfoVoiceWorkWrapper> voiceWorkWrapperList = new ArrayList<>();
        LearningInfoStuWorkDTO info = learningInfoWorkService.quaryWorkAnswerInfo(duserId, unitId, Constants.WORK_TYPE_VOICE_WORK, classId, dschoolId);
        List<LearningInfoWorkDTO> workDTOList = info.getWorkInfo();
        for (LearningInfoWorkDTO workDTO : workDTOList) {
            LearningInfoVoiceWorkWrapper voiceWorkWrapper = new LearningInfoVoiceWorkWrapper();
            voiceWorkWrapper.setWorkId(workDTO.getWorkId());
            voiceWorkWrapper.setContext(workDTO.getContext());
            voiceWorkWrapper.setAvgPlayFreq((int) workDTO.getAverage().getAvgPlayFreq());
            voiceWorkWrapper.setAvgScore((int) workDTO.getAverage().getAvgScore());
            // 参与率
            int finishNum = 0;
            int notFinishNum = 0;
            List<LearningInfoStuTeacherWorkDTO> finishInfo = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getFinishedInfo();
            if (CollectionUtils.isNotEmpty(finishInfo)) {
                finishNum = finishInfo.size();
            }
            List<LearningInfoStuTeacherWorkDTO> notFinishInfo = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getNotFinishedInfo();
            if (CollectionUtils.isNotEmpty(notFinishInfo)) {
                notFinishNum = notFinishInfo.size();
            }
            LearningInfoSubmitStatisWrapper subStatis = new LearningInfoSubmitStatisWrapper();
            subStatis.setTotalStuNum(finishNum + notFinishNum);
            subStatis.setSubmitStuNum(finishNum);
            subStatis.setUnsubmitStuNum(notFinishNum);
            // 得分等级归类
            LearningInfoScoreLevelWrapper scoreLevel = new LearningInfoScoreLevelWrapper();
            scoreLevel.setLevelAStuNum(0);
            scoreLevel.setLevelBStuNum(0);
            scoreLevel.setLevelCStuNum(0);
            scoreLevel.setLevelDStuNum(0);
            List<LearningInfoStuTeacherWorkDTO> AList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelA();
            if (CollectionUtils.isNotEmpty(AList)) {
                scoreLevel.setLevelAStuNum(AList.size());
            }
            List<LearningInfoStuTeacherWorkDTO> BList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelB();
            if (CollectionUtils.isNotEmpty(BList)) {
                scoreLevel.setLevelBStuNum(BList.size());
            }
            List<LearningInfoStuTeacherWorkDTO> CList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelC();
            if (CollectionUtils.isNotEmpty(CList)) {
                scoreLevel.setLevelCStuNum(CList.size());
            }
            List<LearningInfoStuTeacherWorkDTO> DList = (List<LearningInfoStuTeacherWorkDTO>) workDTO.getScoreLevel().getLevelD();
            if (CollectionUtils.isNotEmpty(DList)) {
                scoreLevel.setLevelDStuNum(DList.size());
            }
            voiceWorkWrapper.setLevelStuNum(scoreLevel);
            voiceWorkWrapper.setPartakeRate(subStatis);
            voiceWorkWrapper.setSubmitStuWorkInfo(finishInfo);
            voiceWorkWrapperList.add(voiceWorkWrapper);
            totalStudentNum = totalStudentNum + finishNum + notFinishNum;
            totalSubmitStudentNum = totalSubmitStudentNum + finishNum;
        }

        if (totalStudentNum != 0) {
            totalPartakeRate = totalSubmitStudentNum / totalStudentNum;
        }

        LearningInfoVoiceInfoWrapper voiceInfoWrapper = new LearningInfoVoiceInfoWrapper();
        voiceInfoWrapper.setUnitName(unitName);
        voiceInfoWrapper.setTotalPartakeRate(totalPartakeRate);
        voiceInfoWrapper.setWorkInfoList(voiceWorkWrapperList);

        AjaxResponse response = new AjaxResponse();
        response.setStatus("1");
        response.setWrapper(voiceInfoWrapper);
        return response;
    }
}
