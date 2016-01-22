package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtMagicBankFinish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtMagicBankFinishMapper {

    int insert(CtMagicBankFinish record);

    CtMagicBankFinish selectByPrimaryKey(@Param("finishId") String finishId, @Param("schoolId") String schoolId);

    int updateByPrimaryKey(CtMagicBankFinish record);

    List<CtMagicBankFinish> selectBankFinishStaByUserId(@Param("userId") String userId,@Param("bankId") String bankId, @Param("schoolId") String schoolId);
}