package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtCoursewareClassLog {
    private String cclId;

    private String cclCoursewaresId;

    private String cclUserId;

    private String cclClassId;

    private String schoolId;

    private Date cclBegtime;

    /**
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
        this.cclId = cclId == null ? null : cclId.trim();
    }

    public String getCclCoursewaresId() {
        return cclCoursewaresId;
    }

    public void setCclCoursewaresId(String cclCoursewaresId) {
        this.cclCoursewaresId = cclCoursewaresId == null ? null : cclCoursewaresId.trim();
    }

    public String getCclUserId() {
        return cclUserId;
    }

    public void setCclUserId(String cclUserId) {
        this.cclUserId = cclUserId == null ? null : cclUserId.trim();
    }

    public String getCclClassId() {
        return cclClassId;
    }

    public void setCclClassId(String cclClassId) {
        this.cclClassId = cclClassId == null ? null : cclClassId.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public Date getCclBegtime() {
        return cclBegtime;
    }

    public void setCclBegtime(Date cclBegtime) {
        this.cclBegtime = cclBegtime;
    }

    /**
     * 获取 [CT_COURSEWARE_CLASS_LOG] 的属性 课件1分钟轮询接口记录时间
     */
    public Date getCclEndtime() {
        return cclEndtime;
    }

    /**
     * 设置[CT_COURSEWARE_CLASS_LOG]的属性课件1分钟轮询接口记录时间
     */
    public void setCclEndtime(Date cclEndtime) {
        this.cclEndtime = cclEndtime;
    }

    public Integer getCclFromType() {
        return cclFromType;
    }

    public void setCclFromType(Integer cclFromType) {
        this.cclFromType = cclFromType;
    }
}