package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBankItemShareCollect;
import org.apache.ibatis.annotations.Param;

public interface CtBankItemShareCollectMapper {

    int insert(CtBankItemShareCollect record);

    CtBankItemShareCollect selectByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    int updateByPrimaryKey(CtBankItemShareCollect record);
}