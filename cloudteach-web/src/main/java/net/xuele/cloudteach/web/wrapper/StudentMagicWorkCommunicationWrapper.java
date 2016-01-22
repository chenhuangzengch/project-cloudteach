package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.*;

import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/7/22 0022.
 */
public class StudentMagicWorkCommunicationWrapper {

    private int me;
    private int praiseStatus;
    private String answerId;
    private String icon;
    private String studentName;
    private String classInfo;
    private Date subTime;
    private String answerContext;
    private int score;
    private int praiseCount;
    private String scorecontext;
    private int sysScore;

    // 提分宝
    private List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList;
    private List<WorkAnswerCommentViewDTO> magicWorkAnswerCommNameViewDTOList;

    public String getScorecontext() {
        return scorecontext;
    }

    public void setScorecontext(String scorecontext) {
        this.scorecontext = scorecontext;
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

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public int getSysScore() {
        return sysScore;
    }

    public void setSysScore(int sysScore) {
        this.sysScore = sysScore;
    }

    public List<WorkAnswerCommentViewDTO> getMagicWorkAnswerCommNameViewDTOList() {
        return magicWorkAnswerCommNameViewDTOList;
    }

    public void setMagicWorkAnswerCommNameViewDTOList(List<WorkAnswerCommentViewDTO> magicWorkAnswerCommNameViewDTOList) {
        this.magicWorkAnswerCommNameViewDTOList = magicWorkAnswerCommNameViewDTOList;
    }

    public List<MagicWorkAnswerFilesDTO> getMagicWorkAnswerFilesDTOList() {
        return magicWorkAnswerFilesDTOList;
    }

    public void setMagicWorkAnswerFilesDTOList(List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList) {
        this.magicWorkAnswerFilesDTOList = magicWorkAnswerFilesDTOList;
    }
}
