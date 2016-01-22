package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.dto.CoursewaresResponseDTO;
import net.xuele.cloudteach.dto.TeachCoursewaresShareDTO;
import net.xuele.cloudteach.dto.page.TeachCoursewaresShareRequest;
import net.xuele.cloudteach.service.TeachCoursewaresShareService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.CoursewaresResponseWrapper;
import net.xuele.cloudteach.web.wrapper.SharePraiseWrapper;
import net.xuele.cloudteach.web.wrapper.TeachCoursewaresShareCollectWrapper;
import net.xuele.cloudteach.web.wrapper.TeachCoursewaresShareWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 大家分享的授课课件Controller
 * Created by panglx on 2015/7/17 0017.
 */
@Controller
@RequestMapping(value = "teachCoursewares/share")
public class TeachCoursewaresShareController {
    @Autowired
    private TeachCoursewaresShareService teachCoursewaresShareService;

    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresShareController.class);

    /**
     * 预览
     *
     * @param map 【请求参数】
     * @return
     */
    @RequestMapping(value = "view")
    public String viewShare(ModelMap map) {

        return "/coursewares/viewshare";
    }

    /**
     * 分页获取大家分享的授课课件
     * @param request
     * @return
     */
    @RequestMapping(value = "pageList")
    @ResponseBody
    public AjaxResponse<PageResponse<TeachCoursewaresShareWrapper>> queryTeachCoursewaresShareByPage(TeachCoursewaresShareRequest request){
        logger.info("分页获取大家分享的授课课件：queryTeachCoursewaresShareByPage request："+request);
        AjaxResponse<PageResponse<TeachCoursewaresShareWrapper>> ajaxResponse = new AjaxResponse<>();
        /**从session中获取用户id，学校id，地区id*/
        request.setCreator(GetSessionContentUtil.getInstance().currentUserID());
        request.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
        request.setAreaId(GetSessionContentUtil.getInstance().currentAreaID());

        //调用服务获取大家分享的授课课件
        PageResponse<TeachCoursewaresShareDTO> dtoPageResponse = teachCoursewaresShareService.queryTCoursewaresShareResource(request);
        List<TeachCoursewaresShareDTO> coursewaresShareDTOs = dtoPageResponse.getRows();
        logger.info("DTO对象转为Wrapper");
        List<TeachCoursewaresShareWrapper> coursewaresShareWrappers = new ArrayList<>();
        TeachCoursewaresShareWrapper wrapper;
        for (TeachCoursewaresShareDTO dto:coursewaresShareDTOs){
            wrapper = new TeachCoursewaresShareWrapper();
            BeanUtils.copyProperties(dto,wrapper);
            coursewaresShareWrappers.add(wrapper);
        }
        logger.info("返回前端PageResponse");
        PageResponse<TeachCoursewaresShareWrapper> resultPageResponse = new PageResponse<>();
        BeanUtils.copyProperties(dtoPageResponse,resultPageResponse);
        resultPageResponse.setRows(coursewaresShareWrappers);
        ajaxResponse.setWrapper(resultPageResponse);
        ajaxResponse.setStatus("1");//获取数据成功
        return ajaxResponse;
    }

    /**
     * 点赞大家分享的授课课件
     * @param shareId
     * @return
     */
    @RequestMapping(value = "praise")
    @ResponseBody
    public AjaxResponse<SharePraiseWrapper> praiseTeachCoursewaresShare(String shareId){
        logger.info("点赞大家分享的授课课件：praiseTeachCoursewaresShare shareId："+shareId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();

        AjaxResponse<SharePraiseWrapper> response = new AjaxResponse<>();
        //调用服务点赞大家分享的授课课件
        teachCoursewaresShareService.praise(shareId,userId);
        /**返回Wrapper*/
        SharePraiseWrapper wrapper = new SharePraiseWrapper();
        wrapper.setShareId(shareId);
        wrapper.setPraiseState(1);//点赞状态1--已赞
        response.setWrapper(wrapper);
        response.setStatus("1");
        return response;
    }

    /**
     * 取消点赞大家分享的授课课件
     * @param shareId
     * @return
     */
    @RequestMapping(value = "cancelPraise")
    @ResponseBody
    public AjaxResponse<SharePraiseWrapper> cancelPraise(String shareId){
        logger.info("取消点赞大家分享的授课课件：cancelPraise shareId："+shareId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();

        AjaxResponse<SharePraiseWrapper> response = new AjaxResponse<>();
        //调用服务取消点赞大家分享的授课课件
        teachCoursewaresShareService.cancelPraise(shareId,userId);
        /**返回Wrapper*/
        SharePraiseWrapper wrapper = new SharePraiseWrapper();
        wrapper.setShareId(shareId);
        wrapper.setPraiseState(0);//点赞状态0--未赞
        response.setWrapper(wrapper);
        response.setStatus("1");
        return response;
    }

    /**
     * @param shareId 【请求参数】
     * @return 收藏大家分享的授课课件
     */
    @RequestMapping(value = "collect")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresShareCollectWrapper> collectCoursewaresShare(String shareId){
        logger.info("收藏大家分享的授课课件：collectCoursewaresShare shareId："+shareId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        AjaxResponse<TeachCoursewaresShareCollectWrapper> response = new AjaxResponse<>();
        //调用服务收藏大家分享的授课课件
        String coursewaresId = teachCoursewaresShareService.collectItem(shareId,userId,schoolId);
        TeachCoursewaresShareCollectWrapper wrapper = new TeachCoursewaresShareCollectWrapper();
        wrapper.setCollectStatus(1);//收藏状态改为1--已收藏
        wrapper.setShareId(shareId);//分享id
        wrapper.setCoursewaresId(coursewaresId);//课件id

        response.setWrapper(wrapper);
        response.setStatus("1");
        return response;
    }

    /**
     * @param shareId 【请求参数】
     * @return 取消收藏课件
     */
    @RequestMapping(value = "uncollect")
    @ResponseBody
    public AjaxResponse<TeachCoursewaresShareCollectWrapper> cancelCollect(String shareId){
        logger.info("取消收藏大家分享的授课课件：cancelCollect shareId："+shareId);
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

        AjaxResponse<TeachCoursewaresShareCollectWrapper> response = new AjaxResponse<>();
        //调用服务取消收藏大家分享的授课课件
        String coursewaresId = teachCoursewaresShareService.cancelCollect(shareId,userId,schoolId);

        TeachCoursewaresShareCollectWrapper wrapper = new TeachCoursewaresShareCollectWrapper();
        wrapper.setCollectStatus(0);//收藏状态改为0--未收藏
        wrapper.setShareId(shareId);//分享id
        wrapper.setCoursewaresId(coursewaresId);//课件id

        response.setWrapper(wrapper);
        response.setStatus("1");
        return response;
    }

    /**
     * 取消分享
     * @param shareId
     * @return
     */
    @RequestMapping(value = "cancelShare")
    @ResponseBody
    public AjaxResponse cancelShare(String shareId){
        logger.info("取消分享大家分享的授课课件：cancelShare shareId："+shareId);
        AjaxResponse ajaxResponse = new AjaxResponse();
        //从session中获取schoolId
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        //调用服务取消分享
        String coursewaresId = teachCoursewaresShareService.cancelShare(shareId, userId ,schoolId);
        ajaxResponse.setWrapper(coursewaresId);
        //成功返回1
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 预览
     * @param shareId
     * @return
     */
    @RequestMapping(value = "preview")
    @ResponseBody
    public AjaxResponse<CoursewaresResponseWrapper> preview(String shareId){
        logger.info("预览大家分享的授课课件：preview shareId："+shareId);
        AjaxResponse<CoursewaresResponseWrapper> ajaxResponse = new AjaxResponse();
        //从session中获取userId
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        //调用服务取消分享
        CoursewaresResponseDTO responseDTO = teachCoursewaresShareService.preview(shareId,userId);

        CoursewaresResponseWrapper wrapper = new CoursewaresResponseWrapper();

        BeanUtils.copyProperties(responseDTO,wrapper);

        ajaxResponse.setWrapper(wrapper);
        //成功返回1
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
}