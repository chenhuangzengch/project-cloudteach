package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtMagicWork;

/**
 * MagicWorkDetailView
 * 作业管理-提分宝作业
 * @author duzg
 * @date 2015/7/17 0002
 */
public class MagicWorkDetailView extends CtMagicWork {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 用户职务
     */
    private String positionName;

    /**
     * 课程名
     */
    private String unitName;

    /**
     * 科目号
     */
    private String subjectId;
    /**
     * 科目名
     */
    private String subjectName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}