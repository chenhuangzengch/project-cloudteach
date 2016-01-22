package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtTeachCoursewaresShareContent;

public interface CtTeachCoursewaresShareContentMapper {
    int deleteByPrimaryKey(String coursewaresId);

    int insert(CtTeachCoursewaresShareContent record);

    CtTeachCoursewaresShareContent selectByPrimaryKey(String coursewaresId);

    List<CtTeachCoursewaresShareContent> selectAll();

    int updateByPrimaryKey(CtTeachCoursewaresShareContent record);
}