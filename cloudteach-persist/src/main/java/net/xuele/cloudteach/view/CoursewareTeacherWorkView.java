package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtWorkGather;

import java.util.List;


public class CoursewareTeacherWorkView extends CtWorkGather {
    private List<TeacherWorkItemView> workItemList;

    public List<TeacherWorkItemView> getWorkItemList() {
        return workItemList;
    }

    public void setWorkItemList(List<TeacherWorkItemView> workItemList) {
        this.workItemList = workItemList;
    }
}