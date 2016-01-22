package net.xuele.cloudteach.persist;

import java.util.List;

import net.xuele.cloudteach.domain.CtSynclassWorkGame;
import net.xuele.cloudteach.view.SynclassWorkGameInfoView;
import org.apache.ibatis.annotations.Param;
import net.xuele.cloudteach.view.SynclassWorkGameView;

public interface CtSynclassWorkGameMapper {
    int deleteByPrimaryKey(@Param("workGameId") String workGameId, @Param("schoolId") String schoolId);

    int insert(CtSynclassWorkGame record);

    CtSynclassWorkGame selectByPrimaryKey(@Param("workGameId") String workGameId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtSynclassWorkGame record);

    List<CtSynclassWorkGame> selectByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int updateByWorkId(@Param("workId") String workId, @Param("schoolId") String schoolId);

    int batchInsert(List<CtSynclassWorkGame> gameList);

    int batchUpdate(@Param("gameList") List<CtSynclassWorkGame> gameList, @Param("workId") String workId, @Param("schoolId") String schoolId);

    List<SynclassWorkGameView> querySynclassWorkGameList(@Param("workId") String workId, @Param("schoolId") String schoolId);

    String getSynclassGameName(@Param("gameId") int gameId);

    String getSynclassGameType(@Param("gameId") int gameId);

}