package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.service.common.CloudTeachNotifyContent;
import net.xuele.cloudteach.service.util.*;
import net.xuele.cloudteach.view.*;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.dto.SchoolDTO;
import net.xuele.member.service.SchoolService;
import net.xuele.teacheval.constants.AccessAction;
import net.xuele.teacheval.domain.UserAccessAction;
import net.xuele.teacheval.service.WriteAccessLogService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 教师布置作业管理
 * Created by hujx on 2015/7/25 0025.
 */
@Service
public class TeacherWorkServiceImpl implements TeacherWorkService {

    private static Logger logger = LoggerFactory.getLogger(TeacherWorkServiceImpl.class);

    @Autowired
    CloudTeachService cloudTeachService;//云教学服务
    @Autowired
    WorkStatisticsService workStatisticsService;//作业汇总统计服务
    @Autowired
    CloudDiskService cloudDiskService;//云盘服务
    @Autowired
    BankItemService bankItemService;//教师题库服务
    @Autowired
    WriteAccessLogService writeAccessLogService;
    @Autowired
    CtBankItemMapper ctBankItemMapper;
    @Autowired
    CtBankItemFilesMapper ctBankItemFilesMapper;
    @Autowired
    CtBankItemStatisticsMapper ctBankItemStatisticsMapper;
    @Autowired
    CtWorkStatisticsMapper ctWorkStatisticsMapper;
    @Autowired
    CtWorkClassGatherMapper ctWorkClassGatherMapper;
    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;
    @Autowired
    CtWorkGatherMapper ctWorkGatherMapper;
    @Autowired
    CtTeacherWorkMapper ctTeacherWorkMapper;
    @Autowired
    CtTeacherWorkItemMapper ctTeacherWorkItemMapper;
    @Autowired
    CtTeacherWorkClassMapper ctTeacherWorkClassMapper;
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
    CtWorkTapeFilesMapper ctWorkTapeFilesMapper;
    @Autowired
    CtUnitsMapper ctUnitsMapper;
    @Autowired
    SchoolService schoolService;

    /**
     * @param teacherWorkAddViewDTO
     * @throws CloudteachException
     */
    @Override
    public String addHomework(TeacherWorkAddViewDTO teacherWorkAddViewDTO, WorkTapeFilesDTO workTapeFilesDTO, TeacherWorkItemDTO teacherWorkItemDTO) throws CloudteachException {

        String res = "";

        if (teacherWorkAddViewDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        String userId = teacherWorkAddViewDTO.getUserId();
        String areaId = teacherWorkAddViewDTO.getAreaId();
        String userName = teacherWorkAddViewDTO.getUserName() == null ? "" : teacherWorkAddViewDTO.getUserName();
        String userIcon = teacherWorkAddViewDTO.getUserIcon() == null ? "" : teacherWorkAddViewDTO.getUserIcon();
        String positionName = teacherWorkAddViewDTO.getPositionName() == null ? "" : teacherWorkAddViewDTO.getPositionName();

        String schoolId = teacherWorkAddViewDTO.getSchoolId();
        String unitId = teacherWorkAddViewDTO.getUnitId();
        String classInfoJSON = teacherWorkAddViewDTO.getClassInfoJSON();
        List<String> classList = teacherWorkAddViewDTO.getClassList();
        List<String> itemList = teacherWorkAddViewDTO.getItemList();
        String schoolName = "";

        if (StringUtils.isEmpty(userId) || /*StringUtils.isEmpty(areaId) ||*/ StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        boolean classInfoRight = true;
        // 班级ID列表或者题目列表中必须要有元素
        logger.info("[判断班级ID列表，作业题列表是否为空]");
        if (CollectionUtils.isEmpty(classList)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_CLASS_NULL.getMsg(),
                    CloudTeachErrorEnum.WORK_CLASS_NULL.getCode());
        }

        if ((CollectionUtils.isEmpty(itemList)) && (teacherWorkItemDTO == null)) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_ITEM_NULL.getMsg(),
                    CloudTeachErrorEnum.WORK_ITEM_NULL.getCode());
        }

