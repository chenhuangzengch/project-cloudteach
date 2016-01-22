package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.appCenter.dto.ACCourseWaresDTO;
import net.xuele.appCenter.service.ACCourseWaresService;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.CtSynclassWorkPlayMapper;
import net.xuele.cloudteach.persist.CtWorkGatherMapper;
import net.xuele.cloudteach.persist.CtWorkStudentGatherMapper;
import net.xuele.cloudteach.service.LearningInfoWorkService;
import net.xuele.cloudteach.view.LearningInfoStuSynclassWorkView;
import net.xuele.cloudteach.view.LearningInfoStudentView;
import net.xuele.cloudteach.view.LearningInfoSynclassGameView;
import net.xuele.cloudteach.view.LearningInfoWorkView;
import net.xuele.common.exceptions.CloudteachException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/12/1 0001.
 * 学情-作业服务实现
 */
@Service
public class LearningInfoWorkServiceImpl implements LearningInfoWorkService {

    private static Logger logger = LoggerFactory.getLogger(LearningInfoWorkServiceImpl.class);

    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;
    @Autowired
    CtSynclassWorkPlayMapper ctSynclassWorkPlayMapper;
    @Autowired
    CtWorkGatherMapper ctWorkGatherMapper;

    @Autowired
    ACCourseWaresService acCourseWaresService;

