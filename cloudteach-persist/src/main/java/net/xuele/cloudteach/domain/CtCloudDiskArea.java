package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtCloudDiskArea {
    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String diskId;

    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String userId;

    private String userName;

    private String positionName;

    /**
     * 课程编号
     */
    private String unitId;

    private String unitName;

    private String subjectName;

    private Integer grade;

    /**
     * HDFS文件uri
     */
    private String fileUri;

    /**
     * 云盘文件资源：1其他,2,教案3学案4课件5习题6课程素材
     */
    private Integer fileType;

    /**
     * 文件名
     */
    private String name;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
     */
    private Integer extType;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
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
     * 0：非收藏  1：大家分享的收藏  2：官方资源的收藏
     * 1：收藏
     */
    private Integer collectStatus;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 1，有效，0，无效
     */
    private Integer status;

    private String schoolName;

    /**
     * 地市区域ID（用于分片）
     */
    private String areaCode;

    /**
     * 区县区域ID
     */
    private String areaCode2;

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    public String getDiskId() {
        return diskId;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性文件编号，老数据库为自增ID，新库可以使用uuid
     */
    public void setDiskId(String diskId) {
        this.diskId = diskId == null ? null : diskId.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性文件所属用户id，如果为创建者，所属用户id=创建者id
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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 课程编号
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性课程编号
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 HDFS文件uri
     */
    public String getFileUri() {
        return fileUri;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性HDFS文件uri
     */
    public void setFileUri(String fileUri) {
        this.fileUri = fileUri == null ? null : fileUri.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 云盘文件资源：1其他,2,教案3学案4课件5习题6课程素材
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性云盘文件资源：1其他,2,教案3学案4课件5习题6课程素材
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性文件名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性扩展名
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
     */
    public Integer getExtType() {
        return extType;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
     */
    public void setExtType(Integer extType) {
        this.extType = extType;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
     * NotShare = -5,        不可分享
     * Nomal = 0,              正常可分享
     * Share = 5,                已经分享
     * Success = 10            审核成功
     */
    public Integer getShareStatus() {
        return shareStatus;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
     * NotShare = -5,        不可分享
     * Nomal = 0,              正常可分享
     * Share = 5,                已经分享
     * Success = 10            审核成功
     */
    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 最后一次分享的更新
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性最后一次分享的更新
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 0：非收藏  1：大家分享的收藏  2：官方资源的收藏
     * 1：收藏
     */
    public Integer getCollectStatus() {
        return collectStatus;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性0：非收藏  1：大家分享的收藏  2：官方资源的收藏
     * 1：收藏
     */
    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_CLOUD_DISK_AREA] 的属性 1，有效，0，无效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_CLOUD_DISK_AREA]的属性1，有效，0，无效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getAreaCode2() {
        return areaCode2;
    }

    public void setAreaCode2(String areaCode2) {
        this.areaCode2 = areaCode2;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}