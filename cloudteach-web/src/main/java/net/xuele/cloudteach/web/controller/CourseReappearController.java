package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CourseReappearPageRequest;
import net.xuele.cloudteach.service.CourseReappearService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.CourseReappearForm;
import net.xuele.cloudteach.web.wrapper.CourseReappearWrapper;
import net.xuele.cloudteach.web.wrapper.TeachCoursewaresContentWrapper;
import net.xuele.cloudteach.web.wrapper.TeachCoursewaresWrapper;
import net.xuele.common.ajax.AjaxResponse;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CourseReappearController
 * 课件
 *
 * @author cm.wang
 * @date 2015/7/17 0025
 */

@Controller
@RequestMapping(value = "/courseReappear")
public class CourseReappearController {

    @Autowired
    private CourseReappearService courseReappearService;

    private static Logger logger = LoggerFactory.getLogger(CourseReappearController.class);


    @RequestMapping(value = "/publish")
    public String index() {
        return "coursewares/publish";
    }


    @RequestMapping(value = "/getCourseReappearByUserId")
    @ResponseBody
    public PageResponse<CourseReappearWrapper> getCourseReappearByUserId(CourseReappearPageRequest courseReappearPageRequest) {
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        courseReappearPageRequest.setUserId(userId);
        PageResponse<CourseReappearDTO> dtoPageResponse = courseReappearService.getCourseReappearByUserId(courseReappearPageRequest, schoolId);
        PageResponse<CourseReappearWrapper> wrapperPageResponse = new PageResponse<CourseReappearWrapper>();
        BeanUtils.copyProperties(dtoPageResponse, wrapperPageResponse);
        List<CourseReappearDTO> dtoList = dtoPageResponse.getRows();
        List<CourseReappearWrapper> wrapperList = new ArrayList<CourseReappearWrapper>();
        if (dtoList != null) {
            for (CourseReappearDTO reappearDTO : dtoList) {
                CourseReappearWrapper wrapper = new CourseReappearWrapper();
                BeanUtils.copyProperties(reappearDTO, wrapper);
                wrapperList.add(wrapper);
            }
        }
        wrapperPageResponse.setRows(wrapperList);
        return wrapperPageResponse;
    }

    /**
     * 获得课堂全部内容
     *
     * @param reappearId
     * @return
     */
    @RequestMapping(value = "/getCourseReappearInfo")
    @ResponseBody
    public AjaxResponse<CourseReappearWrapper> getCourseReappearInfo(String reappearId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        CourseReappearInfoDTO infoDTO = courseReappearService.getCourseReappear(reappearId, schoolId);

        CourseReappearWrapper wrapper = new CourseReappearWrapper();
        List<TeachCoursewaresWrapper> teachCoursewaresWrapperList = new ArrayList<TeachCoursewaresWrapper>();
        BeanUtils.copyProperties(infoDTO.getCourseReappearDTO(), wrapper);
        List<TeachCoursewaresDTO> dtoList = infoDTO.getTeachCoursewaresDTOList();
        for (TeachCoursewaresDTO dto : dtoList) {
            TeachCoursewaresWrapper teachCoursewaresWrapper = new TeachCoursewaresWrapper();
            BeanUtils.copyProperties(dto, teachCoursewaresWrapper);
            teachCoursewaresWrapperList.add(teachCoursewaresWrapper);
        }
        wrapper.setWrapperList(teachCoursewaresWrapperList);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(wrapper);
        return ajaxResponse;
    }

