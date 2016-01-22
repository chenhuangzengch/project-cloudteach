package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.TeachCoursewareLogDTO;
import net.xuele.cloudteach.dto.TeachCoursewareLogDetailDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * TeachCoursewareLogService
 * 授课打点服务接口
 *
 * @author sunxh
 * @date 15/12/2
 */
public interface TeachCoursewareLogService {

    /**
     * 列出某个课件的所有授课记录
     * 不包括明细数据
     *
     * @param cclCoursewaresId 课件ID
     * @param schoolId         学校ID
     * @param cclClassId       班级ID
     * @return List<TeachCoursewareLogDTO>
     */
    List<TeachCoursewareLogDTO> listByCourseware(String cclCoursewaresId, String schoolId, String cclClassId) throws CloudteachException;

    /**
     * 根据记录ID获取一个授课记录
     * 不包括明细数据
     *
     * @param schoolId 学校ID
     * @param logId    授课记录ID
     * @return TeachCoursewareLogDTO
     */
    TeachCoursewareLogDTO getLogById(String schoolId, String logId) throws CloudteachException;

    /**
     * 列出某个授课记录的所有明细信息
     *
     * @param cclId    授课记录ID
     * @param schoolId 学校ID
     * @return List<TeachCoursewareLogDetailDTO>
     */
    List<TeachCoursewareLogDetailDTO> listDetailByLogId(String cclId, String schoolId) throws CloudteachException;

    /**
     * 记录课件授课开始时间
     * 参数内容：学校ID schoolId [非空]
     * 参数内容：课件ID cclCoursewaresId [非空]
     * 参数内容：班级ID cclClassId [非空]
     * 参数内容：教师ID cclUserId [非空]
     * 参数内容：开始时间 cclBegtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     * 参数内容：结束时间 cclEndtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     *
     * @param teachCoursewareLogDTO 授课记录DTO
     * @return 记录ID 用于后续信息定位关系
     */
    TeachCoursewareLogDTO logTeachingBegin(TeachCoursewareLogDTO teachCoursewareLogDTO) throws CloudteachException;

    /**
     * 记录课件授课开始时间
     * 参数内容：学校ID schoolId [非空]
     * 参数内容：授课记录ID cclId [非空]
     * 参数内容：课件ID cclCoursewaresId [允许空]
     * 参数内容：班级ID cclClassId [允许空]
     * 参数内容：教师ID cclUserId [允许空]
     * 参数内容：开始时间 cclBegtime [不需要传入，该接口不会处理开始时间]
     * 参数内容：结束时间 cclEndtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     *
     * @param teachCoursewareLogDTO 授课记录DTO
     * @return TeachCoursewareLogDTO
     * @throws CloudteachException
     */
    TeachCoursewareLogDTO logTeaching(TeachCoursewareLogDTO teachCoursewareLogDTO) throws CloudteachException;

    /**
     * 记录授课明细内容
     * 参数内容：学校ID schoolId [非空]
     * 参数内容：授课记录ID cclId [非空]
     * 参数内容：记录明细类型 ccdlType [非空]
     * 参数内容：记录明细内容 ccdlContext [非空]
     * 参数内容：JSONSTRING ccdlContent1 [允许空]
     * 参数内容：开始时间 cclBegtime [不需要传入，该接口不会处理开始时间]
     * 参数内容：结束时间 cclEndtime [不需要传入，系统取当前时间，如果传入会被覆盖]
     *
     * @param teachCoursewareLogDetailDTO 授课记录明细DTO
     * @return TeachCoursewareLogDetailDTO
     * @throws CloudteachException
     */
    TeachCoursewareLogDetailDTO writeLogDetail(TeachCoursewareLogDetailDTO teachCoursewareLogDetailDTO) throws CloudteachException;

}
