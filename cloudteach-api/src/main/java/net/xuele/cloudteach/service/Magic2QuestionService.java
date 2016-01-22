package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.QAnswerDTO;
import net.xuele.cloudteach.dto.QQuestDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * Magic2QuestionService
 * 提分宝题目服务
 * @author panglx
 * @date on 2015/10/21 0021.
 */
public interface Magic2QuestionService {
	/**
	 * 根据课程id，随机查询原题中的num道题目
	 * @param unitId
	 * @param num
	 * @return
	 */
	List<QQuestDTO> selectOriQuestByUnitId(String unitId,int num);

	/**
	 * 根据课程id，随机查询原题中的num道题目(新规则)
	 * @param unitId
	 * @param schoolId
	 * @param userId
	 * @param num
	 * @param rate
	 * @return
	 */
	public List<QQuestDTO> selectOriQuestByUnitId2(String unitId, String schoolId,String userId,int num,double rate);

	/**
	 * 根据课程id，随机查询num道题目(新规则)
	 * @param unitId
	 * @param practiceId
	 * @param schoolId
	 * @param userId
	 * @param num
	 * @param rate
	 * @return
	 */
	public List<QQuestDTO> queryRdQuestByUnitId(String unitId, String practiceId,String schoolId,String userId,int num,double rate);

	/**
	 * 查询某一题的选项
	 * @param qId
	 * @return
	 */
	List<QAnswerDTO> selectAnswerByQId(String qId);

	/**
	 * 顺序获取原题对应的衍生题
	 *
	 * @param unitId
	 * @return
	 * @throws CloudteachException
	 */
	public List<QQuestDTO> queryMagicDeriveQuestListByUnitId(String unitId, int sort, List<String> parentIds) throws CloudteachException;

	/**
	 * 获取 原题及其对应的所有衍生题
	 * @param unitId
	 * @return
	 * @throws CloudteachException
	 */
	//public List<QQuestViewDTO> queryMagicOriAndDeriveQuestByUnitId(String unitId,int num) throws CloudteachException;

	/**
	 * 更改题目状态：0：作废| 1：启用|2：已检查
	 * @param qId
	 * @param status
	 * @return
	 */
	public int updateStatus(String qId,int status);
}
