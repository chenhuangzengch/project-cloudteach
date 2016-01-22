package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CloudDiskPreviewDTO
 *
 * @author sunxh
 * @date 2015/7/4 0004
 */
public class CloudDiskPreviewDTO implements Serializable {

    private static final long serialVersionUID = -4328324206175603273L;

    @Override
    public String toString() {
        return "CloudDiskPreviewDTO{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                '}';
    }

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件地址
     */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
