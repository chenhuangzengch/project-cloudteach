package net.xuele.cloudteach.web.wrapper;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class TeacherResponse {
    private String className;
    private String classId;
    private Integer year;
    private String codeSharing;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCodeSharing() {
        return codeSharing;
    }

    public void setCodeSharing(String codeSharing) {
        this.codeSharing = codeSharing;
    }
}
