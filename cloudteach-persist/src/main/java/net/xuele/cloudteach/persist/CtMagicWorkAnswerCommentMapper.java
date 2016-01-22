package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswerComment;
import net.xuele.cloudteach.view.MagicWorkAnswerCommNameView;
import net.xuele.cloudteach.view.WorkAnswerCommentView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkAnswerCommentMapper {

    int insert(CtMagicWorkAnswerComment record);

    CtMagicWorkAnswerComment selectByPrimaryKey(@Param("commentId") String commentId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkAnswerComment record);

    /**
     * 逻辑删除提分宝作业对应的回答评论记录
     *
     * @param workId
     * @return
     */
    int deleteByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 逻辑删除提分宝作业对应的回答评论记录--单条
     *
     * @param answerId
     * @return
     */
    int delete(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    /**
     * @param answerId
     * @param limitRange
     * @param schoolId
     * @return
     */
    List<WorkAnswerCommentView> getAnswerCommentsInfo(@Param("answerId") String answerId, @Param("limitRange") int limitRange,
                                                      @Param("schoolId") String schoolId);

}