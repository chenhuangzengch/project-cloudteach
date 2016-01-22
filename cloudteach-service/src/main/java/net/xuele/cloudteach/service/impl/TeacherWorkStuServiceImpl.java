package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.StudentWorkCommunicationPageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.BasicWorkInfoView;
import net.xuele.cloudteach.view.StudentWorkCommunicationView;
import net.xuele.cloudteach.view.TeacherWorkFileDetailView;
import net.xuele.cloudteach.view.WorkAnswerCommentView;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hujx on 2015/7/31 0031.
 */
@Service
public class TeacherWorkStuServiceImpl implements TeacherWorkStuService {

    private static Logger logger = LoggerFactory.getLogger(TeacherWorkStuServiceImpl.class);

    @Autowired
    TeacherWorkService teacherWorkService;
    @Autowired
    MagicQuestionService magicQuestionService;
    @Autowired
    WorkStatisticsService workStatisticsService;
    @Autowired
    UserService userService;
    @Autowired
    CloudTeachService cloudTeachService;

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
    CtTeacherWorkMapper ctTeacherWorkMapper;
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
    CtTeacherWorkItemAnswerStatisticsMapper ctTeacherWorkItemAnswerStatisticsMapper;

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

    @Autowired
    StudentService studentService;
    @Autowired
    WriteAccessLogService writeAccessLogService;
    @Autowired
    CloudTeachRedisService cloudTeachRedisService;

    /**
     * @param studentWorkCommunicationPageRequest
     * @return
     * @throws CloudteachException
     */
    @Override
    public PageResponse<StudentWorkCommunicationViewDTO> teacherWorkCommunication(StudentWorkCommunicationPageRequest studentWorkCommunicationPageRequest) throws CloudteachException {

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
        long records = ctTeacherWorkItemAnswerMapper.getSubmitStuRecCount(workId, schoolId);
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
                ctTeacherWorkItemAnswerMapper.getBasicTeacherWorkAnswerInfo(pagesize, page, workId, studentId, subTime, firstReq, schoolId);

        int workType = 0;
        // 获取作业统计表，得到作业类型
        CtWorkGather ctWorkGather = ctWorkGatherMapper.selectByPrimaryKey(workId, schoolId);
        if (ctWorkGather != null) {
            workType = ctWorkGather.getWorkType();
        }

        // 转DTO学生回答信息
        logger.info("[DOMAIN->DTO：]");
        for (StudentWorkCommunicationView studentWorkCommunicationView : studentWorkCommunicationViewList) {
            StudentWorkCommunicationViewDTO studentWorkCommunicationViewDTO = new StudentWorkCommunicationViewDTO();
            String answerId = studentWorkCommunicationView.getAnswerId();
            String userId = studentWorkCommunicationView.getUserId();
            // 获取点赞数量
            logger.info("[获取作业回答的点赞数：]");
            int praiseCount = ctTeacherWorkItemAnswerPraiseMapper.getPraiseCountByAnswer(answerId, schoolId);

            // 获取该用户对记录的点赞状态
            logger.info("[获取当前登录用户对记录的点赞状态：]");
            int praiseStatus = 0;
            int praiseNum = ctTeacherWorkItemAnswerPraiseMapper.getPraiseCountByUNIKEY(answerId, studentId, schoolId);
            if (praiseNum != 0) {
                praiseStatus = 1;
            }

            logger.info("[获取学生班级信息：]");
            ClassInfoDTO classInfo = studentService.queryUserClass(userId);

            // 获取作业回答附件列表
            logger.info("[获取作业回答附件列表：]");
            List<CtTeacherWorkItemAnswerFile> ctTeacherWorkItemAnswerFileList =
                    ctTeacherWorkItemAnswerFileMapper.getAnswerFileInfoByAnswerId(answerId, schoolId);
            // 获取作业回答评论信息
            logger.info("[获取作业回答评论信息：]");
            List<WorkAnswerCommentView> teacherWorkItemAnswerCommNameViewList =
                    ctTeacherWorkItemAnswerCommentMapper.getAnswerCommentsInfo(answerId, Constants.MIN_COMMENT_NUM, schoolId);

            List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = new ArrayList<>();
            List<WorkAnswerCommentViewDTO> teacherWorkItemAnswerCommNameViewDTOList = new ArrayList<>();

            // 附件domain->DTO
            for (CtTeacherWorkItemAnswerFile ctTeacherWorkItemAnswerFile : ctTeacherWorkItemAnswerFileList) {
                TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO = new TeacherWorkItemAnswerFileDTO();
                BeanUtils.copyProperties(ctTeacherWorkItemAnswerFile, teacherWorkItemAnswerFileDTO);
                teacherWorkItemAnswerFileDTOList.add(teacherWorkItemAnswerFileDTO);
            }
            // 评论domain->DTO
            for (WorkAnswerCommentView teacherWorkItemAnswerCommNameView : teacherWorkItemAnswerCommNameViewList) {
                WorkAnswerCommentViewDTO teacherWorkItemAnswerCommNameViewDTO = new WorkAnswerCommentViewDTO();
                BeanUtils.copyProperties(teacherWorkItemAnswerCommNameView, teacherWorkItemAnswerCommNameViewDTO);
                teacherWorkItemAnswerCommNameViewDTO.setContext(StringUtil.getFixedLengthContext(teacherWorkItemAnswerCommNameViewDTO.getContext(), 0));
                teacherWorkItemAnswerCommNameViewDTOList.add(teacherWorkItemAnswerCommNameViewDTO);
            }

            BeanUtils.copyProperties(studentWorkCommunicationView, studentWorkCommunicationViewDTO);
            // 如果为语音作业，需要将score设置成系统评分
            if (workType == 7) {
                studentWorkCommunicationViewDTO.setScore(studentWorkCommunicationViewDTO.getSysScore());
            }
            studentWorkCommunicationViewDTO.setAnswerContext(StringUtil.getFixedLengthContext(studentWorkCommunicationViewDTO.getAnswerContext(), 0));
            studentWorkCommunicationViewDTO.setPraiseCount(praiseCount);
            studentWorkCommunicationViewDTO.setPraiseStatus(praiseStatus);
            studentWorkCommunicationViewDTO.setClassInfo(classInfo.getClassName());
            studentWorkCommunicationViewDTO.setTeacherWorkItemAnswerFileDTOList(teacherWorkItemAnswerFileDTOList);
            studentWorkCommunicationViewDTO.setWorkAnswerCommentViewDTOList(teacherWorkItemAnswerCommNameViewDTOList);
            studentWorkCommunicationViewDTOList.add(studentWorkCommunicationViewDTO);
        }

        PageResponse<StudentWorkCommunicationViewDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(studentWorkCommunicationPageRequest, response);
        response.setRows(studentWorkCommunicationViewDTOList);
        response.setRecords(records);
        return response;

    }

