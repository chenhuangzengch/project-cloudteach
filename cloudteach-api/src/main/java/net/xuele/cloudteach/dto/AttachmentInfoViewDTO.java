package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/27 0027.
 * 附件信息DTO，用于附件json格式封装
 */
public class AttachmentInfoViewDTO implements Serializable {

    private static final long serialVersionUID = -6861158714201587062L;

    // ct_bank_item_files.file_id
    private String fileId;

    // ct_cloud_disk.file_uri
    private String fileUri;

    // ct_cloud_disk.extension
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    @Override
    public String toString() {
        return "AttachmentInfoViewDTO{" +
                "fileId='" + fileId + '\'' +
                ", fileUri='" + fileUri + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
