package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.constant.MagicQueVideoTypeUtil;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.MagicQuestionService;
import net.xuele.cloudteach.service.MagicWorkService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.service.common.CloudTeachNotifyContent;
import net.xuele.cloudteach.service.util.*;
import net.xuele.cloudteach.view.*;
import net.xuele.common.exceptions.CloudteachException;
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
 * MagicWorkServiceImpl
 * 教师提分宝作业服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
@Service
public class MagicWorkServiceImpl implements MagicWorkService {

    private static Logger logger = LoggerFactory.getLogger(MagicWorkServiceImpl.class);

    @Autowired
    CtMagicWorkMapper ctMagicWorkMapper;
    @Autowired
    CtMagicWorkAnswerCommentMapper ctMagicWorkAnswerCommentMapper;
    @Autowired
    CtMagicWorkAnswerFilesMapper ctMagicWorkAnswerFilesMapper;
    @Autowired
    CtMagicWorkAnswerMapper ctMagicWorkAnswerMapper;
    @Autowired
    CtMagicWorkAnswerPraiseMapper ctMagicWorkAnswerPraiseMapper;
    @Autowired
    CtMagicWorkAnswerStatisticsMapper ctMagicWorkAnswerStatisticsMapper;
    @Autowired
    CtMagicWorkClassMapper ctMagicWorkClassMapper;
    @Autowired
    CtWorkStatisticsMapper ctWorkStatisticsMapper;//作业统计表
    @Autowired
    CtMagicQuestionBankStatisticsMapper magicQuestionBankStatisticsMapper;//题库发布统计
    @Autowired
    MagicQuestionService magicQuestionService;//提分宝题库服务
    @Autowired
    CtWorkTapeFilesMapper ctWorkTapeFilesMapper;//作业教师录音附件
    @Autowired
    CtWorkGatherMapper ctWorkGatherMapper;//作业汇总表
    @Autowired
    CtWorkStudentGatherMapper ctWorkStudentGatherMapper;//学生作业汇总表
    @Autowired
    CtWorkClassGatherMapper ctWorkClassGatherMapper;//作业班级汇总表
    @Autowired
    CtMagicWorkChallengeMapper ctMagicWorkChallengeMapper;//学生挑战记录表
    @Autowired
    CtMagicWorkChallengeQueMapper ctMagicWorkChallengeQueMapper;//学生挑战题目表

    @Autowired
    CtMagicQuestionVideoMapper ctMagicQuestionVideoMapper;//提分寶視頻講解

    @Autowired
    WorkStatisticsService workStatisticsService;//作业汇总统计服务
    @Autowired
    CloudTeachService cloudTeachService;//云教学服务

    @Autowired
    CtUnitsMapper ctUnitsMapper;

