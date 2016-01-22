package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtBook;

/**
 * Created by panglx on 2015/7/13 0013.
 */
public class EditionAndSubjectView extends CtBook{
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

	public String getEditionId() {
		return editionId;
	}

	public void setEditionId(String editionId) {
		this.editionId = editionId;
	}

	public String getPressName() {
		return pressName;
	}

	public void setPressName(String pressName) {
		this.pressName = pressName;
	}

	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}
}
