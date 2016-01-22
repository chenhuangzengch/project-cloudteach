package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by panglx on 2015/7/9 0009.
 */
public class UnitRecord {
	String unitId;

	String unitName;

	private List<UnitRecord> record;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public List<UnitRecord> getRecord() {
		return record;
	}

	public void setRecord(List<UnitRecord> record) {
		this.record = record;
	}
}
