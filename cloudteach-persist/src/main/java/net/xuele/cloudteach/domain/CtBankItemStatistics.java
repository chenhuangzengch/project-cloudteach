package net.xuele.cloudteach.domain;

public class CtBankItemStatistics {
    /**
     * 题目ID
     */
    private String itemId;

    /**
     * 发布次数
     */
    private Integer releases;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 获取 [CT_BANK_ITEM_STATISTICS] 的属性 题目ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置[CT_BANK_ITEM_STATISTICS]的属性题目ID
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取 [CT_BANK_ITEM_STATISTICS] 的属性 发布次数
     */
    public Integer getReleases() {
        return releases;
    }

    /**
     * 设置[CT_BANK_ITEM_STATISTICS]的属性发布次数
     */
    public void setReleases(Integer releases) {
        this.releases = releases;
    }

    /**
     * 获取 [CT_BANK_ITEM_STATISTICS] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_BANK_ITEM_STATISTICS]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }
}