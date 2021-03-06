package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.GuidanceItemSharePraiseDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 大家分享的预习项用户点赞信息
 * Created by panglx on 2015/7/16 0016.
 */
public class GuidanceItemSharePraise {
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

	public GuidanceItemSharePraise(GuidanceItemSharePraiseDTO sharePraiseDTO) {
		BeanUtils.copyProperties(sharePraiseDTO, this);
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
