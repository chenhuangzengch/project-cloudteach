package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CourseReappearPageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.CourseReappearService;
import net.xuele.cloudteach.service.common.CloudTeachNotifyContent;
import net.xuele.cloudteach.service.common.SendNotify;
import net.xuele.cloudteach.service.util.*;
import net.xuele.cloudteach.view.FileInfoView;
import net.xuele.cloudteach.view.StudentNumView;
import net.xuele.cloudteach.view.UnitBookNameView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by cm.wang on 2015/7/17 0017.
 */
@Service
public class CourseReappearServiceImpl implements CourseReappearService{

    private static Logger logger = LoggerFactory.getLogger(CourseReappearServiceImpl.class);

    @Autowired
    private CtCourseReappearMapper ctCourseReappearMapper;

    @Autowired
    private CtCourseReappearClassMapper ctCourseReappearClassMapper;

    @Autowired
    private CtCourseReappearCoursewaresMapper ctCourseReappearCoursewaresMapper;

    @Autowired
    private CtCourseReappearStudentMapper ctCourseReappearStudentMapper;

    @Autowired
    private CtTeachCoursewaresStatisticsMapper ctTeachCoursewaresStatisticsMapper;

    @Autowired
    private CtTeachCoursewaresContentMapper ctTeachCoursewaresContentMapper;

    @Autowired
    private CtWorkStatisticsMapper ctWorkStatisticsMapper;

    @Autowired
    private CtUnitsMapper ctUnitsMapper;

    @Autowired
    private CtWorkClassGatherMapper ctWorkClassGatherMapper;

    @Autowired
    private CtWorkGatherMapper ctWorkGatherMapper;

    @Autowired
    private CtWorkStudentGatherMapper ctWorkStudentGatherMapper;

    @Autowired
    private CtTeachCoursewaresMapper ctTeachCoursewaresMapper;

    @Autowired
    CtWorkTapeFilesMapper ctWorkTapeFilesMapper;

    @Autowired
    private CloudTeachService cloudTeachService;

