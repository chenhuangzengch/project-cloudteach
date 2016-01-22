package net.xuele.cloudteach.constant;

/**
 * TeachCoursewareLogEnum
 *
 * @author sunxh
 * @date 15/12/3
 */
public enum TeachCoursewareLogFromEnum {

    FROM_WEB(1, "来自web浏览器"),
    FROM_PC(2, "来自客户端");

    /**
     * 类型值
     */
    private Integer value;

    /**
     * 描述
     */
    private String description;

    TeachCoursewareLogFromEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
