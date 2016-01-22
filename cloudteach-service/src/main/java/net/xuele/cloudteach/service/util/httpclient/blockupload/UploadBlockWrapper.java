package net.xuele.cloudteach.service.util.httpclient.blockupload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UploadMergeWrapper
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadBlockWrapper {

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

    @Override
    public String toString() {
        return "UploadBlockWrapper{" +
                "status=" + status +
                ", error=" + error +
                '}';
    }
}
