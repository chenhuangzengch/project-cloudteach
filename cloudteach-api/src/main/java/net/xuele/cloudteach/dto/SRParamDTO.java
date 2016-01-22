package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by cm.wang on 2015/8/11 0011.
 */
public class SRParamDTO implements Serializable {

    private static final long serialVersionUID = -4147107812952084879L;

    //格式： yyyy-MM-dd
    String startTime;

    String endTime;

    //行政区划代码
    String areaId;

    //科目
    String subjectId;

    //年级,0表示全部
    Integer grade;

    String schoolId;

    String userId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
