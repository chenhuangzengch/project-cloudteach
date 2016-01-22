package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.BlackboardPublishPageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.BlackboardPublishService;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.common.CloudTeachNotifyContent;
import net.xuele.cloudteach.service.common.SendNotify;
import net.xuele.cloudteach.service.util.TranslateBlackboard;
import net.xuele.cloudteach.service.util.TranslateCommon;
import net.xuele.cloudteach.service.util.TranslateJson;
import net.xuele.cloudteach.service.util.VerifyParam;
import net.xuele.cloudteach.view.FileInfoView;
import net.xuele.cloudteach.view.StudentNumView;
import net.xuele.cloudteach.view.UnitBookNameView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import net.xuele.teacheval.constants.AccessAction;
import net.xuele.teacheval.domain.UserAccessAction;
import net.xuele.teacheval.service.WriteAccessLogService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
 * BlackboardPublishServiceImpl
 *
 * @author cm.wang
 * @date 2015/7/14 0016
 */
@Service
public class BlackboardPublishServiceImpl implements BlackboardPublishService {

    private static Logger logger = LoggerFactory.getLogger(BlackboardPublishServiceImpl.class);

    @Autowired
    private CtBlackboardPublishMapper ctBlackboardPublishMapper;

    @Autowired
    private CtBlackboardPublishFilesMapper ctBlackboardPublishFilesMapper;

    @Autowired
    private CtBlackboardPublishClassMapper ctBlackboardPublishClassMapper;

    @Autowired
    private CtBlackboardPublishStudentMapper ctBlackboardPublishStudentMapper;

    @Autowired
    private CtWorkStatisticsMapper ctWorkStatisticsMapper;

    @Autowired
    private CtWorkGatherMapper ctWorkGatherMapper;

    @Autowired
    private CtWorkStudentGatherMapper ctWorkStudentGatherMapper;

    @Autowired
    private CtUnitsMapper ctUnitsMapper;

    @Autowired
    private CtWorkClassGatherMapper ctWorkClassGatherMapper;

    @Autowired
    CtWorkTapeFilesMapper ctWorkTapeFilesMapper;

    @Autowired
    private CloudTeachService cloudTeachService;

    @Autowired
    private WriteAccessLogService writeAccessLogService;

