package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CloudDiskPageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.Date;
import java.util.List;

/**
 * Created by sunxh on 2015/6/8 0008.
 * 云盘服务抽象，
 * 【和云盘模块相关的服务都由这个服务负责】
 */
public interface CloudDiskService {

    /**
     * 根据请求条件分页获得云盘信息，
     * 有可能抛出异常信息【运行时异常，上级调用可以不用捕获】
     *
     * @param request 云盘查询入参 含分页属性
     * @return PageResponse<UserFileDTO>
     * @throws net.xuele.common.exceptions.CloudteachException
     */
    PageResponse<CloudDiskDTO> queryMyFilesByPage(CloudDiskPageRequest request) throws CloudteachException;

    /**
     * 上传云盘资源
     * 【资源信息初始化，至少需包含
     * schoolId:所属用户的学校ID、userId:文件所属用户id、unitId:课程编号、fileUri:HDFS文件uri、
     * fileType:云盘文件类型、name：文件名、extension：扩展名、size：文件大小】
     *
     * @param cloudDiskDTO 云盘资源DTO
     * @return CloudDiskDTO 经初始化的云盘资源DTO
     * @throws CloudteachException
     */
    CloudDiskDTO uploadFile(CloudDiskDTO cloudDiskDTO) throws CloudteachException;

    /**
     * 取消收藏资源
     * 如果传入的diskId不属于当前用户，会抛出异常
     *
     * @param schoolId      学校ID
     * @param diskId        云盘资源ID
     * @param currentUserID 用户ID
     * @return CloudDiskCollectDTO
     * @throws CloudteachException
     */
    CloudDiskCollectDTO cancelCollectFile(String schoolId, String diskId, String currentUserID) throws CloudteachException;

    /**
     * 查询符合条件的所有云盘信息
     * 可处理的条件：
     * unitId：课程ID，不能为空
     * fileType：资源类型[该项为空表示查询所有资源类型]
     * extType：扩展名类型[该项为空或为0均代表不判断扩展名类型]
     * userId：用户ID，不能为空
     * <p/>
     * schoolId：学校ID，不能为空
     *
     * @param cloudDiskDTO 云盘资源DTO
     * @return List<CloudDiskDTO>
     */
    List<CloudDiskDTO> queryMyFiles(CloudDiskDTO cloudDiskDTO);

    /**
     * 移动云盘资源
     * 需传入的参数
     * diskId：资源ID，不能为空
     * userId：用户ID，不能为空
     * fileType：资源类型【要移动到的】,不能为空
     * unitId：课程ID【要移动到的】，不能为空
     * schoolId：学校ID，不能为空
     *
     * @param cloudDiskUpdateDTO 云盘更新DTO
     */
    void moveFile(CloudDiskUpdateDTO cloudDiskUpdateDTO);

    /**
     * 重命名资源
     * 需传入的参数
     * diskId：资源ID，不能为空
     * userId：用户ID，不能为空
     * name：新名称，不能为空
     *
     * @param cloudDiskUpdateDTO 云盘更新DTO
     */
    void renameFile(CloudDiskUpdateDTO cloudDiskUpdateDTO);

    /**
     * 删除资源
     * 需传入的参数
     * diskId：资源ID，不能为空
     * userId：用户ID，不能为空
     *
     * @param cloudDiskUpdateDTO 云盘更新DTO
     */
    void removeFile(CloudDiskUpdateDTO cloudDiskUpdateDTO);

    /**
     * 置顶我的资源：仅在我的云盘中显示置顶
     * 需传入的参数
     * diskId：资源ID，不能为空
     * userId：用户ID，不能为空
     *
     * @param cloudDiskUpdateDTO 云盘更新DTO
     */
    void stickyFile(CloudDiskUpdateDTO cloudDiskUpdateDTO);

