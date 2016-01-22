package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class SynclassWorkAnswerCommNameViewDTO extends SynclassWorkAnswerCommentDTO implements Serializable {

    private static final long serialVersionUID = 8492848414728106327L;

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

    @Override
    public String toString() {
        return "SynclassWorkAnswerCommNameViewDTO{" +
                "headIcon='" + headIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
