package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * GuidanceItemStatisticsDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceItemStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 3314398215137200743L;

    /**
     * 预习项ID
     */
    private String guidanceId;

    /**
     * 发布次数
     */
    private Integer releases;

    /**
     * 学校id--用于数据库分片存储
     */
    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_STATISTICS] 的属性 预习项ID
     */
    public String getGuidanceId() {
        return guidanceId;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_STATISTICS]的属性预习项ID
     */
    public void setGuidanceId(String guidanceId) {
        this.guidanceId = guidanceId == null ? null : guidanceId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_ITEM_STATISTICS] 的属性 发布次数
     */
    public Integer getReleases() {
        return releases;
    }

    /**
     * 设置[CT_GUIDANCE_ITEM_STATISTICS]的属性发布次数
     */
    public void setReleases(Integer releases) {
        this.releases = releases;
    }

    @Override
    public String toString() {
        return "GuidanceItemStatisticsDTO{" +
                "guidanceId='" + guidanceId + '\'' +
                ", releases=" + releases +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}