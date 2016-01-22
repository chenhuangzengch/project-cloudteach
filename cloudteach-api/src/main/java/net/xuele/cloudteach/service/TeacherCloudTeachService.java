package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.TeacherCloudTeachViewDTO;
import net.xuele.cloudteach.dto.TeacherCloudTeachViewDTO;
import net.xuele.cloudteach.dto.page.TeacherCloudTeachViewPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

/**
 * TeacherCloudTeachService
 * 教师云教学服务
 *
 * @author duzg
 * @date 2015/7/8 0002
 */
public interface TeacherCloudTeachService {

    /**
     * @param request
     * @return PageResponse<CloudTeachViewDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据用户号获取对应云教学信息（包括1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业），
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    PageResponse<TeacherCloudTeachViewDTO> queryCloudTeachList(TeacherCloudTeachViewPageRequest request) throws CloudteachException;

    /**
     * @param workId   作业ID
     * @param schoolId 学校ID
     * @param workType 作业类型 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 删除云教学信息
     */
    int delCloudTeach(String workId, String schoolId, Integer workType) throws CloudteachException;
}