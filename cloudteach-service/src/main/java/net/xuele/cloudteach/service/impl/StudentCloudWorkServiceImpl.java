package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.appCenter.dto.ACCourseWaresDTO;
import net.xuele.appCenter.service.ACCourseWaresService;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkPageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.MagicQuestionService;
import net.xuele.cloudteach.service.StudentCloudWorkService;
import net.xuele.cloudteach.service.WorkTapeFilesService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.StudentWorkListView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/7/10 0010
 * 学生-云作业服务
 */
@Service
public class StudentCloudWorkServiceImpl implements StudentCloudWorkService {

    private static Logger logger = LoggerFactory.getLogger(StudentCloudWorkServiceImpl.class);

    @Autowired
    ACCourseWaresService acCourseWaresService;

    @Autowired
    MagicQuestionService magicQuestionService;

    @Autowired
    StudentWorkListMapper studentWorkListMapper;
    @Autowired
    StudentWorkDetailMapper studentWorkDetailMapper;

    @Autowired
    CtWorkStatisticsMapper ctWorkStatisticsMapper;
    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;

    @Autowired
    CtTeacherWorkItemMapper ctTeacherWorkItemMapper;
    @Autowired
    CtTeacherWorkStudentMapper ctTeacherWorkStudentMapper;
    @Autowired
    CtTeacherWorkItemAnswerMapper ctTeacherWorkItemAnswerMapper;
    @Autowired
    CtTeacherWorkItemAnswerFileMapper ctTeacherWorkItemAnswerFileMapper;
    @Autowired
    CtTeacherWorkItemAnswerPraiseMapper ctTeacherWorkItemAnswerPraiseMapper;
    @Autowired
    CtTeacherWorkItemAnswerCommentMapper ctTeacherWorkItemAnswerCommentMapper;

    @Autowired
    CtMagicWorkMapper ctMagicWorkMapper;
    @Autowired
    CtMagicWorkAnswerMapper ctMagicWorkAnswerMapper;
    @Autowired
    CtMagicWorkChallengeMapper ctMagicWorkChallengeMapper;
    @Autowired
    CtMagicWorkAnswerFilesMapper ctMagicWorkAnswerFilesMapper;
    @Autowired
    CtMagicWorkAnswerPraiseMapper ctMagicWorkAnswerPraiseMapper;
    @Autowired
    CtMagicWorkAnswerCommentMapper ctMagicWorkAnswerCommentMapper;

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
    CtCloudDiskMapper cloudDiskMapper;

    @Autowired
    CtBankItemFilesMapper ctBankItemFilesMapper;

    @Autowired
    WorkTapeFilesService workTapeFilesService;

