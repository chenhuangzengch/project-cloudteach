package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtBankItem;
import net.xuele.cloudteach.view.BankItemView;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CtBankItemMapper {

    int insert(CtBankItem record);

    CtBankItem selectByPrimaryKey(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    int updateByPrimaryKey(CtBankItem record);

    /**
     * 根据来源id，收藏用户，获取预习项分享中收藏对应的的预习项
     *
     * @param pid
     * @param creator
     * @param schoolId
     * @return
     */
    List<CtBankItem> selectMyCollect(@Param("pid") String pid, @Param("creator") String creator, @Param("schoolId") String schoolId);

    /**
     * 查询我的题目列表
     *
     * @param schoolId  学校ID
     * @param creator   用户ID
     * @param unitId    课程ID
     * @param type      题目类型：1预习 4电子作业 7口语作业
     * @param isCollect 是否为收藏
     * @return
     */
    List<BankItemView> selectMyItemList(@Param("schoolId") String schoolId, @Param("creator") String creator, @Param("unitId") String unitId, @Param("type") Integer type, @Param("isCollect") Integer isCollect);

    /**
     * 删除题目
     *
     * @param schoolId
     * @param itemId
     * @return
     */
    int removeItem(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    /**
     * 置顶题目
     *
     * @param schoolId
     * @param itemId
     * @return
     */
    int stickyItem(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    /**
     * 取消置顶题目
     *
     * @param schoolId
     * @param itemId
     * @return
     */
    int cancelStickyItem(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    /**
     * 取消分享我的题目
     *
     * @param schoolId
     * @param itemId
     * @return
     */
    int unShareItem(@Param("schoolId") String schoolId, @Param("itemId") String itemId);

    /**
     * 分享
     *
     * @param schoolId
     * @param itemId
     * @param shareTime
     * @return
     */
    int shareItem(@Param("schoolId") String schoolId, @Param("itemId") String itemId, @Param("shareTime") Date shareTime);

    /**
     * 查询用户在某个时间段内{分享成功}的题目总数
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param itemType 题目类型【为空或0表示所有题目类型】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 次数
     */
    Long selectSharedCount(@Param("schoolId") String schoolId, @Param("userId") String userId, @Param("itemType") Integer itemType, @Param("start") Date start, @Param("end") Date end);
}