package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.dto.ClassFbPackageDTO;
import net.xuele.cloudteach.dto.ClassFeedbackDTO;
import net.xuele.cloudteach.dto.ClassFeedbackViewDTO;
import net.xuele.cloudteach.dto.page.ClassFeedbackPageRequest;
import net.xuele.cloudteach.service.ClassFeedbackService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.ClassFBViewWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import net.xuele.member.dto.StudentManagerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassFeedbackController
 *
 * @author panglx
 * @date on 2015/10/15 0015.
 */
@Controller
@RequestMapping(value = "class/feedback")
public class ClassFeedbackController {

	@Autowired
	ClassFeedbackService classFeedbackService;

	private static Logger logger = LoggerFactory.getLogger(ClassFeedbackController.class);

	/**
	 * @param map 【请求参数】
	 * @return 我的课件
	 */
	@RequestMapping(value = "index")
	public String teaching(HttpServletRequest request,ModelMap map) {

		/** 二级导航菜单定位 */
		request.getSession().setAttribute("lastUrl", "/teachCoursewares/teaching");

		return "/coursewares/classfeedback";
	}

	/**
	 * 分页获取随堂反馈资源
	 * @param request
	 * 必输参数：(unitId,classId)
	 * @return
	 */
	@RequestMapping(value = "pageList")
	@ResponseBody
	public AjaxResponse<PageResponse<ClassFBViewWrapper>> queryTeachCoursewaresShareByPage(ClassFeedbackPageRequest request){
		AjaxResponse<PageResponse<ClassFBViewWrapper>> ajaxResponse = new AjaxResponse<>();

		/**从session中获取用户id，学校id*/
		//request.setUserId("1000110002");
		//request.setSchoolId("10001");
		request.setUserId(GetSessionContentUtil.getInstance().currentUserID());
		request.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());

		PageResponse<ClassFeedbackViewDTO> dtoPageResponse = classFeedbackService.queryPageList(request);

		List<ClassFeedbackViewDTO> classFeedbackViewDTOs = dtoPageResponse.getRows();
		logger.info("DTO对象转为Wrapper");
		List<ClassFBViewWrapper> classFBViewWrappers = new ArrayList<>();
		ClassFBViewWrapper wrapper;
		for (ClassFeedbackViewDTO dto:classFeedbackViewDTOs){
			wrapper = new ClassFBViewWrapper();
			BeanUtils.copyProperties(dto,wrapper);
			classFBViewWrappers.add(wrapper);
		}
		logger.info("返回前端PageResponse");
		PageResponse<ClassFBViewWrapper> resultPageResponse = new PageResponse<>();
		BeanUtils.copyProperties(dtoPageResponse,resultPageResponse);
		resultPageResponse.setRows(classFBViewWrappers);
		ajaxResponse.setWrapper(resultPageResponse);
		ajaxResponse.setStatus("1");//获取数据成功
		return ajaxResponse;

	}

	@RequestMapping(value = "remove")
	@ResponseBody
	public AjaxResponse removeFile(@RequestParam("fbId") String fbId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		/**测试数据*/
		//String userId = "1000110002";
		//String schoolId = "10001";
		/**从session中获取用户id，学校id*/
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

		classFeedbackService.removeFile(fbId,userId,schoolId);
		ajaxResponse.setStatus("1");
		ajaxResponse.setWrapper(fbId);
		return ajaxResponse;
	}
	/********************************************测试***************************************************/
	@RequestMapping(value = "getCFbClassList")
	@ResponseBody
	public AjaxResponse<ClassFbPackageDTO>  getCFbClassList(){

		AjaxResponse ajaxResponse = new AjaxResponse();

		String userId = "1030610004";
		classFeedbackService.setClassFBCoursewaresId(userId,"a6a5311446694ddbab8413172deb67d8");
		ClassFbPackageDTO classFbPackageDTO = classFeedbackService.getCFbClassList(userId);

		ajaxResponse.setStatus("1");
		ajaxResponse.setWrapper(classFbPackageDTO);

		return ajaxResponse;
	}

	@RequestMapping(value = "getCFbStudentList")
	@ResponseBody
	public AjaxResponse<List<StudentManagerDTO>>  getCFbStudentList(){
		AjaxResponse ajaxResponse = new AjaxResponse();

		String classId = "be4e6a2d424b11e5825844a8421dc7b3";
		List<StudentManagerDTO> studentList = classFeedbackService.getCFbStudentList(classId);

		ajaxResponse.setStatus("1");
		ajaxResponse.setWrapper(studentList);

		return ajaxResponse;
	}

	/**
	 * 参入参数：studentId 学生id；unitId 课程id；classId 班级id；className 班级名称；uploadUserId 教师id；
	 *          filekey HDFS文件uri；fileName 文件名；extension 扩展名；size 文件大小；
	 * @return
	 */
	@RequestMapping(value = "uploadCFbFile")
	@ResponseBody
	public AjaxResponse uploadCFbFile(){
		AjaxResponse ajaxResponse = new AjaxResponse();
		List<ClassFeedbackDTO> classFeedbackDTOs = new ArrayList<>();
		ClassFeedbackDTO classFeedbackDTO = new ClassFeedbackDTO();
		classFeedbackDTO.setStudentId("1030610003");
		classFeedbackDTO.setUnitId("010003001001001001001");
		classFeedbackDTO.setClassId("be4e755a424b11e5825844a8421dc7b3");
		classFeedbackDTO.setClassName("三年级(1)班");
		classFeedbackDTO.setUploadUserId("1030610004");
		classFeedbackDTO.setFilekey("0004");
		classFeedbackDTO.setFileName("0004");
		classFeedbackDTO.setExtension("jpg");
		classFeedbackDTO.setSize(100);
		classFeedbackDTOs.add(classFeedbackDTO);
		String result = classFeedbackService.uploadCFbFile(classFeedbackDTOs);

		ajaxResponse.setStatus("1");
		ajaxResponse.setWrapper(result);

		return ajaxResponse;
	}
}