    @Override
    @Transactional
    public String addCourseReapper(CourseReappearInfoDTO courseReappearInfoDTO,WorkTapeFilesDTO workTapeFilesDTO,
                                   String userName,String userIcon) {

        logger.info("CourseReappearServiceImpl:发布课件");
        logger.info("courseReappearInfoDTO:"+courseReappearInfoDTO);
        logger.info("workTapeFilesDTO:"+workTapeFilesDTO);
        logger.info("userName:"+userName);
        logger.info("userIcon:"+userIcon);

        //课件发布表
        CtCourseReappear ctCourseReappear = TranslateCourseReappear.getCtCourseReappear(courseReappearInfoDTO.getCourseReappearDTO());
        //课件发布表Id
        String reappearId = ctCourseReappear.getReappearId();
        String schoolId = ctCourseReappear.getSchoolId();
        String unitId =ctCourseReappear.getUnitId();
        String userId = ctCourseReappear.getUserId();
        Date publishTime = ctCourseReappear.getPublishTime();
        String classJson = courseReappearInfoDTO.getClassJson();
        List<String> classIdList = courseReappearInfoDTO.getClassIdList();
        int initClassNum = classIdList.size();
        String response = "";

        try {
            logger.info("校验班级列表跟班级json中的班级id是否一致");
            if(VerifyParam.verifyClassInfo(classIdList, classJson)) {

                List<StudentNumView> studentNumViewList = ctUnitsMapper.getStudentNumBySchoolId(classIdList,schoolId);

                //判断后的班级Id,去除该班级下学生为空的id;
                classIdList = TranslateCommon.getSchoolIdList(studentNumViewList);
                int nowClassNum = classIdList.size();
                if (initClassNum != nowClassNum) {
                    response = TranslateCommon.getClassInfoById(classIdList,classJson);
                }
                else{
                    ctCourseReappearMapper.insert(ctCourseReappear);

                    //课件发布课件表
                    List<String> coursewaresIdList = courseReappearInfoDTO.getCoursewaresIdList();
                    int workItemNum = 0;
                    if (coursewaresIdList != null) {

                        //课件数量
                        workItemNum = coursewaresIdList.size();


                        List<CtCourseReappearCoursewares> coursewaresList = ctCourseReappearCoursewaresMapper.
                                getInitInfo(coursewaresIdList, reappearId, schoolId);
                        if (CollectionUtils.isNotEmpty(coursewaresList)) {
                            ctCourseReappearCoursewaresMapper.batchInsert(coursewaresList);
                        }

                        List<String> rcIdList = TranslateCourseReappear.getRcIdList(coursewaresList);


                        //我的授课课件统计表+1
                        ctTeachCoursewaresStatisticsMapper.batchUpdateIncreaseReleases(coursewaresIdList, schoolId);
                        List<CtTeachCoursewaresContent> coursewaresContentList = ctTeachCoursewaresContentMapper
                                .selectByPrimaryIdList(coursewaresIdList, Constants.COURSEWARE_BELONG_TYPE_MY_APPEAR, schoolId);
                        if (CollectionUtils.isNotEmpty(coursewaresContentList)) {
                            List<CtTeachCoursewaresContent> insertContentList = TranslateCourseReappear.getContent(coursewaresContentList,
                                    Constants.COURSEWARE_BELONG_TYPE_COURSE_APPEAR, rcIdList);
                            logger.info("授课课件内容表inset记录");
                            ctTeachCoursewaresContentMapper.batchInsert(insertContentList);
                        }

                    }

                    if (classIdList != null) {
                        logger.info("课件发布班级inset记录");
                        ctCourseReappearClassMapper.batchInsert(classIdList, reappearId, schoolId);
                        List<CtCourseReappearStudent> studentList = ctCourseReappearStudentMapper.getInitInfo(classIdList, schoolId, reappearId);
                        int count = 0;
                        if (CollectionUtils.isNotEmpty(studentList)) {
                            logger.info("课件发布学生表inset记录");
                            count = ctCourseReappearStudentMapper.batchInsert(studentList);
                        }


                        //科目名称和课程名称
                        UnitBookNameView unitBookNameView = ctUnitsMapper.queryUnitBookNameByUnitId(unitId);

                        List<FileInfoView> fileInfoViewList =
                                ctCourseReappearClassMapper.getFileInfoViewList(reappearId, schoolId);

                        String fileJson = TranslateJson.getFileJson(fileInfoViewList);


                        int classNum = classIdList.size();
                        int workType = Constants.WORK_TYPE_COURSE_REAPPEAR;

                        CtWorkGather ctWorkGather = TranslateBlackboard.getCtWorkGather(classJson, count, classNum, reappearId, workItemNum,
                                workType, schoolId, fileJson, userId, unitBookNameView, ctCourseReappear.getContext(), publishTime, null);
                        logger.info("汇总表inset记录");
                        ctWorkGatherMapper.insert(ctWorkGather);

                        CtWorkStatistics ctWorkStatistics = TranslateBlackboard.getCtWorkStatistics(
                                reappearId, 0, schoolId);
                        logger.info("统计表inset记录");
                        ctWorkStatisticsMapper.insert(ctWorkStatistics);

                        if (CollectionUtils.isNotEmpty(studentList)) {
                            List<CtWorkStudentGather> studentGatherList = TranslateCourseReappear.getStudentGatherList(studentList,reappearId);
                            logger.info("学生汇总表inset记录");
                            ctWorkStudentGatherMapper.batchInsert(studentGatherList);
                        }

                        List<CtWorkClassGather> classGatherList = TranslateCourseReappear.getWorkClassGather(classIdList, reappearId, schoolId);
                        if (CollectionUtils.isNotEmpty(classGatherList)) {
                            logger.info("班级汇总表inset记录");
                            ctWorkClassGatherMapper.batchInsert(classGatherList);
                        }

                        List<String> studentIdList = TranslateCourseReappear.getStudentIdList(studentList);
                        String title = Constants.WARNVIEW_COURSEWARE_TITLE;
                        String content = CloudTeachNotifyContent.getWarnSubContent(userName, Constants.WORK_TYPE_COURSE_REAPPEAR);
                        new Thread(new SendNotify(cloudTeachService,studentIdList,title,content,userId,userIcon)).start();
                    }

                    if (null != workTapeFilesDTO) {
                        CtWorkTapeFiles workTapeFiles = new CtWorkTapeFiles();
                        BeanUtils.copyProperties(workTapeFilesDTO, workTapeFiles);
                        workTapeFiles.setFileId(UUID.randomUUID().toString().replace("-", ""));
                        workTapeFiles.setWorkId(reappearId);
                        workTapeFiles.setWorkType(Constants.WORK_TYPE_COURSE_REAPPEAR);
                        workTapeFiles.setUploadTime(new Date());
                        workTapeFiles.setSchoolId(schoolId);
                        workTapeFiles.setStatus(1);
                        logger.info("作业教师录音附件表inset记录");
                        ctWorkTapeFilesMapper.insert(workTapeFiles);
                    }

                }
            }
            else{
                //参数错误
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(), CloudTeachErrorEnum.PARAMERROR.getCode());
            }
        } catch (IOException e) {
            throw new CloudteachException("发布课件失败！", CloudTeachErrorEnum.WORK_ADD_FAILED.getCode());
        }
        return response;
    }

    @Override
    public PageResponse<CourseReappearDTO> getCourseReappearByUserId(CourseReappearPageRequest courseReappearPageRequest,String schoolId) {

        String userId = courseReappearPageRequest.getUserId();
        //获取总条数
        long count = ctCourseReappearMapper.selectCount(userId,schoolId);

        Page page = PageUtils.buildPage(courseReappearPageRequest);
        int pageSize = courseReappearPageRequest.getPageSize();
        List<CtCourseReappear> ctCourseReappearList = ctCourseReappearMapper.selectPage(pageSize, page, userId,schoolId);

        //返回PageResponse
        PageResponse<CourseReappearDTO> pageResponse = new PageResponse<CourseReappearDTO>();
        PageUtils.buldPageResponse(courseReappearPageRequest, pageResponse);
        pageResponse.setRows(TranslateCourseReappear.entityCourseReappearListToDtoList(ctCourseReappearList));
        pageResponse.setRecords(count);
        return pageResponse;
    }

    @Override
    public TeachCoursewaresContentDTO getTeachCoursewaresContentByRcId(String userId, String rcId,String reappearId,String schoolId) {
        CtTeachCoursewaresContent coursewaresContent = ctTeachCoursewaresContentMapper.selectByPrimaryKey(rcId,schoolId);
        if(coursewaresContent != null){
            CtCourseReappearStudent reappearStudent = TranslateCourseReappear.getCourseReappearStudent(userId);
            int num = ctCourseReappearStudentMapper.updateByRcIdAndUserId(reappearStudent,rcId,schoolId);
            if(num>0){
                int tryTimes = 1;
                int i = 0;
                while(i<=0 && tryTimes<=Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                    CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(reappearId, schoolId);
                    if(ctWorkStatistics==null){
                        throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                    }
                    int subStudentNum = ctWorkStatistics.getWorkSubStudentNum()+1;
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
        return TranslateCourseReappear.entityTeachCoursewaresContentToDto(coursewaresContent);
    }

    @Override
    public int checkCourseReappear(String userId, String rcId,String reappearId,String schoolId) {
        CtCourseReappearStudent reappearStudent = TranslateCourseReappear.getCourseReappearStudent(userId);
        int num = ctCourseReappearStudentMapper.updateByRcIdAndUserId(reappearStudent,rcId,schoolId);
        if(num>0){
            int tryTimes = 1;
            int i = 0;
            while(i<=0 && tryTimes<=Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(reappearId, schoolId);
                if(ctWorkStatistics==null){
                    throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
                }
                int subStudentNum = ctWorkStatistics.getWorkSubStudentNum()+1;
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
    public CourseReappearInfoDTO getCourseReappear(String reappearId,String schoolId){
        //课件信息
        List<CtTeachCoursewares> teachCoursewaresList = ctTeachCoursewaresMapper.selectByReappearId(schoolId, reappearId);
        CtCourseReappear ctCourseReappear = ctCourseReappearMapper.selectByPrimaryKey(reappearId,schoolId);

        CourseReappearInfoDTO infoDTO = new CourseReappearInfoDTO();

        List<TeachCoursewaresDTO> coursewaresDTOList = TranslateCourseReappear.entityCtTeachCoursewaresToDto(teachCoursewaresList);
        CourseReappearDTO reappearDTO = new CourseReappearDTO();
        BeanUtils.copyProperties(ctCourseReappear, reappearDTO);

        infoDTO.setCourseReappearDTO(reappearDTO);
        infoDTO.setTeachCoursewaresDTOList(coursewaresDTOList);

        return infoDTO;
    }

    @Override
    public int editCourseReappear(CourseReappearInfoDTO courseReappearInfoDTO) {
        //课件
        CtCourseReappear ctcoursereapper = TranslateCourseReappear.getExistCtCourseReappear(courseReappearInfoDTO.getCourseReappearDTO());
        String reappearId = ctcoursereapper.getReappearId();
        String schoolId = ctcoursereapper.getSchoolId();
        String userId = ctcoursereapper.getUserId();
        String unitId =ctcoursereapper.getUnitId();
        Date publishTime = ctcoursereapper.getPublishTime();
        String classJson = courseReappearInfoDTO.getClassJson();
        ctCourseReappearMapper.updateByPrimaryKey(ctcoursereapper);

        //课件发布课件表
        List<String> coursewaresIdList = courseReappearInfoDTO.getCoursewaresIdList();
        if(coursewaresIdList != null){
            List<CtCourseReappearCoursewares> oldCoursewaresList = ctCourseReappearCoursewaresMapper.queryCoursewaresListByReappearId(reappearId,schoolId);

            List<String> oldCoursewaresIdList = TranslateCourseReappear.getCoursewaresIdList(oldCoursewaresList);
            List<String> existCoursewaresIdList = new ArrayList<String>();
            List<String> delCoursewaresIdList = new ArrayList<String>();
            List<String> insertCoursewaresIdList = new ArrayList<String>();

            for(String coursewaresId : coursewaresIdList){
                if(oldCoursewaresIdList.contains(coursewaresId)){
                    existCoursewaresIdList.add(coursewaresId);
                }
                else{
                    insertCoursewaresIdList.add(coursewaresId);
                }
            }
            for(String coursewaresId : oldCoursewaresIdList){
                if(!coursewaresIdList.contains(coursewaresId)){
                    delCoursewaresIdList.add(coursewaresId);
                }
            }
            if(delCoursewaresIdList.size()>0){
                ctTeachCoursewaresStatisticsMapper.batchUpdateReduceReleases(delCoursewaresIdList,schoolId);
                ctCourseReappearCoursewaresMapper.updateBycoursewaresIdList(delCoursewaresIdList,schoolId);
            }

            if(insertCoursewaresIdList.size()>0){


                List<CtCourseReappearCoursewares> coursewaresList = ctCourseReappearCoursewaresMapper.
                        getInitInfo(insertCoursewaresIdList, reappearId, schoolId);
                ctCourseReappearCoursewaresMapper.batchInsert(coursewaresList);

                ctTeachCoursewaresStatisticsMapper.batchUpdateIncreaseReleases(insertCoursewaresIdList,schoolId);

                List<String> rcIdList = TranslateCourseReappear.getRcIdList(coursewaresList);


                //授课课件内容表
                //授课课件内容表
                List<CtTeachCoursewaresContent> coursewaresContentList = ctTeachCoursewaresContentMapper
                        .selectByPrimaryIdList(coursewaresIdList, Constants.COURSEWARE_BELONG_TYPE_MY_APPEAR, schoolId);

                if(CollectionUtils.isNotEmpty(coursewaresContentList)){

                    List<CtTeachCoursewaresContent> insertContentList = TranslateCourseReappear.getContent(coursewaresContentList,
                            Constants.COURSEWARE_BELONG_TYPE_COURSE_APPEAR,rcIdList);

                    ctTeachCoursewaresContentMapper.batchInsert(insertContentList);
                }

            }
        }
        //课件发布班级,学生
        //现在需要更新的班级列表
        List<String> classList = courseReappearInfoDTO.getClassIdList();
        if(classList != null){
            //获得当前数据库中的班级列表
            List<String> oldClassList = ctCourseReappearClassMapper.getClassList(reappearId,schoolId);

            //两者存在的班级列表
            List<String> existClassList = new ArrayList<String>();
            List<String> delClassList = new ArrayList<String>();
            List<String> insertClassList = new ArrayList<String>();
            for(String newClass : classList){
                if(oldClassList.contains(newClass)){
                    existClassList.add(newClass);
                }
                else{
                    insertClassList.add(newClass);
                }
            }
            for(String oldCLass : oldClassList){
                if(!classList.contains(oldCLass)){
                    delClassList.add(oldCLass);
                }
            }
            if(delClassList.size()>0 || insertClassList.size()>0){
                int subStudentNum = 0;
                if(existClassList.size()>0){
                    subStudentNum = ctCourseReappearStudentMapper.getStudentNumByClassIdList(existClassList,schoolId);
                }
                if(delClassList.size()>0){
                    ctCourseReappearClassMapper.batchUpdateByClassList(delClassList,schoolId);
                    ctCourseReappearStudentMapper.batchUpdateByClassList(delClassList,schoolId);
                    //班级汇总表
                    ctWorkClassGatherMapper.updateStatusByClassList(0, schoolId, delClassList);
                    //学生汇总表
                    ctWorkStudentGatherMapper.updateStatusByClassList(0, schoolId, delClassList);
                }

                if(insertClassList.size()>0){
                    //课件发布班级
                    ctCourseReappearClassMapper.batchInsert(insertClassList,reappearId,schoolId);
                    //课件发布学生表
                    List<CtCourseReappearStudent> studentList = ctCourseReappearStudentMapper.getInitInfo(insertClassList,schoolId,reappearId);

                    int count =0;
                    if(CollectionUtils.isNotEmpty(studentList)){
                        count = ctCourseReappearStudentMapper.batchInsert(studentList);
                    }

                    //学生汇总表
                    List<CtWorkStudentGather> studentGatherList = ctWorkStudentGatherMapper.getInitInfo(schoolId,classList,reappearId);
                    if(CollectionUtils.isNotEmpty(studentGatherList)){
                        ctWorkStudentGatherMapper.batchInsert(studentGatherList);
                    }


                    //班级汇总表
                    List<CtWorkClassGather> classGatherList = ctWorkClassGatherMapper.getInitInfo(schoolId,classList,reappearId);
                    if(CollectionUtils.isNotEmpty(classGatherList)){
                        ctWorkClassGatherMapper.batchInsert(classGatherList);
                    }


                }

                // 作业统计表ct_work_statistics相关

                int workItemNum = 0;
                if(coursewaresIdList != null)
                    workItemNum = coursewaresIdList.size();
                int studentNum = ctCourseReappearStudentMapper.getCountByReappearId(reappearId,schoolId);

                //科目名称和课程名称
                UnitBookNameView unitBookNameView = ctUnitsMapper.queryUnitBookNameByUnitId(unitId);

                List<FileInfoView> fileInfoViewList =
                        ctCourseReappearClassMapper.getFileInfoViewList(reappearId, schoolId);

                String fileJson = TranslateJson.getFileJson(fileInfoViewList);

                int classNum = classList.size();
                int workType = Constants.WORK_TYPE_COURSE_REAPPEAR;

                //汇总表
                CtWorkGather ctWorkGather = TranslateBlackboard.getCtWorkGather(classJson,studentNum , classNum, reappearId, workItemNum,
                        workType,schoolId, fileJson,userId, unitBookNameView,ctcoursereapper.getContext(),publishTime,null);
                ctWorkGatherMapper.updateByPrimaryKey(ctWorkGather);

                //统计表

                //统计表
                int tryTimes = 1;
                int i = 0;
                while(i<=0 && tryTimes<=Constants.OPTIMISTIC_LOCK_TRY_TIMES){
                    CtWorkStatistics ctWorkStatistics = ctWorkStatisticsMapper.selectByPrimaryKey(reappearId, schoolId);
                    if(ctWorkStatistics==null){
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
    public int delCourseReappear(String reappearId,String schoolId) {


        List<CtCourseReappearCoursewares> coursewaresList = ctCourseReappearCoursewaresMapper.queryCoursewaresListByReappearId(reappearId,schoolId);

        List<String> coursewaresIdList = TranslateCourseReappear.getCoursewaresIdList(coursewaresList);

        //我的授课课件统计表-1
        if(coursewaresIdList != null && coursewaresIdList.size()>0){
            ctTeachCoursewaresStatisticsMapper.batchUpdateReduceReleases(coursewaresIdList,schoolId);
        }
        ctCourseReappearMapper.updateByReappearId(reappearId, schoolId);
        ctCourseReappearClassMapper.updateByReappearId(reappearId, schoolId);
        ctCourseReappearCoursewaresMapper.updateByReappearId(reappearId, schoolId);
        ctCourseReappearStudentMapper.updateByReappearId(reappearId,schoolId);
        ctWorkGatherMapper.updateStatusByWorkId(0,reappearId, schoolId);
        ctWorkStudentGatherMapper.updateStatusByWorkId(0,reappearId, schoolId);
        ctWorkClassGatherMapper.updateStatusByWorkId(0, reappearId,schoolId);

        return 1;
    }
}
