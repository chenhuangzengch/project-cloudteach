package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/7/25 0025.
 */
public class TeacherWorkItemAnswerFileDTO implements Serializable {
    private static final long serialVersionUID = -2006248165784715462L;
    /**
     * 附件ID
     */
    private String answerFileId;

    /**
     * 回答ID(对应教师作业题目学生回答表主键)
     */
    private String answerId;

    /**
     * HDFS文件filekey
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
     * 附件类型：1图片，2录音，3视频
     */
    private Integer fileType;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 附件ID
     */
    public String getAnswerFileId() {
        return answerFileId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性附件ID
     */
    public void setAnswerFileId(String answerFileId) {
        this.answerFileId = answerFileId == null ? null : answerFileId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 回答ID(对应教师作业题目学生回答表主键)
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性回答ID(对应教师作业题目学生回答表主键)
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 HDFS文件filekey
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性HDFS文件filekey
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性扩展名
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 附件类型：1图片，2录音，3视频
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性附件类型：1图片，2录音，3视频
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性上传时间
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM_ANSWER_FILE] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM_ANSWER_FILE]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeacherWorkItemAnswerFileDTO{" +
                "answerFileId='" + answerFileId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", fileType=" + fileType +
                ", size=" + size +
                ", uploadTime=" + uploadTime +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}