    @Override
    public String addBlackboardPublish(BlackboardPublishInfoDTO blackboardPublishInfoDTO, WorkTapeFilesDTO workTapeFilesDTO
            ,String userName,String userIcon) {

        logger.info("BlackboardPublishServiceImpl:发布板书");
        logger.info("blackboardPublishInfoDTO:"+blackboardPublishInfoDTO);
        logger.info("workTapeFilesDTO:"+workTapeFilesDTO);
        logger.info("userName:"+userName);
        logger.info("userIcon:"+userIcon);

        //板书发布表
        CtBlackboardPublish ctBlackboardPublish = TranslateBlackboard.getCtBlackboardPublish(blackboardPublishInfoDTO.getBlackboardPublishDTO());
        String blackboardId = ctBlackboardPublish.getBlackboardId(); //板书Id
        String schoolId = ctBlackboardPublish.getSchoolId();
        String unitId = ctBlackboardPublish.getUnitId();
        String userId = ctBlackboardPublish.getUserId();
        String classJson = blackboardPublishInfoDTO.getClassJson();
        String context = ctBlackboardPublish.getContext();

        List<BlackboardPublishFilesDTO> filesDTOList = blackboardPublishInfoDTO.getFilesDTOList();

        if(CollectionUtils.isEmpty(filesDTOList)){
            throw new CloudteachException(CloudTeachErrorEnum.BLACKBOARD_File_NOT_EXIST.getMsg(),
                    CloudTeachErrorEnum.BLACKBOARD_File_NOT_EXIST.getCode());
        }
        if(StringUtils.isEmpty(classJson)){
            throw new CloudteachException(CloudTeachErrorEnum.WORK_CLASS_NULL.getMsg(),
                    CloudTeachErrorEnum.WORK_CLASS_NULL.getCode());
        }
        if(context == null ){
            ctBlackboardPublish.setContext("");
        }


        Date publishTime = ctBlackboardPublish.getPublishTime();

        //板书发布班级
        List<BlackboardPublishClassDTO> blackboardPublishClassDTOList = blackboardPublishInfoDTO.getBlackboardPublishClassDTOList();
        //板书发布班级表
        List<String> classList = TranslateBlackboard.getClassList(blackboardPublishClassDTOList);
        int initClassNum = classList.size();
        String response = "";


        try {
            logger.info("校验班级列表跟班级json中的班级id是否一致");
            if(VerifyParam.verifyClassInfo(classList, classJson)) {
                List<StudentNumView> studentNumViewList = ctUnitsMapper.getStudentNumBySchoolId(classList,schoolId);

                //判断后的班级Id,去除该班级下学生为空的id;
                classList = TranslateCommon.getSchoolIdList(studentNumViewList);
                int nowClassNum = classList.size();
                if (initClassNum != nowClassNum) {
                    response = TranslateCommon.getClassInfoById(classList,classJson);
                }
                else{
                    logger.info("插入板书");
                    ctBlackboardPublishMapper.insert(ctBlackboardPublish);

                    //附件信息
                    List<CtBlackboardPublishFiles> filesList = new ArrayList<CtBlackboardPublishFiles>();
                    //板书发布附件

                    String fileJson = "[]";
                    if (CollectionUtils.isNotEmpty(filesDTOList)) {
                        filesList = TranslateBlackboard.getCtBlackboardPublishFilesList(filesDTOList, blackboardId, schoolId);
                        logger.info("插入板书附件");
                        ctBlackboardPublishFilesMapper.batchInsert(filesList);
                        List<FileInfoView> fileInfoViewList = TranslateBlackboard.getFileInfoViewList(filesDTOList);
                        fileJson = TranslateJson.getFileJson(fileInfoViewList);
                    }


                    logger.info("插入板书班级");
                    List<CtBlackboardPublishClass> ctBlackboardPublishClassList = new ArrayList<CtBlackboardPublishClass>();
                    ctBlackboardPublishClassList = TranslateBlackboard.getCtBlackboardPublishClassList(blackboardPublishClassDTOList, blackboardId, schoolId);
                    ctBlackboardPublishClassMapper.batchInsert(ctBlackboardPublishClassList);


                    List<CtBlackboardPublishStudent> studentList = ctBlackboardPublishStudentMapper.getInitInfo(classList, schoolId, blackboardId);
                    int count = studentList.size();
                    if (CollectionUtils.isNotEmpty(studentList)) {
                        logger.info("插入板书班级对应的学生");
                        ctBlackboardPublishStudentMapper.batchInsert(studentList);
                    }

                    // 作业统计表ct_work_statistics相关

                    //科目名称和课程名称
                    UnitBookNameView unitBookNameView = ctUnitsMapper.queryUnitBookNameByUnitId(unitId);


                    int classNum = classList.size();
                    int workItemNum = filesList.size();
                    int workType = Constants.WORK_TYPE_BLACKBOARD_PUBLISH;


                    //汇总表
                    CtWorkGather ctWorkGather = TranslateBlackboard.getCtWorkGather(classJson, count, classNum, blackboardId, workItemNum,
                            workType, schoolId, fileJson, userId, unitBookNameView, ctBlackboardPublish.getContext(), publishTime, null);
                    logger.info("插入汇总表");
                    ctWorkGatherMapper.insert(ctWorkGather);
                    //统计表
                    CtWorkStatistics ctWorkStatistics = TranslateBlackboard.getCtWorkStatistics(
                            blackboardId, 0, schoolId);
                    ctWorkStatisticsMapper.insert(ctWorkStatistics);
                    //学生汇总表
                    if (CollectionUtils.isNotEmpty(studentList)) {
                        List<CtWorkStudentGather> studentGatherList = TranslateBlackboard.getStudentGatherList(studentList,blackboardId);
                        ctWorkStudentGatherMapper.batchInsert(studentGatherList);
                    }
                    //班级汇总表
                    List<CtWorkClassGather> classGatherList = TranslateBlackboard.getWorkClassGather(classList, blackboardId, schoolId);
                    if (CollectionUtils.isNotEmpty(classGatherList)) {
                        ctWorkClassGatherMapper.batchInsert(classGatherList);
                    }

                    //作业教师录音附件表
                    if (null != workTapeFilesDTO) {
                        CtWorkTapeFiles workTapeFiles = new CtWorkTapeFiles();
                        BeanUtils.copyProperties(workTapeFilesDTO, workTapeFiles);
                        // 主键
                        workTapeFiles.setFileId(UUID.randomUUID().toString().replace("-", ""));
                        workTapeFiles.setWorkId(blackboardId);
                        workTapeFiles.setWorkType(Constants.WORK_TYPE_BLACKBOARD_PUBLISH);
                        workTapeFiles.setUploadTime(new Date());
                        workTapeFiles.setSchoolId(schoolId);
                        workTapeFiles.setStatus(1);
                        ctWorkTapeFilesMapper.insert(workTapeFiles);
                    }

                    List<String> studentIdList = TranslateBlackboard.getStudentIdList(studentList);
                    String title = Constants.WARNVIEW_BLACKBOARD_TITLE;
                    String content = CloudTeachNotifyContent.getWarnSubContent(userName, workType);
                    new Thread(new SendNotify(cloudTeachService,studentIdList,title,content,userId,userIcon)).start();
                    try{
                        writeAccessLogService.writeAccessLog(new UserAccessAction.Builder()
                                .accessAction(AccessAction.Blackboard)
                                .userId(userId)
                                .occurredTime(new Date().getTime())
                                .build());
                    }catch (Exception e){
                        logger.error(" 写入日志错误："+e.getMessage());
                    }

                }
            }
            else{
                //参数错误
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        } catch (IOException e) {
            throw new CloudteachException("发布板书失败！", CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
        }

        return response;
    }

    @Override
    public int uploadFile(BlackboardPublishFilesDTO blackboardPublishFilesDTO) throws CloudteachException {
        CtBlackboardPublishFiles files = new CtBlackboardPublishFiles();
        files = TranslateBlackboard.entityBlackboardPublishFilesFromDto(blackboardPublishFilesDTO);
        int result = ctBlackboardPublishFilesMapper.insert(files);
        if (1 != result) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.INSERTDISKFAILED);
        }
        return result;
    }

