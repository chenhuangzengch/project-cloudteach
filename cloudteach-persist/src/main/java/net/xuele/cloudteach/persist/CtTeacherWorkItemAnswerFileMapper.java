package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswerFile;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkItemAnswerFileMapper {

    int insert(CtTeacherWorkItemAnswerFile record);

    CtTeacherWorkItemAnswerFile selectByPrimaryKey(@Param("answerFileId") String answerFileId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkItemAnswerFile record);

    int deleteTeacherWorkItemAnswerFile(@Param("answerList") List<String> answerList, @Param("schoolId") String schoolId);

    int deleteStuWorkItemAnswerFile(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkItemAnswerFile> getAnswerFileInfoByAnswerId(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

}