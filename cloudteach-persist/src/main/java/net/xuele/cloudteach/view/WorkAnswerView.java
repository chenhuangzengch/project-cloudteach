package net.xuele.cloudteach.view;

import java.util.Date;
import java.util.List;

public class WorkAnswerView {
    /**
     * 作业学生回答ID
     */
    private String answerId;

    /**
     * 作业ID
     */
    private String workId;

    /**
     * 预习作业预习项ID
     */
    private String workItemId;

    /**
     * 学生用户ID
     */
    private String userId;

    /**
     * 学生用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String icon;


    /**
     * 学生班级ID
     */
    private String classId;

    /**
     * 学生班级名
     */
    private String className;

    /**
     * 回答描述
     */
    private String context;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 系统自动评分
     */
    private Integer sysScore;

    /**
     * 成绩描述
     */
    private String scorecontext;

    /**
     * 挑战记录ID
     */
    private String challengeId;
    /**
     * 挑战次数
     */
    private int challengeTimes;

    /**
     * 学生提交状态
     */
    private Integer subStatus;

    /**
     * 批改状态
     */
    private Integer correctStatus;

    /**
     * 提交时间
     */
    private Date subTime;

    /**
     * 批改时间
     */
    private Date correctTime;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 点赞状态（0未点赞 1已点赞）
     */
    private Integer praiseStatus;

    /**
     * 作业回答附件
     */
    private List<WorkAnswerFileView> fileViewList;

    /**
     * 点赞用户信息
     */
    private List<WorkAnswerPraiseView> praiseViewList;

    /**
     * 评论信息
     */
    private List<WorkAnswerCommentView> commentViewList;

    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSysScore() {
        return sysScore;
    }

    public void setSysScore(Integer sysScore) {
        this.sysScore = sysScore;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getScorecontext() {
        return scorecontext;
    }

    public void setScorecontext(String scorecontext) {
        this.scorecontext = scorecontext;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public Integer getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public Date getCorrectTime() {
        return correctTime;
    }

    public void setCorrectTime(Date correctTime) {
        this.correctTime = correctTime;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public List<WorkAnswerPraiseView> getPraiseViewList() {
        return praiseViewList;
    }

    public void setPraiseViewList(List<WorkAnswerPraiseView> praiseViewList) {
        this.praiseViewList = praiseViewList;
    }

    public List<WorkAnswerCommentView> getCommentViewList() {
        return commentViewList;
    }

    public void setCommentViewList(List<WorkAnswerCommentView> commentViewList) {
        this.commentViewList = commentViewList;
    }

    public List<WorkAnswerFileView> getFileViewList() {
        return fileViewList;
    }

    public void setFileViewList(List<WorkAnswerFileView> fileViewList) {
        this.fileViewList = fileViewList;
    }

    public int getChallengeTimes() {
        return challengeTimes;
    }

    public void setChallengeTimes(int challengeTimes) {
        this.challengeTimes = challengeTimes;
    }
}