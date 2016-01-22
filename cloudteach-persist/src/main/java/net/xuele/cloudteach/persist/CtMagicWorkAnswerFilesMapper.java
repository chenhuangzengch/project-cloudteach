package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswerFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkAnswerFilesMapper {

    int insert(CtMagicWorkAnswerFiles record);

    CtMagicWorkAnswerFiles selectByPrimaryKey(@Param("answerFileId") String answerFileId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkAnswerFiles record);

    /**
     * 逻辑删除提分宝作业对应的回答附件表记录
     *
     * @param workId
     * @return
     */
    int deleteByWorkId(@Param("workId")String workId, @Param("schoolId") String schoolId);

    /**
     * 逻辑删除提分宝作业对应的回答附件表记录--单条
     *
     * @param answerId
     * @return
     */
    int delete(@Param("answerId") String answerId, @Param("schoolId") String schoolId);

    /**
     * 通过回答ID得到回答附件列表信息
     *
     * @param answerId
     * @param schoolId
     * @return
     */
    List<CtMagicWorkAnswerFiles> getAnswerFileInfoByAnswerId(@Param("answerId") String answerId, @Param("schoolId") String schoolId);
}