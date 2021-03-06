package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * PCUnitListWrapper
 *
 * @author sunxh
 * @date 15/8/13
 */
public class PCUnitListWrapper {

    /**
     * 处理状态：1，成功；-1，失败。
     */
    private String state;

    /**
     * 课程列表
     */
    private List<PCUnitWrapper> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PCUnitWrapper> getList() {
        return list;
    }

    public void setList(List<PCUnitWrapper> list) {
        this.list = list;
    }
}
