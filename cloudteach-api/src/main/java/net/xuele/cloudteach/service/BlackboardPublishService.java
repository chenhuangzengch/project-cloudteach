package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.BlackboardPublishPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * BlackboardPublishService
 * 板书服务
 *
 * @author cm.wang
 * @date 2015/7/14 0008
 */
public interface BlackboardPublishService {

    /**
     * 发布板书
     *
     * @param blackboardPublishInfoDTO
     * @return
     */
    String addBlackboardPublish(BlackboardPublishInfoDTO blackboardPublishInfoDTO, WorkTapeFilesDTO workTapeFilesDTO,
                                String userName,String userIcon);

    /**
     * 通过用户Id分页获得的板书(非详细，即没有附件信息)
     *
     * @param blackboardPublishPageRequest
     * @return
     */
    PageResponse<BlackboardPublishDTO> getBlackboardPublishsByUserId(BlackboardPublishPageRequest blackboardPublishPageRequest, String schoolId);

    /**
     * 通过板书Id获得具体的信息
     *
     * @param blackboardId 板书Id
     * @return
     */
    BlackboardPublishInfoDTO getBlackboardPublishById(String blackboardId, String schoolId, String userId);

    /**
     * 学生查看板书时更改已读状态
     *
     * @param userId       学生Id
     * @param blackboardId 板书Id
     * @return
     */
    int checkBlackboardPublish(String userId, String blackboardId, String schoolId);

    /**
     * 上传云盘资源
     * 【资源信息初始化，至少需包含
     * userId:文件所属用户id、unitId:课程编号、fileUri:HDFS文件uri、
     * fileType:云盘文件类型、name：文件名、extension：扩展名、size：文件大小】
     *
     * @param blackboardPublishFilesDTO
     * @return
     * @throws CloudteachException
     */
    int uploadFile(BlackboardPublishFilesDTO blackboardPublishFilesDTO) throws CloudteachException;


    /**
     * 老师编辑板书
     *
     * @param blackboardPublishInfoDTO
     */
    int editBlackboardPublish(BlackboardPublishInfoDTO blackboardPublishInfoDTO);

    /**
     * 根据板书Id删除
     *
     * @param blackboardId
     * @return
     */
    int delBlackboardPublish(String blackboardId, String schoolId);

    /**
     * 获取课程ID对应的所有已添加教案的板书附件信息
     * @param userId
     * @param unitId
     * @param schoolId
     * @return
     */
    List<BlackboardPublishFilesDTO> selectBlackboardListByUnitId(String userId,String unitId,String schoolId);

    /**
     * 获取课程ID对应最新的板书发布信息
     * @param userId
     * @param unitId
     * @param schoolId
     * @return
     */
    BlackboardPublishDTO selectNewBlackboardPublish(String userId,String unitId,String schoolId);
}
