package net.xuele.cloudteach.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.UnitsDTO;
import net.xuele.cloudteach.dto.WorkStatisticsViewDTO;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.WorkStatisticsService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.CloudUnitWrapper;
import net.xuele.cloudteach.web.wrapper.UnitRecord;
import net.xuele.cloudteach.web.wrapper.UserInfoWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by panglx on 2015/7/3 0003.
 */
@Controller
@RequestMapping(value = "cloudTeach")
public class CloudTeachController {
	private static Logger logger = LoggerFactory.getLogger(CloudTeachController.class);
	@Autowired
	private CloudTeachService cloudTeachService;

	@Autowired
	private WorkStatisticsService workStatisticsService;

	@RequestMapping("user")
	@ResponseBody
	public AjaxResponse<UserInfoWrapper> getUserInfo(){
		AjaxResponse<UserInfoWrapper> userInfoWrapperAjaxResponse = new AjaxResponse<>();
		UserInfoWrapper userInfoWrapper = new UserInfoWrapper();
		userInfoWrapper.setUserId(GetSessionContentUtil.getInstance().currentUserID());
		userInfoWrapper.setUserName(GetSessionContentUtil.getInstance().currentUserName());
		userInfoWrapper.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
		userInfoWrapper.setSchoolName(GetSessionContentUtil.getInstance().currentSchoolName());
		userInfoWrapper.setAreaId(GetSessionContentUtil.getInstance().currentAreaID());
		userInfoWrapper.setAreaName(GetSessionContentUtil.getInstance().currentAreaName());

		userInfoWrapperAjaxResponse.setWrapper(userInfoWrapper);
		userInfoWrapperAjaxResponse.setStatus("1");
		return userInfoWrapperAjaxResponse;
	}

	@RequestMapping(value = "units")
	@ResponseBody
	public AjaxResponse<CloudUnitWrapper> selectUnits(){
		logger.info("获取云教学左侧课程树");
		String bookId = GetSessionContentUtil.getInstance().currentBookID();

		AjaxResponse<CloudUnitWrapper> ajaxResponse = new AjaxResponse<CloudUnitWrapper>();

		try{
			List<UnitsDTO> unitsDTOList = cloudTeachService.selectUnits(bookId);

			CloudUnitWrapper cloudUnitWrapper = new CloudUnitWrapper();
			cloudUnitWrapper.setTotal(unitsDTOList.size());

			List<UnitRecord> resultList = new ArrayList<>();//保存单元级别的树
			List<UnitRecord> tmpList = null;//保存课程级别的树
			UnitRecord tmpUR = null;//保存临时的单元对象
			for(UnitsDTO r:unitsDTOList){
				if(r.getUnitType()==1){
					// 上一个tmpUR插入到list
					if(tmpUR != null){
						tmpUR.setRecord(tmpList);
						resultList.add(tmpUR);
					}
					// 更新当前tmpUR
					tmpUR = new UnitRecord();
					tmpList = new ArrayList<>();
					tmpUR.setUnitId(r.getUnitId());
					tmpUR.setUnitName(r.getUnitName());
				}else{
					if (tmpList == null){
						tmpList = new ArrayList<>();
					}
					UnitRecord ur0 = new UnitRecord();
					// type 2 插入到当前tmpUR的list
					ur0.setUnitId(r.getUnitId());
					ur0.setUnitName(r.getUnitName());
					tmpList.add(ur0);
				}
			}
			tmpUR.setRecord(tmpList);
			resultList.add(tmpUR);

			cloudUnitWrapper.setUnitRecordList(resultList);
			ajaxResponse.setStatus("1");
			ajaxResponse.setWrapper(cloudUnitWrapper);
		}catch (Exception e){
			logger.error("bookId:"+bookId+"取课程信息出错");
			ajaxResponse.setStatus("0");
		}

		return ajaxResponse;
	}

	/**
	 * @param gradeNum  年级
	 * @return AjaxResponse
	 * 获取教师某个年级的授课班级
	 */
	@RequestMapping(value = "getClassByGrade")
	@ResponseBody
	public AjaxResponse getClassByGrade(@RequestParam("gradeNum") Integer gradeNum) {
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setStatus("1");
		if(userId == null || "".equals(userId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		if(gradeNum == null || gradeNum.intValue()<=0){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.PARAMERROR.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		try{
			List<ClassInfoDTO> classList = cloudTeachService.selectTeacherGradeClassList(userId, gradeNum.intValue());
			ajaxResponse.setWrapper(classList);
		}catch (CloudteachException e){
			ajaxResponse.setErrorMsg(e.getMsg());
			ajaxResponse.setStatus("0");
		}
		return ajaxResponse;
	}

	/**
	 * @param workId  作业ID
	 * @return AjaxResponse
	 * 获取某个作业的学生提交、完成数统计信息
	 */
	@RequestMapping(value = "getWorkStuStatistics")
	@ResponseBody
	public AjaxResponse getWorkStuStatistics(@RequestParam("workId") String workId) {
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setStatus("1");
		if(userId == null || "".equals(userId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		if(StringUtils.isEmpty(workId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.PARAMERROR.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		try{
			WorkStatisticsViewDTO workStatisticsViewDTO =workStatisticsService.selectWorkStatistics(workId, schoolId);
			ajaxResponse.setWrapper(workStatisticsViewDTO);
		}catch (CloudteachException e){
			ajaxResponse.setErrorMsg(e.getMsg());
			ajaxResponse.setStatus("0");
		}
		return ajaxResponse;
	}

	/**
	 * @param workId  作业ID
	 * @param classId 班级ID
	 * @return AjaxResponse
	 * 获取某个作业下某个班级的学生总数
	 */
	@RequestMapping(value = "getWorkClassStuNum")
	@ResponseBody
	public AjaxResponse getWorkClassStuNum(@RequestParam("workId") String workId,@RequestParam("classId") String classId) {
		logger.info("获取某个作业下某个班级的学生总数");
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setStatus("1");
		if(userId == null || "".equals(userId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		if(StringUtils.isEmpty(workId) || StringUtils.isEmpty(classId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.PARAMERROR.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		try{
			int stuNum =workStatisticsService.getWorkClassStudentNum(workId, classId, schoolId);
			ajaxResponse.setWrapper(stuNum);
		}catch (CloudteachException e){
			ajaxResponse.setErrorMsg(e.getMsg());
			ajaxResponse.setStatus("0");
		}
		return ajaxResponse;
	}

	/**
	 * @param unitId 课程ID
	 * @param unitType 课程类型 1云教学  2同步课堂
	 * @return AjaxResponse
	 * 将教师当前选中的课程设置到redis中
	 */
	@RequestMapping(value = "setTeacherCurrentUnit")
	@ResponseBody
	public AjaxResponse setTeacherCurrentUnit(@RequestParam("unitId") String unitId,@RequestParam("unitType") int unitType) {
		logger.info("将教师当前选中的课程设置到redis中");
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setStatus("1");
		if(userId == null || "".equals(userId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.NOTLOGIN.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.PARAMERROR.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		if(unitType!=1 && unitType!=2){
			ajaxResponse.setErrorMsg(CloudTeachErrorEnum.PARAMERROR.getMsg());
			ajaxResponse.setStatus("0");
			return ajaxResponse;
		}
		try{
			cloudTeachService.setTeacherCurrentUnit(userId,unitId,unitType);
		}catch (CloudteachException e){
			ajaxResponse.setErrorMsg(e.getMsg());
			ajaxResponse.setStatus("0");
		}
		return ajaxResponse;
	}
}
