package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtBankItemShareFiles;
import org.apache.ibatis.annotations.Param;

public interface CtBankItemShareFilesMapper {

    int insert(CtBankItemShareFiles record);

    CtBankItemShareFiles selectByPrimaryKey(String fileId);

    int updateByPrimaryKey(CtBankItemShareFiles record);

    /**
     * 获取题目附件
     *
     * @param shareId
     * @return
     */
    List<CtBankItemShareFiles> selectAttachment(String shareId);

    /**
     * 批量插入附件
     *
     * @param shareId
     * @param diskIds
     * @return
     */
    int batchInsert(@Param("shareId") String shareId, @Param("diskIds") List<String> diskIds);

}