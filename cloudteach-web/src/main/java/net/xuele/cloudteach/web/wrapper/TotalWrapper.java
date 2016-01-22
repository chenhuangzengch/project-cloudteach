package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by sunxh on 2015/7/10 0010.
 */
public class TotalWrapper<T> {
    private long total;
    List<T> rows;

    public TotalWrapper(List<T> rows) {
        this.rows = rows;
        this.total = rows.size();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
