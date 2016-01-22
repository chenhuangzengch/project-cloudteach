package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtBlackboardPublishFiles {
    /**
     * 板书发布附件ID
     */
    private String fileId;

    /**
     * 板书发布ID
     */
    private String blackboardId;

    /**
     * HDFS文件url
     */
    private String url;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 状态
     */
    private Integer status;

    private String schoolId;

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 板书发布附件ID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性板书发布附件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 板书发布ID
     */
    public String getBlackboardId() {
        return blackboardId;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性板书发布ID
     */
    public void setBlackboardId(String blackboardId) {
        this.blackboardId = blackboardId == null ? null : blackboardId.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 HDFS文件url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性HDFS文件url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性扩展名
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性上传时间
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * 获取 [CT_BLACKBOARD_PUBLISH_FILES] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_BLACKBOARD_PUBLISH_FILES]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}