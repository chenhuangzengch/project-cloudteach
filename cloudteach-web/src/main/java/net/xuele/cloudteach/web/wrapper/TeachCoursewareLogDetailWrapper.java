package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * TeachCoursewareLogDetailWrapper
 * 用于封装回传给flash的授课记录明细数据
 *
 * @author sunxh
 * @date 15/12/3
 */
public class TeachCoursewareLogDetailWrapper {
    /**
     * 记录明细ID
     */
    private String ccdlId;

    /**
     * 授课记录ID
     */
    private String cclId;

    /**
     * 记录明细类型
     * 1随堂 2点名 3讲作业 4截图
     */
    private Integer ccdlType;

    /**
     * 记录明细内容
     * flash传入，内容为字符串，例如：随堂
     * 程序不做验证
     * 1随堂 2点名 3讲作业 4截图
     */
    private String ccdlContext;

    /**
     * 开始时间
     */
    private Date ccdlBegtime;

    /**
     * 结束时间
     * 目前取ccdlBegtime的值
     */
    private Date ccdlEndtime;

    /**
     * javabean 的json 字符串
     */
    private String ccdlContent1;

    private String ccdlContent2;

    private String ccdlContent3;

    /**
     * 学校ID
     */
    private String schoolId;

    public String getCcdlId() {
        return ccdlId;
    }

    public void setCcdlId(String ccdlId) {
        this.ccdlId = ccdlId;
    }

    public String getCclId() {
        return cclId;
    }

    public void setCclId(String cclId) {
        this.cclId = cclId;
    }

    public Integer getCcdlType() {
        return ccdlType;
    }

    public void setCcdlType(Integer ccdlType) {
        this.ccdlType = ccdlType;
    }

    public String getCcdlContext() {
        return ccdlContext;
    }

    public void setCcdlContext(String ccdlContext) {
        this.ccdlContext = ccdlContext;
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
        this.ccdlContent1 = ccdlContent1;
    }

    public String getCcdlContent2() {
        return ccdlContent2;
    }

    public void setCcdlContent2(String ccdlContent2) {
        this.ccdlContent2 = ccdlContent2;
    }

    public String getCcdlContent3() {
        return ccdlContent3;
    }

    public void setCcdlContent3(String ccdlContent3) {
        this.ccdlContent3 = ccdlContent3;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "TeachCoursewareLogDetailWrapper{" +
                "ccdlId='" + ccdlId + '\'' +
                ", cclId='" + cclId + '\'' +
                ", ccdlType=" + ccdlType +
                ", ccdlContext='" + ccdlContext + '\'' +
                ", ccdlBegtime=" + ccdlBegtime +
                ", ccdlEndtime=" + ccdlEndtime +
                ", ccdlContent1='" + ccdlContent1 + '\'' +
                ", ccdlContent2='" + ccdlContent2 + '\'' +
                ", ccdlContent3='" + ccdlContent3 + '\'' +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
