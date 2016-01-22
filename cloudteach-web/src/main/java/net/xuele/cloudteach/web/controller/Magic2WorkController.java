package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.CloudTeachService;
import net.xuele.cloudteach.service.Magic2QuestionService;
import net.xuele.cloudteach.service.Magic2WorkService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.form.Magic2WorkSubmitForm;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.member.dto.CtBookDTO;
import net.xuele.member.dto.StudentDTO;
import net.xuele.member.service.FamilyRelationService;
import net.xuele.member.service.SchoolBookService;
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
 * Magic2WorkController
 * 提分宝2 FOR appCenter
 * @author panglx
 * @date on 2015/10/23 0023.
 */
@Controller
@RequestMapping(value = "magic2/work")
public class Magic2WorkController {
	private static Logger logger = LoggerFactory.getLogger(Magic2WorkController.class);

	@Autowired
	Magic2WorkService magic2WorkService;

	@Autowired
	Magic2QuestionService magic2QuestionService;

	@Autowired
	CloudTeachService cloudTeachService;//云教学服务
	@Autowired
	CloudTeachRedisService cloudTeachRedisService;
	@Autowired
	private SchoolBookService schoolBookService;
	@Autowired
	FamilyRelationService familyRelationService;
	/**
	 * 应用中心提分宝挑战
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "appcentermagic2")
	public String appCenterMagic(ModelMap map,String unitId,String studentId) {
		String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();

		String identityId = GetSessionContentUtil.getInstance().getIdentifyId();//身份标识

		UnitsDTO unitsDTO = magic2WorkService.getUnit(unitId);
		BookDTO bookDTO = cloudTeachService.selectEditionAndSubject(unitsDTO.getBookId());
		if (Constants.IDENTIFY_STUDENT.equals(identityId)){//学生
			logger.info("学生进入提分宝：identityId="+identityId);

			logger.info("bookDTO 中的grade="+bookDTO.getGrade()+",semester="+bookDTO.getSemester());
			/**
			 * 年级
			 */
			Integer grade = GetSessionContentUtil.getInstance().getUserSession().getGradeNum();

