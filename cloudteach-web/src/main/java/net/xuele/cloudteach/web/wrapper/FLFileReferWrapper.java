package net.xuele.cloudteach.web.wrapper;

/**
 * 返回给flash的附件包装类
 * Created by sunxh on 15/8/21.
 */
public class FLFileReferWrapper {

    /**
     * 文件编号 如果不是引用云盘，这里为空
     */
    private String diskId;

    /**
     * HDFS文件uri
     */
    private String fileUri;

    /**
     * 文件名
     */
    private String name;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 文件转换状态：0未转换成功  1转换成功
     */
    private int convertStatus;

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
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

    public int getConvertStatus() {
        return convertStatus;
    }

    public void setConvertStatus(int convertStatus) {
        this.convertStatus = convertStatus;
    }
}
