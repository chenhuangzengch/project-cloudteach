package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtCoursewareClassDetailLog;
import org.apache.ibatis.annotations.Param;

public interface CtCoursewareClassDetailLogMapper {

    @Deprecated
    int deleteByPrimaryKey(String ccdlId);

    /**
     * 插入授课记录明细数据
     *
     * @param record 授课记录明细domain对象
     * @return 受影响的行数
     */
    int insert(CtCoursewareClassDetailLog record);

    @Deprecated
    CtCoursewareClassDetailLog selectByPrimaryKey(String ccdlId);

    @Deprecated
    List<CtCoursewareClassDetailLog> selectAll();

    @Deprecated
    int updateByPrimaryKey(CtCoursewareClassDetailLog record);

    /**
     * 查询某个授课记录的所有明细数据
     *
     * @param cclId    授课记录ID
     * @param schoolId 学校ID
     * @return List<CtCoursewareClassDetailLog>
     */
    List<CtCoursewareClassDetailLog> selectByLogId(@Param("cclId") String cclId, @Param("schoolId") String schoolId);
}