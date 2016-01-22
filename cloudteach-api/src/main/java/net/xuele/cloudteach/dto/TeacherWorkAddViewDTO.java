package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/7/25 0025.
 */
public class TeacherWorkAddViewDTO extends TeacherWorkDTO implements Serializable {

    private static final long serialVersionUID = -7273649322373743408L;

    private List<String> classList;
    private String classInfoJSON;
    private List<String> itemList;

    // ** 课外作业用:科目ID和科目名称 **
    private String subjectId;
    private String subjectName;

    // 区域分片统计用
    private String areaId;
    private String userName;
    private String userIcon;
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public String getClassInfoJSON() {
        return classInfoJSON;
    }

    public void setClassInfoJSON(String classInfoJSON) {
        this.classInfoJSON = classInfoJSON;
    }

    @Override
    public String toString() {
        return "TeacherWorkAddViewDTO{" +
                "areaId='" + areaId + '\'' +
                ", classList=" + classList +
                ", classInfoJSON='" + classInfoJSON + '\'' +
                ", itemList=" + itemList +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", userName='" + userName + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }
}
