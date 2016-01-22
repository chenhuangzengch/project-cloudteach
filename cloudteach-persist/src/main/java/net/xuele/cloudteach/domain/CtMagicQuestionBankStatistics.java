package net.xuele.cloudteach.domain;

public class CtMagicQuestionBankStatistics {
    /**
     * 题库ID
     */
    private String bankId;

    /**
     * 发布次数
     */
    private Integer releases;

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK_STATISTICS] 的属性 题库ID
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK_STATISTICS]的属性题库ID
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_BANK_STATISTICS] 的属性 发布次数
     */
    public Integer getReleases() {
        return releases;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_BANK_STATISTICS]的属性发布次数
     */
    public void setReleases(Integer releases) {
        this.releases = releases;
    }
}