package net.xuele.cloudteach.constant;

/**
 * CoursearePackStatusEnum
 * 课件打包状态枚举
 *
 * @author sunxh
 * @date 15/11/17
 */
public enum CoursearePackStatusEnum {


    /**
     * 未打包
     */
    UNPACK(0, "未打包"),

    /**
     * 排队中
     */
    INQUEUE(1, "排队中"),

    /**
     * 打包中
     */
    PACKING(2, "打包中"),

    /**
     * 打包失败
     */
    FAILED(3, "打包失败"),

    /**
     * 打包成功
     */
    SUCCESS(4, "打包成功"),

    /**
     * 文件过期
     */
    OUTDATED(5, "文件过期"),

    /**
     * 文件已失效
     */
    INVALID(6, "文件已失效");

    private Integer status;

    private String description;

    CoursearePackStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    CoursearePackStatusEnum(Integer status) {
        this.status = status;
        this.description = "";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
