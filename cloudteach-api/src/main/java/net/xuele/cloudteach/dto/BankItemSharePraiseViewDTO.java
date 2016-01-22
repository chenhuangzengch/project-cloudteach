package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * BankItemSharePraiseViewDTO
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemSharePraiseViewDTO implements Serializable{

	private static final long serialVersionUID = 4423637360600376927L;

	/**
	 * 分享预习项ID
	 */
	private String shareId;

	/**
	 * 点赞用户ID
	 */
	private String userId;

	/**
	 * 点赞时间
	 */
	private Date praiseTime;

	/**
	 * 用户点赞状态
	 */
	private Integer praiseStatus;

	/************************关联字段************************************/
	/**
	 * 点赞用户名称
	 */
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getPraiseTime() {
		return praiseTime;
	}

	public void setPraiseTime(Date praiseTime) {
		this.praiseTime = praiseTime;
	}

	public Integer getPraiseStatus() {
		return praiseStatus;
	}

	public void setPraiseStatus(Integer praiseStatus) {
		this.praiseStatus = praiseStatus;
	}

	@Override
	public String toString() {
		return "BankItemSharePraiseViewDTO{" +
				"shareId='" + shareId + '\'' +
				", userId='" + userId + '\'' +
				", praiseTime=" + praiseTime +
				", praiseStatus=" + praiseStatus +
				", userName='" + userName + '\'' +
				'}';
	}
}