    /**
     * @param studentWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<StudentWorkListViewDTO> queryToDoWorkList(StudentWorkPageRequest studentWorkPageRequest) throws CloudteachException {

        logger.info("[获取学生待完成列表：");
        String studentId = studentWorkPageRequest.getStudentId();
        String schoolId = studentWorkPageRequest.getSchoolId();
        String subjectId = studentWorkPageRequest.getSubjectId();
        int workType = studentWorkPageRequest.getWorkType();
        if (StringUtils.isEmpty(studentWorkPageRequest.getPublishTime())) {
            studentWorkPageRequest.setPublishTime("0");
        }
        Long time = Long.valueOf(studentWorkPageRequest.getPublishTime());
        String publishTime;

        if (studentId == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：学生ID不能为空！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        if (time == null || time == 0) {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        if (subjectId != null && "".equals(subjectId)) {
            subjectId = null;
        }

        Page page = PageUtils.buildPage(studentWorkPageRequest);
        int pagesize = studentWorkPageRequest.getPageSize();
        // 获取记录数
        long records = studentWorkListMapper.getTodoListRecordCount(studentId, subjectId, workType, schoolId);
        logger.info("[获取待完成条数：" + records + "]");

        List<StudentWorkListViewDTO> studentWorkListViewDTOList = new ArrayList<>();
        logger.info("[获取基础列表信息：StudentWorkListView]");
        List<StudentWorkListView> studentWorkListViewList =
                studentWorkListMapper.myToDoList(pagesize, page, studentId, subjectId, workType, publishTime, schoolId);

        logger.info("[获取待完成list条数：" + studentWorkListViewList.size() + "]");

        logger.info("DOMAIN对象->DTO对象");
        for (StudentWorkListView studentWorkListView : studentWorkListViewList) {
            String workId = studentWorkListView.getWorkId();
            WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);
            StudentWorkListViewDTO studentWorkListViewDTO = new StudentWorkListViewDTO();
            BeanUtils.copyProperties(studentWorkListView, studentWorkListViewDTO);
            // 页面描述字数限制
            studentWorkListViewDTO.setContext(StringUtil.getFixedLengthContext(studentWorkListView.getContext(), 1));

            if (workTapeFilesDTO != null) {
                logger.info("获取教师录音附件信息");
                studentWorkListViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
            }
            studentWorkListViewDTOList.add(studentWorkListViewDTO);
        }

        PageResponse<StudentWorkListViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(studentWorkPageRequest, response);
        response.setRows(studentWorkListViewDTOList);
        response.setRecords(records);
        return response;
    }

    /**
     * @param studentWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<StudentWorkListViewDTO> queryPrepList(StudentWorkPageRequest studentWorkPageRequest) throws CloudteachException {

        logger.info("[获取学生预习列表：");
        String schoolId = studentWorkPageRequest.getSchoolId();
        String studentId = studentWorkPageRequest.getStudentId();
        String subjectId = studentWorkPageRequest.getSubjectId();
        String studentName = studentWorkPageRequest.getStudentName();
        String studentHeadIcon = studentWorkPageRequest.getStudentHeadIcon();
        if (StringUtils.isEmpty(studentWorkPageRequest.getPublishTime())) {
            studentWorkPageRequest.setPublishTime("0");
        }
        Long time = Long.valueOf(studentWorkPageRequest.getPublishTime());
        String publishTime;

        if (studentId == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：学生ID不能为空！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        if (time == null || time == 0) {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        if (subjectId != null && "".equals(subjectId)) {
            subjectId = null;
        }

        Page page = PageUtils.buildPage(studentWorkPageRequest);
        int pagesize = studentWorkPageRequest.getPageSize();
        // 获取记录数
        long records = studentWorkListMapper.getPrepListRecordCount(studentId, subjectId, schoolId);
        logger.info("[获取预习条数：" + records + "]");

        List<StudentWorkListViewDTO> studentWorkListViewDTOList = new ArrayList<>();
        logger.info("[获取基础列表信息：StudentWorkListView]");
        List<StudentWorkListView> studentWorkListViewList =
                studentWorkListMapper.myPrepList(pagesize, page, studentId, subjectId, publishTime, schoolId);

        logger.info("[获取预习list条数：" + studentWorkListViewList.size() + "]");

        // domain转DTO
        logger.info("DOMAIN对象->DTO对象");
        for (StudentWorkListView studentWorkListView : studentWorkListViewList) {
            StudentWorkListViewDTO studentWorkListViewDTO = new StudentWorkListViewDTO();
            BeanUtils.copyProperties(studentWorkListView, studentWorkListViewDTO);

            // 页面描述字数限制
            studentWorkListViewDTO.setContext(StringUtil.getFixedLengthContext(studentWorkListView.getContext(), 1));

            String workId = studentWorkListView.getWorkId();

            // 获取教师录音信息
            WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);

            // 获得学生回答情况
            logger.info("通过作业ID，学生ID，获得该学生对于该作业的回答情况");
            CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer =
                    ctTeacherWorkItemAnswerMapper.getTeacherWorkItemAnswerByWorkStudentId(workId, studentId, schoolId);

            if (ctTeacherWorkItemAnswer != null) {
                // 学生已经提交作业，需要附件信息以及学生回答信息
                if (ctTeacherWorkItemAnswer.getSubStatus() == 1) {
                    logger.info("如果该学生已经提交该作业，获取学生回答的相关信息");
                    // 页面描述字数限制
                    ctTeacherWorkItemAnswer.setContext(StringUtil.getFixedLengthContext(ctTeacherWorkItemAnswer.getContext(), 1));
                    // 1,获取相关附件信息
                    String answerId = ctTeacherWorkItemAnswer.getAnswerId();
                    logger.info("1，获取回答附件信息");
                    List<CtTeacherWorkItemAnswerFile> ctTeacherWorkItemAnswerFileList =
                            ctTeacherWorkItemAnswerFileMapper.getAnswerFileInfoByAnswerId(answerId, schoolId);
                    List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = new ArrayList<>();
                    // domain附件对象转DTO附件对象
                    for (CtTeacherWorkItemAnswerFile ctTeacherWorkItemAnswerFile : ctTeacherWorkItemAnswerFileList) {
                        TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO = new TeacherWorkItemAnswerFileDTO();
                        BeanUtils.copyProperties(ctTeacherWorkItemAnswerFile, teacherWorkItemAnswerFileDTO);
                        teacherWorkItemAnswerFileDTOList.add(teacherWorkItemAnswerFileDTO);
                    }
                    // 2,获取相关回答信息
                    logger.info("2，获取回答题目信息");
                    studentWorkListViewDTO.setSubmitTime(ctTeacherWorkItemAnswer.getSubTime());
                    studentWorkListViewDTO.setAnswerContext(ctTeacherWorkItemAnswer.getContext());
                    studentWorkListViewDTO.setScore(ctTeacherWorkItemAnswer.getScore());
                    studentWorkListViewDTO.setTeacherWorkItemAnswerFileDTOList(teacherWorkItemAnswerFileDTOList);
                }
            }
            studentWorkListViewDTO.setStudentName(studentName);
            studentWorkListViewDTO.setStudentHeadIcon(studentHeadIcon);
            studentWorkListViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
            studentWorkListViewDTOList.add(studentWorkListViewDTO);
        }

        PageResponse<StudentWorkListViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(studentWorkPageRequest, response);
        response.setRows(studentWorkListViewDTOList);
        response.setRecords(records);
        return response;

    }

    /**
     * @param studentWorkPageRequest
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<StudentWorkListViewDTO> queryWorkList(StudentWorkPageRequest studentWorkPageRequest) throws CloudteachException {

        logger.info("[获取学生作业列表：");
        String schoolId = studentWorkPageRequest.getSchoolId();
        String studentId = studentWorkPageRequest.getStudentId();
        String subjectId = studentWorkPageRequest.getSubjectId();
        int workType = studentWorkPageRequest.getWorkType();
        String studentName = studentWorkPageRequest.getStudentName();
        String studentHeadIcon = studentWorkPageRequest.getStudentHeadIcon();
        if (StringUtils.isEmpty(studentWorkPageRequest.getPublishTime())) {
            studentWorkPageRequest.setPublishTime("0");
        }
        Long time = Long.valueOf(studentWorkPageRequest.getPublishTime());
        String publishTime;

        if (studentId == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：学生ID不能为空！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        if (time == null || time == 0) {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date());
        } else {
            publishTime = DateTimeUtil.DateToStringForMycat(new Date(time));
        }

        if (subjectId != null && "".equals(subjectId)) {
            subjectId = null;
        }

        Page page = PageUtils.buildPage(studentWorkPageRequest);
        int pagesize = studentWorkPageRequest.getPageSize();
        // 获取记录数
        long records = studentWorkListMapper.getWorkListRecordCount(studentId, subjectId, workType, schoolId);
        logger.info("[获取作业条数：" + records + "]");

        List<StudentWorkListViewDTO> studentWorkListViewDTOList = new ArrayList<>();
        logger.info("[获取基础列表信息：StudentWorkListView]");
        List<StudentWorkListView> studentWorkListViewList =
                studentWorkListMapper.myWorkList(pagesize, page, studentId, subjectId, workType, publishTime, schoolId);

        logger.info("[获取作业list条数：" + studentWorkListViewList.size() + "]");

        logger.info("DOMAIN对象->DTO对象");
        for (StudentWorkListView studentWorkListView : studentWorkListViewList) {
            // domain转DTO
            StudentWorkListViewDTO studentWorkListViewDTO = new StudentWorkListViewDTO();
            BeanUtils.copyProperties(studentWorkListView, studentWorkListViewDTO);

            // 页面描述字数限制
            studentWorkListViewDTO.setContext(StringUtil.getFixedLengthContext(studentWorkListView.getContext(), 1));

            // 内部获取作业类型
            workType = studentWorkListView.getWorkType();
            // 获得学生回答情况
            String workId = studentWorkListView.getWorkId();

            // 获取教师录音信息
            WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);

            /**
             * 电子作业和语音作业 学生回答处理
             */
            if (workType == Constants.WORK_TYPE_EXERCISE_WORK || workType == Constants.WORK_TYPE_VOICE_WORK
                    || workType == Constants.WORK_TYPE_EXTRA_WORK) {
                logger.info("[教师作业处理：]");
                CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer =
                        ctTeacherWorkItemAnswerMapper.getTeacherWorkItemAnswerByWorkStudentId(workId, studentId, schoolId);
                if (ctTeacherWorkItemAnswer != null) {
                    // 学生已经提交作业，需要附件信息
                    if (ctTeacherWorkItemAnswer.getSubStatus() == 1) {
                        // 1,获取相关附件信息
                        String answerId = ctTeacherWorkItemAnswer.getAnswerId();
                        // 页面描述字数限制
                        ctTeacherWorkItemAnswer.setContext(StringUtil.getFixedLengthContext(ctTeacherWorkItemAnswer.getContext(), 1));

                        List<CtTeacherWorkItemAnswerFile> ctTeacherWorkItemAnswerFileList =
                                ctTeacherWorkItemAnswerFileMapper.getAnswerFileInfoByAnswerId(answerId, schoolId);
                        List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = new ArrayList<>();
                        // domain附件对象转DTO附件对象
                        for (CtTeacherWorkItemAnswerFile ctTeacherWorkItemAnswerFile : ctTeacherWorkItemAnswerFileList) {
                            TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO = new TeacherWorkItemAnswerFileDTO();
                            BeanUtils.copyProperties(ctTeacherWorkItemAnswerFile, teacherWorkItemAnswerFileDTO);
                            teacherWorkItemAnswerFileDTOList.add(teacherWorkItemAnswerFileDTO);
                        }
                        // 2,获取相关回答信息
                        studentWorkListViewDTO.setSubmitTime(ctTeacherWorkItemAnswer.getSubTime());
                        studentWorkListViewDTO.setAnswerContext(ctTeacherWorkItemAnswer.getContext());
                        // 如果是口语作业，score需要设置成系统评分
                        if (workType == Constants.WORK_TYPE_VOICE_WORK) {
                            studentWorkListViewDTO.setScore(ctTeacherWorkItemAnswer.getSysScore());
                        }
                        studentWorkListViewDTO.setTeacherWorkItemAnswerFileDTOList(teacherWorkItemAnswerFileDTOList);
                    }
                }
                studentWorkListViewDTO.setStudentName(studentName);
                studentWorkListViewDTO.setStudentHeadIcon(studentHeadIcon);
                studentWorkListViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
                studentWorkListViewDTOList.add(studentWorkListViewDTO);
            }
            /**
             * 提分宝作业 学生回答处理
             */
            if (workType == Constants.WORK_TYPE_MAGIC_WORK) {
                logger.info("[提分宝处理：]");
                CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.getCtMagicWorkAnswerByWorkStudentId(workId, studentId, schoolId);
                if (ctMagicWorkAnswer != null) {
                    // 学生已经提交作业，需要附件信息,挑战信息记录
                    if (ctMagicWorkAnswer.getSubStatus() == 1) {
                        String answerId = ctMagicWorkAnswer.getAnswerId();
                        String challengeId = ctMagicWorkAnswer.getChallengeId();
                        // 页面描述字数限制
                        ctMagicWorkAnswer.setContext(StringUtil.getFixedLengthContext(ctMagicWorkAnswer.getContext(), 1));
                        // 获得提分宝挑战记录信息
                        CtMagicWorkChallenge ctMagicWorkChallenge = ctMagicWorkChallengeMapper.getMagicWorkChallengeInfo(challengeId, schoolId);
                        MagicWorkChallengeTimesViewDTO magicWorkChallengeTimesViewDTO = new MagicWorkChallengeTimesViewDTO();
                        BeanUtils.copyProperties(ctMagicWorkChallenge, magicWorkChallengeTimesViewDTO);
                        // 设置挑战次数
                        magicWorkChallengeTimesViewDTO.setChallengeTimes(ctMagicWorkChallengeMapper.getChallengeTimesByAnswerId(workId, schoolId));
                        // 获取提分宝回答附件信息
                        List<CtMagicWorkAnswerFiles> ctMagicWorkAnswerFilesList =
                                ctMagicWorkAnswerFilesMapper.getAnswerFileInfoByAnswerId(answerId, schoolId);
                        List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList = new ArrayList<>();
                        // domain转换成DTO
                        for (CtMagicWorkAnswerFiles ctMagicWorkAnswerFiles : ctMagicWorkAnswerFilesList) {
                            MagicWorkAnswerFilesDTO magicWorkAnswerFilesDTO = new MagicWorkAnswerFilesDTO();
                            BeanUtils.copyProperties(ctMagicWorkAnswerFiles, magicWorkAnswerFilesDTO);
                            magicWorkAnswerFilesDTOList.add(magicWorkAnswerFilesDTO);
                        }
                        studentWorkListViewDTO.setSubmitTime(ctMagicWorkAnswer.getSubTime());
                        studentWorkListViewDTO.setAnswerContext(ctMagicWorkAnswer.getContext());
                        studentWorkListViewDTO.setScore(ctMagicWorkAnswer.getScore());
                        studentWorkListViewDTO.setMagicWorkChallengeTimesViewDTO(magicWorkChallengeTimesViewDTO);
                        studentWorkListViewDTO.setMagicWorkAnswerFilesDTOList(magicWorkAnswerFilesDTOList);
                    }
                }
                studentWorkListViewDTO.setStudentName(studentName);
                studentWorkListViewDTO.setStudentHeadIcon(studentHeadIcon);
                studentWorkListViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
                studentWorkListViewDTOList.add(studentWorkListViewDTO);
            }
            /**
             * 同步课堂作业 学生回答处理
             */
            if (workType == Constants.WORK_TYPE_SYNCLASS_WORK) {
                logger.info("[同步课堂处理：]");
                CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.getCtSynclassInfoByWorkStudent(workId, studentId, schoolId);
                if (ctSynclassWorkStudent != null) {
                    // 学生已经提交作业，需要同步课堂游戏记录
                    if (ctSynclassWorkStudent.getSubStatus() == 1) {
                        String workUserId = ctSynclassWorkStudent.getWorkUserId();
                        // 页面描述字数限制
                        ctSynclassWorkStudent.setContext(StringUtil.getFixedLengthContext(ctSynclassWorkStudent.getContext(), 1));
                        // 获取教师录音信息
                        //WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);
                        List<CtSynclassWorkPlay> ctSynclassWorkPlayList = ctSynclassWorkPlayMapper.getSynclassWorkPlayInfoList(workUserId, schoolId);
                        List<SynclassWorkPlayViewDTO> synclassWorkPlayViewDTOList = new ArrayList<>();
                        // domain转DTO
                        for (CtSynclassWorkPlay ctSynclassWorkPlay : ctSynclassWorkPlayList) {
                            CtSynclassWorkGame ctSynclassWorkGame = ctSynclassWorkGameMapper.selectByPrimaryKey(ctSynclassWorkPlay.getWorkGameId(), schoolId);
                            int gameId = ctSynclassWorkGame.getGameId();
                            SynclassWorkPlayViewDTO synclassWorkPlayViewDTO = new SynclassWorkPlayViewDTO();
                            BeanUtils.copyProperties(ctSynclassWorkPlay, synclassWorkPlayViewDTO);
                            // 调用appCenter服务的到游戏信息
                            ACCourseWaresDTO acCourseWaresDTO = acCourseWaresService.selectCourseWareByUcId(gameId);
                            synclassWorkPlayViewDTO.setcReal(acCourseWaresDTO.getcReal());
                            synclassWorkPlayViewDTO.setGameName(acCourseWaresDTO.getcTitle());
                            synclassWorkPlayViewDTOList.add(synclassWorkPlayViewDTO);
                        }
                        studentWorkListViewDTO.setSubmitTime(ctSynclassWorkStudent.getSubTime());
                        studentWorkListViewDTO.setAnswerContext(ctSynclassWorkStudent.getContext());
                        studentWorkListViewDTO.setSynclassWorkPlayViewDTOList(synclassWorkPlayViewDTOList);
                    }

                }
                studentWorkListViewDTO.setStudentName(studentName);
                studentWorkListViewDTO.setStudentHeadIcon(studentHeadIcon);
                studentWorkListViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
                studentWorkListViewDTOList.add(studentWorkListViewDTO);
            }
        }

        PageResponse<StudentWorkListViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(studentWorkPageRequest, response);
        response.setRows(studentWorkListViewDTOList);
        response.setRecords(records);
        return response;
    }

}
