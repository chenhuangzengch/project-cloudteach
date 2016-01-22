package net.xuele.cloudteach.dto;


import java.io.Serializable;

/**
 * GuidanceItemFilesViewDTO
 * 预习作业预习题目附件VIEW
 * @author duzg
 * @date 2015/7/17 0002
 */
public class GuidanceItemFilesViewDTO extends CloudDiskDTO implements Serializable {
    private static final long serialVersionUID = -1372330766025356576L;
    /**
     * 预习附件ID
     */
    private String fileId;

    /**
     * 1，我的预习项，2，大家分享的预习项，3，预习作业预习项
     */
    private Integer fType;

    /**
     * 预习附件关联id
     */
    private String relationId;

    /**
     * 关联的云盘资源id
     */
    private String diskId;

    /**
     * 获取 [CT_GUIDANCE_ITEM_FILES] 的属性 预习附件ID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_FILES]的属性预习附件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_FILES] 的属性 1，我的预习项，2，大家分享的预习项，3，预习作业预习项
     */
    public Integer getfType() {
        return fType;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_FILES]的属性1，我的预习项，2，大家分享的预习项，3，预习作业预习项
     */
    public void setfType(Integer fType) {
        this.fType = fType;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_FILES] 的属性 预习附件关联id
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_FILES]的属性预习附件关联id
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_FILES] 的属性 关联的云盘资源id
     */
    public String getDiskId() {
        return diskId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_FILES]的属性关联的云盘资源id
     */
    public void setDiskId(String diskId) {
        this.diskId = diskId == null ? null : diskId.trim();
    }

    @Override
    public String toString() {
        return "GuidanceItemFilesViewDTO{" +
                "fileId='" + fileId + '\'' +
                ", fType=" + fType +
                ", relationId='" + relationId + '\'' +
                ", diskId='" + diskId + '\'' +
                '}';
    }
}