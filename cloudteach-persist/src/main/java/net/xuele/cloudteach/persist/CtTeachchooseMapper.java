package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.view.TeachchooseView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CtTeachchooseMapper {

    List<TeachchooseView> queryTeachchooseListByPage(@Param("pageSize")int pageSize,@Param("page") Page page,
                                                             @Param("schoolId")String schoolId,@Param("subjectId")String subjectId,
                                                             @Param("grade")Integer grade ,@Param("startTime") String startTime,@Param("endTime") String endTime);

    Long queryTeachchooseCount(@Param("schoolId")String schoolId,@Param("subjectId")String subjectId,
                                                     @Param("grade")Integer grade ,@Param("startTime") String startTime,@Param("endTime") String endTime);

}