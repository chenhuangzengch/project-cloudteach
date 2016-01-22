package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtSynclassWorkAnswerComment;
import net.xuele.cloudteach.view.SynclassWorkAnswerCommNameView;
import net.xuele.cloudteach.view.WorkAnswerCommentView;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkAnswerCommentMapper {

    int insert(CtSynclassWorkAnswerComment record);

    CtSynclassWorkAnswerComment selectByPrimaryKey(@Param("commentId") String commentId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtSynclassWorkAnswerComment record);

    int updateStatus(@Param("commentId") String commentId, @Param("schoolId") String schoolId);

    /**
     * 通过作业学生ID获得评论信息列表
     *
     * @param workUserId
     * @param limitRange
     * @param schoolId
     * @return
     */
    List<WorkAnswerCommentView> getAnswerCommentsInfo(@Param("workUserId") String workUserId, @Param("limitRange") int limitRange,
                                                               @Param("schoolId") String schoolId);


    int deleteByWorkUserId(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

}