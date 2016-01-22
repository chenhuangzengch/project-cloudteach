package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.MagicBankFinishDTO;
import net.xuele.cloudteach.dto.MagicQuestionBankDTO;
import net.xuele.cloudteach.dto.MagicQuestionDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * MagicQuestionService
 * 提分宝题库服务
 *
 * @author duzg
 * @date 2015/7/13 0002
 */
public interface MagicQuestionService {

    /**
     * @param unitId 课程号
     * @return List<MagicQuestionBankDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据课程ID获取提分宝原题题库列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicQuestionBankDTO> queryMagicQuestionBankListByUnitId(String unitId,String extraBookId) throws CloudteachException;

    /**
     * @param bankId 题库号
     * @return CtMagicQuestionBank
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库ID获取提分宝原题题库信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    MagicQuestionBankDTO selectByPrimaryKey(String bankId) throws CloudteachException;

    /**
     * @param bankId 题库号
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库号获取对应原题题目列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicQuestionDTO> queryMagicQuestionMasterListByBankId(String bankId) throws CloudteachException;

    /**
     * @param bankId 题库号
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库号随机获取对应衍生题题目列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicQuestionDTO> queryMagicQuestionSlaveRandomListByBankId(String bankId) throws CloudteachException;

    /**
     * @param bankId 题库号
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据题库号随机获取题目列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicQuestionDTO> queryMagicQuestionRandomListByBankId(String bankId) throws CloudteachException;

    /**
     * @param bankId   题库号
     * @param orderNum 第几套衍生题（从1开始，原题=0）
     * @return List<MagicQuestionDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据衍生题套数号获取一套题列表信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    List<MagicQuestionDTO> queryMagicQuestionListByNum(String bankId, int orderNum) throws CloudteachException;

    /**
     * @param queId 题目号
     * @return MagicQuestionDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据题目号获取题目信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    MagicQuestionDTO queryMagicQuestion(String queId) throws CloudteachException;

    /**
     * 查询某一题库下的题目数
     *
     * @param bankId
     * @return
     */
    int countByBankId(String bankId,int orderNum);

    /**
     * 根据题目id查询题目详细信息
     *
     * @param queId
     * @return
     * @throws CloudteachException
     */
    MagicQuestionDTO selectDetailQue(String queId) throws CloudteachException;

    /**
     * 根据学生用户ID、题库ID查询完成状态
     *
     * @param userId
     * @param bankId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    MagicBankFinishDTO queryBankFinishStaByUserId(String userId, String bankId, String schoolId) throws CloudteachException;
}