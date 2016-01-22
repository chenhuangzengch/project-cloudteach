package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.view.AfterClassWorkView;
import net.xuele.cloudteach.view.EffectiveWorkView;
import net.xuele.cloudteach.view.GuidanceWorkView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherWorkManageMapper {
    /**
     * 分页获取进行中作业
     */
    List<EffectiveWorkView> queryEffectiveWork(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                               @Param("userId") String userId, @Param("classId") String classId,
                                               @Param("subjectId") String subjectId, @Param("workType") String workType,
                                               @Param("schoolId") String schoolId, @Param("publishTime") String publishTime);

    /**
     * 获取某个教师所有进行中作业
     */
    List<EffectiveWorkView> queryAllEffectiveWorkByUserId(@Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 分页获取课后作业
     */
    List<AfterClassWorkView> queryAfterClassWork(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                                 @Param("userId") String userId, @Param("classId") String classId,
                                                 @Param("subjectId") String subjectId, @Param("workType") String workType,
                                                 @Param("schoolId") String schoolId, @Param("publishTime") String publishTime);

    /**
     * 分页获取预习作业
     */
    List<GuidanceWorkView> queryGuidanceWork(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                             @Param("userId") String userId, @Param("classId") String classId,
                                             @Param("subjectId") String subjectId, @Param("schoolId") String schoolId,
                                             @Param("publishTime") String publishTime);

    /**
     * 获取
     * @param userId
     * @param classId
     * @param schoolId
     * @return
     */
    long selectExtraWorkListCount(@Param("userId") String userId, @Param("classId") String classId,
                                  @Param("schoolId") String schoolId);

    /**
     * 分页获取课外作业
     */
    List<AfterClassWorkView> selectExtraWorkList(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                                 @Param("userId") String userId, @Param("classId") String classId,
                                                 @Param("schoolId") String schoolId, @Param("publishTime") String publishTime);

}