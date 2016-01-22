package net.xuele.cloudteach.view;

/**
 * Created by hujx on 2015/12/8 0008.
 */
public class LearningInfoWorkView {

    private String workId;
    /* 作业描述 */
    private String context;

    /**
     * 班级信息(jason格式)包括班级编号，班级名称
     */
    private String workClassJson;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkClassJson() {
        return workClassJson;
    }

    public void setWorkClassJson(String workClassJson) {
        this.workClassJson = workClassJson;
    }
}
