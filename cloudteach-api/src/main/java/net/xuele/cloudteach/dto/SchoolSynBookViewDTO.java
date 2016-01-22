package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class SchoolSynBookViewDTO implements Serializable {
    private static final long serialVersionUID = -5610291170982142003L;
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
    private Integer subjectId;
    /**
     * 年级
     */
    private Integer gradeNum;

    /**
     * 学期
     */
    private Integer semester;
    /**
     * 教材版本ID
     */
    private Integer editionId;
    /**
     * 教材版本名称
     */
    private String editionName;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(Integer gradeNum) {
        this.gradeNum = gradeNum;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getEditionId() {
        return editionId;
    }

    public void setEditionId(Integer editionId) {
        this.editionId = editionId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    @Override
    public String toString() {
        return "SchoolSynBookViewDTO{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", subjectId=" + subjectId +
                ", gradeNum=" + gradeNum +
                ", semester=" + semester +
                ", editionId=" + editionId +
                ", editionName='" + editionName + '\'' +
                '}';
    }
}