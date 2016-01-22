package net.xuele.cloudteach.web.wrapper;

public class MagicQuestionWrapper {
    /**
     * 题目id
     */
    private String queId;

    /**
     * 题库id
     */
    private String bankId;

    /**
     * 分类号(1选择题 2填空题 3判断题...)
     */
    private Integer queTypeCode;

    /**
     * 题目类型
     */
    private String queType;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;

    /**
     * 衍生题顺序（从1开始，原题=0）（套数）
     */
    private Integer orderNum;

    /**
     * 原题id
     */
    private String originalQuestionId;

    /**
     * ID
     */
    private String videoId;

    /**
     * 视频文件key
     */
    private String videoFileKey;

    /**
     * 文件后缀
     */
    private String videoSuffix;

    /**
     * 视频描述
     */
    private String videoDesc;

    public String getOriginalQuestionId() {
        return originalQuestionId;
    }

    public void setOriginalQuestionId(String originalQuestionId) {
        this.originalQuestionId = originalQuestionId;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Integer getQueTypeCode() {
        return queTypeCode;
    }

    public void setQueTypeCode(Integer queTypeCode) {
        this.queTypeCode = queTypeCode;
    }

    public String getQueType() {
        return queType;
    }

    public void setQueType(String queType) {
        this.queType = queType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoFileKey() {
        return videoFileKey;
    }

    public void setVideoFileKey(String videoFileKey) {
        this.videoFileKey = videoFileKey;
    }

    public String getVideoSuffix() {
        return videoSuffix;
    }

    public void setVideoSuffix(String videoSuffix) {
        this.videoSuffix = videoSuffix;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }
}