    /**
     * @param teacherId 教师ID
     * @param unitId    作业ID
     * @param schoolId  学校ID
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<LearningInfoSynclassPlayGameDTO> quarySynclassGameList(String teacherId, String unitId, String schoolId) throws CloudteachException {

        logger.info("[参数校验:]");
        if (StringUtils.isEmpty(teacherId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        // 返回列表
        List<LearningInfoSynclassPlayGameDTO> dtolist = new ArrayList<>();

        logger.info("[获取该教师({})布置的同步课堂游戏信息:]", teacherId);
        List<LearningInfoSynclassGameView> domlist = ctSynclassWorkPlayMapper.selectSynclassGame(teacherId, unitId, schoolId);
        if (CollectionUtils.isNotEmpty(domlist)) {
            for (LearningInfoSynclassGameView dom : domlist) {
                LearningInfoSynclassPlayGameDTO dto = new LearningInfoSynclassPlayGameDTO();
                // 从appcenter获取同步课堂游戏信息
                ACCourseWaresDTO gameInfo = acCourseWaresService.selectCourseWareByUcId(dom.getGameId());
                dto.setGameName(gameInfo.getcTitle());
                dtolist.add(dto);
            }
        } else {
            logger.error("[没有同步课堂游戏信息]");
        }
        return dtolist;
    }

    /**
     * @param teacherId 教师ID
     * @param unitId    作业ID
     * @param classId   班级ID
     * @param schoolId  学校ID
     * @return
     * @throws CloudteachException
     */
    @Override
    public LearningInfoStuWorkDTO quarySynclassAnswerInfo(String teacherId, String unitId, String classId, String schoolId) throws CloudteachException {

        logger.info("[参数校验:]");
        if (StringUtils.isEmpty(teacherId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        List<LearningInfoWorkDTO> workListdto = new ArrayList<>();

        logger.info("获取课程unitId={}下的所有同步课堂作业:", unitId);
        List<LearningInfoWorkView> workViewList = ctWorkGatherMapper.selectWorkList(unitId, teacherId, Constants.WORK_TYPE_SYNCLASS_WORK, schoolId);
        for (LearningInfoWorkView workView : workViewList) {
            LearningInfoWorkDTO workDTO = new LearningInfoWorkDTO();
            String workId = workView.getWorkId();
            logger.info("[获取该作业workId={}下的全部同步课堂游戏:]", workId);
            List<LearningInfoSynclassGameView> gamelist = ctSynclassWorkPlayMapper.selectSynclassGame(teacherId, workId, schoolId);
            for (LearningInfoSynclassGameView gameView : gamelist) {
                LearningInfoSynclassPlayGameDTO playGameDTO = new LearningInfoSynclassPlayGameDTO();
                // 提交学生信息
                List<LearningInfoStuSynclassWorkDTO> subListdto = new ArrayList<>();
                // 未提交学生信息
                List<LearningInfoStuSynclassWorkDTO> unsubListdto = new ArrayList<>();
                // 85-100分级别列表
                List<LearningInfoStuSynclassWorkDTO> levelAList = new ArrayList<>();
                // 70-85分级别列表
                List<LearningInfoStuSynclassWorkDTO> levelBList = new ArrayList<>();
                // 55-69分级别列表
                List<LearningInfoStuSynclassWorkDTO> levelCList = new ArrayList<>();
                // 55分以下级别列表
                List<LearningInfoStuSynclassWorkDTO> levelDList = new ArrayList<>();
                int totalPlayFreq = 0;
                int totalScore = 0;
                int totalPlayTime = 0;
                int avgPlayFreq = 0;
                int avgScore = 0;
                int avgPlayTime = 0;
                // 从appcenter获取同步课堂游戏信息
                ACCourseWaresDTO gameInfo = acCourseWaresService.selectCourseWareByUcId(gameView.getGameId());
                String gameName = gameInfo.getcTitle();
                String workGameId = gameView.getWorkGameId();
                playGameDTO.setGameName(gameName);
                logger.info("[获取该游戏gameName={},workGameId={}下所有学生的练习信息:]", gameName, workGameId);
                List<LearningInfoStuSynclassWorkView> domlist = ctSynclassWorkPlayMapper.selectSynclassPlay(workGameId, schoolId);
                for (LearningInfoStuSynclassWorkView dom : domlist) {
                    int score = dom.getScore();
                    LearningInfoStuSynclassWorkDTO dto = new LearningInfoStuSynclassWorkDTO();
                    dto.setStudentId(dom.getStudentId());
                    dto.setStudentName(dom.getStudentName());
                    dto.setStudentIcon(dom.getStudentIcon());
                    dto.setPlayFreq(dom.getPlayFreq());
                    dto.setPlayTime(dom.getPlayTime());
                    dto.setScore(score);
                    if (dom.getSubStatus() == 1) {
                        // 计算总练习次数,总得分,总用时
                        totalPlayFreq = totalPlayFreq + dom.getPlayFreq();
                        totalScore = totalScore + dom.getScore();
                        totalPlayTime = totalPlayTime + dom.getPlayTime();
                        logger.info("[获取已提交信息:{}]", dto);
                        subListdto.add(dto);
                        if (score >= 85 && score <= 100) {
                            levelAList.add(dto);
                            logger.info("[获取85-100分学生信息{}:]", dto);
                        } else if (score >= 70 && score <= 84) {
                            levelBList.add(dto);
                            logger.info("[获取70-84分学生信息{}:]", dto);
                        } else if (score >= 55 && score <= 69) {
                            levelCList.add(dto);
                            logger.info("[获取55-69分学生信息{}:]", dto);
                        } else {
                            levelDList.add(dto);
                            logger.info("[获取55分以下学生信息{}:]", dto);
                        }
                    } else {
                        logger.info("[获取未提交信息:{}]", dto);
                        unsubListdto.add(dto);
                    }
                    if (CollectionUtils.isNotEmpty(subListdto)) {
                        avgPlayFreq = totalPlayFreq / subListdto.size();
                        avgScore = totalScore / subListdto.size();
                        avgPlayTime = totalPlayTime / subListdto.size();
                    }
                    playGameDTO.setPlayInfo(subListdto);
                    playGameDTO.setNotPlayInfo(unsubListdto);
                    // 评分等级
                    LearningInfoScoreLevelDTO scoreLevel = new LearningInfoScoreLevelDTO();
                    scoreLevel.setLevelA(levelAList);
                    scoreLevel.setLevelB(levelBList);
                    scoreLevel.setLevelC(levelCList);
                    scoreLevel.setLevelD(levelDList);
                    // 作业平均时间计算
                    LearningInfoAverageDTO average = new LearningInfoAverageDTO();
                    average.setAvgPlayFreq(avgPlayFreq);
                    average.setAvgScore(avgScore);
                    average.setAvgPlayTime(avgPlayTime);

                    workDTO.setPlayGameInfo(playGameDTO);
                    workDTO.setScoreLevel(scoreLevel);
                    workDTO.setAverage(average);
                    workDTO.setWorkId(workId);
                    workDTO.setContext(workView.getContext());
                    workListdto.add(workDTO);
                }
            }
        }
        LearningInfoStuWorkDTO dto = new LearningInfoStuWorkDTO();
        dto.setWorkInfo(workListdto);
        return dto;
    }

    /**
     * @param teacherId 教师ID
     * @param unitId    课程ID
     * @param classId   班级ID
     * @param schoolId  学校ID
     * @return
     * @throws CloudteachException
     */
    @Override
    public LearningInfoStuWorkDTO quaryWorkAnswerInfo(String teacherId, String unitId, int workType, String
            classId, String schoolId) throws CloudteachException {

        logger.info("[参数校验:]");
        if (StringUtils.isEmpty(teacherId) || StringUtils.isEmpty(schoolId) || StringUtils.isEmpty(classId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        List<LearningInfoWorkDTO> workListdto = new ArrayList<>();

        logger.info("获取课程unitId={}下作业类型workType={}的所有作业:", unitId, workType);
        List<LearningInfoWorkView> workViewList = ctWorkGatherMapper.selectWorkList(unitId, teacherId, workType, schoolId);
        for (LearningInfoWorkView workView : workViewList) {
            //如果workView.getWorkClassJson()不包含当前班级，剔除该条数据
            String classJson = workView.getWorkClassJson();
            if (!StringUtils.isBlank(classJson) && (!classJson.contains(classId))) {
                continue;
            }
            // 已完成学生作业信息
            List<LearningInfoStuTeacherWorkDTO> subListdto = new ArrayList<>();
            // 未完成学生作业信息
            List<LearningInfoStuTeacherWorkDTO> unsubListdto = new ArrayList<>();
            // A得分学生信息
            List<LearningInfoStuTeacherWorkDTO> levelA = new ArrayList<>();
            // B得分学生信息
            List<LearningInfoStuTeacherWorkDTO> levelB = new ArrayList<>();
            // C得分学生信息
            List<LearningInfoStuTeacherWorkDTO> levelC = new ArrayList<>();
            // D得分学生信息
            List<LearningInfoStuTeacherWorkDTO> levelD = new ArrayList<>();
            long totalWorkTime = 0;
            long avgWorkTime = 0;

            int totalPlayFreq = 0;
            int totalScore = 0;
            int avgPlayFreq = 0;
            int avgScore = 0;

            LearningInfoWorkDTO workDTO = new LearningInfoWorkDTO();
            String workId = workView.getWorkId();
            logger.info("[获取作业workId={}下全部学生作业信息:]", workId);
            List<LearningInfoStudentView> stuWorkList = ctWorkStudentGatherMapper.selectStudentAnwerInfo(workId, classId, null, schoolId);
            for (LearningInfoStudentView studom : stuWorkList) {
                LearningInfoStuTeacherWorkDTO studto = new LearningInfoStuTeacherWorkDTO();
                BeanUtils.copyProperties(studom, studto);
                if (studom.getSubStatus() == 1) {
                    // 计算总用时
                    Date beginTime = studom.getBeginTime();
                    if (beginTime == null) {
                        // 老数据没有开始时间，默认作业时间为5分钟，并且设置开始时间为提交时间的5分钟之前
                        totalWorkTime = totalWorkTime + Constants.DEFAULT_WORK_TIME;
                        studto.setBeginTime(new Date(studto.getSubTime().getTime() - Constants.DEFAULT_WORK_TIME));
                    } else {
                        totalWorkTime = totalWorkTime + (studom.getSubTime().getTime() - studom.getBeginTime().getTime());
                    }
                    // 计算总得分
                    totalScore = totalScore + studom.getSysScore();
                    // 计算总次数
                    totalPlayFreq = totalPlayFreq + studom.getVoicePlayFreq();
                    logger.info("[获取已完成学生作业信息:{}]", studto);
                    subListdto.add(studto);
                    if (workType == Constants.WORK_TYPE_VOICE_WORK) {
                        logger.info("[按得分等级拆分学生作业信息:]");
                        Integer score = studom.getSysScore();
                        if (score >= 85 && score <= 100) {
                            logger.info("[得到85-100的学生:{}]", studto);
                            levelA.add(studto);
                        } else if (score >= 70 && score <= 84) {
                            logger.info("[得到70-84的学生:{}]", studto);
                            levelB.add(studto);
                        } else if (score >= 55 && score <= 69) {
                            logger.info("[得到55-69的学生:{}]", studto);
                            levelC.add(studto);
                        } else {
                            logger.info("[得到55以下的学生:{}]", studto);
                            levelD.add(studto);
                        }
                    } else {
                        if (studom.getCorrectStatus() == 1) {
                            logger.info("[按得分等级拆分学生作业信息:]");
                            Integer score = studom.getScore();
                            if (score == 1) {
                                logger.info("[得到A的学生:{}]", studto);
                                levelA.add(studto);
                            } else if (score == 2) {
                                logger.info("[得到B的学生:{}]", studto);
                                levelB.add(studto);
                            } else if (score == 3) {
                                logger.info("[得到C的学生:{}]", studto);
                                levelC.add(studto);
                            } else {
                                logger.info("[得到D的学生:{}]", studto);
                                levelD.add(studto);
                            }
                        }
                    }
                } else {
                    logger.info("[获取未完成学生作业信息:{}]", studto);
                    unsubListdto.add(studto);
                }
            }
            if (CollectionUtils.isNotEmpty(subListdto)) {
                avgWorkTime = totalWorkTime / subListdto.size();
                avgPlayFreq = totalPlayFreq / subListdto.size();
                avgScore = totalScore / subListdto.size();
            }
            // 作业平均时间计算
            LearningInfoAverageDTO average = new LearningInfoAverageDTO();
            average.setAvgWorkTime(avgWorkTime);
            average.setAvgPlayFreq(avgPlayFreq);
            average.setAvgScore(avgScore);
            // 评分等级
            LearningInfoScoreLevelDTO scoreLevel = new LearningInfoScoreLevelDTO();
            scoreLevel.setLevelA(levelA);
            scoreLevel.setLevelB(levelB);
            scoreLevel.setLevelC(levelC);
            scoreLevel.setLevelD(levelD);

            workDTO.setFinishedInfo(subListdto);
            workDTO.setNotFinishedInfo(unsubListdto);
            workDTO.setScoreLevel(scoreLevel);
            workDTO.setAverage(average);
            workDTO.setWorkId(workId);
            workDTO.setContext(workView.getContext());
            workListdto.add(workDTO);
        }

        LearningInfoStuWorkDTO dto = new LearningInfoStuWorkDTO();
        dto.setWorkInfo(workListdto);
        return dto;
    }
}
