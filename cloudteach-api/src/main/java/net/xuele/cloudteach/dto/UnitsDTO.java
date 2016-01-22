package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by panglx on 2015/7/3 0003.
 */
public class UnitsDTO implements Serializable {
	private static final long serialVersionUID = 2478307446302475971L;
	/**
	 * 课程ID
	 */
	private String unitId;

	private String unitName;

	/**
	 * 课本id（关联ct_book)
	 */
	private String bookId;

	/**
	 * 课程类型
	 */
	private Integer unitType;

	private String sort;

	/**
	 * 获取 [CT_UNITS] 的属性 课程ID
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * 设置[CT_UNITS]的属性课程ID
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName == null ? null : unitName.trim();
	}

	/**
	 * 获取 [CT_UNITS] 的属性 课本id（关联ct_book)
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * 设置[CT_UNITS]的属性课本id（关联ct_book)
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId == null ? null : bookId.trim();
	}

	/**
	 * 获取 [CT_UNITS] 的属性 课程类型
	 */
	public Integer getUnitType() {
		return unitType;
	}

	/**
	 * 设置[CT_UNITS]的属性课程类型
	 */
	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort == null ? null : sort.trim();
	}

	@Override
	public String toString() {
		return "UnitsDTO{" +
				"unitId='" + unitId + '\'' +
				", unitName='" + unitName + '\'' +
				", bookId='" + bookId + '\'' +
				", unitType=" + unitType +
				", sort='" + sort + '\'' +
				'}';
	}
}
