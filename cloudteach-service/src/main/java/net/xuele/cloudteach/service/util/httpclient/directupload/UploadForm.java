package net.xuele.cloudteach.service.util.httpclient.directupload;

/**
 * UploadForm
 *      用于直传模式的上传表单
 * @author sunxh
 * @date 15/10/25
 */
public class UploadForm {

    /**
     * 请求的过期时间(Unix时间戳－秒)
     */
    private Long expiration;

    /**
     * 上传文件内容长度
     */
    private Long fileSize;

    /**
     * 上传文件的MD5值
     */
    private String fileHash;

    /**
     * 这里由于去掉了FileName，所以后缀名为必填
     */
    private String fileType;

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
