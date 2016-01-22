package net.xuele.cloudteach.service.util.httpclient;

/**
 * UploadResult
 *
 * @author sunxh
 * @date 15/10/25
 */
public class UploadResult {


    /**
     * 状态值，是否成功
     */
    private Boolean status;

    /**
     * 状态码，见 附录一
     */
    private Integer error;

    /**
     * 文件系统的文件唯一标识码
     */
    private String fileKey;

    /**
     * 额外扩展参数(原样返回)
     */
    private String extParam;


    public UploadResult() {

        this.setStatus(false);

        this.setFileKey(null);

        this.setError(-1);


    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    @Override
    public String toString() {
        return "UploadWrapper{" +
                "status=" + status +
                ", error=" + error +
                ", fileKey='" + fileKey + '\'' +
                ", extParam='" + extParam + '\'' +
                '}';
    }
}
