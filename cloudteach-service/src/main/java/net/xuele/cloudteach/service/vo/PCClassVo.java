package net.xuele.cloudteach.service.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * PCClassWrapper
 * 返回授课pc端的班级信息 包括学生
 *
 * @author sunxh
 * @date 15/8/18
 */
@XmlRootElement(name = "classInfo")
public class PCClassVo {

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
    private List<PCStudentVo> students;

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

    @XmlElementWrapper(name = "studentlist")
    @XmlElement(name = "student")
    public List<PCStudentVo> getStudents() {
        return students;
    }

    public void setStudents(List<PCStudentVo> students) {
        this.students = students;
    }
    // TODO 重写toString()
}
