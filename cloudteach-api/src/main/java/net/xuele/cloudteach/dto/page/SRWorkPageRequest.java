package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * SRWorkPageRequest
 * 作业服务分页请求信息
 * @author cm.wang
 * @date 2015/8/12 0023
 */
public class SRWorkPageRequest extends PageRequest implements Serializable{


    private static final long serialVersionUID = 3915495011440736190L;


    //格式： yyyy-MM-dd
    String startTime;

    String endTime;

    //行政区划代码
    String areaId;

    //科目
    String subjectId;

    //年级
    Integer grade;

    String userId;

    String schoolId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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
}
