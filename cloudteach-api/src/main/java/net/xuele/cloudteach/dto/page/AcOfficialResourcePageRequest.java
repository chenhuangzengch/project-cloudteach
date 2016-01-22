package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * AcOfficialResourcePageRequest
 *
 * @author panglx
 * @date on 2015/8/17 0017.
 */
public class AcOfficialResourcePageRequest extends PageRequest implements Serializable {
	/**
	 * 学校id
	 */
	private String schoolId;
	/**
	 * 当前登录用户编号
	 */
	private String userId;

	/**
	 * 课程编号
	 */
	private String unitId;

	/**
	 * 文件类型
	 * 1	其他
	 * 2	教案
	 * 3	学案
	 * 4	课件
	 * 5	习题
	 * 6	课程素材
	 */
	private Integer fileType;

	/**
	 * 地区编号
	 */
	private String areaId;

	/**
	 * 扩展名类型
	 */
	private Integer extType;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

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

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getExtType() {
		return extType;
	}

	public void setExtType(Integer extType) {
		this.extType = extType;
	}
}
