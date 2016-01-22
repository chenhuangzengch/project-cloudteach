package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/11/3 0003.
 * 蓝色感恩月活动用DTO
 */
public class BlueTeacherCorrectWorkDTO implements Serializable {
    private static final long serialVersionUID = 2382997758596630306L;

    private String teacherId;

    private Date correctTime;

    private String workId;

    private String answerId;

    private String studentId;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public Date getCorrectTime() {
        return correctTime;
    }

    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    @Override
    public String toString() {
        return "BlueTeacherCorrectWorkDTO{" +
                "answerId='" + answerId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", correctTime=" + correctTime +
                ", workId='" + workId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