    @RequestMapping(value = "/addCourseReappear")
    public String addCourseReapper(ModelMap map, CourseReappearForm courseReappearForm) {

        String classJson = courseReappearForm.getClassJson();
        String context = courseReappearForm.getContext();
        if (context == null || context.equals("")) {
            throw new CloudteachException(CloudTeachErrorEnum.COURSEWARES_CONTENT_NOT_EXIST.getMsg(),
                    CloudTeachErrorEnum.COURSEWARES_CONTENT_NOT_EXIST.getCode());
        }
        //班级不为空
        if (classJson == null || classJson.equals("[]")) {
            throw new CloudteachException(CloudTeachErrorEnum.WORK_CLASS_NULL.getMsg(),
                    CloudTeachErrorEnum.WORK_CLASS_NULL.getCode());
        }


        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String userName = GetSessionContentUtil.getInstance().currentUserName();
        String userIcon = GetSessionContentUtil.getInstance().currentUserIcon();
        String identifyId = GetSessionContentUtil.getInstance().getIdentifyId();
        if (IdentityIdConstants.SCHOOL_MANAGER.equals(identifyId)) {
            userName = "学校管理员";
        } else {
            userName = userName + "老师";
        }
        CourseReappearInfoDTO courseReappearInfoDTO = new CourseReappearInfoDTO();
        CourseReappearDTO courseReappearDTO = new CourseReappearDTO();

        BeanUtils.copyProperties(courseReappearForm, courseReappearDTO);
        courseReappearDTO.setUserId(userId);
        courseReappearDTO.setSchoolId(schoolId);
        courseReappearDTO.setPublishTime(new Date());


        List<String> classIdList = courseReappearForm.getClassList();
        List<String> coursewaresIdList = courseReappearForm.getCoursewaresIdList();
        courseReappearInfoDTO.setClassIdList(classIdList);
        courseReappearInfoDTO.setCourseReappearDTO(courseReappearDTO);
        courseReappearInfoDTO.setCoursewaresIdList(coursewaresIdList);
        courseReappearInfoDTO.setClassJson(classJson);

        String url = null;
        String response = null;
        try {
            response = courseReappearService.addCourseReapper(courseReappearInfoDTO, null, userName, userIcon);
        } catch (CloudteachException e) {
            return url = "/coursewares/fail";
        }

        if ("".equals(response)) {
            url = "/coursewares/success";
        } else {
            url = "/coursewares/fail";
            map.put("error", response);
        }
        return url;
    }

    /**
     * 登陆用户查看具体的课件内容
     *
     * @param rcId
     * @return
     */
    @RequestMapping(value = "/getTeachCoursewaresContent")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresContentWrapper> getTeachCoursewaresContentByRcId(String rcId, String reappearId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        TeachCoursewaresContentDTO contentDTO = courseReappearService.getTeachCoursewaresContentByRcId(userId, rcId, reappearId, schoolId);
        TeachCoursewaresContentWrapper wrapper = new TeachCoursewaresContentWrapper();
        BeanUtils.copyProperties(contentDTO, wrapper);
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(wrapper);
        return ajaxResponse;
    }

    @RequestMapping(value = "/delCourseReappear")
    @ResponseBody
    public AjaxResponse delCourseReappear(String reappearId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        courseReappearService.delCourseReappear(reappearId, schoolId);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    @RequestMapping(value = "/editCourseReappear")
    @ResponseBody
    public AjaxResponse editCourseReappear(CourseReappearForm courseReappearForm) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String classJson = courseReappearForm.getClassJson();

        CourseReappearInfoDTO courseReappearInfoDTO = new CourseReappearInfoDTO();
        CourseReappearDTO courseReappearDTO = new CourseReappearDTO();
        BeanUtils.copyProperties(courseReappearDTO, courseReappearForm);
        courseReappearDTO.setUserId(userId);
        courseReappearDTO.setSchoolId(schoolId);
        courseReappearDTO.setPublishTime(new Date());
        List<String> classIdList = courseReappearForm.getClassList();
        List<String> coursewaresIdList = courseReappearForm.getCoursewaresIdList();
        courseReappearInfoDTO.setClassIdList(classIdList);
        courseReappearInfoDTO.setCourseReappearDTO(courseReappearDTO);
        courseReappearInfoDTO.setCoursewaresIdList(coursewaresIdList);
        courseReappearInfoDTO.setClassJson(classJson);

        courseReappearService.editCourseReappear(courseReappearInfoDTO);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}