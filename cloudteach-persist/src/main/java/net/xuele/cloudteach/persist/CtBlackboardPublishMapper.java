package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBlackboardPublish;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

public interface CtBlackboardPublishMapper {
    int deleteByPrimaryKey(String blackboardId);

    int insert(CtBlackboardPublish record);

    CtBlackboardPublish selectByPrimaryKey(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublish> selectAll(@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtBlackboardPublish record);

    //可以选择的进行修改
    int updateByPrimaryKeyOpt(CtBlackboardPublish record);

    long selectCount(@Param("userId")String userId,@Param("schoolId")String schoolId);

    List<CtBlackboardPublish> selectPage(@Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                                         @Param(value = "userId") String userId,@Param("schoolId")String schoolId);

    int updateStatusByPrimaryKey(@Param("blackboardId")String blackboardId,@Param("schoolId")String schoolId);

    CtBlackboardPublish selectNewBlackboardPublish(@Param("userId")String userId,@Param("unitId")String unitId,@Param("schoolId")String schoolId);
}