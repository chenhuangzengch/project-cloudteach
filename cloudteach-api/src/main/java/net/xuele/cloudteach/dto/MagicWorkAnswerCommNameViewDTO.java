package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/30 0030.
 */
public class MagicWorkAnswerCommNameViewDTO extends MagicWorkAnswerCommentDTO implements Serializable {

    private static final long serialVersionUID = -3123302003261204141L;

    private String headIcon;

    private String userName;

    private String positionName;

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

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    @Override
    public String toString() {
        return "MagicWorkAnswerCommNameViewDTO{" +
                "headIcon='" + headIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
