package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by panglx on 2015/7/3 0003.
 */
public class EditionsDTO implements Serializable {
	private static final long serialVersionUID = 5481895808377435132L;
	/**
	 * 教材版本ID
	 */
	private String editionId;

	/**
	 * 出版社名称
	 */
	private String pressName;

	/**
	 * 教材版本名称
	 */
	private String editionName;

	/**
	 * 获取 [CT_EDITIONS] 的属性 教材版本ID
	 */
	public String getEditionId() {
		return editionId;
	}

	/**
	 * 设置[CT_EDITIONS]的属性教材版本ID
	 */
	public void setEditionId(String editionId) {
		this.editionId = editionId == null ? null : editionId.trim();
	}

	/**
	 * 获取 [CT_EDITIONS] 的属性 出版社名称
	 */
	public String getPressName() {
		return pressName;
	}

	/**
	 * 设置[CT_EDITIONS]的属性出版社名称
	 */
	public void setPressName(String pressName) {
		this.pressName = pressName == null ? null : pressName.trim();
	}

	/**
	 * 获取 [CT_EDITIONS] 的属性 教材版本名称
	 */
	public String getEditionName() {
		return editionName;
	}

	/**
	 * 设置[CT_EDITIONS]的属性教材版本名称
	 */
	public void setEditionName(String editionName) {
		this.editionName = editionName == null ? null : editionName.trim();
	}

	@Override
	public String toString() {
		return "EditionsDTO{" +
				"editionId='" + editionId + '\'' +
				", pressName='" + pressName + '\'' +
				", editionName='" + editionName + '\'' +
				'}';
	}
}
