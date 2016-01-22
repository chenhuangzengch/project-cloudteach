package net.xuele.cloudteach.web.wrapper;

/**
 * FileShowWrapper
 * 文件展示包装类
 *
 * @author sunxh
 * @date 2015/7/20 0020
 */
public class FileShowWrapper {

    /**
     * 文件路径
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
     * 扩展名类型
     */
    private String extType;

    /**
     * 扩展名图标类型
     */
    private String extIconType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getExtIconType() {
        return extIconType;
    }

    public void setExtIconType(String extIconType) {
        this.extIconType = extIconType;
    }
}
