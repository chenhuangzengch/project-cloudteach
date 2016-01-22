package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtSynclassWorkStudent;

import java.util.Date;
import java.util.List;

public class SynclassWorkAnswerView extends CtSynclassWorkStudent {
    /**
     * 学生用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String icon;
    /**
     * 学生班级名
     */
    private String className;
    /**
     * 点赞次数
     */
    private Integer praiseTimes;
    /**
     * 点赞状态（0未点赞 1已点赞）
     */
    private Integer praiseStatus;
    /**
     * 游戏附件联系信息
     */
    private List<SynclassWorkPlayView> playViewList;

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public List<SynclassWorkPlayView> getPlayViewList() {
        return playViewList;
    }

    public void setPlayViewList(List<SynclassWorkPlayView> playViewList) {
        this.playViewList = playViewList;
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
}