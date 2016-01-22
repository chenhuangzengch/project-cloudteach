package net.xuele.cloudteach.view;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/11/3 0003.
 * 蓝色感恩月活动用DTO
 */
public class BlueStudentSubWorkView implements Serializable {

    private String studentId;

    private String teacherId;

    private Date subTime;

    private String workId;

    private String answerId;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

}
