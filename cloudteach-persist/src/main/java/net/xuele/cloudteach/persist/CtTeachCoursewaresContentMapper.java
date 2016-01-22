package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtTeachCoursewaresContent;
import org.apache.ibatis.annotations.Param;

public interface CtTeachCoursewaresContentMapper {
    int deleteByPrimaryKey(String coursewaresId);

    int insert(CtTeachCoursewaresContent record);

    CtTeachCoursewaresContent selectByPrimaryKey(@Param("coursewaresId")String coursewaresId,@Param("schoolId")String schoolId);

    List<CtTeachCoursewaresContent> selectAll(String schoolId);

    int updateByPrimaryKey(CtTeachCoursewaresContent record);

    CtTeachCoursewaresContent selectByPrimaryKey(@Param("coursewaresId")String coursewaresId,@Param("userId")String userId,@Param("schoolId")String schoolId);

    /**
     *
     * @param coursewaresIdList 主键List
     * @param belongType 内容所属对象
     * @return
     */
    List<CtTeachCoursewaresContent> getInitInfo(@Param( "coursewaresIdList") List<String> coursewaresIdList,@Param("reappearId")String reappearId,
                                                @Param( "belongType")int belongType,@Param("schoolId")String schoolId);

    int batchInsert(@Param( "list")List<CtTeachCoursewaresContent> coursewaresContentList);

    /**
     *
     * @param coursewaresIdList
     * @param belongType 所属类型
     * @param schoolId
     * @return
     */
    List<CtTeachCoursewaresContent> selectByPrimaryIdList(@Param("coursewaresIdList") List<String> coursewaresIdList,
                                                          @Param("belongType")Integer belongType,@Param("schoolId")String schoolId);
}