package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtCloudDisk;
import net.xuele.cloudteach.domain.CtCloudDiskAmount;
import net.xuele.cloudteach.view.SRResourceSharedView;
import net.xuele.cloudteach.view.SRResourceStatisticsNumView;
import net.xuele.cloudteach.view.SRSchoolResourceStatisticsView;
import net.xuele.cloudteach.view.SRTeacherResourceStatisticsView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CtCloudDiskMapper {

    int cancelShare(String diskId);

    int insert(CtCloudDisk record);

    CtCloudDisk selectByPrimaryKey(@Param("schoolId") String schoolId, @Param("diskId") String diskId);

    /**
     * 查询一个云盘资源并锁定
     *
     * @param schoolId
     * @param diskId
     * @return
     */
    CtCloudDisk selectByPrimaryKeyForUpdate(@Param("schoolId") String schoolId, @Param("diskId") String diskId);

    int updateByPrimaryKey(CtCloudDisk record);

    int shareUpdate(@Param(value = "schoolId") String schoolId, @Param(value = "diskId") String diskId, @Param(value = "shareStatus") int shareStatus);

    /**
     * 分页获得活动条目
     *
     * @param pageSize    每页条数
     * @param page        分页对象  包含 start : 该页开始位置  和  end: 该页结束位置
     * @param ctCloudDisk 活动对象  【用于查询时条件分拣， 例如 只需要某种类型的资源，比如教案，或者ppt 】
     * @return
     */
    List<CtCloudDisk> selectPage(@Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                                 @Param(value = "ctCloudDisk") CtCloudDisk ctCloudDisk);

    /**
     * 获取符合条件的所有云盘资源
     *
     * @param ctCloudDisk
     * @return
     */
    List<CtCloudDisk> selectAll(CtCloudDisk ctCloudDisk);

    /**
     * 根据条件获取记录的总条数
     *
     * @param ctCloudDisk
     * @return
     */
    long selectCount(CtCloudDisk ctCloudDisk);

    /**
     * 判断是否我的资源
     *
     * @param diskId
     * @param userId
     * @return
     */
    long isMyFile(@Param(value = "diskId") String diskId, @Param(value = "userId") String userId, @Param(value = "schoolId") String schoolId);

    /**
     * 移动云盘资源
     *
     * @param ctCloudDisk
     * @return
     */
    int moveFile(CtCloudDisk ctCloudDisk);

    /**
     * 修改云盘文件名称
     *
     * @param ctCloudDisk
     * @return
     */
    int renameFile(CtCloudDisk ctCloudDisk);

    /**
     * 删除资源
     *
     * @param ctCloudDisk
     * @return
     */
    int removeFile(CtCloudDisk ctCloudDisk);

    /**
     * 置顶我的资源：仅在我的云盘中置顶显示
     *
     * @param ctCloudDisk
     * @return
     */
    int stickyFile(CtCloudDisk ctCloudDisk);

    /**
     * 取消置顶我的资源
     *
     * @param ctCloudDisk
     * @return
     */
    int cancelStickyFile(CtCloudDisk ctCloudDisk);

    /**
     * 批量移动资源
     *
     * @param schoolId
     * @param diskIds
     * @param fileType
     * @param unitId
     * @param userId   @return
     */
    int batchMoveFiles(@Param("schoolId") String schoolId, @Param("diskIds") List<String> diskIds, @Param("fileType") Integer fileType, @Param("unitId") String unitId, @Param("userId") String userId);

    /**
     * 批量删除资源
     *
     * @param schoolId
     * @param diskIds
     * @param currentUserID
     * @return
     */
    int batchRemoveFiles(@Param("schoolId") String schoolId, @Param("diskIds") List<String> diskIds, @Param("userId") String currentUserID);

    /**
     * 查询是否我收藏的
     * 如果返回值不小于1，说明是我收藏的
     *
     * @param shareId 分享ID
     * @param userId  用户ID
     * @return
     */
    long isMyCollect(@Param("shareId") String shareId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 查询我的收藏
     *
     * @param shareId
     * @param userId
     * @return
     */
    List<CtCloudDisk> queryMyCollect(@Param("shareId") String shareId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 获取一组云盘资源
     *
     * @param schoolId
     * @param diskIds
     * @return
     */
    List<CtCloudDisk> queryFilesByIds(@Param("schoolId") String schoolId, @Param("diskIds") List<String> diskIds);

    /**
     * 查询某个区域下教师数
     *
     * @param areaId
     * @return
     */
    int getTeacherAmount(@Param("areaId") String areaId, @Param("schoolId") String schoolId);

    /**
     * 查询某个区域下上传的资源数
     *
     * @param areaId
     * @param begTime 查询开始时间
     * @param endTime 查询截止时间
     * @return
     */
    int getAreaUnderResourceAmount(@Param("areaId") String areaId, @Param("begTime") String begTime,
                                   @Param("endTime") String endTime);

    /**
     * 查询某个区域下上传的资源数
     *
     * @param schoolId 学校ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    int getSchoolUnderResourceAmount(@Param("schoolId") String schoolId, @Param("begTime") String begTime,
                                     @Param("endTime") String endTime);

    /**
     * 查询某个教师上传的资源数
     *
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    int getTeacherUnderResourceAmount(@Param("schoolId") String schoolId, @Param("userId") String userId,
                                      @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 查询某个教师分享的资源数
     *
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    int getTeacherUnderShareResourceAmount(@Param("schoolId") String schoolId, @Param("userId") String userId,
                                           @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取最新分享（分页请求）
     *
     * @param pageSize 每页记录数
     * @param page     分页信息
     * @param areaId   区域ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    List<SRResourceSharedView> getSharedResource(@Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                                                 @Param("areaId") String areaId, @Param("schoolId") String schoolId, @Param("userId") String userId,
                                                 @Param("begTime") String begTime, @Param("endTime") String endTime);
    /**
     * 获取某个范围内的有分享资源的学校数
     *
     * @param areaId   区域ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    int getShareResourceSchoolAmount(@Param("areaId") String areaId, @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取某个范围内的有分享资源的教师数
     *
     * @param areaId   区域ID
     * @param schoolId 学校ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    int getShareResourceTeacherAmount(@Param("areaId") String areaId, @Param("schoolId") String schoolId,
                               @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取最新分享记录数
     *
     * @param areaId   区域ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    int getSharedResourceCount(@Param("areaId") String areaId, @Param("schoolId") String schoolId, @Param("userId") String userId,
                               @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取某个范围内按文件类型分类对应的资源数量
     *
     * @param areaId   区域ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    List<SRResourceStatisticsNumView> getResourceSortFileType(@Param("areaId") String areaId, @Param("schoolId") String schoolId, @Param("userId") String userId,
                                                              @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取某个范围内按文件后缀类型分类对应的资源数量
     *
     * @param areaId   区域ID
     * @param schoolId 学校ID
     * @param userId   教师ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    List<SRResourceStatisticsNumView> getResourceSortExtType(@Param("areaId") String areaId, @Param("schoolId") String schoolId, @Param("userId") String userId,
                                                             @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取区划下学校资源统计记录数
     *
     * @param areaId  区域ID
     * @param begTime 查询开始时间
     * @param endTime 查询截止时间
     * @return
     */
    int getSchoolResourceStatisticsCount(@Param("areaId") String areaId, @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取区划下学校资源统计
     *
     * @param pageSize 每页记录数
     * @param page     分页信息
     * @param areaId   区域ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    List<SRSchoolResourceStatisticsView> getSchoolResourceStatistics(@Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                                                                     @Param("areaId") String areaId, @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取区划下学校资源统计记录数
     *
     * @param areaId  区域ID
     * @param begTime 查询开始时间
     * @param endTime 查询截止时间
     * @return
     */
    int getTeacherResourceStatisticsCount(@Param("areaId") String areaId, @Param("schoolId") String schoolId, @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取区划下教师资源统计
     *
     * @param pageSize 每页记录数
     * @param page     分页信息
     * @param areaId   区域ID
     * @param begTime  查询开始时间
     * @param endTime  查询截止时间
     * @return
     */
    List<SRTeacherResourceStatisticsView> getTeacherResourceStatistics(@Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                                                                       @Param("areaId") String areaId, @Param("schoolId") String schoolId,
                                                                       @Param("begTime") String begTime, @Param("endTime") String endTime);

    /**
     * 获取我收藏的云盘文件列表
     *
     * @param ctCloudDisk
     * @return
     */
    List<CtCloudDisk> selectMyCollect(CtCloudDisk ctCloudDisk);

    /**
     * 获取我收藏的云盘文件pid
     *
     * @param schoolId
     * @param unitId
     * @param userId
     * @param collectStatus
     * @param fileType
     * @return
     */
    List<String> queryMyCollectPid(@Param(value = "schoolId") String schoolId,
                                   @Param("unitId") String unitId, @Param("userId") String userId,
                                   @Param("collectStatus") Integer collectStatus, @Param("fileType") Integer fileType,
                                   @Param("extType") Integer extType);

    /**
     * 根据资源类型统计资源数量
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param unitId   课程ID【非空】
     * @return List<CtCloudDiskAmount>
     */
    List<CtCloudDiskAmount> countByFileType(@Param("schoolId") String schoolId,
                                            @Param("userId") String userId, @Param("unitId") String unitId);

    /**
     * 查询用户在某个时间段内{上传}的资源总数
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 次数
     */
    Long selectUploadCount(@Param("schoolId") String schoolId, @Param("userId") String userId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 查询用户在某个时间段内{分享成功}的资源总数
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 次数
     */
    Long selectSharedCount(@Param("schoolId") String schoolId, @Param("userId") String userId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 获取教师用户主授科目下所有云盘资源
     *
     * @param subjectId
     * @param userId
     * @param schoolId
     * @return
     */
    List<CtCloudDisk> selectAllResourceBySubject(@Param("subjectId") String subjectId, @Param("userId") String userId, @Param("schoolId") String schoolId);

}
