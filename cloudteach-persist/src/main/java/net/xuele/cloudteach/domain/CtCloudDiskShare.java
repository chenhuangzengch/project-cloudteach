package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtCloudDiskShare {

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
     * SCHOOL = 1,        学校
     * AREA = 2              地区
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
    private String schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 分享状态
     * ShareFail = 3,    分享审核不通过
     * Nomal = 0,              正常可分享
     * Share = 1,                已经分享
     * Success = 2            审核成功
     */
    private Integer status;

    /**
     * 审核人
     */
    private String auditUserId;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
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
     * SCHOOL = 1,        学校
     * AREA = 2              地区
     */
    public Integer getShareType() {
        return shareType;
    }

    /**
     * 设置[CT_CLOUD_DISK_SHARE]的属性ALL = 0,                所有人
     * SCHOOL = 1,        学校
     * AREA = 2              地区
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getExtIconType() {
        return extIconType;
    }

    public void setExtIconType(String extIconType) {
        this.extIconType = extIconType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    @Override
    public String toString() {
        return "CtCloudDiskShare{" +
                "shareId='" + shareId + '\'' +
                ", diskId='" + diskId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
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
                ", areaName='" + areaName + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", status=" + status +
                ", auditUserId='" + auditUserId + '\'' +
                '}';
    }
}