package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * 用于包装用户分享操作需传递的用户名、学校、地区信息
 * Created by sunxinhe on 15/7/23.
 */
public class UserShareInfoDTO implements Serializable {
    private static final long serialVersionUID = -7789555884977159794L;

    /**
     * 题目ID
     */
    private String itemId;

    /**
     * 分享类型
     */
    private Integer shareType;

    /**
     * 分享用户编号
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 学校id
     */
    private String schoolId;


    /**
     * 学校名称
     *
     * @return
     */
    private String schoolName;

    /**
     * 地区编号
     */
    private String areaId;

    /**
     * 地区名称
     */
    private String areaName;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "UserShareInfoDTO{" +
                "itemId='" + itemId + '\'' +
                ", shareType=" + shareType +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
