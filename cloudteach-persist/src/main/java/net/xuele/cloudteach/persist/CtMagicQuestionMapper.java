package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicQuestionMapper {
    int deleteByPrimaryKey(String queId);

    int insert(CtMagicQuestion record);

    CtMagicQuestion selectByPrimaryKey(@Param("queId") String queId);

    List<CtMagicQuestion> selectAll();

    List<Integer> queryMagicQuestionSlaveNumList(String bankId);

    List<CtMagicQuestion> queryMagicQuestionListByBankId(@Param("bankId") String bankId,@Param("orderNum") Integer orderNum);

    int updateByPrimaryKey(CtMagicQuestion record);

    /**
     * 查询某一题库下的题目数
     * @param bankId
     * @return
     */
    int countByBankId(@Param("bankId") String bankId,@Param("orderNum")int orderNum);
}