    /**
     * @param answerId
     * @param schoolId
     * @return
     */
    @Override
    public List<WorkAnswerCommentViewDTO> getMoreComments(String answerId, String schoolId, int limitRange) {

        List<WorkAnswerCommentView> workAnswerCommentViewList =
                ctTeacherWorkItemAnswerCommentMapper.getAnswerCommentsInfo(answerId, limitRange, schoolId);
        List<WorkAnswerCommentViewDTO> teacherWorkItemAnswerCommNameViewDTOList = new ArrayList<>();
        for (WorkAnswerCommentView workAnswerCommentView : workAnswerCommentViewList) {
            WorkAnswerCommentViewDTO workAnswerCommentViewDTO = new WorkAnswerCommentViewDTO();
            BeanUtils.copyProperties(workAnswerCommentView, workAnswerCommentViewDTO);
            workAnswerCommentViewDTO.setContext(StringUtil.getFixedLengthContext(workAnswerCommentViewDTO.getContext(), 0));
            teacherWorkItemAnswerCommNameViewDTOList.add(workAnswerCommentViewDTO);
        }

        return teacherWorkItemAnswerCommNameViewDTOList;
    }

    /**
     * 更新作业完成状态(学生提交作业后，需要调用这个接口)
     *
     * @param unSubmitStuAmount 未提交学生人数
     * @param workId            作业ID
     * @param schoolId          学校ID
     * @return
     */
    @Override
    public int updateWorkFinishStatus(int unSubmitStuAmount, String workId, String schoolId) {
        int ret = 1;
        String key = workId;
        if (unSubmitStuAmount == 0) {
            logger.info("[作业已经完成,更改完成状态为2:]");
            ret = ctTeacherWorkMapper.updateWorkFinishStatus(2, workId, schoolId);
            cloudTeachRedisService.del(key);
        } else {
            String value = cloudTeachRedisService.get(key);
            logger.info("[从redis中获取作业完成状态finishStatus:{}]", value);
            if (StringUtils.isEmpty(value)) {
                logger.info("[第一个人提交,更改作业完成状态为1,并存入redis:]");
                CtTeacherWork work = ctTeacherWorkMapper.selectByPrimaryKey(workId, schoolId);
                long time = work.getEndTime().getTime();
                cloudTeachRedisService.setByTimePoint(key, "1", time);
                ret = ctTeacherWorkMapper.updateWorkFinishStatus(1, workId, schoolId);
            }
        }
        return ret;
    }

