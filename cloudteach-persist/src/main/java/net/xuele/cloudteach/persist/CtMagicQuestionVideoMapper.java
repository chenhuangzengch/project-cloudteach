package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicQuestionVideo;

import java.util.List;

public interface CtMagicQuestionVideoMapper {
    int deleteByPrimaryKey(String videoId);

    int insert(CtMagicQuestionVideo record);

    CtMagicQuestionVideo selectByPrimaryKey(String videoId);

    List<CtMagicQuestionVideo> selectAll();

    int updateByPrimaryKey(CtMagicQuestionVideo record);

    /**
     * 根據題目id查詢對應的視頻講解
     * @param queId
     * @return
     */
    List<CtMagicQuestionVideo> selectByQueId(String queId);
}