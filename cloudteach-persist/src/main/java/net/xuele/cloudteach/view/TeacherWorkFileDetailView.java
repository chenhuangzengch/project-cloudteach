package net.xuele.cloudteach.view;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/29 0029.
 */
public class TeacherWorkFileDetailView {

    /**
     * 题目附件ID
     */
    private String fileId;

    /**
     *
     */
    private String url;
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

    private int extType;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getExtType() {
        return extType;
    }

    public void setExtType(int extType) {
        this.extType = extType;
    }
}
