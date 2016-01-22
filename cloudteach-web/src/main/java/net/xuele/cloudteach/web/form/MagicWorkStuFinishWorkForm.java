package net.xuele.cloudteach.web.form;

/**
 * MagicWorkStuFinishWorkForm
 * 学生完成提分宝作业form
 * @author panglx
 * @date on 2015/8/4 0004.
 */
public class MagicWorkStuFinishWorkForm {
	private String unitId;
	private String workId;
	private String bankId;
	private Integer orderNum;
	private String rmQueJSON;
	private String beginTime;
	private String endTime;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getRmQueJSON() {
		return rmQueJSON;
	}

	public void setRmQueJSON(String rmQueJSON) {
		this.rmQueJSON = rmQueJSON;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
