package net.xuele.cloudteach.web.wrapper;

/**
 * Created by sunxh on 15/8/22.
 */
public class FLClassWrapper {

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 学界,一个班级的入学年份
     */
    private Integer year;

    /**
     * 班级号,根据当前年级递增生成
     */
    private Integer codeSharing;

    /**
     * 图标
     */
    private String mImage;

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

    public Integer getCodeSharing() {
        return codeSharing;
    }

    public void setCodeSharing(Integer codeSharing) {
        this.codeSharing = codeSharing;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
