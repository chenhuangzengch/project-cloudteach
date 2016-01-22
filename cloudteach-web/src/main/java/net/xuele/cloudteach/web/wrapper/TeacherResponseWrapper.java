package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class TeacherResponseWrapper {
    private String state;
    private List<TeacherResponse> list ;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TeacherResponse> getList() {
        return list;
    }

    public void setList(List<TeacherResponse> list) {
        this.list = list;
    }
}