    @Override
    public PageResponse<BlackboardPublishDTO> getBlackboardPublishsByUserId(BlackboardPublishPageRequest blackboardPublishPageRequest, String schoolId) {

        String userId = blackboardPublishPageRequest.getUserId();
        //获取总条数
        long count = ctBlackboardPublishMapper.selectCount(userId, schoolId);

        Page page = PageUtils.buildPage(blackboardPublishPageRequest);
        int pageSize = blackboardPublishPageRequest.getPageSize();
        List<CtBlackboardPublish> ctBlackboardPublishList = ctBlackboardPublishMapper.selectPage(pageSize, page, userId, schoolId);

        //返回PageResponse
        PageResponse<BlackboardPublishDTO> pageResponse = new PageResponse<BlackboardPublishDTO>();
        PageUtils.buldPageResponse(blackboardPublishPageRequest, pageResponse);
        pageResponse.setRows(TranslateBlackboard.entityBlackboardPublishListToDtoList(ctBlackboardPublishList));
        pageResponse.setRecords(count);
        return pageResponse;
    }

    @Override
    public BlackboardPublishInfoDTO getBlackboardPublishById(String blackboardId, String schoolId, String userId) {
        CtBlackboardPublish ctBlackboardPublish = ctBlackboardPublishMapper.selectByPrimaryKey(blackboardId, schoolId);
        List<CtBlackboardPublishFiles> filesList = ctBlackboardPublishFilesMapper.selectByBlackboardId(blackboardId, schoolId);

        //当学生第一次看的时候，把已读状态改为1，并在统计表里更改
        checkBlackboardPublish(userId, blackboardId, schoolId);

        BlackboardPublishInfoDTO infoDTO = new BlackboardPublishInfoDTO();
        BlackboardPublishDTO blackboardPublishDTO = TranslateBlackboard.entityBlackboardPublishToDto(ctBlackboardPublish);
        List<BlackboardPublishFilesDTO> filesDTOList = TranslateBlackboard.getFileList(filesList);
        infoDTO.setFilesDTOList(filesDTOList);
        infoDTO.setBlackboardPublishDTO(blackboardPublishDTO);

        return infoDTO;
    }

    public int checkBlackboardPublish(String userId, String blackboardId, String schoolId) {
        CtBlackboardPublishStudent blackboardPublishStudent = TranslateBlackboard.getCtBlackboardPublishStudent(userId, blackboardId, schoolId);
        int num = ctBlackboardPublishStudentMapper.updateByBlackboardIdAndUserIdSelective(blackboardPublishStudent);
        if (num > 0) {
            ctWorkStudentGatherMapper.updateSubStatusByWorkIdAndUserId(userId, schoolId, blackboardId);

            int tryTimes = 1;
            int i = 0;
            while(i<=0 && tryTimes<=Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(blackboardId, schoolId);
                if (ctWorkStatistics == null) {
                    throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                }
                int subStudentNum = ctWorkStatistics.getWorkSubStudentNum() + 1;
                ctWorkStatistics.setWorkSubStudentNum(subStudentNum);
                i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
                tryTimes++;
            }
            if(tryTimes>Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                //超过乐观锁最大尝试次数，更新失败
                throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
            }
        }

        return 1;
    }

