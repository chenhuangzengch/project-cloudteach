package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * SRCloudDiskPageRequest
 * 云盘资源统计服务分页请求信息
 * @author duzg
 * @date 2015/8/6 0024
 */
public class SRCloudDiskPageRequest extends PageRequest implements Serializable{

    private static final long serialVersionUID = 8299207275515740162L;

    /**
     * 教师ID
     */
    private String userId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 区域ID
     */
    private String areaId;

    /**
     * 查询开始时间
     */
    private Date startDate;

    /**
     * 查询截止时间
     */
    private Date endDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SRCloudDiskPageRequest{" +
                "userId='" + userId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
