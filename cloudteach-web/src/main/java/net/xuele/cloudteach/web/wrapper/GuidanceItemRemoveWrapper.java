package net.xuele.cloudteach.web.wrapper;

/**
 * GuidanceItemRemoveWrapper
 *
 * @author sunxh
 * @date 2015/7/21 0021
 */
public class GuidanceItemRemoveWrapper {

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
