package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * 评论人姓名
 * Created by hujx on 2015/7/22 0022.
 */
public class GuidanceWorkItemAnswerCommNameViewDTO extends GuidanceWorkItemAnswerCommentDTO implements Serializable {

    private static final long serialVersionUID = 4553776171790499905L;
    private String headIcon;

    private String userName;

    private String positionName;

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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "GuidanceWorkItemAnswerCommNameViewDTO{" +
                "headIcon='" + headIcon + '\'' +
                ", userName='" + userName + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
