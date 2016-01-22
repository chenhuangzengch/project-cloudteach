package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * PCClassWrapper
 * 返回授课pc端的班级信息 包括学生
 *
 * @author sunxh
 * @date 15/8/18
 */
public class PCClassWrapper {

    /**
     * 班级ID
     */
    private String classID;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学生列表
     */
    private List<PCStudentWrapper> students;

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PCStudentWrapper> getStudents() {
        return students;
    }

    public void setStudents(List<PCStudentWrapper> students) {
        this.students = students;
    }
}
