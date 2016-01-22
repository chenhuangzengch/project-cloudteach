package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * BlackboardPublishPageRequest
 *
 * @author cm.wang
 * @date 2015/7/14 0015
 */
public class BlackboardPublishPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 6981584696169065379L;

    //用户Id
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BlackboardPublishPageRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
