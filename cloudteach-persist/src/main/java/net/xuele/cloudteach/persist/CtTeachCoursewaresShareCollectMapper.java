package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtTeachCoursewaresShareCollect;
import org.apache.ibatis.annotations.Param;

public interface CtTeachCoursewaresShareCollectMapper {
    int deleteByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    int insert(CtTeachCoursewaresShareCollect record);

    CtTeachCoursewaresShareCollect selectByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    List<CtTeachCoursewaresShareCollect> selectAll();

    int updateByPrimaryKey(CtTeachCoursewaresShareCollect record);
}