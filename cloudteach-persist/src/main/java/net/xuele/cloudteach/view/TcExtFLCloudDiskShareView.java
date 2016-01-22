package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtCloudDiskShare;

public class TcExtFLCloudDiskShareView{
	/**
	 * 分享id
	 */
	private String shareId;

	/**
	 * 云盘编号
	 */
	private String diskId;

	/**
	 * 分享用户编号
	 */
	private String userId;

	/**
	 * 分享用户名字
	 */
	private String userName;

	/**
	 * HDFS文件key
	 */
	private String fileUri;

	/**
	 * 文件类型
	 */
	private Integer fileType;

	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 扩展名
	 */
	private String extention;

	/**
	 * 扩展名类型
	 */
	private Integer extType;

	/**
	 * 文件大小
	 */
	private Integer size;

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public Integer getExtType() {
		return extType;
	}

	public void setExtType(Integer extType) {
		this.extType = extType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
