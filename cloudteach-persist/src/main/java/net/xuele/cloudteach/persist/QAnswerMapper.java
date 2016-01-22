package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.QAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QAnswerMapper {
    int deleteByPrimaryKey(String aId);

    int insert(QAnswer record);

    QAnswer selectByPrimaryKey(String aId);

    List<QAnswer> selectAll();

    int updateByPrimaryKey(QAnswer record);

    /**
     * 查询某一题的选项
     * @param qId
     * @return
     */
    List<QAnswer> selectByQId(@Param("qId")String qId);
}