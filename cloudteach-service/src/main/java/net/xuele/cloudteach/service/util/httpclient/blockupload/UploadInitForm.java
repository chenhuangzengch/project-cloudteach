package net.xuele.cloudteach.service.util.httpclient.blockupload;

/**
 * UploadInitForm
 * 初始化表单参数
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadInitForm {

    /**
     * 请求的过期时间(Unix时间戳－秒)
     */
    private Long expiration;

    /**
     * 文件块数
     */
    private Integer fileBlocks;

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

//    /**
//     * 文件大小限制，格式：min,max，单位：字节，如 102400,1024000，允许上传 100Kb～1Mb 的文件（未使用）
//     */
//    private String contentLengthRange;
//
//    /**
//     * 文件类型限制，制定允许上传的文件扩展名（未使用）
//     */
//    private String allowFileType;
//
//    /**
//     * 异步消息通知URL（未使用）
//     */
//    private String notifyUrl;
//
//    /**
//     * 返回URL（未使用）
//     */
//    private String returnUrl;
//
//    /**
//     * 额外扩展参数，用于返回调用端（A＝1&B=2&C=3）
//     */
//    private String extParam;

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Integer getFileBlocks() {
        return fileBlocks;
    }

    public void setFileBlocks(Integer fileBlocks) {
        this.fileBlocks = fileBlocks;
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

    @Override
    public String toString() {
        return "UploadInitForm{" +
                "expiration=" + expiration +
                ", fileBlocks=" + fileBlocks +
                ", fileSize=" + fileSize +
                ", fileHash='" + fileHash + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
