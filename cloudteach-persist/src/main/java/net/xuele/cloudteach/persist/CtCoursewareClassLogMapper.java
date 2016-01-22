package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtCoursewareClassLog;
import org.apache.ibatis.annotations.Param;

public interface CtCoursewareClassLogMapper {

    @Deprecated
    int deleteByPrimaryKey(String cclId);

    int insert(CtCoursewareClassLog record);

    /**
     * 按学校ID和授课记录ID查询单条记录
     * 学校ID（schhoolId）为分片参数
     *
     * @param schoolId 学校ID
     * @param cclId    授课记录ID
     * @return CtCoursewareClassLog
     */
    CtCoursewareClassLog selectByPrimaryKey(@Param("schoolId") String schoolId, @Param("cclId") String cclId);

    @Deprecated
    List<CtCoursewareClassLog> selectAll();

    @Deprecated
    int updateByPrimaryKey(CtCoursewareClassLog record);

    /**
     * 更新结束时间
     *
     * @param record 授课记录
     * @return 受影响行数
     */
    int updateLogEnd(CtCoursewareClassLog record);

    /**
     * 查询某个课件相关的所有授课记录
     *
     * @param cclCoursewaresId 课件ID
     * @param schoolId         学校ID
     * @return List<CtCoursewareClassLog>
     */
    List<CtCoursewareClassLog> selectByCourseware(@Param("cclCoursewaresId") String cclCoursewaresId, @Param("schoolId") String schoolId);

    /**
     * 查询某个课件某个班级相关的所有授课记录
     *
     * @param cclCoursewaresId 课件ID
     * @param schoolId         学校ID
     * @param cclClassId       班级ID
     * @return List<CtCoursewareClassLog>
     */
    List<CtCoursewareClassLog> selectByCoursewareAndClass(@Param("cclCoursewaresId") String cclCoursewaresId, @Param("schoolId") String schoolId, @Param("cclClassId") String cclClassId);
}