    @Override
    public int editBlackboardPublish(BlackboardPublishInfoDTO blackboardPublishInfoDTO) {
        //板书发布表
        CtBlackboardPublish ctBlackboardPublish = TranslateBlackboard.getExistCtBlackboardPublish(blackboardPublishInfoDTO.getBlackboardPublishDTO());
        String blackboardId = ctBlackboardPublish.getBlackboardId();
        String schoolId = ctBlackboardPublish.getSchoolId();
        String unitId = ctBlackboardPublish.getUnitId();
        String userId = ctBlackboardPublish.getUserId();
        String classJson = blackboardPublishInfoDTO.getClassJson();
        Date publishTime = ctBlackboardPublish.getPublishTime();
        List<BlackboardPublishFilesDTO> filesDTOList = blackboardPublishInfoDTO.getFilesDTOList();

        ctBlackboardPublishMapper.updateByPrimaryKeyOpt(ctBlackboardPublish);

        //板书发布附件
        List<CtBlackboardPublishFiles> filesList = new ArrayList<CtBlackboardPublishFiles>();
        if (filesDTOList != null) {

            filesList = TranslateBlackboard.getCtBlackboardPublishFilesList(filesDTOList, blackboardId, schoolId);

            //先删除后增加
            ctBlackboardPublishFilesMapper.updateStatusByBlackboardId(blackboardId, schoolId);
            ctBlackboardPublishFilesMapper.batchInsert(filesList);
        }

        //板书发布班级,学生
        List<BlackboardPublishClassDTO> blackboardPublishClassDTOList = blackboardPublishInfoDTO.getBlackboardPublishClassDTOList();


        if (blackboardPublishClassDTOList != null) {
            //获得当前数据库中的班级列表
            List<String> oldClassList = ctBlackboardPublishClassMapper.getClassList(blackboardId, schoolId);
            //现在需要更新的班级列表
            List<String> classList = TranslateBlackboard.getClassList(blackboardPublishClassDTOList);
            //两者存在的班级列表
            List<String> existClassList = new ArrayList<String>();
            List<String> delClassList = new ArrayList<String>();
            List<String> insertClassList = new ArrayList<String>();
            for (String newClass : classList) {
                if (oldClassList.contains(newClass)) {
                    existClassList.add(newClass);
                } else {
                    insertClassList.add(newClass);
                }
            }
            for (String oldCLass : oldClassList) {
                if (!classList.contains(oldCLass)) {
                    delClassList.add(oldCLass);
                }
            }
            //班级更新
            if (delClassList.size() > 0 || insertClassList.size() > 0) {
                int subStudentNum = 0;
                if (existClassList.size() > 0) {
                    subStudentNum = ctBlackboardPublishStudentMapper.getStudentNumByClassIdList(existClassList, schoolId);
                }
                if (delClassList.size() > 0) {
                    ctBlackboardPublishClassMapper.batchUpdateByClassList(delClassList, schoolId);
                    ctBlackboardPublishStudentMapper.batchUpdateByClassIdList(delClassList, schoolId);
                    //班级汇总表
                    ctWorkClassGatherMapper.updateStatusByClassList(0, schoolId, delClassList);
                    //学生汇总表
                    ctWorkStudentGatherMapper.updateStatusByClassList(0, schoolId, delClassList);
                }
                if (insertClassList.size() > 0) {
                    List<CtBlackboardPublishClass> ctBlackboardPublishClassList =
                            TranslateBlackboard.getCtBlackboardPublishClassListByClassList(insertClassList, blackboardId, schoolId);
                    ctBlackboardPublishClassMapper.batchInsert(ctBlackboardPublishClassList);
                    List<CtBlackboardPublishStudent> studentList = ctBlackboardPublishStudentMapper.getInitInfo(classList, schoolId, blackboardId);
                    int count = studentList.size();
                    ctBlackboardPublishStudentMapper.batchInsert(studentList);
                    //班级汇总表
                    List<CtWorkClassGather> classGatherList = ctWorkClassGatherMapper.getInitInfo(schoolId, insertClassList, blackboardId);
                    ctWorkClassGatherMapper.batchInsert(classGatherList);

                    //学生汇总表
                    List<CtWorkStudentGather> studentGatherList = ctWorkStudentGatherMapper.getInitInfo(schoolId, insertClassList, blackboardId);
                    ctWorkStudentGatherMapper.batchInsert(studentGatherList);
                }

                //科目名称和课程名称
                UnitBookNameView unitBookNameView = ctUnitsMapper.queryUnitBookNameByUnitId(unitId);

                List<FileInfoView> fileInfoViewList =
                        ctBlackboardPublishFilesMapper.selectByUrl(schoolId, classList);

                String fileJson = TranslateJson.getFileJson(fileInfoViewList);

                int classNum = classList.size();
                int workType = Constants.WORK_TYPE_BLACKBOARD_PUBLISH;
                int workItemNum = filesList.size();
                int studentNum = ctBlackboardPublishStudentMapper.getCountByBlackboardId(blackboardId, schoolId);

                //汇总表
                CtWorkGather ctWorkGather = TranslateBlackboard.getCtWorkGather(classJson, studentNum, classNum, blackboardId, workItemNum,
                        workType, schoolId, fileJson, userId, unitBookNameView, ctBlackboardPublish.getContext(), publishTime, null);
                ctWorkGatherMapper.updateByPrimaryKey(ctWorkGather);
                //统计表

                int tryTimes = 1;
                int i = 0;
                while(i<=0 && tryTimes<=Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                    CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(blackboardId, schoolId);
                    if (ctWorkStatistics == null) {
                        throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                    }
                    ctWorkStatistics.setWorkSubStudentNum(subStudentNum);
                    i = ctWorkStatisticsMapper.updateByPrimaryKey(ctWorkStatistics);
                    tryTimes++;
                }
                if(tryTimes>Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                    //超过乐观锁最大尝试次数，更新失败
                    throw new CloudteachException(CloudTeachErrorEnum.SYSTEMBUSY.getMsg(), CloudTeachErrorEnum.SYSTEMBUSY.getCode());
                }
            }
        }
        return 1;
    }

