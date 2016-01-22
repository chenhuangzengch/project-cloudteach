package net.xuele.cloudteach.web.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/18 0018.
 * 讨论作业包装
 */
public class DiscussJobWrapper {

    //状态
    private int state;

    //讨论作业列表
    private List<SingleDiscussJob> dis = new ArrayList<SingleDiscussJob>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<SingleDiscussJob> getDis() {
        return dis;
    }

    public void setDis(List<SingleDiscussJob> dis) {
        this.dis = dis;
    }
}
