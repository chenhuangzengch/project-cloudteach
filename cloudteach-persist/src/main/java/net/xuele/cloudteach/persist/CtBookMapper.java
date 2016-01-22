package net.xuele.cloudteach.persist;

import java.util.List;
import net.xuele.cloudteach.domain.CtBook;
import net.xuele.cloudteach.view.EditionAndSubjectView;

public interface CtBookMapper {
    int deleteByPrimaryKey(String bookId);

    int insert(CtBook record);

    CtBook selectByPrimaryKey(String bookId);

    List<CtBook> selectAll();

    int updateByPrimaryKey(CtBook record);

    EditionAndSubjectView selectByBookId(String bookId);
}