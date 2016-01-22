package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * BankItemAppFilesDTO
 * 移动端发布电子作业或口语作业时提交的本地文件
 * @author duzg
 * @date 2015/7/31 0002
 */
public class BankItemAppFilesDTO implements Serializable {

    private static final long serialVersionUID = -3851162727559780248L;
    /**
     * fileKey
     */
    private String url;

    private String fileName;

    private String extension;

    /**
     * 1图片，2录音，3视频
     */
    private Integer fileType;

    private Integer size;

    private Date uploadTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getFileType() {
        return fileType;
    }

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

    @Override
    public String toString() {
        return "BankItemAppFilesDTO{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                ", fileType=" + fileType +
                ", size=" + size +
                ", uploadTime=" + uploadTime +
                '}';
    }
}