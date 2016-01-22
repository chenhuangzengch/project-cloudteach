package net.xuele.cloudteach.web.wrapper;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class CloudDiskResponse {
    private String diskId;
    private String fileUri;
    private String name;
    private String extension;
    private Integer fileType;
    /**
     * 文件转换状态
     * 1成功 0未成功
     */
    private int convertStatus = 1;

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


    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public int getConvertStatus() {
        return convertStatus;
    }

    public void setConvertStatus(int convertStatus) {
        this.convertStatus = convertStatus;
    }
}
