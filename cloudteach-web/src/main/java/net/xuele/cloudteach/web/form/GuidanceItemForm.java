package net.xuele.cloudteach.web.form;

/**
 * GuidanceItemForm
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceItemForm {
    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String userId;

    /**
     * 课程编号
     */
    private String unitId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}