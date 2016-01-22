package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by panglx on 2015/7/1 0001.
 */
public class DiskSharePraiseDTO implements Serializable {

	private static final long serialVersionUID = 7284905508321621066L;
	/**
	 * 文件编号
	 */
	private String shareId;

	/**
	 * 点赞用户
	 */
	private String userId;

	/**
	 * 点赞状态
	 */
	private Integer status;

	/**
	 * 获取 [CT_DISK_SHARE_PRAISE] 的属性 文件编号
	 */
	public String getShareId() {
		return shareId;
	}

	/**
	 * 设置[CT_DISK_SHARE_PRAISE]的属性文件编号
	 */
	public void setShareId(String shareId) {
		this.shareId = shareId == null ? null : shareId.trim();
	}

	/**
	 * 获取 [CT_DISK_SHARE_PRAISE] 的属性 点赞用户
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置[CT_DISK_SHARE_PRAISE]的属性点赞用户
	 */
	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	/**
	 * 获取 [CT_DISK_SHARE_PRAISE] 的属性 点赞状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置[CT_DISK_SHARE_PRAISE]的属性点赞状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DiskSharePraiseDTO{" +
				"shareId='" + shareId + '\'' +
				", userId='" + userId + '\'' +
				", status=" + status +
				'}';
	}
}
