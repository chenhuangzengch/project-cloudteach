package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.ClassFeedbackDTO;

import java.util.List;

/**
 * ClassFeedbackWrapper
 * 随堂反馈数据包装类
 * @author duzg
 * @date 15/10/14
 */
public class ClassFeedbackWrapper {
    /**
     * 处理状态：1，成功；-1，失败。
     */
    private String state;

    private List<ClassFeedbackDTO> classFBList;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ClassFeedbackDTO> getClassFBList() {
        return classFBList;
    }

    public void setClassFBList(List<ClassFeedbackDTO> classFBList) {
        this.classFBList = classFBList;
    }

    @Override
    public String toString() {
        return "ClassFeedbackWrapper{" +
                "state='" + state + '\'' +
                ", classFBList=" + classFBList +
                '}';
    }
}