package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtTeachCoursewaresShare;
import net.xuele.cloudteach.view.CoursewaresResponseView;
import net.xuele.cloudteach.view.TeachCoursewaresShareView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtTeachCoursewaresShareMapper {
    int deleteByPrimaryKey(String shareId);

    int insert(CtTeachCoursewaresShare record);

    CtTeachCoursewaresShare selectByPrimaryKey(String shareId);

    List<CtTeachCoursewaresShare> selectAll();

    int updateByPrimaryKey(CtTeachCoursewaresShare record);

    /**
     * 分页查询
     * @param pageSize
     * @param page
     * @param record
     * @param seltype
     * @return
     */
    List<TeachCoursewaresShareView> selectByPage(@Param(value = "pageSize") int pageSize,@Param("page") Page page,@Param("record") CtTeachCoursewaresShare record, @Param("seltype") int seltype);

    /**分享总数*/
    int selectCount(@Param("record") CtTeachCoursewaresShare record,@Param("seltype") int seltype);

    /**我的分享总数*/
    int myShareCount(CtTeachCoursewaresShare record);

    /**
     * 取消分享
     * @param shareId
     * @return
     */
    int unShareCourseware( @Param("shareId") String shareId);

    /**
     * 预览
     * @param shareId
     * @return
     */
    CoursewaresResponseView preview(@Param("shareId") String shareId);
}