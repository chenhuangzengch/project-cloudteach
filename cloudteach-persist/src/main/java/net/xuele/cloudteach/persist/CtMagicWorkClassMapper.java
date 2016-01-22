package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWorkAnswer;
import net.xuele.cloudteach.domain.CtMagicWorkClass;
import net.xuele.cloudteach.view.WorkClassView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicWorkClassMapper {

    int insert(CtMagicWorkClass record);

    CtMagicWorkClass selectByPrimaryKey(@Param("workClassId") String workClassId,@Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWorkClass record);

    List<WorkClassView> queryMagicWorkClassList(@Param("workId") String workId,@Param("schoolId") String schoolId);

    /**
     * 逻辑删除提分宝作业对应的班级
     * @param workId
     * @return
     */
    int deleteByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据作业id查询对应的班级列表
     * @param workId
     * @return
     */
    List<CtMagicWorkClass> selectByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 根据班级id对应的班级信息
     * @param workId
     * @param classId
     * @return
     */
    int deleteByClassId(@Param("workId") String workId,@Param("classId") String classId, @Param("schoolId") String schoolId);

    /**
     * 查询班级对应的学生回答信息
     * @param workClass
     * @return
     */
    List<CtMagicWorkAnswer> selectWorkAnswer(@Param("workClass") CtMagicWorkClass workClass);

}