package net.xuele.cloudteach.web.wrapper;

/**
 * GuidanceItemStikyWrapper
 *
 * @author sunxh
 * @date 2015/7/21 0021
 */
public class GuidanceItemStikyWrapper {

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
