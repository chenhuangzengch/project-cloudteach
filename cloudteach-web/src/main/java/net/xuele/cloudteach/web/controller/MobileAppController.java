package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.dto.MagicQuestionBankDTO;
import net.xuele.cloudteach.dto.MagicWorkChallengeQueViewDTO;
import net.xuele.cloudteach.dto.MagicWorkQueDetailDTO;
import net.xuele.cloudteach.service.MagicQuestionService;
import net.xuele.cloudteach.service.MagicWorkStuService;
import net.xuele.member.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * MobileAppController
 * 移动端controller
 * @author panglx
 * @date on 2015/8/16 0016.
 */
@Controller
@RequestMapping(value = "mobileApp")
public class MobileAppController {
	private static Logger logger = LoggerFactory.getLogger(MobileAppController.class);
	@Autowired
	private MagicWorkStuService magicWorkStuService;//学生提分宝作业服务
	@Autowired
	private MagicQuestionService magicQuestionService;
	@Autowired
	private UserService userService;
	/**
	 * 移动端提分宝挑战
	 *
	 * @param map
	 * @param bankId 题库id
	 * @return
	 */
	@RequestMapping(value = "magic")
	public String mobileAppMagic(ModelMap map, String userId,String workId,String bankId,String orderNum) {
		logger.info("移动端提分宝挑战页面:mobileAppMagic 参数：userId="+userId+",workId="+workId+",bankId="+bankId+",orderNum="+orderNum);
		String schoolId = userService.getSchoolIdByUserId(userId);
		Integer order = StringUtils.isEmpty(orderNum)?null:new Integer(orderNum);
		logger.info("获取提分宝作业题目信息");
		logger.info("转换后的orderNum:"+order);
		logger.info("schoolId:"+schoolId);
		logger.info("调用服务获取提分宝题目信息 queryMagicQueDetail");
		MagicWorkQueDetailDTO magicWorkQueDetailDTO = magicWorkStuService.queryMagicQueDetail(order,workId, bankId, userId, schoolId, 2);
		logger.info("调用服务获取题库信息 selectByPrimaryKey");
		MagicQuestionBankDTO magicQuestionBankDTO = magicQuestionService.selectByPrimaryKey(bankId);
		map.put("magicWorkQueDetailDTO", magicWorkQueDetailDTO);
		map.put("userId", userId);
		map.put("schoolId", schoolId);
		map.put("bankId", bankId);
		map.put("total", magicWorkQueDetailDTO.getTotal());
		map.put("unitId", magicQuestionBankDTO.getUnitId());
		map.put("orderNum", magicWorkQueDetailDTO.getOrderNum());
		return "workmanage/student/mobileAppMagic";
	}
	/**
	 * 移动端提分宝挑战对完答案页面
	 *
	 * @param map
	 * @param bankId 题库id
	 * @return
	 */
	@RequestMapping(value = "magicInfo")
	public String mobileAppMagicInfo(ModelMap map, String userId,Integer orderNum,String bankId,String challengeId) {
		//bankId = "7109f5a13ccf11e5b032005056a70232";
		//userId =  "265143510212";//学生id
		//orderNum = 0;
		logger.info("移动端提分宝挑战对完答案页面:mobileAppMagicInfo 参数：userId="+userId+",orderNum:"+orderNum+",bankId="+bankId+",challengeId="+challengeId);
		String schoolId = userService.getSchoolIdByUserId(userId);
		logger.info("schoolId:"+schoolId);
		logger.info("查询挑战题目及回答信息 getChallengeAnsInfo");
		List<MagicWorkChallengeQueViewDTO> magicQuestionViewDTO = magicWorkStuService.getChallengeAnsInfo( bankId, orderNum, challengeId,schoolId);

		map.put("magicQuestionViewDTO", magicQuestionViewDTO);
		map.put("userId", userId);
		map.put("schoolId", schoolId);
		map.put("total", magicQuestionViewDTO.size());
		return "workmanage/student/mobileAppMagicInfo";
	}
}
