package net.xuele.cloudteach.web.wrapper;

import java.util.Date;
import java.util.List;

/**
 * GuidanceItemShareDetailWrapper
 *
 * @author panglx
 * @date on 2015/7/20 0020.
 */
public class GuidanceItemShareDetailWrapper {
	/**
	 * 分享预习项ID
	 */
	private String shareId;

	/**
	 * 提交方式：图片
	 */
	private Integer subImage;

	/**
	 * 提交方式：录音
	 */
	private Integer subTape;

	/**
	 * 提交方式：视频
	 */
	private Integer subVideo;

	/**
	 * 描述内容
	 */
	private String context;

	/**
	 * 分享时间
	 */
	private Date shareTime;

	/**
	 * 头像
	 */
	private String icon;

	/**
	 * 预习项附件信息
	 */
	List<GuidanceItemFilesDetail> guidanceItemFilesDetails;

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public Integer getSubImage() {
		return subImage;
	}

	public void setSubImage(Integer subImage) {
		this.subImage = subImage;
	}

	public Integer getSubTape() {
		return subTape;
	}

	public void setSubTape(Integer subTape) {
		this.subTape = subTape;
	}

	public Integer getSubVideo() {
		return subVideo;
	}

	public void setSubVideo(Integer subVideo) {
		this.subVideo = subVideo;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<GuidanceItemFilesDetail> getGuidanceItemFilesDetails() {
		return guidanceItemFilesDetails;
	}

	public void setGuidanceItemFilesDetails(List<GuidanceItemFilesDetail> guidanceItemFilesDetails) {
		this.guidanceItemFilesDetails = guidanceItemFilesDetails;
	}
}
