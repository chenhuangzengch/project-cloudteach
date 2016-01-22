package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkClassViewDTO
 * 作业发布对应班级
 * @author duzg
 * @date 2015/7/18 0002
 */
public class WorkClassViewDTO implements Serializable {
    private static final long serialVersionUID = -831061302861422273L;
    /**
     * 作业学生ID
     */
    private String workClassId;
    /**
     * 作业ID
     */
    private String workId;
    /**
     * 班级号
     */
    private String classId;
    /**
     * 上次提醒交作业时间
     */
    private Date lastWarnTime;
    /**
     * 班级名称
     */
    private String className;

    private Integer status;
    /**
     * 学界,一个班级的入学年份
     */
    private Integer years;

    /**
     * 班级号,根据当前年级递增生成
     */
    private Integer codeSharing;

    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getCodeSharing() {
        return codeSharing;
    }

    public void setCodeSharing(Integer codeSharing) {
        this.codeSharing = codeSharing;
    }

    public Date getLastWarnTime() {
        return lastWarnTime;
    }

    public void setLastWarnTime(Date lastWarnTime) {
        this.lastWarnTime = lastWarnTime;
    }

    @Override
    public String toString() {
        return "WorkClassViewDTO{" +
                "workClassId='" + workClassId + '\'' +
                ", workId='" + workId + '\'' +
                ", classId='" + classId + '\'' +
                ", lastWarnTime=" + lastWarnTime +
                ", className='" + className + '\'' +
                ", status=" + status +
                ", years=" + years +
                ", codeSharing=" + codeSharing +
                '}';
    }
}