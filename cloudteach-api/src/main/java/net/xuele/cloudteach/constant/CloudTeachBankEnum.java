package net.xuele.cloudteach.constant;

import java.util.HashMap;

/**
 * CloudTeachBankEnum
 * 云教学题库相关的枚举
 *
 * @author sunxh
 * @date 15/7/25
 */

public enum CloudTeachBankEnum {

    /**
     * 题目类型：1预习 4电子作业 7口语作业1
     */
    ITEM_TYPE_GUIDANCE(1, "预习题目"),
    ITEM_TYPE_EXERCISE(4, "电子作业题目"),
    ITEM_TYPE_VOICE(7, "口语作业题目"),
    ITEM_TYPE_EXTRA(8, "课外作业"),

    ITEM_FROM_COLLECT(1, "来自收藏"),
    ITEM_FROM_MINE(0, "我的题目"),


    /**
     * 附件类型：1教师题目，3教师作业题目
     */
    FILE_TYPE_TEACHERQUE(1, "教师题目"),
    FILE_TYPE_TEACHERWORKQUE(3, "教师作业题目"),;

    /**
     * 代码
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;

    CloudTeachBankEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    private static HashMap<Integer, CloudTeachBankEnum> mapper = new HashMap<>();

    static {
        for (CloudTeachBankEnum etm : values()) {
            mapper.put(etm.code, etm);
        }
    }

    public static CloudTeachBankEnum get(Integer code) {
        return mapper.get(code);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
