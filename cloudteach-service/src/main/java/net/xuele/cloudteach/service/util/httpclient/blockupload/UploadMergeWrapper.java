package net.xuele.cloudteach.service.util.httpclient.blockupload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UploadMergeWrapper
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadMergeWrapper {

    /**
     * 状态值，是否成功
     */
    @JsonProperty(value = "Status")
    private Boolean status;

    /**
     * 状态码，见 附录一
     */
    @JsonProperty(value = "Error")
    private Integer error;

    /**
     * 文件系统的文件唯一标识码
     */
    @JsonProperty(value = "FileKey")
    private String fileKey;

    /**
     * 额外扩展参数(原样返回)
     */
    @JsonProperty(value = "ExtParam")
    private String extParam;

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
}
