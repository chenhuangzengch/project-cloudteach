package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassFeedbackDTO
 *
 * @author panglx
 * @date on 2015/10/14 0014.
 */
public class ClassFeedbackDTO implements Serializable{

	private static final long serialVersionUID = 8297689517387625669L;
	private String fbId;

	private String studentId;

	private String studentName;

	private String coursewaresId;

	private String unitId;

	private String classId;

	private String className;

	private String filekey;

	private String fileName;

	private String extension;

	private Integer fileType;

	private Integer size;

	private Date uploadTime;

	private String uploadUserId;

	private String schoolId;

	private Integer status;

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId == null ? null : fbId.trim();
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId == null ? null : studentId.trim();
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName == null ? null : studentName.trim();
	}

	public String getCoursewaresId() {
		return coursewaresId;
	}

	public void setCoursewaresId(String coursewaresId) {
		this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId == null ? null : classId.trim();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFilekey() {
		return filekey;
	}

	public void setFilekey(String filekey) {
		this.filekey = filekey == null ? null : filekey.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension == null ? null : extension.trim();
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId == null ? null : uploadUserId.trim();
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId == null ? null : schoolId.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
