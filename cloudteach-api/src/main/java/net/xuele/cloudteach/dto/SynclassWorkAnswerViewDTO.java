package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

public class SynclassWorkAnswerViewDTO extends SynclassWorkStudentDTO implements Serializable {

    private static final long serialVersionUID = -6432354720299718194L;
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
     * 游戏附件练习信息
     */
    private List<SynclassWorkPlayViewDTO> playViewList;

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

    public List<SynclassWorkPlayViewDTO> getPlayViewList() {
        return playViewList;
    }

    public void setPlayViewList(List<SynclassWorkPlayViewDTO> playViewList) {
        this.playViewList = playViewList;
    }

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

    @Override
    public String toString() {
        return "SynclassWorkAnswerViewDTO{" +
                "userName='" + userName + '\'' +
                ", icon='" + icon + '\'' +
                ", className='" + className + '\'' +
                ", praiseTimes=" + praiseTimes +
                ", praiseStatus=" + praiseStatus +
                ", playViewList=" + playViewList +
                ", praiseViewList=" + praiseViewList +
                ", commentViewList=" + commentViewList +
                '}';
    }
}