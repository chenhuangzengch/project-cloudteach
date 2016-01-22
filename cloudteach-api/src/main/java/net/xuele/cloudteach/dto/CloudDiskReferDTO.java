package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CloudDiskReferDTO
 *
 * @author sunxh
 * @date 2015/7/17 0017
 */
public class CloudDiskReferDTO implements Serializable {
    private static final long serialVersionUID = -6643129725145854317L;

    @Override
    public String toString() {
        return "CloudDiskReferDTO{" +
                "referId='" + referId + '\'' +
                ", diskId='" + diskId + '\'' +
                ", userId='" + userId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", filePk='" + filePk + '\'' +
                ", fileUri='" + fileUri + '\'' +
                ", fileType=" + fileType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", extension='" + extension + '\'' +
                ", extType=" + extType +
                ", extIconType='" + extIconType + '\'' +
                ", size=" + size +
                '}';
    }

    /**
     * 引用ID
     */
    private String referId;

    /**
     * 文件编号
     */
    private String diskId;


    /**
     * 文件所属用户id
     */
    private String userId;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 文件唯一性编号
     */
    private String filePk;

    /**
     * HDFS文件uri
     */
    private String fileUri;

    /**
     * 云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
     */
    private Integer fileType;

    /**
     * 文件名
     */
    private String name;

    /**
     * 简介
     */
    private String description;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 扩展名类型
     */
    private Integer extType;

    /**
     * 扩展名图标类型
     */
    private String extIconType;

    /**
     * 文件大小
     */
    private Integer size;

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getFilePk() {
        return filePk;
    }

    public void setFilePk(String filePk) {
        this.filePk = filePk;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }

    public String getExtIconType() {
        return extIconType;
    }

    public void setExtIconType(String extIconType) {
        this.extIconType = extIconType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
