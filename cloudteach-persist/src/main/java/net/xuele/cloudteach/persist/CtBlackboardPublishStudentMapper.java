package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBlackboardPublishStudent;
import org.apache.ibatis.annotations.Param;

public interface CtBlackboardPublishStudentMapper {
    int deleteByPrimaryKey(String blackboardUserId);

    int insert(CtBlackboardPublishStudent record);

    CtBlackboardPublishStudent selectByPrimaryKey(@Param("blackboardUserId")String blackboardUserId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublishStudent> selectAll(@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtBlackboardPublishStudent record);

    int batchInsert(List<CtBlackboardPublishStudent> list);

    int updateByBlackboardIdAndUserIdSelective(CtBlackboardPublishStudent record);

    int updateStatusByBlackboardId(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    int getStudentNumByClassIdList(@Param("list")List<String> list,@Param("schoolId")String schoolId);

    int batchUpdateByClassIdList(@Param("list")List<String> list,@Param("schoolId")String schoolId);

    int getCountByBlackboardId(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublishStudent> getInitInfo(@Param("classList")List<String> classList,
                                                 @Param("schoolId")String schoolId,@Param("blackboardId")String blackboardId);
}