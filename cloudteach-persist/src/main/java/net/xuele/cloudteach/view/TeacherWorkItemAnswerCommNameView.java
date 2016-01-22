package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtTeacherWorkItemAnswerComment;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/30 0030.
 */
public class TeacherWorkItemAnswerCommNameView extends CtTeacherWorkItemAnswerComment {


    private String headIcon;

    private String userName;

    private String positionName;

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
