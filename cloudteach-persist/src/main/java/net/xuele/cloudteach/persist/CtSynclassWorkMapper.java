package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtSynclassWork;
import net.xuele.cloudteach.view.MagicWorkDetailView;
import net.xuele.cloudteach.view.SynclassWorkDetailView;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkMapper {

    int insert(CtSynclassWork record);

    CtSynclassWork selectByPrimaryKey(@Param("workId")String workId,@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtSynclassWork record);

    int updateByWorkId(@Param("workId")String workId,@Param("schoolId")String schoolId);

    /**
     * 获取某个同步课堂作业信息
     * */
    SynclassWorkDetailView querySynclassWorkDetail(@Param("workId")String workId,@Param("schoolId")String schoolId);

    int updateByDelStudent(@Param("finishStatus")Integer finishStatus,@Param("correctStatus")Integer correctStatus,
                           @Param("workId")String workId,@Param("schoolId")String schoolId);

    int updateFinishStatusByWorkId(@Param("finishStatus")Integer finishStatus,
                                   @Param("workId")String workId,@Param("schoolId")String schoolId);
}