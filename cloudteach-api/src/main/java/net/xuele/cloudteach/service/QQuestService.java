package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.QQuestDTO;
import net.xuele.cloudteach.dto.QQuestViewDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * Created by hujx on 2015/10/22 0022.
 * 题库服务
 */
public interface QQuestService {

    /**
     * 通过unitId获取该单元下所有原题以及对应的衍生提
     *
     * @param unitId  单元ID
     * @param randNum 取几道衍生题
     * @return
     */
    List<QQuestViewDTO> quaryFullQuestions(String unitId, Integer randNum) throws CloudteachException;

    /**
     * 获取提分宝题目
     *
     * @param unitId
     * @param questSort
     * @param randNum
     * @return
     * @throws CloudteachException
     */
    List<QQuestDTO> quaryMagicQuestions(String unitId, int questSort, Integer randNum) throws CloudteachException;

}
