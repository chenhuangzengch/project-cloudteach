package net.xuele.cloudteach.web.wrapper;

/**
 * BankItemStickyWrapper
 *      预习题置顶信息
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemStickyWrapper {

    /**
     * 预习项ID
     */
    private String itemId;

    /**
     * 置顶标志
     */
    private Integer stickStatus;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getStickStatus() {
        return stickStatus;
    }

    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }
}
