package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.xuele.cloudteach.constant.ExtensionTypeMapperEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.TcExtFLShareDiskPageRequest;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;
import net.xuele.cloudteach.web.common.CloudTeachHTTPClientUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.page.PageResponse;
import net.xuele.member.dto.ClassDTO;
import net.xuele.member.dto.SchoolDTO;
import net.xuele.member.dto.StudentManagerDTO;
import net.xuele.member.service.ClassService;
import net.xuele.member.service.SchoolService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TeachCoursewaresExtFLController
 *
 * @author sunxh
 * @date 2015/7/22 0022
 */

@Controller
@RequestMapping("coursewares/extfl")
public class TeachCoursewaresExtFLController {

    @Value("${file.upload.url}")
    private String dlDomainName;

    @Autowired
    private TeachCoursewaresShareService teachCoursewaresShareService;

    @Autowired
    private TeachCoursewaresService teachCoursewaresService;

    @Autowired
    private CloudDiskService cloudDiskService;

    @Autowired
    private CloudTeachService cloudTeachService;

    @Autowired
    private TeacherWorkStuService teacherWorkStuService;

    @Autowired
    private TeacherWorkService teacherWorkService;

    @Autowired
    private ClassService classService;

    @Autowired
    private WorkStatisticsService workStatisticsService;

    @Autowired
    private CloudDiskShareService cloudDiskShareService;

