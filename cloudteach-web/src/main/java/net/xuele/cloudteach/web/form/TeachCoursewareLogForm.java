package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;

import java.util.Date;

/**
 * TeachCoursewareLogForm
 * 授课课件打点表单数据
 * 用于接收来自前端（目前是flash）的授课打点数据
 *
 * @author sunxh
 * @date 15/12/2
 */
public class TeachCoursewareLogForm {

    /**
     * 授课记录ID
     */
    private String cclId;

    /**
     * 随机码
     * 具体格式由flash确定，不超过32字符，例如：201512041101
     * 每一个授课页面，所对应的一组事件全部使用同一个随机码
     */
    private String randomId;

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

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public String getCclCoursewaresId() {
        return cclCoursewaresId;
    }

    public void setCclCoursewaresId(String cclCoursewaresId) {
        this.cclCoursewaresId = cclCoursewaresId;
    }

    /**
     * 解密处理
     *
     * @return
     */
    public String getCclUserId() {
        return cclUserId == null ? null : CloudTeachEncryptUtil.decrypt(cclUserId);
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

    /**
     * 解密处理
     *
     * @return
     */
    public String getSchoolId() {
        return schoolId == null ? null : CloudTeachEncryptUtil.decrypt(schoolId);
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
        return "TeachCoursewareLogForm{" +
                "cclId='" + cclId + '\'' +
                ", randomId='" + randomId + '\'' +
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
