package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.dto.TeachCoursewareLogDTO;
import net.xuele.cloudteach.dto.TeachCoursewareLogDetailDTO;
import net.xuele.cloudteach.service.TeachCoursewareLogService;
import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;
import net.xuele.cloudteach.web.form.TeachCoursewareLogDetailForm;
import net.xuele.cloudteach.web.form.TeachCoursewareLogForm;
import net.xuele.cloudteach.web.wrapper.TeachCoursewareLogDetailWrapper;
import net.xuele.cloudteach.web.wrapper.TeachCoursewareLogWrapper;
import net.xuele.common.ajax.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * TeachCoursewaresLogExtFLController
 * 授课打点控制器
 * 向flash提供接口，异常不会抛出，只反馈为状态
 *
 * @author sunxh
 * @date 15/12/2
 */
@Controller
@RequestMapping("teachlog")
public class TeachCoursewaresLogExtFLController {

    @Autowired
    private TeachCoursewareLogService teachCoursewareLogService;

    private static Logger logger = LoggerFactory.getLogger(TeachCoursewaresLogExtFLController.class);

    /**
     * 获取某个课件所有授课记录
     * ---------
     * 传入参数 schoolId 学校ID [非空]
     * 传入参数 cclCoursewaresId 课件ID [非空]
     *
     * @return List<TeachCoursewareLogWrapper>
     */
    @ResponseBody
    @RequestMapping("list")
    public AjaxResponse<List<TeachCoursewareLogWrapper>> listByCourseware(TeachCoursewareLogForm teachCoursewareLogForm) {
        AjaxResponse<List<TeachCoursewareLogWrapper>> ajaxResponse = new AjaxResponse<>();
        List<TeachCoursewareLogWrapper> coursewareLogWrapperList = new ArrayList<>();
        logger.warn("收到授课记录列表请求，开始处理：teachCoursewareLogForm={} ", teachCoursewareLogForm);

        try {
            //获取记录DTO列表
            List<TeachCoursewareLogDTO> coursewareLogDTOs = teachCoursewareLogService.listByCourseware(teachCoursewareLogForm.getCclCoursewaresId(), teachCoursewareLogForm.getSchoolId(),teachCoursewareLogForm.getCclClassId());
            logger.warn("授课记录列表请求处理完成：coursewareLogDTOs={} ", coursewareLogDTOs);

            //DTO列表转wrapper
            for (TeachCoursewareLogDTO coursewareLogDTO : coursewareLogDTOs) {
                TeachCoursewareLogWrapper logWrapper = toTeachCoursewareLogWrapper(coursewareLogDTO);
                coursewareLogWrapperList.add(logWrapper);
            }

            ajaxResponse.setWrapper(coursewareLogWrapperList);
            ajaxResponse.setStatus("1");

        } catch (Exception e) {
            logger.error("记录授课开始发生异常", e);
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMessage());
        }

