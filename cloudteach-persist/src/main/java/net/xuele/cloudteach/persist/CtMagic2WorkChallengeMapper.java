package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagic2WorkChallenge;
import net.xuele.cloudteach.view.Magic2WorkClassStatisticsView;
import net.xuele.cloudteach.view.Magic2WorkClassTotalView;
import net.xuele.cloudteach.view.Magic2WorkMyStatisticsView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagic2WorkChallengeMapper {
    int deleteByPrimaryKey(String challengeId);

    int insert(CtMagic2WorkChallenge record);

    CtMagic2WorkChallenge selectByPrimaryKey(@Param("challengeId")String challengeId,@Param("schoolId")String schoolId);

    List<CtMagic2WorkChallenge> selectAll();

    int updateByPrimaryKey(CtMagic2WorkChallenge record);

    /**
     * 查询该课程下该用户的挑战次数
     * @param unitId
     * @param userId
     * @param schoolId
     * @return
     */
    int selectCount(@Param("unitId")String unitId,@Param("userId")String userId,@Param("schoolId")String schoolId);


    /**
     * 查询某一课程下的练习记录（同一练习返回最高分挑战记录）
     * @param schoolId
     * @param unitId
     * @param userId
     * @return
     */
    List<CtMagic2WorkChallenge> selectMaxPracticeList(@Param("schoolId")String schoolId,@Param("unitId")String unitId,@Param("userId")String userId);


    /**
     * 查询某一课程下的挑战记录
     * @param schoolId
     * @param unitId
     * @param userId
     * @return
     */
    List<CtMagic2WorkChallenge> selectChallengeList(@Param("schoolId")String schoolId,@Param("unitId")String unitId,@Param("userId")String userId);

    /**
     * 查询学生挑战总计
     * @param schoolId
     * @param unitId
     * @param userId
     * @return
     */
    Magic2WorkClassTotalView selectStuTotal(@Param("schoolId")String schoolId,@Param("unitId")String unitId,@Param("userId")String userId);
    /**
     * 提分宝个人统计
     * @param schoolId
     * @param unitId
     * @param userId
     * @return
     */
    List<Magic2WorkMyStatisticsView> selectMyStatistics(@Param("schoolId")String schoolId,@Param("unitId")String unitId,@Param("userId")String userId);

    /**
     * 提分宝班级统计
     * @param schoolId
     * @param unitId
     * @param classId
     * @return
     */
    List<Magic2WorkClassStatisticsView> selectClassStatistics(@Param("schoolId")String schoolId,@Param("unitId")String unitId,@Param("classId")String classId);

    /**
     * 提分宝班级总计
     * @param schoolId
     * @param unitId
     * @param classId
     * @return
     */
    Magic2WorkClassTotalView selectClassTotal(@Param("schoolId")String schoolId,@Param("unitId")String unitId,@Param("classId")String classId);
}