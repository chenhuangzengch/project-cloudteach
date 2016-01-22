package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * CoursewareClassCFBLogDTO
 * 课件学情打点--随堂反馈内容
 *
 * @author duzg
 * @date on 2015/12/02.
 */
public class CoursewareClassCFBLogDTO implements Serializable {

    private static final long serialVersionUID = 3795343772783156947L;

    private String userId;

    private String userName;

    private String userIcon;

    private String fileName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    @Override
    public String toString() {
        return "CoursewareClassCFBLogDTO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
