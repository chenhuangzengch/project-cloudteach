package net.xuele.cloudteach.web.form;

/**
 * Magic2WorkSubmitForm
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2WorkSubmitForm {
	private String practiceId ;//练习id
	private String unitId;//课程id
	private Integer sort;//第几套题
	private Integer rNum;//回答正确题数
	private String ansQueJSON;//学生回答json
	private Integer totalNum;//总题数
	private String beginTime;//开始时间
	private String endTime;//完成时间

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getrNum() {
		return rNum;
	}

	public void setrNum(Integer rNum) {
		this.rNum = rNum;
	}

	public String getAnsQueJSON() {
		return ansQueJSON;
	}

	public void setAnsQueJSON(String ansQueJSON) {
		this.ansQueJSON = ansQueJSON;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
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
