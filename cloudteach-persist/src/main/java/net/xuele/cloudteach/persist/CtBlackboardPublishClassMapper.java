package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBlackboardPublishClass;
import org.apache.ibatis.annotations.Param;

public interface CtBlackboardPublishClassMapper {
    int deleteByPrimaryKey(String blackboardClassId);

    int insert(CtBlackboardPublishClass record);

    CtBlackboardPublishClass selectByPrimaryKey(@Param("blackboardClassId")String blackboardClassId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublishClass> selectAll(String schoolId);

    int updateByPrimaryKey(CtBlackboardPublishClass record);

    int batchInsert(@Param("list")List<CtBlackboardPublishClass> list);

    int updateStatusByBlackboardId(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    List<String> getClassList(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    int batchUpdateByClassList(@Param("list")List<String> list,@Param("schoolId")String schoolId);

}