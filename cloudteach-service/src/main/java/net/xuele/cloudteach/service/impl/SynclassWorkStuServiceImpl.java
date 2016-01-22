package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.appCenter.dto.ACCourseWaresDTO;
import net.xuele.appCenter.service.ACCourseWaresService;
import net.xuele.appCenter.service.TranslateFlashService;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.SynclassWorkService;
import net.xuele.cloudteach.service.SynclassWorkStuService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.*;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import net.xuele.member.dto.UserDTO;
import net.xuele.member.service.StudentService;
import net.xuele.member.service.UserService;
import net.xuele.teacheval.constants.AccessAction;
import net.xuele.teacheval.domain.UserAccessAction;
import net.xuele.teacheval.service.WriteAccessLogService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SynclassWorkStuServiceImpl
 * 学生同步课堂作业服务
 *
 * @author hujx
 * @date 2015/7/7 0002
 */
@Service
public class SynclassWorkStuServiceImpl implements SynclassWorkStuService {

    private static Logger logger = LoggerFactory.getLogger(SynclassWorkStuServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    ACCourseWaresService acCourseWaresService;

    @Autowired
    WorkTapeFilesService workTapeFilesService;

    @Autowired
    TranslateFlashService translateFlashService;

    @Autowired
    WorkStatisticsService workStatisticsService;

    @Autowired
    SynclassWorkService synclassWorkService;

    @Autowired
    StudentWorkListMapper studentWorkListMapper;
    @Autowired
    StudentWorkDetailMapper studentWorkDetailMapper;

    @Autowired
    CtWorkGatherMapper ctWorkGatherMapper;
    @Autowired
    CtWorkStatisticsMapper ctWorkStatisticsMapper;
    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;

    @Autowired
    CtSynclassWorkMapper ctSynclassWorkMapper;
    @Autowired
    CtSynclassWorkGameMapper ctSynclassWorkGameMapper;
    @Autowired
    CtSynclassWorkPlayMapper ctSynclassWorkPlayMapper;
    @Autowired
    CtSynclassWorkStudentMapper ctSynclassWorkStudentMapper;
    @Autowired
    CtSynclassWorkAnswerPraiseMapper ctSynclassWorkAnswerPraiseMapper;
    @Autowired
    CtSynclassWorkAnswerCommentMapper ctSynclassWorkAnswerCommentMapper;
    @Autowired
    CtSynclassWorkStudentStatisticsMapper ctSynclassWorkStudentStatisticsMapper;

    @Autowired
    CtCloudDiskMapper cloudDiskMapper;

    @Autowired
    CtBankItemFilesMapper ctBankItemFilesMapper;

    @Autowired
    StudentService studentService;
    @Autowired
    WriteAccessLogService writeAccessLogService;

    /**
     * 通过作业ID，学生ID，查找学生回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    @Override
    public SynclassWorkStudentDTO getStuAnswerByWorkStu(String workId, String studentId, String schoolId) throws CloudteachException {

        SynclassWorkStudentDTO synclassWorkStudentDTO = new SynclassWorkStudentDTO();

        CtSynclassWorkStudent ctSynclassWorkStudent =
                ctSynclassWorkStudentMapper.getCtSynclassInfoByWorkStudent(workId, studentId, schoolId);
        if (ctSynclassWorkStudent == null) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(ctSynclassWorkStudent, synclassWorkStudentDTO);
        synclassWorkStudentDTO.setContext(StringUtil.getFixedLengthContext(synclassWorkStudentDTO.getContext(), 0));

        return synclassWorkStudentDTO;
    }

    /**
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public BasicWorkInfoViewDTO getBasicWorkInfo(String workId, String studentId, String schoolId) throws CloudteachException {

        BasicWorkInfoViewDTO basicWorkInfoViewDTO = new BasicWorkInfoViewDTO();

        BasicWorkInfoView basicWorkInfoView = studentWorkListMapper.getBasicSynClassWorkInfo(workId, studentId, schoolId);
        if (basicWorkInfoView == null) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(basicWorkInfoView, basicWorkInfoViewDTO);
        return basicWorkInfoViewDTO;
    }

    /**
     * 获取某个学生的同步课堂作业详细
     * (作业基本信息，教师录音信息，游戏信息，学生回答信息，学生玩游戏结果信息,学生作业统计信息)
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public SynclassWorkFinishDetailViewDTO synclassWorkFinishDetail(String workId, String studentId, String schoolId) throws CloudteachException {

        SynclassWorkFinishDetailViewDTO synclassWorkFinishDetailViewDTO = new SynclassWorkFinishDetailViewDTO();
        BasicWorkInfoViewDTO basicWorkInfoViewDTO = new BasicWorkInfoViewDTO();
        List<SynclassWorkGameInfoViewDTO> synclassWorkGameInfoViewDTOList = new ArrayList<>();
        SynclassWorkStudentDTO synclassWorkStudentDTO = new SynclassWorkAnswerViewDTO();
        List<SynclassWorkGamePlayInfoViewDTO> synclassWorkGamePlayInfoViewDTOList = new ArrayList<>();
        SynclassWorkStudentStatisticsDTO synclassWorkStudentStatisticsDTO = new SynclassWorkStudentStatisticsDTO();

        logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(studentId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getMsg(),
                    CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getCode());
        }

        logger.info("[获取同步课堂基本信息：]");
        basicWorkInfoViewDTO = this.getBasicWorkInfo(workId, studentId, schoolId);

        logger.info("[获取教师录音信息：]");
        WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);

        logger.info("[获取同步课堂游戏信息：]");
        List<SynclassWorkGameInfoView> gameInfoList = ctSynclassWorkPlayMapper.getSynclassWorkGameId(workId, studentId, schoolId);
        for (SynclassWorkGameInfoView gameInfo : gameInfoList) {
            SynclassWorkGameInfoViewDTO synclassWorkGameInfoViewDTO = new SynclassWorkGameInfoViewDTO();
            int gameId = gameInfo.getGameId();
            String playId = gameInfo.getPlayId();
            int percentScore = gameInfo.getMaxScore();
            //计算得到页面显示x.x分，小数点后一位
            float floatScore = new BigDecimal(gameInfo.getMaxScore() / 20.00).setScale(1, RoundingMode.HALF_UP).floatValue();
            // 通过gameId(UC_ID)从appCenter中获取游戏信息
            ACCourseWaresDTO acCourseWaresDTO = acCourseWaresService.selectCourseWareByUcId(gameId);
            // appCenter回传信息，userId，unitId，schoolId
            String UID = studentId + "|" + acCourseWaresDTO.getUnitcode() + "|" + schoolId + "|" + workId;

            synclassWorkGameInfoViewDTO.setGameId(gameId);
            synclassWorkGameInfoViewDTO.setCID(acCourseWaresDTO.getcId());
            synclassWorkGameInfoViewDTO.setGameName(acCourseWaresDTO.getcTitle());
            synclassWorkGameInfoViewDTO.setPlayTimes(gameInfo.getPlayTimes());
            synclassWorkGameInfoViewDTO.setPercentScore(percentScore);
            synclassWorkGameInfoViewDTO.setFloatScore(floatScore);
            synclassWorkGameInfoViewDTO.setCReal(acCourseWaresDTO.getcReal());
            synclassWorkGameInfoViewDTO.setPlayId(playId);
            synclassWorkGameInfoViewDTO.setUID(UID);

            synclassWorkGameInfoViewDTOList.add(synclassWorkGameInfoViewDTO);
        }

        logger.info("[获取学生回答信息：]");
        synclassWorkStudentDTO = this.getStuAnswerByWorkStu(workId, studentId, schoolId);

        logger.info("[获取学生完成游戏详细：]");
        List<SynclassWorkGamePlayInfoView> synclassWorkGamePlayInfoViewList =
                ctSynclassWorkPlayMapper.getSynclassWorkGameByWorkStu(workId, studentId, schoolId);
        for (SynclassWorkGamePlayInfoView synclassWorkGamePlayInfoView : synclassWorkGamePlayInfoViewList) {
            SynclassWorkGamePlayInfoViewDTO synclassWorkGamePlayInfoViewDTO = new SynclassWorkGamePlayInfoViewDTO();
            int gameId = synclassWorkGamePlayInfoView.getGameId();
            // 通过gameId(UC_ID)从appCenter中获取游戏信息
            ACCourseWaresDTO acCourseWaresDTO = acCourseWaresService.selectCourseWareByUcId(gameId);
            synclassWorkGamePlayInfoView.setGameName(acCourseWaresDTO.getcTitle());
            BeanUtils.copyProperties(synclassWorkGamePlayInfoView, synclassWorkGamePlayInfoViewDTO);
            synclassWorkGamePlayInfoViewDTOList.add(synclassWorkGamePlayInfoViewDTO);
        }

        logger.info("[获取学生回答统计信息：]");
        CtSynclassWorkStudentStatistics ctSynclassWorkStudentStatistics = ctSynclassWorkStudentStatisticsMapper.selectByPrimaryKey(synclassWorkStudentDTO.getWorkUserId(), schoolId);
        if (ctSynclassWorkStudentStatistics == null) {
            throw new CloudteachException(CloudTeachErrorEnum.ANSWER_STATISTICS_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.ANSWER_STATISTICS_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(ctSynclassWorkStudentStatistics, synclassWorkStudentStatisticsDTO);

        logger.info("[获取学生信息：]");
        UserDTO stuInfo = userService.getByUserId(studentId);

        synclassWorkFinishDetailViewDTO.setBasicWorkInfo(basicWorkInfoViewDTO);
        synclassWorkFinishDetailViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
        synclassWorkFinishDetailViewDTO.setStuAnswerInfo(synclassWorkStudentDTO);
        synclassWorkFinishDetailViewDTO.setSynclassGameInfo(synclassWorkGameInfoViewDTOList);
        synclassWorkFinishDetailViewDTO.setStuGamePlayResult(synclassWorkGamePlayInfoViewDTOList);
        synclassWorkFinishDetailViewDTO.setSynclassWorkStudentStatisticsDTO(synclassWorkStudentStatisticsDTO);
        synclassWorkFinishDetailViewDTO.setStuInfo(stuInfo);

        return synclassWorkFinishDetailViewDTO;
    }

    /**
     * @param studentWorkCommunicationPageRequest
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<StudentWorkCommunicationViewDTO> synclassWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) throws CloudteachException {

        //同步课堂作业的作业交流列表
        String schoolId = studentWorkCommunicationPageRequest.getSchoolId();
        String studentId = studentWorkCommunicationPageRequest.getStudentId();
        String workId = studentWorkCommunicationPageRequest.getWorkId();
        Long time = null;
        int firstReq = 0;
        Date now = new Date();
        if (studentWorkCommunicationPageRequest.getSubTime() != null) {
            time = Long.parseLong(studentWorkCommunicationPageRequest.getSubTime());
        }
        String subTime;

        if (time == null || time == 0) {
            subTime = DateTimeUtil.DateToStringForMycat(new Date());
            firstReq = 1;
        } else {
            subTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        Page page = PageUtils.buildPage(studentWorkCommunicationPageRequest);
        int pagesize = studentWorkCommunicationPageRequest.getPageSize();
        long records = ctSynclassWorkStudentMapper.getSubmitStuRecCount(workId, schoolId);
        logger.info("[获取已提交作业学生数：" + records + "]");

        List<StudentWorkCommunicationViewDTO> studentWorkCommunicationViewDTOList = new ArrayList<>();

        logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(studentId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getMsg(),
                    CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getCode());
        }

        // 如果学生没有完成该作业，则不允许查看大家的作业
        logger.info("[判断学生是否允许进入作业交流：]");
        boolean allowed = workStatisticsService.allowToWorkCommunication(workId, studentId, now, schoolId);
        if (!allowed) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_VIEW_DISCUSSION_FAILED.getMsg(),
                    CloudTeachErrorEnum.WORK_VIEW_DISCUSSION_FAILED.getCode());
        }

        // 获取学生回答信息
        logger.info("[获取学生回答信息：]");
        List<StudentWorkCommunicationView> studentWorkCommunicationViewList =
                ctSynclassWorkStudentMapper.getBasicSynclassWorkStudentAnswerInfo(pagesize, page, workId, studentId, subTime, firstReq, schoolId);

        // 转DTO学生回答信息
        logger.info("[DOMAIN->DTO：]");
        for (StudentWorkCommunicationView studentWorkCommunicationView : studentWorkCommunicationViewList) {
            StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO = new StudentWorkCommunicationViewDTO();
            // 同步课堂表中没有answerId，这里的answerId是workUserId的别名
            String workUserId = studentWorkCommunicationView.getAnswerId();
            String userId = studentWorkCommunicationView.getUserId();
            // 获取记录的点赞数量
            logger.info("[获取作业回答的点赞数：]");
            int praiseCount = ctSynclassWorkAnswerPraiseMapper.getPraiseCountByAnswer(workUserId, schoolId);

            // 获取该用户对记录的点赞状态
            logger.info("[获取当前登录用户对记录的点赞状态：]");
            int praiseStatus = 0;
            int praiseNum = ctSynclassWorkAnswerPraiseMapper.getPraiseCountByUNIKEY(workUserId, studentId, schoolId);
            if (praiseNum != 0) {
                praiseStatus = 1;
            }

            logger.info("[获取学生班级信息：]");
            ClassInfoDTO classInfo = studentService.queryUserClass(userId);

            // 获取该作业下所有学生的同步课堂游戏信息
            logger.info("[获取该作业下学生的同步课堂游戏信息：]");
            List<SynclassWorkGamePlayInfoView> synclassWorkGamePlayInfoViewList =
                    ctSynclassWorkPlayMapper.getSynclassWorkGameInfo(workUserId, schoolId);
            // 获取评论信息
            logger.info("[获取作业回答评论信息：]");
            List<WorkAnswerCommentView> synclassWorkAnswerCommNameViewList =
                    ctSynclassWorkAnswerCommentMapper.getAnswerCommentsInfo(workUserId, Constants.MIN_COMMENT_NUM, schoolId);

            List<SynclassWorkGamePlayInfoViewDTO> synclassWorkGamePlayInfoViewDTOList = new ArrayList<>();
            List<WorkAnswerCommentViewDTO> synclassWorkAnswerCommNameViewDTOList = new ArrayList<>();

            for (SynclassWorkGamePlayInfoView synclassWorkGamePlayInfoView : synclassWorkGamePlayInfoViewList) {
                SynclassWorkGamePlayInfoViewDTO synclassWorkGamePlayInfoViewDTO = new SynclassWorkGamePlayInfoViewDTO();
                int gameId = synclassWorkGamePlayInfoView.getGameId();
                // 通过gameId(UC_ID)从appCenter中获取游戏信息
                ACCourseWaresDTO acCourseWaresDTO = acCourseWaresService.selectCourseWareByUcId(gameId);
                synclassWorkGamePlayInfoView.setGameName(acCourseWaresDTO.getcTitle());
                BeanUtils.copyProperties(synclassWorkGamePlayInfoView, synclassWorkGamePlayInfoViewDTO);
                synclassWorkGamePlayInfoViewDTOList.add(synclassWorkGamePlayInfoViewDTO);
            }

            for (WorkAnswerCommentView synclassWorkAnswerCommNameView : synclassWorkAnswerCommNameViewList) {
                WorkAnswerCommentViewDTO synclassWorkAnswerCommNameViewDTO = new WorkAnswerCommentViewDTO();
                BeanUtils.copyProperties(synclassWorkAnswerCommNameView, synclassWorkAnswerCommNameViewDTO);
                synclassWorkAnswerCommNameViewDTO.setContext(StringUtil.getFixedLengthContext(synclassWorkAnswerCommNameViewDTO.getContext(), 0));
                synclassWorkAnswerCommNameViewDTOList.add(synclassWorkAnswerCommNameViewDTO);
            }

            BeanUtils.copyProperties(studentWorkCommunicationView, studentWorkCommunicationViewDTO);
            studentWorkCommunicationViewDTO.setAnswerContext(StringUtil.getFixedLengthContext(studentWorkCommunicationViewDTO.getAnswerContext(), 0));
            studentWorkCommunicationViewDTO.setPraiseCount(praiseCount);
            studentWorkCommunicationViewDTO.setPraiseStatus(praiseStatus);
            studentWorkCommunicationViewDTO.setClassInfo(classInfo.getClassName());
            studentWorkCommunicationViewDTO.setSynclassWorkGamePlayInfoViewDTOList(synclassWorkGamePlayInfoViewDTOList);
            studentWorkCommunicationViewDTO.setWorkAnswerCommentViewDTOList(synclassWorkAnswerCommNameViewDTOList);
            studentWorkCommunicationViewDTOList.add(studentWorkCommunicationViewDTO);
        }


        PageResponse<StudentWorkCommunicationViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(studentWorkCommunicationPageRequest, response);
        response.setRows(studentWorkCommunicationViewDTOList);
        response.setRecords(records);
        return response;

    }

    /**
     * answerId下的所有评论(limit 100)
     *
     * @param workUserId
     * @param schoolId
     * @return
     */
    @Override
    public List<WorkAnswerCommentViewDTO> getMoreComments(String workUserId, String schoolId, int limitRange) {

        List<WorkAnswerCommentView> synclassWorkAnswerCommNameViewList =
                ctSynclassWorkAnswerCommentMapper.getAnswerCommentsInfo(workUserId, limitRange, schoolId);
        List<WorkAnswerCommentViewDTO> synclassWorkAnswerCommNameViewDTOList = new ArrayList<>();
        for (WorkAnswerCommentView synclassWorkAnswerCommNameView : synclassWorkAnswerCommNameViewList) {
            WorkAnswerCommentViewDTO synclassWorkAnswerCommNameViewDTO = new WorkAnswerCommentViewDTO();
            BeanUtils.copyProperties(synclassWorkAnswerCommNameView, synclassWorkAnswerCommNameViewDTO);
            synclassWorkAnswerCommNameViewDTO.setContext(StringUtil.getFixedLengthContext(synclassWorkAnswerCommNameViewDTO.getContext(), 0));
            synclassWorkAnswerCommNameViewDTOList.add(synclassWorkAnswerCommNameViewDTO);
        }
        return synclassWorkAnswerCommNameViewDTOList;
    }

    /**
     * 学生提交同步课堂作业
     *
     * @param synclassWorkStudentDTO
     */
    @Override
    public void handInMySynclassWork(SynclassWorkStudentDTO synclassWorkStudentDTO) throws CloudteachException {

        String workId = synclassWorkStudentDTO.getWorkId();
        String studentId = synclassWorkStudentDTO.getUserId();
        String schoolId = synclassWorkStudentDTO.getSchoolId();

        // 获取作业信息
        CtSynclassWork ctSynclassWork = ctSynclassWorkMapper.selectByPrimaryKey(workId, schoolId);
        // 判断提交作业的时候，作业是否有效
        if (ctSynclassWork == null || ctSynclassWork.getStatus() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        // 超过最晚提交时间，作业不能提交
        if (ctSynclassWork.getEndTime().getTime() < new Date().getTime()) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "：超过最晚交作业时间！",
                    CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
        }

        // 更新 同步课堂作业学生表 ct_synclass_work_student
        CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.getCtSynclassInfoByWorkStudent(workId, studentId, schoolId);
        if (ctSynclassWorkStudent == null || ctSynclassWorkStudent.getStatus() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }

        int subStatus = ctSynclassWorkStudent.getSubStatus();
        String workUserId = ctSynclassWorkStudent.getWorkUserId();

        // 获取学生未完成的游戏gameId
        List<Integer> gameIds = ctSynclassWorkPlayMapper.getNotFinishedGames(workId, studentId, schoolId);
        if (!CollectionUtils.isEmpty(gameIds) && gameIds.size() > 0) {
            List<String> games = new ArrayList<>();
            for (int gameId : gameIds) {
                ACCourseWaresDTO acCourseWaresDTO = acCourseWaresService.selectCourseWareByUcId(gameId);
                games.add(acCourseWaresDTO.getcTitle());
            }
            throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "你还没有完成以下游戏：" + games,
                    CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
        }
        // 如果学生已经提交作业，则不允许再次提交
        if (subStatus == 1) {
            throw new CloudteachException(CloudTeachErrorEnum.ALREADYSUM.getMsg(), CloudTeachErrorEnum.ALREADYSUM.getCode());
        }

        String answerContext = synclassWorkStudentDTO.getContext();
        // 学生回答内容不能为空
        if (answerContext == null || answerContext.equals("")) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "：请填写回答内容！",
                    CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
        }
        // 字数不能超过指定长度
        if (!StringUtil.checkContextLength(answerContext)) {
            throw new CloudteachException(CloudTeachErrorEnum.OVERMAXLENGTH.getMsg(), CloudTeachErrorEnum.OVERMAXLENGTH.getCode());
        }

        Date subtime = new Date();
        ctSynclassWorkStudent.setContext(answerContext);
        ctSynclassWorkStudent.setSubTime(subtime);
        ctSynclassWorkStudent.setSubStatus(1);
        ctSynclassWorkStudentMapper.updateByPrimaryKey(ctSynclassWorkStudent);

        // 更新 ct_synclass_work_play
        ctSynclassWorkPlayMapper.updateAfterSubmit(workUserId, subtime, schoolId);

        // 更新 作业学生汇总表 ct_work_student_gather
        CtWorkStudentGather ctWorkStudentGather = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkIdAndUserId(workId, studentId, schoolId);
        ctWorkStudentGather.setSubStatus(1);
        ctWorkStudentGather.setSubTime(subtime);
        ctWorkStudentGatherMapper.updateByPrimaryKey(ctWorkStudentGather);

        // 更新 作业统计表 ct_work_statistics
        int unSubmitStuNumbers = workStatisticsService.updateSubmitStudentNum(workId, schoolId);

        // 更新作业作业完成状态
        if (unSubmitStuNumbers == 0) {
            ctSynclassWorkMapper.updateFinishStatusByWorkId(2, workId, schoolId);
        } else {
            ctSynclassWorkMapper.updateFinishStatusByWorkId(1, workId, schoolId);
        }
        try {
            logger.info("[调用日志服务接口]");
            writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
                    .accessAction(AccessAction.HomeworkFinish)
                    .userId(studentId)
                    .occurredTime(subtime.getTime())
                    .build());
        } catch (Exception e) {
            logger.error("[写入日志失败:{}]", e.getMessage());
        }
    }

    @Override
    public void synclassGamePlayResult(String playId, String schoolId, String user_id, int score) {

        // 获取同步课堂作业学生练习表信息
        CtSynclassWorkPlay ctSynclassWorkPlay = ctSynclassWorkPlayMapper.selectByPrimaryKey(playId, schoolId);

        // 判断这次获得的分数是否是最高分数
        int maxScore = ctSynclassWorkPlay.getMaxScore();
        if (maxScore < score) {
            ctSynclassWorkPlay.setMaxScore(score);
        }
        ctSynclassWorkPlay.setPlayTimes(ctSynclassWorkPlay.getPlayTimes() + 1);
        ctSynclassWorkPlay.setFinishStatus(1);
        ctSynclassWorkPlay.setUpdateUserId(user_id);
        ctSynclassWorkPlay.setSubTime(new Date());
        ctSynclassWorkPlay.setUpdateTime(new Date());
        // 更新游戏结果
        ctSynclassWorkPlayMapper.updateByPrimaryKey(ctSynclassWorkPlay);

    }

    /**
     * 点赞
     *
     * @param answerId
     * @param userId
     * @param schoolId
     * @param userType
     */
    @Override
    public void praise(String answerId, String userId, String schoolId, int userType) {
        // 调用教师作业服务点赞方法
        logger.info("[学生对以下回答点赞, 回答ID: " + answerId + "]");
        synclassWorkService.praise(answerId, userId, schoolId, userType);
    }

    /**
     * 取消点赞
     *
     * @param answerId
     * @param userId
     * @param schoolId
     * @param userType
     */
    @Override
    public void cancelPraise(String answerId, String userId, String schoolId, int userType) {
        // 调用教师作业服务取消点赞方法
        logger.info("[学生取消对以下回答的点赞, 回答ID: " + answerId + "]");
        synclassWorkService.unpraise(answerId, userId, schoolId, userType);
    }

    /**
     * 评论
     *
     * @param answerId
     * @param userId
     * @param schoolId
     * @param context
     * @param userType
     */
    @Override
    public String comment(String answerId, String userId, String schoolId, String context, int userType) {
        // 调用教师作业服务评论方法
        logger.info("[学生对以下回答评论, 回答ID: " + answerId + "], 评论内容: " + context + "]");
        return synclassWorkService.comment(answerId, userId, schoolId, context, userType);
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @param userId
     * @param schoolId
     */
    @Override
    public void delComment(String commentId, String userId, String schoolId) {
        // 调用教师作业服务评论方法
        logger.info("[学生删除对以下回答的评论, 评论ID: +" + commentId + "]");
        synclassWorkService.delStuCommont(commentId, userId, schoolId);
    }

    /**
     * 根据workUserId获取学生作业回答信息
     *
     * @param workUserId
     * @param schoolId
     * @return
     */
    @Override
    public SynclassWorkStudentDTO getSynclassWorkAnswerByAnswerId(String workUserId, String schoolId) {
        CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.selectByPrimaryKey(workUserId, schoolId);
        if (ctSynclassWorkStudent == null) {
            return null;
        }
        SynclassWorkStudentDTO synclassWorkStudentDTO = new SynclassWorkStudentDTO();
        BeanUtils.copyProperties(ctSynclassWorkStudent, synclassWorkStudentDTO);
        return synclassWorkStudentDTO;
    }

    /**
     * 获取某个用户对应某个学生作业的点赞状态信息
     *
     * @param workUserId
     * @param userId
     * @param schoolId
     * @return
     */
    @Override
    public SynclassWorkAnswerPraiseDTO getSynclassWorkStuPraiseStatusByUserId(String workUserId, String userId, String schoolId) {
        CtSynclassWorkAnswerPraise ctSynclassWorkAnswerPraise = ctSynclassWorkAnswerPraiseMapper.selectByPrimaryKey(workUserId, userId, schoolId);
        if (ctSynclassWorkAnswerPraise == null) {
            return null;
        }
        SynclassWorkAnswerPraiseDTO synclassWorkAnswerPraiseDTO = new SynclassWorkAnswerPraiseDTO();
        BeanUtils.copyProperties(ctSynclassWorkAnswerPraise, synclassWorkAnswerPraiseDTO);
        return synclassWorkAnswerPraiseDTO;
    }

    /**
     * @param answerId
     */
    @Override
    public void delWorkAnswer(String answerId) {

    }
}