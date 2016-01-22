package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtCloudDiskShareStatistics;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

public interface CtCloudDiskShareStatisticsMapper {
    int deleteByPrimaryKey(String shareId);

    int insert(CtCloudDiskShareStatistics record);

    CtCloudDiskShareStatistics selectByPrimaryKey(String shareId);

    List<CtCloudDiskShareStatistics> selectAll();

    int updateByPrimaryKey(CtCloudDiskShareStatistics record);
}