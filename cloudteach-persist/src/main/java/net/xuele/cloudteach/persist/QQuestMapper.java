package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.QQuest;
import net.xuele.cloudteach.view.QQuestSortPView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QQuestMapper {
    int deleteByPrimaryKey(String qId);

    int insert(QQuest record);

    QQuest selectByPrimaryKey(String qId);

    List<QQuest> selectAll();

    int updateByPrimaryKey(QQuest record);

    /**
     * 根据课程id，获取原题的num道题目
     *
     * @param unitId
     * @param num
     * @return
     */
    List<QQuest> selectByUnitId(@Param("unitId") String unitId, @Param("num") int num);

    /**
     * 获取某个课程下所有的原题题目
     * @param unitId
     * @return
     */
    List<QQuest> selectAllOriByUnitId(@Param("unitId") String unitId);
    /**
     * 查询题目sort，用于随机取题
     *
     * @param unitId
     * @return
     */
    //List<Integer> queryQQuestSortList(String unitId);

    /**
     * 查询原题对应的衍生题
     *
     * @param unitId
     * @param sort
     * @return
     */
    List<QQuest> selectChildByUnitId(@Param("unitId") String unitId, @Param("sort") int sort, @Param("parentIds") List<String> parentIds);


    /**
     * 根据课程id，获取原题及其衍生题的num道题目
     *
     * @param unitId
     * @param num
     * @return
     */
    //List<QQuestView> selectFullQuestByUnitId(@Param("unitId") String unitId, @Param("num") Integer num);

    /**
     * 查询最大衍生题sort
     * @param unitId
     * @return
     */
    //int queryQQuestMaxSort(String unitId);

    /**
     * 查询一个题目及其选项
     * @param qId
     * @return
     */
    //CtMagic2QuestInfo selectQuestInfo(String qId);

    /**
     * 更改题目状态
     * @param qId
     * @return
     */
    int updateStatus(@Param("qId") String qId,@Param("status") int status);

    /**
     * 查询课程下所有题目，根据sort排序
     * @param unitId
     * @return
     */
    List<QQuest> selectAllQuestByUnitId(@Param("unitId") String unitId);

    /**
     * 查询课程下原题数量
     * @param unitId
     * @return
     */
    int selectOriNum(@Param("unitId") String unitId);

    /**
     * 查询题目parent_id和sort
     * @param qId
     * @param unitId
     * @return
     */
    QQuestSortPView selectSortAndPIdByQId(@Param("qId") String qId,@Param("unitId") String unitId);
}