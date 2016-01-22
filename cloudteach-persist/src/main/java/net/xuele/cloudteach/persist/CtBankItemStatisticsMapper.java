package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtBankItemStatistics;
import org.apache.ibatis.annotations.Param;

public interface CtBankItemStatisticsMapper {

    int insert(CtBankItemStatistics record);

    CtBankItemStatistics selectByPrimaryKey(@Param("itemId") String itemId,@Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtBankItemStatistics record);

    int updateReleasesByItem(@Param("itemId") String itemId, @Param("range") int range, @Param("schoolId") String schoolId);

}