package net.xuele.cloudteach.persist;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by sunxh on 2015/11/2
 */
public interface CTBlueActStatisticMapper {

    /**
     * 时间段内，统计学校教师批改的预习/电子作业的数量
     *
     * @param schoolId  学校ID
     * @param start 开始时间
     * @param end   截止时间
     * @return Long 次数
     */
    Long teacherPublishWorkTimes(@Param("schoolId") String schoolId ,@Param("start") Date start, @Param("end") Date end );

    /**
     * 时间段内，统计学校教师布置并且批改的预习/电子作业的数量
     *
     * @param schoolId  学校ID
     * @param start 开始时间
     * @param end   截止时间
     * @return Long 次数
     */
    Long teacherCorrectWorkTimes(@Param("schoolId") String schoolId ,@Param("start") Date start, @Param("end") Date end );

    /**
     * 时间段内，统计学校学生提交的所有预习/电子作业的数量
     *
     * @param schoolId  学校ID
     * @param start 开始时间
     * @param end   截止时间
     * @return Long 次数
     */
    Long stuFinishWorkTimes(@Param("schoolId") String schoolId ,@Param("start") Date start, @Param("end") Date end );
}
