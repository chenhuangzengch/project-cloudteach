package net.xuele.cloudteach.dto;


import java.io.Serializable;
import java.util.List;

/**
 * ExerciseWorkAnswerViewDTO
 * 电子作业回答VIEW
 * @author duzg
 * @date 2015/7/17 0002
 */
public class ExerciseWorkAnswerViewDTO extends ExerciseWorkAnswerDTO implements Serializable {

    private static final long serialVersionUID = -1837964867266968946L;
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
     * 班级入学年份
     */
    private Integer years;
    /**
     * 班级入学年份
     */
    private Integer codeSharing;

    /**
     * 点赞用户信息
     */
    private List<WorkAnswerPraiseViewDTO> praiseViewList;

    /**
     * 附件信息
     */
    private List<WorkAnswerFileViewDTO> fileViewList;

    /**
     * 评论信息
     */
    private List<WorkAnswerCommentViewDTO> commentViewList;

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

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getCodeSharing() {
        return codeSharing;
    }

    public void setCodeSharing(Integer codeSharing) {
        this.codeSharing = codeSharing;
    }

    public List<WorkAnswerPraiseViewDTO> getPraiseViewList() {
        return praiseViewList;
    }

    public void setPraiseViewList(List<WorkAnswerPraiseViewDTO> praiseViewList) {
        this.praiseViewList = praiseViewList;
    }

    public List<WorkAnswerFileViewDTO> getFileViewList() {
        return fileViewList;
    }

    public void setFileViewList(List<WorkAnswerFileViewDTO> fileViewList) {
        this.fileViewList = fileViewList;
    }

    public List<WorkAnswerCommentViewDTO> getCommentViewList() {
        return commentViewList;
    }

    public void setCommentViewList(List<WorkAnswerCommentViewDTO> commentViewList) {
        this.commentViewList = commentViewList;
    }

    @Override
    public String toString() {
        return "ExerciseWorkAnswerViewDTO{" +
                "userName='" + userName + '\'' +
                ", icon='" + icon + '\'' +
                ", className='" + className + '\'' +
                ", years=" + years +
                ", codeSharing=" + codeSharing +
                ", praiseViewList=" + praiseViewList +
                ", fileViewList=" + fileViewList +
                ", commentViewList=" + commentViewList +
                '}';
    }
}