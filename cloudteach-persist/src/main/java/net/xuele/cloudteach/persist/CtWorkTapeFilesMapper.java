package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtWorkTapeFiles;
import org.apache.ibatis.annotations.Param;

public interface CtWorkTapeFilesMapper {
    int removeWorkTapeFile(String fileId, String schoolId);

    int insert(CtWorkTapeFiles record);

    CtWorkTapeFiles selectByPrimaryKey(@Param("fileId") String fileId, @Param("schoolId") String schoolId);

    List<CtWorkTapeFiles> queryListByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateWorkTapeFile(CtWorkTapeFiles record);

    /**
     * 查询作业对应的录音附件（一个作业只能有一个录音附件）
     *
     * @param workId
     * @return
     */
    int selectTapeFile(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 逻辑删除作业对应录音附件
     *
     * @param workId
     * @return
     */
    int removeTapeFile(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     *
     *
     * @param workId
     * @param schoolId
     * @return
     */
    CtWorkTapeFiles getWorkTapeFilesByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<CtWorkTapeFiles> getWorkTapeFilesByWorkIdList(@Param("workIdList") List<String> workIdList);
}