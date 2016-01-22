package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * CtMagicQuestionVideoDTO
 *
 * @author panglx
 * @date on 2015/8/19 0019.
 */
public class MagicQuestionVideoDTO implements Serializable{

	private static final long serialVersionUID = 2464707934438959693L;
	/**
	 * ID
	 */
	private String videoId;

	/**
	 * 题目ID
	 */
	private String queId;

	/**
	 * 视频文件key
	 */
	private String videoFileKey;

	/**
	 * 文件后缀
	 */
	private String videoSuffix;

	/**
	 * 视频描述
	 */
	private String videoDesc;

	/**
	 * 状态(0、无效，1、有效)
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 修改人
	 */
	private String updateUser;

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 ID
	 */
	public String getVideoId() {
		return videoId;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性ID
	 */
	public void setVideoId(String videoId) {
		this.videoId = videoId == null ? null : videoId.trim();
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 题目ID
	 */
	public String getQueId() {
		return queId;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性题目ID
	 */
	public void setQueId(String queId) {
		this.queId = queId == null ? null : queId.trim();
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 视频文件key
	 */
	public String getVideoFileKey() {
		return videoFileKey;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性视频文件key
	 */
	public void setVideoFileKey(String videoFileKey) {
		this.videoFileKey = videoFileKey == null ? null : videoFileKey.trim();
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 文件后缀
	 */
	public String getVideoSuffix() {
		return videoSuffix;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性文件后缀
	 */
	public void setVideoSuffix(String videoSuffix) {
		this.videoSuffix = videoSuffix == null ? null : videoSuffix.trim();
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 视频描述
	 */
	public String getVideoDesc() {
		return videoDesc;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性视频描述
	 */
	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc == null ? null : videoDesc.trim();
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 状态(0、无效，1、有效)
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性状态(0、无效，1、有效)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 创建人
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性创建人
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 [CT_MAGIC_QUESTION_VIDEO] 的属性 修改人
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 设置[CT_MAGIC_QUESTION_VIDEO]的属性修改人
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

}
