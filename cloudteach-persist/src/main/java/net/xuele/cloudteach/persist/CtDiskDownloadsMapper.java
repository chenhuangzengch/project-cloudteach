package net.xuele.cloudteach.persist;

import java.util.Date;
import java.util.List;

import net.xuele.cloudteach.domain.CtDiskDownloads;
import org.apache.ibatis.annotations.Param;

public interface CtDiskDownloadsMapper {
    int deleteByPrimaryKey(@Param("fileId") String fileId, @Param("userId") String userId);

    int insert(CtDiskDownloads record);

    CtDiskDownloads selectByPrimaryKey(@Param("fileId") String fileId, @Param("userId") String userId);

    List<CtDiskDownloads> selectAll();

    int updateByPrimaryKey(CtDiskDownloads record);

    /**
     * 查询用户一定时间内（time） 下载的数量
     *
     * @param schoolId     学校Id
     * @param userId       用户Id
     * @param time         时间范围【time-->now】
     * @return
     */
    long countDownload(@Param("schoolId") String schoolId, @Param("userId") String userId, @Param("time") Date time);
}