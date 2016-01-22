package net.xuele.cloudteach.web.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DiskExistWrapper
 *
 * @author sunxh
 * @date 15/8/18
 */
public class DiskExistWrapper {

    @JsonProperty(value = "FileKey")
    private String fileKey;

    @JsonProperty(value = "Exist")
    private Boolean exist;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }
}
