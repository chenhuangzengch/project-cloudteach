package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.*;

import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/8/5 0005.
 */
public class StudentSynclassWorkCommunicationWrapper {

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

    // 同步课堂
    private List<SynclassWorkGamePlayInfoViewDTO> synclassWorkGamePlayInfoViewDTOList;
    private List<WorkAnswerCommentViewDTO> synclassWorkAnswerCommNameViewDTOList;

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

    public List<WorkAnswerCommentViewDTO> getSynclassWorkAnswerCommNameViewDTOList() {
        return synclassWorkAnswerCommNameViewDTOList;
    }

    public void setSynclassWorkAnswerCommNameViewDTOList(List<WorkAnswerCommentViewDTO> synclassWorkAnswerCommNameViewDTOList) {
        this.synclassWorkAnswerCommNameViewDTOList = synclassWorkAnswerCommNameViewDTOList;
    }

    public List<SynclassWorkGamePlayInfoViewDTO> getSynclassWorkGamePlayInfoViewDTOList() {
        return synclassWorkGamePlayInfoViewDTOList;
    }

    public void setSynclassWorkGamePlayInfoViewDTOList(List<SynclassWorkGamePlayInfoViewDTO> synclassWorkGamePlayInfoViewDTOList) {
        this.synclassWorkGamePlayInfoViewDTOList = synclassWorkGamePlayInfoViewDTOList;
    }
}
