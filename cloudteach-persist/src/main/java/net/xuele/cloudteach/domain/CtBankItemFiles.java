package net.xuele.cloudteach.domain;

public class CtBankItemFiles {
    /**
     * 题目附件ID
     */
    private String fileId;

    /**
     * 1教师题目，3教师作业题目
     */
    private Integer fType;

    /**
     * 题目附件关联id(题目ID 或 作业ID)
     */
    private String relationId;

    /**
     * 关联的云盘资源id
     */
    private String diskId;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 获取 [CT_BANK_ITEM_FILES] 的属性 题目附件ID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置[CT_BANK_ITEM_FILES]的属性题目附件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_FILES] 的属性 1教师题目，3教师作业题目
     */
    public Integer getfType() {
        return fType;
    }

    /**
     * 设置[CT_BANK_ITEM_FILES]的属性1教师题目，3教师作业题目
     */
    public void setfType(Integer fType) {
        this.fType = fType;
    }

    /**
     * 获取 [CT_BANK_ITEM_FILES] 的属性 题目附件关联id(题目ID 或 作业ID)
     */
    public String getRelationId() {
        return relationId;
    }

    /**
     * 设置[CT_BANK_ITEM_FILES]的属性题目附件关联id(题目ID 或 作业ID)
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_FILES] 的属性 关联的云盘资源id
     */
    public String getDiskId() {
        return diskId;
    }

    /**
     * 设置[CT_BANK_ITEM_FILES]的属性关联的云盘资源id
     */
    public void setDiskId(String diskId) {
        this.diskId = diskId == null ? null : diskId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_FILES] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_BANK_ITEM_FILES]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }
}