package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * MagicWorkDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class MagicWorkDTO implements Serializable {
    private static final long serialVersionUID = -5066345315739380967L;
    private String workId;

    private String userId;

    private String schoolId;

    private String unitId;

    private String bankId;

    private String context;

    /**
     * 是否为定时发布，0，定时发布，1，及时发布
     */
    private Integer workType;

    private Date publishTime;

    private Date endTime;

    private Integer subImage;

    private Integer subTape;

    private Integer subVideo;

    private Integer subOther;

    /**
     * （异步变更）0未完成 ，1部分完成，2全部完成
     */
    private Integer finishStatus;

    /**
     * （异步变更）0未批改，1部分批改，2全部批改
     */
    private Integer correctStatus;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_MAGIC_WORK] 的属性 是否为定时发布，0，定时发布，1，及时发布
     */
    public Integer getWorkType() {
        return workType;
    }

    /**
     * 设置[CT_MAGIC_WORK]的属性是否为定时发布，0，定时发布，1，及时发布
     */
    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSubImage() {
        return subImage;
    }

    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    public Integer getSubTape() {
        return subTape;
    }

    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    public Integer getSubVideo() {
        return subVideo;
    }

    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    public Integer getSubOther() {
        return subOther;
    }

    public void setSubOther(Integer subOther) {
        this.subOther = subOther;
    }

    /**
     * 获取 [CT_MAGIC_WORK] 的属性 （异步变更）0未完成 ，1部分完成，2全部完成
     */
    public Integer getFinishStatus() {
        return finishStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK]的属性（异步变更）0未完成 ，1部分完成，2全部完成
     */
    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }

    /**
     * 获取 [CT_MAGIC_WORK] 的属性 （异步变更）0未批改，1部分批改，2全部批改
     */
    public Integer getCorrectStatus() {
        return correctStatus;
    }

    /**
     * 设置[CT_MAGIC_WORK]的属性（异步变更）0未批改，1部分批改，2全部批改
     */
    public void setCorrectStatus(Integer correctStatus) {
        this.correctStatus = correctStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MagicWorkDTO{" +
                "workId='" + workId + '\'' +
                ", userId='" + userId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", context='" + context + '\'' +
                ", workType=" + workType +
                ", publishTime=" + publishTime +
                ", endTime=" + endTime +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", finishStatus=" + finishStatus +
                ", correctStatus=" + correctStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}