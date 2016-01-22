package net.xuele.cloudteach.web.wrapper;

/**
 * PCStudentWrapper
 *
 * @author sunxh
 * @date 15/8/18
 */
public class PCStudentWrapper {

    /**
     * 学生ID
     */
    private String studentID;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 头像key
     */
    private String iconURI;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
    }
}
