package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtDiskSharePraise;
import org.apache.ibatis.annotations.Param;

public interface CtDiskSharePraiseMapper {
    int deleteByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    int insert(CtDiskSharePraise record);

    CtDiskSharePraise selectByPrimaryKey(@Param("shareId") String shareId, @Param("userId") String userId);

    List<CtDiskSharePraise> selectAll();

    int updateByPrimaryKey(CtDiskSharePraise record);

    int count(@Param("shareId") String shareId, @Param("userId") String userId);
}