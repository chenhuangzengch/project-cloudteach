package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by sunxinhe on 15/8/22.
 */
public class FLBankItemWrapper {

    private String itemId;

    private Integer itemType;

    private String context;

    private String voiceContext;

    private List<FLFileReferWrapper> fileList;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
    }

    public List<FLFileReferWrapper> getFileList() {
        return fileList;
    }

    public void setFileList(List<FLFileReferWrapper> fileList) {
        this.fileList = fileList;
    }
}
