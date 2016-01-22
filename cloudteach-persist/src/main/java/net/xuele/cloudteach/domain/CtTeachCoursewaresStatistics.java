package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtTeachCoursewaresStatistics {
    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 发布次数
     */
    private Integer releases;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 课件打包状态：0：未打包；1：排队中；2：打包中；3：打包失败；4：打包成功；5：过期(课件内容已更新) 6：中转站文件失效
     * 默认为0
     */
    private Integer packStatus;

    /**
     * 上次打包对应的更新时间
     * <p/>
     * 系统会根据该属性判断当前包是否过期
     */
    private Date packLastUpdate;

    /**
     * 包有效期限
     * <p/>
     * 系统会根据该属性判断当前包文件是否失效
     */
    private Date packExpDate;

    /**
     * 包文件key
     */
    private String packUri;

    /**
     * 获取 [CT_TEACH_COURSEWARES_STATISTICS] 的属性 授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_STATISTICS]的属性授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_STATISTICS] 的属性 发布次数
     */
    public Integer getReleases() {
        return releases;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_STATISTICS]的属性发布次数
     */
    public void setReleases(Integer releases) {
        this.releases = releases;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public Integer getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(Integer packStatus) {
        this.packStatus = packStatus;
    }

    public Date getPackLastUpdate() {
        return packLastUpdate;
    }

    public void setPackLastUpdate(Date packLastUpdate) {
        this.packLastUpdate = packLastUpdate;
    }

    public Date getPackExpDate() {
        return packExpDate;
    }

    public void setPackExpDate(Date packExpDate) {
        this.packExpDate = packExpDate;
    }

    public String getPackUri() {
        return packUri;
    }

    public void setPackUri(String packUri) {
        this.packUri = packUri;
    }
}