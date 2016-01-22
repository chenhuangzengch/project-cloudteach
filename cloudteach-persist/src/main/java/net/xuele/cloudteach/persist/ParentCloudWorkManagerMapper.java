package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.view.ParentCloudWorkManagerView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dj on 2015/7/31 0031.
 */
public interface ParentCloudWorkManagerMapper {
    List<ParentCloudWorkManagerView> queryIncompleteWorkList(@Param("pageSize")int pageSize,@Param("page") Page page,
                                                             @Param("studentId")String studentId,@Param("subjectId")String subjectId,
                                                             @Param("workType")Integer workType ,@Param("schoolId") String schoolId,@Param("publishTime") String publishTime);

    List<ParentCloudWorkManagerView> queryWorkList(@Param("pageSize")int pageSize,@Param("page") Page page,
                                                            @Param("studentId")String studentId,@Param("subjectId")String subjectId,
                                                            @Param("workType")Integer workType ,@Param("schoolId") String schoolId,@Param("publishTime") String publishTime);

    List<ParentCloudWorkManagerView> queryGuidanceWorkList(@Param("pageSize")int pageSize,@Param("page") Page page,
                                                           @Param("studentId")String studentId,@Param("subjectId")String subjectId,
                                                           @Param("workType")Integer workType ,@Param("schoolId") String schoolId,@Param("publishTime") String publishTime);
}
