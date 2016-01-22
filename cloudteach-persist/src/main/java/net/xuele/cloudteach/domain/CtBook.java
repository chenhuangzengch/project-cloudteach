package net.xuele.cloudteach.domain;

public class CtBook {
    /**
     * 课本ID
     */
    private String bookId;

    /**
     * 课本名称
     */
    private String bookName;

    /**
     * 科目
     */
    private String subjectId;

    /**
     * 科目名称（冗余字段）
     */
    private String subjectName;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 学期
     */
    private Integer semester;

    /**
     * 学期描述
     */
    private String semesterDescribe;

    /**
     * 教材版本ID（引用ct_editions)
     */
    private String editionId;

    /**
     * 出版社版本
     */
    private String bookVersion;

    /**
     * 课标
     */
    private Integer standard;

    /**
     * 版次
     */
    private Integer editionVersion;

    /**
     * 获取 [CT_BOOK] 的属性 课本ID
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * 设置[CT_BOOK]的属性课本ID
     */
    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    /**
     * 获取 [CT_BOOK] 的属性 课本名称
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * 设置[CT_BOOK]的属性课本名称
     */
    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    /**
     * 获取 [CT_BOOK] 的属性 科目
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * 设置[CT_BOOK]的属性科目
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId == null ? null : subjectId.trim();
    }

    /**
     * 获取 [CT_BOOK] 的属性 科目名称（冗余字段）
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置[CT_BOOK]的属性科目名称（冗余字段）
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 获取 [CT_BOOK] 的属性 年级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * 设置[CT_BOOK]的属性年级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取 [CT_BOOK] 的属性 学期
     */
    public Integer getSemester() {
        return semester;
    }

    /**
     * 设置[CT_BOOK]的属性学期
     */
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    /**
     * 获取 [CT_BOOK] 的属性 学期描述
     */
    public String getSemesterDescribe() {
        return semesterDescribe;
    }

    /**
     * 设置[CT_BOOK]的属性学期描述
     */
    public void setSemesterDescribe(String semesterDescribe) {
        this.semesterDescribe = semesterDescribe == null ? null : semesterDescribe.trim();
    }

    /**
     * 获取 [CT_BOOK] 的属性 教材版本ID（引用ct_editions)
     */
    public String getEditionId() {
        return editionId;
    }

    /**
     * 设置[CT_BOOK]的属性教材版本ID（引用ct_editions)
     */
    public void setEditionId(String editionId) {
        this.editionId = editionId == null ? null : editionId.trim();
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Integer getEditionVersion() {
        return editionVersion;
    }

    public void setEditionVersion(Integer editionVersion) {
        this.editionVersion = editionVersion;
    }
}