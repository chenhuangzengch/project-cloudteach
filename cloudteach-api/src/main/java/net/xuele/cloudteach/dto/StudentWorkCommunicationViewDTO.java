package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/7/22 0022.
 */
public class StudentWorkCommunicationViewDTO implements Serializable {

    private static final long serialVersionUID = -8055512985338091923L;

    // 是否为学生本人
    private int me;
    // 学生是否对该条记录点过赞
    private int praiseStatus;
    private String answerId;
    private String userId;
    private String studentHeadIcon;
    private String studentName;
    private String classInfo;
    private Date subTime;
    private String answerContext;
    private int score;
    private int sysScore;
    private int praiseCount;
    private String scorecontext;

    // 预习，电子，语音作业
    private List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList;
    // 提分宝
    private List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList;
    // 同步课堂
    private List<SynclassWorkGamePlayInfoViewDTO> synclassWorkGamePlayInfoViewDTOList;
    // 评论信息
    private List<WorkAnswerCommentViewDTO> workAnswerCommentViewDTOList;

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public List<MagicWorkAnswerFilesDTO> getMagicWorkAnswerFilesDTOList() {
        return magicWorkAnswerFilesDTOList;
    }

    public void setMagicWorkAnswerFilesDTOList(List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList) {
        this.magicWorkAnswerFilesDTOList = magicWorkAnswerFilesDTOList;
    }

    public int getMe() {
        return me;
    }

    public void setMe(int me) {
        this.me = me;
    }

    public int getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(int praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScorecontext() {
        return scorecontext;
    }

    public void setScorecontext(String scorecontext) {
        this.scorecontext = scorecontext;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStudentHeadIcon() {
        return studentHeadIcon;
    }

    public void setStudentHeadIcon(String studentHeadIcon) {
        this.studentHeadIcon = studentHeadIcon;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public List<SynclassWorkGamePlayInfoViewDTO> getSynclassWorkGamePlayInfoViewDTOList() {
        return synclassWorkGamePlayInfoViewDTOList;
    }

    public void setSynclassWorkGamePlayInfoViewDTOList(List<SynclassWorkGamePlayInfoViewDTO> synclassWorkGamePlayInfoViewDTOList) {
        this.synclassWorkGamePlayInfoViewDTOList = synclassWorkGamePlayInfoViewDTOList;
    }

    public List<TeacherWorkItemAnswerFileDTO> getTeacherWorkItemAnswerFileDTOList() {
        return teacherWorkItemAnswerFileDTOList;
    }

    public void setTeacherWorkItemAnswerFileDTOList(List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList) {
        this.teacherWorkItemAnswerFileDTOList = teacherWorkItemAnswerFileDTOList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<WorkAnswerCommentViewDTO> getWorkAnswerCommentViewDTOList() {
        return workAnswerCommentViewDTOList;
    }

    public void setWorkAnswerCommentViewDTOList(List<WorkAnswerCommentViewDTO> workAnswerCommentViewDTOList) {
        this.workAnswerCommentViewDTOList = workAnswerCommentViewDTOList;
    }

    public int getSysScore() {
        return sysScore;
    }

    public void setSysScore(int sysScore) {
        this.sysScore = sysScore;
    }

    @Override
    public String toString() {
        return "StudentWorkCommunicationViewDTO{" +
                "me=" + me +
                ", praiseStatus=" + praiseStatus +
                ", answerId='" + answerId + '\'' +
                ", userId='" + userId + '\'' +
                ", studentHeadIcon='" + studentHeadIcon + '\'' +
                ", studentName='" + studentName + '\'' +
                ", classInfo='" + classInfo + '\'' +
                ", subTime=" + subTime +
                ", answerContext='" + answerContext + '\'' +
                ", score=" + score +
                ", sysScore=" + sysScore +
                ", praiseCount=" + praiseCount +
                ", scorecontext='" + scorecontext + '\'' +
                ", teacherWorkItemAnswerFileDTOList=" + teacherWorkItemAnswerFileDTOList +
                ", magicWorkAnswerFilesDTOList=" + magicWorkAnswerFilesDTOList +
                ", synclassWorkGamePlayInfoViewDTOList=" + synclassWorkGamePlayInfoViewDTOList +
                ", workAnswerCommentViewDTOList=" + workAnswerCommentViewDTOList +
                '}';
    }
}
