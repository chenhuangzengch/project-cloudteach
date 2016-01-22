package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtUnits;
import net.xuele.cloudteach.view.SchoolSynBookView;
import net.xuele.cloudteach.view.StudentNumView;
import net.xuele.cloudteach.view.SynUnitView;
import net.xuele.cloudteach.view.UnitBookNameView;
import org.apache.ibatis.annotations.Param;

public interface CtUnitsMapper {
    int deleteByPrimaryKey(String unitId);

    int insert(CtUnits record);

    CtUnits selectByPrimaryKey(String unitId);

    List<CtUnits> selectAll();

    int updateByPrimaryKey(CtUnits record);

    List<CtUnits> selectByBookid(String bookId);

    int selectCount(String bookId);

    List<SchoolSynBookView> querySchoolSynBookListBySchoolId(String schoolId);

    List<SynUnitView> querySynUnitListByBookId(String bookId);

    UnitBookNameView queryUnitBookNameByUnitId(String unitId);

    UnitBookNameView querySynclassUnitBookNameByUnitId(String unitId);

    List<StudentNumView> getStudentNumBySchoolId(@Param("classIdList")List<String> classIdList,@Param("schoolId")String schoolId);
}