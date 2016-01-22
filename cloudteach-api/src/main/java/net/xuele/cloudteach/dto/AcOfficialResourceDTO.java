package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * AcOfficialResourceDTO
 *
 * @author panglx
 * @date on 2015/8/17 0017.
 */
public class AcOfficialResourceDTO implements Serializable{

	private static final long serialVersionUID = 7645582510913777352L;
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
	 * 地区名称
	 */
	private String areaName;

	/**
	 * 学校id
	 */
	private String organizationId;

	/**
	 * 学校名称
	 */
	private String organizationName;

	/**
	 * 分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
	 */
	private Integer status;

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 分享id
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性分享id
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId == null ? null : resourceId.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 分享用户编号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性分享用户编号
	 */
	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 分享用户名字
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性分享用户名字
	 */
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 课程编号
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性课程编号
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 文件MD5值
	 */
	public String getFilePk() {
		return filePk;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性文件MD5值
	 */
	public void setFilePk(String filePk) {
		this.filePk = filePk == null ? null : filePk.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 HDFS文件key
	 */
	public String getFileUri() {
		return fileUri;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性HDFS文件key
	 */
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri == null ? null : fileUri.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 资源类型：1其他,2,教案3学案4课件5习题6课程素材
	 */
	public Integer getFileType() {
		return fileType;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性资源类型：1其他,2,教案3学案4课件5习题6课程素材
	 */
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 文件名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性文件名
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 简介
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性简介
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 扩展名
	 */
	public String getExtention() {
		return extention;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性扩展名
	 */
	public void setExtention(String extention) {
		this.extention = extention == null ? null : extention.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
	 */
	public Integer getExtType() {
		return extType;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
	 */
	public void setExtType(Integer extType) {
		this.extType = extType;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 扩展名对应图标
	 */
	public String getExtIconType() {
		return extIconType;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性扩展名对应图标
	 */
	public void setExtIconType(String extIconType) {
		this.extIconType = extIconType == null ? null : extIconType.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 文件大小
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性文件大小
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 添加时间
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 运维人员对分享资源进行加精(置顶)
	 */
	public Integer getBoutique() {
		return boutique;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性运维人员对分享资源进行加精(置顶)
	 */
	public void setBoutique(Integer boutique) {
		this.boutique = boutique;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 分享时间
	 */
	public Date getShareTime() {
		return shareTime;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性分享时间
	 */
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 ALL = 0,                所有人
	 SCHOOL = 1,        学校
	 AREA = 2              地区
	 */
	public Integer getShareType() {
		return shareType;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性ALL = 0,                所有人
	 SCHOOL = 1,        学校
	 AREA = 2              地区
	 */
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 地区编号
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性地区编号
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId == null ? null : areaId.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 地区名称
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 学校id
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性学校id
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId == null ? null : organizationId.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 学校名称
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性学校名称
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName == null ? null : organizationName.trim();
	}

	/**
	 * 获取 [AC_OFFICIAL_RESOURCE] 的属性 分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置[AC_OFFICIAL_RESOURCE]的属性分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
