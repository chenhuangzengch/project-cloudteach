package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by panglx on 2015/7/1 0001.
 */
public class CloudDiskShareDTO implements Serializable {
	private static final long serialVersionUID = 8913603638648372737L;

	@Override
	public String toString() {
		return "CloudDiskShareDTO{" +
				"shareId='" + shareId + '\'' +
				", diskId='" + diskId + '\'' +
				", userId='" + userId + '\'' +
				", unitId='" + unitId + '\'' +
				", filePk='" + filePk + '\'' +
				", fileUri='" + fileUri + '\'' +
				", fileType=" + fileType +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", extention='" + extention + '\'' +
				", extType=" + extType +
				", extIconType='" + extIconType + '\'' +
				", size=" + size +
				", addTime=" + addTime +
				", updateTime=" + updateTime +
				", boutique=" + boutique +
				", shareTime=" + shareTime +
				", auditInstructions='" + auditInstructions + '\'' +
				", shareType=" + shareType +
				", areaId='" + areaId + '\'' +
				", schoolId='" + schoolId + '\'' +
				", status=" + status +
				", saveTimes=" + saveTimes +
				", praiseTimes=" + praiseTimes +
				", collectState=" + collectState +
				", praiseState=" + praiseState +
				", shareState=" + shareState +
				", schoolName='" + schoolName + '\'' +
				", areaName='" + areaName + '\'' +
				", userName='" + userName + '\'' +
				'}';
	}

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
	 * 文件类型
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
	 * 扩展名类型
	 */
	private Integer extType;

	/**
	 * 扩展名图标类型
	 */
	private String extIconType;

	/**
	 * 文件大小
	 */
	private Integer size;

	/**
	 * 添加时间
	 */
	private Date addTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 运维人员对分享资源进行加精(置顶)
	 */
	private Integer boutique;

	/**
	 * 分享时间
	 */
	private Date shareTime;

	/**
	 * 审核原因
	 */
	private String auditInstructions;

	/**
	 * ALL = 0,                所有人
	 SCHOOL = 1,        学校
	 AREA = 2              地区
	 */
	private Integer shareType;

	/**
	 * 地区编号
	 */
	private String areaId;

	/**
	 * 学校id
	 */
	private String schoolId;

	/**
	 *  分享状态
	 ShareFail = 3,    分享审核不通过
	 Nomal = 0,              正常可分享
	 Share = 1,                审核中
	 Success = 2            审核成功（分享成功）
	 */
	private Integer status;

	/**
	 * 收藏数
	 */
	private Integer saveTimes;

	/**
	 * 点赞总数
	 */
	private Integer praiseTimes;

	/**
	 * 收藏状态
	 */
	private Integer collectState;

	/**
	 * 点赞状态
	 */
	private Integer praiseState;

	/**
	 * 分享状态--是否自己分享
	 */
	private Integer shareState;

	/**
	 * 学校名称
	 * @return
	 */
	private String schoolName;

	/**
	 * 地区名称
	 */
	private String areaName;

	/**
	 * 用户名
	 */
	private String userName;

	private String audit_user_id;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getShareState() {
		return shareState;
	}

	public void setShareState(Integer shareState) {
		this.shareState = shareState;
	}

	public Integer getSaveTimes() {
		return saveTimes;
	}

	public void setSaveTimes(Integer saveTimes) {
		this.saveTimes = saveTimes;
	}

	public Integer getPraiseTimes() {
		return praiseTimes;
	}

	public void setPraiseTimes(Integer praiseTimes) {
		this.praiseTimes = praiseTimes;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public Integer getPraiseState() {
		return praiseState;
	}

	public void setPraiseState(Integer praiseState) {
		this.praiseState = praiseState;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 分享id
	 */
	public String getShareId() {
		return shareId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性分享id
	 */
	public void setShareId(String shareId) {
		this.shareId = shareId == null ? null : shareId.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 云盘编号
	 */
	public String getDiskId() {
		return diskId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性云盘编号
	 */
	public void setDiskId(String diskId) {
		this.diskId = diskId == null ? null : diskId.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 分享用户编号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性分享用户编号
	 */
	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 课程编号
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性课程编号
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 文件MD5值
	 */
	public String getFilePk() {
		return filePk;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性文件MD5值
	 */
	public void setFilePk(String filePk) {
		this.filePk = filePk == null ? null : filePk.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 HDFS文件key
	 */
	public String getFileUri() {
		return fileUri;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性HDFS文件key
	 */
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri == null ? null : fileUri.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 文件类型
	 */
	public Integer getFileType() {
		return fileType;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性文件类型
	 */
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 文件名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性文件名
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 简介
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性简介
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 扩展名
	 */
	public String getExtention() {
		return extention;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性扩展名
	 */
	public void setExtention(String extention) {
		this.extention = extention == null ? null : extention.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 文件大小
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性文件大小
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 添加时间
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 运维人员对分享资源进行加精(置顶)
	 */
	public Integer getBoutique() {
		return boutique;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性运维人员对分享资源进行加精(置顶)
	 */
	public void setBoutique(Integer boutique) {
		this.boutique = boutique;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 分享时间
	 */
	public Date getShareTime() {
		return shareTime;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性分享时间
	 */
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 审核原因
	 */
	public String getAuditInstructions() {
		return auditInstructions;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性审核原因
	 */
	public void setAuditInstructions(String auditInstructions) {
		this.auditInstructions = auditInstructions == null ? null : auditInstructions.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 ALL = 0,                所有人
	 SCHOOL = 1,        学校
	 AREA = 2              地区
	 */
	public Integer getShareType() {
		return shareType;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性ALL = 0,                所有人
	 SCHOOL = 1,        学校
	 AREA = 2              地区
	 */
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 地区编号
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性地区编号
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId == null ? null : areaId.trim();
	}

	/**
	 * 获取 [CT_CLOUD_DISK_SHARE] 的属性 学校id
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * 设置[CT_CLOUD_DISK_SHARE]的属性学校id
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId == null ? null : schoolId.trim();
	}

	/**
	 *  分享状态
	 ShareFail = 3,    分享审核不通过
	 Nomal = 0,              正常可分享
	 Share = 1,                审核中
	 Success = 2            审核成功（分享成功）
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 *  分享状态
	 ShareFail = 3,    分享审核不通过
	 Nomal = 0,              正常可分享
	 Share = 1,                审核中
	 Success = 2            审核成功（分享成功）
	 */
	public void setStatus(Integer status) {
		this.status = status;
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

	public String getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(String audit_user_id) {
		this.audit_user_id = audit_user_id;
	}
}
