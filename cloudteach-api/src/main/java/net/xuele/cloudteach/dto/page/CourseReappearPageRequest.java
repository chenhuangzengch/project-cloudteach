package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * CourseReappearPageRequest
 *
 * @author cm.wang
 * @date 2015/7/17 0018
 */
public class CourseReappearPageRequest extends PageRequest implements Serializable {


    //用户Id
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CourseReappearPageRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
