package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.appCenter.dto.*;
import net.xuele.appCenter.service.ACCourseWaresService;
import net.xuele.appCenter.service.DUnitsService;
import net.xuele.appCenter.service.TranslateFlashService;
import net.xuele.appCenter.service.ViewCourseWaresService;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.SynclassWorkService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.service.common.CloudTeachNotifyContent;
import net.xuele.cloudteach.service.util.*;
import net.xuele.cloudteach.view.*;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.dto.CtBookDTO;
import net.xuele.member.service.SchoolBookService;
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
 * SynclassWorkServiceImpl
 * 教师同步课堂作业服务
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
@Service
public class SynclassWorkServiceImpl implements SynclassWorkService {

    private static Logger logger = LoggerFactory.getLogger(SynclassWorkServiceImpl.class);

    @Autowired
    CtSynclassWorkAnswerCommentMapper ctSynclassWorkAnswerCommentMapper;
    @Autowired
    CtSynclassWorkAnswerPraiseMapper ctSynclassWorkAnswerPraiseMapper;
    @Autowired
    CtSynclassWorkClassMapper ctSynclassWorkClassMapper;
    @Autowired
    CtSynclassWorkGameMapper ctSynclassWorkGameMapper;
    @Autowired
    CtSynclassWorkMapper ctSynclassWorkMapper;
    @Autowired
    CtSynclassWorkPlayMapper ctSynclassWorkPlayMapper;
    @Autowired
    CtSynclassWorkStudentMapper ctSynclassWorkStudentMapper;
    @Autowired
    CtSynclassWorkStudentStatisticsMapper ctSynclassWorkStudentStatisticsMapper;
    @Autowired
    CtWorkStatisticsMapper ctWorkStatisticsMapper;
    @Autowired
    CloudTeachService cloudTeachService;//云教学服务
    @Autowired
    WorkStatisticsService workStatisticsService;//作业汇总统计服务

    @Autowired
    private CtUnitsMapper ctUnitsMapper;

    @Autowired
    private CtWorkClassGatherMapper ctWorkClassGatherMapper;

    @Autowired
    private CtWorkGatherMapper ctWorkGatherMapper;

    @Autowired
    private CtWorkStudentGatherMapper ctWorkStudentGatherMapper;

    @Autowired
    CtWorkTapeFilesMapper ctWorkTapeFilesMapper;

    @Autowired
    ViewCourseWaresService viewCourseWaresService;

    @Autowired
    TranslateFlashService translateFlashService;

    @Autowired
    SchoolBookService schoolBookService;

    @Autowired
    DUnitsService dUnitsService;

    @Autowired
    ACCourseWaresService aCCourseWaresService;

    @Autowired
    WriteAccessLogService writeAccessLogService;

    public List<ViewCourseWaresDTO> queryCourseWare(String unitCode) {
        return viewCourseWaresService.queryCourseWare(unitCode);
    }


    public FlashAddressDTO getSynClassFlashVars(SynClassFlashInDTO synClassFlashInDTO) {
        return translateFlashService.getSynClassFlashVars(synClassFlashInDTO);
    }

    public List<DUnitsDTO> getSyncUnit(String bookId, String schoolId) {
        BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(bookId);
        CtBookDTO ctBookDTO = schoolBookService.getDBook(bookDTO.getGrade(), Integer.parseInt(bookDTO.getSubjectId()), schoolId);
        if (ctBookDTO == null || StringUtils.isEmpty(ctBookDTO.getBookId())) {
            return null;
        }
        List<DUnitsDTO> dUnitsDTOList = dUnitsService.queryByBookId(ctBookDTO.getBookId());
        return dUnitsDTOList;
    }

    /**
     * @param synclassWorkDTO 同步课堂作业
     * @param classDTOList    发布班级列表
     * @param gameDTOList     同步课堂作业附件列表
     * @param classJson       前端发来的班级IdNameJson
     *                        新增同步课堂作业
     */
    @Override
    public String addSynclassWork(SynclassWorkDTO synclassWorkDTO, List<SynclassWorkClassDTO> classDTOList,
                                  List<SynclassWorkGameDTO> gameDTOList, String classJson, WorkTapeFilesDTO workTapeFilesDTO) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:发布同步课堂作业");
        logger.info("synclassWorkDTO:" + synclassWorkDTO);
        logger.info("classDTOList:" + classDTOList);
        logger.info("gameDTOList:" + gameDTOList);
        logger.info("classJson:" + classJson);
        logger.info("workTapeFilesDTO:" + workTapeFilesDTO);
        CtSynclassWork work = TranslateSyncclassWork.getSynclassWorkFromDto(synclassWorkDTO);
        String workId = work.getWorkId();
        String schoolId = work.getSchoolId();
        String userId = work.getUserId();
        String unitId = work.getUnitId();
        Date publishTime = work.getPublishTime();
        Date endTime = work.getEndTime();

        List<CtSynclassWorkClass> classList = TranslateSyncclassWork.getCtSynclassWorkClassList(workId, classDTOList, schoolId);

        List<String> classIdList = new ArrayList<String>();

