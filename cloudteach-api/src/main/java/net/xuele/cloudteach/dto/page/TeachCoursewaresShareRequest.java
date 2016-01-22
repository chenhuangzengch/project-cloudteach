package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * Created by panglx on 2015/7/17 0017.
 */
public class TeachCoursewaresShareRequest extends PageRequest implements Serializable {

	private static final long serialVersionUID = 2633499708039256258L;
	/**
	 * 用户ID:（分享人）userid
	 */
	private String creator;

	/**
	 * 课程ID
	 */
	private String unitId;

	/**
	 * 学校ID
	 */
	private String schoolId;

	/**
	 * 区域ID
	 */
	private String areaId;

	/**
	 * 筛选类型
	 */
	private int seltype;

	public int getSeltype() {
		return seltype;
	}

	public void setSeltype(int seltype) {
		this.seltype = seltype;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "TeachCoursewaresShareRequest{" +
				"creator='" + creator + '\'' +
				", unitId='" + unitId + '\'' +
				", schoolId='" + schoolId + '\'' +
				", areaId='" + areaId + '\'' +
				", seltype=" + seltype +
				'}';
	}
}
