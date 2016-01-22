package net.xuele.cloudteach.persist;

import java.util.Date;
import java.util.List;

import net.xuele.cloudteach.domain.CtSynclassWorkClass;
import net.xuele.cloudteach.domain.CtSynclassWorkGame;
import net.xuele.cloudteach.domain.CtSynclassWorkPlay;
import net.xuele.cloudteach.view.*;
import org.apache.ibatis.annotations.Param;

public interface CtSynclassWorkPlayMapper {
    int deleteByPrimaryKey(@Param("playId") String playId, @Param("schoolId") String schoolId);

    int insert(CtSynclassWorkPlay record);

    CtSynclassWorkPlay selectByPrimaryKey(@Param("playId") String playId, @Param("schoolId") String schoolId);

    CtSynclassWorkPlay selectByGameId(@Param("gameId") String gameId, @Param("userId") String userId, @Param("schoolId") String schoolId);

    CtSynclassWorkPlay selectByWorkIdAndGameId(@Param("workId") String workId, @Param("gameId") String gameId,
                                               @Param("userId") String userId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtSynclassWorkPlay record);

    int updateByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int batchUpdateByGame(@Param("gameList") List<CtSynclassWorkGame> gameList, @Param("workId") String workId, @Param("schoolId") String schoolId);

    int batchUpdateByClass(@Param("classList") List<CtSynclassWorkClass> classList, @Param("schoolId") String schoolId);

    int selectCount(CtSynclassWorkPlay record);

    /**
     * 通过学生作业ID，得到游戏记录列表
     *
     * @param workUserId
     * @param schoolId
     * @return
     */
    List<CtSynclassWorkPlay> getSynclassWorkPlayInfoList(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

    List<SynclassWorkGamePlayInfoView> getSynclassWorkGameInfo(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);

    List<SynclassWorkGameInfoView> getSynclassWorkGameId(@Param("workId") String workId, @Param("studentId") String studentId,
                                                         @Param("schoolId") String schoolId);

    int updateAfterSubmit(@Param("workUserId") String workUserId, @Param("subTime") Date subTime, @Param("schoolId") String schoolId);

    /**
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    List<SynclassWorkGamePlayInfoView> getSynclassWorkGameByWorkStu(@Param("workId") String workId, @Param("studentId") String studentId,
                                                                    @Param("schoolId") String schoolId);

    int delete(@Param("workUserId") String workUserId, @Param("schoolId") String schoolId);


    List<CtSynclassWorkPlay> getInitInfoByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int batchInsertPlay(List<CtSynclassWorkPlay> playList);

    List<CtSynclassWorkPlay> getInitInfoByClassAndGame(@Param("workId") String workId, @Param("classList") List<CtSynclassWorkClass> classList,
                                                       @Param("gameList") List<CtSynclassWorkGame> gameList, @Param("schoolId") String schoolId);

    /**
     * @param workId
     * @param studentId
     * @param schoolId
     * @return
     */
    List<Integer> getNotFinishedGames(@Param("workId") String workId, @Param("studentId") String studentId,
                                      @Param("schoolId") String schoolId);

    /**
     * 获取某个同步课堂作业中某游戏对应某个班级中已提交作业的学生信息
     *
     * @param workId     作业ID
     * @param classId    班级ID
     * @param workGameId 游戏附件ID
     * @param schoolId   学校ID
     * @return
     */
    List<SynclassWorkPlayView> queryClassGameSubedStuList(@Param("workId") String workId, @Param("classId") String classId,
                                                          @Param("workGameId") String workGameId, @Param("schoolId") String schoolId);

    List<LearningInfoSynclassGameView> selectSynclassGame(@Param("teacherId") String teacherId, @Param("workId") String workId,
                                                          @Param("schoolId") String schoolId);

    List<LearningInfoStuSynclassWorkView> selectSynclassPlay(@Param("workGameId") String workGameId, @Param("schoolId") String schoolId);
}