    @Autowired
    private SchoolService schoolService;


    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresShareController.class);

    //保存课件
    @RequestMapping("save")
    @ResponseBody
    public FlashResponse save(@RequestParam("coursewaresId") String coursewaresId,
                              @RequestParam("coursewaresName") String coursewaresName,
                              @RequestParam("content") String content,
                              @RequestParam("userId") String userId,
                              @RequestParam("schoolId") String schoolId,
                              @RequestParam("unitId") String unitId) {
        logger.info("授课Flash-保存课件 : coursewaresId=" + coursewaresId + ";coursewaresName=" + coursewaresName
                + ";content=" + content + ";userId=" + userId + ";schoolId=" + schoolId + ";unitId=" + unitId);

        FlashResponse flashResponse = new FlashResponse();
        try {
            TeachCoursewaresDTO teachCoursewaresDTO = new TeachCoursewaresDTO();
            TeachCoursewaresContentDTO teachCoursewaresContentDTO = new TeachCoursewaresContentDTO();

            // TeachCoursewaresStatisticsDTO teachCoursewaresStatisticsDTO = new TeachCoursewaresStatisticsDTO();

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-保存课件参数解密 : coursewaresId=" + coursewaresId + ";coursewaresName=" + coursewaresName
                    + ";content=" + content + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId + ";unitId=" + unitId);

            teachCoursewaresDTO.setTitle(coursewaresName);
            teachCoursewaresDTO.setUserId(dUserId);
            teachCoursewaresDTO.setSchoolId(dSchoolId);
            teachCoursewaresDTO.setUnitId(unitId);

            teachCoursewaresContentDTO.setContent(content);
            teachCoursewaresContentDTO.setSchoolId(dSchoolId);

            if (coursewaresId == null || coursewaresId == "") {
                logger.info("Insert: " + coursewaresName + "; content : " + content + "; userid : " + userId
                        + "; schoolId : " + schoolId + "; unitId : " + unitId);

                coursewaresId = teachCoursewaresService.addTeachCoursewares(teachCoursewaresDTO, teachCoursewaresContentDTO);
            } else {
                logger.info("Update: id : " + coursewaresId + "; name : " + coursewaresName + "; content : "
                        + content + "; userid : " + userId + "; schoolId : " + schoolId + "; unitId : " + unitId);

                teachCoursewaresDTO.setCoursewaresId(coursewaresId);
                teachCoursewaresContentDTO.setCoursewaresId(coursewaresId);
                teachCoursewaresService.updateTeachCoursewares(teachCoursewaresDTO, teachCoursewaresContentDTO);
            }
            flashResponse.setState("1");
            flashResponse.setId(coursewaresId);

        } catch (Exception e) {
            flashResponse.setState("0");
            logger.error("授课Flash-保存课件出现错误 : " + e.getMessage(), e);
        }

        return flashResponse;
    }

    /**
     * 课件内容
     *
     * @param userId
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    @RequestMapping("getcoursewares")
    @ResponseBody
    public String getCourseWareContent(@RequestParam("userId") String userId,
                                       @RequestParam("schoolId") String schoolId,
                                       @RequestParam("coursewaresId") String coursewaresId) {

        logger.info("授课Flash-获取课件 : coursewaresId=" + coursewaresId + ";userId=" + userId + ";schoolId=" + schoolId);

        String content = "";
        try {
            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-获取课件参数解密 : coursewaresId=" + coursewaresId + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);


            CoursewaresResponseDTO coursewaresResponseDTO = teachCoursewaresService.queryMyTeachCourse(coursewaresId, dUserId, dSchoolId);

            content = coursewaresResponseDTO.getContent();
            if (content == null) {
                content = "";
            }

        } catch (Exception e) {
            logger.error("授课Flash-获取课件出现错误 : " + e.getMessage(), e);
        }

        return content;
    }

    /**
     * 获取课程内容
     *
     * @param unitId
     * @return
     */
    @RequestMapping("getunitmsg")
    @ResponseBody
    public TeachCoursewaresUnitMSGWrapper getCourseWareContent(@RequestParam("unitId") String unitId) {

        logger.info("获取课程内容 : unitId=" + unitId);

        TeachCoursewaresUnitMSGWrapper teachCoursewaresUnitMSGWrapper = new TeachCoursewaresUnitMSGWrapper();

        UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(unitId);
        if(unitsDTO!=null){
            teachCoursewaresUnitMSGWrapper.setUnitName(unitsDTO.getUnitName());
            BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(unitsDTO.getBookId());
            if(bookDTO!=null){
                teachCoursewaresUnitMSGWrapper.setSubjectId(bookDTO.getSubjectId());
                teachCoursewaresUnitMSGWrapper.setSubjectName(bookDTO.getSubjectName());
                teachCoursewaresUnitMSGWrapper.setGrade(bookDTO.getGrade());
                teachCoursewaresUnitMSGWrapper.setSemester(bookDTO.getSemester());
                teachCoursewaresUnitMSGWrapper.setSemesterDescribe(bookDTO.getSemesterDescribe());
            }
        }
        return teachCoursewaresUnitMSGWrapper;
    }

    /**
     * 预览分享的课件
     *
     * @param shareId
     * @return
     */
    @RequestMapping(value = "viewshare")
    @ResponseBody
    public CoursewaresResponseWrapper preview(@RequestParam("shareId") String shareId, @RequestParam("userId") String userId) {
        logger.info("授课Flash-预览分享的课件 : shareId=" + shareId + ";userId=" + userId);

        CoursewaresResponseWrapper coursewaresResponseWrapper = new CoursewaresResponseWrapper();
        try {

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密

            logger.info("授课Flash-预览分享的课件参数解密 : shareId=" + shareId + ";dUserId=" + dUserId);

            CoursewaresResponseDTO coursewaresResponseDTO = teachCoursewaresShareService.preview(shareId, dUserId);
            coursewaresResponseWrapper.setState("1");
            coursewaresResponseWrapper.setContent(coursewaresResponseDTO.getContent());
            coursewaresResponseWrapper.setUserId(coursewaresResponseDTO.getUserId());
            coursewaresResponseWrapper.setUnitId(coursewaresResponseDTO.getUnitId());
            coursewaresResponseWrapper.setCoursewaresName(coursewaresResponseDTO.getCoursewaresName());
            coursewaresResponseWrapper.setCoursewaresId(coursewaresResponseDTO.getCoursewaresId());
        } catch (Exception e) {
            coursewaresResponseWrapper.setState("0");
            logger.error("授课Flash-预览分享的课件出现错误 : " + e.getMessage(), e);
        }
        return coursewaresResponseWrapper;
    }

    //获取云盘资源
    @RequestMapping("disklist")
    @ResponseBody
    public CloudDiskResponseWrapper getCloudeDiskList(@RequestParam(value = "unitId") String unitId,
                                                      @RequestParam(value = "fileType", required = false) Integer fileType,
                                                      @RequestParam(value = "extType", required = false) Integer extType,
                                                      @RequestParam(value = "userId") String userId,
                                                      @RequestParam(value = "schoolId") String schoolId) {
        logger.info("授课Flash-获取云盘资源 : unitId=" + unitId + ";fileType=" + fileType + ";extType="
                + extType + ";userId=" + userId + ";schoolId=" + schoolId);

        CloudDiskResponseWrapper cloudDiskResponseWrapper = new CloudDiskResponseWrapper();
        try {

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-获取云盘资源参数解密 : unitId=" + unitId + ";fileType=" + fileType + ";extType="
                    + extType + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);

            CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
            cloudDiskDTO.setUnitId(unitId);
            cloudDiskDTO.setFileType(fileType);
            cloudDiskDTO.setExtType(extType);
            cloudDiskDTO.setUserId(dUserId);
            cloudDiskDTO.setSchoolId(dSchoolId);
            List<CloudDiskDTO> cloudDiskDTOList = cloudDiskService.queryMyFiles(cloudDiskDTO);

            /** 过滤未转换的云盘文件 */
            cloudDiskDTOList = filterDisk(cloudDiskDTOList);

            List<CloudDiskResponse> cloudDiskResponseList = new ArrayList<>();
            for (CloudDiskDTO dto : cloudDiskDTOList) {
                CloudDiskResponse cloudDiskResponse = new CloudDiskResponse();
                cloudDiskResponse.setFileType(dto.getFileType());
                cloudDiskResponse.setDiskId(dto.getDiskId());
                cloudDiskResponse.setExtension(dto.getExtension());
                cloudDiskResponse.setFileUri(dto.getFileUri());
                cloudDiskResponse.setName(dto.getName());
                cloudDiskResponse.setConvertStatus(dto.getConvertStatus());
                cloudDiskResponseList.add(cloudDiskResponse);
            }

            cloudDiskResponseWrapper.setState("1");
            cloudDiskResponseWrapper.setResources(cloudDiskResponseList);
        } catch (Exception e) {
            cloudDiskResponseWrapper.setState("0");
            logger.error("授课Flash-获取云盘资源出现错误 : " + e.getMessage(), e);
        }

        return cloudDiskResponseWrapper;
    }

    /**
     * @param request  【请求参数】
     * @return
     * 课件分页获取大家分享的云盘资源
     */
    @RequestMapping(value = "sharedisklist")
    @ResponseBody
    public PageResponse<TcExtFLCloudDiskShareWrapper> queryShareDiskList(TcExtFLShareDiskPageRequest request) {
        logger.info("课件分页获取大家分享的云盘资源:queryShareDiskList");

        PageResponse<TcExtFLCloudDiskShareWrapper> tcExtFLShareResponse = new PageResponse<>();

        if(StringUtils.isEmpty(request.getSchoolId())){
            logger.error("学校ID不能为空");
            return tcExtFLShareResponse;
        }
        if(StringUtils.isEmpty(request.getUnitId())){
            logger.error("课程ID不能为空");
            return tcExtFLShareResponse;
        }
        //解密
        request.setSchoolId(CloudTeachEncryptUtil.decrypt(request.getSchoolId()));

        SchoolDTO schoolDTO = schoolService.getBySchoolId(request.getSchoolId());
        if(schoolDTO == null){
            logger.error("学校ID："+request.getSchoolId()+"对应学校信息不存在");
            return tcExtFLShareResponse;
        }

        request.setAreaId(schoolDTO.getArea());

        PageResponse<TcExtFLCloudDiskShareDTO> pageResponse = cloudDiskShareService.queryTcExtFLSharedResource(request);

        /** 过滤未转换的分享云盘文件 */
        pageResponse.setRows(filterShareDisk(pageResponse.getRows()));

        List<TcExtFLCloudDiskShareWrapper> reslist = new ArrayList<>();

        //转换Wrapper
        for(TcExtFLCloudDiskShareDTO cdDTO : pageResponse.getRows()){
            TcExtFLCloudDiskShareWrapper wrapper = new TcExtFLCloudDiskShareWrapper();
            BeanUtils.copyProperties(cdDTO, wrapper);
            reslist.add(wrapper);
        }

        tcExtFLShareResponse.setRows(reslist);
        tcExtFLShareResponse.setRecords(pageResponse.getRecords());
        tcExtFLShareResponse.setPage(pageResponse.getPage());
        tcExtFLShareResponse.setPageSize(pageResponse.getPageSize());

        return tcExtFLShareResponse;
    }

    //获取教师所有授课班级
    @RequestMapping("getclasslist")
    @ResponseBody
    public TeacherResponseWrapper selectTeacherClassList(@RequestParam("userId") String userId,
                                                         @RequestParam("schoolId") String schoolId) {
        logger.info("授课Flash-获取教师信息 : userId=" + userId + ";schoolId=" + schoolId);

        TeacherResponseWrapper teacherResponseWrapper = new TeacherResponseWrapper();
        List<TeacherResponse> teacherResponseList = new ArrayList<>();
        try {

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
//            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-获取教师信息 : dUserId=" + dUserId + ";schoolId=" + schoolId);


            List<ClassInfoDTO> classInfoDTOList = cloudTeachService.selectTeacherClassList(dUserId);
            for (ClassInfoDTO classInfoDTO : classInfoDTOList) {
                TeacherResponse teacherResponse = new TeacherResponse();
                teacherResponse.setCodeSharing("");
                teacherResponse.setClassName(classInfoDTO.getClassName());
                teacherResponse.setClassId(classInfoDTO.getClassId());
                teacherResponse.setYear(classInfoDTO.getYear());
                teacherResponseList.add(teacherResponse);
            }
            teacherResponseWrapper.setState("1");
            teacherResponseWrapper.setList(teacherResponseList);
        } catch (Exception e) {
            teacherResponseWrapper.setState("0");
            logger.error("授课Flash-获取教师信息出现错误 : " + e.getMessage(), e);
        }

        return teacherResponseWrapper;
    }

    //获取课件对应年级下教师授课班级
    @RequestMapping("getgradeclasslist")
    @ResponseBody
    public TeacherResponseWrapper selectTeacherGradeClassList(@RequestParam("userId") String userId,
                                                              @RequestParam("unitId") String unitId) {
        logger.info("授课Flash-获取课件对应年级下教师授课班级 : userId=" + userId + ";unitId=" + unitId);

        TeacherResponseWrapper teacherResponseWrapper = new TeacherResponseWrapper();
        teacherResponseWrapper.setState("0");
        List<TeacherResponse> teacherResponseList = new ArrayList<>();
        try {

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密

            UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(unitId);
            if(unitsDTO!=null){
                BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(unitsDTO.getBookId());
                if(bookDTO!=null){
                    //根据老师用户ID、课本对应年级获取教师授课班级列表信息
                    List<ClassInfoDTO> classInfoDTOList = cloudTeachService.selectTeacherGradeClassList(dUserId, bookDTO.getGrade().intValue());
                    for (ClassInfoDTO classInfoDTO : classInfoDTOList) {
                        TeacherResponse teacherResponse = new TeacherResponse();
                        teacherResponse.setCodeSharing("");
                        teacherResponse.setClassName(classInfoDTO.getClassName());
                        teacherResponse.setClassId(classInfoDTO.getClassId());
                        teacherResponse.setYear(classInfoDTO.getYear());
                        teacherResponseList.add(teacherResponse);
                    }
                    teacherResponseWrapper.setState("1");
                    teacherResponseWrapper.setList(teacherResponseList);
                }
            }
        } catch (Exception e) {
            teacherResponseWrapper.setState("0");
            logger.error("授课Flash-获取课件对应年级下教师授课班级 : " + e.getMessage(), e);
        }

        return teacherResponseWrapper;
    }

    //获取某班学生信息
    @RequestMapping("getstudentlist")
    @ResponseBody
    public StudentResponseWrapper selectTeacherClassList(@RequestParam("classId") String classId) {
        logger.info("授课Flash-获取某班学生列表 : classId=" + classId );

        StudentResponseWrapper studentResponseWrapper = new StudentResponseWrapper();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        try {

            List<StudentManagerDTO> studentManagerDTOList = cloudTeachService.selectClassStudentList(classId);
            for (StudentManagerDTO studentManagerDTO : studentManagerDTOList) {
                StudentResponse studentResponse = new StudentResponse();
                studentResponse.setStudentName(studentManagerDTO.getRealName());
                studentResponse.setStudentId(studentManagerDTO.getUserId());
                studentResponse.setIconUrl(studentManagerDTO.getIcon());
                studentResponseList.add(studentResponse);
            }
            studentResponseWrapper.setState("1");
            studentResponseWrapper.setList(studentResponseList);
        } catch (Exception e) {
            studentResponseWrapper.setState("0");
            logger.error("授课Flash-获取某班学生列表出现错误 : " + e.getMessage(), e);
        }
        return studentResponseWrapper;
    }

    // 通过课程ID，教师ID，作业类型获取作业列表
    @RequestMapping("worklist")
    @ResponseBody
    public FLResponse<FLTeacherWorkWrapper> workList(
            @RequestParam("schoolId") String schoolId,
            @RequestParam("unitId") String unitId,
            @RequestParam("userId") String userId,
            @RequestParam("workType") Integer workType) {
        logger.info("授课Flash-获取作业列表 : schoolId=" + schoolId + ";unitId=" + unitId + ";userId=" + userId + ";workType=" + workType);

        FLResponse<FLTeacherWorkWrapper> teacherWorkWrapperFLResponse = new FLResponse<>();
        List<FLTeacherWorkWrapper> workWrapperList = new ArrayList<>();

        try {

//            String dUserId = userId;
//            String dSchoolId = schoolId;
            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("获取作业列表 : dSchoolId=" + dSchoolId + ";unitId=" + unitId + ";dUserId=" + dUserId + ";workType=" + workType);

            List<CoursewareTeacherWorkViewDTO> cwtList = teacherWorkService.queryCoursewareTeacherWorkList(dSchoolId, unitId, dUserId, workType);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            /** 生成作业列表 */
            if (CollectionUtils.isNotEmpty(cwtList)) {
                for (CoursewareTeacherWorkViewDTO ctWork : cwtList) {
                    FLTeacherWorkWrapper workWrapper = new FLTeacherWorkWrapper();
                    workWrapper.setWorkId(ctWork.getWorkId());
                    workWrapper.setWorkType(ctWork.getWorkType());
                    workWrapper.setPublishTime(dateFormat.format(ctWork.getPublishTime()));

                    /** 生成题目列表 */
                    List<FLBankItemWrapper> itemWrappers = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(ctWork.getWorkItemList())) {
                        for (TeacherWorkItemViewDTO item : ctWork.getWorkItemList()) {

                            FLBankItemWrapper itemWrapper = new FLBankItemWrapper();
                            itemWrapper.setItemId(item.getItemId());
                            itemWrapper.setItemType(item.getItemType());
                            itemWrapper.setContext(item.getContext());
                            itemWrapper.setVoiceContext(item.getVoiceContext());

                            /** 生成附件列表 */
                            List<FLFileReferWrapper> fileReferWrappers = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(item.getItemFilesList())) {
                                for (BankItemFilesViewDTO file : item.getItemFilesList()) {
                                    FLFileReferWrapper fileReferWrapper = new FLFileReferWrapper();
                                    fileReferWrapper.setDiskId(file.getDiskId());
                                    fileReferWrapper.setFileUri(file.getFileUri());
                                    fileReferWrapper.setName(file.getName());
                                    fileReferWrapper.setExtension(file.getExtension());
                                    fileReferWrappers.add(fileReferWrapper);
                                }
                            }
                            itemWrapper.setFileList(fileReferWrappers);
                            /** 生成附件列表 end */

                            itemWrappers.add(itemWrapper);
                        }
                        /** 生成题目列表 end */
                    }

                    workWrapper.setItemList(itemWrappers);
                    workWrapperList.add(workWrapper);
                }
            }
            /** 生成作业列表 end */
            teacherWorkWrapperFLResponse.setList(workWrapperList);
            teacherWorkWrapperFLResponse.setState("1");
        } catch (Exception e) {
            teacherWorkWrapperFLResponse.setState("0");
            logger.error("授课Flash-获取作业列表出现错误 : " + e.getMessage(), e);
        }
        return teacherWorkWrapperFLResponse;
    }

    /**
     * 获取作业关联的班级列表
     *
     * @param workId   作业ID
     * @param userId   教师用户ID【加密】
     * @param schoolId 学校ID【加密】
     * @return FLResponse<FLClassWrapper>  作业对应的班级列表
     */
    @RequestMapping("getworkclasslist")
    @ResponseBody
    public FLResponse<FLClassWrapper> getWorkClassList(
            @RequestParam("workId") String workId,
            @RequestParam("userId") String userId,
            @RequestParam("schoolId") String schoolId) {
        logger.info("授课Flash-获取作业关联的班级列表 : workId=" + workId + ";userId=" + userId + ";schoolId=" + schoolId);

        FLResponse<FLClassWrapper> classWrapperFLResponse = new FLResponse<>();
        List<FLClassWrapper> classWrappers = new ArrayList<>();

        try {

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-获取作业关联的班级列表 : workId=" + workId + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);

            /** 获取作业对应的班级列表 */
            List<WorkClassViewDTO> classViewDTOs = teacherWorkService.queryTeacherWorkClassList(workId, dSchoolId);

            for (WorkClassViewDTO classViewDTO : classViewDTOs) {

                FLClassWrapper clazz = new FLClassWrapper();
                clazz.setClassId(classViewDTO.getClassId());                //班级ID
                clazz.setClassName(classViewDTO.getClassName());            //班级名称
                clazz.setYear(classViewDTO.getYears());                     //学届
                clazz.setCodeSharing(classViewDTO.getCodeSharing());        //班级号,根据当前年级递增生成

                ClassDTO classDTO = classService.getByClassId(clazz.getClassId());
                clazz.setmImage(classDTO.getmImage());

                classWrappers.add(clazz);
            }

            classWrapperFLResponse.setList(classWrappers);
            classWrapperFLResponse.setState("1");

        } catch (Exception e) {

            classWrapperFLResponse.setState("0");
            logger.error("授课Flash-获取作业关联的班级列表出现错误 : " + e.getMessage(), e);
        }

        return classWrapperFLResponse;
    }

    /**
     * 获取某个作业对应的具体班级的学生列表
     *
     * @param classId  班级ID
     * @param workId   作业ID _备用
     * @param userId   教师UserID【加密】 _备用
     * @param schoolId 学校ID【加密】  _备用
     * @return
     */
    @RequestMapping("getworkstudentlist")
    @ResponseBody
    public StudentResponseWrapper getWorkStudentList(
            @RequestParam("classId") String classId,
            @RequestParam(value = "workId", required = false) String workId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "schoolId", required = false) String schoolId) {
        logger.info("授课Flash-获取某个作业对应的具体班级的学生列表 : classId=" + classId + ";workId=" + workId
                + ";userId=" + userId + ";schoolId=" + schoolId);

        StudentResponseWrapper studentResponseWrapper = new StudentResponseWrapper();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        try {


            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-获取某个作业对应的具体班级的学生列表参数解密 : classId=" + classId + ";workId=" + workId
                    + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);

            //获取学生完成作业情况列表并组装map
            List<WorkStudentGatherDTO> studentGatherDTOList =  workStatisticsService.queryStuFinishedState(workId, classId, dSchoolId);

            Map<String,Integer> stuFinishMap = new HashMap<>();
            for (WorkStudentGatherDTO dto : studentGatherDTOList) {
                stuFinishMap.put(dto.getStudentId(),dto.getSubStatus());
            }

            List<StudentManagerDTO> studentManagerDTOList = cloudTeachService.selectClassStudentList(classId);
            for (StudentManagerDTO studentManagerDTO : studentManagerDTOList) {
                StudentResponse studentResponse = new StudentResponse();
                studentResponse.setStudentName(studentManagerDTO.getRealName());
                studentResponse.setStudentId(studentManagerDTO.getUserId());

                //包装学生完成该作业的状态，如果获取不到状态，置为0
                studentResponse.setSubStatus(stuFinishMap.get(studentResponse.getStudentId()) == null ? 0 : stuFinishMap.get(studentResponse.getStudentId()));

                studentResponseList.add(studentResponse);
            }
            studentResponseWrapper.setState("1");
            studentResponseWrapper.setList(studentResponseList);
        } catch (Exception e) {
            studentResponseWrapper.setState("0");
            logger.error("授课Flash-获取某个作业对应的具体班级的学生列表出现错误 : " + e.getMessage(), e);
        }
        return studentResponseWrapper;
    }

    /**
     * 获取某个学生的作业回答详情
     *
     * @param workId   作业ID
     * @param userId   学生用户ID
     * @param schoolId 学校ID
     * @return
     */
    @RequestMapping("getworkfinishdetail")
    @ResponseBody
    public FLStuWorkDetailWrapper getWorkFinishDetail(
            @RequestParam("workId") String workId,
            @RequestParam("userId") String userId,
            @RequestParam("schoolId") String schoolId) {
        logger.info("授课Flash-获取某个学生的作业回答详情 : workId=" + workId + ";userId=" + userId + ";schoolId=" + schoolId);

        String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
        String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

        logger.info("授课Flash-获取某个学生的作业回答详情参数解密 : workId=" + workId + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);

        /** 包装学生回答详情：context，files */
        FLStuWorkDetailWrapper stuWorkDetail = new FLStuWorkDetailWrapper(); //学生回答对象
        List<FLFileReferWrapper> files = new ArrayList<>();

        try {


            // 获取完成的作业信息：
            TeacherWorkFinishDetailViewDTO workFinishDetail = teacherWorkStuService.teacherWorkFinishDetail(workId, dUserId, dSchoolId);
            // 获取学生回答信息
            TeacherWorkItemAnswerDTO stuWorkAnswer = workFinishDetail.getTeacherWorkItemAnswerDTO();
            stuWorkDetail.setContext(stuWorkAnswer.getContext());

            // 获取学生添加附件信息
            List<TeacherWorkItemAnswerFileDTO> stuWorkAnswerSubFileList = workFinishDetail.getCtTeacherWorkItemAnswerFileList();
            for (TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO : stuWorkAnswerSubFileList) {
                FLFileReferWrapper file = new FLFileReferWrapper();
                file.setFileUri(teacherWorkItemAnswerFileDTO.getUrl());             //fileKey
                file.setName(teacherWorkItemAnswerFileDTO.getFileName());           //fileName
                file.setExtension(teacherWorkItemAnswerFileDTO.getExtension());     //文件扩展名
                files.add(file);
            }

            stuWorkDetail.setList(files);
            stuWorkDetail.setState("1");

        } catch (Exception e) {
            stuWorkDetail.setState("0");
            logger.error("授课Flash-获取某个学生的作业回答详情出现错误 : " + e.getMessage(), e);
        }

        return stuWorkDetail;

    }


