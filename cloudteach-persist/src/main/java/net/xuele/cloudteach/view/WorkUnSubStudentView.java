package net.xuele.cloudteach.view;

public class WorkUnSubStudentView {

    /**
     * 预习作业学生ID
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}