    @Override
    public int delBlackboardPublish(String blackboardId, String schoolId) {
        ctBlackboardPublishStudentMapper.updateStatusByBlackboardId(blackboardId, schoolId);
        ctBlackboardPublishClassMapper.updateStatusByBlackboardId(blackboardId, schoolId);
        ctBlackboardPublishFilesMapper.updateStatusByBlackboardId(blackboardId, schoolId);
        ctBlackboardPublishMapper.updateStatusByPrimaryKey(blackboardId, schoolId);
        ctWorkGatherMapper.updateStatusByWorkId(0, blackboardId, schoolId);
        ctWorkStudentGatherMapper.updateStatusByWorkId(0, blackboardId, schoolId);
        ctWorkClassGatherMapper.updateStatusByWorkId(0, blackboardId, schoolId);
        return 1;
    }

    /**
     * 获取课程ID对应的所有已添加教案的板书附件信息
     * @param userId
     * @param unitId
     * @param schoolId
     * @return
     */
    @Override
    public List<BlackboardPublishFilesDTO> selectBlackboardListByUnitId(String userId,String unitId, String schoolId) {
        List<CtBlackboardPublishFiles> bbFilesList = ctBlackboardPublishFilesMapper.
                selectAddLessionPlanBlackboardListByUnitId(userId, unitId, schoolId);
        if (bbFilesList == null) {
            return null;
        }
        List<BlackboardPublishFilesDTO> filesDTOList = TranslateBlackboard.getFileList(bbFilesList);
        return filesDTOList;
    }

    /**
     * 获取课程ID对应最新的板书发布信息
     * @param userId
     * @param unitId
     * @param schoolId
     * @return
     */
    @Override
    public BlackboardPublishDTO selectNewBlackboardPublish(String userId,String unitId, String schoolId) {
        CtBlackboardPublish ctBlackboardPublish = ctBlackboardPublishMapper.selectNewBlackboardPublish(userId,unitId, schoolId);
        if(ctBlackboardPublish == null){
            return null;
        }
        BlackboardPublishDTO blackboardPublishDTO = new BlackboardPublishDTO();
        BeanUtils.copyProperties(ctBlackboardPublish,blackboardPublishDTO);
        return blackboardPublishDTO;
    }
}
