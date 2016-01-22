package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CoursearePackStatusEnum;
import net.xuele.cloudteach.dto.TeachCoursewarePackDTO;
import net.xuele.cloudteach.dto.TeachCoursewaresDTO;
import net.xuele.cloudteach.dto.UserShareInfoDTO;
import net.xuele.cloudteach.service.TeachCoursewaresService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.security.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * TeachCoursewaresController
 *
 * @author sunxh
 * @date 2015/7/22 0022
 */
@Controller
@RequestMapping("teachCoursewares")
public class TeachCoursewaresController {


    @Autowired
    private TeachCoursewaresService teachCoursewaresService;

    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresShareController.class);

    /**
     * @param map 【请求参数】
     * @return 我的课件
     */
    @RequestMapping(value = "teaching")
    public String teaching(HttpServletRequest request, ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/teachCoursewares/teaching");

        map.put("ctUserSession", SessionUtil.getUserSession());

        return "/coursewares/teaching";
    }

    /**
     * @param map 【请求参数】
     * @return 大家的分享
     */
    @RequestMapping(value = "teachingShare")
    public String teachingShare(HttpServletRequest request, ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/teachCoursewares/teaching");

        return "/coursewares/teachingShare";
    }


    /**
     * 预览
     *
     * @param map 【请求参数】
     * @return
     */
    @RequestMapping(value = "view")
    public String view(ModelMap map) {

        return "/coursewares/view";
    }

    /**
     * 根据单元ID查询课件列表
     *
     * @param unitId
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public AjaxResponse<List<TeachCoursewaresWrapper>> listMyTeachCoursewares(@RequestParam("unitId") String unitId) {
        AjaxResponse<List<TeachCoursewaresWrapper>> listAjaxResponse = new AjaxResponse<>();
        List<TeachCoursewaresWrapper> coursewaresWrapperList = new ArrayList<>();

        //获取课件列表
        List<TeachCoursewaresDTO> coursewaresDTOList = teachCoursewaresService.queryMyTeachCoursewaresList(GetSessionContentUtil.getInstance().currentSchoolID(),
                unitId, GetSessionContentUtil.getInstance().currentUserID());

        for (TeachCoursewaresDTO coursewaresDTO : coursewaresDTOList) {
            //DTO转wrapper
            TeachCoursewaresWrapper coursewaresWrapper = toTeachCoursewaresWrapper(coursewaresDTO);
            coursewaresWrapperList.add(coursewaresWrapper);
        }


        listAjaxResponse.setWrapper(coursewaresWrapperList);
        listAjaxResponse.setStatus("1");
        return listAjaxResponse;
    }

    @RequestMapping("remove")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresRemoveWrapper> removeMyTeachCourseware(@RequestParam("coursewaresId") String coursewaresId) {
        AjaxResponse<TeachCoursewaresRemoveWrapper> coursewaresRemoveWrapperAjaxResponse = new AjaxResponse<>();
        teachCoursewaresService.removeTeachCourseware(GetSessionContentUtil.getInstance().currentSchoolID(), coursewaresId, GetSessionContentUtil.getInstance().currentUserID());

        TeachCoursewaresRemoveWrapper teachCoursewaresRemoveWrapper = new TeachCoursewaresRemoveWrapper();
        teachCoursewaresRemoveWrapper.setStatus(0);
        teachCoursewaresRemoveWrapper.setCoursewaresId(coursewaresId);
        coursewaresRemoveWrapperAjaxResponse.setWrapper(teachCoursewaresRemoveWrapper);
        coursewaresRemoveWrapperAjaxResponse.setStatus("1");
        return coursewaresRemoveWrapperAjaxResponse;
    }

    @RequestMapping("sticky")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresStickyWrapper> stickyMyTeachCourseware(@RequestParam("coursewaresId") String coursewaresId) {
        AjaxResponse<TeachCoursewaresStickyWrapper> coursewaresStickyWrapperAjaxResponse = new AjaxResponse<>();
        teachCoursewaresService.stickyTeachCourseware(GetSessionContentUtil.getInstance().currentSchoolID(), coursewaresId, GetSessionContentUtil.getInstance().currentUserID());

        TeachCoursewaresStickyWrapper coursewaresStickyWrapper = new TeachCoursewaresStickyWrapper();
        coursewaresStickyWrapper.setStickStatus(1);
        coursewaresStickyWrapper.setCoursewaresId(coursewaresId);
        coursewaresStickyWrapperAjaxResponse.setWrapper(coursewaresStickyWrapper);
        coursewaresStickyWrapperAjaxResponse.setStatus("1");
        return coursewaresStickyWrapperAjaxResponse;
    }

    @RequestMapping("cancelSticky")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresStickyWrapper> cancelStickyMyTeachCourseware(@RequestParam("coursewaresId") String coursewaresId) {
        AjaxResponse<TeachCoursewaresStickyWrapper> coursewaresStickyWrapperAjaxResponse = new AjaxResponse<>();
        teachCoursewaresService.cancelStickyTeachCourseware(GetSessionContentUtil.getInstance().currentSchoolID(), coursewaresId, GetSessionContentUtil.getInstance().currentUserID());

        TeachCoursewaresStickyWrapper coursewaresStickyWrapper = new TeachCoursewaresStickyWrapper();
        coursewaresStickyWrapper.setStickStatus(0);
        coursewaresStickyWrapper.setCoursewaresId(coursewaresId);
        coursewaresStickyWrapperAjaxResponse.setWrapper(coursewaresStickyWrapper);
        coursewaresStickyWrapperAjaxResponse.setStatus("1");
        return coursewaresStickyWrapperAjaxResponse;
    }

    @RequestMapping("cancelCollect")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresRemoveWrapper> cancelCollectTeachCourseware(@RequestParam("coursewaresId") String coursewaresId) {

        AjaxResponse<TeachCoursewaresRemoveWrapper> coursewaresRemoveWrapperAjaxResponse = new AjaxResponse<>();
        teachCoursewaresService.cancelCollectTeachCourseware(GetSessionContentUtil.getInstance().currentSchoolID(), coursewaresId, GetSessionContentUtil.getInstance().currentUserID());

        TeachCoursewaresRemoveWrapper teachCoursewaresRemoveWrapper = new TeachCoursewaresRemoveWrapper();
        teachCoursewaresRemoveWrapper.setStatus(0);
        teachCoursewaresRemoveWrapper.setCoursewaresId(coursewaresId);
        coursewaresRemoveWrapperAjaxResponse.setWrapper(teachCoursewaresRemoveWrapper);
        coursewaresRemoveWrapperAjaxResponse.setStatus("1");
        return coursewaresRemoveWrapperAjaxResponse;
    }

    @RequestMapping("share")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresShareStatusWrapper> shareTeachCourseware(@RequestParam("coursewaresId") String coursewaresId, @RequestParam("shareType") Integer shareType) {
        AjaxResponse<TeachCoursewaresShareStatusWrapper> coursewaresRemoveWrapperAjaxResponse = new AjaxResponse<>();

        UserShareInfoDTO userShareInfoDTO = new UserShareInfoDTO();
        userShareInfoDTO.setItemId(coursewaresId);
        userShareInfoDTO.setShareType(shareType);
        userShareInfoDTO.setUserId(GetSessionContentUtil.getInstance().currentUserID());
        userShareInfoDTO.setUserName(SessionUtil.getUserSession().getRealName());
        userShareInfoDTO.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
        userShareInfoDTO.setSchoolName(GetSessionContentUtil.getInstance().currentSchoolName());
        userShareInfoDTO.setAreaId(GetSessionContentUtil.getInstance().currentAreaID());
        userShareInfoDTO.setAreaName(GetSessionContentUtil.getInstance().currentAreaName());


        //分享课件
        teachCoursewaresService.shareCourseware(userShareInfoDTO);

        TeachCoursewaresShareStatusWrapper coursewaresShareStatusWrapper = new TeachCoursewaresShareStatusWrapper();
        coursewaresShareStatusWrapper.setShareStatus(1);
        coursewaresShareStatusWrapper.setCoursewaresId(coursewaresId);
        coursewaresRemoveWrapperAjaxResponse.setWrapper(coursewaresShareStatusWrapper);
        coursewaresRemoveWrapperAjaxResponse.setStatus("1");
        return coursewaresRemoveWrapperAjaxResponse;
    }

    //===========================================课件打包接口 start============================================


    @RequestMapping("packstatus")
    @ResponseBody
    public AjaxResponse<TeachCoursewarePackWrapper> packStatus(@RequestParam("coursewaresId") String coursewaresId) {
        AjaxResponse<TeachCoursewarePackWrapper> coursewarePackWrapperAjaxResponse = new AjaxResponse<>();
        //查询课件打包状态

        TeachCoursewarePackWrapper coursewarePackWrapper = new TeachCoursewarePackWrapper();

        String schoolId = SessionUtil.getUserSession().getSchoolId();
        String userId = SessionUtil.getUserSession().getUserId();

        TeachCoursewarePackDTO packDTO = teachCoursewaresService.getPackStatus(coursewaresId,schoolId,userId);

        coursewarePackWrapper.setCoursewaresId(coursewaresId);
        coursewarePackWrapper.setPackStatus(packDTO.getPackStatus());
        coursewarePackWrapper.setName(packDTO.getName());
        coursewarePackWrapper.setExtension(packDTO.getExtension());
        coursewarePackWrapper.setPackUri(packDTO.getPackUri());
        coursewarePackWrapper.setPackLastUpdate(packDTO.getPackLastUpdate());
        coursewarePackWrapper.setPackExpDate(packDTO.getPackExpDate());

        coursewarePackWrapperAjaxResponse.setWrapper(coursewarePackWrapper);
        coursewarePackWrapperAjaxResponse.setStatus("1");
        return coursewarePackWrapperAjaxResponse;
    }


    @RequestMapping("pack")
    @ResponseBody
    public AjaxResponse<TeachCoursewarePackWrapper> pack(@RequestParam("coursewaresId") String coursewaresId) {
        AjaxResponse<TeachCoursewarePackWrapper> coursewarePackWrapperAjaxResponse = new AjaxResponse<>();
        //发起打包任务
        TeachCoursewarePackWrapper coursewarePackWrapper = new TeachCoursewarePackWrapper();

        String schoolId = SessionUtil.getUserSession().getSchoolId();
        String userId = SessionUtil.getUserSession().getUserId();

        TeachCoursewarePackDTO packDTO = teachCoursewaresService.pack(coursewaresId,schoolId,userId);

        coursewarePackWrapper.setCoursewaresId(coursewaresId);
        coursewarePackWrapper.setPackStatus(packDTO.getPackStatus());
        coursewarePackWrapper.setName(packDTO.getName());
        coursewarePackWrapper.setExtension(packDTO.getExtension());
        coursewarePackWrapper.setPackUri(packDTO.getPackUri());
        coursewarePackWrapper.setPackLastUpdate(packDTO.getPackLastUpdate());
        coursewarePackWrapper.setPackExpDate(packDTO.getPackExpDate());

        coursewarePackWrapperAjaxResponse.setWrapper(coursewarePackWrapper);
        coursewarePackWrapperAjaxResponse.setStatus("1");
        return coursewarePackWrapperAjaxResponse;
    }

    //===========================================课件打包接口 end  ============================================


    /**
     * 课件DTO转wrapper
     *
     * @param teachCoursewaresDTO
     * @return
     */
    public static TeachCoursewaresWrapper toTeachCoursewaresWrapper(TeachCoursewaresDTO teachCoursewaresDTO) {
        if (teachCoursewaresDTO == null) {
            return null;
        }
        TeachCoursewaresWrapper teachCoursewaresWrapper = new TeachCoursewaresWrapper();
        teachCoursewaresWrapper.setCoursewaresId(teachCoursewaresDTO.getCoursewaresId());
        teachCoursewaresWrapper.setCreator(teachCoursewaresDTO.getCreator());
        teachCoursewaresWrapper.setUnitId(teachCoursewaresDTO.getUnitId());
        teachCoursewaresWrapper.setIsCollect(teachCoursewaresDTO.getIsCollect());
        teachCoursewaresWrapper.setUserId(teachCoursewaresDTO.getUserId());
        teachCoursewaresWrapper.setPid(teachCoursewaresDTO.getPid());
        teachCoursewaresWrapper.setTitle(teachCoursewaresDTO.getTitle());
        teachCoursewaresWrapper.setStickStatus(teachCoursewaresDTO.getStickStatus());
        teachCoursewaresWrapper.setCreateTime(teachCoursewaresDTO.getCreateTime());
        teachCoursewaresWrapper.setUpdateTime(teachCoursewaresDTO.getUpdateTime());
        teachCoursewaresWrapper.setShareStatus(teachCoursewaresDTO.getShareStatus());
        teachCoursewaresWrapper.setStatus(teachCoursewaresDTO.getStatus());

        //打包状态
        teachCoursewaresWrapper.setPackStatus(teachCoursewaresDTO.getPackStatus());
        teachCoursewaresWrapper.setFileUri(teachCoursewaresDTO.getFileUri());

        return teachCoursewaresWrapper;


    }
}