        for (CtSynclassWorkClass workClass : classList) {
            String classId = workClass.getClassId();
            classIdList.add(classId);
        }
        int initClassNum = classIdList.size();
        String response = "";
        try {
            logger.info("校验班级列表跟班级json中的班级id是否一致");
            if (VerifyParam.verifyClassInfo(classIdList, classJson)) {

                List<StudentNumView> studentNumViewList = ctUnitsMapper.getStudentNumBySchoolId(classIdList, schoolId);

                //判断后的班级Id,去除该班级下学生为空的id;
                classIdList = TranslateCommon.getSchoolIdList(studentNumViewList);
                int nowClassNum = classIdList.size();
                if (initClassNum != nowClassNum) {
                    response = TranslateCommon.getClassInfoById(classIdList, classJson);
                } else {
                    //同步课堂作业班级表
                    ctSynclassWorkClassMapper.batchInsert(classList);

                    //同步课堂作业表
                    ctSynclassWorkMapper.insert(work);

                    // 得到该预习作业布置的所有对象班级信息
                    List<ClassIdNameView> classIdNameViewList = new ArrayList<ClassIdNameView>();


                    //同步课堂作业学生表
                    List<CtSynclassWorkStudent> studentList = ctSynclassWorkStudentMapper.getInitInfo(classIdList, schoolId, workId);
                    if (CollectionUtils.isNotEmpty(studentList)) {
                        ctSynclassWorkStudentMapper.batchInsert(studentList);
                    }

                    List<String> workUserIdList = TranslateSyncclassWork.getWorkUserIdList(studentList);
                    //同步课堂作业学生统计表
                    int count = 0;
                    if (workUserIdList.size() > 0)
                        count = ctSynclassWorkStudentStatisticsMapper.initStudentStatistics(workUserIdList, schoolId);

                    int workType = Constants.WORK_TYPE_SYNCLASS_WORK;
                    int classNum = classList.size();
                    int workItemNum = gameDTOList == null ? 0 : gameDTOList.size();

                    //同步课堂科目名称和课程名称
                    UnitBookNameView unitBookNameView = ctUnitsMapper.querySynclassUnitBookNameByUnitId(unitId);
                    String fileJson = "[]";

                    //汇总表
                    CtWorkGather ctWorkGather = TranslateBlackboard.getCtWorkGather(classJson, count, classNum, workId, workItemNum,
                            workType, schoolId, fileJson, userId, unitBookNameView, work.getContext(), publishTime, endTime);
                    ctWorkGatherMapper.insert(ctWorkGather);

                    //统计表
                    CtWorkStatistics ctWorkStatistics = TranslateBlackboard.getCtWorkStatistics(
                            workId, 0, schoolId);
                    ctWorkStatisticsMapper.insert(ctWorkStatistics);
                    //学生汇总表
                    if (CollectionUtils.isNotEmpty(studentList)) {
                        List<CtWorkStudentGather> studentGatherList = TranslateSyncclassWork.getStudentGatherList(studentList, workId);
                        ctWorkStudentGatherMapper.batchInsert(studentGatherList);
                    }
                    //班级汇总表
                    List<CtWorkClassGather> classGatherList = TranslateSyncclassWork.getWorkClassGather(classIdList, workId, schoolId);
                    if (CollectionUtils.isNotEmpty(classGatherList)) {
                        ctWorkClassGatherMapper.batchInsert(classGatherList);
                    }

                    if (CollectionUtils.isNotEmpty(gameDTOList)) {
                        //同步课堂作业游戏附件表
                        List<CtSynclassWorkGame> gameList = TranslateSyncclassWork.getCtSynclassWorkGameList(workId, gameDTOList, schoolId);
                        if (CollectionUtils.isNotEmpty(gameList)) {
                            ctSynclassWorkGameMapper.batchInsert(gameList);
                        }

                        //同步课堂作业学生练习表
                        if (CollectionUtils.isNotEmpty(gameList) && CollectionUtils.isNotEmpty(studentList)) {
                            List<CtSynclassWorkPlay> playList = TranslateSyncclassWork.getPlayList(gameList, studentList);
                            ctSynclassWorkPlayMapper.batchInsertPlay(playList);
                        }

                    }

                    //作业教师录音附件表
                    if (null != workTapeFilesDTO) {
                        CtWorkTapeFiles workTapeFiles = new CtWorkTapeFiles();
                        BeanUtils.copyProperties(workTapeFilesDTO, workTapeFiles);
                        // 主键
                        workTapeFiles.setFileId(UUID.randomUUID().toString().replace("-", ""));
                        workTapeFiles.setWorkId(workId);
                        workTapeFiles.setWorkType(Constants.WORK_TYPE_SYNCLASS_WORK);
                        workTapeFiles.setUploadTime(new Date());
                        workTapeFiles.setSchoolId(schoolId);
                        workTapeFiles.setStatus(1);
                        ctWorkTapeFilesMapper.insert(workTapeFiles);
                    }
                    try {
                        logger.info("[调用日志服务接口]");
                        writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
                                .accessAction(AccessAction.AfterHomework)
                                .userId(userId)
                                .occurredTime(publishTime.getTime())
                                .build());
                    } catch (Exception e) {
                        logger.error("[写入日志失败:{}]", e.getMessage());
                    }
                }
            } else {
                //参数错误
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        } catch (IOException e) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_ADD_FAILED.getMsg(), CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
        }
        return response;
    }

    /**
     * @param synclassWorkDTO 同步课堂作业
     * @param classDTOList    发布班级列表
     * @param gameDTOList     同步课堂作业附件列表 ，只能删除，不能增加
     *                        修改同步课堂作业（当前版本没有修改作业功能）
     */
    @Override
    public void modifySynclassWork(SynclassWorkDTO synclassWorkDTO, List<SynclassWorkClassDTO> classDTOList,
                                   List<SynclassWorkGameDTO> gameDTOList, String classJson) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:修改同步课堂作业");
        CtSynclassWork work = TranslateSyncclassWork.getExistSynclassWorkFromDto(synclassWorkDTO);
        String workId = work.getWorkId();
        String schoolId = work.getSchoolId();
        String unitId = work.getUnitId();
        String userId = work.getUserId();
        Date publishTime = work.getPublishTime();
        Date endTime = work.getEndTime();

        //同步课堂作业表
        ctSynclassWorkMapper.updateByPrimaryKey(work);

        List<CtSynclassWorkClass> insertClassList = new ArrayList<CtSynclassWorkClass>();
        List<CtSynclassWorkClass> existClassList = new ArrayList<CtSynclassWorkClass>();
        List<CtSynclassWorkClass> delClassList = new ArrayList<CtSynclassWorkClass>();
        List<CtSynclassWorkClass> oldClassList = ctSynclassWorkClassMapper.selectByWorkId(workId, schoolId);
        int subStuNum = 0;

        List<CtSynclassWorkClass> classList = TranslateSyncclassWork.getCtSynclassWorkClassList(workId, classDTOList, schoolId);


        //获得比较后的classList
        TranslateSyncclassWork.getComparedClassList(classList, oldClassList,
                existClassList, delClassList, insertClassList);

        List<String> delClassIdList = TranslateSyncclassWork.getClassList(delClassList);

        List<String> insertClassIdList = TranslateSyncclassWork.getClassList(insertClassList);

        //需要删除的班级和学生，练习表;学生统计表可以不更改
        if (delClassList.size() > 0) {
            ctSynclassWorkClassMapper.batchUpdate(delClassList, schoolId);
            ctSynclassWorkStudentMapper.batchUpdateStatusByClassId(delClassList, schoolId);
            ctSynclassWorkPlayMapper.batchUpdateByClass(delClassList, schoolId);
            //班级汇总表
            ctWorkClassGatherMapper.updateStatusByClassList(0, schoolId, delClassIdList);
            //学生汇总表
            ctWorkStudentGatherMapper.updateStatusByClassList(0, schoolId, delClassIdList);
        }
        //需要添加的班级和学生等
        if (insertClassList.size() > 0) {
            ctSynclassWorkClassMapper.batchInsert(insertClassList);
            //同步课堂作业学生表
            List<CtSynclassWorkStudent> studentList = ctSynclassWorkStudentMapper.getInitInfo(insertClassIdList, workId, schoolId);
            ctSynclassWorkStudentMapper.batchInsert(studentList);

            List<String> workUserIdList = TranslateSyncclassWork.getWorkUserIdList(studentList);
            //同步课堂作业学生统计表
            ctSynclassWorkStudentStatisticsMapper.initStudentStatistics(workUserIdList, schoolId);

            //班级汇总表
            List<CtWorkClassGather> classGatherList = ctWorkClassGatherMapper.getInitInfo(schoolId, insertClassIdList, workId);
            ctWorkClassGatherMapper.batchInsert(classGatherList);
            //学生汇总表
            List<CtWorkStudentGather> studentGatherList = ctWorkStudentGatherMapper.getInitInfo(schoolId, insertClassIdList, workId);
            ctWorkStudentGatherMapper.batchInsert(studentGatherList);
        }

        if (existClassList.size() > 0) {
            subStuNum = ctSynclassWorkStudentMapper.getStudentNumByClassIdList(existClassList, schoolId);
        }

        //数据库里的数据
        List<CtSynclassWorkGame> oldGameList = ctSynclassWorkGameMapper.selectByWorkId(workId, schoolId);


        //oldGameList肯定比gameList来的大，所以就没有insertGameList了。
        if (gameDTOList != null) {
            //同步课堂作业游戏附件表
            List<CtSynclassWorkGame> gameList = TranslateSyncclassWork.getCtSynclassWorkGameList(workId, gameDTOList, schoolId);


            List<CtSynclassWorkGame> existGameList = new ArrayList<CtSynclassWorkGame>();
            List<CtSynclassWorkGame> delGameList = new ArrayList<CtSynclassWorkGame>();

            TranslateSyncclassWork.getComparedGameList(gameList, oldGameList, existGameList, delGameList);

            //需要删除的附件
            if (delGameList.size() > 0) {
                ctSynclassWorkGameMapper.batchUpdate(delGameList, workId, schoolId);
                ctSynclassWorkPlayMapper.batchUpdateByGame(delGameList, workId, schoolId);
            }

            //新增班级的附件全部添加
            if (insertClassList.size() > 0) {
                List<CtSynclassWorkPlay> playList = ctSynclassWorkPlayMapper.getInitInfoByClassAndGame(workId, insertClassList, gameList, schoolId);
                ctSynclassWorkPlayMapper.batchInsertPlay(playList);
            }
        }


        int workType = Constants.WORK_TYPE_SYNCLASS_WORK;
        int classNum = classList.size();
        int workItemNum = gameDTOList.size();

        //科目名称和课程名称
        UnitBookNameView unitBookNameView = ctUnitsMapper.queryUnitBookNameByUnitId(unitId);
        String fileJson = "";

        int studentNum = ctSynclassWorkStudentMapper.getCountByWorkId(workId, schoolId);


        //汇总表
        CtWorkGather ctWorkGather = TranslateBlackboard.getCtWorkGather(classJson, studentNum, classNum, workId, workItemNum,
                workType, schoolId, fileJson, userId, unitBookNameView, work.getContext(), publishTime, endTime);
        ctWorkGatherMapper.updateByPrimaryKey(ctWorkGather);
        //统计表
        int tryTimes = 1;
        int i = 0;
        while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(workId, schoolId);
            if (ctWorkStatistics == null) {
                throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
            }
            ctWorkStatistics.setWorkSubStudentNum(subStuNum);
            i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            tryTimes++;
        }
        if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
            //超过乐观锁最大尝试次数，更新失败
            throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
        }
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 根据作业ID删除同步课堂作业相关信息
     */
    @Override
    public void delSynclassWork(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:删除同步课堂作业,workId:" + workId + ",schoolId:" + schoolId);
        ctSynclassWorkMapper.updateByWorkId(workId, schoolId);
        ctSynclassWorkClassMapper.updateByWorkId(workId, schoolId);
        ctSynclassWorkStudentMapper.updateByWorkId(workId, schoolId, null);
        ctSynclassWorkPlayMapper.updateByWorkId(workId, schoolId);
        ctSynclassWorkGameMapper.updateByWorkId(workId, schoolId);
        ctWorkGatherMapper.updateStatusByWorkId(0, workId, schoolId);
        ctWorkStudentGatherMapper.updateStatusByWorkId(0, workId, schoolId);
        ctWorkClassGatherMapper.updateStatusByWorkId(0, workId, schoolId);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 return SynclassWorkDTO
     *                 根据作业ID获取同步课堂作业信息
     */
    @Override
    public SynclassWorkDTO querySynclassWork(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业信息,workId:" + workId + ",schoolId:" + schoolId);
        CtSynclassWork work = ctSynclassWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (work == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        return TranslateSyncclassWork.entitySynclassWorkToDto(work);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 return List<SynclassWorkGameDTO>
     *                 根据作业ID获取同步课堂作业对应游戏列表信息
     */
    @Override
    public List<SynclassWorkGameDTO> querySynclassWorkGameListByWorkId(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业对应游戏列表信息,workId:" + workId + ",schoolId:" + schoolId);
        List<CtSynclassWorkGame> gameList = ctSynclassWorkGameMapper.selectByWorkId(workId, schoolId);
        if (gameList == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        return TranslateSyncclassWork.entityGameListToListDto(gameList);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 return List<SynclassWorkClassDTO>
     *                 根据作业ID获取同步课堂作业对应班级列表信息
     */
    @Override
    public List<SynclassWorkClassDTO> querySynclassWorkClassListByWorkId(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业对应班级列表信息,workId:" + workId + ",schoolId:" + schoolId);
        List<CtSynclassWorkClass> classList = ctSynclassWorkClassMapper.selectByWorkId(workId, schoolId);
        if (classList == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        return TranslateSyncclassWork.entityClassListToListDto(classList);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 return List<SynclassWorkStudentDTO>
     *                 根据作业ID获取同步课堂作业对应学生列表信息
     */
    @Override
    public List<SynclassWorkStudentDTO> querySynclassWorkStudentListByWorkId(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业对应学生列表信息,workId:" + workId + ",schoolId:" + schoolId);
        List<CtSynclassWorkStudent> studentList = ctSynclassWorkStudentMapper.selectStudentListByWorkId(workId, schoolId);
        if (studentList == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        return TranslateSyncclassWork.entityStudentListToListDto(studentList);
    }

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param schoolId   学校ID
     *                   return SynclassWorkStudentDTO
     *                   根据同步课堂作业学生ID获取同步课堂作业学生信息
     */
    @Override
    public SynclassWorkStudentDTO querySynclassWorkAnswer(String workUserId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:取同步课堂作业学生信息,workUserId:" + workUserId + ",schoolId:" + schoolId);
        CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.selectByPrimaryKey(workUserId, schoolId);
        if (ctSynclassWorkStudent == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        return TranslateSyncclassWork.entitySynclassWorkStudentToDto(ctSynclassWorkStudent);
    }

    /**
     * @param workId       作业Id
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @throws CloudteachException 快速批改同步课堂作业
     */
    @Override
    public void quicklyCorrect(String workId, String context, Integer praiseStatus, String userId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:快速批改同步课堂作业,workId:" + workId + ",context:" + context + ",praiseStatus:" + praiseStatus + ",userId:" + userId + ",schoolId:" + schoolId);
        //获取作业信息
        CtSynclassWork ctSynclassWork = ctSynclassWorkMapper.selectByPrimaryKey(workId, schoolId);
        if (ctSynclassWork == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        //查询同步课堂作业项学生回答表中所有已提交未批改的学生回答信息
        List<CtSynclassWorkStudent> answerlist = ctSynclassWorkStudentMapper.selectSubedUnCorrectList(workId, schoolId);

        for (CtSynclassWorkStudent workstudentinfo : answerlist) {
            //修改每个学生回答表信息（批改状态）
            //修改学生作业相关信息批改状态
            correct(workstudentinfo.getWorkUserId(), schoolId);

            if (context != null && !"".equals(context.trim())) {
                //新增评论记录
                comment(workstudentinfo.getWorkUserId(), userId, schoolId, context, 1);
            }

            if (praiseStatus != null && 1 == praiseStatus.intValue()) {
                //鼓励（点赞）
                praise(workstudentinfo.getWorkUserId(), userId, schoolId, 1);
            }
        }

        //修改作业汇总统计信息
        updateWorkGatherAfterCorrect(workId, schoolId);
    }

    /**
     * @param workId   作业Id
     * @param schoolId 学校ID
     *                 根据workId获取同步课堂作业班级信息
     */
    @Override
    public List<WorkClassViewDTO> querySynclassWorkClassList(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业班级信息,workId:" + workId + ",schoolId:" + schoolId);
        List<WorkClassView> workClassList = ctSynclassWorkClassMapper.querySynclassWorkClassList(workId, schoolId);
        if (workClassList == null) {
            return null;
        }
        return WorkEntityTransform.entityWorkClassViewListToDtoList(workClassList);
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     *                 根据workId获取同步课堂作业下未提交作业的学生信息
     */
    @Override
    public List<WorkUnSubStudentViewDTO> selectUnSubStudentList(String workId, String classId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业下未提交作业的学生信息,workId:" + workId + ",classId:" + classId + ",schoolId:" + schoolId);
        List<WorkUnSubStudentView> exerciseWorkUnSubStudentList = ctSynclassWorkStudentMapper.selectUnSubStudentList(workId, classId, schoolId);
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
     *                    获取同步课堂作业中某个班级的学生提交信息
     */
    @Override
    public List<SynclassWorkAnswerViewDTO> querySubedWorkAnswerList(String workId, String classId, String teachUserId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业中某个班级的学生提交信息,workId:" + workId + ",classId:" + classId + ",teachUserId:" + teachUserId + ",schoolId:" + schoolId);
        List<SynclassWorkAnswerView> synclassWorkAnswerList = ctSynclassWorkStudentMapper.querySubedWorkAnswerList(workId, classId, teachUserId, schoolId);
        if (synclassWorkAnswerList == null) {
            return null;
        }
        for (SynclassWorkAnswerView viewObj : synclassWorkAnswerList) {
            for (SynclassWorkPlayView playViewObj : viewObj.getPlayViewList()) {
                //获取游戏信息
                String gameId = playViewObj.getGameFileId();
                ACCourseWaresDTO aCCourseWaresDTO = aCCourseWaresService.selectCourseWareByUcId(new Integer(gameId));
                if (aCCourseWaresDTO != null && aCCourseWaresDTO.getcTitle() != null) {
                    playViewObj.setGameName(aCCourseWaresDTO.getcTitle());
                } else {
                    playViewObj.setGameName("");
                }
                if (aCCourseWaresDTO != null && aCCourseWaresDTO.getcReal() != null) {
                    playViewObj.setcReal(aCCourseWaresDTO.getcReal());
                } else {
                    playViewObj.setcReal(0);
                }
            }

        }
        return entityScWorkAswViewListToDtoList(synclassWorkAnswerList);
    }

    /**
     * @param workId   作业Id
     * @param classId  班级ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param userIcon 教师头像
     * @param userName 教师名字
     * @param workType 作业类型（1预习 2提分宝 3同步课堂 4电子作业 7口语作业）
     *                 根据同步课堂作业ID发通知提醒所有未提交作业的学生
     */
    @Override
    public int warnSub(String workId, String classId, String schoolId, String userId, String userIcon, String userName, int workType) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:发通知提醒所有未提交作业的学生,workId:" + workId + ",classId:" + classId + ",schoolId:" + schoolId + ",userId:" + userId + ",userIcon:" + userIcon + ",userName:" + userName + ",workType:" + workType);
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
     * @param workUserId 同步课堂作业学生ID
     * @param userId     教师用户ID
     * @param schoolId   学校ID
     * @param userType   用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 点赞answerId对应的作业回答
     */
    @Override
    public void praise(String workUserId, String userId, String schoolId, int userType) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:点赞学生作业,workUserId:" + workUserId + ",userId:" + userId + ",userType:" + userType + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workUserId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //查询点赞记录
        CtSynclassWorkAnswerPraise praiseinfo = ctSynclassWorkAnswerPraiseMapper.selectByPrimaryKey(workUserId, userId, schoolId);
        if (praiseinfo == null) {
            //无记录则新增点赞记录
            praiseinfo = new CtSynclassWorkAnswerPraise();
            praiseinfo.setPraiseId(UUID.randomUUID().toString().replace("-", ""));
            praiseinfo.setWorkUserId(workUserId);
            praiseinfo.setUserId(userId);
            praiseinfo.setUserType(userType);
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(1);
            praiseinfo.setStatus(1);
            praiseinfo.setSchoolId(schoolId);
            ctSynclassWorkAnswerPraiseMapper.insert(praiseinfo);

            CtSynclassWorkStudentStatistics answerStatistics = ctSynclassWorkStudentStatisticsMapper.selectByPrimaryKey(workUserId, schoolId);
            answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() + 1);
            ctSynclassWorkStudentStatisticsMapper.updateByPrimaryKey(answerStatistics);
        } else if (praiseinfo.getPraiseStatus().intValue() == 0) {
            //有记录则更新
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(1);
            ctSynclassWorkAnswerPraiseMapper.updateByPrimaryKey(praiseinfo);

            CtSynclassWorkStudentStatistics answerStatistics = ctSynclassWorkStudentStatisticsMapper.selectByPrimaryKey(workUserId, schoolId);
            answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() + 1);
            ctSynclassWorkStudentStatisticsMapper.updateByPrimaryKey(answerStatistics);

        }
    }

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param userId     教师用户ID
     * @param schoolId   学校ID
     * @param userType   用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 取消点赞answerId对应的作业回答
     */
    @Override
    public void unpraise(String workUserId, String userId, String schoolId, int userType) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:取消点赞学生作业,workUserId:" + workUserId + ",userId:" + userId + ",userType:" + userType + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workUserId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        //查询点赞记录
        CtSynclassWorkAnswerPraise praiseinfo = ctSynclassWorkAnswerPraiseMapper.selectByPrimaryKey(workUserId, userId, schoolId);
        if (praiseinfo == null) {
            //无记录
            praiseinfo = new CtSynclassWorkAnswerPraise();
            praiseinfo.setPraiseId(UUID.randomUUID().toString().replace("-", ""));
            praiseinfo.setWorkUserId(workUserId);
            praiseinfo.setUserId(userId);
            praiseinfo.setUserType(userType);
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(0);
            praiseinfo.setStatus(1);
            praiseinfo.setSchoolId(schoolId);
            ctSynclassWorkAnswerPraiseMapper.insert(praiseinfo);
        } else if (praiseinfo.getPraiseStatus().intValue() == 1) {
            //有记录则更新
            praiseinfo.setPraiseTime(new Date());
            praiseinfo.setPraiseStatus(0);
            ctSynclassWorkAnswerPraiseMapper.updateByPrimaryKey(praiseinfo);
        }
        CtSynclassWorkStudentStatistics answerStatistics = ctSynclassWorkStudentStatisticsMapper.selectByPrimaryKey(workUserId, schoolId);
        answerStatistics.setPraiseTimes(answerStatistics.getPraiseTimes().intValue() - 1);
        if (answerStatistics.getPraiseTimes().intValue() < 0) {
            answerStatistics.setPraiseTimes(0);
        }
        ctSynclassWorkStudentStatisticsMapper.updateByPrimaryKey(answerStatistics);
    }

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param userId     用户ID
     * @param schoolId   学校ID
     * @param context    评论内容
     * @param userType   用户类型 1教师 2学生
     * @throws net.xuele.common.exceptions.CloudteachException 评论answerId对应的作业回答
     */
    @Override
    public String comment(String workUserId, String userId, String schoolId, String context, int userType) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:点评学生作业,workUserId:" + workUserId + ",userId:" + userId + ",userType:" + userType + ",context:" + context + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workUserId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId) || StringUtils.isEmpty(context)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        CtSynclassWorkAnswerComment commentinfo = new CtSynclassWorkAnswerComment();
        commentinfo.setCommentId(UUID.randomUUID().toString().replace("-", ""));
        commentinfo.setWorkUserId(workUserId);
        commentinfo.setUserId(userId);
        commentinfo.setUserType(userType);
        commentinfo.setContext(context);
        commentinfo.setCommentTime(new Date());
        commentinfo.setSchoolId(schoolId);
        commentinfo.setStatus(1);
        ctSynclassWorkAnswerCommentMapper.insert(commentinfo);

        CtSynclassWorkStudentStatistics answerStatistics = ctSynclassWorkStudentStatisticsMapper.selectByPrimaryKey(workUserId, schoolId);
        answerStatistics.setCommentTimes(answerStatistics.getCommentTimes().intValue() + 1);
        ctSynclassWorkStudentStatisticsMapper.updateByPrimaryKey(answerStatistics);

        if (userType == 1) {
            //评论人是教师则需要修改批改作业状态
            correct(workUserId, schoolId);
        }

        return commentinfo.getCommentId();
    }

    /**
     * @param workUserId 同步课堂作业学生ID
     * @param schoolId   学校ID
     *                   批改answerId对应的作业回答
     */
    @Override
    public void correct(String workUserId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:批改学生作业,workUserId:" + workUserId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(workUserId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.selectByPrimaryKey(workUserId, schoolId);

        if (ctSynclassWorkStudent == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        if (ctSynclassWorkStudent.getCorrectStatus().intValue() == 0) {
            ctSynclassWorkStudent.setCorrectStatus(1);
            ctSynclassWorkStudent.setCorrectTime(new Date());
            ctSynclassWorkStudentMapper.updateByPrimaryKey(ctSynclassWorkStudent);
            //修改作业学生汇总表，将已提交未批改学生作业修改为已批改
            ctWorkStudentGatherMapper.updateByTeacherCorrect(ctSynclassWorkStudent.getWorkId(), ctSynclassWorkStudent.getUserId(), schoolId);
        }
        //修改作业汇总统计信息
        updateWorkGatherAfterCorrect(ctSynclassWorkStudent.getWorkId(), schoolId);
    }

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     *                 学生作业批改后修改作业批改状态、作业统计信息已批改学生数
     */
    @Override
    public void updateWorkGatherAfterCorrect(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:学生作业批改后修改作业批改状态、作业统计信息已批改学生数,workId:" + workId + ",schoolId:" + schoolId);
        //获取未批改学生信息
        List<CtSynclassWorkStudent> unCorrectStudentList = ctSynclassWorkStudentMapper.selectUnCorrectStudentList(workId, schoolId);

        //判断最新作业批改状态（0未批改 1部分批改 2全部批改）
        int newCorrectStatus = 0;
        if (unCorrectStudentList == null || unCorrectStudentList.size() == 0) {
            newCorrectStatus = 2;
        } else {
            newCorrectStatus = 1;
        }
        CtSynclassWork ctSynclassWork = ctSynclassWorkMapper.selectByPrimaryKey(workId, schoolId);
        //修改作业表信息（作业批改状态）
        if (ctSynclassWork.getCorrectStatus().intValue() < newCorrectStatus) {
            ctSynclassWork.setCorrectStatus(newCorrectStatus);
            ctSynclassWorkMapper.updateByPrimaryKey(ctSynclassWork);
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
        logger.info("SynclassWorkServiceImpl:教师删除评论,commentId:" + commentId + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //验证删除的是否是该教师布置作业对应的评论
        CtSynclassWorkAnswerComment commentObj = ctSynclassWorkAnswerCommentMapper.selectByPrimaryKey(commentId, schoolId);
        if (commentObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.selectByPrimaryKey(commentObj.getWorkUserId(), schoolId);
        if (ctSynclassWorkStudent == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        CtSynclassWork workObj = ctSynclassWorkMapper.selectByPrimaryKey(ctSynclassWorkStudent.getWorkId(), schoolId);
        if (workObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }

        if (!workObj.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        commentObj.setStatus(0);
        ctSynclassWorkAnswerCommentMapper.updateByPrimaryKey(commentObj);

        CtSynclassWorkStudentStatistics answerStatistics = ctSynclassWorkStudentStatisticsMapper.selectByPrimaryKey(commentObj.getWorkUserId(), schoolId);
        answerStatistics.setCommentTimes(answerStatistics.getCommentTimes().intValue() - 1);
        ctSynclassWorkStudentStatisticsMapper.updateByPrimaryKey(answerStatistics);
    }

    /**
     * @param commentId 评论ID
     * @param userId    学生用户ID
     * @param schoolId  学校ID
     *                  学生删除自己的评论
     */
    @Override
    public void delStuCommont(String commentId, String userId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:学生删除自己的评论,commentId:" + commentId + ",userId:" + userId + ",schoolId:" + schoolId);
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        //验证删除的是否是该学生的评论
        CtSynclassWorkAnswerComment commentObj = ctSynclassWorkAnswerCommentMapper.selectByPrimaryKey(commentId, schoolId);
        if (commentObj == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(),
                    CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (!commentObj.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
                    CloudTeachErrorEnum.NOPERMISSION.getCode());
        }
        commentObj.setStatus(0);
        ctSynclassWorkAnswerCommentMapper.updateByPrimaryKey(commentObj);
    }

    /**
     * @param workId 作业Id
     *               根据workId获取同步课堂作业信息
     */
    @Override
    public SynclassWorkDetailViewDTO querySynclassWorkDetailByWorkId(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取同步课堂作业信息,workId:" + workId + ",schoolId:" + schoolId);
        SynclassWorkDetailView synclassWorkDetailView = ctSynclassWorkMapper.querySynclassWorkDetail(workId, schoolId);
        if (synclassWorkDetailView == null) {
            return null;
        }
        SynclassWorkDetailViewDTO synclassWorkDetailViewDTO = new SynclassWorkDetailViewDTO();
        BeanUtils.copyProperties(synclassWorkDetailView, synclassWorkDetailViewDTO);
        return synclassWorkDetailViewDTO;
    }

    /**
     * @param workId 作业Id
     *               根据同步课堂作业ID获取对应的游戏附件信息
     */
    @Override
    public List<SynclassWorkGameViewDTO> querySynclassWorkGameList(String workId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:获取对应的游戏附件信息,workId:" + workId + ",schoolId:" + schoolId);
        List<SynclassWorkGameView> synclassWorkGameViewList = ctSynclassWorkGameMapper.querySynclassWorkGameList(workId, schoolId);
        if (synclassWorkGameViewList == null) {
            return null;
        }
        for (SynclassWorkGameView viewObj : synclassWorkGameViewList) {
            //获取游戏信息
            String gameId = viewObj.getGameFileId();
            ACCourseWaresDTO aCCourseWaresDTO = aCCourseWaresService.selectCourseWareByUcId(new Integer(gameId));
            if (aCCourseWaresDTO != null) {
                viewObj.setGameName(aCCourseWaresDTO.getcTitle());
                viewObj.setcReal(aCCourseWaresDTO.getcReal());
                viewObj.setcId(aCCourseWaresDTO.getcId());
                viewObj.setCkName(aCCourseWaresDTO.getCkName());
            } else {
                viewObj.setGameName("");
                viewObj.setcReal(0);
                viewObj.setcId(0);
                viewObj.setCkName("");
            }

        }
        return entityScWorkGameListToDtoList(synclassWorkGameViewList);
    }
    //=======================private methods================================//

    /**
     * @param datalist 同步课堂作业回答信息List<domain>转换成List<DTO>
     */
    private static List<SynclassWorkAnswerViewDTO> entityScWorkAswViewListToDtoList(List<SynclassWorkAnswerView> datalist) {
        List<SynclassWorkAnswerViewDTO> resList = new ArrayList<>();
        for (SynclassWorkAnswerView objDATA : datalist) {
            SynclassWorkAnswerViewDTO objDTO = new SynclassWorkAnswerViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            objDTO.setPlayViewList(entityScWorkPlayListToDtoList(objDATA.getPlayViewList()));
            objDTO.setCommentViewList(WorkEntityTransform.entityWorkAnswerCommentListToDtoList(objDATA.getCommentViewList()));
            objDTO.setPraiseViewList(WorkEntityTransform.entityWorkAnswerPraiseListToDtoList(objDATA.getPraiseViewList()));
            resList.add(objDTO);
        }
        return resList;
    }

    /**
     * @param datalist 同步课堂作业练习信息List<domain>转换成List<DTO>
     */
    private static List<SynclassWorkPlayViewDTO> entityScWorkPlayListToDtoList(List<SynclassWorkPlayView> datalist) {
        List<SynclassWorkPlayViewDTO> resList = new ArrayList<>();
        for (SynclassWorkPlayView objDATA : datalist) {
            SynclassWorkPlayViewDTO objDTO = new SynclassWorkPlayViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    /**
     * @param datalist 同步课堂作业游戏附件List<domain>转换成List<DTO>
     */
    private static List<SynclassWorkGameViewDTO> entityScWorkGameListToDtoList(List<SynclassWorkGameView> datalist) {
        List<SynclassWorkGameViewDTO> resList = new ArrayList<>();
        for (SynclassWorkGameView objDATA : datalist) {
            SynclassWorkGameViewDTO objDTO = new SynclassWorkGameViewDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    /**
     * 修改学生练习表
     *
     * @param playDTO
     * @return
     */
    @Override
    public void modifySynclassWorkPlay(SynclassWorkPlayDTO playDTO) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:修改学生练习表");
        String schoolId = playDTO.getSchoolId();
        String workId = playDTO.getWorkId();
        String gameId = playDTO.getWorkGameId();
        String userId = playDTO.getUserId();
        //查询当前数据库里的数据
        CtSynclassWorkPlay oldPlay = ctSynclassWorkPlayMapper.selectByWorkIdAndGameId(workId, gameId, userId, schoolId);
        if (oldPlay == null) {
            throw new CloudteachException(CloudTeachErrorEnum.SYNCLASS_GAME_NULL.getMsg(),
                    CloudTeachErrorEnum.SYNCLASS_GAME_NULL.getCode());
        }
        CtSynclassWorkPlay play = TranslateSyncclassWork.getSynclassWorkPlayFromDto(playDTO, oldPlay);
        // 获取同步课堂学生记录
        CtSynclassWorkStudent synclassWorkStudent = ctSynclassWorkStudentMapper.getCtSynclassInfoByWorkStudent(workId, userId, schoolId);
        //同步课堂作业学生练习表
        if (synclassWorkStudent.getSubStatus() == 0) {
            ctSynclassWorkPlayMapper.updateByPrimaryKey(play);
        }
        //同步课堂作业学生表 是否已全部提交
        //count为总条数和总的提交数之差
  /*      int count =ctSynclassWorkPlayMapper.selectCount(oldPlay);
        //代表全部提交了
        if(count == 0 && play.getPlayTimes()== 1){
            ctSynclassWorkStudentMapper.updateSubStatusByWorkId(oldPlay.getWorkId(),oldPlay.getUserId(),schoolId);

            int i = 0;
            while(i<=0){
                CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(oldPlay.getWorkId(), schoolId);
                if(ctWorkStatistics==null){
                    throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                }
                int subStudentNum =ctWorkStatistics.getWorkSubStudentNum() + 1;
                ctWorkStatistics.setWorkSubStudentNum(subStudentNum);
                i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
            }

            ctWorkStudentGatherMapper.updateByStuSubmit(oldPlay.getWorkId(),oldPlay.getUserId(),schoolId);
            int subCount = ctSynclassWorkStudentMapper.selectSubCount(oldPlay.getWorkId(),schoolId);

            int finishStatus = 1;
            //已经全部提交
            if(subCount == 0){
                finishStatus = 2;
            }
            ctSynclassWorkMapper.updateFinishStatusByWorkId(finishStatus,oldPlay.getWorkId(),schoolId);
        }*/
    }

    /**
     * 编辑点赞记录表(没有的话，添加；有的话，修改)
     *
     * @param praiseDTO
     * @throws CloudteachException
     */
    @Override
    public void editSynclassWorkAnswerPraise(SynclassWorkAnswerPraiseDTO praiseDTO) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:编辑点赞记录表");
        CtSynclassWorkAnswerPraise praise = TranslateSyncclassWork.getSynclassWorkAnswerPraiseFromDto(praiseDTO);
        String schoolId = praise.getSchoolId();
        //为0的时候，表示数据库中肯定有数据
        if (praise.getPraiseStatus() == 0) {
            ctSynclassWorkAnswerPraiseMapper.updateByPrimaryKey(praise);
            ctSynclassWorkStudentStatisticsMapper.updatePraiseTimes(-1, praise.getWorkUserId(), schoolId);
        } else {
            //增加点赞
            CtSynclassWorkAnswerPraise oldPraise = ctSynclassWorkAnswerPraiseMapper.
                    selectByPrimaryKey(praise.getWorkUserId(), praise.getUserId(), schoolId);
            ctSynclassWorkStudentStatisticsMapper.updatePraiseTimes(1, praise.getWorkUserId(), schoolId);
            if (oldPraise == null) {
                ctSynclassWorkAnswerPraiseMapper.insert(praise);
            } else {
                ctSynclassWorkAnswerPraiseMapper.updateByPrimaryKey(praise);
            }
        }
    }

    /**
     * 新增评论记录表
     *
     * @param commentDTO
     * @throws CloudteachException
     */
    @Override
    public void addSynclassWorkAnswerComment(SynclassWorkAnswerCommentDTO commentDTO) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:新增评论记录表");
        CtSynclassWorkAnswerComment comment = TranslateSyncclassWork.getSynclassWorkAnswerCommentFromDto(commentDTO);
        ctSynclassWorkAnswerCommentMapper.insert(comment);
        ctSynclassWorkStudentStatisticsMapper.updateCommentTimes(1, comment.getWorkUserId(), comment.getSchoolId());
    }

    /**
     * 删除评论记录表
     *
     * @param commentDTO
     */
    @Override
    public void delSynclassWorkAnswerComment(SynclassWorkAnswerCommentDTO commentDTO) {
        logger.info("SynclassWorkServiceImpl:删除评论记录表");
        CtSynclassWorkAnswerComment comment = TranslateSyncclassWork.getSynclassWorkAnswerCommentFromDto(commentDTO);
        ctSynclassWorkAnswerCommentMapper.updateStatus(comment.getCommentId(), comment.getSchoolId());
        ctSynclassWorkStudentStatisticsMapper.updateCommentTimes(-1, comment.getWorkUserId(), comment.getSchoolId());
    }

    /**
     * 删除学生提交的作业
     *
     * @param userId     教师ID
     * @param workUserId 学生回答ID
     * @param schoolId
     * @throws CloudteachException
     */
    @Override
    public void delSynclassStuWork(String userId, String workUserId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:删除学生作业");
        CtSynclassWorkStudent oldStudent = ctSynclassWorkStudentMapper.selectSynclassWorkAnswerForUpdate(workUserId, schoolId);
        if (oldStudent == null) {
            throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
        }
        if (oldStudent.getStatus().intValue() != 1) {
            throw new CloudteachException(CloudTeachErrorEnum.DATAERROR.getMsg() + "：作业已经被删除", CloudTeachErrorEnum.DATAERROR.getCode());
        }
        CtSynclassWorkStudent student = TranslateSyncclassWork.getInitStudent(oldStudent);

        CtSynclassWork CtSynclassWork = ctSynclassWorkMapper.selectByPrimaryKey(oldStudent.getWorkId(), schoolId);

        if (CtSynclassWork == null || !CtSynclassWork.getUserId().equals(userId)) {
            throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(), CloudTeachErrorEnum.NOPERMISSION.getCode());
        }

        String oldWorkUserId = oldStudent.getWorkUserId();

        //同步课堂作业表
        //已经提交 没提交就肯定没批改
        if (oldStudent.getSubStatus() == 1) {
            CtWorkStatistics workStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(oldStudent.getWorkId(), schoolId);
            int correctFlag = 0;
            //被批改过
            if (oldStudent.getCorrectStatus() == 1) {
                correctFlag = -1;
            }
            //作业表
            int finishStatus = 0;
            int correctStatus = 0;
            if (workStatistics.getWorkSubStudentNum() > 1) {
                finishStatus = -1;
            }
            if (workStatistics.getWorkCorrectStudentNum() > 1) {
                correctStatus = -1;
            }
            ctSynclassWorkMapper.updateByDelStudent(finishStatus, correctStatus, oldStudent.getWorkId(), schoolId);

            //作业统计表
            int tryTimes = 1;
            int i = 0;
            while (i <= 0 && tryTimes <= Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
                CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(oldStudent.getWorkId(), schoolId);
                if (ctWorkStatistics == null) {
                    throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                }
                int subStudentNum = ctWorkStatistics.getWorkSubStudentNum() - 1;
                int correctStudentNum = ctWorkStatistics.getWorkCorrectStudentNum() + correctFlag;
                ctWorkStatistics.setWorkCorrectStudentNum(correctStudentNum);
                ctWorkStatistics.setWorkSubStudentNum(subStudentNum);
                i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
                tryTimes++;
            }
            if (tryTimes > Constants.OPTIMISTIC_LOCK_TRY_TIMES) {
                //超过乐观锁最大尝试次数，更新失败
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }

            //作业学生汇总表
            ctWorkStudentGatherMapper.updateInitByWorkIdAndUserId(oldStudent.getWorkId(), oldStudent.getUserId(), schoolId);
        }

        //学生表信息
        ctSynclassWorkStudentMapper.updateByWorkId(oldStudent.getWorkId(), schoolId, oldStudent.getUserId());
        ctSynclassWorkStudentMapper.insert(student);

        //课堂学生统计表
        ctSynclassWorkStudentStatisticsMapper.updateStatusByPrimaryKey(oldWorkUserId, schoolId);
        CtSynclassWorkStudentStatistics statistics = TranslateSyncclassWork.getInitStudentStatistics(schoolId);
        statistics.setWorkUserId(student.getWorkUserId());
        ctSynclassWorkStudentStatisticsMapper.insert(statistics);

        //点赞记录表
        ctSynclassWorkAnswerPraiseMapper.deleteByWorkUserId(oldWorkUserId, schoolId);

        //评论记录表
        ctSynclassWorkAnswerCommentMapper.deleteByWorkUserId(oldWorkUserId, schoolId);

        //学生练习表
        List<CtSynclassWorkPlay> newPlayList = ctSynclassWorkPlayMapper.getSynclassWorkPlayInfoList(oldWorkUserId, schoolId);
        ctSynclassWorkPlayMapper.delete(oldWorkUserId, schoolId);
        for (CtSynclassWorkPlay playObj : newPlayList) {
            playObj.setPlayId(UUID.randomUUID().toString().replace("-", ""));
            playObj.setWorkUserId(student.getWorkUserId());
            playObj.setPlayTimes(0);
            playObj.setMaxScore(0);
            playObj.setFinishStatus(0);
            playObj.setSubTime(null);
            playObj.setUpdateTime(new Date());
            playObj.setSpendtime(0);
            playObj.setUpdateUserId(CtSynclassWork.getUserId());
        }
        ctSynclassWorkPlayMapper.batchInsertPlay(newPlayList);
    }

    /**
     * @param workId    作业Id
     * @param studentId 学生ID
     * @param schoolId  学校ID
     *                  获取同步课堂作业对应某个学生的回答信息
     */
    @Override
    public SynclassWorkStudentDTO getStuSynclassWorkAnswer(String workId, String studentId, String schoolId) throws CloudteachException {
        logger.info("SynclassWorkServiceImpl:快速批改同步课堂作业");
        CtSynclassWorkStudent ctSynclassWorkStudent = ctSynclassWorkStudentMapper.getCtSynclassInfoByWorkStudent(workId, studentId, schoolId);
        if (ctSynclassWorkStudent == null) {
            return null;
        }
        SynclassWorkStudentDTO synclassWorkStudentDTO = new SynclassWorkStudentDTO();
        BeanUtils.copyProperties(ctSynclassWorkStudent, synclassWorkStudentDTO);
        return synclassWorkStudentDTO;
    }

    /**
     * 获取同步课堂作业对应某个班级学生的作业统计信息
     *
     * @param schoolId   学校ID
     * @param classId    班级ID
     * @param workId     作业Id
     * @param workGameId 游戏附件ID
     * @return
     */
    @Override
    public SRTeacherWorkDTO getSynclassWorkSR(String schoolId, String classId, String workId, String workGameId) {
        logger.info("获取同步课堂作业下某个游戏对应某个班级学生的作业统计信息,schoolId:" + schoolId + ",workId:" + workId + ",classId:" + classId + ",workGameId:" + workGameId);

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
        //已打分学生按成绩排名列表
        List<SRTeacherWorkStuDTO> orderStuList = new ArrayList<>();

        //获取该作业对应班级的学生总数
        int totalStuNum = ctSynclassWorkStudentMapper.queryClassStuCount(workId, classId, schoolId);
        if (totalStuNum <= 0) {
            throw new CloudteachException(CloudTeachErrorEnum.CLASSNOSTUDENT.getMsg() + ":无法获取该班级学生作业的统计信息", CloudTeachErrorEnum.CLASSNOSTUDENT.getCode());
        }
        sRTeacherWorkDTO.setClassStuNum(totalStuNum);
        //获取教师作业某个班级中已提交作业的学生信息
        List<SynclassWorkPlayView> subedAnswerList = ctSynclassWorkPlayMapper.queryClassGameSubedStuList(workId, classId, workGameId, schoolId);
        if (subedAnswerList != null && subedAnswerList.size() > 0) {
            sRTeacherWorkDTO.setSubStuNum(subedAnswerList.size());
        }
        for (SynclassWorkPlayView synclassWorkPlayView : subedAnswerList) {
            SRTeacherWorkStuDTO sRTeacherWorkStuDTO = new SRTeacherWorkStuDTO();
            sRTeacherWorkStuDTO.setStudentId(synclassWorkPlayView.getUserId());
            sRTeacherWorkStuDTO.setStudentName(synclassWorkPlayView.getUserName());
            sRTeacherWorkStuDTO.setHeadUrl(synclassWorkPlayView.getIcon());

            sRTeacherWorkStuDTO.setScore(synclassWorkPlayView.getMaxScore());
            if (synclassWorkPlayView.getMaxScore() >= 85) {
                scoreAStuList.add(sRTeacherWorkStuDTO);
            } else if (synclassWorkPlayView.getMaxScore() >= 70 && synclassWorkPlayView.getMaxScore() < 85) {
                scoreBStuList.add(sRTeacherWorkStuDTO);
            } else if (synclassWorkPlayView.getMaxScore() >= 55 && synclassWorkPlayView.getMaxScore() < 70) {
                scoreCStuList.add(sRTeacherWorkStuDTO);
            } else {
                scoreDStuList.add(sRTeacherWorkStuDTO);
            }
            orderStuList.add(sRTeacherWorkStuDTO);
        }
        sRTeacherWorkDTO.setFinshPect((int) NumberFormat.getFloatRound(100.0 * sRTeacherWorkDTO.getSubStuNum() / sRTeacherWorkDTO.getClassStuNum(), 0));
        sRTeacherWorkDTO.setScoreAPect((int) NumberFormat.getFloatRound(100.0 * scoreAStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreBPect((int) NumberFormat.getFloatRound(100.0 * scoreBStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreCPect((int) NumberFormat.getFloatRound(100.0 * scoreCStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreDPect((int) NumberFormat.getFloatRound(100.0 * scoreDStuList.size() / sRTeacherWorkDTO.getSubStuNum(), 0));
        sRTeacherWorkDTO.setScoreAStuList(scoreAStuList);
        sRTeacherWorkDTO.setScoreBStuList(scoreBStuList);
        sRTeacherWorkDTO.setScoreCStuList(scoreCStuList);
        sRTeacherWorkDTO.setScoreDStuList(scoreDStuList);
        sRTeacherWorkDTO.setOrderStuList(orderStuList);

        return sRTeacherWorkDTO;
    }
}