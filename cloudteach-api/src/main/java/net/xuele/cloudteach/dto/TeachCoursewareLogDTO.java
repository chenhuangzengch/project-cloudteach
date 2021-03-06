package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * TeachCoursewareLogDTO
 * 授课课件打点数据DTO
 * 用于记录授课打点信息
 *
 * @author sunxh
 * @date 15/12/2
 */
public class TeachCoursewareLogDTO implements Serializable {

    private static final long serialVersionUID = -9091306857797744058L;

    /**
     * 授课记录ID
     */
    private String cclId;

    /**
     * 课件ID
     */
    private String cclCoursewaresId;

    /**
     * 用户ID
     */
    private String cclUserId;

    /**
     * 班级ID
     */
    private String cclClassId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 记录开始时间
     */
    private Date cclBegtime;

    /**
     * 记录结束时间
     * 课件1分钟轮询接口记录时间
     */
    private Date cclEndtime;

    /**
     * 来源类型（1:web,2:客户端）
     */
    private Integer cclFromType;

    public String getCclId() {
        return cclId;
    }

    public void setCclId(String cclId) {
        this.cclId = cclId;
    }

    public String getCclCoursewaresId() {
        return cclCoursewaresId;
    }

    public void setCclCoursewaresId(String cclCoursewaresId) {
        this.cclCoursewaresId = cclCoursewaresId;
    }

    public String getCclUserId() {
        return cclUserId;
    }

    public void setCclUserId(String cclUserId) {
        this.cclUserId = cclUserId;
    }

    public String getCclClassId() {
        return cclClassId;
    }

    public void setCclClassId(String cclClassId) {
        this.cclClassId = cclClassId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Date getCclBegtime() {
        return cclBegtime;
    }

    public void setCclBegtime(Date cclBegtime) {
        this.cclBegtime = cclBegtime;
    }

    public Date getCclEndtime() {
        return cclEndtime;
    }

    public void setCclEndtime(Date cclEndtime) {
        this.cclEndtime = cclEndtime;
    }

    public Integer getCclFromType() {
        return cclFromType;
    }

    public void setCclFromType(Integer cclFromType) {
        this.cclFromType = cclFromType;
    }

    @Override
    public String toString() {
        return "TeachCoursewareLogDTO{" +
                "cclId='" + cclId + '\'' +
                ", cclCoursewaresId='" + cclCoursewaresId + '\'' +
                ", cclUserId='" + cclUserId + '\'' +
                ", cclClassId='" + cclClassId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", cclBegtime=" + cclBegtime +
                ", cclEndtime=" + cclEndtime +
                ", cclFromType=" + cclFromType +
                '}';
    }
}
