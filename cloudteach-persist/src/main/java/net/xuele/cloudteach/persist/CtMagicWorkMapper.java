package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicWork;
import net.xuele.cloudteach.view.MagicWorkDetailView;
import org.apache.ibatis.annotations.Param;

public interface CtMagicWorkMapper {

    int insert(CtMagicWork record);

    CtMagicWork selectByPrimaryKey(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicWork record);

    /**
     * 获取某个提分宝作业信息
     */
    MagicWorkDetailView queryMagicWorkDetail(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 逻辑删除
     *
     * @param workId
     * @return
     */
    int delete(@Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 通过学生ID，获取提分宝作业信息
     *
     * @param studentId
     * @param schoolId
     * @return
     */
    CtMagicWork getMagicWorkByStudent(@Param("workId") String workId, @Param("studentId") String studentId,
                                      @Param("schoolId") String schoolId);

    /**
     * state：修改作业完成状态 0未完成 ，1部分完成，2全部完成
     *
     * @param workId
     * @param schoolId
     * @return
     */
    int updateFinishStatus(@Param("finishStatus") int finishStatus, @Param("workId") String workId, @Param("schoolId") String schoolId);

    /**
     * 修改作业批改状态 0未完成 ，1部分完成，2全部完成
     *
     * @param correctStatus
     * @param workId
     * @param schoolId
     * @return
     */
    int updateCorrectStatus(@Param("correctStatus") int correctStatus, @Param("workId") String workId, @Param("schoolId") String schoolId);
}