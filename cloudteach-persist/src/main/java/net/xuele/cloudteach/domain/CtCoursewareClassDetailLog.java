package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtCoursewareClassDetailLog {
    private String ccdlId;

    private String cclId;

    /**
     * 1随堂 2点名 3讲作业 4截图
     */
    private Integer ccdlType;

    /**
     * 1随堂 2点名 3讲作业 4截图
     */
    private String ccdlContext;

    private Date ccdlBegtime;

    private Date ccdlEndtime;

    private String ccdlContent1;

    private String ccdlContent2;

    private String ccdlContent3;

    private String schoolId;

    public String getCcdlId() {
        return ccdlId;
    }

    public void setCcdlId(String ccdlId) {
        this.ccdlId = ccdlId == null ? null : ccdlId.trim();
    }

    public String getCclId() {
        return cclId;
    }

    public void setCclId(String cclId) {
        this.cclId = cclId == null ? null : cclId.trim();
    }

    /**
     * 获取 [CT_COURSEWARE_CLASS_DETAIL_LOG] 的属性 1随堂 2点名 3讲作业 4截图
     */
    public Integer getCcdlType() {
        return ccdlType;
    }

    /**
     * 设置[CT_COURSEWARE_CLASS_DETAIL_LOG]的属性1随堂 2点名 3讲作业 4截图
     */
    public void setCcdlType(Integer ccdlType) {
        this.ccdlType = ccdlType;
    }

    /**
     * 获取 [CT_COURSEWARE_CLASS_DETAIL_LOG] 的属性 1随堂 2点名 3讲作业 4截图
     */
    public String getCcdlContext() {
        return ccdlContext;
    }

    /**
     * 设置[CT_COURSEWARE_CLASS_DETAIL_LOG]的属性1随堂 2点名 3讲作业 4截图
     */
    public void setCcdlContext(String ccdlContext) {
        this.ccdlContext = ccdlContext == null ? null : ccdlContext.trim();
    }

    public Date getCcdlBegtime() {
        return ccdlBegtime;
    }

    public void setCcdlBegtime(Date ccdlBegtime) {
        this.ccdlBegtime = ccdlBegtime;
    }

    public Date getCcdlEndtime() {
        return ccdlEndtime;
    }

    public void setCcdlEndtime(Date ccdlEndtime) {
        this.ccdlEndtime = ccdlEndtime;
    }

    public String getCcdlContent1() {
        return ccdlContent1;
    }

    public void setCcdlContent1(String ccdlContent1) {
        this.ccdlContent1 = ccdlContent1 == null ? null : ccdlContent1.trim();
    }

    public String getCcdlContent2() {
        return ccdlContent2;
    }

    public void setCcdlContent2(String ccdlContent2) {
        this.ccdlContent2 = ccdlContent2 == null ? null : ccdlContent2.trim();
    }

    public String getCcdlContent3() {
        return ccdlContent3;
    }

    public void setCcdlContent3(String ccdlContent3) {
        this.ccdlContent3 = ccdlContent3 == null ? null : ccdlContent3.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }
}