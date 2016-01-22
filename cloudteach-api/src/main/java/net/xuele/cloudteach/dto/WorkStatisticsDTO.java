package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class WorkStatisticsDTO implements Serializable {
    private static final long serialVersionUID = -1345349810628211939L;
    private String workId;
    /**
     * 版本号，用于乐观锁
     */
    private Integer version;

    private Integer workSubStudentNum;

    private Integer workCorrectStudentNum;

    private Date lastWarnTime;

    private String schoolId;

    private Integer workViewStudentNum;

    public Integer getWorkViewStudentNum() {
        return workViewStudentNum;
    }

    public void setWorkViewStudentNum(Integer workViewStudentNum) {
        this.workViewStudentNum = workViewStudentNum;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Date getLastWarnTime() {
        return lastWarnTime;
    }

    public void setLastWarnTime(Date lastWarnTime) {
        this.lastWarnTime = lastWarnTime;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "WorkStatisticsDTO{" +
                "lastWarnTime=" + lastWarnTime +
                ", workId='" + workId + '\'' +
                ", version=" + version +
                ", workSubStudentNum=" + workSubStudentNum +
                ", workCorrectStudentNum=" + workCorrectStudentNum +
                ", schoolId='" + schoolId + '\'' +
                ", workViewStudentNum=" + workViewStudentNum +
                '}';
    }
}