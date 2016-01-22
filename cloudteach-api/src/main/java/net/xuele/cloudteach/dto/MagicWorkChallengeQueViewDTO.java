package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * MagicWorkChallengeQueViewDTO
 *
 * @author panglx
 * @date on 2015/8/14 0014.
 */
public class MagicWorkChallengeQueViewDTO extends MagicQuestionDTO implements Serializable{

	private static final long serialVersionUID = 2868848460347372334L;

	/**
	 * 回答正确状态:0错误 1正确
	 */
	private Integer rwStatus;

	/**
	 * ID
	 */
	private String videoId;

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

	public Integer getRwStatus() {
		return rwStatus;
	}

	public void setRwStatus(Integer rwStatus) {
		this.rwStatus = rwStatus;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoFileKey() {
		return videoFileKey;
	}

	public void setVideoFileKey(String videoFileKey) {
		this.videoFileKey = videoFileKey;
	}

	public String getVideoSuffix() {
		return videoSuffix;
	}

	public void setVideoSuffix(String videoSuffix) {
		this.videoSuffix = videoSuffix;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}
}
