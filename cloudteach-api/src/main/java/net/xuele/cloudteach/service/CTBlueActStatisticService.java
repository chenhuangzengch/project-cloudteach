package net.xuele.cloudteach.service;

import java.util.Date;

/**
 * CTBlueActStatisticService
 *      用于蓝色感恩月活动定时统计的服务
 * @author sunxh
 * @date 15/11/2
 */
public interface CTBlueActStatisticService {

    /**
     * 学校学生完成作业次数
     * @param schoolId  学校ID
     * @param start     开始时间
     * @param end       结束时间
     * @return  Long 次数
     */
    Long stuFinishWorkTimes(String schoolId,Date start,Date end);

    /**
     * 学校教师布置作业次数
     * @param schoolId  学校ID
     * @param start     开始时间
     * @param end       结束时间
     * @return  Long 次数
     */
    Long teacherPublishWorkTimes(String schoolId,Date start,Date end);


    /**
     * 学校教师批改作业次数
     * @param schoolId  学校ID
     * @param start     开始时间
     * @param end       结束时间
     * @return  Long 次数
     */
    Long teacherCorrectWorkTimes(String schoolId,Date start,Date end);


}
