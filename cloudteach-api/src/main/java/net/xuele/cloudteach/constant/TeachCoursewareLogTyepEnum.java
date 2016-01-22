package net.xuele.cloudteach.constant;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * TeachCoursewareLogEnum
 * 1随堂 2点名 3讲作业 4截图
 *
 * @author sunxh
 * @date 15/12/4
 */
public enum TeachCoursewareLogTyepEnum {

    SUITANG(1, "随堂"),
    DIANMING(2, "点名"),
    JIANGZUOYE(3, "讲作业"),
    JIETU(4, "截图");

    /**
     * 类型值
     */
    private Integer value;

    /**
     * 描述
     */
    private String context;

    TeachCoursewareLogTyepEnum(Integer value, String context) {
        this.value = value;
        this.context = context;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public static String getContext(Integer value) {
        String context = "";
        TeachCoursewareLogTyepEnum logTyepEnum = preMapper.get(value);
        if (logTyepEnum != null) {
            context = logTyepEnum.getContext();
        }
        return context;
    }

    private static HashMap<Integer, TeachCoursewareLogTyepEnum> preMapper = new HashMap<>();

    static {
        for (TeachCoursewareLogTyepEnum logTyepEnum : EnumSet.allOf(TeachCoursewareLogTyepEnum.class)) {
            preMapper.put(logTyepEnum.value, logTyepEnum);
        }
    }
}
