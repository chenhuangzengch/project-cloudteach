package net.xuele.cloudteach.web.wrapper;

/**
 * FlashResponse
 *
 * @author sunxh
 * @date 2015/7/22 0022
 */
public class FlashResponse<T> {
    private String state;

    private String id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