        // 判断提供的班级ID列表与封装的班级JSON信息是否一致
        logger.info("[判断班级ID列表，班级JSON信息是否匹配]");
        try {
            classInfoRight = VerifyParam.verifyClassInfo(classList, classInfoJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 不一致时需要抛出异常
        if (!classInfoRight) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：班级ID信息与班级JSON信息不匹配",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        logger.info("[验证选择班级中是否存在没有学生的班级]");
        List<StudentNumView> studentNumViewList = ctUnitsMapper.getStudentNumBySchoolId(classList, schoolId);
        List<String> newClassList = TranslateCommon.getSchoolIdList(studentNumViewList);
        try {
            if (newClassList.size() != classList.size()) {
                res = TranslateCommon.getClassInfoById(newClassList, classInfoJSON);
            } else {
                for (String itemId : itemList) {
                    logger.info("[*******开始布置作业处理：*******]");
                    CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, itemId);

                    // 教师作业表ct_teacher_work初始化相关
                    logger.info("[布置作业初始化：教师作业表(ct_teacher_work)]");
                    // 作业ID,主键
                    String workId = UUID.randomUUID().toString().replace("-", "");
                    logger.info("[新建作业ID：" + workId + "]");
                    CtTeacherWork ctTeacherWork = new CtTeacherWork();
                    Date publishDate = teacherWorkAddViewDTO.getPublishTime();
                    Date CreateUpdateDate = new Date();
                    // 调用方法判断发布类型
                    int ret = DateTimeUtil.compareNowDate(publishDate, "yyyy-mm-dd");
                    if (ret == 0) {
                        teacherWorkAddViewDTO.setPublishType(1);
                    } else {
                        teacherWorkAddViewDTO.setPublishType(0);
                    }

                    teacherWorkAddViewDTO.setWorkType(ctBankItem.getItemType());
                    teacherWorkAddViewDTO.setWorkId(workId);
                    teacherWorkAddViewDTO.setCreateTime(CreateUpdateDate);
                    teacherWorkAddViewDTO.setUpdateTime(CreateUpdateDate);
                    teacherWorkAddViewDTO.setFinishStatus(0);
                    teacherWorkAddViewDTO.setCorrectStatus(0);
                    teacherWorkAddViewDTO.setStatus(1);
                    BeanUtils.copyProperties(teacherWorkAddViewDTO, ctTeacherWork);
                    int ctTeacherWorkResult = ctTeacherWorkMapper.insert(ctTeacherWork);

                    if (ctTeacherWorkResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：教师作业表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }

                    // 初始化 ct_teacher_work_item
                    logger.info("[布置作业初始化：教师作业题目表(ct_teacher_work_item)]");
                    int ctTeacherWorkItemResult;
                    String workItemId;
                    if (teacherWorkItemDTO != null) {
                        CtTeacherWorkItem ctTeacherWorkItem = new CtTeacherWorkItem();
                        BeanUtils.copyProperties(teacherWorkItemDTO, ctTeacherWorkItem);
                        workItemId = UUID.randomUUID().toString().replace("-", "");
                        ctTeacherWorkItem.setWorkId(workId);
                        ctTeacherWorkItem.setWorkItemId(workItemId);
                        ctTeacherWorkItem.setStatus(1);
                        ctTeacherWorkItem.setSchoolId(schoolId);
                        ctTeacherWorkItemResult = ctTeacherWorkItemMapper.insert(ctTeacherWorkItem);
                    } else {
                        CtTeacherWorkItem workItemInitInfo = ctTeacherWorkItemMapper.getInitInfo(workId, itemId, schoolId);
                        workItemId = workItemInitInfo.getWorkItemId();
                        if (workItemInitInfo == null) {
                            throw new CloudteachException(CloudTeachErrorEnum.DATA_MISMATCHED.getMsg() + "：教师题目表",
                                    CloudTeachErrorEnum.DATA_MISMATCHED.getCode());
                        }
                        ctTeacherWorkItemResult = ctTeacherWorkItemMapper.insert(workItemInitInfo);
                    }
                    if (ctTeacherWorkItemResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：教师作业题目表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }

                    // 初始化 ct_teacher_work_class
                    logger.info("[布置作业初始化：教师作业班级表(ct_teacher_work_class)]");
                    List<CtTeacherWorkClass> workClassInitInfoList = ctTeacherWorkClassMapper.getInitInfo(workId, classList, schoolId);
                    if (CollectionUtils.isEmpty(workClassInitInfoList)) {
                        throw new CloudteachException(CloudTeachErrorEnum.DATA_MISMATCHED.getMsg() + "：班级表",
                                CloudTeachErrorEnum.DATA_MISMATCHED.getCode());
                    }
                    int ctTeacherWorkClassResult = ctTeacherWorkClassMapper.initCtTeacherWorkClass(workClassInitInfoList);
                    if (ctTeacherWorkClassResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：教师作业班级表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }

                    // 初始化 ct_teacher_work_student
                    logger.info("[布置作业初始化：教师作业学生表(ct_teacher_work_student)]");
                    List<CtTeacherWorkStudent> workStudentInitInfoList = ctTeacherWorkStudentMapper.getInitInfo(workId, classList, schoolId);
                    if (CollectionUtils.isEmpty(workStudentInitInfoList)) {
                        throw new CloudteachException(CloudTeachErrorEnum.DATA_MISMATCHED.getMsg() + "：学生表",
                                CloudTeachErrorEnum.DATA_MISMATCHED.getCode());
                    }
                    int WorkStudentNumber = workStudentInitInfoList.size();
                    logger.info("[CtTeacherWorkStudent学生数:{}]", WorkStudentNumber);
                    if (WorkStudentNumber <= Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC) {
                        int ctTeacherWorkStudentResult = ctTeacherWorkStudentMapper.initCtTeacherWorkStudent(workStudentInitInfoList);
                        if (ctTeacherWorkStudentResult < 1) {
                            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师作业学生表初始化失败",
                                    CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                        }
                    } else {
                        List<CtTeacherWorkStudent> newList = new ArrayList<>();
                        int count = WorkStudentNumber / Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC;
                        int remainder = WorkStudentNumber % Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC;
                        // 把list拆分成每个Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC个元素的若干个list，在insert到数据库
                        for (int i = 0; i < count; i++) {
                            newList = workStudentInitInfoList.subList(i * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC, (i + 1) * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC);
                            int ctTeacherWorkStudentResult = ctTeacherWorkStudentMapper.initCtTeacherWorkStudent(newList);
                            if (ctTeacherWorkStudentResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师作业学生表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }
                        }
                        // 将余下的数据insert到数据库
                        if (remainder > 1) {
                            newList = workStudentInitInfoList.subList(count * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC, count * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC + remainder);
                            int ctTeacherWorkStudentResult = ctTeacherWorkStudentMapper.initCtTeacherWorkStudent(newList);
                            if (ctTeacherWorkStudentResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师作业学生表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }
                        }
                    }

                    // 初始化 ct_teacher_work_item_answer,ct_teacher_work_item_answer_statistics
                    logger.info("[布置作业初始化：教师作业题目学生回答表(ct_teacher_work_item_answer),教师作业题目学生回答计数表(ct_teacher_work_item_answer_statistics)]");
                    List<CtTeacherWorkItemAnswer> workItemAnswerInitInfoList =
                            ctTeacherWorkItemAnswerMapper.getInitInfo(workId, workItemId, classList, schoolId);
                    if (CollectionUtils.isEmpty(workItemAnswerInitInfoList)) {
                        throw new CloudteachException(CloudTeachErrorEnum.DATA_MISMATCHED.getMsg() + "：学生表相关信息",
                                CloudTeachErrorEnum.DATA_MISMATCHED.getCode());
                    }
                    int workAnswerAmout = workItemAnswerInitInfoList.size();
                    logger.info("[CtTeacherWorkItemAnswer:{}]", workAnswerAmout);
                    if (workAnswerAmout <= Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC) {
                        int ctTeacherWorkItemAnswerResult = ctTeacherWorkItemAnswerMapper.initCtTeacherWorkItemAnswer(workItemAnswerInitInfoList);
                        if (ctTeacherWorkItemAnswerResult < 1) {
                            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：教师作业题目学生回答表初始化失败",
                                    CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                        }

                        int ctTeacherWorkItemAnswerStatisticsResult = ctTeacherWorkItemAnswerStatisticsMapper.initCtTeacherWorkItemAnswerStat(workItemAnswerInitInfoList);
                        if (ctTeacherWorkItemAnswerStatisticsResult < 1) {
                            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师作业题目学生回答计数表初始化失败",
                                    CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                        }
                    } else {
                        List<CtTeacherWorkItemAnswer> newList = new ArrayList<>();
                        int count = workAnswerAmout / Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC;
                        int remainder = workAnswerAmout % Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC;
                        for (int i = 0; i < count; i++) {
                            newList = workItemAnswerInitInfoList.subList(i * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC, (i + 1) * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC);
                            int ctTeacherWorkItemAnswerResult = ctTeacherWorkItemAnswerMapper.initCtTeacherWorkItemAnswer(newList);
                            if (ctTeacherWorkItemAnswerResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：教师作业题目学生回答表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }

                            int ctTeacherWorkItemAnswerStatisticsResult = ctTeacherWorkItemAnswerStatisticsMapper.initCtTeacherWorkItemAnswerStat(newList);
                            if (ctTeacherWorkItemAnswerStatisticsResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师作业题目学生回答计数表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }
                        }
                        if (remainder > 1) {
                            newList = workItemAnswerInitInfoList.subList(count * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC, count * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC + remainder);
                            int ctTeacherWorkItemAnswerResult = ctTeacherWorkItemAnswerMapper.initCtTeacherWorkItemAnswer(newList);
                            if (ctTeacherWorkItemAnswerResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：教师作业题目学生回答表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }

                            int ctTeacherWorkItemAnswerStatisticsResult = ctTeacherWorkItemAnswerStatisticsMapper.initCtTeacherWorkItemAnswerStat(newList);
                            if (ctTeacherWorkItemAnswerStatisticsResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师作业题目学生回答计数表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }
                        }
                    }

                    // 更新 ct_bank_item_statistics 发布次数+1
                    logger.info("[更新发布次数：教师题目统计表(ct_bank_item_statistics)]");
                    ctBankItemStatisticsMapper.updateReleasesByItem(itemId, 1, schoolId);
                    // 将布置过的作业的附件拷贝一份到ct_bank_item_files
                    logger.info("[布置作业初始化：教师题目附件表(ct_bank_item_files)]");
                    if (teacherWorkItemDTO != null) {
                        itemId = teacherWorkItemDTO.getItemId();
                    }
                    List<CtBankItemFiles> bankItemFilesInitInfoList = ctBankItemFilesMapper.getInitInfo(workId, itemId, schoolId);
                    if (CollectionUtils.isNotEmpty(bankItemFilesInitInfoList)) {
                        int ctBankItemFilesResult = ctBankItemFilesMapper.initCopyBankItemFiles(bankItemFilesInitInfoList);
                        if (ctBankItemFilesResult < 1) {
                            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "教师题目附件表初始化失败",
                                    CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                        }
                    }

                    // 初始化作业统计表
                    logger.info("[布置作业初始化：作业统计表(ct_work_statistics)]");
                    CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();
                    ctWorkStatistics.setWorkId(workId);
                    ctWorkStatistics.setVersion(1);
                    ctWorkStatistics.setWorkViewStudentNum(0);
                    ctWorkStatistics.setWorkSubStudentNum(0);
                    ctWorkStatistics.setWorkCorrectStudentNum(0);
                    ctWorkStatistics.setLastWarnTime(null);
                    ctWorkStatistics.setSchoolId(schoolId);
                    int ctWorkStatisticsResult = ctWorkStatisticsMapper.insert(ctWorkStatistics);
                    if (ctWorkStatisticsResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业统计表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }

                    // 初始化作业汇总表
                    logger.info("[布置作业初始化：作业汇总表(ct_work_gather)]");
                    int classCount = classList.size();// 班级数
                    int studentCount = ctTeacherWorkStudentMapper.studentCountByWorkId(workId, schoolId);// 学生数
                    //CtTeacherWorkItem ctTeacherWorkItem = ctTeacherWorkItemMapper.getTeacherWorkItemByWorkId(workId, schoolId);

                    String subjectId = "";
                    String subjectName = "";
                    String unitName = "";
                    int grade = 0;
                    if (StringUtils.isNotEmpty(unitId)) {
                        // 获取章节课程信息
                        logger.info("[获取章节课程信息]");
                        UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(unitId);
                        if (unitsDTO != null) {
                            unitName = unitsDTO.getUnitName();
                            // 获取课本信息
                            logger.info("[获取课本信息]");
                            BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(unitsDTO.getBookId());
                            if (bookDTO != null) {
                                subjectId = bookDTO.getSubjectId();
                                subjectName = bookDTO.getSubjectName();
                                grade = bookDTO.getGrade();
                            }
                        }
                    } else {
                        unitId = "";
                    }

                    CtWorkGather ctWorkGather = new CtWorkGather();
                    ctWorkGather.setWorkId(workId);
                    ctWorkGather.setUserId(teacherWorkAddViewDTO.getUserId());
                    ctWorkGather.setPublishTime(teacherWorkAddViewDTO.getPublishTime());
                    ctWorkGather.setEndTime(teacherWorkAddViewDTO.getEndTime());
                    ctWorkGather.setSubjectId(subjectId);
                    ctWorkGather.setSubjectName(subjectName);
                    ctWorkGather.setUnitId(unitId);
                    ctWorkGather.setUnitName(unitName);
                    ctWorkGather.setContext(ctBankItem.getContext());
                    ctWorkGather.setVoiceContext(ctBankItem.getVoiceContext());
                    ctWorkGather.setFiles("");
                    //ctWorkGather.setFiles(cloudTeachService.AttachmentInfoJson(ctTeacherWorkItem.getWorkItemId(), 3, schoolId));
                    ctWorkGather.setWorkType(ctBankItem.getItemType());
                    ctWorkGather.setWorkItemNum(1);
                    ctWorkGather.setWorkClassNum(classCount);
                    ctWorkGather.setWorkClassJson(classInfoJSON);
                    ctWorkGather.setWorkStudentNum(studentCount);
                    ctWorkGather.setSchoolId(schoolId);
                    ctWorkGather.setStatus(1);
                    int ctWorkGatherResult = ctWorkGatherMapper.insert(ctWorkGather);
                    if (ctWorkGatherResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业汇总表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }

                    // 初始化作业班级汇总表
                    logger.info("[布置作业初始化：作业班级汇总表(ct_work_class_gather)]");
                    List<CtTeacherWorkClass> teacherWorkClassList = ctTeacherWorkClassMapper.getTeacherWorkClassByWorkId(workId, schoolId);
                    int ctWorkClassGatherResult = ctWorkClassGatherMapper.initCtWorkClassGather(teacherWorkClassList);
                    if (ctWorkClassGatherResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业班级汇总表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }
                    // 初始化作业学生汇总表
                    logger.info("[布置作业初始化：作业学生汇总表(ct_work_student_gather)]");
                    List<CtTeacherWorkStudent> teacherWorkStudentList = ctTeacherWorkStudentMapper.getTeacherWorkStudentByWorkId(workId, schoolId);
                    if (CollectionUtils.isEmpty(teacherWorkStudentList)) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业学生汇总表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }
                    int studentGatherNumber = teacherWorkStudentList.size();
                    if (studentGatherNumber <= Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC) {
                        int ctWorkStudentGatherResult = ctWorkStudentGatherMapper.initCtWorkStudentGather(teacherWorkStudentList);
                        if (ctWorkStudentGatherResult < 1) {
                            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业学生汇总表初始化失败",
                                    CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                        }
                    } else {
                        List<CtTeacherWorkStudent> newList = new ArrayList<>();
                        int count = studentGatherNumber / Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC;
                        int remainder = studentGatherNumber % Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC;
                        for (int i = 0; i < count; i++) {
                            newList = teacherWorkStudentList.subList(i * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC, Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC * (i + 1));
                            int ctWorkStudentGatherResult = ctWorkStudentGatherMapper.initCtWorkStudentGather(newList);
                            if (ctWorkStudentGatherResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业学生汇总表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }
                        }
                        if (remainder > 1) {
                            newList = teacherWorkStudentList.subList(count * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC, count * Constants.MAX_STUDENT_NUMBER_FOR_PUBLIC + remainder);
                            int ctWorkStudentGatherResult = ctWorkStudentGatherMapper.initCtWorkStudentGather(newList);
                            if (ctWorkStudentGatherResult < 1) {
                                throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业学生汇总表初始化失败",
                                        CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                            }
                        }
                    }

                    //作业教师录音附件表
                    logger.info("[布置作业初始化：作业教师录音附件表(ct_work_tape_files)]");
                    if (null != workTapeFilesDTO) {
                        CtWorkTapeFiles workTapeFiles = new CtWorkTapeFiles();
                        BeanUtils.copyProperties(workTapeFilesDTO, workTapeFiles);
                        // 主键
                        workTapeFiles.setFileId(UUID.randomUUID().toString().replace("-", ""));
                        workTapeFiles.setWorkId(workId);
                        workTapeFiles.setWorkType(ctBankItem.getItemType());
                        workTapeFiles.setUploadTime(new Date());
                        workTapeFiles.setSchoolId(schoolId);
                        workTapeFiles.setStatus(1);
                        ctWorkTapeFilesMapper.insert(workTapeFiles);
                    }

                    // TODO 注释，这一块先不上
                    /*logger.info("[布置作业初始化：作业汇总区域分片表(ct_work_gather_area)]");
                    CtWorkGatherArea ctWorkGatherArea = new CtWorkGatherArea();
                    BeanUtils.copyProperties(ctWorkGather, ctWorkGatherArea);
                    // 调用member接口，获取学校信息
                    SchoolDTO school = schoolService.getBySchoolId(schoolId);
                    if (school != null) {
                        schoolName = school.getName();
                    }
                    ctWorkGatherArea.setUserIcon(userIcon);
                    ctWorkGatherArea.setUserName(userName);
                    ctWorkGatherArea.setPositionName(positionName);
                    ctWorkGatherArea.setGrade(grade);
                    ctWorkGatherArea.setWorkSubStudentNum(0);
                    ctWorkGatherArea.setWorkCorrectStudentNum(0);
                    ctWorkGatherArea.setSchoolName(schoolName);
                    ctWorkGatherArea.setAreaCode(areaId.substring(0, 4));
                    ctWorkGatherArea.setAreaCode2(areaId);
                    int ctWorkGatherAreaResult = ctWorkGatherAreaMapper.insert(ctWorkGatherArea);
                    if (ctWorkGatherAreaResult < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "作业汇总区域分片表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }*/

                    try {
                        if (ctBankItem.getItemType() == 1) {
                            logger.info("[调用日志服务接口]");
                            writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
                                    .accessAction(AccessAction.PreviewHomework)
                                    .userId(userId)
                                    .occurredTime(publishDate.getTime())
                                    .build());
                        }
                        if (ctBankItem.getItemType() == 4 || ctBankItem.getItemType() == 8) {
                            logger.info("[调用日志服务接口]");
                            writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
                                    .accessAction(AccessAction.AfterHomework)
                                    .userId(userId)
                                    .occurredTime(publishDate.getTime())
                                    .build());
                        }
                    } catch (Exception e) {
                        logger.error("[写入日志失败:{}]", e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg(), CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
        }
        return res;
    }

    /**
     * @param workId   作业Id
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public int delHomework(String workId, String schoolId) throws CloudteachException {

        // 获取预习作业表信息
        logger.info("[获取教师作业信息：ct_teacher_work]");
        CtTeacherWork ctTeacherWork = ctTeacherWorkMapper.selectByPrimaryKey(workId, schoolId);

        if (ctTeacherWork == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：没有该作业！",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        if (ctTeacherWork.getStatus() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_DELETE_FAILED.getMsg(),
                    CloudTeachErrorEnum.WORK_DELETE_FAILED.getCode());
        }

        // 通过workId得到学生answerId
        List<String> answerList = ctTeacherWorkItemAnswerMapper.getAnswerListByWorkId(workId, schoolId);
        // 通过workId得到itemId
        String itemId = ctTeacherWorkItemMapper.getItemIdByWorkId(workId, schoolId);
        // 教师作业表 ct_teacher_work 数据逻辑删除
        logger.info("[删除作业：教师作业表(ct_teacher_work)]");
        ctTeacherWorkMapper.deleteTeacherWork(workId, schoolId);
        // 教师作业题目表 ct_teacher_work_item 数据逻辑删除
        logger.info("[删除作业：教师作业题目表(ct_teacher_work_item)]");
        ctTeacherWorkItemMapper.deleteTeacherWorkItem(workId, schoolId);
        // 教师作业班级表 ct_teacher_work_class 数据逻辑删除
        logger.info("[删除作业：教师作业班级表(ct_teacher_work_class)]");
        ctTeacherWorkClassMapper.deleteTeacherWorkClass(workId, schoolId);
        // 教师作业学生表 ct_teacher_work_student 数据逻辑删除
        logger.info("[删除作业：教师作业学生表(ct_teacher_work_student)]");
        ctTeacherWorkStudentMapper.deleteTeacherWorkStudent(workId, schoolId);
        // 教师作业题目学生回答表 ct_teacher_work_item_answer 数据逻辑删除
        logger.info("[删除作业：教师作业题目学生回答表(ct_teacher_work_item_answer)]");
        ctTeacherWorkItemAnswerMapper.deleteTeacherWorkItemAnswer(workId, schoolId);

        /** 20151010,健壮代码(容错处理),当answerList为空时,不执行以下操作，防止数据库层面报错而导致删除作业失败 **/
        if (CollectionUtils.isNotEmpty(answerList)) {
            // 教师作业题目学生回答附件 ct_teacher_work_item_answer_file 数据逻辑删除
            logger.info("[删除作业：教师作业题目学生回答附件(ct_teacher_work_item_answer_file)]");
            ctTeacherWorkItemAnswerFileMapper.deleteTeacherWorkItemAnswerFile(answerList, schoolId);
            // 教师作业题目学生回答计数表 ct_teacher_work_item_answer_statistics 数据逻辑删除
            logger.info("[删除作业：教师作业题目学生回答计数表(ct_teacher_work_item_answer_statistics)]");
            ctTeacherWorkItemAnswerStatisticsMapper.deleteTeacherWorkItemAnswerStatistics(answerList, schoolId);
            // 教师作业题目学生回答点赞记录 ct_teacher_work_item_answer_praise 数据逻辑删除
            logger.info("[删除作业：教师作业题目学生回答点赞记录(ct_teacher_work_item_answer_praise)]");
            ctTeacherWorkItemAnswerPraiseMapper.deleteTeacherWorkItemAnswerPraise(answerList, schoolId);
            // 教师作业题目学生回答评论记录 ct_teacher_work_item_answer_comment 数据逻辑删除
            logger.info("[删除作业：教师作业题目学生回答评论记录(ct_teacher_work_item_answer_comment)]");
            ctTeacherWorkItemAnswerCommentMapper.deleteTeacherWorkItemAnswerComment(answerList, schoolId);
        }

        // 教师题目统计表 ct_bank_item_statistics 发布次数-1
        logger.info("[更新发布次数-1]");
        ctBankItemStatisticsMapper.updateReleasesByItem(itemId, -1, schoolId);
        // 作业汇总表 ct_work_gather 数据逻辑删除
        logger.info("[删除作业：作业汇总表(ct_work_gather)]");
        ctWorkGatherMapper.delWorkGather(workId, schoolId);
        // 作业班级汇总表 ct_work_class_gather 数据逻辑删除
        logger.info("[删除作业：作业班级汇总表(ct_work_class_gather)]");
        ctWorkClassGatherMapper.delWorkClassGather(workId, schoolId);
        // 作业学生汇总表 ct_work_student_gather 数据逻辑删除
        logger.info("[删除作业：作业学生汇总表(ct_work_student_gather)]");
        ctWorkStudentGatherMapper.delWorkStudentGather(workId, schoolId);

        return 1;
    }

