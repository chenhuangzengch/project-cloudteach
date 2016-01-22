package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtTeachCoursewares;
import net.xuele.common.exceptions.CloudteachException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CtTeachCoursewaresMapper {
    int deleteByPrimaryKey(String coursewaresId);

    int insert(CtTeachCoursewares record);

    CtTeachCoursewares selectByPrimaryKey(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);

    List<CtTeachCoursewares> selectAll();

    int updateByPrimaryKey(CtTeachCoursewares record);

    /**
     * 获取我收藏的授课课件
     *
     * @param pid
     * @param creator
     * @return
     */
    CtTeachCoursewares selectMyCollect(@Param("pid") String pid, @Param("creator") String creator, @Param("schoolId") String schoolId);

    /**
     * 获取我的课件
     *
     * @param uintId
     * @param userId
     * @return
     */
    List<CtTeachCoursewares> selectByUnitId(@Param("schoolId") String schoolId, @Param("unitId") String uintId, @Param("userId") String userId);

    /**
     * 取消分享
     *
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    int unShareCourseware(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);

    CtTeachCoursewares selectByPrimaryKey(@Param("coursewaresId") String coursewaresId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    List<CtTeachCoursewares> selectByReappearId(@Param("schoolId") String schoolId, @Param("reappearId") String reappearId);

    /**
     * 删除课件
     *
     * @param schoolId      学校Id
     * @param coursewaresId 课件Id
     * @return int
     */
    int removeTeachCourseware(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);

    /**
     * 取消收藏课件
     *
     * @param schoolId      学校Id
     * @param coursewaresId 课件Id
     * @return int
     */
    int cancelCollectTeachCourseware(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);


    /**
     * 分享课件
     *
     * @param schoolId      学校ID
     * @param coursewaresId 课件ID
     * @return
     */
    int shareCourseware(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);

    /**
     * 置顶课件
     *
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    int stickyTeachCourseware(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);

    /**
     * 取消置顶课件
     *
     * @param schoolId
     * @param coursewaresId
     * @return
     */
    int cancelStickyTeachCourseware(@Param("schoolId") String schoolId, @Param("coursewaresId") String coursewaresId);

    /**
     * 查询用户在某个时间段内{分享成功}的课件总数
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 次数
     */
    Long selectSharedCount(@Param("schoolId") String schoolId, @Param("userId") String userId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 更新课件打包状态
     *
     * @param schoolId     学校ID
     * @param coursewareId 课件ID
     * @param packStatus   打包状态 :0未打包,1打包中,2打包成功,3打包失败
     * @param fileUri      zip包文件Key
     * @return int 受影响的行数
     * @throws CloudteachException
     */
    int updateCoursePackStatus(@Param("schoolId") String schoolId, @Param("coursewareId") String coursewareId,@Param("packStatus") Integer packStatus,@Param("fileUri") String fileUri);
}