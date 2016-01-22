package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtMagicWorkAnswerComment;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/30 0030.
 */
public class MagicWorkAnswerCommNameView extends CtMagicWorkAnswerComment {

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
}
