package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.TeachersStudentDTO;

import java.util.List;

/**
 * PCGradeStudentWrapper
 *
 * @author duzg
 * @date 2015/8/17 0009
 */
public class PCGradeStudentWrapper {

    private String state;

    private List<PCClassWrapper> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PCClassWrapper> getList() {
        return list;
    }

    public void setList(List<PCClassWrapper> list) {
        this.list = list;
    }
}
