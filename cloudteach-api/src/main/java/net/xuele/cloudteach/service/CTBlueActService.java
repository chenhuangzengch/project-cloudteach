package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.BlueStudentSubWorkDTO;
import net.xuele.cloudteach.dto.BlueTeacherCorrectWorkDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.Date;
import java.util.List;

/**
 * Created by hux on 2015/11/2 0002.
 * 蓝色感恩月活动服务
 */
public interface CTBlueActService {

    /**
     * 时间段内，教师批改的所有预习/电子作业的数量
     *
     * @param teacherId
     * @param begin
     * @param end
     * @return
     * @throws CloudteachException
     */
    public List<BlueTeacherCorrectWorkDTO> getCorrectWork(String teacherId, Date begin, Date end, String schoolId) throws CloudteachException;

    /**
     * 时间段内，教师布置并且学生提交的所有预习/电子作业的数量
     *
     * @param teacherId
     * @param begin
     * @param end
     * @return
     * @throws CloudteachException
     */
    public List<BlueStudentSubWorkDTO> getSubedWork(String teacherId, Date actBegin, Date begin, Date end, String schoolId) throws CloudteachException;

    /**
     * 时间段内，学生提交的所有预习/电子作业的数量
     *
     * @param studentId
     * @param begin
     * @param end
     * @return
     * @throws CloudteachException
     */
    public List<BlueStudentSubWorkDTO> getStudentSubedWork(String studentId, Date begin, Date end, String schoolId) throws CloudteachException;

}
