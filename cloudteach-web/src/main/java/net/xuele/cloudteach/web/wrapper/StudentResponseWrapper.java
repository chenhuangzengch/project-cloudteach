package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class StudentResponseWrapper {
    private String state;
    private List<StudentResponse> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<StudentResponse> getList() {
        return list;
    }

    public void setList(List<StudentResponse> list) {
        this.list = list;
    }
}
