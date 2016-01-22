package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtBankItemFiles;
import net.xuele.cloudteach.view.AttachmentInfoView;
import net.xuele.cloudteach.view.TeacherWorkFileDetailView;
import org.apache.ibatis.annotations.Param;

public interface CtBankItemFilesMapper {
    int insert(CtBankItemFiles record);

    CtBankItemFiles selectByPrimaryKey(@Param("fileId") String fileId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtBankItemFiles record);

    List<CtBankItemFiles> getInitInfo(@Param("workId") String workId, @Param("itemId") String itemId, @Param("schoolId") String schoolId);

    int initCopyBankItemFiles(@Param("initInfoList") List<CtBankItemFiles> initInfoList);

    /**
     * 查询题目附件
     *
     * @param schoolId
     * @param itemId
     * @param type
     * @return
     */
    List<CtBankItemFiles> selectAttachment(@Param("schoolId") String schoolId, @Param("itemId") String itemId, @Param("type") Integer type);

    /**
     * 查询题目附件数
     * 仅用于教师题目，作业题目不可用
     * @param schoolId
     * @param itemId
     * @return
     */
    long selectAttachmentAmount(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    /**
     * 批量插入附件
     *
     * @param schoolId
     * @param type
     * @param itemId
     * @param diskIds
     * @return
     */
    int batchInsert(@Param("schoolId") String schoolId, @Param("type") Integer type, @Param("itemId") String itemId, @Param("diskIds") List<String> diskIds);

    /**
     * 删除附件
     *
     * @param schoolId
     * @param itemId
     */
    void deleteByItemId(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    List<AttachmentInfoView> attachmentInfoForJson(@Param("itemId") String itemId, @Param("type") Integer type, @Param("schoolId") String schoolId);

    /**
     * 获取某个题目所带的附件信息
     *
     * @param workItemId
     * @param schoolId
     * @return
     */
    List<TeacherWorkFileDetailView> teacherWorkFileInfo(@Param("workItemId") String workItemId, @Param("schoolId") String schoolId);

}