package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * TeachCoursewareLogWrapper
 * 授课课件打点数据wrapper
 * 用于封装回传给前端（目前是flash）的授课打点数据
 *
 * @author sunxh
 * @date 15/12/2
 */
public class TeachCoursewareLogWrapper {

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
        return "TeachCoursewareLogWrapper{" +
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
