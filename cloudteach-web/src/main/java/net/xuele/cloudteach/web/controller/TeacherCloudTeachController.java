package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.reflect.ClassPath;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.BookDTO;
import net.xuele.cloudteach.dto.Magic2StuAnsJsonDTO;
import net.xuele.cloudteach.dto.Magic2WorkChallengeDTO;
import net.xuele.cloudteach.dto.TeacherCloudTeachViewDTO;
import net.xuele.cloudteach.dto.page.TeacherCloudTeachViewPageRequest;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.Magic2WorkService;
import net.xuele.cloudteach.service.TeacherCloudTeachService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.TeacherCloudTeachViewWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;
import net.xuele.member.constant.IdentityIdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * TeacherCloudTeachController
 * 教师云教学
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
@Controller
@RequestMapping(value = "teacher")
public class TeacherCloudTeachController {


    @Autowired
    private TeacherCloudTeachService teacherCloudTeachService;

    @Autowired
    private CloudTeachService cloudTeachService;

    @Autowired
    private Magic2WorkService magic2WorkService;

    private static Logger logger = LoggerFactory.getLogger(TeacherWorkManageController.class);

    /**
     * 根据老师用户ID获取教师授课班级列表信息
     *
     * @return
     */
    @RequestMapping(value = "classList")
    @ResponseBody
    public AjaxResponse classList() {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("根据老师用户ID获取教师授课班级列表信息,userId:" + userId + ",schoolId:" + schoolId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        if (StringUtils.isEmpty(userId)) {
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
        List<ClassInfoDTO> teachClassList = new ArrayList<>();
        try {
            // 如果是管理员，则获取全部的班级
            if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
                teachClassList = cloudTeachService.queryAllClassList(userId, schoolId);
            } else {
                teachClassList = cloudTeachService.selectTeacherClassList(userId);
            }
            ajaxResponse.setWrapper(teachClassList);
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
        }

        return ajaxResponse;
    }

    /**
     * 根据老师用户ID,BOOKID,获取对应的班级信息列表
     *
     * @return
     */
    @RequestMapping(value = "gradeClassList")
    @ResponseBody
    public AjaxResponse gradeClassList() {

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String bookId = GetSessionContentUtil.getInstance().currentBookID();
        logger.info("根据老师用户ID,BOOKID,获取对应的班级信息列表,userId:" + userId + ",bookId:" + bookId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus("1");
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
        if (StringUtils.isEmpty(userId)) {
            ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
            ajaxResponse.setStatus("0");
            return ajaxResponse;
        }

        BookDTO bookDTO;
        List<ClassInfoDTO> teachGradeClassList = new ArrayList<>();

        try {
            bookDTO = cloudTeachService.selectEditionAndSubject(bookId);
            if (!IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
                teachGradeClassList = cloudTeachService.selectTeacherGradeClassList(userId, bookDTO.getGrade().intValue());
            }
            ajaxResponse.setWrapper(teachGradeClassList);
        } catch (CloudteachException e) {
            ajaxResponse.setErrorMsg(e.getMsg());
            ajaxResponse.setStatus("0");
        }

        return ajaxResponse;
    }

    /**
     * 云教学首页初始化
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "initList")
    public String initList(ModelMap map) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String bookId = GetSessionContentUtil.getInstance().currentBookID();
        logger.info("云教学首页初始化,userId:" + userId + ",schoolId:" + schoolId + ",bookId:" + bookId);
        if (bookId != null) {
            //获取主授课本
            BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(bookId);
            if (bookDTO != null) {
                //根据老师用户ID、主授课本对应年级获取教师授课班级列表信息
                List<ClassInfoDTO> teachClassList = cloudTeachService.selectTeacherGradeClassList(userId, bookDTO.getGrade().intValue());

                ClassInfoDTO initclassInfo = new ClassInfoDTO();
                if (teachClassList != null && teachClassList.size() > 0) {
                    initclassInfo = teachClassList.get(0);
                }

                //获取教师所授课本是否能布置提分宝作业与同步课堂作业
                //1小学语数英(可以布置同步课堂) 2初中数理化（可以布置提分宝） 0其他
                Integer teacherType = GetSessionContentUtil.getInstance().getTeacherPeriodType();

                map.put("teachClassList", teachClassList);
                map.put("initclassInfo", initclassInfo);
                map.put("teacherType", teacherType);
            }
        }

        return "cloudteach/teacher/initList";
    }

    /**
     * @param request 【请求参数】
     * @return 分页获取发布过的所有信息（包括导学作业、课件、板书、课后作业）（默认排序方式：发布时间倒序）
     */
    @RequestMapping(value = "workList")
    @ResponseBody
    public AjaxResponse<PageResponse<TeacherCloudTeachViewWrapper>> workList(TeacherCloudTeachViewPageRequest request) {
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        request.setUserId(userId);
        request.setSchoolId(schoolId);
        request.setListType(2);
        if (StringUtils.isEmpty(request.getPublishTime())) {
            request.setPublishTime("0");
        }
        logger.info("分页获取发布过的所有信息,userId:" + userId + ",schoolId:" + schoolId + ",publishTime:" + request.getPublishTime());
        //返回对象
        AjaxResponse<PageResponse<TeacherCloudTeachViewWrapper>> teacherCloudTeachViewWrapperAjaxResponse = new AjaxResponse<>();

        //调用服务查询出数据存放在DTO中
        PageResponse<TeacherCloudTeachViewDTO> teacherCloudTeachPageResponse = teacherCloudTeachService.queryCloudTeachList(request);
        //数据转换目标PageResponse<wrapper>对象
        PageResponse<TeacherCloudTeachViewWrapper> teacherCloudTeachViewPageResponse = new PageResponse<>();
        //数据转换中间wrapper对象
        List<TeacherCloudTeachViewWrapper> teacherCloudTeachViewWrapperlist = new ArrayList<>();

        //转换Wrapper
        for (TeacherCloudTeachViewDTO ewvDTO : teacherCloudTeachPageResponse.getRows()) {
            teacherCloudTeachViewWrapperlist.add(new TeacherCloudTeachViewWrapper(ewvDTO));
        }
        BeanUtils.copyProperties(teacherCloudTeachPageResponse, teacherCloudTeachViewPageResponse);
        teacherCloudTeachViewPageResponse.setRows(teacherCloudTeachViewWrapperlist);

        teacherCloudTeachViewWrapperAjaxResponse.setStatus("1");
        teacherCloudTeachViewWrapperAjaxResponse.setWrapper(teacherCloudTeachViewPageResponse);
        return teacherCloudTeachViewWrapperAjaxResponse;
    }

    /**
     * 根据教师所授课本判断进入作业中的哪个功能
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "initWorkPage")
    public String initWorkPage(HttpServletRequest request, ModelMap map) {
        logger.info("根据教师所授课本判断进入作业中的哪个功能");
        request.getSession().setAttribute("lastUrl", "/teacher/initWorkPage");
        Integer periodType = GetSessionContentUtil.getInstance().getTeacherPeriodType();
        if (periodType == null) {
            periodType = 0;
        }
        String url = "redirect:/exercise/item/index";

//        if(1 == periodType.intValue()){
//            //同步课堂作业
//            url = "redirect:/synclass/work/index";
//        }else if(2 == periodType.intValue()){
//            //提分宝作业
//            url = "redirect:/magic/work/index";
//        }else{
//            //电子作业
//            url = "redirect:/exercise/item/index";
//        }

        return url;
    }


    /**
     * 测试提分宝2评分功能
     * @param map
     * @return
     */
    /*@RequestMapping(value = "testmagic2score")
    public String testmagic2score(ModelMap map) {
        Magic2WorkChallengeDTO magic2WorkChallenge = new Magic2WorkChallengeDTO();
        List <Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOList = new ArrayList<>();
        Magic2StuAnsJsonDTO obj1 = new Magic2StuAnsJsonDTO();
        Magic2StuAnsJsonDTO obj2 = new Magic2StuAnsJsonDTO();
        Magic2StuAnsJsonDTO obj3 = new Magic2StuAnsJsonDTO();
        Magic2StuAnsJsonDTO obj4 = new Magic2StuAnsJsonDTO();
        Magic2StuAnsJsonDTO obj5 = new Magic2StuAnsJsonDTO();

        obj1.setQueId("10001");
        obj1.setQueTime(21000L);
        obj1.setRw(1);

        obj2.setQueId("10002");
        obj2.setQueTime(20000L);
        obj2.setRw(1);

        obj3.setQueId("10003");
        obj3.setQueTime(11000L);
        obj3.setRw(1);

        obj4.setQueId("10004");
        obj4.setQueTime(13000L);
        obj4.setRw(1);

        obj5.setQueId("10005");
        obj5.setQueTime(15000L);
        obj5.setRw(1);

        magic2StuAnsJsonDTOList.add(obj1);
        magic2StuAnsJsonDTOList.add(obj2);
        magic2StuAnsJsonDTOList.add(obj3);
        magic2StuAnsJsonDTOList.add(obj4);
        magic2StuAnsJsonDTOList.add(obj5);

        Magic2WorkChallengeDTO magic2WorkChallengeDTO = magic2WorkService.magic2WorkAutoScore(magic2WorkChallenge, magic2StuAnsJsonDTOList, 1);
        System.out.println("分数："+magic2WorkChallengeDTO.getScore()+",描述："+magic2WorkChallengeDTO.getScoreContext());
        return null;
    }*/
}