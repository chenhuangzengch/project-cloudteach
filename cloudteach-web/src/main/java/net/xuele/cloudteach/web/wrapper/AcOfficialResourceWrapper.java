package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * AcOfficialResourceWrapper
 *
 * @author panglx
 * @date on 2015/8/17 0017.
 */
public class AcOfficialResourceWrapper {
	/**
	 * 分享id
	 */
	private String resourceId;

	/**
	 * 分享用户编号
	 */
	private String userId;

	/**
	 * 分享用户名字
	 */
	private String userName;

	/**
	 * 课程编号
	 */
	private String unitId;

	/**
	 * 文件MD5值
	 */
	private String filePk;

	/**
	 * HDFS文件key
	 */
	private String fileUri;

	/**
	 * 资源类型：1其他,2,教案3学案4课件5习题6课程素材
	 */
	private Integer fileType;

	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 简介
	 */
	private String description;

	/**
	 * 扩展名
	 */
	private String extention;

	/**
	 * 扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
	 */
	private Integer extType;

	/**
	 * 扩展名对应图标
	 */
	private String extIconType;

	/**
	 * 文件大小
	 */
	private Integer size;

	/**
	 * 运维人员对分享资源进行加精(置顶)
	 */
	private Integer boutique;

	/**
	 * 分享时间
	 */
	private Date shareTime;


	/**
	 * 地区编号
	 */
	private String areaId;

	/**
	 * 地区名称
	 */
	private String areaName;

	/**
	 * 机构id
	 */
	private String organizationId;

	/**
	 * 机构名称
	 */
	private String organizationName;

	/**
	 * 分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
	 */
	private Integer status;

	/**
	 * 收藏状态
	 */
	private Integer collectState;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getFilePk() {
		return filePk;
	}

	public void setFilePk(String filePk) {
		this.filePk = filePk;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getExtIconType() {
		return extIconType;
	}

	public void setExtIconType(String extIconType) {
		this.extIconType = extIconType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getBoutique() {
		return boutique;
	}

	public void setBoutique(Integer boutique) {
		this.boutique = boutique;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}
}
