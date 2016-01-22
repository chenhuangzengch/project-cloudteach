package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtCloudDiskShare;
import net.xuele.cloudteach.domain.CtCloudDiskShareCount;
import net.xuele.cloudteach.view.CtCloudDiskShareView;
import net.xuele.cloudteach.view.TcExtFLCloudDiskShareView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtCloudDiskShareMapper {
    int deleteByPrimaryKey(String shareId);

    int insert(CtCloudDiskShare record);

    CtCloudDiskShare selectByPrimaryKey(String shareId);

    List<CtCloudDiskShare> selectAll();

    int updateByPrimaryKey(CtCloudDiskShare record);

    /**
     * 查询总数
     * @param record
     * @return
     */
    int count(CtCloudDiskShare record);

    /**
     * 我的收藏计数
     * @param record
     * @return
     */
    int myCount(@Param("record") CtCloudDiskShare record,
                @Param("list") List<String> list);

    /**
     *
     * @param unitId
     * @param schoolId
     * @param areaId
     * @return
     */
    List<CtCloudDiskShareCount> countByFileType(@Param(value = "unitId")String unitId,@Param(value = "schoolId")String schoolId,@Param(value = "areaId")String areaId);

    List<CtCloudDiskShareView> query(@Param(value = "pageSize") int pageSize,@Param("page") Page page,
                                     @Param("record") CtCloudDiskShare record,@Param("order") int order,
                                     @Param("list") List<String> list);

    /**
     * 根据文件ID  同一个文件同时智能被分享一次
     * @param diskId
     * @return
     */
    CtCloudDiskShare selectByUserAndDiskId(@Param("diskId")String diskId);
    /**
     * 后台置顶
     * @param record
     * @return
     */
    int stickyShareFile(CtCloudDiskShare record);

    /**
     * 取消分享
     * @param diskId
     * @return
     */
    int cancelShare(@Param(value = "diskId") String diskId);

    /**
     * 分享审核
     * @param record
     * @return
     */
    int shareAudit(CtCloudDiskShare record);

    /**
     * 判断是否我分享的
     * @param shareId
     * @param userId
     * @return
     */
    int isMyShare(@Param("shareId")String shareId,@Param("userId")String userId);

    /**
     * 根据云盘id查询分享的云盘资源
     * @param diskId
     * @return
     */
    CtCloudDiskShare selectByDiskId(@Param("diskId")String diskId);

    /**
     * 课件查询大家的分享计数
     * @param schoolId
     * @param unitId
     * @param areaId
     * @param extType
     * @return
     */
    int queryTcExtFLCount(@Param("schoolId") String schoolId,
                          @Param("unitId") String unitId,
                          @Param("areaId") String areaId,
                          @Param("extType") Integer extType);

    /**
     * 课件查询大家的分享计数
     * @param pageSize
     * @param page
     * @param schoolId
     * @param unitId
     * @param areaId
     * @param extType
     * @return
     */
    List<TcExtFLCloudDiskShareView> queryTcExtFL(@Param(value = "pageSize") int pageSize,
                                                 @Param("page") Page page,
                                                 @Param("schoolId") String schoolId,
                                                 @Param("unitId") String unitId,
                                                 @Param("areaId") String areaId,
                                                 @Param("extType") Integer extType);

}