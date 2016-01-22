package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * ClassFeedbackPageRequest
 *
 * @author panglx
 * @date on 2015/10/15 0015.
 */
public class ClassFeedbackPageRequest extends PageRequest implements Serializable {
	private static final long serialVersionUID = 1985181626281545786L;
	/**
	 * 分享用户编号
	 */
	private String userId;

	/**
	 * 课程编号
	 */
	private String unitId;

	/**
	 * 学校id
	 */
	private String schoolId;

	/**
	 * 班级id
	 */
	private String classId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}
