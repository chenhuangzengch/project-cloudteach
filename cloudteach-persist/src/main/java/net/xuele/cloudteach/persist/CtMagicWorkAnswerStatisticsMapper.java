package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswer;
import net.xuele.cloudteach.domain.CtMagicWorkAnswerStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkAnswerStatisticsMapper {

    int insert(CtMagicWorkAnswerStatistics record);

    CtMagicWorkAnswerStatistics selectByPrimaryKey(@Param("answerId") String answerId,@Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkAnswerStatistics record);

    /**
     * 逻辑删除提分宝作业回答表对应的统计表记录
     * @param workId
     * @return
     */
    int deleteByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     *
     * @param answerId
     * @return
     */
    int delete(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    /**
     * 用户发布提分宝作业时 提分宝作业回答统计表初始化
     * @param answerList
     * @return
     */
    int initialize(@Param("answerList") List<CtMagicWorkAnswer> answerList);

}