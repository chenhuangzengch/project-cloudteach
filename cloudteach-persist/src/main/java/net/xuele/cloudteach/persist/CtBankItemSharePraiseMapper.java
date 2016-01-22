package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBankItemSharePraise;
import org.apache.ibatis.annotations.Param;

public interface CtBankItemSharePraiseMapper {

    int insert(CtBankItemSharePraise record);

    CtBankItemSharePraise selectByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    int updateByPrimaryKey(CtBankItemSharePraise record);

    /**
     * 查询点赞用户
     * @param shareId
     * @return
     */
    List<CtBankItemSharePraise> selectPraiseUser(@Param("shareId") String shareId);
}