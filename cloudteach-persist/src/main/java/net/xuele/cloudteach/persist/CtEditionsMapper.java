package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtEditions;

public interface CtEditionsMapper {
    int deleteByPrimaryKey(String editionId);

    int insert(CtEditions record);

    CtEditions selectByPrimaryKey(String editionId);

    List<CtEditions> selectAll();

    int updateByPrimaryKey(CtEditions record);
}