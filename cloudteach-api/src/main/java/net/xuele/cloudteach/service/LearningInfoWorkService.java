package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.LearningInfoStuWorkDTO;
import net.xuele.cloudteach.dto.LearningInfoSynclassPlayGameDTO;
import net.xuele.common.exceptions.CloudteachException;

import java.util.List;

/**
 * Created by hujx on 2015/12/1 0001.
 * 学情-作业服务
 */
public interface LearningInfoWorkService {

    /**
     * 获取同步课堂作业游戏列表
     *
     * @param teacherId 教师ID
     * @param unitId    作业ID
     * @param schoolId  学校ID
     * @return
     * @throws CloudteachException
     */
    public List<LearningInfoSynclassPlayGameDTO> quarySynclassGameList(String teacherId, String unitId, String schoolId) throws CloudteachException;

    /**
     * 根据playid获取同步课堂学生游戏结果信息
     *
     * @param teacherId  教师ID
     * @param unitId     作业ID
     * @param classId    班级ID
     * @param schoolId   学校ID
     * @return
     * @throws CloudteachException
     */
    public LearningInfoStuWorkDTO quarySynclassAnswerInfo(String teacherId, String unitId, String classId, String schoolId) throws CloudteachException;

    /**
     * 获取作业学生回答信息
     *
     * @param teacherId 教师ID
     * @param unitId    课程单元ID
     * @param classId   班级ID
     * @param schoolId  学校ID
     * @return
     * @throws CloudteachException
     */
    public LearningInfoStuWorkDTO quaryWorkAnswerInfo(String teacherId, String unitId, int workType, String classId, String schoolId) throws CloudteachException;

}
