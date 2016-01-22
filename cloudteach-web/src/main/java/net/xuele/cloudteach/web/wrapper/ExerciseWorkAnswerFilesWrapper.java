package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * ExerciseWorkAnswerFilesWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class ExerciseWorkAnswerFilesWrapper {
    private String answerFileId;

    private String answerId;

    private String url;

    private String fileName;

    private String extension;

    /**
     * 1图片，2录音，3视频
     */
    private Integer fileType;

    private Integer size;

    private Date uploadTime;

    private Integer status;

    public String getAnswerFileId() {
        return answerFileId;
    }

    public void setAnswerFileId(String answerFileId) {
        this.answerFileId = answerFileId == null ? null : answerFileId.trim();
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * 获取 [CT_EXERCISE_WORK_ANSWER_FILES] 的属性 1图片，2录音，3视频
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * 设置[CT_EXERCISE_WORK_ANSWER_FILES]的属性1图片，2录音，3视频
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}