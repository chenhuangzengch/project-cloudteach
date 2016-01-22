package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.view.BlueStudentSubWorkView;
import net.xuele.cloudteach.view.BlueTeacherCorrectWorkView;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/11/2 0002.
 */
public interface CTBlueActMapper {

    /**
     * 时间段内，教师批改的所有预习/电子作业的数量
     *
     * @param teacherId
     * @param begin
     * @param end
     * @param schoolId
     * @return
     */
    List<BlueTeacherCorrectWorkView> selectCorrectWork(@Param("teacherId") String teacherId, @Param("begin") Date begin,
                                                       @Param("end") Date end, @Param("schoolId") String schoolId);

    /**
     * 时间段内，教师布置并且学生提交的所有预习/电子作业的数量
     *
     * @param teacherId
     * @param begin
     * @param end
     * @param schoolId
     * @return
     */
    List<BlueStudentSubWorkView> selectSubedWork(@Param("teacherId") String teacherId, @Param("actBegin")Date actBegin,@Param("begin") Date begin,
                                                 @Param("end") Date end, @Param("schoolId") String schoolId);

    /**
     * 时间段内，学生提交的所有预习/电子作业的数量
     *
     * @param studentId
     * @param begin
     * @param end
     * @param schoolId
     * @return
     */
    List<BlueStudentSubWorkView> selectStudentSubedWork(@Param("studentId") String studentId, @Param("begin") Date begin,
                                                        @Param("end") Date end, @Param("schoolId") String schoolId);
}
