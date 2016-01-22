package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtCloudDisk;

public class BankItemFilesView extends CtCloudDisk {
    /**
     * 附件ID
     */
    private String fileId;

    /**
     * 1，教师题目，3，教师作业题目
     */
    private Integer fType;

    /**
     * 附件关联id
     */
    private String relationId;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

}