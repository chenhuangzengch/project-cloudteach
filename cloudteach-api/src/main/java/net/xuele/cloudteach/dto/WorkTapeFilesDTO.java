package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkTapeFilesDTO
 * 作业教师录音附件
 * @author duzg
 * @date 2015/7/12 0002
 */
public class WorkTapeFilesDTO implements Serializable {
    private static final long serialVersionUID = 917047121640706228L;
    /**
     * uuid
     */
    private String fileId;

    /**
     * 作业ID
     */
    private String workId;

    /**
     * 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    private Integer workType;

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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 uuid
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性uuid
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 1预习作业，2提分宝作业，3同步课堂作业，4电子作业
     */
    public Integer getWorkType() {
        return workType;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性1预习作业，2提分宝作业，3同步课堂作业，4电子作业
     */
    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 HDFS文件url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性HDFS文件url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性扩展名
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性上传时间
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * 获取 [CT_WORK_TAPE_FILES] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_WORK_TAPE_FILES]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkTapeFilesDTO{" +
                "fileId='" + fileId + '\'' +
                ", workId='" + workId + '\'' +
                ", workType=" + workType +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", uploadTime=" + uploadTime +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}