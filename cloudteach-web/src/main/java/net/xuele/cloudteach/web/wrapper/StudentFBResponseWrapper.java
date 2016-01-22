package net.xuele.cloudteach.web.wrapper;

import java.util.List;

public class StudentFBResponseWrapper {
    private String state;
    private List<StudentWrapper> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<StudentWrapper> getList() {
        return list;
    }

    public void setList(List<StudentWrapper> list) {
        this.list = list;
    }
}
