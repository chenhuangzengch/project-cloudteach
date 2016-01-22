package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * BankItemWithAttachmentDTO
 * 带附件的题目DTO
 *
 * @author sunxh
 * @date 15/7/25
 */
public class BankItemWithAttachmentDTO extends BankItemDTO implements Serializable {

    private static final long serialVersionUID = -6402271813951887429L;

    /**
     * 附件列表
     */
    public List<CloudDiskReferDTO> attachmentList;

    public List<CloudDiskReferDTO> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<CloudDiskReferDTO> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public String toString() {
        return "BankItemWithAttachmentDTO{" +
                "attachmentList=" + attachmentList +
                '}';
    }
}
