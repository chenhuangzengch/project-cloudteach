package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class SubjectGatherViewDTO implements Serializable {

    private static final long serialVersionUID = -3646008037777459092L;

    private String subjectId;

    private String subjectName;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "SubjectGatherViewDTO{" +
                "subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}