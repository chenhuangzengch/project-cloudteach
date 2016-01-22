package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBlackboardPublishFiles;
import net.xuele.cloudteach.view.AttachmentInfoView;
import net.xuele.cloudteach.view.FileInfoView;
import org.apache.ibatis.annotations.Param;

public interface CtBlackboardPublishFilesMapper {
    int deleteByPrimaryKey(String fileId);

    int insert(CtBlackboardPublishFiles record);

    CtBlackboardPublishFiles selectByPrimaryKey(@Param("")String fileId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublishFiles> selectAll(@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtBlackboardPublishFiles record);

    int batchInsert(List<CtBlackboardPublishFiles> list);

    int updateStatusByBlackboardId(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    int batchUpdateByUrl(@Param("blackboardId")String blackboardId,@Param("urlList")List<String> urlList,@Param("schoolId")String schoolId);

    List<FileInfoView> selectByUrl(@Param("schoolId")String schoolId,@Param("fileKeyList")List<String> fileKeyList);

    List<CtBlackboardPublishFiles> selectByBlackboardId(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublishFiles> selectBlackboardListByUnitId(@Param("userId")String userId,@Param("unitId")String blackboardId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublishFiles> selectAddLessionPlanBlackboardListByUnitId(@Param("userId")String userId,@Param("unitId")String blackboardId,@Param("schoolId")String schoolId);
}