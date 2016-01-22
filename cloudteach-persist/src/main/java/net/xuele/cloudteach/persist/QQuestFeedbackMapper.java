package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.QQuestFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QQuestFeedbackMapper {
    int deleteByPrimaryKey(String fbId);

    int insert(QQuestFeedback record);

    /**
     * 批量插入
     * @param records
     * @return
     */
    int batchInsert(@Param("records") List<QQuestFeedback> records);

    QQuestFeedback selectByPrimaryKey(String fbId);

    List<QQuestFeedback> selectAll();

    int updateByPrimaryKey(QQuestFeedback record);
}