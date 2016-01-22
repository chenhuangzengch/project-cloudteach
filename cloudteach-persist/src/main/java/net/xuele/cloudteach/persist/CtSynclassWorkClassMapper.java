package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtSynclassWorkClass;
import net.xuele.cloudteach.view.WorkClassView;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkClassMapper {
    int deleteByPrimaryKey(@Param("workClassId")String workClassId,@Param("schoolId")String schoolId);

    int insert(CtSynclassWorkClass record);

    CtSynclassWorkClass selectByPrimaryKey(@Param("workClassId")String workClassId,@Param("schoolId")String schoolId);

    int updateByPrimaryKey(CtSynclassWorkClass record);

    List<CtSynclassWorkClass> selectByWorkId(@Param("workId")String workId,@Param("schoolId")String schoolId);

    int updateByWorkId(@Param("workId")String workId,@Param("schoolId")String schoolId);

    int batchInsert(List<CtSynclassWorkClass> classList);

    int batchUpdate(@Param("classList")List<CtSynclassWorkClass> classList,@Param("schoolId")String schoolId);

    List<WorkClassView> querySynclassWorkClassList(@Param("workId") String workId,@Param("schoolId")String schoolId);
}