    /**
     * 取消置顶我的资源
     * 需传入的参数
     * diskId：资源ID，不能为空
     * userId：用户ID，不能为空
     *
     * @param cloudDiskUpdateDTO 云盘更新DTO
     */
    void cancelStickyFile(CloudDiskUpdateDTO cloudDiskUpdateDTO);

    /**
     * 批量移动资源
     *
     * @param schoolId      学校ID
     * @param diskIds       云盘资源ID
     * @param fileType      资源类型
     * @param unitId        课程ID
     * @param currentUserID 用户ID
     */
    void batchMoveFiles(String schoolId, List<String> diskIds, Integer fileType, String unitId, String currentUserID);

    /**
     * 批量删除文件
     *
     * @param schoolId      学校ID
     * @param diskIds       云盘资源ID
     * @param currentUserID 用户ID
     */
    void batchRemoveFiles(String schoolId, List<String> diskIds, String currentUserID);

    /**
     * 下载文件
     *
     * @param schoolId      学校ID
     * @param diskId        云盘资源ID
     * @param currentUserID 用户ID
     * @return CloudDiskDownloadDTO
     * @throws CloudteachException
     */
    CloudDiskDownloadDTO downloadFile(String schoolId, String diskId, String currentUserID) throws CloudteachException;

    /**
     * 预览文件
     *
     * @param schoolId 学校ID
     * @param diskId   云盘资源ID
     * @return CloudDiskPreviewDTO
     */
    CloudDiskPreviewDTO previewFile(String schoolId, String diskId);

    /**
     * 获取一组云盘资源引用数据
     *
     * @param diskIds 云盘资源ID
     * @return List<CloudDiskReferDTO>
     */
    List<CloudDiskReferDTO> queryFilesByIds(String schoolId, List<String> diskIds);

    /**
     * 根据ID获取一个云盘资源
     *
     * @param schoolId 学校ID
     * @param diskId   云盘资源ID
     * @return CloudDiskDTO 云盘资源DTO
     */
    CloudDiskDTO getFileById(String schoolId, String diskId);

    /**
     * 查询我收藏的云盘文件列表【来自大家的分享】
     * 可处理的条件：
     * unitId：课程ID，不能为空
     * userId：用户ID，不能为空
     * schoolId：学校ID，不能为空
     * fileType：资源类型[该项为空表示查询所有资源类型]
     *
     * @param cloudDiskDTO 云盘资源DTO
     * @return List<CloudDiskDTO>
     * @throws CloudteachException
     */
    List<CloudDiskDTO> queryMyCollectFrShare(CloudDiskDTO cloudDiskDTO) throws CloudteachException;

    /**
     * 查询我收藏的云盘文件列表【来自官方资源】
     * 可处理的条件：
     * unitId：课程ID，不能为空
     * userId：用户ID，不能为空
     * schoolId：学校ID，不能为空
     * fileType：资源类型[该项为空表示查询所有资源类型]
     *
     * @param cloudDiskDTO 云盘资源DTO
     * @return List<CloudDiskDTO>
     * @throws CloudteachException
     */
    List<CloudDiskDTO> queryMyCollectFrOfficial(CloudDiskDTO cloudDiskDTO) throws CloudteachException;

    /**
     * 列出所有资源类型对应的我的云盘资源数量
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param unitId   课程ID【非空】
     * @return List<CloudDiskAmountDTO> 资源数列表
     * @throws CloudteachException
     */
    List<CloudDiskAmountDTO> listDiskAmout(String schoolId, String userId, String unitId) throws CloudteachException;

    /**
     * 查询用户在任务时间内上传的云盘资源数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 上传次数
     * @throws CloudteachException
     */
    Long getDiskUploadAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;

    /**
     * 查询用户在任务时间内分享成功的云盘资源数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 分享次数
     * @throws CloudteachException
     */
    Long getDiskSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException;

    /**
     * 获取教师主授科目下所有的云盘资源
     *
     * @param subjectId
     * @param userId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    List<CloudDiskDTO> getAllResourceBySubject(String subjectId, String userId, String schoolId) throws CloudteachException;
}