    /**
     * 新增提分宝作业
     *
     * @param magicWorkFormDTO
     * @throws CloudteachException
     * @author panglx
     */
    @Override
    public String addMagicWork(MagicWorkFormDTO magicWorkFormDTO) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:新增提分宝作业,magicWorkFormDTO:" + magicWorkFormDTO);
        List<String> classList = magicWorkFormDTO.getClassList();
        //选择班级数
        int initClassNum = classList.size();
        String classJson = magicWorkFormDTO.getClassJosn();
        try {
            logger.info("校验班级列表跟班级json中的班级id是否一致");
            if (VerifyParam.verifyClassInfo(classList, classJson)) {
                //查询班级下的学生数
                List<StudentNumView> studentNumViewList = ctUnitsMapper.getStudentNumBySchoolId(classList, magicWorkFormDTO.getMagicWorkDTO().getSchoolId());
                //判断后的班级Id,去除该班级下学生为空的id;
                classList = TranslateCommon.getSchoolIdList(studentNumViewList);
                //去除空班级后的班级数
                int nowClassNum = classList.size();
                if (initClassNum != nowClassNum) {
                    return TranslateCommon.getClassInfoById(classList, classJson);
                }
                CtMagicWork magicWork = new CtMagicWork();
                BeanUtils.copyProperties(magicWorkFormDTO.getMagicWorkDTO(), magicWork);

                logger.info("初始化作业数据");
                magicWork.setWorkId(UUID.randomUUID().toString().replace("-", ""));
                if (DateTimeUtil.compareNowDate(magicWork.getPublishTime(), "yyyy-MM-dd") == 1) {
                    magicWork.setWorkType(0);//定时发布
                } else {
                    magicWork.setWorkType(1);//及时发布
                }
                magicWork.setFinishStatus(0);//未完成
                magicWork.setCorrectStatus(0);//未批改
                magicWork.setCreateTime(new Date());//创建时间
                magicWork.setUpdateTime(new Date());//修改时间
                magicWork.setStatus(1);
                logger.info("提分表作业表inset记录");
                int result = ctMagicWorkMapper.insert(magicWork);
                if (result < 1) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分表作业表初始化失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
                logger.info("提分宝作业班级表inset记录");
                CtMagicWorkClass workClass;
                List<CtMagicWorkAnswer> answerList = new ArrayList<>();
                List<CtMagicWorkAnswer> answers;
                for (String classId : classList) {
                    //判断发布班级是否为空，为空不发布
                    workClass = new CtMagicWorkClass();
                    workClass.setWorkClassId(UUID.randomUUID().toString().replace("-", ""));
                    workClass.setClassId(classId);
                    workClass.setStatus(1);
                    workClass.setWorkId(magicWork.getWorkId());
                    workClass.setSchoolId(magicWork.getSchoolId());//学校id--用于分片
                    result = ctMagicWorkClassMapper.insert(workClass);
                    if (result < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业班级表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }
                    logger.info("查询班级对应的学生回答信息");
                    answers = ctMagicWorkClassMapper.selectWorkAnswer(workClass);
                    if (CollectionUtils.isNotEmpty(answers)) {
                        answerList.addAll(answers);
                    }
                    logger.info("作业班级汇总表初始化");
                    CtWorkClassGather workClassGather = new CtWorkClassGather();
                    BeanUtils.copyProperties(workClass, workClassGather);
                    workClassGather.setWorkClassId(UUID.randomUUID().toString().replace("-", ""));
                    result = ctWorkClassGatherMapper.insert(workClassGather);
                    if (result < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业班级汇总表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }
                }
                logger.info("提分宝作业回答表初始化");
                if (CollectionUtils.isEmpty(answerList)) {
                    //查找的对象不存在
                    throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                }
                result = ctMagicWorkAnswerMapper.initialize(answerList);
                if (result < answerList.size()) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业回答表初始化失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
                logger.info("提分宝作业学生回答统计表初始化");
                result = ctMagicWorkAnswerStatisticsMapper.initialize(answerList);
                if (result < answerList.size()) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业学生回答统计表初始化失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
                logger.info("提分宝作业统计表初始化");
                CtWorkStatistics workStatistics = new CtWorkStatistics();
                workStatistics.setWorkId(magicWork.getWorkId());//作业id
                workStatistics.setVersion(0);//版本号
                workStatistics.setWorkViewStudentNum(0);//已查看学生数
                workStatistics.setWorkSubStudentNum(0);//已提交学生数
                workStatistics.setWorkCorrectStudentNum(0);//已批改学生数
                workStatistics.setSchoolId(magicWork.getSchoolId());//学校id--用于分片
                result = ctWorkStatisticsMapper.insert(workStatistics);
                if (result < 1) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业统计表初始化失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
                logger.info("作业汇总表初始化");
                CtWorkGather workGather = new CtWorkGather();
                workGather.setWorkId(magicWork.getWorkId());//作业id
                workGather.setUserId(magicWork.getUserId());
                workGather.setPublishTime(magicWork.getPublishTime());//发布时间
                workGather.setEndTime(magicWork.getEndTime());//作业最晚提交时间
                logger.info("调用服务查询课程信息");
                UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(magicWork.getUnitId());
                workGather.setUnitId(magicWork.getUnitId());//课程id
                workGather.setUnitName(unitsDTO.getUnitName());//`   课程名称
                logger.info("调用服务查询课本信息");
                BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(unitsDTO.getBookId());
                workGather.setSubjectId(bookDTO.getSubjectId());//科目id
                workGather.setSubjectName(bookDTO.getSubjectName());// 科目名称
                workGather.setContext(magicWork.getContext());//作业描述
                workGather.setFiles("");// 附件数组--提分宝无附件
                workGather.setWorkType(2);//1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
                workGather.setWorkItemNum(magicQuestionService.countByBankId(magicWork.getBankId(), 0));//预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
                workGather.setWorkClassNum(classList.size());//班级数
                workGather.setWorkClassJson(classJson);//班级信息(jason格式)包括班级编号，班级名称
                workGather.setWorkStudentNum(ctMagicWorkAnswerMapper.countByWorkId(magicWork.getWorkId(), magicWork.getSchoolId()));//作业对应学生数
                workGather.setSchoolId(magicWork.getSchoolId());//学校ID:用于数据库分片存储
                workGather.setStatus(1);//状态:0删除 1有效
                result = ctWorkGatherMapper.insert(workGather);
                if (result < 1) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业汇总表初始化失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
                logger.info("学生作业汇总表初始化");
                result = ctWorkStudentGatherMapper.initMagicWorkStudentGather(answerList);
                if (result < answerList.size()) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝学生作业汇总表初始化失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
                WorkTapeFilesDTO tapeFilesDTO = magicWorkFormDTO.getTapeFilesDTO();
                logger.info("作业教师录音附件表");
                if (null != tapeFilesDTO) {
                    CtWorkTapeFiles workTapeFiles = new CtWorkTapeFiles();
                    BeanUtils.copyProperties(tapeFilesDTO, workTapeFiles);
                    workTapeFiles.setFileId(UUID.randomUUID().toString().replace("-", ""));
                    workTapeFiles.setWorkId(magicWork.getWorkId());//作业id
                    workTapeFiles.setWorkType(2);//作业类型--提分神奇作业
                    workTapeFiles.setUploadTime(new Date());//上传时间
                    workTapeFiles.setSchoolId(magicWork.getSchoolId());
                    workTapeFiles.setStatus(1);
                    result = ctWorkTapeFilesMapper.insert(workTapeFiles);
                    if (result < 1) {
                        throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业教师录音附件表初始化失败",
                                CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                    }
                }
                logger.info("发布次数+1");
                result = this.updateReleases(magicWork.getBankId(), 1);
                if (result < 1) {
                    throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg() + "：提分宝作业发布次数+1失败",
                            CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
                }
            } else {
                //参数错误
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * @param unitId
     * @return
     * @throws CloudteachException
     * @author panglx
     * 显示云作业题库列表
     */
    public List<MagicWorkListDTO> showMagicWorkList(String unitId, String extraBookId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取云作业题库列表,unitId:" + unitId + ",extraBookId:" + extraBookId);
        List<MagicWorkListDTO> magicWorkListDTOs = new ArrayList<>();//提分宝封装DTO列表
        //根据课程ID获取提分宝原题题库列表信息
        List<MagicQuestionBankDTO> magicQuestionBankDTOs = magicQuestionService.queryMagicQuestionBankListByUnitId(unitId, extraBookId);
        if (CollectionUtils.isEmpty(magicQuestionBankDTOs))
            return magicWorkListDTOs;
        MagicWorkListDTO magicWorkListDTO;//提分宝封装DTO
        for (MagicQuestionBankDTO bankDTO : magicQuestionBankDTOs) {
            magicWorkListDTO = new MagicWorkListDTO();
            BeanUtils.copyProperties(bankDTO, magicWorkListDTO);
            //提分宝题目信息
            List<MagicQuestionDTO> magicQuestionDTOs = magicQuestionService.queryMagicQuestionMasterListByBankId(bankDTO.getBankId());
            //题库发布次数
            CtMagicQuestionBankStatistics bankStatistics = magicQuestionBankStatisticsMapper.selectByPrimaryKey(bankDTO.getBankId());
            if (null != bankStatistics) {
                magicWorkListDTO.setReleases(bankStatistics.getReleases());//发布次数
            } else {
                magicWorkListDTO.setReleases(0);
            }
            magicWorkListDTO.setMagicQuestionDTO(magicQuestionDTOs);//题目列表
            magicWorkListDTOs.add(magicWorkListDTO);
        }
        return magicWorkListDTOs;
    }

    /**
     * 查看题目详情
     *
     * @param queId
     * @return
     */
    @Override
    public MagicQuestionDTO viewDetailQue(String queId) {
        logger.info("MagicWorkServiceImpl:获取提分宝作业题目详情");
        return magicQuestionService.selectDetailQue(queId);
    }

    /**
     * @param magicWorkDTO 提分宝作业
     * @param classList    发布班级列表
     * @throws net.xuele.common.exceptions.CloudteachException 修改提分宝作业 一期不上线，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    public void modifyMagicWork(MagicWorkDTO magicWorkDTO, List<String> classList) throws CloudteachException {
        /*//如果提分宝作业最晚提交时间早于当前时间则不允许修改作业
        if(magicWorkDTO.getEndTime().getTime() <= new Date().getTime()){
            throw new CloudteachException(CloudTeachErrorEnum.WORK_EDIT_FAILED.getMsg(),
                    CloudTeachErrorEnum.WORK_EDIT_FAILED.getCode());
        }
        //作业统计表版本控制
        CtWorkStatistics workStatistics = new CtWorkStatistics() ;
        int version = 0;
        int newVersion = -1;
        //作业统计表版本号变更时循环执行以下步骤
        while(version != newVersion){
            workStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(magicWorkDTO.getWorkId(),magicWorkDTO.getSchoolId());
            version = workStatistics.getVersion();
            //提分宝作业表update
            CtMagicWork magicWork = new CtMagicWork();
            BeanUtils.copyProperties(magicWorkDTO,magicWork);
            ctMagicWorkMapper.updateByPrimaryKey(magicWork);
            //提分宝作业班级更新
            //1.根据workId得到修改前的班级列表
            String workId = magicWork.getWorkId();//作业id
            List<MagicWorkClassDTO> magicWorkClassDTOs = this.queryMagicWorkClassListByWorkId(workId,magicWork.getSchoolId());
            List<String> oldclasslist = new ArrayList<>();
            for (MagicWorkClassDTO dto:magicWorkClassDTOs){
                oldclasslist.add(dto.getClassId());
            }
            //2.得到新增的/减少的班级列表
            List<String> newclassid = new ArrayList<>(classList);
            List<String> reducedclassid = new ArrayList<>(oldclasslist);
            newclassid.removeAll(oldclasslist);//去掉已经包含的classId，剩下的就是需要初始化的
            reducedclassid.removeAll(classList);// 去掉重复的classId，剩下的就是需要逻辑删除的
            //3.逻辑删除取消的班级及其关联信息
            if(null != reducedclassid){
                for (String reducedclass:reducedclassid){
                    //提分宝作业班级表逻辑删除记录
                    ctMagicWorkClassMapper.deleteByClassId(workId,reducedclass,magicWork.getSchoolId());
                    //提分宝作业学生回答表逻辑删除
                    ctMagicWorkAnswerMapper.deleteByClassId(workId,reducedclass,magicWork.getSchoolId());
                    //得到提分宝作业学生回答id列表
                    List<String> answerIds = ctMagicWorkAnswerMapper.selectanswerIdListByClassId(workId,reducedclass,magicWork.getSchoolId());
                    for (String id:answerIds){
                        ctMagicWorkAnswerStatisticsMapper.delete(id,magicWork.getSchoolId());//逻辑删除提分宝作业回答表对应的统计表记录
                        ctMagicWorkAnswerFilesMapper.delete(id,magicWork.getSchoolId());//逻辑删除提分宝作业对应的回答附件表记录
                        ctMagicWorkAnswerPraiseMapper.delete(id,magicWork.getSchoolId());//逻辑删除提分宝作业对应的回答点赞记录
                        ctMagicWorkAnswerCommentMapper.delete(id,magicWork.getSchoolId());//逻辑删除提分宝作业回答评论记录
                    }
                    //TODO 删除学生作业汇总表

                }
            }
            //4.增加发布班级
            if (null != newclassid){
                CtMagicWorkClass workClass;
                for (String newclass:newclassid){
                    //提分宝作业班级表inset记录
                    workClass = new CtMagicWorkClass();
                    workClass.setWorkClassId(UUID.randomUUID().toString().replace("-", ""));
                    workClass.setClassId(newclass);
                    workClass.setStatus(1);
                    workClass.setWorkId(magicWork.getWorkId());
                    ctMagicWorkClassMapper.insert(workClass);
                }
                //提分宝作业回答表初始化
//                ctMagicWorkAnswerMapper.initialize(magicWork);
                //提分宝作业学生回答统计表初始化
//                ctMagicWorkAnswerStatisticsMapper.initialize(magicWork.getWorkId(),magicWork.getSchoolId());
                //TODO 更改学生作业汇总表

            }
            workStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(magicWorkDTO.getWorkId(),magicWork.getSchoolId());
            newVersion = workStatistics.getVersion();
        }
        //更新作业统计信息
        workStatistics.setVersion(newVersion+1);//版本号
        ctWorkStatisticsMapper.updateByPrimaryKey(workStatistics);
        //TODO 更改作业汇总表*/


    }

    /**
     * @param workId 作业ID
     *               根据作业ID逻辑删除提分宝作业相关信息
     */
    @Override
    public void delMagicWork(String workId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:删除提分宝作业,workId:" + workId + ",schoolId:" + schoolId);
        CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (null == magicWork) {
            //查找的对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        } else {
            ctMagicWorkMapper.delete(workId, schoolId);//逻辑删除提分宝作业表记录
            ctMagicWorkClassMapper.deleteByWorkId(workId, schoolId);//逻辑删除提分宝作业对应的班级
            List<String> answerIds = ctMagicWorkAnswerMapper.selectByWorkId(workId, schoolId);//根据提分宝作业id查询的回答表记录
            for (String answerId : answerIds) {
                ctMagicWorkAnswerStatisticsMapper.delete(answerId, schoolId);//逻辑删除提分宝作业回答表对应的统计表记录
                ctMagicWorkAnswerFilesMapper.delete(answerId, schoolId);//逻辑删除提分宝作业对应的回答附件表记录
                ctMagicWorkAnswerPraiseMapper.delete(answerId, schoolId);//逻辑删除提分宝作业对应的回答点赞记录
                ctMagicWorkAnswerCommentMapper.delete(answerId, schoolId);//逻辑删除提分宝作业回答评论记录
            }
            ctMagicWorkAnswerMapper.deleteByWorkId(workId, schoolId);//逻辑删除提分宝作业对应的回答表记录
            //教师录音附件删除
            ctWorkTapeFilesMapper.removeTapeFile(workId, schoolId);
            //提分宝学生挑战记录表逻辑删除
            ctMagicWorkChallengeMapper.delMagicWorkChallenge(workId, schoolId);
            //提分宝学生挑战题目表逻辑删除
            ctMagicWorkChallengeQueMapper.delMagicWorkChallengeQue(workId, schoolId);
            //作业汇总表逻辑删除
            ctWorkGatherMapper.delWorkGather(workId, schoolId);
            //作业班级汇总表逻辑删除
            ctWorkClassGatherMapper.delWorkClassGather(workId, schoolId);
            //作业学生汇总表逻辑删除
            ctWorkStudentGatherMapper.delWorkStudentGather(workId, schoolId);

            //发布次数-1
            this.updateReleases(magicWork.getBankId(), -1);
        }

    }

    /**
     * 删除指定学生提交作业
     *
     * @param userId   当前登录教师
     * @param answerId 学生回答id
     * @param schoolId 学校id
     * @throws CloudteachException
     */
    public void delStuAnswer(String userId, String answerId, String schoolId) throws CloudteachException {
        logger.info("查询作业回答表记录,userId:" + userId + ",answerId:" + answerId + ",schoolId:" + schoolId);
        CtMagicWorkAnswer magicWorkAnswer = ctMagicWorkAnswerMapper.selectMagicWorkAnswerForUpdate(answerId, schoolId);
        if (magicWorkAnswer == null) {
            //查找的对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (magicWorkAnswer.getStatus().intValue() != 1) {
            throw new CloudteachException(CloudTeachErrorEnum.DATAERROR.getMsg() + "：作业已经被删除", CloudTeachErrorEnum.DATAERROR.getCode());
        }
        //提分宝作业id
        String workId = magicWorkAnswer.getWorkId();
        //学生id
        String studentId = magicWorkAnswer.getUserId();
        //查询提分宝作业信息
        CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        //判断该作业是否当前用户发布
        if (magicWork == null) {
            //查找的对象不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        } else if (!userId.equals(magicWork.getUserId())) {
            //该作业不是当前教师发布，不能删除
            throw new CloudteachException(CloudTeachErrorEnum.WORKIDNOTDELETE.getMsg(), CloudTeachErrorEnum.WORKIDNOTDELETE.getCode());
        }
        logger.info("1.逻辑删除提分宝作业回答表对应的统计表记录");
        ctMagicWorkAnswerStatisticsMapper.delete(answerId, schoolId);

        logger.info("2.逻辑删除提分宝作业对应的回答附件表记录");
        ctMagicWorkAnswerFilesMapper.delete(answerId, schoolId);//逻辑删除提分宝作业对应的回答附件表记录

        logger.info("3.逻辑删除提分宝作业对应的回答点赞记录");
        ctMagicWorkAnswerPraiseMapper.delete(answerId, schoolId);//逻辑删除提分宝作业对应的回答点赞记录

        logger.info("4.逻辑删除提分宝作业回答评论记录");
        ctMagicWorkAnswerCommentMapper.delete(answerId, schoolId);//逻辑删除提分宝作业回答评论记录

        logger.info("5.逻辑删除提分宝作业对应的回答表记录");
        ctMagicWorkAnswerMapper.delByPrimaryKey(magicWorkAnswer);

        //logger.info("6.提分宝学生挑战记录表逻辑删除");
        //ctMagicWorkChallengeMapper.delStuMagicWorkChallenge(workId,studentId,schoolId);

        //logger.info("7.提分宝学生挑战题目表逻辑删除");
        //ctMagicWorkChallengeQueMapper.delStuMagicWorkChallengeQue(workId,studentId,schoolId);

        logger.info("8.作业学生汇总表update提交状态、批改状态");
        ctWorkStudentGatherMapper.updateStuWorkStudentGather(workId, studentId, schoolId);

        logger.info("9.作业统计表uodate已提交学生数、已批改学生数");
        CtWorkStatistics ctWorkStatistics = new CtWorkStatistics();

        int tryTimes = 1;
        int i = 0;
        while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workId, schoolId);
            if (ctWorkStatistics == null) {
                //对象不存在
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            ctWorkStatistics.setWorkSubStudentNum(ctWorkStatistics.getWorkSubStudentNum() - 1);//已提交学生数-1
            if (magicWorkAnswer.getCorrectStatus() == 1) {//已批改
                ctWorkStatistics.setWorkCorrectStudentNum(ctWorkStatistics.getWorkCorrectStudentNum() - 1);//已批改学生数-1
            }
            i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            tryTimes++;
        }
        if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            //超过乐观锁最大尝试次数，更新失败
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }

