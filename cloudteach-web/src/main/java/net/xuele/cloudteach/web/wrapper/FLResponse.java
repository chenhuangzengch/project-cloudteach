package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Flash 返回数据包装类
 * Created by sunxh on 15/8/22.
 */
public class FLResponse<T> {

    private String state;

    private List<T> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
