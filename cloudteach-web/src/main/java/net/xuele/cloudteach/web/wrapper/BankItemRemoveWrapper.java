package net.xuele.cloudteach.web.wrapper;

/**
 * BankItemRemoveWrapper
 *
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemRemoveWrapper {
    /**
     * 预习项ID
     */
    private String itemId;

    /**
     * 状态
     */
    private Integer status;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
