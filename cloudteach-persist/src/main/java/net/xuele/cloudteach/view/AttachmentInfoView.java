package net.xuele.cloudteach.view;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/27 0027.
 * 附件信息DTO，用于附件json格式封装
 */
public class AttachmentInfoView implements Serializable {

    // ct_bank_item_files.file_id
    private String id;

    // ct_cloud_disk.file_uri
    private String uri;

    // ct_cloud_disk.name
    private String name;

    // ct_cloud_disk.extension
    private String ext;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
