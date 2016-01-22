package net.xuele.cloudteach.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * WorkAnswerViewDTO
 * 作业题目回答VIEW
 * @author duzg
 * @date 2015/7/17 0002
 */
public class WorkAnswerViewDTO implements Serializable {

    private static final long serialVersionUID = -1837964867266968946L;

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
    private List<WorkAnswerFileViewDTO> fileViewList;
//
//    /**
//     * 同步课堂游戏附件练习信息
//     */
//    private List<SynclassWorkPlayViewDTO> playViewList;

    /**
     * 点赞用户信息
     */
    private List<WorkAnswerPraiseViewDTO> praiseViewList;

    /**
     * 评论信息
     */
    private List<WorkAnswerCommentViewDTO> commentViewList;

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

    public String getScorecontext() {
        return scorecontext;
    }

    public void setScorecontext(String scorecontext) {
        this.scorecontext = scorecontext;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
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

//    public List<SynclassWorkPlayViewDTO> getPlayViewList() {
//        return playViewList;
//    }
//
//    public void setPlayViewList(List<SynclassWorkPlayViewDTO> playViewList) {
//        this.playViewList = playViewList;
//    }

    public List<WorkAnswerPraiseViewDTO> getPraiseViewList() {
        return praiseViewList;
    }

    public void setPraiseViewList(List<WorkAnswerPraiseViewDTO> praiseViewList) {
        this.praiseViewList = praiseViewList;
    }

    public List<WorkAnswerCommentViewDTO> getCommentViewList() {
        return commentViewList;
    }

    public void setCommentViewList(List<WorkAnswerCommentViewDTO> commentViewList) {
        this.commentViewList = commentViewList;
    }

    public List<WorkAnswerFileViewDTO> getFileViewList() {
        return fileViewList;
    }

    public void setFileViewList(List<WorkAnswerFileViewDTO> fileViewList) {
        this.fileViewList = fileViewList;
    }

    @Override
    public String toString() {
        return "WorkAnswerViewDTO{" +
                "answerId='" + answerId + '\'' +
                ", workId='" + workId + '\'' +
                ", workItemId='" + workItemId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", icon='" + icon + '\'' +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", context='" + context + '\'' +
                ", score=" + score +
                ", scorecontext='" + scorecontext + '\'' +
                ", challengeId='" + challengeId + '\'' +
                ", subStatus=" + subStatus +
                ", correctStatus=" + correctStatus +
                ", subTime=" + subTime +
                ", correctTime=" + correctTime +
                ", status=" + status +
                ", praiseTimes=" + praiseTimes +
                ", praiseStatus=" + praiseStatus +
                ", fileViewList=" + fileViewList +
                ", praiseViewList=" + praiseViewList +
                ", commentViewList=" + commentViewList +
                '}';
    }
}