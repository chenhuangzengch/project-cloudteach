package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * SynclassWorkDetailViewDTO
 * 作业管理-同步课堂作业
 * @author duzg
 * @date 2015/7/12 0002
 */
public class SynclassWorkDetailViewDTO extends SynclassWorkDTO   implements Serializable {

    private static final long serialVersionUID = 2326155327277192359L;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
        return "SynclassWorkDetailViewDTO{" +
                "userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", positionName='" + positionName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}