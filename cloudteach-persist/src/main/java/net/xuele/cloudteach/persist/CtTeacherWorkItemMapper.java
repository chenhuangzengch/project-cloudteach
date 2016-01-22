package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkItem;
import net.xuele.cloudteach.view.TeacherWorkItemView;
import org.apache.ibatis.annotations.Param;

public interface CtTeacherWorkItemMapper {

    int insert(CtTeacherWorkItem record);

    CtTeacherWorkItem selectByPrimaryKey(@Param("workItemId") String workItemId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtTeacherWorkItem record);

    String getItemIdByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    CtTeacherWorkItem getInitInfo(@Param("workId") String workId, @Param("itemId") String itemId, @Param("schoolId") String schoolId);
    //List<CtTeacherWorkItem> getInitInfo(@Param("workId") String workId, @Param("itemId") String itemId, @Param("schoolId") String schoolId);

    int initCtTeacherWorkItem(@Param("initInfoList") List<CtTeacherWorkItem> initInfoList);

    int deleteTeacherWorkItem(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<CtTeacherWorkItem> queryTeacherWorkItemList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<TeacherWorkItemView> queryItemContainFilesList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 通过作业ID得到作业题目信息
     *
     * @param workId
     * @param schoolId
     * @return
     */
    CtTeacherWorkItem getTeacherWorkItemByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);
}