        logger.info("10.提分宝作业表判断update作业完成状态、作业批改状态");
        int workSubStudentNum = ctWorkStatistics.getWorkSubStudentNum();//已提交学生数
        int workCorrectStudentNum = ctWorkStatistics.getWorkCorrectStudentNum();//已批改学生数
        //0未完成 ，1部分完成，2全部完成
        if (workSubStudentNum == 0 && workCorrectStudentNum == 0) {//全部未提交未批改
            magicWork.setFinishStatus(0);
            magicWork.setCorrectStatus(0);
        } else if (workSubStudentNum != 0 && workCorrectStudentNum != 0) {//部分未提交未批改
            magicWork.setFinishStatus(1);
            magicWork.setCorrectStatus(1);

        } else {//部分提交，全部未批改
            magicWork.setFinishStatus(1);
            magicWork.setCorrectStatus(0);
        }
        ctMagicWorkMapper.updateByPrimaryKey(magicWork);

        logger.info("11.提分宝作业对应的回答表初始化");
        magicWorkAnswer.setAnswerId(UUID.randomUUID().toString().replace("-", ""));
        magicWorkAnswer.setContext("");
        magicWorkAnswer.setScore(0);
        magicWorkAnswer.setChallengeId("");
        magicWorkAnswer.setSubStatus(0);
        magicWorkAnswer.setCorrectStatus(0);
        magicWorkAnswer.setSubTime(null);
        magicWorkAnswer.setCorrectTime(null);
        magicWorkAnswer.setUpdateTime(new Date());
        magicWorkAnswer.setStatus(1);
        int result = ctMagicWorkAnswerMapper.insert(magicWorkAnswer);
        if (result < 1) {
            throw new CloudteachException(CloudTeachErrorEnum.FAILDELSTUANS.getMsg() + ":回答表初始化失败！", CloudTeachErrorEnum.FAILDELSTUANS.getCode());
        }
        logger.info("12.提分宝作业回答表对应的统计表初始化");
        CtMagicWorkAnswerStatistics magicWorkAnswerStatistics = new CtMagicWorkAnswerStatistics();
        magicWorkAnswerStatistics.setAnswerId(magicWorkAnswer.getAnswerId());
        magicWorkAnswerStatistics.setSchoolId(schoolId);
        magicWorkAnswerStatistics.setCommentTimes(0);
        magicWorkAnswerStatistics.setPraiseTimes(0);
        magicWorkAnswerStatistics.setStatus(1);
        result = ctMagicWorkAnswerStatisticsMapper.insert(magicWorkAnswerStatistics);
        if (result < 1) {
            throw new CloudteachException(CloudTeachErrorEnum.FAILDELSTUANS.getMsg() + ":回答统计表初始化失败！", CloudTeachErrorEnum.FAILDELSTUANS.getCode());
        }
    }


    /**
     * @param workId 作业ID
     *               return MagicWorkDTO
     *               根据作业ID获取提分宝作业信息
     */
    @Override
    public MagicWorkDTO queryMagicWork(String workId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取提分宝作业信息,workId:" + workId + ",schoolId:" + schoolId);
        CtMagicWork magicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        MagicWorkDTO magicWorkDTO = new MagicWorkDTO();
        BeanUtils.copyProperties(magicWork, magicWorkDTO);
        return magicWorkDTO;
    }

    /**
     * @param workId 作业ID
     *               return List<CourseReappearClassDTO>
     *               根据作业ID获取提分宝作业对应班级列表信息
     */
    @Override
    public List<MagicWorkClassDTO> queryMagicWorkClassListByWorkId(String workId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取提分宝作业对应班级列表信息,workId:" + workId + ",schoolId:" + schoolId);
        //查询作业对应的班级列表
        List<CtMagicWorkClass> magicWorkClassList = ctMagicWorkClassMapper.selectByWorkId(workId, schoolId);
        //domain转DTO
        List<MagicWorkClassDTO> magicWorkClassDTOs = new ArrayList<>();
        MagicWorkClassDTO workClassDTO;
        for (CtMagicWorkClass workClass : magicWorkClassList) {
            workClassDTO = new MagicWorkClassDTO();
            BeanUtils.copyProperties(workClass, workClassDTO);
            magicWorkClassDTOs.add(workClassDTO);
        }

        return magicWorkClassDTOs;
    }

    /**
     * @param workId 作业ID
     *               return List<MagicWorkAnswerDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID获取提分宝作业对应学生回答列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public List<MagicWorkAnswerDTO> queryMagicWorkAnswerListByWorkId(String workId, String schoolId) throws CloudteachException {
        return null;
    }

    /**
     * @param answerId 提分宝作业回答ID
     *                 return MagicWorkAnswerDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据提分宝作业回答ID获取提分宝作业回答信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    @Override
    public MagicWorkAnswerDTO queryMagicWorkAnswer(String answerId, String schoolId) throws CloudteachException {
        return null;
    }

    /**
     * @param workId       作业Id
     * @param context      评语
     * @param praiseStatus 鼓励状态
     * @param userId       教师用户号
     * @param schoolId     学校ID
     *                     快速批改提分宝作业
     */
    @Override
    public void quicklyCorrect(String workId, String context, Integer praiseStatus, String userId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:快速批改提分宝作业,workId:" + workId + ",context:" + context + ",praiseStatus:" + praiseStatus + ",userId:" + userId + ",schoolId:" + schoolId);
        //获取作业信息
        CtMagicWork ctMagicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (ctMagicWork == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        //查询提分宝作业项学生回答表中所有已提交未批改的学生回答信息
        List<CtMagicWorkAnswer> answerlist = ctMagicWorkAnswerMapper.selectSubedUnCorrectList(workId, schoolId);

        for (CtMagicWorkAnswer answerinfo : answerlist) {
            //修改每个学生回答表信息（批改状态）

            //修改学生作业相关信息批改状态
            correct(answerinfo.getAnswerId(), schoolId);

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
     *                 根据workId获取提分宝作业班级信息
     */
    @Override
    public List<WorkClassViewDTO> queryMagicWorkClassList(String workId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取提分宝作业班级信息,workId:" + workId + ",schoolId:" + schoolId);
        List<WorkClassView> workClassList = ctMagicWorkClassMapper.queryMagicWorkClassList(workId, schoolId);
        if (workClassList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkClassViewListToDtoList(workClassList);
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     *                 根据workId获取提分宝作业下未提交作业的学生信息
     */
    @Override
    public List<WorkUnSubStudentViewDTO> selectUnSubStudentList(String workId, String classId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取提分宝作业下未提交作业的学生信息,workId:" + workId + ",classId:" + classId + ",schoolId:" + schoolId);
        List<WorkUnSubStudentView> exerciseWorkUnSubStudentList = ctMagicWorkAnswerMapper.selectUnSubStudentList(workId, classId, schoolId);
        if (exerciseWorkUnSubStudentList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkUnSubStudentListToDtoList(exerciseWorkUnSubStudentList);
    }

    /**
     * @param workId      作业ID
     * @param classId     班级ID
     * @param teachUserId 作业布置教师ID
     * @param schoolId    学校ID
     *                    获取提分宝作业中某个班级的学生提交信息
     */
    @Override
    public List<WorkAnswerViewDTO> querySubedWorkAnswerList(String workId, String classId, String teachUserId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取提分宝作业中某个班级的学生提交信息,workId:" + workId + ",classId:" + classId + ",teachUserId:" + teachUserId + ",schoolId:" + schoolId);
        List<WorkAnswerView> guidanceWorkAnswerList = ctMagicWorkAnswerMapper.querySubedWorkAnswerList(workId, classId, teachUserId, schoolId);
        if (guidanceWorkAnswerList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkAswViewListToDtoList(guidanceWorkAnswerList);
    }

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     *                 根据workId获取提分宝作业信息
     */
    @Override
    public MagicWorkDetailViewDTO queryMagicWorkDetailByWorkId(String workId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:获取提分宝作业信息,workId:" + workId + ",schoolId:" + schoolId);
        MagicWorkDetailView magicWorkDetailView = ctMagicWorkMapper.queryMagicWorkDetail(workId, schoolId);
        if (magicWorkDetailView == null) {//作业不存在
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        MagicWorkDetailViewDTO magicWorkDetailViewDTO = new MagicWorkDetailViewDTO();
        BeanUtils.copyProperties(magicWorkDetailView, magicWorkDetailViewDTO);
        return magicWorkDetailViewDTO;
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param userIcon 教师头像
     * @param userName 教师名字
     * @param workType 作业类型（1预习 2提分宝 3同步课堂 4电子作业 7口语作业）
     * @return 0提醒太频繁  1提醒成功
     * 根据提分宝作业ID发通知提醒所有未提交作业的学生
     */
    @Override
    public int warnSub(String workId, String classId, String schoolId, String userId, String userIcon, String userName, int workType) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId:" + classId + ",userId:" + userId + ",schoolId:" + schoolId + ",userIcon:" + userIcon + ",userName:" + userName + ",workType:" + workType);
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
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 点赞answerId对应的作业回答
     */
    @Override
    public void praise(String answerId, String userId, String schoolId, int userType) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:点赞学生作业,answerId:" + answerId + ",userId:" + userId + ",userType:" + userType + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //查询点赞记录
        CtMagicWorkAnswerPraise praiseinfo = ctMagicWorkAnswerPraiseMapper.selectByPrimaryKey(answerId, userId, schoolId);
        if (praiseinfo == null) {
            //无记录则新增点赞记录
            praiseinfo = new CtMagicWorkAnswerPraise();
            praiseinfo.setPraiseId(UUID.randomUUID().toString().replace("-", ""));
            praiseinfo.setAnswerId(answerId);
            praiseinfo.setUserId(userId);
            praiseinfo.setUserType(userType);
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(1);
            praiseinfo.setStatus(1);
            praiseinfo.setSchoolId(schoolId);
            ctMagicWorkAnswerPraiseMapper.insert(praiseinfo);

            CtMagicWorkAnswerStatistics answerStatistics = ctMagicWorkAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
            answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() + 1);
            ctMagicWorkAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
        } else if (praiseinfo.getPraiseStatus().intValue() == 0) {
            //有记录则更新
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(1);
            ctMagicWorkAnswerPraiseMapper.updateByPrimaryKey(praiseinfo);

            CtMagicWorkAnswerStatistics answerStatistics = ctMagicWorkAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
            answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() + 1);
            ctMagicWorkAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
        }

    }

    /**
     * @param answerId 学生回答ID
     * @param userId   教师用户ID
     * @param schoolId 学校ID
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 取消点赞answerId对应的作业回答
     */
    @Override
    public void unpraise(String answerId, String userId, String schoolId, int userType) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:取消点赞学生作业,answerId:" + answerId + ",userId:" + userId + ",userType:" + userType + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //查询点赞记录
        CtMagicWorkAnswerPraise praiseinfo = ctMagicWorkAnswerPraiseMapper.selectByPrimaryKey(answerId, userId, schoolId);
        if (praiseinfo == null) {
            //无记录
            praiseinfo = new CtMagicWorkAnswerPraise();
            praiseinfo.setPraiseId(UUID.randomUUID().toString().replace("-", ""));
            praiseinfo.setAnswerId(answerId);
            praiseinfo.setUserId(userId);
            praiseinfo.setUserType(userType);
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(0);
            praiseinfo.setStatus(1);
            praiseinfo.setSchoolId(schoolId);
            ctMagicWorkAnswerPraiseMapper.insert(praiseinfo);
        } else if (praiseinfo.getPraiseStatus().intValue() == 1) {
            //有记录则更新
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(0);
            ctMagicWorkAnswerPraiseMapper.updateByPrimaryKey(praiseinfo);
        }
        CtMagicWorkAnswerStatistics answerStatistics = ctMagicWorkAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
        answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() - 1);
        if (answerStatistics.getPraiseTimes().intValue() < 0) {
            answerStatistics.setPraiseTimes(0);
        }
        ctMagicWorkAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
    }

    /**
     * @param answerId 学生回答ID
     * @param userId   用户ID
     * @param schoolId 学校ID
     * @param context  评论内容
     * @param userType 用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 评论answerId对应的作业回答
     */
    @Override
    public String comment(String answerId, String userId, String schoolId, String context, int userType) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:点评学生作业,answerId:" + answerId + ",userId:" + userId + ",userType:" + userType + ",context:" + context + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId) || StringUtils.isEmpty(context)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        CtMagicWorkAnswerComment commentinfo = new CtMagicWorkAnswerComment();
        commentinfo.setCommentId(UUID.randomUUID().toString().replace("-", ""));
        commentinfo.setAnswerId(answerId);
        commentinfo.setUserId(userId);
        commentinfo.setUserType(userType);
        commentinfo.setContext(context);
        commentinfo.setCommentTime(new Date());
        commentinfo.setSchoolId(schoolId);
        commentinfo.setStatus(1);
        ctMagicWorkAnswerCommentMapper.insert(commentinfo);

        CtMagicWorkAnswerStatistics answerStatistics = ctMagicWorkAnswerStatisticsMapper.selectByPrimaryKey(answerId, schoolId);
        answerStatistics.setCommentTimes(answerStatistics.getCommentTimes().intValue() + 1);
        ctMagicWorkAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);

        if (userType == 1) {
            //评论人是教师则需要修改批改作业状态
            correct(answerId, schoolId);
        }

        return commentinfo.getCommentId();
    }

    /**
     * @param answerId 学生回答ID
     * @param schoolId 学校ID
     *                 批改answerId对应的作业回答
     */
    @Override
    public void correct(String answerId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:批改学生作业,answerId:" + answerId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(answerId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.selectByPrimaryKey(answerId, schoolId);

        if (ctMagicWorkAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        if (ctMagicWorkAnswer.getCorrectStatus().intValue() == 0) {
            ctMagicWorkAnswer.setCorrectStatus(1);
            ctMagicWorkAnswer.setCorrectTime(new Date());
            ctMagicWorkAnswerMapper.updateByPrimaryKey(ctMagicWorkAnswer);
            //修改作业学生汇总表，将已提交未批改学生作业修改为已批改
            ctWorkStudentGatherMapper.updateByTeacherCorrect(ctMagicWorkAnswer.getWorkId(), ctMagicWorkAnswer.getUserId(), schoolId);
        }
        //修改作业汇总统计信息
        updateWorkGatherAfterCorrect(ctMagicWorkAnswer.getWorkId(), schoolId);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 学生作业批改后修改作业批改状态、作业统计信息已批改学生数
     */
    @Override
    public void updateWorkGatherAfterCorrect(String workId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:学生作业批改后修改作业批改状态、作业统计信息已批改学生数,workId:" + workId + ",schoolId:" + schoolId);
        //获取未批改学生信息
        List<CtMagicWorkAnswer> unCorrectStudentList = ctMagicWorkAnswerMapper.selectUnCorrectStudentList(workId, schoolId);

        //判断最新作业批改状态（0未批改 1部分批改 2全部批改）
        int newCorrectStatus = 0;
        if (unCorrectStudentList == null || unCorrectStudentList.size() == 0) {
            newCorrectStatus = 2;
        } else {
            newCorrectStatus = 1;
        }
        CtMagicWork ctMagicWork = ctMagicWorkMapper.selectByPrimaryKey(workId, schoolId);
        //修改作业表信息（作业批改状态）
        if (ctMagicWork.getCorrectStatus().intValue() < newCorrectStatus) {
            ctMagicWork.setCorrectStatus(newCorrectStatus);
            ctMagicWorkMapper.updateByPrimaryKey(ctMagicWork);
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
        logger.info("MagicWorkServiceImpl:删除评论,commentId:" + commentId + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //验证删除的是否是该教师布置作业对应的评论
        CtMagicWorkAnswerComment commentObj = ctMagicWorkAnswerCommentMapper.selectByPrimaryKey(commentId, schoolId);
        if (commentObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.selectByPrimaryKey(commentObj.getAnswerId(), schoolId);
        if (ctMagicWorkAnswer == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        CtMagicWork workObj = ctMagicWorkMapper.selectByPrimaryKey(ctMagicWorkAnswer.getWorkId(), schoolId);
        if (workObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        if (!workObj.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        commentObj.setStatus(0);
        ctMagicWorkAnswerCommentMapper.updateByPrimaryKey(commentObj);

        CtMagicWorkAnswerStatistics answerStatistics = ctMagicWorkAnswerStatisticsMapper.selectByPrimaryKey(commentObj.getAnswerId(), schoolId);
        answerStatistics.setCommentTimes(answerStatistics.getCommentTimes().intValue() - 1);
        ctMagicWorkAnswerStatisticsMapper.updateByPrimaryKey(answerStatistics);
    }

    /**
     * @param commentId 评论ID
     * @param userId    学生用户ID
     * @param schoolId  学校ID
     *                  学生删除自己的评论
     */
    @Override
    public void delStuCommont(String commentId, String userId, String schoolId) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:学生删除自己的评论,commentId:" + commentId + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //验证删除的是否是该学生的评论
        CtMagicWorkAnswerComment commentObj = ctMagicWorkAnswerCommentMapper.selectByPrimaryKey(commentId, schoolId);
        if (commentObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (!commentObj.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        commentObj.setStatus(0);
        ctMagicWorkAnswerCommentMapper.updateByPrimaryKey(commentObj);
    }

    /**
     * @param workId    作业ID
     * @param studentId 学生用户ID
     * @param schoolId  学校ID
     *                  获取提分宝作业对应某个学生的回答信息
     */
    @Override
    public MagicWorkAnswerDTO getStuMagicWorkAnswer(String workId, String studentId, String schoolId) throws CloudteachException {
        logger.info("获取同步课堂作业对应某个学生的回答信息,workId:" + workId + ",studentId:" + studentId + ",schoolId:" + schoolId);
        CtMagicWorkAnswer ctMagicWorkAnswer = ctMagicWorkAnswerMapper.getCtMagicWorkAnswerByWorkStudentId(workId, studentId, schoolId);
        if (ctMagicWorkAnswer == null) {
            return null;
        }
        MagicWorkAnswerDTO magicWorkAnswerDTO = new MagicWorkAnswerDTO();
        BeanUtils.copyProperties(ctMagicWorkAnswer, magicWorkAnswerDTO);
        return magicWorkAnswerDTO;
    }

    /**
     * 获取提分宝作业对应某个班级学生的作业统计信息
     *
     * @param schoolId 学校ID
     * @param classId  班级ID
     * @param workId   作业Id
     * @return
     */
    @Override
    public SRTeacherWorkDTO getMagicWorkSR(String schoolId, String classId, String workId) {
        logger.info("获取提分宝作业对应某个班级学生的作业统计信息,schoolId:" + schoolId + ",workId:" + workId + ",classId:" + classId);

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
        //得分为E学生列表
        List<SRTeacherWorkStuDTO> scoreEStuList = new ArrayList<>();
        //得分为F学生列表
        List<SRTeacherWorkStuDTO> scoreFStuList = new ArrayList<>();
        //未打分学生列表
        List<SRTeacherWorkStuDTO> unScoreStuList = new ArrayList<>();
        //已打分学生按成绩排名列表
        List<SRTeacherWorkStuDTO> orderStuList = new ArrayList<>();

        //获取该作业对应班级的学生总数
        int totalStuNum = ctMagicWorkAnswerMapper.queryClassStuCount(workId, classId, schoolId);
        if (totalStuNum <= 0) {
            throw new CloudteachException(CloudTeachErrorEnum.CLASSNOSTUDENT.getMsg() + ":无法获取该班级学生作业的统计信息", CloudTeachErrorEnum.CLASSNOSTUDENT.getCode());
        }
        sRTeacherWorkDTO.setClassStuNum(totalStuNum);
        //获取教师作业某个班级中已提交作业的学生信息
        List<WorkAnswerView> subedAnswerList = ctMagicWorkAnswerMapper.queryClassSubedStuList(workId, classId, schoolId);
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
            sRTeacherWorkStuDTO.setChallengeTimes(workAnswerView.getChallengeTimes());
            sRTeacherWorkStuDTO.setScore(workAnswerView.getScore());
            if (workAnswerView.getScore() == 10) {
                scoreAStuList.add(sRTeacherWorkStuDTO);
            } else if (workAnswerView.getScore() == 9) {
                scoreBStuList.add(sRTeacherWorkStuDTO);
            } else if (workAnswerView.getScore() == 8) {
                scoreCStuList.add(sRTeacherWorkStuDTO);
            } else if (workAnswerView.getScore() == 7) {
                scoreDStuList.add(sRTeacherWorkStuDTO);
            } else if (workAnswerView.getScore() == 6) {
                scoreEStuList.add(sRTeacherWorkStuDTO);
            } else {
                scoreFStuList.add(sRTeacherWorkStuDTO);
            }
            orderStuList.add(sRTeacherWorkStuDTO);
        }
        sRTeacherWorkDTO.setFinshPect((int) NumberFormat.getFloatRound(100.0 * sRTeacherWorkDTO.getSubStuNum() / sRTeacherWorkDTO.getClassStuNum(), 0));
        sRTeacherWorkDTO.setScoreAPect((int) NumberFormat.getFloatRound(100.0 * scoreAStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreBPect((int) NumberFormat.getFloatRound(100.0 * scoreBStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreCPect((int) NumberFormat.getFloatRound(100.0 * scoreCStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreDPect((int) NumberFormat.getFloatRound(100.0 * scoreDStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreEPect((int) NumberFormat.getFloatRound(100.0 * scoreEStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreFPect((int) NumberFormat.getFloatRound(100.0 * scoreFStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreAStuList(scoreAStuList);
        sRTeacherWorkDTO.setScoreBStuList(scoreBStuList);
        sRTeacherWorkDTO.setScoreCStuList(scoreCStuList);
        sRTeacherWorkDTO.setScoreDStuList(scoreDStuList);
        sRTeacherWorkDTO.setScoreEStuList(scoreEStuList);
        sRTeacherWorkDTO.setScoreFStuList(scoreFStuList);
        sRTeacherWorkDTO.setOrderStuList(orderStuList);

        return sRTeacherWorkDTO;
    }

    /****************************提分宝习题讲解*********************************/

    /**
     * 查询提分宝袭习题讲解
     *
     * @param qieId 题目id（原题id）
     * @param type  1--pc端，2--手機端，3--其他
     * @return
     * @throws CloudteachException
     */
    public MagicQuestionVideoDTO getMagicQueVideoByQueId(String qieId, Integer type) throws CloudteachException {
        logger.info("MagicWorkServiceImpl:查询提分宝袭习题讲解,qieId:" + qieId + ",type:" + type);
        //查询视频讲解
        List<CtMagicQuestionVideo> magicQuestionVideoList = ctMagicQuestionVideoMapper.selectByQueId(qieId);
        if (magicQuestionVideoList != null) {
            //文件后缀
            String videoSuffix;
            MagicQuestionVideoDTO magicQuestionVideoDTO;
            for (CtMagicQuestionVideo video : magicQuestionVideoList) {
                videoSuffix = video.getVideoSuffix();
                if (type == 1 && MagicQueVideoTypeUtil.getInstance().getIsPcType(videoSuffix) == 1) {//pc端
                    magicQuestionVideoDTO = new MagicQuestionVideoDTO();
                    BeanUtils.copyProperties(video, magicQuestionVideoDTO);
                    return magicQuestionVideoDTO;
                } else if (type == 2 && MagicQueVideoTypeUtil.getInstance().getIsMobileType(videoSuffix) == 1) {//手机端
                    magicQuestionVideoDTO = new MagicQuestionVideoDTO();
                    BeanUtils.copyProperties(video, magicQuestionVideoDTO);
                    return magicQuestionVideoDTO;
                }
            }
        }
        return null;
    }
    /******************************私有方法*****************************************/
    /**
     * 更新题库发布次数
     *
     * @param bankId
     * @param num
     * @return
     */
    private int updateReleases(String bankId, int num) {

        CtMagicQuestionBankStatistics statistics = magicQuestionBankStatisticsMapper.selectByPrimaryKey(bankId);
        if (null == statistics) {//没有发布记录
            statistics = new CtMagicQuestionBankStatistics();
            statistics.setBankId(bankId);//题库id
            statistics.setReleases(num);
            return magicQuestionBankStatisticsMapper.insert(statistics);
        } else {
            statistics.setReleases(statistics.getReleases() + num);
            return magicQuestionBankStatisticsMapper.updateReleases(statistics);
        }
    }

}