    /**
     * @param teacherWorkItemAnswerDTO
     * @param teacherWorkItemAnswerFileDTOList
     */
    @Override
    public void handInMyTeacherWork(TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO, List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList) throws CloudteachException {

        // 获取作业ID
        String workId = teacherWorkItemAnswerDTO.getWorkId();
        // 获取学生ID
        String studentId = teacherWorkItemAnswerDTO.getUserId();
        // 获取schoolId
        String schoolId = teacherWorkItemAnswerDTO.getSchoolId();

        logger.info("[作业信息验证：]");
        // 获取作业信息
        CtTeacherWork ctTeacherWork = ctTeacherWorkMapper.selectByPrimaryKey(workId, schoolId);
        // 判断提交作业的时候，作业是否有效
        if (ctTeacherWork == null || ctTeacherWork.getStatus() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        // 超过最晚提交时间，作业不能提交
        if (ctTeacherWork.getEndTime().getTime() < new Date().getTime()) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "：超过最晚交作业时间！",
                    CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
        }

        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.getTeacherWorkItemAnswerByWorkStudentId(workId, studentId, schoolId);
        if (ctTeacherWorkItemAnswer == null || ctTeacherWorkItemAnswer.getStatus() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        int subStatus = ctTeacherWorkItemAnswer.getSubStatus();
        // 如果学生已经提交作业，则不允许再次提交
        if (subStatus == 1) {
            throw new CloudteachException(CloudTeachErrorEnum.ALREADYSUM.getMsg(), CloudTeachErrorEnum.ALREADYSUM.getCode());
        }

        String answerContext = teacherWorkItemAnswerDTO.getContext();
        // 如果不限制提交方式，学生不提交附件时，必须填写文字
        BasicWorkInfoViewDTO workBasic = this.getBasicWorkInfo(workId, studentId, schoolId);
        int subOther = workBasic.getSubOther();
        if (subOther == 1 && CollectionUtils.isEmpty(teacherWorkItemAnswerFileDTOList)) {
            if (answerContext == null || answerContext.equals("")) {
                throw new CloudteachException(CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getMsg() + "：请填写回答内容！",
                        CloudTeachErrorEnum.WORK_SUBMIT_FAILED.getCode());
            }
        } else {
            if (answerContext == null) {
                answerContext = "";
            }
        }

        // 作业回答内容字数check
        if (!StringUtil.checkContextLength(answerContext)) {
            throw new CloudteachException(CloudTeachErrorEnum.OVERMAXLENGTH.getMsg(), CloudTeachErrorEnum.OVERMAXLENGTH.getCode());
        }

        logger.info("[学生上传附件验证：]");
        boolean checkSubFiles = cloudTeachService.checkStuTeacherWorkSubFiles(teacherWorkItemAnswerFileDTOList, workId, schoolId);
        if (!checkSubFiles) {
            throw new CloudteachException(CloudTeachErrorEnum.ERRORSUBMIT.getMsg() + "：附件不符合作业要求！",
                    CloudTeachErrorEnum.ERRORSUBMIT.getCode());
        }

        // 要求带附件，则插入附件信息
        logger.info("[获取附件信息]");
        if (teacherWorkItemAnswerFileDTOList != null && teacherWorkItemAnswerFileDTOList.size() != 0) {
            for (TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO : teacherWorkItemAnswerFileDTOList) {
                CtTeacherWorkItemAnswerFile ctTeacherWorkItemAnswerFile = new CtTeacherWorkItemAnswerFile();
                BeanUtils.copyProperties(teacherWorkItemAnswerFileDTO, ctTeacherWorkItemAnswerFile);
                ctTeacherWorkItemAnswerFile.setFileName(StringUtil.fileNameSubstring(teacherWorkItemAnswerFileDTO.getFileName(), Constants.MAX_FILE_NAME_LENGTH));
                ctTeacherWorkItemAnswerFile.setAnswerFileId(UUID.randomUUID().toString().replace("-", ""));
                ctTeacherWorkItemAnswerFile.setAnswerId(ctTeacherWorkItemAnswer.getAnswerId());
                ctTeacherWorkItemAnswerFile.setFileType(teacherWorkItemAnswerFileDTO.getFileType());
                ctTeacherWorkItemAnswerFile.setUploadTime(new Date());
                ctTeacherWorkItemAnswerFile.setSchoolId(schoolId);
                ctTeacherWorkItemAnswerFile.setStatus(1);
                ctTeacherWorkItemAnswerFileMapper.insert(ctTeacherWorkItemAnswerFile);
            }
        }

        Date subTime = new Date();
        ctTeacherWorkItemAnswer.setContext(answerContext);
        ctTeacherWorkItemAnswer.setSubTime(subTime);
        ctTeacherWorkItemAnswer.setUpdateTime(subTime);
        ctTeacherWorkItemAnswer.setUpdateUserId(studentId);
        if (teacherWorkItemAnswerDTO.getSysScore() != null) {
            ctTeacherWorkItemAnswer.setSysScore(teacherWorkItemAnswerDTO.getSysScore());
        }
        ctTeacherWorkItemAnswer.setSubStatus(1);

        CtTeacherWorkStudent ctTeacherWorkStudent =
                ctTeacherWorkStudentMapper.getTeacherWorkStudentByStudentId(workId, studentId, schoolId);
        ctTeacherWorkStudent.setSubStatus(1);

        CtWorkStudentGather ctWorkStudentGather =
                ctWorkStudentGatherMapper.getWorkStudentGatherByWorkIdAndUserId(ctTeacherWorkStudent.getWorkId(), ctTeacherWorkStudent.getUserId(), schoolId);
        ctWorkStudentGather.setSubStatus(1);
        ctWorkStudentGather.setSubTime(subTime);
        // 提交口语作业设置练习次数
        if (teacherWorkItemAnswerDTO.getPlayTimes() != null) {
            ctWorkStudentGather.setPlayTimes(teacherWorkItemAnswerDTO.getPlayTimes());
        }
        // 更新 教师作业学生表 ct_teacher_work_student 的提交状态
        logger.info("[更新：教师作业学生表(ct_teacher_work_student)的提交状态]");
        ctTeacherWorkStudentMapper.updateByPrimaryKey(ctTeacherWorkStudent);
        // 更新 教师作业题目学生回答表 ct_teacher_work_item_answer 的回答信息
        logger.info("[更新：教师作业题目学生回答表(ct_teacher_work_item_answer)的回答信息]");
        ctTeacherWorkItemAnswerMapper.updateByPrimaryKey(ctTeacherWorkItemAnswer);
        // 更新 作业学生汇总表 ct_work_student_gather 的提交状态
        logger.info("[更新：作业学生汇总表(ct_work_student_gather)的提交状态]");
        ctWorkStudentGatherMapper.updateByPrimaryKey(ctWorkStudentGather);

        // /** 20151124，将以下2个update处理，提到交作业事务外进行 **/
        // 更新 作业统计表 ct_work_statistics 的已提交作业学生数
        /*logger.info("[更新：作业统计表(ct_work_statistics)的已提交作业学生数]");
        int unSubmitStuNumbers = workStatisticsService.updateSubmitStudentNum(workId, schoolId);
        // 判断是否需要更新
        logger.info("[更新：教师作业表(ct_teacher_work)的作业完成状态]");
        if (unSubmitStuNumbers == 0) {
            ctTeacherWorkMapper.updateWorkFinishStatus(2, workId, schoolId);
        } else {
            ctTeacherWorkMapper.updateWorkFinishStatus(1, workId, schoolId);
        }*/
        try {
            logger.info("[调用日志服务接口]");
            writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
                    .accessAction(AccessAction.HomeworkFinish)
                    .userId(studentId)
                    .occurredTime(subTime.getTime())
                    .build());
        } catch (Exception e) {
            logger.error("[写入日志失败:{}]", e.getMessage());
        }
    }

