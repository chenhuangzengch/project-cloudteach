package net.xuele.cloudteach.view;
/**
 * WorkClassView
 * 作业发布对应班级
 * @author duzg
 * @date 2015/7/18 0002
 */
public class WorkClassView {
    /**
     * 作业学生ID
     */
    private String workClassId;
    /**
     * 作业ID
     */
    private String workId;
    /**
     * 班级号
     */
    private String classId;
    /**
     * 班级名称
     */
    private String className;

    private Integer status;
    /**
     * 学界,一个班级的入学年份
     */
    private Integer years;

    /**
     * 班级号,根据当前年级递增生成
     */
    private Integer codeSharing;

    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getCodeSharing() {
        return codeSharing;
    }

    public void setCodeSharing(Integer codeSharing) {
        this.codeSharing = codeSharing;
    }
}