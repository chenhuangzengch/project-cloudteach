package net.xuele.cloudteach.service;


import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CloudDiskSharePageRequest;
import net.xuele.cloudteach.dto.page.TcExtFLShareDiskPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * Created by panglx on 2015/6/8 0008.
 * 大家分享的资源服务
 */
public interface CloudDiskShareService {

    /**
     * @param request
     * @return PageResponse<CloudDiskShareDTO>
     * @throws net.xuele.common.exceptions.CloudteachException 根据请求条件分页获得云盘信息，
     *                                                         有可能抛出云盘异常信息【运行时异常，上层调用可以不用捕获】
     */
    PageResponse<CloudDiskShareDTO> querySharedResource(CloudDiskSharePageRequest request) throws CloudteachException;

    /**
     * @param request
     * @return PageResponse<CloudDiskShareDTO>
     * @课件分页获取大家分享的云盘资源
     */
    PageResponse<TcExtFLCloudDiskShareDTO> queryTcExtFLSharedResource(TcExtFLShareDiskPageRequest request) throws CloudteachException;

    /**
     * 云盘分享统计
     * @param unitId
     * @param schoolId
     * @param areaId
     * @return
     * @throws CloudteachException
     */
    public int selectShareNum(String unitId,String schoolId,String areaId)throws CloudteachException;

    /**
     * 根据fileType分类计数
     * @param unitId
     * @param schoolId
     * @param areaId
     * @return
     */
    public List<CloudDiskShareCountDTO> countByFileType(String unitId,String schoolId,String areaId);

    /**
     * 查询计数表数据
     *
     * @param shareId
     * @return
     */
    CloudDiskShareStatisticsDTO selectDiskShareStatistics(String shareId);

    /**
     * 增加计数表记录
     *
     * @param record
     * @return
     */
    int insertDiskShareStatistics(CloudDiskShareStatisticsDTO record);

    /**
     * 取消文件分享
     *
     * @param diskId
     * @return
     */
    void cancelShareFile(String schoolId, String diskId, String userId);

    /**
     * 点赞
     *
     * @param shareId
     * @param userId
     */
    void praise(String shareId, String userId);

    /**
     * 取消赞
     *
     * @param shareId
     * @param userId
     */
    void cancelSharePraise(String shareId, String userId);

    /**
     * 收藏文件
     *
     * @param shareId
     * @param diskId
     * @param userId
     * @return
     * @throws CloudteachException
     */
    CloudDiskDTO collectFile(String shareId, String diskId, String userId, String schoolId) throws CloudteachException;

    /**
     * 后台置顶
     *
     * @param shareId
     */
    void stickyShareFile(String shareId);

    /**
     * 取消置顶
     *
     * @param shareId
     */
    void cancelStickyShareFile(String shareId);

    /**
     * 预览别人分享的文件
     *
     * @param shareId
     * @param currentUserId
     * @return
     */
    CloudDiskPreviewDTO previewFile(String shareId, String currentUserId);

    /**
     * 取消收藏
     *
     * @param shareId
     * @param userId
     * @return
     */
    CloudDiskCollectDTO cancelSharedCollectFile(String shareId, String userId, String schoolId);

    /**
     * 更新资源收藏数
     * statistic+1时加一 -1时减一
     *
     * @param shareId
     * @param statistic
     */
    void updateCollectStatistics(String shareId, Integer statistic);

    /**
     * 分享云盘资源
     *
     * @param cloudDiskShareUserInfoDTO
     */
    Integer shareFile(UserShareInfoDTO cloudDiskShareUserInfoDTO);
}