			/**
			 * 学期
			 */
			Integer semester = GetSessionContentUtil.getInstance().getUserSession().getSemester();
			logger.info("session 中的grade="+grade+",semester="+semester);
			if (!grade.equals(bookDTO.getGrade()) ){
				logger.info("无权限进行该操作");
				throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
						CloudTeachErrorEnum.NOPERMISSION.getCode());
			}

		}else if (Constants.IDENTIFY_TEACHER.equals(identityId)){//教师
			logger.info("教师进入提分宝：identityId="+identityId);
			/*String bookId = GetSessionContentUtil.getInstance().currentBookID();//课本
			if (bookId == null || bookId != null && !bookId.equals(bookDTO.getBookId())){
				logger.info("无权限进行该操作");
				throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
						CloudTeachErrorEnum.NOPERMISSION.getCode());
			}*/
			List<CtBookDTO> ctBookDTOList = schoolBookService.queryTeacherSyncBook(userId, schoolId);
			for (CtBookDTO b:ctBookDTOList){
				if (bookDTO.getBookId().equals(b.getBookId())){
					map.put("tcBook",1);
					break;
				}
			}
			if (!map.get("tcBook").equals(1)){
				logger.info("无权限进行该操作");
				throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
						CloudTeachErrorEnum.NOPERMISSION.getCode());
			}

		}else{
			//获取家长对应小孩
			List<StudentDTO> studentDTOs = familyRelationService.queryKidInfo(userId);
			for (StudentDTO studentDTO : studentDTOs) {
				/**通过课本对应的年级学期跟学生对应的年级学期比较，取该课本对应的学生*/
				if (studentDTO.getGradeNum() == bookDTO.getGrade()
						&& studentId.equals(studentDTO.getUserId())) {
					/**判断studentId是否在studentDTOs中*/
					map.put("prStu",1);
					break;
				}
			}
			/**学生不在studentWrappers中*/
			if (!map.get("prStu").equals(1)){
				logger.info("无权限进行该操作");
				throw new CloudteachException(CloudTeachErrorEnum.NOPERMISSION.getMsg(),
						CloudTeachErrorEnum.NOPERMISSION.getCode());
			}
		}

		logger.info("unitName="+unitsDTO.getUnitName());
		String unitName = unitsDTO.getUnitName();

		map.put("unitId", unitId);
		map.put("unitName", unitName);
		map.put("identityId", identityId);

		return "magic2/index";
	}

	/**
	 * 提分宝2开始答卷
	 * @param unitId 课程id
	 * @return
	 */
	@RequestMapping(value = "beginwork-old")
	@ResponseBody
	public AjaxResponse<Magic2WorkListWrapper> workItemDetail_Old(String unitId,String practiceId) {
		String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
		//String schoolId = "110101";

		//获取提分宝题目信息
		Magic2WorkListWrapper wrapper = new Magic2WorkListWrapper();
		List<Magic2WorkQuestInfoDTO> magic2WorkQuestInfoDTOs =  magic2WorkService.showMagic2WorkList(unitId,practiceId, schoolId,userId);
		List<Magic2WorkQueWrapper> magic2WorkQueWrappers = new ArrayList<>();
		Magic2WorkQueWrapper queWrapper;
		//题目答案
		QAnswerWrapper qAnswerWrapper;
		List<QAnswerWrapper> qAnswerWrapperList;
		int totalNum = magic2WorkQuestInfoDTOs.size();
		for (Magic2WorkQuestInfoDTO dto:magic2WorkQuestInfoDTOs){
			queWrapper = new Magic2WorkQueWrapper();
			BeanUtils.copyProperties(dto.getqQuestDTO(),queWrapper);
			queWrapper.setQueId(dto.getqQuestDTO().getqId());
			//题目答案
			qAnswerWrapperList = new ArrayList<>();
			for (QAnswerDTO answerDTO:dto.getqAnswerDTOs()){
				qAnswerWrapper = new QAnswerWrapper();
				BeanUtils.copyProperties(answerDTO, qAnswerWrapper);
				qAnswerWrapper.setQueId(answerDTO.getqId());
				qAnswerWrapperList.add(qAnswerWrapper);
			}
			queWrapper.setqAnswerDTOs(qAnswerWrapperList);
			magic2WorkQueWrappers.add(queWrapper);
		}
		//maxSort 可去掉
		//int maxSort = magic2QuestionService.queryQQuestMaxSort(unitId);
		//wrapper.setMaxSort(maxSort);
		wrapper.setTotalNum(totalNum);
		wrapper.setQueWrappers(magic2WorkQueWrappers);
		AjaxResponse<Magic2WorkListWrapper> response = new AjaxResponse<>();
		response.setWrapper(wrapper);
		response.setStatus("1");
		return response;
	}

	/**
	 * 提分宝2开始答卷(PC)
	 * @param unitId 课程id
	 * @return
	 */
	@RequestMapping(value = "beginworkpc")
	@ResponseBody
	public AjaxResponse<Magic2WorkListWrapper> workItemDetailPC(String unitId,String practiceId) {
		String userId = GetSessionContentUtil.getInstance().currentUserID();//学生id
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
		//String schoolId = "110101";
		/**
		 * 调用接口获取提分宝题目信息
		 */
		List<Magic2WorkQuestInfoDTO> magic2WorkQuestInfoDTOs =
				magic2WorkService.showMagic2WorkList(unitId,practiceId,schoolId,userId);

		/**
		 * 封装返回前端题目及答案
		 */
		List<Magic2WorkQueWrapper> magic2WorkQueWrappers = new ArrayList<>();
		Magic2WorkQueWrapper queWrapper;//题目wrapper
		QAnswerWrapper qAnswerWrapper;//答案wrapper
		List<QAnswerWrapper> qAnswerWrapperList;
		int totalNum = magic2WorkQuestInfoDTOs.size();
		for (Magic2WorkQuestInfoDTO dto:magic2WorkQuestInfoDTOs){
			queWrapper = new Magic2WorkQueWrapper();
			BeanUtils.copyProperties(dto.getqQuestDTO(),queWrapper);
			queWrapper.setQueId(dto.getqQuestDTO().getqId());
			/**题目答案*/
			qAnswerWrapperList = new ArrayList<>();
			for (QAnswerDTO answerDTO:dto.getqAnswerDTOs()){
				qAnswerWrapper = new QAnswerWrapper();
				BeanUtils.copyProperties(answerDTO, qAnswerWrapper);
				qAnswerWrapper.setQueId(answerDTO.getqId());
				qAnswerWrapper.setIscorrect(0);
				if (queWrapper.getType() == 3){
					/**填空题答案置为1*/
					qAnswerWrapper.setaContent("~");
				}
				qAnswerWrapperList.add(qAnswerWrapper);
			}
			queWrapper.setqAnswerDTOs(qAnswerWrapperList);
			magic2WorkQueWrappers.add(queWrapper);
		}
		Magic2WorkListWrapper wrapper = new Magic2WorkListWrapper();
		wrapper.setTotalNum(totalNum);
		wrapper.setQueWrappers(magic2WorkQueWrappers);
		AjaxResponse<Magic2WorkListWrapper> response = new AjaxResponse<>();
		response.setWrapper(wrapper);
		response.setStatus("1");
		return response;
	}

	/**
	 * 提交答卷(PC)
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "submitworkpc")
	@ResponseBody
	public AjaxResponse<Magic2WorkSubmitWrapper> submitMagicWorkPC(Magic2WorkSubmitForm form){

		String userId = GetSessionContentUtil.getInstance().currentUserID();//用户id
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id

		String identityId = GetSessionContentUtil.getInstance().getIdentifyId();//身份标识

		Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO = new Magic2WorkSubmitFormDTO();
		//开始时间
		Date beginTime = new Date(Long.parseLong(form.getBeginTime().isEmpty() ? "0" : form.getBeginTime()));
		//完成时间
		Date endTime = new Date(Long.parseLong(form.getEndTime().isEmpty() ? "0" : form.getEndTime()));
		magic2WorkSubmitFormDTO.setUnitId(form.getUnitId());
		magic2WorkSubmitFormDTO.setPracticeId(form.getPracticeId());
		magic2WorkSubmitFormDTO.setSort(form.getSort());
		magic2WorkSubmitFormDTO.setAnsQueJSON(form.getAnsQueJSON());
		magic2WorkSubmitFormDTO.setTotalNum(form.getTotalNum());
		magic2WorkSubmitFormDTO.setEndTime(endTime);
		magic2WorkSubmitFormDTO.setBeginTime(beginTime);
		magic2WorkSubmitFormDTO.setUserId(userId);
		magic2WorkSubmitFormDTO.setSchoolId(schoolId);
		magic2WorkSubmitFormDTO.setPort(1);//pc端挑战
		//身份标识
		magic2WorkSubmitFormDTO.setIdentityId(identityId);
		logger.info("调用接口提交答卷： magic2WorkService.submitMagicWorkChallenge()");
		Magic2WorkChallengeDTO dto  = magic2WorkService.submitMagic2WorkChallengePC(magic2WorkSubmitFormDTO);
		Magic2WorkSubmitWrapper submitWrapper = new Magic2WorkSubmitWrapper();
		BeanUtils.copyProperties(dto,submitWrapper);

		AjaxResponse<Magic2WorkSubmitWrapper> response = new AjaxResponse<>();
		response.setStatus("1");
		response.setWrapper(submitWrapper);
		return response;
	}

	/**
	 * 退出提分宝
	 * @param practiceId
	 * @return
	 */
	@RequestMapping(value = "exitwork")
	@ResponseBody
	public AjaxResponse<Magic2WorkMaxPracticeDTO> exitMagicWork(String practiceId){

		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id
		logger.info("调用接口退出提分宝，返回练习最高分： magic2WorkService.getMagic2WorkMaxPractice()");
		Magic2WorkMaxPracticeDTO maxPracticeDTO = magic2WorkService.getMagic2WorkMaxPractice(practiceId,schoolId);
		AjaxResponse<Magic2WorkMaxPracticeDTO> response = new AjaxResponse<>();
		response.setStatus("1");
		response.setWrapper(maxPracticeDTO);
		return response;
	}

	/**
	 * 报告问题（问题反馈）
	 * @param unitId
	 * @param queId
	 * @param challengeId
	 * @param qType
	 * @param fbType
	 * @param fbContent
	 * @return
	 */
	@RequestMapping(value = "feedback")
	@ResponseBody
	public AjaxResponse reportQuest(String unitId,String queId,String challengeId,int qType,int fbType,String fbContent,String userAnswer){
		String userId = GetSessionContentUtil.getInstance().currentUserID();//用户id
		String userName =  GetSessionContentUtil.getInstance().currentUserName();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id

		QQuestFeedbackDTO questFeedbackDTO = new QQuestFeedbackDTO();
		questFeedbackDTO.setUnitId(unitId);
		questFeedbackDTO.setQueId(queId);
		questFeedbackDTO.setFbType(fbType);
		questFeedbackDTO.setqType(qType);
		questFeedbackDTO.setUserAnswer(userAnswer);
		questFeedbackDTO.setFbContent(fbContent==null?"":fbContent);
		questFeedbackDTO.setUserId(userId);
		questFeedbackDTO.setUserName(userName);
		questFeedbackDTO.setFbTime(new Date());


		int result = magic2WorkService.insertQuestFeedback(questFeedbackDTO,challengeId,schoolId);

		AjaxResponse response = new AjaxResponse<>();
		response.setStatus("1");
		response.setWrapper(result);
		return response;
	}
}
