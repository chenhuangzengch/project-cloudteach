package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.view.BasicWorkInfoView;
import net.xuele.cloudteach.view.StudentWorkListView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hujx on 2015/7/12 0012.
 */
public interface StudentWorkListMapper {

    /**
     * @param userId
     * @param subjectId
     * @param workType
     * @param schoolId
     * @return
     */
    long getTodoListRecordCount(@Param("userId") String userId, @Param("subjectId") String subjectId,
                                @Param("workType") int workType, @Param("schoolId") String schoolId);

    /**
     * @param userId
     * @param subjectId
     * @param schoolId
     * @return
     */
    long getPrepListRecordCount(@Param("userId") String userId, @Param("subjectId") String subjectId,
                                @Param("schoolId") String schoolId);

    /**
     * @param userId
     * @param subjectId
     * @param workType
     * @param schoolId
     * @return
     */
    long getWorkListRecordCount(@Param("userId") String userId, @Param("subjectId") String subjectId,
                                @Param("workType") int workType, @Param("schoolId") String schoolId);

    /**
     * @param workId
     * @param userId
     * @param schoolId
     * @return
     */
    BasicWorkInfoView getBasicTeacherWorkInfo(@Param("workId") String workId, @Param("userId") String userId,
                                              @Param("schoolId") String schoolId);

    /**
     * @param workId
     * @param userId
     * @param schoolId
     * @return
     */
    BasicWorkInfoView getBasicMagicWorkInfo(@Param("workId") String workId, @Param("userId") String userId,
                                            @Param("schoolId") String schoolId);

    /**
     * @param workId
     * @param userId
     * @param schoolId
     * @return
     */
    BasicWorkInfoView getBasicSynClassWorkInfo(@Param("workId") String workId, @Param("userId") String userId,
                                               @Param("schoolId") String schoolId);

    /**
     * @param pageSize
     * @param page
     * @param userId
     * @param subjectId
     * @param schoolId
     * @return
     */
    List<StudentWorkListView> myToDoList(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                         @Param("userId") String userId, @Param("subjectId") String subjectId,
                                         @Param("workType") int workType, @Param("publishTime") String publishTime,
                                         @Param("schoolId") String schoolId);

    /**
     * @param pageSize
     * @param page
     * @param userId
     * @param subjectId
     * @param schoolId
     * @return
     */
    List<StudentWorkListView> myPrepList(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                         @Param("userId") String userId, @Param("subjectId") String subjectId,
                                         @Param("publishTime") String publishTime, @Param("schoolId") String schoolId);

    /**
     * @param pageSize
     * @param page
     * @param userId
     * @param subjectId
     * @param schoolId
     * @return
     */
    List<StudentWorkListView> myWorkList(@Param(value = "pageSize") int pageSize, @Param("page") Page page,
                                         @Param("userId") String userId, @Param("subjectId") String subjectId,
                                         @Param("workType") int workType, @Param("publishTime") String publishTime,
                                         @Param("schoolId") String schoolId);

}
