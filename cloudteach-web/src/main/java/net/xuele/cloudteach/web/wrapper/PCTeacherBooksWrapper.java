package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * PCTeacherBooksWrapper
 *
 * @author sunxh
 * @date 15/8/13
 */
public class PCTeacherBooksWrapper {

    /**
     * 处理状态：1，成功；-1，失败。
     */
    private String state;

    private List<PCBookWrapper> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PCBookWrapper> getList() {
        return list;
    }

    public void setList(List<PCBookWrapper> list) {
        this.list = list;
    }

}
