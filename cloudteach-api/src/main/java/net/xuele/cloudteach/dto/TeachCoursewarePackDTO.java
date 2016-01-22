package net.xuele.cloudteach.dto;

import net.xuele.cloudteach.constant.CoursearePackStatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * TeachCoursewarePackDTO
 * 用于包装课件打包状态的DTO
 *
 * @author sunxh
 * @date 15/11/17
 */
public class TeachCoursewarePackDTO implements Serializable {

    private static final long serialVersionUID = -2000552508517469497L;

    /**
     * 授课课件ID
     */
    private String coursewaresId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 用户ID
     */
    private String userId;

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
     * 文件名
     */
    private String name;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 当前排队的任务数
     */
    private Integer queueDepth;

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }

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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQueueDepth() {
        return queueDepth;
    }

    public void setQueueDepth(Integer queueDepth) {
        this.queueDepth = queueDepth;
    }

    @Override
    public String toString() {
        return "TeachCoursewarePackDTO{" +
                "coursewaresId='" + coursewaresId + '\'' +
                ", packStatus=" + packStatus +
                ", packLastUpdate=" + packLastUpdate +
                ", packExpDate=" + packExpDate +
                ", packUri='" + packUri + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                ", queueDepth=" + queueDepth +
                '}';
    }
}
