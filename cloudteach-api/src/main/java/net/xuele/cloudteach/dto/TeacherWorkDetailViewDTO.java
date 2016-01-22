package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

public class TeacherWorkDetailViewDTO extends TeacherWorkDTO implements Serializable {


    private static final long serialVersionUID = -3464792141284875864L;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 用户职务
     */
    private String positionName;

    /**
     * 课程名
     */
    private String unitName;
    /**
     * 科目号
     */
    private String subjectId;
    /**
     * 科目名
     */
    private String subjectName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "TeacherWorkDetailViewDTO{" +
                "userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", positionName='" + positionName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
