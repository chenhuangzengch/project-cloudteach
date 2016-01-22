package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class WorkStatisticsViewDTO implements Serializable {
    private static final long serialVersionUID = 682917648367402412L;
    private String workId;

    private Integer workSubStudentNum;

    private Integer workCorrectStudentNum;
    /**
     * 作业对应学生数
     */
    private Integer workStudentNum;

    /**
     * 作业学生数参与百分比
     */
    private Float workSubStudentPect;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Integer getWorkSubStudentNum() {
        return workSubStudentNum;
    }

    public void setWorkSubStudentNum(Integer workSubStudentNum) {
        this.workSubStudentNum = workSubStudentNum;
    }

    public Integer getWorkCorrectStudentNum() {
        return workCorrectStudentNum;
    }

    public void setWorkCorrectStudentNum(Integer workCorrectStudentNum) {
        this.workCorrectStudentNum = workCorrectStudentNum;
    }

    public Integer getWorkStudentNum() {
        return workStudentNum;
    }

    public void setWorkStudentNum(Integer workStudentNum) {
        this.workStudentNum = workStudentNum;
    }

    public Float getWorkSubStudentPect() {
        return workSubStudentPect;
    }

    public void setWorkSubStudentPect(Float workSubStudentPect) {
        this.workSubStudentPect = workSubStudentPect;
    }

    @Override
    public String toString() {
        return "WorkStatisticsViewDTO{" +
                "workId='" + workId + '\'' +
                ", workSubStudentNum=" + workSubStudentNum +
                ", workCorrectStudentNum=" + workCorrectStudentNum +
                ", workStudentNum=" + workStudentNum +
                ", workSubStudentPect=" + workSubStudentPect +
                '}';
    }
}