package net.xuele.cloudteach.web.wrapper;

/**
 * Created by hujx on 2015/8/4 0004.
 */
public class TeacherWorkFileDetailWrapper {


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

}
