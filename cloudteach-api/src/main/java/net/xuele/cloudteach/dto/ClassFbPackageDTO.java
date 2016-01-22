package net.xuele.cloudteach.dto;

import net.xuele.common.dto.ClassInfoDTO;
import net.xuele.member.dto.StudentManagerDTO;

import java.io.Serializable;
import java.util.List;

/**
 * ClassFeedbackInfoDTO
 *
 * @author panglx
 * @date on 2015/10/14 0014.
 */
public class ClassFbPackageDTO implements Serializable {


	private static final long serialVersionUID = 1225077531046659297L;
	/**
	 * 课本名称
	 */
	private String bookName;

	/**
	 * 课程id
	 */
	private String unitId;

	/**
	 * 课程名称
	 */
	private String unitName;

	/**
	 * 科目名称
	 */
	private String subjectName;

	/**
	 * 授课班级信息
	 */
	List<ClassInfoDTO> classInfoDTOs;

	List<StudentManagerDTO> studetnDTOs;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public List<ClassInfoDTO> getClassInfoDTOs() {
		return classInfoDTOs;
	}

	public void setClassInfoDTOs(List<ClassInfoDTO> classInfoDTOs) {
		this.classInfoDTOs = classInfoDTOs;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<StudentManagerDTO> getStudetnDTOs() {
		return studetnDTOs;
	}

	public void setStudetnDTOs(List<StudentManagerDTO> studetnDTOs) {
		this.studetnDTOs = studetnDTOs;
	}

	@Override
	public String toString() {
		return "ClassFbPackageDTO{" +
				"bookName='" + bookName + '\'' +
				", unitId='" + unitId + '\'' +
				", unitName='" + unitName + '\'' +
				", subjectName='" + subjectName + '\'' +
				", classInfoDTOs=" + classInfoDTOs +
				", studetnDTOs=" + studetnDTOs +
				'}';
	}
}