    /**
     * 获取教师作业中某个学生提交的作业信息
     * (包含题目基本信息，题目附件信息，教师录音信息，学生回答信息，学生提交附件信息，学生回答统计信息)
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    @Override
    public TeacherWorkFinishDetailViewDTO teacherWorkFinishDetail(String workId, String studentId, String schoolId) throws CloudteachException {

        TeacherWorkFinishDetailViewDTO teacherWorkFinishDetailViewDTO = new TeacherWorkFinishDetailViewDTO();
        List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewDTOList = new ArrayList<>();
        TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = new TeacherWorkItemAnswerDTO();
        List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList = new ArrayList<>();
        BasicWorkInfoViewDTO basicWorkInfoViewDTO = new BasicWorkInfoViewDTO();
        TeacherWorkItemAnswerStatisticsDTO teacherWorkItemAnswerStatisticsDTO = new TeacherWorkItemAnswerStatisticsDTO();

        logger.info("[校验作业是否属于该学生：]");
        if (!workStatisticsService.checkWorkOwnerStu(studentId, workId, schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getMsg(),
                    CloudTeachErrorEnum.WORK_IS_NOT_YOURS.getCode());
        }

        logger.info("[获取教师题目基本信息：]");
        basicWorkInfoViewDTO = this.getBasicWorkInfo(workId, studentId, schoolId);

        logger.info("[获取教师录音信息：]");
        WorkTapeFilesDTO workTapeFilesDTO = workTapeFilesService.getWorkTapeFilesByWorkId(workId, schoolId);

        logger.info("[获取题目附件信息：]");
        CtTeacherWorkItem ctTeacherWorkItem = ctTeacherWorkItemMapper.getTeacherWorkItemByWorkId(workId, schoolId);
        String workItemId = ctTeacherWorkItem.getWorkItemId();
        List<TeacherWorkFileDetailView> teacherWorkFileDetailViewList = ctBankItemFilesMapper.teacherWorkFileInfo(workItemId, schoolId);
        for (TeacherWorkFileDetailView teacherWorkFileDetailView : teacherWorkFileDetailViewList) {
            TeacherWorkFileDetailViewDTO teacherWorkFileDetailViewDTO = new TeacherWorkFileDetailViewDTO();
            BeanUtils.copyProperties(teacherWorkFileDetailView, teacherWorkFileDetailViewDTO);
            teacherWorkFileDetailViewDTOList.add(teacherWorkFileDetailViewDTO);
        }

        logger.info("[获取学生回答信息：]");
        teacherWorkItemAnswerDTO = this.getStuAnswerByWorkStu(workId, studentId, schoolId);

        logger.info("[获取学生提交附件信息：]");
        List<CtTeacherWorkItemAnswerFile> ctTeacherWorkItemAnswerFileList =
                ctTeacherWorkItemAnswerFileMapper.getAnswerFileInfoByAnswerId(teacherWorkItemAnswerDTO.getAnswerId(), schoolId);
        for (CtTeacherWorkItemAnswerFile ctTeacherWorkItemAnswerFile : ctTeacherWorkItemAnswerFileList) {
            TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO = new TeacherWorkItemAnswerFileDTO();
            BeanUtils.copyProperties(ctTeacherWorkItemAnswerFile, teacherWorkItemAnswerFileDTO);
            teacherWorkItemAnswerFileDTOList.add(teacherWorkItemAnswerFileDTO);
        }

        logger.info("[获取学生回答统计信息：]");
        CtTeacherWorkItemAnswerStatistics ctTeacherWorkItemAnswerStatistics = ctTeacherWorkItemAnswerStatisticsMapper.selectByPrimaryKey(teacherWorkItemAnswerDTO.getAnswerId(), schoolId);
        if (ctTeacherWorkItemAnswerStatistics == null) {
            throw new CloudteachException(CloudTeachErrorEnum.ANSWER_STATISTICS_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.ANSWER_STATISTICS_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(ctTeacherWorkItemAnswerStatistics, teacherWorkItemAnswerStatisticsDTO);

        logger.info("[获取学生信息：]");
        UserDTO stuInfo = userService.getByUserId(studentId);

        teacherWorkFinishDetailViewDTO.setBasicWorkInfo(basicWorkInfoViewDTO);
        teacherWorkFinishDetailViewDTO.setTeacherWorkFileDetailViewList(teacherWorkFileDetailViewDTOList);
        teacherWorkFinishDetailViewDTO.setTeacherWorkItemAnswerDTO(teacherWorkItemAnswerDTO);
        teacherWorkFinishDetailViewDTO.setCtTeacherWorkItemAnswerFileList(teacherWorkItemAnswerFileDTOList);
        teacherWorkFinishDetailViewDTO.setWorkTapeFilesDTO(workTapeFilesDTO);
        teacherWorkFinishDetailViewDTO.setTeacherWorkItemAnswerStatisticsDTO(teacherWorkItemAnswerStatisticsDTO);
        teacherWorkFinishDetailViewDTO.setStuInfo(stuInfo);

        return teacherWorkFinishDetailViewDTO;
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
        teacherWorkService.praise(answerId, userId, schoolId, userType);
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
        teacherWorkService.unpraise(answerId, userId, schoolId, userType);
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
        return teacherWorkService.comment(answerId, userId, schoolId, context, userType);
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
        // 调用教师作业服务学生删除自己评论方法
        logger.info("[学生删除对以下回答的评论, 评论ID: +" + commentId + "]");
        teacherWorkService.delStuCommont(commentId, userId, schoolId);
    }

    /**
     * 根据answerId获取学生作业回答信息
     *
     * @param answerId
     * @param schoolId
     * @return
     */
    @Override
    public TeacherWorkItemAnswerDTO getTeacherWorkItemAnswerByAnswerId(String answerId, String schoolId) {
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.selectByPrimaryKey(answerId, schoolId);
        if (ctTeacherWorkItemAnswer == null) {
            return null;
        }
        TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = new TeacherWorkItemAnswerDTO();
        BeanUtils.copyProperties(ctTeacherWorkItemAnswer, teacherWorkItemAnswerDTO);
        return teacherWorkItemAnswerDTO;
    }

