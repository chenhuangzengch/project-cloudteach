package net.xuele.cloudteach.web.form;

/**
 * Created by hujx on 2015/8/4 0004.
 */
public class workSubmitForm {

    //作业ID
    private String workId;
    //作业回答描述
    private String answerContext;
    //作业添加附件JSON
    private String workFilesJSON;

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkFilesJSON() {
        return workFilesJSON;
    }

    public void setWorkFilesJSON(String workFilesJSON) {
        this.workFilesJSON = workFilesJSON;
    }
}
