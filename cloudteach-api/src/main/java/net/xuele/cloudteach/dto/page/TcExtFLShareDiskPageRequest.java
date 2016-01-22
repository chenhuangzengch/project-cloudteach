package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;

public class TcExtFLShareDiskPageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 643098695438387815L;
    /**
     * 教师ID
     */
    private String userId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 地区编号
     */
    private String areaId;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 文件后缀类型 1其他,2ppt,3word,4视频,5录音,6图片
     */
    private Integer extType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }

    @Override
    public String toString() {
        return "TcExtFLShareDiskPageRequest{" +
                "userId='" + userId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", extType=" + extType +
                '}';
    }
}
