package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtCloudDisk {
    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String diskId;

    /**
     * 文件用户编号，老数据库为学校ID+编号
     */
    private String creator;

    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String userId;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 文件唯一性编号
     */
    private String filePk;

    /**
     * HDFS文件uri
     */
    private String fileUri;

    /**
     * 云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
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
    private String extension;

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
     * 用户文件的置顶状态
     * 0：不置顶
     * 1：置顶
     */
    private Integer stickyStatus;

    /**
     * ShareFail = -105,    分享审核不通过
     * NotShare = -5,        不可分享
     * Nomal = 0,              正常可分享
     * Share = 5,                已经分享
     * Success = 10            审核成功
     */
    private Integer shareStatus;

    /**
     * 最后一次分享的更新
     */
    private Date shareTime;

    /**
     * 审核说明
     */
    private String auditInstructions;

    /**
     * 0：非收藏
     * 1：收藏【来自大家的分享】
     * 2：收藏【来自官方资源】
     */
    private Integer collectStatus;

    /**
     * 如果是收藏来的资源，也会在这张表插入一条记录，但是UFID不为空，表示源文件的ID
     */
    private String pid;

    /**
     * 学校ID，用于分片
     */
    private String schoolId;


    /**
     * 1正常0  已删除
     */
    private Integer status;

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    public String getDiskId() {
        return diskId;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性文件编号，老数据库为自增ID，新库可以使用uuid
     */
    public void setDiskId(String diskId) {
        this.diskId = diskId == null ? null : diskId.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 文件用户编号，老数据库为学校ID+编号
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性文件用户编号，老数据库为学校ID+编号
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 课程编号
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性课程编号
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 文件唯一性编号
     */
    public String getFilePk() {
        return filePk;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性文件唯一性编号
     */
    public void setFilePk(String filePk) {
        this.filePk = filePk == null ? null : filePk.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 HDFS文件uri
     */
    public String getFileUri() {
        return fileUri;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性HDFS文件uri
     */
    public void setFileUri(String fileUri) {
        this.fileUri = fileUri == null ? null : fileUri.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性文件名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 简介
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性简介
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性扩展名
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
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

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 用户文件的置顶状态
     * 0：不置顶
     * 1：置顶
     */
    public Integer getStickyStatus() {
        return stickyStatus;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性用户文件的置顶状态
     * 0：不置顶
     * 1：置顶
     */
    public void setStickyStatus(Integer stickyStatus) {
        this.stickyStatus = stickyStatus;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性  ShareFail = -105,    分享审核不通过
     * NotShare = -5,        不可分享
     * Nomal = 0,              正常可分享
     * Share = 5,                已经分享
     * Success = 10            审核成功
     */
    public Integer getShareStatus() {
        return shareStatus;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性 ShareFail = -105,    分享审核不通过
     * NotShare = -5,        不可分享
     * Nomal = 0,              正常可分享
     * Share = 5,                已经分享
     * Success = 10            审核成功
     */
    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 最后一次分享的更新
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性最后一次分享的更新
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 审核说明
     */
    public String getAuditInstructions() {
        return auditInstructions;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性审核说明
     */
    public void setAuditInstructions(String auditInstructions) {
        this.auditInstructions = auditInstructions == null ? null : auditInstructions.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 0：非收藏
     * 1：收藏
     */
    public Integer getCollectStatus() {
        return collectStatus;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性0：非收藏
     * 1：收藏
     */
    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 如果是收藏来的资源，也会在这张表插入一条记录，但是UFID不为空，表示源文件的ID
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性如果是收藏来的资源，也会在这张表插入一条记录，但是UFID不为空，表示源文件的ID
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK] 的属性 1正常0  已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_CLOUD_DISK]的属性1正常0  已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}