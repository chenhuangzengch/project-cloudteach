package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * BankItemSharePageRequest
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemSharePageRequest extends PageRequest implements Serializable {

	private static final long serialVersionUID = 4620983105174691508L;
	/**
	 * 对应的课程ID
	 */
	private String unitId;

	/**
	 * 所属用户ID
	 */
	private String userId;

	/**
	 * 筛选类型
	 */
	private int seltype;

	/**
	 * 地区编号
	 */
	private String areaId;

	/**
	 * 学校id
	 */
	private String schoolId;

	/**
	 * 题目类型：1预习 4电子作业 7口语作业
	 */
	private Integer itemType;

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSeltype() {
		return seltype;
	}

	public void setSeltype(int seltype) {
		this.seltype = seltype;
	}

	@Override
	public String toString() {
		return "BankItemSharePageRequest{" +
				"unitId='" + unitId + '\'' +
				", userId='" + userId + '\'' +
				", seltype=" + seltype +
				", areaId='" + areaId + '\'' +
				", schoolId='" + schoolId + '\'' +
				", itemType=" + itemType +
				'}';
	}
}
