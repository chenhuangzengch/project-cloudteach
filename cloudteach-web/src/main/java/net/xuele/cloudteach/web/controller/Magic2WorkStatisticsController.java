package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.Magic2WorkStatisticsService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.StudentWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.dto.StudentDTO;
import net.xuele.member.service.FamilyRelationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Magic2WorkStatisticsController
 * 提分宝2数据统计
 * @author panglx
 * @date on 2015/12/24 0024.
 */
@Controller
@RequestMapping(value = "magic2/work/statistics")
public class Magic2WorkStatisticsController {
	private static Logger logger = LoggerFactory.getLogger(Magic2WorkStatisticsController.class);

	@Autowired
	Magic2WorkStatisticsService statisticsService;
	@Autowired
	FamilyRelationService familyRelationService;
	@Autowired
	CloudTeachService cloudTeachService;//云教学服务
	/**
	 * 提分宝数据统计首页
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(ModelMap map,String unitId,String classId,String studentId){
		String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id
		String identityId = GetSessionContentUtil.getInstance().getIdentifyId();//身份标识
		UnitsDTO unitsDTO = cloudTeachService.getUnitInfo(unitId);//课程信息
		BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(unitsDTO.getBookId());//课本信息
		map.put("unitsDTO",unitsDTO);
		map.put("model",0);
		if (Constants.IDENTIFY_TEACHER.equals(identityId)){//教师
			/**
			 * 教师进入跳到班级的统计页
			 */
			List<ClassInfoDTO> teacherClass = GetSessionContentUtil.getInstance().getClassInfo();//教师任课班级
			List<ClassInfoDTO> resultClass = new ArrayList<>();
			for (int i = 0; i < teacherClass.size(); i++) {
				ClassInfoDTO classInfoDTO = teacherClass.get(i);
				if (classInfoDTO.getGradeNum() == bookDTO.getGrade()){
					/**得到改年级的授课班级*/
					resultClass.add(classInfoDTO);
				}
			}
			Magic2WorkClassTotalDTO classTotalDTO;
			Magic2WorkClassStatisticsDTO classStatisticsDTO;
			if (StringUtils.isEmpty(classId)){
				/**提分宝班级总计*/
				classTotalDTO = statisticsService.
						getMagic2WorkClassTotal(schoolId,unitId,resultClass.get(0).getClassId());
				/**提分宝班级统计*/
				classStatisticsDTO = statisticsService.
						getMagic2WorkClassStatistics(schoolId, unitId, resultClass.get(0).getClassId());
				map.put("model",1);
			}else{
				/**判断classId是否在teacherClass中*/
				for (ClassInfoDTO cdto:resultClass){
					if (classId.equals(cdto.getClassId())){
						resultClass.remove(cdto);
						resultClass.add(0,cdto);
						map.put("model",2);
						break;
					}
				}
				/**班级不在teacherClass中*/
				if (!map.get("model").equals(2)){
					logger.info("无权限进行该操作");
					throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
							CloudTeachErrorEnum.NOPERMISSION.getCode());
				}
				/**提分宝班级总计*/
				classTotalDTO = statisticsService.
						getMagic2WorkClassTotal(schoolId,unitId,classId);
				/**提分宝班级统计*/
				classStatisticsDTO = statisticsService.
						getMagic2WorkClassStatistics(schoolId, unitId, classId);
			}
			if (resultClass.size()==1){
				map.put("classNum",1);
			}else{
				map.put("classNum",0);
			}

			map.put("teacherClass",resultClass);
			map.put("classTotalDTO",classTotalDTO);
			map.put("classStatisticsDTO",classStatisticsDTO);
			return "magic2/statistics/index";

		}else if (Constants.IDENTIFY_PARENT.equals(identityId)) {
			/**
			 * 家长进入直接跳到学生的个人统计页
			 */
			if (StringUtils.isEmpty(studentId)) {
				logger.info("参数错误");
				throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg(),
						CloudTeachErrorEnum.PARAMERROR.getCode());
			}else{
				//获取家长对应小孩
				List<StudentDTO> studentDTOs = familyRelationService.queryKidInfo(userId);
				StudentWrapper studentWrapper = new StudentWrapper();
				for (StudentDTO studentDTO : studentDTOs) {
					/**通过课本对应的年级学期跟学生对应的年级学期比较，取该课本对应的学生*/
					if (studentDTO.getGradeNum() == bookDTO.getGrade()
							&& studentId.equals(studentDTO.getUserId())) {
						/**判断studentId是否在studentDTOs中*/
						studentWrapper.setStudentId(studentDTO.getUserId());
						studentWrapper.setStudentName(studentDTO.getRealName());
						studentWrapper.setIcon(studentDTO.getIcon());
						studentWrapper.setSchoolId(studentDTO.getSchoolId());
						map.put("model",4);
						break;
					}
				}
				List<Magic2WorkMyStatisticsDTO> myStatisticsDTOList;
				Magic2WorkClassTotalDTO stuTotal;
				/**学生不在studentWrappers中*/
				if (!map.get("model").equals(4)){
					logger.info("无权限进行该操作");
					throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
							CloudTeachErrorEnum.NOPERMISSION.getCode());
				}
				/**提分宝个人统计*/
				myStatisticsDTOList = statisticsService.
						getMagic2WorkMyStatistics(studentWrapper.getSchoolId(), unitId, studentId);
				/**提分宝个人总计*/
				stuTotal = statisticsService.
						getMagic2WorkStuTotal(studentWrapper.getSchoolId(), unitId, studentId);

				map.put("stuTotal",stuTotal);
				map.put("studentWrapper",studentWrapper);
				map.put("myStatisticsDTOList",myStatisticsDTOList);
				return "magic2/statistics/personal";
			}
		}else{//学生
			logger.info("无权限进行该操作");
			throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
					CloudTeachErrorEnum.NOPERMISSION.getCode());
		}
	}


	@RequestMapping(value = "classTotal")
	@ResponseBody
	public AjaxResponse<Magic2WorkClassTotalDTO> getClassTotal(String schoolId,String unitId,String classId){
		/**提分宝班级总计*/
		Magic2WorkClassTotalDTO classTotalDTO = statisticsService.
				getMagic2WorkClassTotal(schoolId,unitId,classId);
		AjaxResponse<Magic2WorkClassTotalDTO> response = new AjaxResponse<>();
		response.setStatus("1");
		response.setWrapper(classTotalDTO);
		return response;

	}

	@RequestMapping(value = "classStatistics")
	@ResponseBody
	public AjaxResponse<Magic2WorkClassStatisticsDTO> getClassStatistics(String schoolId,String unitId,String classId){
		/**提分宝班级总计*/
		Magic2WorkClassStatisticsDTO classStatisticsDTO = statisticsService.
				getMagic2WorkClassStatistics(schoolId, unitId, classId);
		AjaxResponse<Magic2WorkClassStatisticsDTO> response = new AjaxResponse<>();
		response.setStatus("1");
		response.setWrapper(classStatisticsDTO);
		return response;

	}

	/**
	 * 提分宝数据统计学生个人统计
	 * @param unitId
	 * @param userId 学生id
	 * @return
	 */
	@RequestMapping(value = "myStatistics")
	@ResponseBody
	public AjaxResponse<List<Magic2WorkMyStatisticsDTO>> getMyStatistics(String unitId,String userId){
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id
		//String schoolId = "10001";
		/**提分宝班级总计*/
		List<Magic2WorkMyStatisticsDTO> myStatistics = statisticsService.
				getMagic2WorkMyStatistics(schoolId, unitId, userId);
		AjaxResponse<List<Magic2WorkMyStatisticsDTO>> response = new AjaxResponse<>();
		response.setStatus("1");
		response.setWrapper(myStatistics);
		return response;

	}

}