//  =====================================合并的两个接口 added by sunxh @1509141038========================================

    /**
     * 获取教师某个课程下的作业信息（包括作业发布班级信息）
     * @param schoolId 学校ID
     * @param unitId   课程ID
     * @param userId   教师ID
     * @param workType 0预习&电子&口语 1预习 4电子作业 7口语作业
     * @param classId 班级ID
     * @return
     */
    @RequestMapping("workswithclass")
    @ResponseBody
    public FLResponse<FLTeacherWorkWrapper> workswithclass(
            @RequestParam("schoolId") String schoolId,
            @RequestParam("unitId") String unitId,
            @RequestParam("userId") String userId,
            @RequestParam("workType") Integer workType,
            @RequestParam("classId") String classId) {
        logger.info("授课Flash-获取作业列表 : schoolId=" + schoolId + ";unitId=" + unitId + ";userId=" + userId + ";workType=" + workType+";classId="+classId);

        FLResponse<FLTeacherWorkWrapper> teacherWorkWrapperFLResponse = new FLResponse<>();
        List<FLTeacherWorkWrapper> workWrapperList = new ArrayList<>();

        try {

            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("获取作业列表 : dSchoolId=" + dSchoolId + ";unitId=" + unitId + ";dUserId=" + dUserId + ";workType=" + workType);

            List<CoursewareTeacherWorkViewDTO> cwtList = teacherWorkService.queryCoursewareTeacherWorkList(dSchoolId, unitId, dUserId, workType);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            /** 生成作业列表 */
            if (CollectionUtils.isNotEmpty(cwtList)) {
                for (CoursewareTeacherWorkViewDTO ctWork : cwtList) {
                    if(ctWork.getWorkClassJson().indexOf(classId) < 0){
                        continue;
                    }
                    FLTeacherWorkWrapper workWrapper = new FLTeacherWorkWrapper();
                    workWrapper.setWorkId(ctWork.getWorkId());
                    workWrapper.setWorkType(ctWork.getWorkType());
                    workWrapper.setPublishTime(dateFormat.format(ctWork.getPublishTime()));

                    /** 获取作业对应的班级列表 */
                    List<FLClassWrapper> classWrappers = new ArrayList<>();
                    List<WorkClassViewDTO> classViewDTOs = teacherWorkService.queryTeacherWorkClassList(ctWork.getWorkId(), dSchoolId);

                    for (WorkClassViewDTO classViewDTO : classViewDTOs) {

                        FLClassWrapper clazz = new FLClassWrapper();
                        clazz.setClassId(classViewDTO.getClassId());                //班级ID
                        clazz.setClassName(classViewDTO.getClassName());            //班级名称
                        clazz.setYear(classViewDTO.getYears());                     //学届
                        clazz.setCodeSharing(classViewDTO.getCodeSharing());        //班级号,根据当前年级递增生成

                        ClassDTO classDTO = classService.getByClassId(clazz.getClassId());
                        clazz.setmImage(classDTO.getmImage());

                        classWrappers.add(clazz);
                    }

                    workWrapper.setClassList(classWrappers);
                    /** 获取作业对应的班级列表 end */

                    /** 生成题目列表 */
                    List<FLBankItemWrapper> itemWrappers = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(ctWork.getWorkItemList())) {
                        for (TeacherWorkItemViewDTO item : ctWork.getWorkItemList()) {

                            FLBankItemWrapper itemWrapper = new FLBankItemWrapper();
                            itemWrapper.setItemId(item.getItemId());
                            itemWrapper.setItemType(ctWork.getWorkType());
                            itemWrapper.setContext(item.getContext());
                            itemWrapper.setVoiceContext(item.getVoiceContext());

                            /** 生成附件列表 */
                            List<FLFileReferWrapper> fileReferWrappers = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(item.getItemFilesList())) {
                                for (BankItemFilesViewDTO file : item.getItemFilesList()) {
                                    FLFileReferWrapper fileReferWrapper = new FLFileReferWrapper();
                                    fileReferWrapper.setDiskId(file.getDiskId());
                                    fileReferWrapper.setFileUri(file.getFileUri());
                                    fileReferWrapper.setName(file.getName());
                                    fileReferWrapper.setExtension(file.getExtension());
                                    fileReferWrappers.add(fileReferWrapper);
                                }
                            }
                            itemWrapper.setFileList(fileReferWrappers);
                            /** 生成附件列表 end */

                            itemWrappers.add(itemWrapper);
                        }
                        /** 生成题目列表 end */
                    }

                    workWrapper.setItemList(itemWrappers);
                    workWrapperList.add(workWrapper);
                }
            }
            /** 生成作业列表 end */
            teacherWorkWrapperFLResponse.setList(workWrapperList);
            teacherWorkWrapperFLResponse.setState("1");
        } catch (Exception e) {
            teacherWorkWrapperFLResponse.setState("0");
            logger.error("授课Flash-获取作业列表出现错误 : " + e.getMessage(), e);
        }
        return teacherWorkWrapperFLResponse;
    }


    /**
     * 获取某个作业对应的具体班级的学生列表
     *
     * @param classId  班级ID
     * @param workId   作业ID
     * @param userId   教师UserID【加密】
     * @param schoolId 学校ID【加密】
     * @return
     */
    @RequestMapping("studentswithworkdetail")
    @ResponseBody
    public FLResponse<FLStudentWithWorkWrapper> studentswithworkdetail(
            @RequestParam("classId") String classId,
            @RequestParam(value = "workId", required = false) String workId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "schoolId", required = false) String schoolId) {
        logger.info("授课Flash-获取某个作业对应的具体班级的学生列表 : classId=" + classId + ";workId=" + workId
                + ";userId=" + userId + ";schoolId=" + schoolId);

        FLResponse<FLStudentWithWorkWrapper> studentResponseWrapper = new FLResponse<FLStudentWithWorkWrapper>();
        List<FLStudentWithWorkWrapper> studentResponseList = new ArrayList<>();
        try {


            String dUserId = CloudTeachEncryptUtil.decrypt(userId);     //用户id字段解密
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId); //学校id字段解密

            logger.info("授课Flash-获取某个作业对应的具体班级的学生列表参数解密 : classId=" + classId + ";workId=" + workId
                    + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);

            //获取学生完成作业情况列表并组装map
            List<WorkStudentGatherDTO> studentGatherDTOList =  workStatisticsService.queryStuFinishedState(workId, classId, dSchoolId);

            Map<String,Integer> stuFinishMap = new HashMap<>();
            for (WorkStudentGatherDTO dto : studentGatherDTOList) {
                stuFinishMap.put(dto.getStudentId(),dto.getSubStatus());
            }

            List<StudentManagerDTO> studentManagerDTOList = cloudTeachService.selectClassStudentList(classId);
            for (StudentManagerDTO studentManagerDTO : studentManagerDTOList) {
                FLStudentWithWorkWrapper studentResponse = new FLStudentWithWorkWrapper();
                studentResponse.setStudentName(studentManagerDTO.getRealName());
                studentResponse.setStudentId(studentManagerDTO.getUserId());
                studentResponse.setStudentIcon(studentManagerDTO.getIcon());

                //包装学生完成该作业的状态，如果获取不到状态，置为0
                studentResponse.setSubStatus(stuFinishMap.get(studentResponse.getStudentId()) == null ? 0 : stuFinishMap.get(studentResponse.getStudentId()));

                if(studentResponse.getSubStatus()==1){
                    /** 包装学生回答详情：context，files */
                    List<FLFileReferWrapper> files = new ArrayList<>();

                    // 获取完成的作业信息：
                    TeacherWorkFinishDetailViewDTO workFinishDetail = teacherWorkStuService.teacherWorkFinishDetail(workId, studentResponse.getStudentId(), dSchoolId);
                    // 获取学生回答信息
                    TeacherWorkItemAnswerDTO stuWorkAnswer = workFinishDetail.getTeacherWorkItemAnswerDTO();
                    studentResponse.setContext(stuWorkAnswer.getContext());

                    // 获取学生添加附件信息
                    List<TeacherWorkItemAnswerFileDTO> stuWorkAnswerSubFileList = workFinishDetail.getCtTeacherWorkItemAnswerFileList();
                    for (TeacherWorkItemAnswerFileDTO teacherWorkItemAnswerFileDTO : stuWorkAnswerSubFileList) {

                        FLFileReferWrapper file = new FLFileReferWrapper();

                        //验证文件是否转换成功--start
                        List<CloudDiskDTO> cloudDiskDTOlist = new ArrayList<>();
                        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
                        cloudDiskDTO.setFileUri(teacherWorkItemAnswerFileDTO.getAnswerFileId());
                        cloudDiskDTO.setFileUri(teacherWorkItemAnswerFileDTO.getFileName());
                        cloudDiskDTO.setFileUri(teacherWorkItemAnswerFileDTO.getUrl());
                        cloudDiskDTO.setExtension(teacherWorkItemAnswerFileDTO.getExtension());
                        cloudDiskDTOlist.add(cloudDiskDTO);
                        cloudDiskDTOlist = filterDisk(cloudDiskDTOlist);

                        if(cloudDiskDTOlist==null || cloudDiskDTOlist.size()<1){
                            file.setConvertStatus(0);//文件转换未成功
                        }else{
                            file.setConvertStatus(1);//文件转换成功
                        }
                        //验证文件是否转换成功--end

                        file.setFileUri(teacherWorkItemAnswerFileDTO.getUrl());             //fileKey
                        file.setName(teacherWorkItemAnswerFileDTO.getFileName());           //fileName
                        file.setExtension(teacherWorkItemAnswerFileDTO.getExtension());     //文件扩展名
                        files.add(file);
                    }

                    studentResponse.setFileList(files);
                }

                studentResponseList.add(studentResponse);
            }
            //排序，将已提交作业学生排前面
            List<FLStudentWithWorkWrapper> subList = new ArrayList<>();
            List<FLStudentWithWorkWrapper> unSubList = new ArrayList<>();
            for(FLStudentWithWorkWrapper fLStudentWithWorkWrapper :studentResponseList){
                if(fLStudentWithWorkWrapper.getSubStatus().intValue()==1){
                    subList.add(fLStudentWithWorkWrapper);
                }else{
                    unSubList.add(fLStudentWithWorkWrapper);
                }
            }
            List<FLStudentWithWorkWrapper> resList = new ArrayList<>();
            resList.addAll(subList);
            resList.addAll(unSubList);
            studentResponseWrapper.setState("1");
            studentResponseWrapper.setList(resList);
        } catch (Exception e) {
            studentResponseWrapper.setState("0");
            logger.error("授课Flash-获取某个作业对应的具体班级的学生列表出现错误 : " + e.getMessage(), e);
        }
        return studentResponseWrapper;
    }



