package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * 包装学生提交的作业详情，包括描述和附件
 * Created by sunxh on 15/8/21.
 */
public class FLStuWorkDetailWrapper {

    /**
     * 请求响应状态
     */
    private String state;

    /**
     * 回答描述
     */
    private String context;

    /**
     * 附件列表
     */
    private List<FLFileReferWrapper> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContext() {
        return context == null ? "" : context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<FLFileReferWrapper> getList() {
        return list;
    }

    public void setList(List<FLFileReferWrapper> list) {
        this.list = list;
    }
}
