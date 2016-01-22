package net.xuele.cloudteach.constant;

/**
 * DiskFileTypeEnum
 * 云盘文件类型枚举
 * 云盘文件资源：1其他,2,教案3学案4课件5习题6课程素材
 *
 * @author sunxh
 * @date 15/12/15
 */
public enum DiskFileTypeEnum {

    ALL(0, "全部"),

    OTHER(1, "其他"),

    TEACHPLAN(2, "教案"),

    LEARNPLAN(3, "学案"),

    COURSEWARE(4, "课件"),

    EXERCISE(5, "习题"),

    MATERIAL(6, "课程素材");

    DiskFileTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
        this.description = "";
    }

    DiskFileTypeEnum(Integer value, String name, String description) {
        this.value = value;
        this.name = name;
        this.description = description;
    }

    /**
     * 类型代码
     */
    private Integer value;

    /**
     * 类型名称：对应页面上显示的筛选类型
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
