package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicQuestionBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicQuestionBankMapper {
    int deleteByPrimaryKey(String bankId);

    int insert(CtMagicQuestionBank record);

    CtMagicQuestionBank selectByPrimaryKey(String bankId);

    List<CtMagicQuestionBank> selectAll();

    List<CtMagicQuestionBank> queryMagicQuestionBankListByUnitId(@Param(value = "unitId")String unitId,
                                                                 @Param(value = "extraBookId")String extraBookId);

    int updateByPrimaryKey(CtMagicQuestionBank record);
}