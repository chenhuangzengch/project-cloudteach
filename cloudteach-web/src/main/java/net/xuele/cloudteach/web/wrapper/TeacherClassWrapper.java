package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/16 0016.
 * {"state":1,"list":[]}
 */

public class TeacherClassWrapper implements Serializable{

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<GradeClassWrapper> getList() {
        return list;
    }

    public void setList(List<GradeClassWrapper> list) {
        this.list = list;
    }

    private int state;

    private List<GradeClassWrapper> list = new ArrayList<GradeClassWrapper>();
}
