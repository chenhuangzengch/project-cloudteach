package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by panglx on 2015/7/1 0001.
 */
public class DiskDownloadsDTO implements Serializable {

    private static final long serialVersionUID = 5230978186372465L;
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

    @Override
    public String toString() {
        return "DiskDownloadsDTO{" +
                "fileId='" + fileId + '\'' +
                ", userId='" + userId + '\'' +
                ", downloadTime=" + downloadTime +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
