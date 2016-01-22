package net.xuele.cloudteach.domain;

import java.util.Date;

public class QQuestFeedback {
    /**
     * 编号
     */
    private String fbId;

    /**
     * 反馈题目
     */
    private String queId;

    /**
     * 题目类型
     */
    private Integer qType;

    /**
     * 课本ID
     */
    private String bookId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 反馈类型:1,2,3,4,9-其他
     */
    private Integer fbType;

    /**
     * 反馈答案ID:填空题有值
     */
    private String aId;

    /**
     * 反馈人ID
     */
    private String userId;

    /**
     * 反馈人名字
     */
    private String userName;

    /**
     * 反馈时间
     */
    private Date fbTime;

    /**
     * 反馈处理人ID
     */
    private String handleUserId;

    /**
     * 反馈处理人名字
     */
    private String handleUserName;

    /**
     * 反馈处理时间
     */
    private Date handleTime;

    /**
     * 处理状态
     */
    private Integer status;

    /**
     * 反馈内容:反馈类型为9时有值
     */
    private String fbContent;

    /**
     * 用户回答答案:填空题/选择题有值
     */
    private String userAnswer;

    public Integer getqType() {
        return qType;
    }

    public void setqType(Integer qType) {
        this.qType = qType;
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 编号
     */
    public String getFbId() {
        return fbId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性编号
     */
    public void setFbId(String fbId) {
        this.fbId = fbId == null ? null : fbId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈题目
     */
    public String getQueId() {
        return queId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈题目
     */
    public void setQueId(String queId) {
        this.queId = queId == null ? null : queId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 课本ID
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性课本ID
     */
    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈类型:1,2,3,4,9-其他
     */
    public Integer getFbType() {
        return fbType;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈类型:1,2,3,4,9-其他
     */
    public void setFbType(Integer fbType) {
        this.fbType = fbType;
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈答案ID:填空题有值
     */
    public String getaId() {
        return aId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈答案ID:填空题有值
     */
    public void setaId(String aId) {
        this.aId = aId == null ? null : aId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈人ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈人ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈人名字
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈人名字
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈时间
     */
    public Date getFbTime() {
        return fbTime;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈时间
     */
    public void setFbTime(Date fbTime) {
        this.fbTime = fbTime;
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈处理人ID
     */
    public String getHandleUserId() {
        return handleUserId;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈处理人ID
     */
    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId == null ? null : handleUserId.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈处理人名字
     */
    public String getHandleUserName() {
        return handleUserName;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈处理人名字
     */
    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName == null ? null : handleUserName.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈处理时间
     */
    public Date getHandleTime() {
        return handleTime;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈处理时间
     */
    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 处理状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性处理状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 反馈内容:反馈类型为9时有值
     */
    public String getFbContent() {
        return fbContent;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性反馈内容:反馈类型为9时有值
     */
    public void setFbContent(String fbContent) {
        this.fbContent = fbContent == null ? null : fbContent.trim();
    }

    /**
     * 获取 [Q_QUEST_FEEDBACK] 的属性 用户回答答案:填空题/选择题有值
     */
    public String getUserAnswer() {
        return userAnswer;
    }

    /**
     * 设置[Q_QUEST_FEEDBACK]的属性用户回答答案:填空题/选择题有值
     */
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer == null ? null : userAnswer.trim();
    }
}