package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtMagicQuestionBankStatistics;

public interface CtMagicQuestionBankStatisticsMapper {
    int deleteByPrimaryKey(String bankId);

    int insert(CtMagicQuestionBankStatistics record);

    CtMagicQuestionBankStatistics selectByPrimaryKey(String bankId);

    List<CtMagicQuestionBankStatistics> selectAll();

    int updateByPrimaryKey(CtMagicQuestionBankStatistics record);

    /**
     * 更新发布次数
     * @param record
     * @return
     */
    int updateReleases(CtMagicQuestionBankStatistics record);
}