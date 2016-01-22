package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/22 0022.
 */
public class StudentWorkCommunicationPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -7537231388389878128L;

    private String schoolId;
    private String studentId;
    private String workId;
    private String itemId;
    private int workType;
    // 学生提交作业时间，分页用
    private String subTime;

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    @Override
    public String toString() {
        return "StudentWorkCommunicationPageRequest{" +
                "schoolId='" + schoolId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", workId='" + workId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", workType=" + workType +
                '}';
    }
}
