package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by panglx on 2015/7/10 0010.
 */
public class EditionAndSubjectDTO implements Serializable {
	/**
	 * 课本ID
	 */
	private String bookId;
	/**
	 * 教材版本ID
	 */
	private String editionId;

	/**
	 * 教材版本名称
	 */
	private String editionName;

	/**
	 * 年级
	 */
	private Integer grade;

	/**
	 * 科目
	 */
	private String subjectId;

	/**
	 * 科目名称（冗余字段）
	 */
	private String subjectName;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getEditionId() {
		return editionId;
	}

	public void setEditionId(String editionId) {
		this.editionId = editionId;
	}

	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public String toString() {
		return "EditionAndSubjectDTO{" +
				"bookId='" + bookId + '\'' +
				", editionId='" + editionId + '\'' +
				", editionName='" + editionName + '\'' +
				", grade=" + grade +
				", subjectId='" + subjectId + '\'' +
				", subjectName='" + subjectName + '\'' +
				'}';
	}
}
