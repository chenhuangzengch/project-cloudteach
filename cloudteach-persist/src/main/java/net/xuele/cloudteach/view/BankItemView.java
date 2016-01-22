package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtBankItem;

/**
 * BankItemView
 *
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemView extends CtBankItem {

    /**
     * 附件总数
     */
    private Long filesAmount;

    /**
     * 发布次数
     */
    private Integer releases;

    public Long getFilesAmount() {
        return filesAmount;
    }

    public void setFilesAmount(Long filesAmount) {
        this.filesAmount = filesAmount;
    }

    public Integer getReleases() {
        return releases;
    }

    public void setReleases(Integer releases) {
        this.releases = releases;
    }
}
