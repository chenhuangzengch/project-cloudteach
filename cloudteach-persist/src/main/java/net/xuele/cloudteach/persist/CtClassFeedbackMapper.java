package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtClassFeedback;
import net.xuele.cloudteach.view.ClassFeedbackView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtClassFeedbackMapper {
    int deleteByPrimaryKey(String fbId);

    int insert(CtClassFeedback record);

    CtClassFeedback selectByPrimaryKey(@Param("fbId") String fbId,@Param("schoolId") String schoolId);

    List<CtClassFeedback> selectAll();

    int updateByPrimaryKey(CtClassFeedback record);


    List<CtClassFeedback> selectClassFBByCwIdList(@Param("schoolId") String schoolId,
                                                  @Param("uploadUserId") String uploadUserId,
                                                  @Param("coursewaresId") String coursewaresId,
                                                  @Param("classId") String classId,
                                                  @Param("begTime") String begTime,
                                                  @Param("endTime") String endTime);

    /**
     * 分页查询随堂反馈资源
     * @param unitId
     * @param uploadUserId
     * @param schoolId
     * @param classId
     * @param pageSize
     * @param page
     * @return
     */
    List<ClassFeedbackView> queryPageList(@Param("unitId") String unitId,
                                @Param("uploadUserId") String uploadUserId,
                                @Param("schoolId") String schoolId,
                                @Param("classId") String classId,
                                @Param("pageSize") int pageSize,
                                @Param("page") Page page
                                );

    /**
     * 查询随堂反馈资源数
     * @param unitId
     * @param uploadUserId
     * @param schoolId
     * @param classId
     * @return
     */
    int count(@Param("unitId") String unitId,
              @Param("uploadUserId") String uploadUserId,
              @Param("schoolId") String schoolId,
              @Param("classId") String classId);
}