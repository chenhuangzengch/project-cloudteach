package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBankItemShareStatistics;

public interface CtBankItemShareStatisticsMapper {

    int insert(CtBankItemShareStatistics record);

    CtBankItemShareStatistics selectByPrimaryKey(String shareId);

    int updateByPrimaryKey(CtBankItemShareStatistics record);

    /**
     * 更改统计表数量
     * @param record
     * @return
     */
    int updateCount(CtBankItemShareStatistics record);

}