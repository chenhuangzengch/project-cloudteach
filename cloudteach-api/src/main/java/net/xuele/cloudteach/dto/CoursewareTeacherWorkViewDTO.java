package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duzg on 2015/8/22 0022.
 */
public class CoursewareTeacherWorkViewDTO extends WorkGatherDTO implements Serializable {

    private static final long serialVersionUID = 4485777102511676396L;

    private List<TeacherWorkItemViewDTO> workItemList;


    public List<TeacherWorkItemViewDTO> getWorkItemList() {
        return workItemList;
    }

    public void setWorkItemList(List<TeacherWorkItemViewDTO> workItemList) {
        this.workItemList = workItemList;
    }
}
