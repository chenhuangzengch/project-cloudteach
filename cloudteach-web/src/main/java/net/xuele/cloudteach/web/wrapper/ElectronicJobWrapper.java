package net.xuele.cloudteach.web.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/18 0018.
 * 电子作业包装
 */
public class ElectronicJobWrapper {

    //状态
    private int state;

    //电子作业列表
    private List<SingleElectronicJob> qlist = new ArrayList<SingleElectronicJob>();


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<SingleElectronicJob> getQlist() {
        return qlist;
    }

    public void setQlist(List<SingleElectronicJob> qlist) {
        this.qlist = qlist;
    }
}