        return ajaxResponse;
    }

    /**
     * 获取某个授课记录的所有明细信息
     * ---------
     * 传入参数 schoolId 学校ID [非空]
     * 传入参数 cclId 授课记录ID [非空]
     *
     * @return List<TeachCoursewareLogWrapper>
     */
    @ResponseBody
    @RequestMapping("detail/list")
    public AjaxResponse<List<TeachCoursewareLogDetailWrapper>> listDetailByLog(TeachCoursewareLogDetailForm teachCoursewareLogDetailForm) {
        //根据记录ID获取明细
        AjaxResponse<List<TeachCoursewareLogDetailWrapper>> ajaxResponse = new AjaxResponse<>();
        List<TeachCoursewareLogDetailWrapper> logDetailWrapperList = new ArrayList<>();
        logger.warn("收到授课记录明细列表请求，开始处理：teachCoursewareLogDetailForm={} ", teachCoursewareLogDetailForm);

        try {

            //获取授课记录明细列表
            List<TeachCoursewareLogDetailDTO> logDetailDTOs = teachCoursewareLogService.listDetailByLogId(teachCoursewareLogDetailForm.getCclId(), teachCoursewareLogDetailForm.getSchoolId());
            logger.warn("授课记录列表请求处理完成：logDetailDTOs={} ", logDetailDTOs);

            //DTO列表转wrapper
            for (TeachCoursewareLogDetailDTO logDetailDTO : logDetailDTOs) {
                TeachCoursewareLogDetailWrapper logDetailWrapper = toTeachCoursewareLogDetailWrapper(logDetailDTO);
                logDetailWrapperList.add(logDetailWrapper);
            }

            ajaxResponse.setWrapper(logDetailWrapperList);
            ajaxResponse.setStatus("1");

        } catch (Exception e) {
            logger.error("记录授课开始发生异常", e);
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMessage());
        }

        return ajaxResponse;
    }

    /**
     * 记录授课开始
     * --------
     * 由flash在授课开始时发起请求
     * 系统记录开始时间为当前时间【取服务器系统时间】
     * 并写入数据库
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("begin")
    public AjaxResponse<TeachCoursewareLogWrapper> logTeachBegin(TeachCoursewareLogForm teachCoursewareLogForm) {
        AjaxResponse<TeachCoursewareLogWrapper> ajaxResponse = new AjaxResponse<>();
        TeachCoursewareLogWrapper coursewareLogWrapper = new TeachCoursewareLogWrapper();

        try {
            //转DTO
            TeachCoursewareLogDTO coursewareLogDTO = toTeachCoursewareLogDTO(teachCoursewareLogForm);
            logger.warn("收到授课打点数据请求，开始处理：coursewareLogDTO={} ", coursewareLogDTO);

            //记录授课开始
            coursewareLogDTO = teachCoursewareLogService.logTeachingBegin(coursewareLogDTO);
            logger.warn("授课打点数据请求处理完成：coursewareLogDTO={} ", coursewareLogDTO);

            //组装响应数据
            coursewareLogWrapper = toTeachCoursewareLogWrapper(coursewareLogDTO);
            ajaxResponse.setWrapper(coursewareLogWrapper);
            ajaxResponse.setStatus("1");

        } catch (Exception e) {
            logger.error("记录授课开始发生异常", e);
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMessage());
        }

        return ajaxResponse;
    }


    /**
     * 记录授课进行状态
     * --------
     * 由flash在授课时持续发起请求 比如每分钟一次
     * 系统记录结束时间为当前时间【取服务器系统时间】
     * 并写入数据库
     */
    @ResponseBody
    @RequestMapping("teaching")
    public AjaxResponse logTeaching(TeachCoursewareLogForm teachCoursewareLogForm) {
        AjaxResponse ajaxResponse = new AjaxResponse();

        try {
            //转DTO
            TeachCoursewareLogDTO coursewareLogDTO = toTeachCoursewareLogDTO(teachCoursewareLogForm);
            logger.warn("收到授课状态记录请求，开始处理：coursewareLogDTO={} ", coursewareLogDTO);

            //记录授课开始
            teachCoursewareLogService.logTeaching(coursewareLogDTO);
            logger.warn("授课状态记录请求处理完成：coursewareLogDTO={} ", coursewareLogDTO);

            ajaxResponse.setStatus("1");
        } catch (Exception e) {
            logger.error("记录授课进行发生异常", e);
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMessage());
        }

        return ajaxResponse;
    }


    /**
     * 记录授课明细
     * --------
     * 由flash在授课操作时发起请求 比如随堂
     * 系统记录结束时间为当前时间【取服务器系统时间】
     * 并写入数据库
     */
    @ResponseBody
    @RequestMapping(value = "detail/write")
    public AjaxResponse writeLogDetail(TeachCoursewareLogDetailForm teachCoursewareLogDetailForm) {
        AjaxResponse ajaxResponse = new AjaxResponse();

        try {
            //转DTO
            TeachCoursewareLogDetailDTO coursewareLogDetailDTO = toTeachCoursewareLogDetailDTO(teachCoursewareLogDetailForm);
            logger.warn("收到授课明细数据请求，开始处理：coursewareLogDetailDTO={} ", coursewareLogDetailDTO);

            //记录授课明细信息
            teachCoursewareLogService.writeLogDetail(coursewareLogDetailDTO);
            logger.warn("授课明细数据请求处理完成：coursewareLogDetailDTO={} ", coursewareLogDetailDTO);

            ajaxResponse.setStatus("1");
        } catch (Exception e) {
            logger.error("记录授课明细发生异常", e);
            ajaxResponse.setStatus("0");
            ajaxResponse.setErrorMsg(e.getMessage());
        }

        return ajaxResponse;
    }


    //================================================private 方法=================================================

    /**
     * 授课记录Form转DTO
     *
     * @param teachCoursewareLogForm 授课记录Form
     * @return TeachCoursewareLogDTO
     */
    private TeachCoursewareLogDTO toTeachCoursewareLogDTO(TeachCoursewareLogForm teachCoursewareLogForm) {
        if (teachCoursewareLogForm == null) {
            return null;
        }
        Assert.hasText(teachCoursewareLogForm.getCclCoursewaresId(), "课件Id不能为空!");
        Assert.hasText(teachCoursewareLogForm.getRandomId(), "randomId不能为空!");

        TeachCoursewareLogDTO teachCoursewareLogDTO = new TeachCoursewareLogDTO();
        //授课记录ID由课件ID和flash传入的随机码组装
        teachCoursewareLogDTO.setCclId(teachCoursewareLogForm.getCclCoursewaresId() + teachCoursewareLogForm.getRandomId());
        teachCoursewareLogDTO.setCclCoursewaresId(teachCoursewareLogForm.getCclCoursewaresId());
        teachCoursewareLogDTO.setCclUserId(teachCoursewareLogForm.getCclUserId());
        teachCoursewareLogDTO.setCclClassId(teachCoursewareLogForm.getCclClassId());
        teachCoursewareLogDTO.setSchoolId(teachCoursewareLogForm.getSchoolId());
        return teachCoursewareLogDTO;
    }

    /**
     * 授课记录明细FORM转DTO
     *
     * @param teachCoursewareLogDetailForm 授课记录明细
     * @return TeachCoursewareLogDetailDTO
     */
    private TeachCoursewareLogDetailDTO toTeachCoursewareLogDetailDTO(TeachCoursewareLogDetailForm teachCoursewareLogDetailForm) {
        if (teachCoursewareLogDetailForm == null) {
            return null;
        }
        Assert.hasText(teachCoursewareLogDetailForm.getSchoolId(), "学校Id不能为空!");
        Assert.hasText(teachCoursewareLogDetailForm.getCclCoursewaresId(), "课件Id不能为空!");
        Assert.hasText(teachCoursewareLogDetailForm.getRandomId(), "randomId不能为空!");

        TeachCoursewareLogDetailDTO teachCoursewareLogDetailDTO = new TeachCoursewareLogDetailDTO();
//        teachCoursewareLogDetailDTO.setCcdlId(teachCoursewareLogDetailForm.getCcdlId());
        //授课记录ID由课件ID和flash传入的随机码组装
        teachCoursewareLogDetailDTO.setCclId(teachCoursewareLogDetailForm.getCclCoursewaresId() + teachCoursewareLogDetailForm.getRandomId());
        teachCoursewareLogDetailDTO.setCcdlType(teachCoursewareLogDetailForm.getCcdlType());
        teachCoursewareLogDetailDTO.setCcdlContext(teachCoursewareLogDetailForm.getCcdlContext());
        teachCoursewareLogDetailDTO.setCcdlBegtime(teachCoursewareLogDetailForm.getCcdlBegtime());
        teachCoursewareLogDetailDTO.setCcdlEndtime(teachCoursewareLogDetailForm.getCcdlEndtime());
        teachCoursewareLogDetailDTO.setCcdlContent1(teachCoursewareLogDetailForm.getCcdlContent1());
        teachCoursewareLogDetailDTO.setCcdlContent2(teachCoursewareLogDetailForm.getCcdlContent2());
        teachCoursewareLogDetailDTO.setCcdlContent3(teachCoursewareLogDetailForm.getCcdlContent3());
        teachCoursewareLogDetailDTO.setSchoolId(teachCoursewareLogDetailForm.getSchoolId());
        return teachCoursewareLogDetailDTO;
    }

    /**
     * 授课记录DTO转wrapper
     *
     * @param coursewareLogDTO 授课记录DTO
     * @return TeachCoursewareLogWrapper
     */
    private TeachCoursewareLogWrapper toTeachCoursewareLogWrapper(TeachCoursewareLogDTO coursewareLogDTO) {
        if (coursewareLogDTO == null) {
            return null;
        }
        TeachCoursewareLogWrapper teachCoursewareLogWrapper = new TeachCoursewareLogWrapper();
        teachCoursewareLogWrapper.setCclId(coursewareLogDTO.getCclId());
        teachCoursewareLogWrapper.setCclCoursewaresId(coursewareLogDTO.getCclCoursewaresId());
        teachCoursewareLogWrapper.setCclUserId(coursewareLogDTO.getCclUserId());
        teachCoursewareLogWrapper.setCclClassId(coursewareLogDTO.getCclClassId());
        teachCoursewareLogWrapper.setSchoolId(coursewareLogDTO.getSchoolId());
        teachCoursewareLogWrapper.setCclBegtime(coursewareLogDTO.getCclBegtime());
        teachCoursewareLogWrapper.setCclEndtime(coursewareLogDTO.getCclEndtime());
        teachCoursewareLogWrapper.setCclFromType(coursewareLogDTO.getCclFromType());
        return teachCoursewareLogWrapper;
    }

    /**
     * 授课记录明细DTO转wrapper
     *
     * @param logDetailDTO 授课记录明细DTO
     * @return TeachCoursewareLogDetailWrapper
     */
    private TeachCoursewareLogDetailWrapper toTeachCoursewareLogDetailWrapper(TeachCoursewareLogDetailDTO logDetailDTO) {
        if (logDetailDTO == null) {
            return null;
        }
        TeachCoursewareLogDetailWrapper teachCoursewareLogDetailWrapper = new TeachCoursewareLogDetailWrapper();
        teachCoursewareLogDetailWrapper.setCcdlId(logDetailDTO.getCcdlId());
        teachCoursewareLogDetailWrapper.setCclId(logDetailDTO.getCclId());
        teachCoursewareLogDetailWrapper.setCcdlType(logDetailDTO.getCcdlType());
        teachCoursewareLogDetailWrapper.setCcdlContext(logDetailDTO.getCcdlContext());
        teachCoursewareLogDetailWrapper.setCcdlBegtime(logDetailDTO.getCcdlBegtime());
        teachCoursewareLogDetailWrapper.setCcdlEndtime(logDetailDTO.getCcdlEndtime());
        teachCoursewareLogDetailWrapper.setCcdlContent1(logDetailDTO.getCcdlContent1());
        teachCoursewareLogDetailWrapper.setCcdlContent2(logDetailDTO.getCcdlContent2());
        teachCoursewareLogDetailWrapper.setCcdlContent3(logDetailDTO.getCcdlContent3());
        teachCoursewareLogDetailWrapper.setSchoolId(logDetailDTO.getSchoolId());
        return teachCoursewareLogDetailWrapper;
    }

}
