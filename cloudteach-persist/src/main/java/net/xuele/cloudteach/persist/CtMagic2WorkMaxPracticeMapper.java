package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagic2WorkMaxPractice;
import net.xuele.cloudteach.view.Magic2WorkBookRateView;
import net.xuele.cloudteach.view.Magic2WorkUnitsRateView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagic2WorkMaxPracticeMapper {
    int deleteByPrimaryKey(@Param("practiceId")String practiceId,@Param("schoolId")String schoolId);

    int insert(CtMagic2WorkMaxPractice record);

    CtMagic2WorkMaxPractice selectByPrimaryKey(@Param("practiceId")String practiceId,@Param("schoolId")String schoolId);

    List<CtMagic2WorkMaxPractice> selectAll();

    int updateByPrimaryKey(CtMagic2WorkMaxPractice record);

    /**
     * 查询某一课程下的练习记录最高分
     * @param unitId
     * @param schoolId
     * @param userId
     * @return
     */
    CtMagic2WorkMaxPractice selectByUnitIdAndUserId(@Param("unitId")String unitId,@Param("schoolId")String schoolId,@Param("userId")String userId);

    /**
     * 按课本统计百分比
     * @param bookId
     * @param schoolId
     * @param classIds
     * @return
     */
    List<Magic2WorkBookRateView> selectBookRate(@Param("bookId")String bookId,@Param("schoolId")String schoolId,@Param("classIds")List<String> classIds);

    /**
     * 按课程统计百分比
     * @param bookId
     * @param schoolId
     * @param classIds
     * @return
     */
    List<Magic2WorkUnitsRateView> selectUnitsRate(@Param("bookId")String bookId,@Param("schoolId")String schoolId,@Param("classIds")List<String> classIds);
}