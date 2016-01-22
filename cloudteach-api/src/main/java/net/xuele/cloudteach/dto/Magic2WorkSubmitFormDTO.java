package net.xuele.cloudteach.dto;

import net.xuele.cloudteach.constant.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * Magic2WorkSubmitFormDTO
 *
 * @author panglx
 * @date on 2015/10/22 0022.
 */
public class Magic2WorkSubmitFormDTO implements Serializable{
	private static final long serialVersionUID = 4765730892350951990L;
	String practiceId ;//练习id
	private String unitId;//课程id
	private Integer sort;//舍弃字段
	//Integer rNum;//回答正确题数
	private String ansQueJSON;//学生回答json
	Integer totalNum;//总题数
	private java.util.Date beginTime;//开始时间
	private Date endTime;//完成时间
	private String schoolId;//学校id
	private String userId;//用户id
	private String identityId = Constants.IDENTIFY_STUDENT;//用户身份标识:默认学生
	private int port = 2;//1-pc端，2-移动端

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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

	public String getAnsQueJSON() {
		return ansQueJSON;
	}

	public void setAnsQueJSON(String ansQueJSON) {
		this.ansQueJSON = ansQueJSON;
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

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	@Override
    public String toString() {
        return "Magic2WorkSubmitFormDTO{" +
                "ansQueJSON='" + ansQueJSON + '\'' +
                ", unitId='" + unitId + '\'' +
                ", sort=" + sort +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", schoolId='" + schoolId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
