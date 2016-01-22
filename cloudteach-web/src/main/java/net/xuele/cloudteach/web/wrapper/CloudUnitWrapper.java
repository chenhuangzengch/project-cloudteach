package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * 课程目录展示结构
 * Created by panglx on 2015/7/9 0009.
 */
public class CloudUnitWrapper {
	int total;

	List<UnitRecord> unitRecordList;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<UnitRecord> getUnitRecordList() {
		return unitRecordList;
	}

	public void setUnitRecordList(List<UnitRecord> unitRecordList) {
		this.unitRecordList = unitRecordList;
	}

}


