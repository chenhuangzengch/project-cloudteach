package net.xuele.cloudteach.service;

import com.fasterxml.jackson.core.JsonParseException;
import net.xuele.cloudteach.dto.AfterClassWorkViewDTO;
import net.xuele.cloudteach.dto.EffectiveWorkViewDTO;
import net.xuele.cloudteach.dto.GuidanceWorkViewDTO;
import net.xuele.cloudteach.dto.page.AfterClassWorkViewPageRequest;
import net.xuele.cloudteach.dto.page.EffectiveWorkViewPageRequest;
import net.xuele.cloudteach.dto.page.GuidanceWorkViewPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.io.IOException;

/**
 * TeacherWorkManageService
 * 教师作业管理服务
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
public interface TeacherWorkManageService {
    /**
     * @param request
     * @return PageResponse<EffectiveWorkViewDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据用户号获取该用户布置的所有进行中的作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    PageResponse<EffectiveWorkViewDTO> queryEffectiveWork(EffectiveWorkViewPageRequest request) throws CloudteachException;

    /**
     * @param request
     * @return PageResponse<AfterClassWorkViewDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据用户号获取该用户布置的所有课后作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    PageResponse<AfterClassWorkViewDTO> queryAfterClassWork(AfterClassWorkViewPageRequest request) throws CloudteachException;

    /**
     * @param request
     * @return PageResponse<GudanceWorkViewDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据用户号获取该用户布置的所有预习作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    PageResponse<GuidanceWorkViewDTO> queryGuidanceWork(GuidanceWorkViewPageRequest request) throws CloudteachException;

    /**
     * @param request
     * @return PageResponse<AfterClassWorkViewDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据用户号获取该用户布置的所有课外作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    PageResponse<AfterClassWorkViewDTO> queryExtraWork(AfterClassWorkViewPageRequest request) throws CloudteachException;

    /**
     * @param workId       作业ID
     * @param workType     作业类型：1预习作业，2提分神器作业，3同步课堂作业，4电子作业
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param score        评分
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 对作业ID对应的作业进行快速批改，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void quicklyCorrect(String workId, Integer workType, String context, Integer praiseStatus, Integer score, String userId, String schoolId) throws CloudteachException;

    /**
     * @param workjson     json格式参数包含了workId、workType、context
     * @param context      评语
     * @param praiseStatus 鼓励状态：0不鼓励 1鼓励
     * @param userId       教师用户号
     * @param schoolId     学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 全部快速批改，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    String quicklyAllCorrect(String workjson, String context, Integer praiseStatus, String userId, String schoolId) throws CloudteachException, IOException;

    /**
     * @param workId   作业ID
     * @param workType 作业类型：1预习作业，2提分神器作业，3同步课堂作业，4电子作业,7口语作业
     * @param userId   教师用户号
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 根据作业ID逻辑删除对应的作业信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void delWork(String workId, Integer workType, String userId, String schoolId) throws CloudteachException;

    /**
     * @param answerId 作业学生回答ID
     * @param workType 作业类型：1预习作业，2提分神器作业，3同步课堂作业，4电子作业,7口语作业
     * @param userId   教师用户号
     * @param schoolId 学校ID
     * @throws net.xuele.common.exceptions.CloudteachException 删除answerId对应的学生作业，有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    void delStuWork(String answerId, Integer workType, String userId, String schoolId) throws CloudteachException;
}