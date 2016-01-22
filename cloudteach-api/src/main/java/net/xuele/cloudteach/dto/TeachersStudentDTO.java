package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * TeachersStudentDTO
 *
 * @author duzg
 * @date 2015/8/17 0009
 */
public class TeachersStudentDTO implements Serializable {

    private static final long serialVersionUID = -8828513116031185570L;

    /**
     * 班级ID
     */
    private String classId;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 学生ID
     */
    private String studentId;
    /**
     * 学生名称
     */
    private String studentName;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
