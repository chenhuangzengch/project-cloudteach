package net.xuele.cloudteach.dto;


import java.io.Serializable;

public class WorkUnSubStudentViewDTO implements Serializable {

    private static final long serialVersionUID = -8742302247888342276L;
    /**
     * 预习作业ID
     */
    private String workId;

    /**
     * 学生用户ID
     */
    private String userId;
    /**
     * 学生用户名
     */
    private String userName;
    /**
     * 班级ID
     */
    private String classId;

    /**
     * 用户头像
     */
    private String icon;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "WorkUnSubStudentViewDTO{" +
                "workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", classId='" + classId + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}