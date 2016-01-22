package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * TeachchoosePageRequest
 *
 * @author hushengguo
 * @date 2015/11/5
 */
public class TeachchoosePageRequest extends PageRequest implements Serializable{

    private static final long serialVersionUID = -6971857604484804509L;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 科目ID
     */
    private String subjectId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

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

    @Override
    public String toString() {
        return "TeachchoosePageRequest{" +
                "schoolId='" + schoolId + '\'' +
                ", grade=" + grade +
                ", subjectId='" + subjectId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

}
