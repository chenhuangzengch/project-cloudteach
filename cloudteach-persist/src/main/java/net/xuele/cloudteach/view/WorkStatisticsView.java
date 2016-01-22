package net.xuele.cloudteach.view;

public class WorkStatisticsView {
    private String workId;

    private Integer workSubStudentNum;

    private Integer workCorrectStudentNum;
    /**
     * 作业对应学生数
     */
    private Integer workStudentNum;

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
}