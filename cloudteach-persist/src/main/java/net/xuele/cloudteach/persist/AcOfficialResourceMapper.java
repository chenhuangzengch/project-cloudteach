package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.AcOfficialResource;
import net.xuele.cloudteach.domain.CtCloudDiskShareCount;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcOfficialResourceMapper {
    int deleteByPrimaryKey(String resourceId);

    int insert(AcOfficialResource record);

    /**
     * 分页获取官方资源
     * @param unitId
     * @param areaId
     * @param fileType
     * @param extType
     * @param pageSize
     * @param page
     * @return
     */
    List<AcOfficialResource> selectByPage(@Param(value = "unitId")String unitId,@Param(value = "areaId")String areaId,
                                    @Param(value = "fileType")Integer fileType,@Param(value = "extType")Integer extType,
                                    @Param(value = "pageSize") int pageSize,@Param("page") Page page);

    List<AcOfficialResource> selectAll();

    /**
     * 查询记录数
     * @param unitId
     * @param areaId
     * @param fileType
     * @param extType
     * @return
     */
    int selectCount(@Param(value = "unitId")String unitId,@Param(value = "areaId")String areaId,
                    @Param(value = "fileType")Integer fileType,@Param(value = "extType")Integer extType);
    /**
     * 根据主键查询
     * @param resourceId
     * @return
     */
    AcOfficialResource selectByPrimaryKey(String resourceId);

    int updateByPrimaryKey(AcOfficialResource record);

    /**
     *
     * @param unitId
     * @param schoolId
     * @param areaId
     * @return
     */
    List<CtCloudDiskShareCount> countByFileType(@Param(value = "unitId")String unitId,
                                                @Param(value = "schoolId")String schoolId,
                                                @Param(value = "areaId")String areaId);

}