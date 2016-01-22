package net.xuele.cloudteach.persist;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hujx on 2015/7/15 0015.
 */
public interface StudentWorkDetailMapper {

    /**
     * @param workId
     * @param studentId
     * @return
     */
    int guidanceItemCount(@Param("workId") String workId, @Param("studentId") String studentId, @Param("schoolId") String schoolId);

    /**
     * @param workItemId
     * @param studentId
     * @return
     */
    //StudentDoGuidanceWorkView studentDoGuidanceWorkView(@Param("workItemId") String workItemId, @Param("StudentId") String studentId);

    /**
     * 点击某个预习作业后，进入该预习作业的详细
     *
     * @param workId
     * @param studentId
     * @return
     */
    //List<StudentDoGuidanceWorkView> myGuidanceItemWorkList(@Param("workId") String workId, @Param("studentId") String studentId,
    //                                                       @Param("schoolId") String schoolId);

}
