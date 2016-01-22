package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtTeachCoursewaresSharePraise;
import org.apache.ibatis.annotations.Param;

public interface CtTeachCoursewaresSharePraiseMapper {
    int deleteByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    int insert(CtTeachCoursewaresSharePraise record);

    CtTeachCoursewaresSharePraise selectByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    List<CtTeachCoursewaresSharePraise> selectAll();

    int updateByPrimaryKey(CtTeachCoursewaresSharePraise record);
}