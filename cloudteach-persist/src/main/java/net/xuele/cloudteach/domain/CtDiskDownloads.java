package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtDiskDownloads {

    /**
     * 下载编号
     */
    private String downloadId;

    /**
     * 文件编号
     */
    private String fileId;

    /**
     * 下载用户
     */
    private String userId;

    /**
     * 下载时间
     */
    private Date downloadTime;

    /**
     * 学校ID
     */
    private String schoolId;

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    /**
     * 获取 [CT_DISK_DOWNLOADS] 的属性 文件编号
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置[CT_DISK_DOWNLOADS]的属性文件编号
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 获取 [CT_DISK_DOWNLOADS] 的属性 下载用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_DISK_DOWNLOADS]的属性下载用户
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [CT_DISK_DOWNLOADS] 的属性 下载时间
     */
    public Date getDownloadTime() {
        return downloadTime;
    }

    /**
     * 设置[CT_DISK_DOWNLOADS]的属性下载时间
     */
    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }
}