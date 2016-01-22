package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtTeacherWorkClass;
import net.xuele.cloudteach.domain.CtWorkClassGather;
import org.apache.ibatis.annotations.Param;

public interface CtWorkClassGatherMapper {

    int insert(CtWorkClassGather record);

    CtWorkClassGather selectByPrimaryKey(@Param("workClassId") String workStudentId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtWorkClassGather record);

    int initCtWorkClassGather(@Param("initInfoList") List<CtTeacherWorkClass> asda);

    int initWorkClassGather(@Param("workId") String workId, @Param("schoolId") String schoolId, @Param("classList") List<String> classList);

    int updateStatusByClassList(@Param("status") int status, @Param("schoolId") String schoolId, @Param("classList") List<String> classList);

    int updateStatusByWorkId(@Param("status") int status, @Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 删除提分宝作业时逻辑删除作业班级汇总表信息
     *
     * @param workId
     * @param schoolId
     * @return
     */
    int delWorkClassGather(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<CtWorkClassGather> getWorkClassGatherByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    List<CtWorkClassGather> getInitInfo(@Param("schoolId") String schoolId,
                                        @Param("classList") List<String> classList,@Param("workId") String workId);

    int batchInsert(List<CtWorkClassGather> classGatherList);

    List<CtWorkClassGather> getWorkClassGatherByClassId(@Param("workId") String workId,@Param("classId") String classId,
                                                       @Param("schoolId") String schoolId);
}