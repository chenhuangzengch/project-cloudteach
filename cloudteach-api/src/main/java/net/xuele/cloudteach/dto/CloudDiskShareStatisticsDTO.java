package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by panglx on 2015/7/1 0001.
 */
public class CloudDiskShareStatisticsDTO implements Serializable {
	private static final long serialVersionUID = -4886321162205294468L;

	@Override
	public String toString() {
		return "CloudDiskShareStatisticsDTO{" +
				"shareId='" + shareId + '\'' +
				", downloadTimes=" + downloadTimes +
				", saveTimes=" + saveTimes +
				", readTimes=" + readTimes +
				", praiseTimes=" + praiseTimes +
				'}';
	}

	/**
	 * 分享编号
	 */
	private String shareId;

	/**
	 * 下载数
	 */
	private Integer downloadTimes;

	/**
	 * 收藏数
	 */
	private Integer saveTimes;

	/**
	 * 浏览数
	 */
	private Integer readTimes;

	/**
	 * 点赞总数
	 */
	private Integer praiseTimes;

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE_STATISTICS] 的属性 文件编号
	 */
	public String getShareId() {
		return shareId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE_STATISTICS]的属性文件编号
	 */
	public void setShareId(String shareId) {
		this.shareId = shareId == null ? null : shareId.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE_STATISTICS] 的属性 下载数
	 */
	public Integer getDownloadTimes() {
		return downloadTimes;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE_STATISTICS]的属性下载数
	 */
	public void setDownloadTimes(Integer downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE_STATISTICS] 的属性 收藏数
	 */
	public Integer getSaveTimes() {
		return saveTimes;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE_STATISTICS]的属性收藏数
	 */
	public void setSaveTimes(Integer saveTimes) {
		this.saveTimes = saveTimes;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE_STATISTICS] 的属性 浏览数
	 */
	public Integer getReadTimes() {
		return readTimes;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE_STATISTICS]的属性浏览数
	 */
	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE_STATISTICS] 的属性 点赞总数
	 */
	public Integer getPraiseTimes() {
		return praiseTimes;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE_STATISTICS]的属性点赞总数
	 */
	public void setPraiseTimes(Integer praiseTimes) {
		this.praiseTimes = praiseTimes;
	}
}
