package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtMagicQuestionHistory;

public interface CtMagicQuestionHistoryMapper {
    int deleteByPrimaryKey(String hisId);

    int insert(CtMagicQuestionHistory record);

    CtMagicQuestionHistory selectByPrimaryKey(String hisId);

    List<CtMagicQuestionHistory> selectAll();

    int updateByPrimaryKey(CtMagicQuestionHistory record);
}