//  =================================================合并的两个接口 end===================================================


    /**
     * 根据教师用户ID获取某个课程相关的所有学生信息
     * @param userId
     * @param unitId
     * @return
     */
    @RequestMapping("myStuList")
    @ResponseBody
    public PCGradeStudentWrapper getMyStuList(@RequestParam("userId") String userId, @RequestParam("unitId") String unitId) {

        UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(unitId);

        String bookId = unitsDTO.getBookId();

        logger.info("授课神器-获取我的学生 : userId=" + userId + ";bookId=" + bookId);

        PCGradeStudentWrapper PCGradeStudentWrapper = new PCGradeStudentWrapper();
        List<PCClassWrapper> classWrappers = new ArrayList<>();

        if (userId == null) {
            userId = "";
        }

        String dUserId = CloudTeachEncryptUtil.decrypt(userId);

        logger.info("授课神器-获取我的学生参数解密 : dUserId=" + dUserId + ";bookId=" + bookId);

        try {
            BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(bookId);

            List<ClassInfoDTO> classList = cloudTeachService.selectTeacherGradeClassList(dUserId, bookDTO.getGrade());

            /** 带学生的班级列表 */
            for (ClassInfoDTO classInfoDTO : classList) {
                /** 班级信息 */
                PCClassWrapper classWrapper = new PCClassWrapper();
                classWrapper.setClassID(classInfoDTO.getClassId());         //班级ID
                classWrapper.setClassName(classInfoDTO.getClassName());     //班级名称

                if (classInfoDTO != null && classInfoDTO.getClassId() != null) {
                    /** 包装班级学生信息 */
                    List<PCStudentWrapper> studentWrappers = new ArrayList<>();

                    List<StudentManagerDTO> classStudentList = cloudTeachService.selectClassStudentList(classInfoDTO.getClassId());
                    for (StudentManagerDTO studentManagerDTO : classStudentList) {
                        PCStudentWrapper student = new PCStudentWrapper();
                        student.setStudentID(studentManagerDTO.getUserId());        //学生ID
                        student.setStudentName(studentManagerDTO.getRealName());    //学生姓名
                        student.setIconURI(studentManagerDTO.getIcon());            //学生头像
                        studentWrappers.add(student);
                    }

                    classWrapper.setStudents(studentWrappers);
                }
                classWrappers.add(classWrapper);
            }

            PCGradeStudentWrapper.setState("1");
            PCGradeStudentWrapper.setList(classWrappers);
        } catch (Exception e) {
            PCGradeStudentWrapper.setState("-1");
            logger.error("授课神器-获取我的学生出现错误 : " + e.getMessage(), e);
        }

        return PCGradeStudentWrapper;
    }


