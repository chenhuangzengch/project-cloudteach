package net.xuele.cloudteach.dto;

import java.io.Serializable;
/**
 * SynclassWorkClassDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class SynclassWorkClassDTO implements Serializable {
    private static final long serialVersionUID = -3222623573092823198L;
    private String workClassId;

    private String workId;

    private String classId;

    private Integer status;

    private  String schoolId;

    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId == null ? null : workClassId.trim();
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "SynclassWorkClassDTO{" +
                "workClassId='" + workClassId + '\'' +
                ", workId='" + workId + '\'' +
                ", classId='" + classId + '\'' +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}