package net.xuele.cloudteach.web.wrapper;

import net.xuele.common.dto.ClassInfoDTO;

import java.util.List;

public class TeacherClassByUnitReponseWrapper {
    private String state;
    private List<ClassInfoDTO> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ClassInfoDTO> getList() {
        return list;
    }

    public void setList(List<ClassInfoDTO> list) {
        this.list = list;
    }
}