//    <<=============================================私有方法==============================================>>

    /**
     * 标记出未转换完成的disk资源（转换状态改为未成功）
     *
     * @param list
     * @return
     */
    private List<CloudDiskDTO> filterDisk(List<CloudDiskDTO> list) {
        CloudTeachHTTPClientUtil httpClientUtil = new CloudTeachHTTPClientUtil();

        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<>();

        /** 循环云盘列表 组装请求数据 */
        logger.info("组装查询转换状态参数-- start");

        Iterator<CloudDiskDTO> it = list.iterator();
        while (it.hasNext()) {
            CloudDiskDTO value = it.next();
            if (value.getFileUri() == null || value.getFileUri().trim().equals("")) {
                it.remove();//FileURI不合法的 删除
            } else if (value.getExtension() == null || value.getExtension().equals("")) {
                it.remove();//扩展名不合法的   删除
            } else if (null == ExtensionTypeMapperEnum.needExchange(value.getExtension())) {
                it.remove();//是否需转换的状态不正确的 一般是非法扩展名  删除
            } else if (ExtensionTypeMapperEnum.needExchange(value.getExtension())) {
                String prefix = ExtensionTypeMapperEnum.getPrefix(value.getExtension());
                //如果有关联预览的前缀，并且需要转换，则添加到查询列表
                if (prefix != null && (!prefix.trim().equals(""))) {
                    formparams.add(new BasicNameValuePair("filekeys", value.getFileUri() + "_" + prefix));
                    logger.info("查询转换状态：" + value.getFileUri() + "_" + prefix + ";");
                }
            }
        }

        logger.info("组装查询转换状态参数-- end");

        try {
            if (CollectionUtils.isNotEmpty(formparams)) {
                String fileCheckUrl = dlDomainName + "/upload/ExistFiles";
                String r = httpClientUtil.post(formparams, fileCheckUrl);
                logger.info("来自" + fileCheckUrl + "的文件转换状态查询结果：" + r);

                ObjectMapper mapper = new ObjectMapper();

                DiskExistListWrapper existList = mapper.readValue(r, DiskExistListWrapper.class);
                Map<String, Boolean> existMap = existList.getMap();

                //循环云盘列表 删除未转换的

                it = list.iterator();
                while (it.hasNext()) {
                    CloudDiskDTO value = it.next();
                    Boolean exsit = false;
                    try {
                        //不需要转换或者转换成功的 exsit=true
                        exsit = (!ExtensionTypeMapperEnum.needExchange(value.getExtension())) || existMap.get(value.getFileUri());
                    } catch (Exception e) {
                        logger.error("判断文件是否存在发生异常：",e);
                        exsit = false;
                    }
                    if (!exsit) {
                        logger.info("移除未转换完成的disk——------" + value.getDiskId() + ":" + value.getFileUri() + ":" + value.getName());
                        //it.remove();
                        value.setConvertStatus(0);
                    }
                }

                logger.info("过滤后的结果----" + list.toString());
            } else {
                logger.info("没有需要验证转换状态的资源，过滤后的结果----" + list.toString());
            }

        } catch (IOException e) {
            logger.error("IO异常：",e);
        }

        return list;
    }

    /**
     * 标记出未转换完成的sharedisk（转换状态改为未成功）
     *
     * @param list
     * @return
     */
    private List<TcExtFLCloudDiskShareDTO> filterShareDisk(List<TcExtFLCloudDiskShareDTO> list) {
        CloudTeachHTTPClientUtil httpClientUtil = new CloudTeachHTTPClientUtil();

        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<>();

        /** 循环分享云盘列表 组装请求数据 */
        logger.info("组装查询转换状态参数-- start");

        Iterator<TcExtFLCloudDiskShareDTO> it = list.iterator();
        while (it.hasNext()) {
            TcExtFLCloudDiskShareDTO value = it.next();
            if (value.getFileUri() == null || value.getFileUri().trim().equals("")) {
                it.remove();//FileURI不合法的 删除
            } else if (value.getExtention() == null || value.getExtention().equals("")) {
                it.remove();//扩展名不合法的   删除
            } else if (null == ExtensionTypeMapperEnum.needExchange(value.getExtention())) {
                it.remove();//是否需转换的状态不正确的 一般是非法扩展名  删除
            } else if (ExtensionTypeMapperEnum.needExchange(value.getExtention())) {
                String prefix = ExtensionTypeMapperEnum.getPrefix(value.getExtention());
                //如果有关联预览的前缀，并且需要转换，则添加到查询列表
                if (prefix != null && (!prefix.trim().equals(""))) {
                    formparams.add(new BasicNameValuePair("filekeys", value.getFileUri() + "_" + prefix));
                    logger.info("查询转换状态：" + value.getFileUri() + "_" + prefix + ";");
                }
            }
        }

        logger.info("组装查询转换状态参数-- end");

        try {
            if (CollectionUtils.isNotEmpty(formparams)) {
                String fileCheckUrl = dlDomainName + "/upload/ExistFiles";
                String r = httpClientUtil.post(formparams, fileCheckUrl);
                logger.info("来自" + fileCheckUrl + "的文件转换状态查询结果：" + r);

                ObjectMapper mapper = new ObjectMapper();

                DiskExistListWrapper existList = mapper.readValue(r, DiskExistListWrapper.class);
                Map<String, Boolean> existMap = existList.getMap();

                //循环云盘列表 删除未转换的

                it = list.iterator();
                while (it.hasNext()) {
                    TcExtFLCloudDiskShareDTO value = it.next();
                    Boolean exsit = false;
                    try {
                        //不需要转换或者转换成功的 exsit=true
                        exsit = (!ExtensionTypeMapperEnum.needExchange(value.getExtention())) || existMap.get(value.getFileUri());
                    } catch (Exception e) {
                        logger.error("判断文件是否存在发生异常：",e);
                        exsit = false;
                    }
                    if (!exsit) {
                        logger.info("移除未转换完成的disk——------" + value.getDiskId() + ":" + value.getFileUri() + ":" + value.getName());
                        //it.remove();
                        value.setConvertStatus(0);
                    }
                }

                logger.info("过滤后的结果----" + list.toString());
            } else {
                logger.info("没有需要验证转换状态的资源，过滤后的结果----" + list.toString());
            }

        } catch (IOException e) {
            logger.error("IO异常：",e);
        }

        return list;
    }

}
