package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.view.CoursewareTeacherWorkView;
import net.xuele.cloudteach.view.LearningInfoWorkView;
import net.xuele.cloudteach.view.SubjectGatherView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CtWorkGatherMapper {

    int insert(CtWorkGather record);

    CtWorkGather selectByPrimaryKey(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtWorkGather record);

    /**
     * 删除提分宝作业时逻辑删除作业汇总表信息
     *
     * @param workId
     * @param schoolId
     * @return
     */
    int delWorkGather(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateStatusByWorkId(@Param("status") int status, @Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 获取某个教师某个课程对应某种教师作业（预习、电子、口语）信息,用于授课课件接口
     *
     * @param schoolId
     * @param unitId
     * @param userId
     * @param workType
     * @return
     */
    List<CoursewareTeacherWorkView> queryCoursewareTeacherWorkList(@Param("schoolId") String schoolId, @Param("unitId") String unitId,
                                                                   @Param("userId") String userId, @Param("workType") Integer workType);

    /**
     * 获取教师已布置作业的所有科目类型
     *
     * @param userId
     * @param schoolId
     * @return
     */
    List<SubjectGatherView> queryTeacherWorkSubjectList(@Param("userId") String userId, @Param("schoolId") String schoolId);

    /**
     * 获取班级对应已布置作业的所有科目类型
     *
     * @param classId
     * @param schoolId
     * @return
     */
    List<SubjectGatherView> queryClassWorkSubjectList(@Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 获取学生对应已布置作业的所有科目类型
     *
     * @param studentId
     * @param schoolId
     * @return
     */
    List<SubjectGatherView> queryStudentWorkSubjectList(@Param("studentId") String studentId, @Param("schoolId") String schoolId);

    /**
     * 通过各参数获取作业数量
     *
     * @param startTime
     * @param endTime
     * @param areaId
     * @param subjectId
     * @param grade
     * @return
     */
    Integer getWorkNum(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                       @Param("subjectId") String subjectId, @Param("grade") Integer grade
            , @Param("schoolId") String schoolId, @Param("userId") String userId);

    List<SRWork> getWorkList(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                             @Param("subjectId") String subjectId, @Param("grade") Integer grade,
                             @Param(value = "pageSize") int pageSize, @Param(value = "page") Page page,
                             @Param("schoolId") String schoolId, @Param("userId") String userId);

    /**
     * 根据学校ID获得对应学校下老师的数量
     *
     * @param schoolIdList
     * @return
     */
    List<SRSchoolStatistic> getTeacherAmount(List<String> schoolIdList);


    /**
     * 根据区域获得对应学校下老师发布的作业数量
     *
     * @param grade
     * @return
     */
    List<SRSchoolStatistic> getWorkAmount(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                                          @Param("subjectId") String subjectId, @Param("grade") Integer grade,
                                          @Param(value = "pageSize") int pageSize, @Param(value = "page") Page page);

    /**
     * 根据根据获得对应学校的各个属性
     *
     * @param schoolIdList
     * @return
     */
    List<SRSchoolStatistic> getSchoolStatistic(List<String> schoolIdList);

    Integer getSchoolStatisticAmount(@Param("areaId") String areaId);

    /**
     * 获得区域下全部的作业
     *
     * @param startTime
     * @param endTime
     * @param areaId
     * @param subjectId
     * @param grade
     * @return
     */
    Integer getAllWorkAmount(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                             @Param("subjectId") String subjectId, @Param("grade") Integer grade);

    /**
     * 通过各参数获取作业数量
     *
     * @param startTime
     * @param endTime
     * @param areaId
     * @param subjectId
     * @param grade
     * @return
     */
    Integer getBlackboardNum(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                             @Param("subjectId") String subjectId, @Param("grade") Integer grade,
                             @Param("schoolId") String schoolId, @Param("userId") String userId);


    /**
     * 获得板书列表
     *
     * @param startTime
     * @param endTime
     * @param areaId
     * @param subjectId
     * @param grade
     * @param pageSize
     * @param page
     * @return
     */
    List<SRBlackboard> getBlackboardList(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                                         @Param("subjectId") String subjectId, @Param("grade") Integer grade, @Param(value = "pageSize") int pageSize,
                                         @Param(value = "page") Page page, @Param("schoolId") String schoolId, @Param("userId") String userId);

    List<TeacherStatistic> getWorkByTeacher(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                                            @Param("subjectId") String subjectId, @Param("grade") Integer grade, @Param(value = "pageSize") int pageSize,
                                            @Param(value = "page") Page page, @Param("schoolId") String schoolId);


    TeacherStatistic getTeacherName(@Param("userId") String userId, @Param("schoolId") String schoolId);

    TeacherStatistic getSubjectByUser(@Param("userId") String userId, @Param("schoolId") String schoolId);

    Integer getTeacherAmout(@Param("areaId") String areaId, @Param("schoolId") String schoolId);

    List<TeacherStatistic> getBlackboardByTeacher(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                                                  @Param("subjectId") String subjectId, @Param("grade") Integer grade, @Param(value = "pageSize") int pageSize,
                                                  @Param(value = "page") Page page, @Param("schoolId") String schoolId);

    /**
     * 根据区域获得对应学校下老师发布的板书数量
     *
     * @param grade
     * @return
     */
    List<SRSchoolStatistic> getBlackboardAmount(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("areaId") String areaId,
                                                @Param("subjectId") String subjectId, @Param("grade") Integer grade,
                                                @Param(value = "pageSize") int pageSize, @Param(value = "page") Page page);

    List<LearningInfoWorkView> selectWorkList(@Param("unitId") String unitId, @Param("teacherId") String teacherId,
                                              @Param("workType") int workType, @Param("schoolId") String schoolId);

}