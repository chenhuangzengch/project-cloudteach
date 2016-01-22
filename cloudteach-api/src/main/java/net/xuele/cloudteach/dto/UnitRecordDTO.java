package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by panglx on 2015/7/10 0010.
 * 课程目录结构
 */
public class UnitRecordDTO implements Serializable {
	private static final long serialVersionUID = -4797062568871649137L;
	/**
	 * 单元信息
	 */
	UnitsDTO unit;

	/**
	 * 课程信息
	 */
	List<UnitsDTO> lessons;

	public UnitsDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitsDTO unit) {
		this.unit = unit;
	}

	public List<UnitsDTO> getLessons() {
		return lessons;
	}

	public void setLessons(List<UnitsDTO> lessons) {
		this.lessons = lessons;
	}

	@Override
	public String toString() {
		return "UnitRecordDTO{" +
				"unit=" + unit +
				", lessons=" + lessons +
				'}';
	}
}
