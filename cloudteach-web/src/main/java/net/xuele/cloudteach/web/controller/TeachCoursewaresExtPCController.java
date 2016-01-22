package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.BookDTO;
import net.xuele.cloudteach.service.*;
import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.member.dto.*;
import net.xuele.member.service.StudentService;
import net.xuele.member.service.TeacherService;
import net.xuele.member.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TeachCoursewaresExtPCController
 * 为授课PC端提供数据的controller
 *
 * @author sunxh
 * @date 15/8/13
 */
@Controller
@RequestMapping("coursewares/extpc")
public class TeachCoursewaresExtPCController {
    /**
     * 外部接口：登录：net.xuele.member.service.UserService#checkPassword(java.lang.String, java.lang.String)
     * <p/>
     * 外部接口：获取教师信息：net.xuele.member.service.TeacherService#getTeacherInfo(java.lang.String)
     */

    @Autowired
    private TeachCoursewaresShareService teachCoursewaresShareService;

    @Autowired
    private TeachCoursewaresService teachCoursewaresService;

    @Autowired
    private CloudDiskService cloudDiskService;

    @Autowired
    private CloudTeachService cloudTeachService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClassFeedbackService classFeedbackService;

    @Autowired
    private StudentService studentService;

    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresExtPCController.class);

    /**
     * 用户登录
     *
     * @param userId
     * @param password
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public PCTeacherInfoWrapper login(@RequestParam("userId") String userId, @RequestParam("password") String password) {
        logger.info("授课神器登录：userId=" + userId + ";password=" + password);

        PCTeacherInfoWrapper teacherInfoWrapper = new PCTeacherInfoWrapper();

        try {
            String dUserId = CloudTeachEncryptUtil.decrypt(userId);
            String dPassword = CloudTeachEncryptUtil.decrypt(password);
            logger.info("授课神器登录参数解密：dUserId=" + dUserId + ";dPassword=" + dPassword);


            //登录，不记录日志
            UserDTO userDTO = userService.checkPassword(dUserId, dPassword);

            //获取教师信息
            UserTeacherDTO teacherDTO = teacherService.getTeacherInfo(dUserId);

            //填充返回对象
            teacherInfoWrapper.setState("1");       //处理状态
            teacherInfoWrapper.setUserid(CloudTeachEncryptUtil.encrypt(teacherDTO.getUserId()));    //用户ID
            teacherInfoWrapper.setName(teacherDTO.getRealName());                                   //用户名
            teacherInfoWrapper.setSchoolId(CloudTeachEncryptUtil.encrypt(userDTO.getSchoolId()));   //学校ID
            teacherInfoWrapper.setJcid(teacherDTO.getBookId());                                     //课本ID
            teacherInfoWrapper.setJcname(teacherDTO.getBookName());                                 //课本名称


            //获取第一个课程
            if (teacherDTO.getBookId() != null) {
                List<UnitsDTO> unitsDTOList = cloudTeachService.selectUnits(teacherDTO.getBookId());
                if (CollectionUtils.isNotEmpty(unitsDTOList)) {
                    UnitsDTO unitsDTO = unitsDTOList.get(0);
                    teacherInfoWrapper.setKcid(unitsDTO.getUnitId());                               //课程ID
                    teacherInfoWrapper.setKcname(unitsDTO.getUnitName());                           //课程名称
                }
            }

        } catch (Exception e) {
            teacherInfoWrapper.setState("-1");
            logger.error("授课神器登录出现错误 : " + e.getMessage(), e);
        }

        return teacherInfoWrapper;
    }

    /**
     * 列出用户课本列表
     * 包括与教师所授教材相关的所有教材版本
     *
     * @param userId
     * @return
     */
    @RequestMapping("booklist")
    @ResponseBody
    public PCTeacherBooksWrapper bookList(@RequestParam("userId") String userId) {
        logger.info("授课神器-列出用户课本列表：userId=" + userId);

        PCTeacherBooksWrapper booksWrapper = new PCTeacherBooksWrapper();

        try {
            String dUserId = CloudTeachEncryptUtil.decrypt(userId);
            logger.info("授课神器-列出用户课本列表参数解密：dUserId=" + dUserId);

            List<CtBookDTO> bookDTOList = cloudTeachService.selectTeacherBookList(dUserId);

            List<PCBookWrapper> bookWrapperList = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(bookDTOList)) {
                for (CtBookDTO ctBookDTO : bookDTOList) {
                    PCBookWrapper bookWrapper = new PCBookWrapper();
                    bookWrapper.setJcid(ctBookDTO.getBookId());
                    bookWrapper.setJcname(ctBookDTO.getBookName());
                    bookWrapperList.add(bookWrapper);
                }
            }

            booksWrapper.setState("1");
            booksWrapper.setList(bookWrapperList);

        } catch (Exception e) {
            booksWrapper.setState("-1");
            logger.error("授课神器-列出用户课本列表出现错误 : " + e.getMessage(), e);
        }

        return booksWrapper;
    }

    /**
     * 课程树
     *
     * @param bookId
     * @return
     */
    @RequestMapping("unitlist")
    @ResponseBody
    public PCUnitListWrapper unitList(@RequestParam("bookId") String bookId) {
        logger.info("授课神器-获取课程数 : bookId=" + bookId);

        PCUnitListWrapper unitListWrapper = new PCUnitListWrapper();
        List<PCUnitWrapper> unitWrappers = new ArrayList<>();

        try {
            List<UnitsDTO> unitsDTOList = cloudTeachService.selectUnits(bookId);

            if (CollectionUtils.isNotEmpty(unitsDTOList)) {

                for (UnitsDTO unitsDTO : unitsDTOList) {
                    PCUnitWrapper unitWrapper = new PCUnitWrapper();
                    unitWrapper.setKcid(unitsDTO.getUnitId());
                    unitWrapper.setKcname(unitsDTO.getUnitName());
                    unitWrappers.add(unitWrapper);
                }
            }

            unitListWrapper.setState("1");
            unitListWrapper.setList(unitWrappers);


        } catch (Exception e) {
            unitListWrapper.setState("-1");
            logger.error("授课神器-获取课程数出现错误 : " + e.getMessage(), e);
        }

        return unitListWrapper;
    }

    /**
     * 课件列表
     *
     * @param userId
     * @param schoolId
     * @param unitId
     * @return
     */
    @RequestMapping("coursewarelist")
    @ResponseBody
    public PCCoursewareListWrapper coursewareist(@RequestParam("userId") String userId,
                                                 @RequestParam("schoolId") String schoolId,
                                                 @RequestParam("unitId") String unitId) {
        logger.info("授课神器-获取课件列表：userId=" + userId + ";schoolId=" + schoolId + ";unitId=" + unitId);

        PCCoursewareListWrapper coursewareListWrapper = new PCCoursewareListWrapper();
        List<PCCoursewareWrapper> coursewareWrappers = new ArrayList<>();

        try {
            String dUserId = CloudTeachEncryptUtil.decrypt(userId);
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId);

            logger.info("授课神器-获取课件列表参数解密：dUserId=" + dUserId + ";dSchoolId=" + dSchoolId + ";unitId=" + unitId);

            //获取课件列表
            List<TeachCoursewaresDTO> coursewaresDTOList = teachCoursewaresService.queryMyTeachCoursewaresList(dSchoolId, unitId, dUserId);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


            for (TeachCoursewaresDTO teachCoursewaresDTO : coursewaresDTOList) {
                PCCoursewareWrapper courseware = new PCCoursewareWrapper();
                courseware.setSkid(teachCoursewaresDTO.getCoursewaresId());
                courseware.setName(teachCoursewaresDTO.getTitle());
                courseware.setUserid(teachCoursewaresDTO.getUserId());
                courseware.setTime(dateFormat.format(teachCoursewaresDTO.getCreateTime()));
                coursewareWrappers.add(courseware);
            }

            coursewareListWrapper.setState("1");
            coursewareListWrapper.setList(coursewareWrappers);

        } catch (Exception e) {
            coursewareListWrapper.setState("-1");
            logger.error("授课神器-获取课件列表出现错误 : " + e.getMessage(), e);
        }


        return coursewareListWrapper;
    }

    /**
     * 课件内容
     *
     * @param userId
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    @RequestMapping("getcourseware")
    @ResponseBody
    public String getCourseware(@RequestParam("userId") String userId,
                                @RequestParam("schoolId") String schoolId,
                                @RequestParam("coursewaresId") String coursewaresId) {
        logger.info("授课神器-获取课件内容 ：userId=" + userId + ";schoolId=" + schoolId + ";coursewaresId=" + coursewaresId);

        String content = "";
        try {
            String dUserId = CloudTeachEncryptUtil.decrypt(userId);
            String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId);

            logger.info("授课神器-获取课件内容参数解密 ：dUserId=" + dUserId + ";dSchoolId=" + dSchoolId + ";coursewaresId=" + coursewaresId);

            CoursewaresResponseDTO coursewaresResponseDTO = teachCoursewaresService.queryMyTeachCourse(coursewaresId, dUserId, dSchoolId);

            content = coursewaresResponseDTO.getContent();

        } catch (Exception e) {
            logger.error("授课神器-获取课件内容出现错误 : " + e.getMessage(), e);
        }

        return content;
    }

    //获取云盘资源
    @RequestMapping("disklist")
    @ResponseBody
    public CloudDiskResponseWrapper getCloudeDiskList(@RequestParam(value = "unitId") String unitId,
                                                      @RequestParam(value = "fileType", required = false) Integer fileType,
                                                      @RequestParam(value = "extType", required = false) Integer extType,
                                                      @RequestParam(value = "userId") String userId,
                                                      @RequestParam(value = "schoolId") String schoolId) {
        logger.info("授课神器-获取云盘资源 : unitId=" + unitId + ";fileType=" + fileType + ";extType=" + extType + ";userId=" + userId + ";schoolId=" + schoolId);

        CloudDiskResponseWrapper cloudDiskResponseWrapper = new CloudDiskResponseWrapper();
        String dUserId = CloudTeachEncryptUtil.decrypt(userId);
        String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId);

        logger.info("授课神器-获取云盘资源参数解密 : unitId=" + unitId + ";fileType=" + fileType + ";extType=" + extType + ";dUserId=" + dUserId + ";dSchoolId=" + dSchoolId);

        try {
            CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
            cloudDiskDTO.setUnitId(unitId);
            cloudDiskDTO.setFileType(fileType);
            cloudDiskDTO.setExtType(extType);
            cloudDiskDTO.setUserId(dUserId);
            cloudDiskDTO.setSchoolId(dSchoolId);
            List<CloudDiskDTO> cloudDiskDTOList = cloudDiskService.queryMyFiles(cloudDiskDTO);
            List<CloudDiskResponse> cloudDiskResponseList = new ArrayList<>();
            for (CloudDiskDTO dto : cloudDiskDTOList) {
                CloudDiskResponse cloudDiskResponse = new CloudDiskResponse();
                cloudDiskResponse.setFileType(dto.getFileType());
                cloudDiskResponse.setDiskId(dto.getDiskId());
                cloudDiskResponse.setExtension(dto.getExtension());
                cloudDiskResponse.setFileUri(dto.getFileUri());
                cloudDiskResponse.setName(dto.getName());
                cloudDiskResponseList.add(cloudDiskResponse);
            }

            cloudDiskResponseWrapper.setState("1");
            cloudDiskResponseWrapper.setResources(cloudDiskResponseList);
        } catch (Exception e) {
            cloudDiskResponseWrapper.setState("0");
            logger.error("授课神器-获取云盘资源出现错误 : " + e.getMessage(), e);
        }
        return cloudDiskResponseWrapper;
    }

    //根据教师用户ID获取所有学生信息
    @RequestMapping("myStuList")
    @ResponseBody
    public PCGradeStudentWrapper getMyStuList(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
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

    /**
     * 设置教师正在进行随堂反馈的课件ID及授课班级ID
     * @param userId 用户ID
     * @param coursewaresId 课件ID
     * @param randomId 随机码
     * @param classId 班级ID
     * @return
     */
    @RequestMapping("setclassfbcwid")
    @ResponseBody
    public String setClassFBCoursewaresId(@RequestParam("userId") String userId,
                                @RequestParam("coursewaresId") String coursewaresId,
                                @RequestParam("randomId") String randomId,
                                @RequestParam("classId") String classId) {
        logger.info("授课神器-设置教师正在进行随堂反馈的课件ID及授课班级ID ：userId=" + userId
                + ";coursewaresId=" + coursewaresId
                + ";randomId=" + randomId
                + ";classId="+classId);

        String res = "-1";
        String dUserId = CloudTeachEncryptUtil.decrypt(userId);
        //String dUserId = userId;
//        String str1 = "";
//        String str2 = "";
//        str1 = classFeedbackService.getClassFBCoursewaresId(dUserId);
        try {
            //设置课件ID
            boolean setres = classFeedbackService.setClassFBCoursewaresId(dUserId, coursewaresId+":"+randomId);
            if(setres){
                //设置班级ID
                setres = classFeedbackService.setClassFBClass(coursewaresId,classId);
                if(setres){
                    res = "1";
                }
            }
//            str2 = classFeedbackService.getClassFBCoursewaresId(dUserId);
//            System.out.println(str1+":"+str2);
        } catch (Exception e) {
            logger.error("授课神器-设置教师正在进行随堂反馈的课件ID及授课班级ID出现错误 : " + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 获取随堂反馈照片列表(获取课件ID对应当天上传的随堂反馈照片)
     * @param userId
     * @param schoolId
     * @param coursewaresId
     * @param randomId
     * @return
     */
    @RequestMapping("getclassfblist")
    @ResponseBody
    public ClassFeedbackWrapper getClassFBList(@RequestParam("userId") String userId,
                             @RequestParam("schoolId") String schoolId,
                             @RequestParam("coursewaresId") String coursewaresId,
                             @RequestParam("randomId") String randomId) {
        logger.info("授课神器-获取随堂反馈列表 ：userId=" + userId + ";schoolId=" + schoolId+";coursewaresId="+coursewaresId+";randomId="+randomId);
        ClassFeedbackWrapper classFeedbackWrapper = new ClassFeedbackWrapper();
        classFeedbackWrapper.setState("-1");

        String dUserId = CloudTeachEncryptUtil.decrypt(userId);
        String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId);
        //String dUserId = userId;
        //String dSchoolId = schoolId;
        try {
            //获取课件ID
            String classFBCoursewaresId = classFeedbackService.getClassFBCoursewaresId(dUserId);
            if(!StringUtils.isEmpty(classFBCoursewaresId) && classFBCoursewaresId.equals(coursewaresId+":"+randomId)){
                //获取是否有新照片状态：false没有，true有
                boolean newStatus = classFeedbackService.getNewClassFBStatus(coursewaresId);
                if(newStatus) {
                    List<ClassFeedbackDTO> list = classFeedbackService.getClassFBList(dUserId, dSchoolId, coursewaresId);
                    //设置是否有新照片状态为没有
                    classFeedbackService.setNewClassFBStatus(coursewaresId,"0");
                    if (!CollectionUtils.isEmpty(list)) {
                        classFeedbackWrapper.setState("1");
                        classFeedbackWrapper.setClassFBList(list);
                    }
                }
            }else{
                //该课件ID不是正在进行随堂反馈的课件ID
                classFeedbackWrapper.setState("-2");
            }
        } catch (Exception e) {
            logger.error("授课神器-获取随堂反馈列表出现错误 : " + e.getMessage(), e);
        }
        return classFeedbackWrapper;
    }

    /**
     * 非同步情况下获取随堂反馈照片列表(获取课件ID对应当天上传的随堂反馈照片)
     * @param userId
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    @RequestMapping("getunsynclassfblist")
    @ResponseBody
    public ClassFeedbackWrapper getUnSynClassFBList(@RequestParam("userId") String userId,
                                               @RequestParam("schoolId") String schoolId,
                                               @RequestParam("coursewaresId") String coursewaresId) {
        logger.info("授课神器-非同步情况下获取随堂反馈列表 ：userId=" + userId + ";schoolId=" + schoolId+";coursewaresId="+coursewaresId);
        ClassFeedbackWrapper classFeedbackWrapper = new ClassFeedbackWrapper();
        classFeedbackWrapper.setState("-1");

        String dUserId = CloudTeachEncryptUtil.decrypt(userId);
        String dSchoolId = CloudTeachEncryptUtil.decrypt(schoolId);
        //String dUserId = userId;
        //String dSchoolId = schoolId;
        try {
            List<ClassFeedbackDTO> list = classFeedbackService.getClassFBList(dUserId, dSchoolId, coursewaresId);
            classFeedbackWrapper.setClassFBList(list);
            classFeedbackWrapper.setState("1");
        }catch (Exception e) {
            logger.error("授课神器-非同步情况下获取随堂反馈列表 : " + e.getMessage(), e);
        }

        return classFeedbackWrapper;
    }

    /**
     * 获取随堂反馈下随机点名选中的的学生信息(课件需轮询接口)
     * @param userId
     * @param coursewaresId
     * @return StudentFBResponseWrapper.state:1有学生信息 -1无学生信息 0取消随机点名 -2课件未开通随堂反馈，建议停止轮询
     */
    @RequestMapping("getselectedstulist")
    @ResponseBody
    public StudentFBResponseWrapper getSelectedStuList(@RequestParam("userId") String userId,
                                               @RequestParam("coursewaresId") String coursewaresId,
                                               @RequestParam("randomId") String randomId) {
        logger.info("授课神器-获取随堂反馈下随机点名点中的的学生信息 ：userId=" + userId +";coursewaresId="+coursewaresId);
        StudentFBResponseWrapper studentFBResponseWrapper = new StudentFBResponseWrapper();
        studentFBResponseWrapper.setState("-1");

        String dUserId = CloudTeachEncryptUtil.decrypt(userId);
        //String dUserId = userId;
        try {
            //获取课件ID
            String classFBCoursewaresId = classFeedbackService.getClassFBCoursewaresId(dUserId);
            if(!StringUtils.isEmpty(classFBCoursewaresId) && classFBCoursewaresId.equals(coursewaresId+":"+randomId)){
                //获取是否有随机点名选中学生状态：false没有，true有
                String newStatus = classFeedbackService.getSelectedStuFBStatus(coursewaresId);
                if(newStatus!=null && newStatus.equals("1")) {
                    String stuIds = classFeedbackService.getSelectedStuIdFB(coursewaresId);
                    String[] stuIdArray = stuIds.split(":");
                    //redis中选中的学生信息不为空
                    if(!StringUtils.isEmpty(stuIds)){
                        //获取该班级的所有学生信息
                        List<StudentWrapper>  StudentWrapperList = new ArrayList<>();
                        for(String studentId : stuIdArray){
                            MyMessageDTO student = studentService.getMyMessageStudent(studentId);
                            StudentWrapper studentWrapper = new StudentWrapper();
                            studentWrapper.setStudentId(student.getUserId());
                            studentWrapper.setStudentName(student.getRealName());
                            studentWrapper.setIcon(student.getIcon());
                            StudentWrapperList.add(studentWrapper);
                        }
                        studentFBResponseWrapper.setList(StudentWrapperList);
                        //设置随堂反馈下是否有随机点名选中的的学生状态为没有
                        classFeedbackService.setSelectedStuFBStatus(coursewaresId, "0");
                    }

                    if (!CollectionUtils.isEmpty(studentFBResponseWrapper.getList())) {
                        studentFBResponseWrapper.setState("1");
                    }
                }else if(newStatus!=null && newStatus.equals("-1")){
                    studentFBResponseWrapper.setState("0");
                }
            }else{
                //该课件ID不是正在进行随堂反馈的课件ID
                studentFBResponseWrapper.setState("-2");
            }
        } catch (Exception e) {
            logger.error("授课神器-获取随堂反馈下随机点名点中的的学生信息 : " + e.getMessage(), e);
        }
        return studentFBResponseWrapper;
    }

    /**
     * 获取教师的某个课件对应的授课班级列表
     * @param userId
     * @param unitId
     * @return
     */
    @RequestMapping("getclasslistbyunit")
    @ResponseBody
    public TeacherClassByUnitReponseWrapper getClassListByUnit(@RequestParam("userId") String userId,
                                                       @RequestParam("unitId") String unitId) {
        logger.info("授课神器-获取教师的某个课件对应的授课班级列表 ：userId=" + userId +";unitId="+unitId);
        TeacherClassByUnitReponseWrapper teacherClassByUnitReponseWrapper = new TeacherClassByUnitReponseWrapper();
        teacherClassByUnitReponseWrapper.setState("-1");

        String dUserId = CloudTeachEncryptUtil.decrypt(userId);
        //String dUserId = userId;
        try {

            BookDTO bookDTO = cloudTeachService.getBookByUnit(unitId);

            if(bookDTO != null){
                List<ClassInfoDTO> classList = cloudTeachService.selectTeacherGradeClassList(dUserId, bookDTO.getGrade());
                teacherClassByUnitReponseWrapper.setState("1");
                teacherClassByUnitReponseWrapper.setList(classList);
            }

        } catch (Exception e) {
            logger.error("授课神器-获取随堂反馈下随机点名点中的的学生信息 : " + e.getMessage(), e);
        }
        return teacherClassByUnitReponseWrapper;
    }

    /**
     * 测试接口(设置随堂反馈随机选中的学生信息)
     * http://www.xueleyun.com/cloudteach/coursewares/extpc/setSelectedStusFB?userId=1000110002&stuIds=1000110025:1000110033:1000110038&coursewaresId=44ea02567aa0489fa957f3f1da924216
     * @param userId
     * @param stuIds
     * @return
     */
    @RequestMapping("setSelectedStusFB")
    @ResponseBody
    public String setSelectedStusFB(@RequestParam("userId") String userId,
                                    @RequestParam("stuIds") String stuIds,
                                    @RequestParam("coursewaresId") String coursewaresId) {
        //classFeedbackService.setClassFBCoursewaresId(userId, coursewaresId);
        String res = classFeedbackService.setSelectedStusFB(userId, stuIds);

        return res;
    }

    /**
     * 测试接口(随堂反馈取消随机选择学生)
     * http://www.xueleyun.com/cloudteach/coursewares/extpc/cancelSelectedStusFB?userId=1000110002
     * @param userId
     * @return
     */
    @RequestMapping("cancelSelectedStusFB")
    @ResponseBody
    public String cancelSelectedStusFB(@RequestParam("userId") String userId) {
        String res = classFeedbackService.cancelSelectedStusFB(userId);
        return res;
    }

    /**
     * 测试接口(随堂反馈取消随机选择学生)
     * http://www.xueleyun.com/cloudteach/coursewares/extpc/getCFbClassList?userId=1030610004
     * @param userId
     * @return
     */
    @RequestMapping("getCFbClassList")
    @ResponseBody
    public String getCFbClassList(@RequestParam("userId") String userId) {
        ClassFbPackageDTO classFbPackageDTO = classFeedbackService.getCFbClassList(userId);
        return null;
    }
}
