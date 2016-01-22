package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.domain.CtClassFeedback;
import net.xuele.cloudteach.domain.CtTeachCoursewares;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.ClassFeedbackPageRequest;
import net.xuele.cloudteach.persist.CtClassFeedbackMapper;
import net.xuele.cloudteach.persist.CtTeachCoursewaresMapper;
import net.xuele.cloudteach.service.ClassFeedbackService;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.TeachCoursewareLogService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.service.util.JsonUtil;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.cloudteach.view.ClassFeedbackView;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import net.xuele.member.dto.StudentManagerDTO;
import net.xuele.member.dto.UserDTO;
import net.xuele.member.service.UserService;
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
 * Created by duzg on 2015/10/14 0009.
 * * 随堂反馈服务的实现，类头上加入@Service 表示该服务可暴露成dubbo服务
 *
 * @Autowired 表示自动注入依赖
 */

@Service
public class ClassFeedbackServiceImpl implements ClassFeedbackService {

    @Autowired
    CloudTeachRedisService cloudTeachRedisService;

    @Autowired
    private CtClassFeedbackMapper ctClassFeedbackMapper;

    @Autowired
    private UserService userService;

    @Autowired
    CtTeachCoursewaresMapper ctTeachCoursewaresMapper;

    private static Logger logger = LoggerFactory.getLogger(CloudDiskServiceImpl.class);

    @Autowired
    CloudTeachService cloudTeachService;

    @Autowired
    TeachCoursewareLogService teachCoursewareLogService;

