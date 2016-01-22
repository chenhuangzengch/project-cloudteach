package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

/**
 * GuidanceItemPageRequest
 *
 * @author sunxh
 * @date 2015/7/9 0009
 */
public class GuidanceItemPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 6981584696169065378L;

    /**
     * 对应的课程ID
     */
    private String unitId;

    /**
     * 是否为收藏
     */
    private Integer isCollect;

    /**
     * 所属用户ID
     */
    private String userId;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GuidanceItemPageRequest{" +
                "unitId='" + unitId + '\'' +
                ", isCollect=" + isCollect +
                ", userId='" + userId + '\'' +
                '}';
    }
}
