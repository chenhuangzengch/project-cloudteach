package net.xuele.cloudteach.service.vo;

import javax.xml.bind.annotation.XmlElement;

/**
 * PCStudentWrapper
 *
 * @author sunxh
 * @date 15/8/18
 */
public class PCStudentVo {

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

    @XmlElement(name = "iconFileKey")
    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
    }
    // TODO 重写toString()
}
