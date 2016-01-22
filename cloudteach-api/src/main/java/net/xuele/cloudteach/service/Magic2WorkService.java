package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * Magic2WorkService
 *
 * @author panglx
 * @date on 2015/10/22 0022.
 */
public interface Magic2WorkService {
	/**
	 * 获取课程信息
	 * @param unitId
	 * @return
	 */
	public UnitsDTO getUnit(String unitId);

	/**
	 * 获取题目列表
	 * @param unitId
	 * @param practiceId
	 * @param schoolId
	 * @param userId
	 * @return
	 */
	public List<Magic2WorkQuestInfoDTO> showMagic2WorkList_old(String unitId,String practiceId,String schoolId,String userId);

	/**
	 * 获取题目列表app-新规则
	 * @param unitId
	 * @param practiceId
	 * @param schoolId
	 * @param userId
	 * @return
	 * @throws CloudteachException
	 */
	public List<Magic2WorkQuestInfoDTO> showMagic2WorkList(String unitId, String practiceId, String schoolId,String userId) throws CloudteachException;

	/**
	 * 提交提分宝答卷
	 * @param magic2WorkSubmitFormDTO
	 * @return
	 */
	public Magic2WorkChallengeDTO submitMagic2WorkChallenge_old(Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO);

	/**
	 * 提交提分宝答卷app-新规则
	 * @param magic2WorkSubmitFormDTO
	 * @return
	 */
	public Magic2WorkChallengeDTO submitMagic2WorkChallenge(Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO);

	/**
	 * 提交提分宝答卷pc-新规则
	 * @param magic2WorkSubmitFormDTO
	 * @return
	 */
	public Magic2WorkChallengeDTO submitMagic2WorkChallengePC(Magic2WorkSubmitFormDTO magic2WorkSubmitFormDTO);
	/**
	 * 获取提分宝练习最高分
	 * @param practiceId
	 * @return
	 */
	public Magic2WorkMaxPracticeDTO getMagic2WorkMaxPractice(String practiceId,String schoolId);

	//public Magic2WorkChallengeDTO magic2WorkAutoScore(Magic2WorkChallengeDTO magic2WorkChallenge, List<Magic2StuAnsJsonDTO> magic2StuAnsJsonDTOList,int challengeTimes);

	/**
	 * 获取某一课程下的挑战记录
	 * @param schoolId
	 * @param unitId
	 * @param userId
	 * @return
	 */
	public List<Magic2WorkChallengeDTO> getChallengeListByUnitId(String schoolId, String unitId, String userId);

	/**
	 * 报告问题时插入问题反馈表记录
	 * @param questFeedbackDTO
	 * @return
	 */
	public int insertQuestFeedback(QQuestFeedbackDTO questFeedbackDTO,String challengeId,String schoolId);
}