    /**
     * @param workId       作业Id
     * @param context      评语
     * @param praiseStatus 鼓励状态
     * @param score        评分
     * @param userId       教师用户号
     * @param schoolId     学校ID
     *                     快速批改学生作业
     */
    @Override
    public void quicklyCorrect(String workId, String context, Integer praiseStatus, Integer score, String
            userId, String schoolId) throws CloudteachException {
        logger.info("快速批改学生作业,workId:" + workId + ",context:" + context + ",praiseStatus:" + praiseStatus + ",score:" + score + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //获取作业信息
        CtTeacherWork ctTeacherWork = ctTeacherWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (ctTeacherWork == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        // 预习作业，电子作业，课外作业快速批改的时候，必须评分
        if (ctTeacherWork.getWorkType() == 1 || ctTeacherWork.getWorkType() == 4 || ctTeacherWork.getWorkType() == 8) {
            if (score == null) {
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":预习,电子,课外3类作业,快速批改必须要打分!",
                        CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        }
        //查询作业学生回答表中所有已提交未批改的学生回答信息
        List<CtTeacherWorkItemAnswer> answerlist = ctTeacherWorkItemAnswerMapper.selectSubedUnCorrectList(workId, schoolId);

        for (CtTeacherWorkItemAnswer answerinfo : answerlist) {
            //修改每个学生回答表信息（老师评分、批改状态）

            //修改学生作业相关信息批改状态
            correct(answerinfo.getAnswerId(), schoolId, score);

            if (context != null && !"".equals(context.trim())) {
                //新增评论记录
                comment(answerinfo.getAnswerId(), userId, schoolId, context, 1);
            }

            if (praiseStatus != null && 1 == praiseStatus.intValue()) {
                //鼓励（点赞）
                praise(answerinfo.getAnswerId(), userId, schoolId, 1);
            }
        }

        //修改作业汇总统计信息
        updateWorkGatherAfterCorrect(workId, schoolId);
    }

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取预习作业班级信息
     */
    @Override
    public List<WorkClassViewDTO> queryTeacherWorkClassList(String workId, String schoolId) throws
            CloudteachException {
        List<WorkClassView> guidanceWorkClassList = ctTeacherWorkClassMapper.queryTeacherWorkClassList(workId, schoolId);
        if (guidanceWorkClassList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkClassViewListToDtoList(guidanceWorkClassList);
    }

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取教师作业题目信息
     */
    @Override
    public List<TeacherWorkItemDTO> queryTeacherWorkItemList(String workId, String schoolId) throws
            CloudteachException {
        List<CtTeacherWorkItem> teacherWorkItemList = ctTeacherWorkItemMapper.queryTeacherWorkItemList(workId, schoolId);
        if (teacherWorkItemList == null || teacherWorkItemList.size() == 0) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":无法获取教师作业题目信息", CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        return entityTeacherWorkItemListToDtoList(teacherWorkItemList);
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取预习作业下未提交作业的学生信息
     */
    @Override
    public List<WorkUnSubStudentViewDTO> selectUnSubStudentList(String workId, String classId, String schoolId) throws
            CloudteachException {
        List<WorkUnSubStudentView> ctTeacherWorkStudentList = ctTeacherWorkStudentMapper.selectUnSubStudentList(workId, classId, schoolId);
        if (ctTeacherWorkStudentList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkUnSubStudentListToDtoList(ctTeacherWorkStudentList);
    }

    /**
     * @param workId      作业Id
     * @param workItemId  题目ID
     * @param classId     班级ID
     * @param schoolId    学校ID
     * @param teachUserId 作业布置教师ID
     * @throws net.xuele.common.exceptions.CloudteachException 获取某个教师作业中某个题目下某个班级的学生提交信息
     */
    @Override
    public List<WorkAnswerViewDTO> querySubedWorkAnswerList(String workId, String workItemId, String
            classId, String schoolId, String teachUserId) throws CloudteachException {
        List<WorkAnswerView> teacherWorkAnswerList = ctTeacherWorkItemAnswerMapper.
                querySubedWorkAnswerList(workId, workItemId, classId, schoolId, teachUserId);
        if (teacherWorkAnswerList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkAswViewListToDtoList(teacherWorkAnswerList);
    }

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取教师作业信息
     */
    @Override
    public TeacherWorkDetailViewDTO queryTeacherWorkDetailByWorkId(String workId, String schoolId) throws
            CloudteachException {
        TeacherWorkDetailView teacherWorkDetailView = ctTeacherWorkMapper.queryTeacherWorkDetail(workId, schoolId);
        if (teacherWorkDetailView == null) {
            return null;
        }
        TeacherWorkDetailViewDTO teacherWorkDetailViewDTO = new TeacherWorkDetailViewDTO();
        BeanUtils.copyProperties(teacherWorkDetailView, teacherWorkDetailViewDTO);
        return teacherWorkDetailViewDTO;
    }

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据workId获取教师作业题目信息(包括题目下的附件信息)
     */
    @Override
    public List<TeacherWorkItemViewDTO> queryItemContainFilesList(String workId, String schoolId) throws
            CloudteachException {
        List<TeacherWorkItemView> teacherWorkItemviewList = ctTeacherWorkItemMapper.queryItemContainFilesList(workId, schoolId);
        if (teacherWorkItemviewList == null) {
            return null;
        }
        return entityTeacherWorkItemViewListToDtoList(teacherWorkItemviewList);
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param userIcon 教师头像
     * @param userName 教师名字
     * @param workType 作业类型（1预习 2提分宝 3同步课堂 4电子作业 7口语作业）
     * @retrun 0提醒太频繁  1提醒成功
     * 根据教师作业ID发通知提醒所有未提交作业的学生
     */
    @Override
    public int warnSub(String workId, String classId, String schoolId, String userId, String userIcon, String userName, int workType) throws CloudteachException {
        logger.info("教师作业:发送提醒交作业通知,workId:" + workId + ",classId:" + classId + ",schoolId:" + schoolId + ",userId:" + userId + ",userIcon:" + userIcon + ",userName:" + userName + ",workType:" + workType);
        if (StringUtils.isEmpty(workId) || StringUtils.isEmpty(classId) || StringUtils.isEmpty(schoolId) || StringUtils.isEmpty(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法发送通知提醒学生交作业",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //获取未提交作业学生信息
        List<WorkUnSubStudentViewDTO> unSubStudentList = this.selectUnSubStudentList(workId, classId, schoolId);
        if (unSubStudentList == null || unSubStudentList.size() == 0) {
            return 0;
        }
        //验证时间间隔，如果验证通过记录最新提醒时间
        int warnSta = workStatisticsService.updateLastWarnTime(workId, classId, schoolId);
        if (warnSta != 1) {
            return 0;
        }
        List<String> studentlist = new ArrayList<>();
        for (WorkUnSubStudentViewDTO dtoobj : unSubStudentList) {
            studentlist.add(dtoobj.getUserId());
        }
        String title = Constants.WARNSUB_TITLE;
        String content = CloudTeachNotifyContent.getWarnSubContent(userName, workType);
        cloudTeachService.sendWarnSubNotify(studentlist, title, content, userId, userIcon);
        return 1;
    }

    /**
     * @param answerId 学生回答ID
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 点赞answerId对应的作业回答
     */
    @Override
    public void praise(String answerId, String userId, String schoolId, int userType) throws CloudteachException {
        logger.info("教师作业:点赞操作,answerId:" + answerId + ",userId:" + userId + ",schoolId:" + schoolId + ",userType:" + userType);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法进行点赞操作",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //查询点赞记录
        CtTeacherWorkItemAnswerPraise praiseinfo = ctTeacherWorkItemAnswerPraiseMapper.selectByPrimaryKey(answerId, userId, schoolId);
        if (praiseinfo == null) {
            //无记录则新增点赞记录
            logger.info("教师作业:新增点赞记录");
            praiseinfo = new CtTeacherWorkItemAnswerPraise();
            praiseinfo.setPraiseId(UUID.randomUUID().toString().replace("-", ""));
            praiseinfo.setAnswerId(answerId);
            praiseinfo.setUserId(userId);
            praiseinfo.setUserType(userType);
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(1);
            praiseinfo.setStatus(1);
            praiseinfo.setSchoolId(schoolId);
            ctTeacherWorkItemAnswerPraiseMapper.insert(praiseinfo);

            logger.info("教师作业:点赞后更新点赞次数");
            CtTeacherWorkItemAnswerStatistics answerStatistics = ctTeacherWorkItemAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
            answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() + 1);
            ctTeacherWorkItemAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
        } else if (praiseinfo.getPraiseStatus().intValue() == 0) {
            //有记录则更新
            logger.info("教师作业:修改点赞记录中的点赞状态");
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(1);
            ctTeacherWorkItemAnswerPraiseMapper.updateByPrimaryKey(praiseinfo);

            logger.info("教师作业:点赞后更新点赞次数");
            CtTeacherWorkItemAnswerStatistics answerStatistics = ctTeacherWorkItemAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
            answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() + 1);
            ctTeacherWorkItemAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
        }
    }

    /**
     * @param answerId 学生回答ID
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 取消点赞answerId对应的作业回答
     */
    @Override
    public void unpraise(String answerId, String userId, String schoolId, int userType) throws CloudteachException {
        logger.info("教师作业:取消点赞操作,answerId:" + answerId + ",userId:" + userId + ",schoolId:" + schoolId + ",userType:" + userType);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法进行取消点赞操作",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //查询点赞记录
        CtTeacherWorkItemAnswerPraise praiseinfo = ctTeacherWorkItemAnswerPraiseMapper.selectByPrimaryKey(answerId, userId, schoolId);
        if (praiseinfo == null) {
            //无记录
            logger.info("教师作业:新增取消点赞记录");
            praiseinfo = new CtTeacherWorkItemAnswerPraise();
            praiseinfo.setPraiseId(UUID.randomUUID().toString().replace("-", ""));
            praiseinfo.setAnswerId(answerId);
            praiseinfo.setUserId(userId);
            praiseinfo.setUserType(userType);
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(0);
            praiseinfo.setStatus(1);
            praiseinfo.setSchoolId(schoolId);
            ctTeacherWorkItemAnswerPraiseMapper.insert(praiseinfo);
        } else if (praiseinfo.getPraiseStatus().intValue() == 1) {
            //有记录则更新
            logger.info("教师作业:修改点赞记录中的点赞状态");
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(0);
            ctTeacherWorkItemAnswerPraiseMapper.updateByPrimaryKey(praiseinfo);
        }

        logger.info("教师作业:取消点赞后更新点赞次数");
        CtTeacherWorkItemAnswerStatistics answerStatistics = ctTeacherWorkItemAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
        answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() - 1);
        if (answerStatistics.getPraiseTimes().intValue() < 0) {
            answerStatistics.setPraiseTimes(0);
        }
        ctTeacherWorkItemAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
    }

    /**
     * @param answerId 学生回答ID
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @param context  评论内容
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 评论answerId对应的作业回答
     */
    @Override
    public String comment(String answerId, String userId, String schoolId, String context, int userType) throws CloudteachException {
        logger.info("教师作业:评论操作,answerId:" + answerId + ",userId:" + userId + ",schoolId:" + schoolId + ",context:" + context + ",userType:" + userType);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId) ) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法进行评论操作",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (StringUtils.isEmpty(context)) {
            throw new CloudteachException("没有评论内容，操作失败",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        logger.info("教师作业:新增评论记录");
        CtTeacherWorkItemAnswerComment commentinfo = new CtTeacherWorkItemAnswerComment();
        commentinfo.setCommentId(UUID.randomUUID().toString().replace("-", ""));
        commentinfo.setAnswerId(answerId);
        commentinfo.setUserId(userId);
        commentinfo.setUserType(userType);
        commentinfo.setContext(context);
        commentinfo.setCommentTime(new Date());
        commentinfo.setSchoolId(schoolId);
        commentinfo.setStatus(1);
        ctTeacherWorkItemAnswerCommentMapper.insert(commentinfo);

        logger.info("教师作业:更新评论次数");
        CtTeacherWorkItemAnswerStatistics answerStatistics = ctTeacherWorkItemAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
        answerStatistics.setCommentTimes(answerStatistics.getCommentTimes().intValue() + 1);
        ctTeacherWorkItemAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);

        CtTeacherWorkItemAnswer answer = ctTeacherWorkItemAnswerMapper.selectByPrimaryKey(answerId, schoolId);
        CtTeacherWork work = ctTeacherWorkMapper.selectByPrimaryKey(answer.getWorkId(), schoolId);
        // /* 20151029,除了口语作业外,去掉教师对作业评论会批改作业的操作,现在只有打分才算批改作业 */
        if (userType == 1 && work.getWorkType() == 7) {
            // 如果是口语作业，则评论就作为批改动作
            logger.info("口语作业:评论批改学生作业");
            correct(answerId, schoolId, null);
        }

        return commentinfo.getCommentId();
    }

    /**
     * @param answerId 学生回答ID
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @param score    评分
     * @throws net.xuele.common.exceptions.CloudteachException 评分answerId对应的作业回答
     */
    @Override
    public void score(String answerId, String userId, String schoolId, Integer score) throws CloudteachException {
        logger.info("教师作业:打分操作,answerId:" + answerId + ",userId:" + userId + ",schoolId:" + schoolId + ",score:" + score);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId) || score == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法进行打分操作",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.selectByPrimaryKey(answerId, schoolId);
        ctTeacherWorkItemAnswer.setScore(score);
        ctTeacherWorkItemAnswerMapper.updateByPrimaryKey(ctTeacherWorkItemAnswer);
        //修改作业汇总统计信息
        updateWorkGatherAfterCorrect(ctTeacherWorkItemAnswer.getWorkId(), schoolId);
        correct(answerId, schoolId, null);
    }

    /**
     * @param answerId 学生回答ID
     * @param userId   教师ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 删除单个学生提交的作业
     */
    @Override
    public void delStuWork(String answerId, String userId, String schoolId) throws CloudteachException {
        logger.info("教师作业:删除单个学生作业,answerId:" + answerId + ",userId:" + userId + ",schoolId:" + schoolId);
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.selectTeacherWorkAnswerForUpdate(answerId, schoolId);
        if (ctTeacherWorkItemAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (ctTeacherWorkItemAnswer.getStatus().intValue() != 1) {
            throw new CloudteachException(CloudTeachErrorEnum.DATAERROR.getMsg() + "：作业已经被删除", CloudTeachErrorEnum.DATAERROR.getCode());
        }
        CtTeacherWork ctTeacherWork = ctTeacherWorkMapper.selectByPrimaryKey(ctTeacherWorkItemAnswer.getWorkId(), schoolId);
        //验证作业是否为教师所布置
        if (ctTeacherWork == null || !ctTeacherWork.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(), CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        CtTeacherWorkStudent ctTeacherWorkStudent = ctTeacherWorkStudentMapper.getTeacherWorkStudentByStudentId(ctTeacherWorkItemAnswer.getWorkId(), ctTeacherWorkItemAnswer.getUserId(), schoolId);

        //删除学生提交的作业相关信息
        ctTeacherWorkItemAnswerCommentMapper.deleteStuWorkItemAnswerComment(answerId, schoolId);
        ctTeacherWorkItemAnswerPraiseMapper.deleteStuWorkItemAnswerPraise(answerId, schoolId);
        ctTeacherWorkItemAnswerFileMapper.deleteStuWorkItemAnswerFile(answerId, schoolId);
        ctTeacherWorkItemAnswerStatisticsMapper.deleteStuWorkItemAnswerStatistics(answerId, schoolId);
        ctTeacherWorkItemAnswerMapper.deleteStuWorkItemAnswer(answerId, schoolId);
        ctTeacherWorkStudentMapper.deleteStudentWork(ctTeacherWorkItemAnswer.getWorkId(), schoolId, ctTeacherWorkItemAnswer.getUserId());
        //初始化新的作业
        //初始化教师作业题目学生回答表
        String newanswerId = UUID.randomUUID().toString().replace("-", "");
        ctTeacherWorkItemAnswer.setAnswerId(newanswerId);
        ctTeacherWorkItemAnswer.setScore(0);
        ctTeacherWorkItemAnswer.setSysScore(0);
        ctTeacherWorkItemAnswer.setSubStatus(0);
        ctTeacherWorkItemAnswer.setCorrectStatus(0);
        ctTeacherWorkItemAnswer.setSubTime(new Date());
        ctTeacherWorkItemAnswer.setCorrectTime(new Date());
        ctTeacherWorkItemAnswer.setUpdateTime(new Date());
        ctTeacherWorkItemAnswer.setUpdateUserId(userId);
        //初始化教师作业学生表
        ctTeacherWorkStudent.setWorkUserId(UUID.randomUUID().toString().replace("-", ""));
        ctTeacherWorkStudent.setSubStatus(0);
        ctTeacherWorkStudent.setCorrectStatus(0);
        //初始化教师作业题目学生回答计数表
        CtTeacherWorkItemAnswerStatistics ctTeacherWorkItemAnswerStatistics = new CtTeacherWorkItemAnswerStatistics();
        ctTeacherWorkItemAnswerStatistics.setAnswerId(newanswerId);
        ctTeacherWorkItemAnswerStatistics.setCommentTimes(0);
        ctTeacherWorkItemAnswerStatistics.setPraiseTimes(0);
        ctTeacherWorkItemAnswerStatistics.setSchoolId(schoolId);
        ctTeacherWorkItemAnswerStatistics.setStatus(1);

        ctTeacherWorkItemAnswerMapper.insert(ctTeacherWorkItemAnswer);
        ctTeacherWorkStudentMapper.insert(ctTeacherWorkStudent);
        ctTeacherWorkItemAnswerStatisticsMapper.insert(ctTeacherWorkItemAnswerStatistics);

        //获取作业学生汇总表
        CtWorkStudentGather ctWorkStudentGather = ctWorkStudentGatherMapper.getWorkStudentGatherByWorkIdAndUserId(ctTeacherWork.getWorkId(), ctTeacherWorkStudent.getUserId(), schoolId);

        WorkStudentGatherDTO workStudentGatherDTO = new WorkStudentGatherDTO();
        BeanUtils.copyProperties(ctWorkStudentGather, workStudentGatherDTO);
        //修改作业统计表信息（更新已提交人数、已批改人数）
        workStatisticsService.updateByDelStuWork(workStudentGatherDTO);

        //初始化提交状体、批改状态
        ctWorkStudentGather.setSubStatus(0);
        ctWorkStudentGather.setCorrectStatus(0);
        ctWorkStudentGather.setViewStatus(0);
        ctWorkStudentGather.setViewTime(new Date());
        ctWorkStudentGather.setSubStatus(0);
        ctWorkStudentGather.setSubTime(new Date());
        ctWorkStudentGatherMapper.updateByPrimaryKey(ctWorkStudentGather);
    }


    /**
     * @param answerId 学生回答ID
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 批改answerId对应的作业回答
     */
    @Override
    public void correct(String answerId, String schoolId, Integer score) throws CloudteachException {
        logger.info("教师作业：单个学生批改操作,answerId:" + answerId + ",schoolId:" + schoolId + ",score:" + score);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法进行批改操作",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.selectByPrimaryKey(answerId, schoolId);

        if (ctTeacherWorkItemAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":学生作业回答信息不存在，无法信息批改操作",
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        if (ctTeacherWorkItemAnswer.getCorrectStatus().intValue() == 0) {
            ctTeacherWorkItemAnswer.setCorrectStatus(1);
            ctTeacherWorkItemAnswer.setCorrectTime(new Date());
            if (score != null && score.intValue() > 0) {
                ctTeacherWorkItemAnswer.setScore(score);
            }
            ctTeacherWorkItemAnswerMapper.updateByPrimaryKey(ctTeacherWorkItemAnswer);
            //修改学生情况表信息,将已提交未批改学生作业修改为已批改
            ctTeacherWorkStudentMapper.updateByTeacherCorrect(ctTeacherWorkItemAnswer.getWorkId(), ctTeacherWorkItemAnswer.getUserId(), schoolId);
            //修改作业学生汇总表，将已提交未批改学生作业修改为已批改
            ctWorkStudentGatherMapper.updateByTeacherCorrect(ctTeacherWorkItemAnswer.getWorkId(), ctTeacherWorkItemAnswer.getUserId(), schoolId);
        }
        //修改作业汇总统计信息
        updateWorkGatherAfterCorrect(ctTeacherWorkItemAnswer.getWorkId(), schoolId);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 学生作业批改后修改作业批改状态、作业统计信息已批改学生数
     */
    @Override
    public void updateWorkGatherAfterCorrect(String workId, String schoolId) throws CloudteachException {
        logger.info("教师作业：作业批改后修改作业学生信息、作业信息、作业统计汇总信息,workId:" + workId + ",schoolId:" + schoolId);
        //获取未批改学生信息
        List<CtTeacherWorkStudent> unCorrectStudentList = ctTeacherWorkStudentMapper.selectUnCorrectStudentList(workId, schoolId);

        //判断最新作业批改状态（0未批改 1部分批改 2全部批改）
        int newCorrectStatus = 0;
        if (unCorrectStudentList == null || unCorrectStudentList.size() == 0) {
            newCorrectStatus = 2;
        } else {
            newCorrectStatus = 1;
        }
        CtTeacherWork ctTeacherWork = ctTeacherWorkMapper.selectByPrimaryKey(workId, schoolId);
        //修改作业表信息（作业批改状态）
        if (ctTeacherWork.getCorrectStatus().intValue() < newCorrectStatus) {
            ctTeacherWork.setCorrectStatus(newCorrectStatus);
            ctTeacherWorkMapper.updateByPrimaryKey(ctTeacherWork);
        }

        //修改作业统计信息中的已批改学生数
        int unCorrectStudentNum = unCorrectStudentList == null ? 0 : unCorrectStudentList.size();
        workStatisticsService.updateCorrectStudentNum(workId, schoolId, unCorrectStudentNum);
    }

    /**
     * @param commentId 评论ID
     * @param userId    教师用户ID
     * @param schoolId  学校ID
     *                  教师删除自己布置作业学生回答下任何人的评论
     */
    @Override
    public void delCommont(String commentId, String userId, String schoolId) throws CloudteachException {
        logger.info("教师作业:教师删除评论,commentId:" + commentId + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法删除评论",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //验证删除的是否是该教师布置作业对应的评论
        CtTeacherWorkItemAnswerComment commentObj = ctTeacherWorkItemAnswerCommentMapper.selectByPrimaryKey(commentId, schoolId);
        if (commentObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":评论信息已经被删除，无法重复删除",
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.selectByPrimaryKey(commentObj.getAnswerId(), schoolId);
        if (ctTeacherWorkItemAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":学生作业回答信息不存在，无法删除评论",
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        CtTeacherWork workObj = ctTeacherWorkMapper.selectByPrimaryKey(ctTeacherWorkItemAnswer.getWorkId(), schoolId);
        if (workObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":作业信息不存在，无法删除评论",
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        if (!workObj.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        commentObj.setStatus(0);
        ctTeacherWorkItemAnswerCommentMapper.updateByPrimaryKey(commentObj);

        CtTeacherWorkItemAnswerStatistics answerStatistics = ctTeacherWorkItemAnswerStatisticsMapper.selectByPrimaryKey(commentObj.getAnswerId(), schoolId);
        answerStatistics.setCommentTimes(answerStatistics.getCommentTimes().intValue() - 1);
        ctTeacherWorkItemAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
    }

    /**
     * @param commentId 评论ID
     * @param userId    学生用户ID
     * @param schoolId  学校ID
     *                  学生删除自己的评论
     */
    @Override
    public void delStuCommont(String commentId, String userId, String schoolId) throws CloudteachException {
        logger.info("教师作业:学生删除自己评论,commentId:" + commentId + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法删除评论",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //验证删除的是否是该学生的评论
        CtTeacherWorkItemAnswerComment commentObj = ctTeacherWorkItemAnswerCommentMapper.selectByPrimaryKey(commentId, schoolId);
        if (commentObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg() + ":评论信息已经被删除，无法重复删除",
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (!commentObj.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        commentObj.setStatus(0);
        ctTeacherWorkItemAnswerCommentMapper.updateByPrimaryKey(commentObj);
    }

    /**
     * @param teacherWorkAddViewDTO 发布的作业信息
     * @param bankItemCreateDTO     发布的题目信息
     * @param diskIdList            题目中引用的云盘资源ID
     * @param fileList              题目中引用的本地文件信息
     * @param workTapeFilesDTO      教师录音文件
     * @return 0操作失败 1操作成功
     * 保存并发布教师题目（该接口用于移动端发布电子作业、口语作业）
     */
    @Override
    public int saveAndPublishItem(TeacherWorkAddViewDTO teacherWorkAddViewDTO, BankItemCreateDTO bankItemCreateDTO,
                                  List<String> diskIdList, List<BankItemAppFilesDTO> fileList,
                                  WorkTapeFilesDTO workTapeFilesDTO) throws CloudteachException {
        int retValue = 0;

        logger.info("教师作业：保存并发布教师题目");
        logger.info("teacherWorkAddViewDTO:" + teacherWorkAddViewDTO);
        logger.info("bankItemCreateDTO:" + bankItemCreateDTO);
        logger.info("diskIdList:" + diskIdList);
        logger.info("fileList:" + fileList);
        logger.info("workTapeFilesDTO:" + workTapeFilesDTO);
        if (teacherWorkAddViewDTO == null || bankItemCreateDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":参数不正确无法发布作业",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (teacherWorkAddViewDTO.getWorkType().intValue() != 4 && teacherWorkAddViewDTO.getWorkType().intValue() != 7) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":作业类型不正确无法发布作业",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (bankItemCreateDTO.getItemType().intValue() != 4 && bankItemCreateDTO.getItemType().intValue() != 7) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":作业类型不正确无法发布作业",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (workTapeFilesDTO != null) {
            if (StringUtils.isEmpty(workTapeFilesDTO.getUrl())
                    || StringUtils.isEmpty(workTapeFilesDTO.getFileName())
                    || StringUtils.isEmpty(workTapeFilesDTO.getExtension())
                    || workTapeFilesDTO.getSize() == null) {
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：教师录音文件信息不全无法发布作业",
                        CloudTeachErrorEnum.PARAMERROR.getCode());
            }

        }
        logger.info("教师作业：保存本地文件至云盘下的习题分类中");
        for (BankItemAppFilesDTO fileObj : fileList) {
            CloudDiskDTO diskDTO = new CloudDiskDTO();
            diskDTO.setCreator(teacherWorkAddViewDTO.getUserId());
            diskDTO.setExtension(fileObj.getExtension());//扩展名
            diskDTO.setFileUri(fileObj.getUrl());//文件uri
            diskDTO.setFilePk("");
            diskDTO.setDescription("");
            diskDTO.setAuditInstructions("");
            diskDTO.setPid("");
            diskDTO.setSize(fileObj.getSize());
            diskDTO.setName(fileObj.getFileName());
            diskDTO.setUnitId(teacherWorkAddViewDTO.getUnitId());
            diskDTO.setFileType(5);
            diskDTO.setUserId(teacherWorkAddViewDTO.getUserId());
            diskDTO.setSchoolId(teacherWorkAddViewDTO.getSchoolId());
            diskDTO = cloudDiskService.uploadFile(diskDTO);
            diskIdList.add(diskDTO.getDiskId());
        }

        bankItemCreateDTO.setDiskIds(diskIdList);
        String itemId = bankItemService.createBankItem(bankItemCreateDTO);

        logger.info("教师作业：发布作业");
        List<String> itemIdList = new ArrayList<>();
        itemIdList.add(itemId);
        teacherWorkAddViewDTO.setItemList(itemIdList);
        TeacherWorkItemDTO teacherWorkItemDTO = new TeacherWorkItemDTO();
        teacherWorkItemDTO.setItemId(itemId);
        teacherWorkItemDTO.setUnitId(teacherWorkAddViewDTO.getUnitId());
        teacherWorkItemDTO.setItemType(bankItemCreateDTO.getItemType());
        teacherWorkItemDTO.setSubTape(bankItemCreateDTO.getSubTape());
        teacherWorkItemDTO.setVoiceContext(bankItemCreateDTO.getVoiceContext());
        if (bankItemCreateDTO.getItemType() != null && 7 == bankItemCreateDTO.getItemType().intValue()) {
            //如果是语音作业
            teacherWorkItemDTO.setSubImage(0);
            teacherWorkItemDTO.setSubVideo(0);
        } else {
            teacherWorkItemDTO.setSubImage(bankItemCreateDTO.getSubImage());
            teacherWorkItemDTO.setSubVideo(bankItemCreateDTO.getSubVideo());
        }

        // teacherWorkItemDTO.setSubOther(0); /**20151012,提交方式不限制修改,subOther有页面上选择**/
        teacherWorkItemDTO.setSubOther(bankItemCreateDTO.getSubOther());
        teacherWorkItemDTO.setContext(bankItemCreateDTO.getContext());

        String ret = null;
        ret = this.addHomework(teacherWorkAddViewDTO, workTapeFilesDTO, teacherWorkItemDTO);
        if (ret == "" || ret == null) {
            retValue = 1;
        } else {
            retValue = 0;
        }
        return retValue;
    }

    /**
     * 教师保存并发布课外作业
     *
     * @param extraWorkDTO  发布课外作业信息
     * @param itemCreateDTO 发布课外题目信息
     * @param filesList     本地上传的文件信息
     * @param tapeFilesDTO  附带教师录音信息
     * @return
     * @throws CloudteachException
     */
    @Override
    public int saveAndPubExtraWork(TeacherWorkAddViewDTO extraWorkDTO, BankItemCreateDTO itemCreateDTO,
                                   List<BankItemAppFilesDTO> filesList, WorkTapeFilesDTO tapeFilesDTO) throws CloudteachException {

        int ret = 0;
        logger.info("[课外作业,保存并发布课外作业:]");
        logger.info("[extraWorkDTO: {}]", extraWorkDTO);
        logger.info("[bankItemCreateDTO: {}]", itemCreateDTO);
        logger.info("[filesList: {}]", filesList);
        logger.info("[workTapeFilesDTO: {}]", tapeFilesDTO);

        logger.info("[验证参数是否正确:]");
        if (extraWorkDTO == null || itemCreateDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":请填写要布置的作业信息",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (extraWorkDTO.getWorkType().intValue() != Constants.WORK_TYPE_EXTRA_WORK) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":作业类型不正确无法发布作业",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (tapeFilesDTO != null) {
            if (StringUtils.isEmpty(tapeFilesDTO.getUrl())
                    || StringUtils.isEmpty(tapeFilesDTO.getFileName())
                    || StringUtils.isEmpty(tapeFilesDTO.getExtension())
                    || tapeFilesDTO.getSize() == null) {
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：教师录音文件信息不全无法发布作业",
                        CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        }

        logger.info("[课外作业:新建课外作业题目]");
        itemCreateDTO.setFilesList(filesList);
        String itemId = bankItemService.createBankItem(itemCreateDTO);

        logger.info("[课外作业:发布作业]");
        List<String> itemIdList = new ArrayList<>();
        itemIdList.add(itemId);
        extraWorkDTO.setItemList(itemIdList);
        TeacherWorkItemDTO teacherWorkItemDTO = new TeacherWorkItemDTO();

        teacherWorkItemDTO.setItemId(itemId);
        teacherWorkItemDTO.setUnitId(extraWorkDTO.getUnitId());
        teacherWorkItemDTO.setItemType(itemCreateDTO.getItemType());
        teacherWorkItemDTO.setSubTape(itemCreateDTO.getSubTape());
        teacherWorkItemDTO.setSubImage(itemCreateDTO.getSubImage());
        teacherWorkItemDTO.setSubVideo(itemCreateDTO.getSubVideo());
        teacherWorkItemDTO.setSubOther(itemCreateDTO.getSubOther());
        teacherWorkItemDTO.setVoiceContext(itemCreateDTO.getVoiceContext());
        teacherWorkItemDTO.setContext(itemCreateDTO.getContext());

        String retValue = null;
        retValue = this.addHomework(extraWorkDTO, tapeFilesDTO, teacherWorkItemDTO);
        if (retValue == "" || retValue == null) {
            ret = 1;
        } else {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + retValue, CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
        }
        return ret;
    }

    /**
     * 教师编辑保存并发布课外作业
     *
     * @param extraWorkDTO 发布课外作业信息
     * @param itemEditDTO  发布课外题目信息
     * @param tapeFilesDTO 附带教师录音信息
     * @param userId       用户ID
     * @param schoolId     学校ID
     * @return
     * @throws CloudteachException
     */
    @Override
    public int editAndPubExtraWork(TeacherWorkAddViewDTO extraWorkDTO, BankItemEditDTO itemEditDTO, WorkTapeFilesDTO tapeFilesDTO, String userId, String schoolId) throws CloudteachException {

        int ret = 0;
        logger.info("[课外作业,保存并发布课外作业:]");
        logger.info("[extraWorkDTO: {}]", extraWorkDTO);
        logger.info("[bankItemEditDTO: {}]", itemEditDTO);
        logger.info("[workTapeFilesDTO: {}]", tapeFilesDTO);

        logger.info("[验证参数是否正确:]");
        if (extraWorkDTO == null || itemEditDTO == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":请填写要布置的作业信息",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (extraWorkDTO.getWorkType().intValue() != Constants.WORK_TYPE_EXTRA_WORK) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":作业类型不正确无法发布作业",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (tapeFilesDTO != null) {
            if (StringUtils.isEmpty(tapeFilesDTO.getUrl())
                    || StringUtils.isEmpty(tapeFilesDTO.getFileName())
                    || StringUtils.isEmpty(tapeFilesDTO.getExtension())
                    || tapeFilesDTO.getSize() == null) {
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "：教师录音文件信息不全无法发布作业",
                        CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        }

        logger.info("[课外作业:编辑课外作业题目]");
        String itemId = itemEditDTO.getItemId();
        bankItemService.editItem(itemEditDTO, schoolId, userId);

        logger.info("[课外作业:发布作业]");
        List<String> itemIdList = new ArrayList<>();
        itemIdList.add(itemId);
        extraWorkDTO.setItemList(itemIdList);
        TeacherWorkItemDTO teacherWorkItemDTO = new TeacherWorkItemDTO();
        teacherWorkItemDTO.setItemId(itemId);
        teacherWorkItemDTO.setUnitId(extraWorkDTO.getUnitId());
        teacherWorkItemDTO.setItemType(itemEditDTO.getItemType());
        teacherWorkItemDTO.setSubTape(itemEditDTO.getSubTape());
        teacherWorkItemDTO.setSubImage(itemEditDTO.getSubImage());
        teacherWorkItemDTO.setSubVideo(itemEditDTO.getSubVideo());
        teacherWorkItemDTO.setSubOther(itemEditDTO.getSubOther());
        teacherWorkItemDTO.setVoiceContext(itemEditDTO.getVoiceContext());
        teacherWorkItemDTO.setContext(itemEditDTO.getContext());

        String retValue = null;
        retValue = this.addHomework(extraWorkDTO, tapeFilesDTO, teacherWorkItemDTO);
        if (retValue == "" || retValue == null) {
            ret = 1;
        } else {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + retValue, CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
        }
        return ret;
    }

    /**
     * 获取某个教师某个课程对应某种教师作业（预习、电子、口语）信息,用于授课课件接口
     *
     * @param schoolId 学校ID
     * @param unitId   课程ID
     * @param userId   教师ID
     * @param workType 作业类型（0预习&电子&口语 1预习 4电子作业 7口语作业）
     * @return
     */
    @Override
    public List<CoursewareTeacherWorkViewDTO> queryCoursewareTeacherWorkList(String schoolId, String unitId, String userId, Integer workType) {
        logger.info("教师作业：根据课程、作业类型获取教师作业,schoolId:" + schoolId + ",unitId:" + unitId + ",userId:" + userId + ",workType:" + workType);
        if (StringUtils.isEmpty(schoolId) || StringUtils.isEmpty(unitId) || StringUtils.isEmpty(userId) || workType == null) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if (workType.intValue() != 0 && workType.intValue() != 1 && workType.intValue() != 4 && workType.intValue() != 7) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":作业类型错误无法获取作业信息", CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        List<CoursewareTeacherWorkView> coursewareTeacherWorkViewList = ctWorkGatherMapper.queryCoursewareTeacherWorkList(schoolId, unitId, userId, workType);
        if (coursewareTeacherWorkViewList == null) {
            return null;
        }

        /** 生成workList */
        List<CoursewareTeacherWorkViewDTO> coursewareTeacherWorkViewDTOList = new ArrayList<>();
        for (CoursewareTeacherWorkView objDATA : coursewareTeacherWorkViewList) {
            CoursewareTeacherWorkViewDTO objDTO = toCoursewareTeacherWorkViewDTO(objDATA);
            coursewareTeacherWorkViewDTOList.add(objDTO);
        }

        return coursewareTeacherWorkViewDTOList;
    }

    /**
     * 获取教师作业对应某个学生的回答信息
     *
     * @param schoolId  学校ID
     * @param studentId 学生ID
     * @param workId    作业Id
     * @return
     */
    @Override
    public TeacherWorkItemAnswerDTO getStuTeacherWorkAnswer(String schoolId, String studentId, String workId) {
        logger.info("获取教师作业对应某个学生的回答信息,schoolId:" + schoolId + ",studentId:" + studentId + ",workId:" + workId);
        CtTeacherWorkItemAnswer ctTeacherWorkItemAnswer = ctTeacherWorkItemAnswerMapper.getTeacherWorkItemAnswerByWorkStudentId(workId, studentId, schoolId);
        if (ctTeacherWorkItemAnswer == null) {
            return null;
        }
        TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO = new TeacherWorkItemAnswerDTO();
        BeanUtils.copyProperties(ctTeacherWorkItemAnswer, teacherWorkItemAnswerDTO);
        return teacherWorkItemAnswerDTO;
    }

    /**
     * 获取教师作业对应某个班级学生的作业统计信息
     *
     * @param schoolId 学校ID
     * @param classId  班级ID
     * @param workId   作业Id
     * @param workType 作业类型
     * @return
     */
    @Override
    public SRTeacherWorkDTO getTeacherWorkSR(String schoolId, String classId, String workId, int workType) {
        logger.info("获取教师作业对应某个班级学生的作业统计信息,schoolId:" + schoolId + ",workId:" + workId + ",classId:" + classId + ",workType:" + workType);

        if (1 != workType && 4 != workType && 7 != workType && 8 != workType) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + ":作业类型不正确", CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //作业统计信息
        SRTeacherWorkDTO sRTeacherWorkDTO = new SRTeacherWorkDTO();
        //得分为A学生列表
        List<SRTeacherWorkStuDTO> scoreAStuList = new ArrayList<>();
        //得分为B学生列表
        List<SRTeacherWorkStuDTO> scoreBStuList = new ArrayList<>();
        //得分为C学生列表
        List<SRTeacherWorkStuDTO> scoreCStuList = new ArrayList<>();
        //得分为D学生列表
        List<SRTeacherWorkStuDTO> scoreDStuList = new ArrayList<>();
        //未打分学生列表
        List<SRTeacherWorkStuDTO> unScoreStuList = new ArrayList<>();
        //已打分学生按成绩排名列表
        List<SRTeacherWorkStuDTO> orderStuList = new ArrayList<>();

        //获取该作业对应班级的学生总数
        logger.info("获取该作业对应班级的学生总数:");
        int totalStuNum = ctTeacherWorkStudentMapper.queryClassStuCount(workId, classId, schoolId);
        if (totalStuNum <= 0) {
            throw new CloudteachException(CloudTeachErrorEnum.CLASSNOSTUDENT.getMsg() + ":无法获取该班级学生作业的统计信息", CloudTeachErrorEnum.CLASSNOSTUDENT.getCode());
        }
        sRTeacherWorkDTO.setClassStuNum(totalStuNum);
        //获取教师作业某个班级中已提交作业的学生信息
        logger.info("获取教师作业某个班级中已提交作业的学生信息:");
        List<WorkAnswerView> subedAnswerList = ctTeacherWorkItemAnswerMapper.queryClassSubedStuList(workId, classId, schoolId);
        if (subedAnswerList != null && subedAnswerList.size() > 0) {
            sRTeacherWorkDTO.setSubStuNum(subedAnswerList.size());
        }
        for (WorkAnswerView workAnswerView : subedAnswerList) {
            SRTeacherWorkStuDTO sRTeacherWorkStuDTO = new SRTeacherWorkStuDTO();
            sRTeacherWorkStuDTO.setStudentId(workAnswerView.getUserId());
            sRTeacherWorkStuDTO.setStudentName(workAnswerView.getUserName());
            sRTeacherWorkStuDTO.setHeadUrl(workAnswerView.getIcon());
            sRTeacherWorkStuDTO.setSubStatus(workAnswerView.getSubStatus());
            sRTeacherWorkStuDTO.setCorrectStatus(workAnswerView.getCorrectStatus());

            if (1 == workType || 4 == workType || 8 == workType) {
                //预习作业或电子作业
                sRTeacherWorkStuDTO.setScore(workAnswerView.getScore());
                if (workAnswerView.getScore() == 1) {
                    scoreAStuList.add(sRTeacherWorkStuDTO);
                } else if (workAnswerView.getScore() == 2) {
                    scoreBStuList.add(sRTeacherWorkStuDTO);
                } else if (workAnswerView.getScore() == 3) {
                    scoreCStuList.add(sRTeacherWorkStuDTO);
                } else if (workAnswerView.getScore() == 4) {
                    scoreDStuList.add(sRTeacherWorkStuDTO);
                } else {
                    unScoreStuList.add(sRTeacherWorkStuDTO);
                }
            } else if (7 == workType) {
                //口语作业
                sRTeacherWorkStuDTO.setScore(workAnswerView.getSysScore());
                if (workAnswerView.getSysScore() >= 85) {
                    scoreAStuList.add(sRTeacherWorkStuDTO);
                } else if (workAnswerView.getSysScore() >= 70 && workAnswerView.getSysScore() < 85) {
                    scoreBStuList.add(sRTeacherWorkStuDTO);
                } else if (workAnswerView.getSysScore() >= 55 && workAnswerView.getSysScore() < 70) {
                    scoreCStuList.add(sRTeacherWorkStuDTO);
                } else {
                    scoreDStuList.add(sRTeacherWorkStuDTO);
                }
                orderStuList.add(sRTeacherWorkStuDTO);
            }
        }
        sRTeacherWorkDTO.setFinshPect((int) NumberFormat.getFloatRound(100.0 * sRTeacherWorkDTO.getSubStuNum() / sRTeacherWorkDTO.getClassStuNum(), 0));
        sRTeacherWorkDTO.setScoreAPect((int) NumberFormat.getFloatRound(100.0 * scoreAStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreBPect((int) NumberFormat.getFloatRound(100.0 * scoreBStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreCPect((int) NumberFormat.getFloatRound(100.0 * scoreCStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreDPect((int) NumberFormat.getFloatRound(100.0 * scoreDStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setUnScorePect((int) NumberFormat.getFloatRound(100.0 * unScoreStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreAStuList(scoreAStuList);
        sRTeacherWorkDTO.setScoreBStuList(scoreBStuList);
        sRTeacherWorkDTO.setScoreCStuList(scoreCStuList);
        sRTeacherWorkDTO.setScoreDStuList(scoreDStuList);
        sRTeacherWorkDTO.setUnScoreStuList(unScoreStuList);
        sRTeacherWorkDTO.setOrderStuList(orderStuList);

        return sRTeacherWorkDTO;
    }

    //=======================private methods================================//
    private List<TeacherWorkItemDTO> entityTeacherWorkItemListToDtoList(List<CtTeacherWorkItem> datalist) {
        List<TeacherWorkItemDTO> resList = new ArrayList<>();
        for (CtTeacherWorkItem objDATA : datalist) {
            TeacherWorkItemDTO objDTO = new TeacherWorkItemDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    private List<TeacherWorkItemViewDTO> entityTeacherWorkItemViewListToDtoList
            (List<TeacherWorkItemView> datalist) {
        List<TeacherWorkItemViewDTO> resList = new ArrayList<>();
        for (TeacherWorkItemView objDATA : datalist) {
            TeacherWorkItemViewDTO objDTO = new TeacherWorkItemViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            objDTO.setItemFilesList(entityBankItemFilesViewListToDtoList(objDATA.getItemFilesList()));
            resList.add(objDTO);
        }
        return resList;
    }

    private List<BankItemFilesViewDTO> entityBankItemFilesViewListToDtoList(List<BankItemFilesView> datalist) {
        List<BankItemFilesViewDTO> resList = new ArrayList<>();
        for (BankItemFilesView objDATA : datalist) {
            BankItemFilesViewDTO objDTO = new BankItemFilesViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    /**
     * 生成作业DTO对象
     *
     * @param objDATA 作业View对象
     * @return
     */
    private CoursewareTeacherWorkViewDTO toCoursewareTeacherWorkViewDTO(CoursewareTeacherWorkView objDATA) {
        if (objDATA == null) {
            return null;
        }
        CoursewareTeacherWorkViewDTO coursewareTeacherWorkViewDTO = new CoursewareTeacherWorkViewDTO();
        coursewareTeacherWorkViewDTO.setWorkId(objDATA.getWorkId());
        coursewareTeacherWorkViewDTO.setUserId(objDATA.getUserId());
        coursewareTeacherWorkViewDTO.setPublishTime(objDATA.getPublishTime());
        coursewareTeacherWorkViewDTO.setSubjectId(objDATA.getSubjectId());
        coursewareTeacherWorkViewDTO.setUnitId(objDATA.getUnitId());
        coursewareTeacherWorkViewDTO.setSubjectName(objDATA.getSubjectName());
        coursewareTeacherWorkViewDTO.setUnitName(objDATA.getUnitName());
        coursewareTeacherWorkViewDTO.setContext(objDATA.getContext());
        coursewareTeacherWorkViewDTO.setFiles(objDATA.getFiles());
        coursewareTeacherWorkViewDTO.setWorkType(objDATA.getWorkType());
        coursewareTeacherWorkViewDTO.setWorkItemNum(objDATA.getWorkItemNum());
        coursewareTeacherWorkViewDTO.setWorkClassNum(objDATA.getWorkClassNum());
        coursewareTeacherWorkViewDTO.setWorkClassJson(objDATA.getWorkClassJson());
        coursewareTeacherWorkViewDTO.setWorkStudentNum(objDATA.getWorkStudentNum());
        coursewareTeacherWorkViewDTO.setSchoolId(objDATA.getSchoolId());
        coursewareTeacherWorkViewDTO.setStatus(objDATA.getStatus());

        /** workItemList 【生成作业附带的题目列表  目前为单个题目 】 */
        if (CollectionUtils.isNotEmpty(objDATA.getWorkItemList())) {
            List<TeacherWorkItemViewDTO> workItemViewDTOList = new ArrayList<>();
            for (TeacherWorkItemView workItemView : objDATA.getWorkItemList()) {
                TeacherWorkItemViewDTO workItemViewDTO = toTeacherWorkItemViewDTO(workItemView);
                workItemViewDTOList.add(workItemViewDTO);
            }
            coursewareTeacherWorkViewDTO.setWorkItemList(workItemViewDTOList);
        }

        return coursewareTeacherWorkViewDTO;
    }

    /**
     * 生成题目DTO
     *
     * @param workItemView 作业题目VIEW对象
     * @return
     */
    private TeacherWorkItemViewDTO toTeacherWorkItemViewDTO(TeacherWorkItemView workItemView) {
        if (workItemView == null) {
            return null;
        }
        TeacherWorkItemViewDTO teacherWorkItemViewDTO = new TeacherWorkItemViewDTO();

        teacherWorkItemViewDTO.setItemId(workItemView.getItemId());
        teacherWorkItemViewDTO.setItemType(workItemView.getItemType());
        teacherWorkItemViewDTO.setContext(workItemView.getContext());
        teacherWorkItemViewDTO.setVoiceContext(workItemView.getVoiceContext());

        /** fileList 【生成题目附件列表】 */
        if (CollectionUtils.isNotEmpty(workItemView.getItemFilesList())) {
            List<BankItemFilesViewDTO> filesViewDTOList = new ArrayList<>();
            for (BankItemFilesView itemFilesView : workItemView.getItemFilesList()) {
                BankItemFilesViewDTO itemFilesViewDTO = toBankItemFilesViewDTO(itemFilesView);
                filesViewDTOList.add(itemFilesViewDTO);
            }
            teacherWorkItemViewDTO.setItemFilesList(filesViewDTOList);
        }

        return teacherWorkItemViewDTO;
    }

    /**
     * 生成题目附件
     *
     * @param itemFilesView
     * @return
     */
    private BankItemFilesViewDTO toBankItemFilesViewDTO(BankItemFilesView itemFilesView) {
        if (itemFilesView == null) {
            return null;
        }
        BankItemFilesViewDTO bankItemFilesViewDTO = new BankItemFilesViewDTO();
        bankItemFilesViewDTO.setFileId(itemFilesView.getFileId());
        bankItemFilesViewDTO.setfType(itemFilesView.getfType());
        bankItemFilesViewDTO.setRelationId(itemFilesView.getRelationId());
        bankItemFilesViewDTO.setDiskId(itemFilesView.getDiskId());
        bankItemFilesViewDTO.setFileUri(itemFilesView.getFileUri());
        bankItemFilesViewDTO.setExtension(itemFilesView.getExtension());
        bankItemFilesViewDTO.setName(itemFilesView.getName());
        return bankItemFilesViewDTO;
    }
}
