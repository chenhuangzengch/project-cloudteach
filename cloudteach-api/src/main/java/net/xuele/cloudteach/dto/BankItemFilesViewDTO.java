package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class BankItemFilesViewDTO extends CloudDiskDTO implements Serializable {

    private static final long serialVersionUID = -2148315291966518778L;
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


    @Override
    public String toString() {
        return "BankItemFilesViewDTO{" +
                "fileId='" + fileId + '\'' +
                ", fType=" + fType +
                ", relationId='" + relationId + '\'' +
                '}';
    }
}