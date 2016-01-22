package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/29 0029.
 */
public class TeacherWorkFileDetailViewDTO implements Serializable {
    private static final long serialVersionUID = 2985379759170826667L;

    /**
     * 题目附件ID
     */
    private String FileId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 扩展名图标类型
     */
    private String extIconType;

    private String url;

    private int extType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return FileId;
    }

    public void setFileId(String fileId) {
        FileId = fileId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtIconType() {
        return extIconType;
    }

    public void setExtIconType(String extIconType) {
        this.extIconType = extIconType;
    }

    public int getExtType() {
        return extType;
    }

    public void setExtType(int extType) {
        this.extType = extType;
    }

    @Override
    public String toString() {
        return "TeacherWorkFileDetailViewDTO{" +
                "extension='" + extension + '\'' +
                ", FileId='" + FileId + '\'' +
                ", name='" + name + '\'' +
                ", extIconType='" + extIconType + '\'' +
                ", url='" + url + '\'' +
                ", extType=" + extType +
                '}';
    }
}
