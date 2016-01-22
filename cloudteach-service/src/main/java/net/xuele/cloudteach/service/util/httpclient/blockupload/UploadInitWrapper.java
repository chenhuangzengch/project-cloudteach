package net.xuele.cloudteach.service.util.httpclient.blockupload;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UploadInitWrapper
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadInitWrapper {

    /**
     * 状态值
     */
    @JsonProperty(value = "Status")
    private Boolean status;

    /**
     * 分块上传索引key，下一步分块上传数据时必须携带本参数（GUID）
     */
    @JsonProperty(value = "SaveToken")
    private String saveToken;

    /**
     * 文件分块数量
     */
    @JsonProperty(value = "Blocks")
    private Integer blocks;

    /**
     * 后续上传接收地址(如http://fs.xuele.net/)
     */
    @JsonProperty(value = "Host")
    private String host;

    /**
     * 额外扩展参数(原样返回)
     */
    @JsonProperty(value = "ExtParam")
    private String extParam;

    @JsonProperty(value = "BlockStatus")
    private String blockStatus;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSaveToken() {
        return saveToken;
    }

    public void setSaveToken(String saveToken) {
        this.saveToken = saveToken;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public String getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(String blockStatus) {
        this.blockStatus = blockStatus;
    }
}