    /**
     * 获取用户对应的授课班级及授课课本名称、课程名称
     * @param userId
     * @return
     * @author panglx
     * @throws CloudteachException
     */
    @Override
    public ClassFbPackageDTO getCFbClassList(String userId) throws CloudteachException {
        logger.info("获取用户对应的授课班级及授课课本名称、课程名称getCFbClassList:userId="+userId);
        ClassFbPackageDTO classFbPackageDTO = new ClassFbPackageDTO();
        logger.info("从redis中获取授课课件");
        String coursewaresId = this.getClassFBCoursewaresId(userId);
        logger.info("课件idcoursewaresId："+coursewaresId);
        if (StringUtils.isEmpty(coursewaresId)){
            return null;
            //throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg()+"请先在授课神器中打开授课课件，进入随堂反馈", CloudTeachErrorEnum.NOPERMISSION.getCode());
        }else{
            //课件信息中去除随机码信息
            if(coursewaresId.indexOf(":")>0){
                coursewaresId = coursewaresId.substring(0,coursewaresId.indexOf(":"));
            }
            //学校id
            String schoolId = userService.getSchoolIdByUserId(userId);
            logger.info("schoolId："+schoolId);
            if (StringUtils.isEmpty(schoolId)){
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+"该用户的学校id为空，请检查！", CloudTeachErrorEnum.PARAMERROR.getCode());
            }
            logger.info("根据课件id查询课件信息");
            CtTeachCoursewares coursewares = ctTeachCoursewaresMapper.selectByPrimaryKey(schoolId,coursewaresId);
            if (coursewares == null){
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+"该课件不存在！", CloudTeachErrorEnum.PARAMERROR.getCode());
            }
            logger.info("课件信息coursewares："+coursewares.toString());
            String unitId = coursewares.getUnitId();
            logger.info("unitId："+unitId);
            //课程id
            classFbPackageDTO.setUnitId(unitId);
            logger.info("根据课程id获取课程信息");
            UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(unitId);
            if (unitsDTO == null){
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+"该课程不存在！", CloudTeachErrorEnum.PARAMERROR.getCode());
            }
            logger.info("课程信息unitsDTO："+unitsDTO.toString());
            classFbPackageDTO.setUnitName(unitsDTO.getUnitName());
            logger.info("根据课程id获取课本信息");
            BookDTO bookDTO = cloudTeachService.getBookByUnit(unitId);
            if (bookDTO == null){
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+"该课本不存在！", CloudTeachErrorEnum.PARAMERROR.getCode());
            }
            logger.info("课本信息bookDTO："+bookDTO.toString());
            //课本名字
            classFbPackageDTO.setBookName(bookDTO.getBookName());
            //科目名称
            classFbPackageDTO.setSubjectName(bookDTO.getSubjectName());

            String classId = this.getClassFBClass(coursewaresId);
            if(StringUtils.isEmpty(classId)){
                throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+"授课课件没有对应的班级信息！", CloudTeachErrorEnum.PARAMERROR.getCode());
            }

            logger.info("设置班级信息");
            List<ClassInfoDTO> classInfoDTOs = cloudTeachService.selectTeacherGradeClassList(userId, bookDTO.getGrade());
            for(ClassInfoDTO classInfoDTO : classInfoDTOs){
                if(classId.equals(classInfoDTO.getClassId())){
                    List<ClassInfoDTO> resList = new ArrayList<>();
                    resList.add(classInfoDTO);
                    classFbPackageDTO.setClassInfoDTOs(resList);
                    break;
                }
            }

            logger.info("根据班级号获取学生信息");
            List<StudentManagerDTO> StudentManagerDTOs = this.getCFbStudentList(classId);
            classFbPackageDTO.setStudetnDTOs(StudentManagerDTOs);
        }
        return classFbPackageDTO;
    }

    /**
     * 获取班级对应的学生列表
     * @param classId
     * @return
     * @author panglx
     * @throws CloudteachException
     */
    @Override
    public List<StudentManagerDTO> getCFbStudentList(String classId) throws CloudteachException {
        logger.info("获取班级对应的学生列表getCFbStudentList:classId="+classId);
        return cloudTeachService.selectClassStudentList(classId);
    }

    /**
     * 上传随堂反馈图片
     * 参入参数：studentId 学生id；unitId 课程id；classId 班级id；className 班级名称；uploadUserId 教师id；
     *          filekey HDFS文件uri；fileName 文件名；extension 扩展名；size 文件大小；
     * @param classFeedbackDTOs
     * @return
     * @author panglx
     * @throws CloudteachException
     */
    @Override
    public String uploadCFbFile(List<ClassFeedbackDTO> classFeedbackDTOs) throws CloudteachException {
        logger.info("上传随堂反馈图片classFeedbackDTO:classFeedbackDTOs");

        List<CtClassFeedback> classFeedbacks = this.entityDtoListToList(classFeedbackDTOs);
        if (CollectionUtils.isNotEmpty(classFeedbacks)){

            String uploadUserId = classFeedbacks.get(0).getUploadUserId();//教师id
            logger.info("从redis中获取授课课件id");
            String coursewaresId = this.getClassFBCoursewaresId(uploadUserId);//课件id
            String cclId = coursewaresId.replace(":","");

            if(!StringUtils.isEmpty(coursewaresId) && coursewaresId.indexOf(":")>0){
                //去除随机码信息
                coursewaresId = coursewaresId.substring(0,coursewaresId.indexOf(":"));

            }else{
                return "0";
            }

            List<CoursewareClassCFBLogDTO> clist = new ArrayList<>();
            String schoolId = "";
            for (CtClassFeedback classFeedback:classFeedbacks){
                /**
                 * 查询学生信息
                 */
                String studentId = classFeedback.getStudentId();
                logger.info("studentId:"+studentId);
                UserDTO  userDTO = userService.getByUserId(studentId);
                if (userDTO == null){
                    throw new CloudteachException(CloudTeachErrorEnum.DATAERROR.getMsg()+"该学生不存在！", CloudTeachErrorEnum.DATAERROR.getCode());
                }
                logger.info("学生信息userDTO:"+userDTO);
                String studentName =  userDTO.getRealName();//学生名称
                schoolId = userDTO.getSchoolId();//学校

                classFeedback.setSchoolId(schoolId);
                classFeedback.setStudentName(studentName);

                classFeedback.setFbId(UUID.randomUUID().toString().replace("-", ""));
                classFeedback.setStatus(1);
                classFeedback.setFileType(1);
                classFeedback.setUploadTime(new Date());
                classFeedback.setClassName(classFeedback.getClassName());
                logger.info("上传的文件名按规则截取长度");
                classFeedback.setFileName(StringUtil.fileNameSubstring(classFeedback.getFileName(), Constants.MAX_FILE_NAME_LENGTH));

                logger.info("授课课件id-coursewaresId:"+coursewaresId);
                if (coursewaresId.equals("")){
                    throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg()+"请先在授课神器中打开授课课件，进入随堂反馈", CloudTeachErrorEnum.NOPERMISSION.getCode());
                }
                classFeedback.setCoursewaresId(coursewaresId);//课件id

                //随堂反馈表中插入数据
                int result = ctClassFeedbackMapper.insert(classFeedback);
                String status = "0";
                if (1 != result) {
                    CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.INSERTDISKFAILED);
                }else{
                    status = "1";
                }
                //设置教师正在进行随堂反馈的课件是否有新照片
                if (!this.setNewClassFBStatus(coursewaresId,status)){
                    return "0";
                }
                //学情打点
                CoursewareClassCFBLogDTO coursewareClassCFBLogDTO = new CoursewareClassCFBLogDTO();
                coursewareClassCFBLogDTO.setUserId(classFeedback.getStudentId());
                coursewareClassCFBLogDTO.setUserName(classFeedback.getStudentName());
                coursewareClassCFBLogDTO.setUserIcon(userDTO.getIcon());
                coursewareClassCFBLogDTO.setFileName(classFeedback.getFileName());
                clist.add(coursewareClassCFBLogDTO);
            }
            try {
                //根据授课记录ID获取授课记录，如果获取不到则不进行打点操作
                TeachCoursewareLogDTO teachCoursewareLogDTO = teachCoursewareLogService.getLogById(schoolId,cclId);

                if(teachCoursewareLogDTO!=null && !StringUtils.isEmpty(teachCoursewareLogDTO.getCclId())) {
                    //记录课件打点日志信息
                    String clogJson = JsonUtil.getBeanToJson(clist);
                    TeachCoursewareLogDetailDTO teachCoursewareLogDetailDTO = new TeachCoursewareLogDetailDTO();
                    teachCoursewareLogDetailDTO.setCclId(cclId);
                    teachCoursewareLogDetailDTO.setCcdlType(1);
                    teachCoursewareLogDetailDTO.setCcdlContext("随堂反馈");
                    teachCoursewareLogDetailDTO.setSchoolId(schoolId);
                    teachCoursewareLogDetailDTO.setCcdlContent1(clogJson);
                    teachCoursewareLogDetailDTO.setCcdlContent2("");
                    teachCoursewareLogDetailDTO.setCcdlContent3("");
                    teachCoursewareLogService.writeLogDetail(teachCoursewareLogDetailDTO);
                }
            } catch (IOException e) {
                logger.error("ClassFeedbackServiceImpl.uploadCFbFile:课件学情随堂反馈打点出错");
            }
        }else{
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+"参数不能为空", CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        return "1";
    }

    /**
     * 手机端上传随堂反馈随机点名选中的学生信息
     * @param userId 教师ID
     * @param studentIds 学生ID：多个学生以“:”分隔
     * @return 0设置失败  1设置成功
     * @throws CloudteachException
     */
    @Override
    public String setSelectedStusFB(String userId,String studentIds) throws CloudteachException {
        logger.info("手机端上传随堂反馈随机点名选中的学生信息:userId:"+userId+",studentIds:"+studentIds);
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(studentIds)){
            return "0";
        }
        //获取课件id
        String coursewaresId = this.getClassFBCoursewaresId(userId);
        if(StringUtils.isEmpty(coursewaresId)){
            return "0";
        }

        //去除随机码信息
        if(coursewaresId.indexOf(":")>0){
            coursewaresId = coursewaresId.substring(0,coursewaresId.indexOf(":"));
        }
        //将学生信息设置到redis
        setSelectedStuIdFB(coursewaresId,studentIds);
        //设置随堂反馈下有随机点名选中的的学生
        setSelectedStuFBStatus(coursewaresId,"1");
        return "1";
    }

    /**
     * 手机端取消随堂反馈随机点名操作
     * @param userId 教师ID
     * @return 0取消失败  1取消成功
     * @throws CloudteachException
     */
    @Override
    public String cancelSelectedStusFB(String userId) throws CloudteachException {
        logger.info("手机端取消随堂反馈随机点名操作:userId:"+userId);
        if(StringUtils.isEmpty(userId)){
            return "0";
        }
        //获取课件id
        String coursewaresId = this.getClassFBCoursewaresId(userId);
        if(StringUtils.isEmpty(coursewaresId)){
            return "0";
        }

        //去除随机码信息
        if(coursewaresId.indexOf(":")>0){
            coursewaresId = coursewaresId.substring(0,coursewaresId.indexOf(":"));
        }

        //取消随堂反馈随机点名
        setSelectedStuFBStatus(coursewaresId,"-1");
        return "1";
    }

    /**
     * 分页查询随堂反馈资源
     * @param request
     * @return
     * @author panglx
     * @throws CloudteachException
     */
    @Override
    public PageResponse<ClassFeedbackViewDTO> queryPageList(ClassFeedbackPageRequest request) throws CloudteachException{
        logger.info("分页查询随堂反馈资源queryPageList:request"+request.toString());
        //获取数据
        Page page = PageUtils.buildPage(request);

        String unitId = request.getUnitId();
        String userId = request.getUserId();
        String schoolId = request.getSchoolId();
        String classId = request.getClassId();
        /**
         * 分页获取随堂反馈资源
         */
        List<ClassFeedbackView> classFeedbackViews = ctClassFeedbackMapper.queryPageList(unitId,userId,schoolId,classId,request.getPageSize(),page);
        List<ClassFeedbackViewDTO> classFeedbackViewDTOs = this.entityListToViewDtoList(classFeedbackViews);
        logger.info("获取随堂反馈记录数");
        int records = ctClassFeedbackMapper.count(unitId,userId,schoolId,classId);
        logger.info("records="+records);
        PageResponse<ClassFeedbackViewDTO> pageResponse = new PageResponse<>();
        PageUtils.buldPageResponse(request, pageResponse);
        pageResponse.setRecords(records);
        pageResponse.setRows(classFeedbackViewDTOs);

        return pageResponse;
    }

    /**
     * 删除随堂反馈资源
     * @param fbId
     * @param schoolId
     * @author panglx
     * @throws CloudteachException
     */
    @Override
    public void removeFile(String fbId,String userId,String schoolId) throws CloudteachException{
        logger.info("删除随堂反馈资源removeFile:fbId="+fbId+",userId="+userId+",schoolId="+schoolId);
        CtClassFeedback classFeedback = ctClassFeedbackMapper.selectByPrimaryKey(fbId,schoolId);
        //查找的对象不存在
        if (null == classFeedback) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.OBJECTNOTFOUND);
        }
        //无权限进行该操作
        if (!userId.equals(classFeedback.getUploadUserId())){
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.NOPERMISSION);
        }
        classFeedback.setStatus(0);

        int result = ctClassFeedbackMapper.updateByPrimaryKey(classFeedback);
        logger.info("删除结果result="+result);
        if (1 != result){
            //资源删除出错
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ERRORDELETE);
        }

    }

    /**
     * 设置教师正在进行随堂反馈的课件ID
     * @param userId
     * @param coursewaresId
     * @return
     */
    @Override
    public boolean setClassFBCoursewaresId(String userId, String coursewaresId) {
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(coursewaresId)){
            return false;
        }
        Date nowTime = new Date();
        //此信息日终结束即失效
        long liveTime = (DateTimeUtil.getDayEnd(nowTime).getTime() - nowTime.getTime())/1000;
        //redis中设置userId对应的随堂反馈课件ID
        cloudTeachRedisService.set("ClassFB" + userId, coursewaresId,liveTime);

        return true;
    }

    /**
     * 获取教师正在进行随堂反馈的课件ID
     * @param userId
     * @return
     */
    @Override
    public String getClassFBCoursewaresId(String userId) {
        if(StringUtils.isEmpty(userId)){
            return "";
        }
        //redis获取userId对应的随堂反馈课件ID
        String classFBCoursewaresId = cloudTeachRedisService.get("ClassFB" + userId);

        if(classFBCoursewaresId == null){
            classFBCoursewaresId = "";
        }

        return classFBCoursewaresId;
    }

    /**
     * 设置教师正在授课的课件对应的班级
     * @param coursewaresId
     * @param classId
     * @return
     */
    @Override
    public boolean setClassFBClass(String coursewaresId, String classId) {
        if(StringUtils.isEmpty(coursewaresId) || StringUtils.isEmpty(classId)){
            return false;
        }
        Date nowTime = new Date();
        //此信息日终结束即失效
        long liveTime = (DateTimeUtil.getDayEnd(nowTime).getTime() - nowTime.getTime())/1000;
        //redis中设置userId对应的随堂反馈课件ID
        cloudTeachRedisService.set("ClassFBClass" + coursewaresId, classId,liveTime);

        return true;
    }

    /**
     * 获取教师正在授课的课件对应的班级
     * @param coursewaresId
     * @return
     */
    @Override
    public String getClassFBClass(String coursewaresId) {
        if(StringUtils.isEmpty(coursewaresId)){
            return "";
        }
        //redis获取userId对应的随堂反馈课件ID
        String classId = cloudTeachRedisService.get("ClassFBClass" + coursewaresId);

        if(classId == null){
            classId = "";
        }

        return classId;
    }

    /**
     * 获取随堂反馈列表(获取课件ID对应当天上传的随堂反馈照片)
     * @param userId
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    @Override
    public List<ClassFeedbackDTO> getClassFBList(String userId, String schoolId, String coursewaresId) {
        if(StringUtils.isEmpty(userId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":教师用户ID为空,获取随堂反馈列表失败", CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID为空,获取随堂反馈列表失败", CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(coursewaresId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":课件ID为空,获取随堂反馈列表失败", CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        String classId = this.getClassFBClass(coursewaresId);

        //只获取今天上传的所有图片
        Date nowTime = new Date();
        Date begTime = DateTimeUtil.getDayBegin(nowTime);
        Date endTime = DateTimeUtil.getDayEnd(nowTime);

        List<CtClassFeedback> classFeedbackList = ctClassFeedbackMapper.selectClassFBByCwIdList(schoolId, userId, coursewaresId, classId, DateTimeUtil.DateToStringForMycat(begTime), DateTimeUtil.DateToStringForMycat(endTime));
        if(classFeedbackList==null || classFeedbackList.size()==0){
            return null;
        }

        return entityListToDtoList(classFeedbackList);
    }

    /**
     * 设置教师正在进行随堂反馈的课件是否有新照片
     * app上传照片后需要调用此接口将status修改为1
     * @param coursewaresId
     * @param status（0没有 1有）
     * @return
     */
    @Override
    public boolean setNewClassFBStatus(String coursewaresId,String status) {
        if(StringUtils.isEmpty(coursewaresId) || StringUtils.isEmpty(status)){
            return false;
        }
        if(!status.equals("1") && !status.equals("0")){
            return false;
        }
        Date nowTime = new Date();
        //此信息日终结束即失效
        long liveTime = (DateTimeUtil.getDayEnd(nowTime).getTime() - nowTime.getTime())/1000;
        //redis中设置设置教师正在进行随堂反馈的课件是否有新照片
        cloudTeachRedisService.set("NewClassFB" + coursewaresId, status,liveTime);

        return true;
    }

    /**
     * 获取教师正在进行随堂反馈的课件是否有新照片状态（0没有 1有）
     * @param coursewaresId
     * @return false没有  true有
     */
    @Override
    public boolean getNewClassFBStatus(String coursewaresId) {
        if(StringUtils.isEmpty(coursewaresId)){
            return false;
        }
        //redis获取教师正在进行随堂反馈的课件是否有新照片状态（0没有 1有）
        String status = cloudTeachRedisService.get("NewClassFB" + coursewaresId);

        if(!StringUtils.isEmpty(status) && status.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 设置随堂反馈下是否有随机点名选中的的学生
     * app随机点名后需要调用此接口将status修改为1
     * @param coursewaresId
     * @param status（0没有 1有 -1取消随机点名）
     * @return
     */
    @Override
    public boolean setSelectedStuFBStatus(String coursewaresId,String status) {
        if(StringUtils.isEmpty(coursewaresId) || StringUtils.isEmpty(status)){
            return false;
        }
        if(!status.equals("1") && !status.equals("0") && !status.equals("-1")){
            return false;
        }
        Date nowTime = new Date();
        //此信息日终结束即失效
        long liveTime = (DateTimeUtil.getDayEnd(nowTime).getTime() - nowTime.getTime())/1000;
        //redis中设置随堂反馈下是否有随机点名选中的的学生
        cloudTeachRedisService.set("SelectedStuStaFB" + coursewaresId, status,liveTime);

        return true;
    }

    /**
     * 获取随堂反馈下是否有随机点名选中的的学生状态（0没有 1有）
     * @param coursewaresId
     * @return （0没有 1有 -1取消随机点名）
     */
    @Override
    public String getSelectedStuFBStatus(String coursewaresId) {
        if(StringUtils.isEmpty(coursewaresId)){
            return "0";
        }
        //redis获取随堂反馈下是否有随机点名选中的的学生状态（0没有 1有）
        String status = cloudTeachRedisService.get("SelectedStuStaFB" + coursewaresId);

        if(StringUtils.isEmpty(status)){
            return "0";
        }else{
            return status;
        }
    }

    /**
     * 设置随堂反馈下是否有随机点名选中的的学生ID
     * app随机点名后需要调用此接口记录信息
     * @param coursewaresId
     * @param studentIds（学生ID：多个学生以“:”分隔）
     * @return
     */
    @Override
    public boolean setSelectedStuIdFB(String coursewaresId,String studentIds){
        if(StringUtils.isEmpty(coursewaresId) || StringUtils.isEmpty(studentIds)){
            return false;
        }
        Date nowTime = new Date();
        //此信息日终结束即失效
        long liveTime = (DateTimeUtil.getDayEnd(nowTime).getTime() - nowTime.getTime())/1000;
        //redis中设置随堂反馈下是否有随机点名选中的的学生ID
        cloudTeachRedisService.set("SelectedStuIdFB" + coursewaresId, studentIds, liveTime);
        return true;
    }

    /**
     * 获取随堂反馈下是否有随机点名选中的的学生ID（学生ID：多个学生以“:”分隔）
     * @param coursewaresId
     * @return
     */
    @Override
    public String getSelectedStuIdFB(String coursewaresId){
        if(StringUtils.isEmpty(coursewaresId)){
            return "";
        }
        //redis获取随堂反馈下是否有随机点名选中的的学生ID
        String stuIds = cloudTeachRedisService.get("SelectedStuIdFB" + coursewaresId);

        if(!StringUtils.isEmpty(stuIds)){
            return stuIds;
        }else{
            return "";
        }
    }

    //=======================private method================================//
    private List<ClassFeedbackDTO> entityListToDtoList(List<CtClassFeedback> ctCloudDisks) {
        List<ClassFeedbackDTO> classFeedbackDTOList = new ArrayList<ClassFeedbackDTO>();
        for (CtClassFeedback mRes : ctCloudDisks) {
            ClassFeedbackDTO classFeedbackDTO = new ClassFeedbackDTO();
            BeanUtils.copyProperties(mRes, classFeedbackDTO);
            classFeedbackDTOList.add(classFeedbackDTO);
        }
        return classFeedbackDTOList;
    }

    private List<CtClassFeedback> entityDtoListToList(List<ClassFeedbackDTO> classFeedbackDTOList) {
        List<CtClassFeedback> ctCloudDisks = new ArrayList<>();
        CtClassFeedback classFeedback;
        for (ClassFeedbackDTO mRes : classFeedbackDTOList) {
            classFeedback = new CtClassFeedback();
            BeanUtils.copyProperties(mRes, classFeedback);
            ctCloudDisks.add(classFeedback);
        }
        return ctCloudDisks;
    }

    private List<ClassFeedbackViewDTO> entityListToViewDtoList(List<ClassFeedbackView> classFeedbackViews) {
        List<ClassFeedbackViewDTO> classFeedbackViewDTOs = new ArrayList<>();
        for (ClassFeedbackView mRes : classFeedbackViews) {
            ClassFeedbackViewDTO feedbackViewDTO = new ClassFeedbackViewDTO();
            BeanUtils.copyProperties(mRes, feedbackViewDTO);
            classFeedbackViewDTOs.add(feedbackViewDTO);
        }
        return classFeedbackViewDTOs;
    }
}