    /**
     * 获取某个用户对应某个学生作业的点赞状态信息
     *
     * @param answerId 提分宝作业回答ID
     * @param userId   点赞用户ID
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public TeacherWorkItemAnswerPraiseDTO getTeacherWorkStuPraiseStatusByUserId(String answerId, String userId, String schoolId) {
        CtTeacherWorkItemAnswerPraise ctTeacherWorkItemAnswerPraise = ctTeacherWorkItemAnswerPraiseMapper.selectByPrimaryKey(answerId, userId, schoolId);
        if (ctTeacherWorkItemAnswerPraise == null) {
            return null;
        }
        TeacherWorkItemAnswerPraiseDTO teacherWorkItemAnswerPraiseDTO = new TeacherWorkItemAnswerPraiseDTO();
        BeanUtils.copyProperties(ctTeacherWorkItemAnswerPraise, teacherWorkItemAnswerPraiseDTO);
        return teacherWorkItemAnswerPraiseDTO;
    }

    /**
     * 通过作业ID，学生ID，查找学生回答信息
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    @Override
    public TeacherWorkItemAnswerDTO getStuAnswerByWorkStu(String workId, String studentId, String schoolId) throws CloudteachException {

        TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = new TeacherWorkItemAnswerDTO();
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.getTeacherWorkItemAnswerByWorkStudentId(workId, studentId, schoolId);
        if (ctTeacherWorkItemAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(ctTeacherWorkItemAnswer, teacherWorkItemAnswerDTO);
        teacherWorkItemAnswerDTO.setContext(StringUtil.getFixedLengthContext(teacherWorkItemAnswerDTO.getContext(), 0));

        return teacherWorkItemAnswerDTO;
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
        BasicWorkInfoView basicWorkInfoView = studentWorkListMapper.getBasicTeacherWorkInfo(workId, studentId, schoolId);
        if (basicWorkInfoView == null) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_NOT_FOUND.getMsg(),
                    CloudTeachErrorEnum.WORK_NOT_FOUND.getCode());
        }
        BeanUtils.copyProperties(basicWorkInfoView, basicWorkInfoViewDTO);
        return basicWorkInfoViewDTO;
    }

    /**
     * @param answerId
     */
    @Override
    public void delWorkAnswer(String answerId) {

    }
}
