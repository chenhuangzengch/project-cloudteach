package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.BankItemSharePraiseViewDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * BankItemSharePraiseWrapper
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemSharePraiseWrapper {
	/**
	 * 点赞用户ID
	 */
	private String userId;

	/**
	 * 点赞时间
	 */
	private Date praiseTime;

	/**
	 * 点赞用户名称
	 */
	private String userName;

	public BankItemSharePraiseWrapper(BankItemSharePraiseViewDTO praiseViewDTO) {
		BeanUtils.copyProperties(praiseViewDTO, this);
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
