package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswerComment;
import net.xuele.cloudteach.view.TeacherWorkItemAnswerCommNameView;
import net.xuele.cloudteach.view.WorkAnswerCommentView;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkItemAnswerCommentMapper {

    int insert(CtTeacherWorkItemAnswerComment record);

    CtTeacherWorkItemAnswerComment selectByPrimaryKey(@Param("commentId") String commentId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkItemAnswerComment record);

    int deleteTeacherWorkItemAnswerComment(@Param("answerList") List<String> answerList, @Param("schoolId") String schoolId);

    int deleteStuWorkItemAnswerComment(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    /**
     * @param answerId
     * @param limitRange 3/100
     * @param schoolId
     * @return
     */
    List<WorkAnswerCommentView> getAnswerCommentsInfo(@Param("answerId") String answerId, @Param("limitRange") int limitRange,
                                                      @Param("schoolId") String schoolId);

    /**
     * 获取某个学生对该作业其他学生回答的评论个数
     *
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    Integer selectCommentOthersTimes(@Param("workId") String workId, @Param("studentId") String studentId,
                                     @Param("answerId") String answerId, @Param("schoolId") String schoolId);
}