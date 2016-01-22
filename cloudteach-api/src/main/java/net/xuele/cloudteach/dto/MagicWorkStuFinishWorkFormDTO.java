package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * MagicWorkStuFinishWorkFormDTO
 * 学生完成提分宝作业dto
 * @author panglx
 * @date on 2015/8/4 0004.
 */
public class MagicWorkStuFinishWorkFormDTO implements Serializable{

	private static final long serialVersionUID = 4191950906360744902L;
	private String unitId;//课程id
	private String workId;//作业id
	private String bankId;//题库id
	private Integer orderNum;//第几套题
	private String rmQueJSON;//答案对错
	private java.util.Date beginTime;//开始时间
	private Date endTime;//完成时间
//	private int score;//分数
//	private String scoreContext ;//分数描述
	private String schoolId;//
	private String userId;//

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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

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

	@Override
	public String toString() {
		return "MagicWorkStuFinishWorkFormDTO{" +
				", unitId='" + unitId + '\'' +
				", workId='" + workId + '\'' +
				", bankId='" + bankId + '\'' +
				", orderNum=" + orderNum +
				", rmQueJSON='" + rmQueJSON + '\'' +
				", beginTime=" + beginTime +
				", endTime=" + endTime +
				", schoolId='" + schoolId + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}
