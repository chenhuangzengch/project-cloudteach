package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * GuidanceWorkItemDTO
 *
 * @author duzg
 * @date 2015/7/2 0002
 */
public class GuidanceWorkItemDTO implements Serializable {
    private static final long serialVersionUID = -4953920083032214423L;

    /**
     * 预习作业预习项ID
     */
    private String workItemId;

    /**
     * 预习作业ID
     */
    private String workId;

    /**
     * 预习项ID
     */
    private String itemId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 提交方式：图片
     */
    private Integer subImage;

    /**
     * 提交方式：录音
     */
    private Integer subTape;

    /**
     * 提交方式：视频
     */
    private Integer subVideo;

    /**
     * 提交方式：其他
     */
    private Integer subOther;

    /**
     * 描述内容
     */
    private String context;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 预习作业预习项ID
     */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性预习作业预习项ID
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId == null ? null : workItemId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 预习作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性预习作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 预习项ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性预习项ID
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 提交方式：图片
     */
    public Integer getSubImage() {
        return subImage;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性提交方式：图片
     */
    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 提交方式：录音
     */
    public Integer getSubTape() {
        return subTape;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性提交方式：录音
     */
    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 提交方式：视频
     */
    public Integer getSubVideo() {
        return subVideo;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性提交方式：视频
     */
    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 提交方式：其他
     */
    public Integer getSubOther() {
        return subOther;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性提交方式：其他
     */
    public void setSubOther(Integer subOther) {
        this.subOther = subOther;
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 描述内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性描述内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_GUIDANCE_WORK_ITEM] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_GUIDANCE_WORK_ITEM]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GuidanceWorkItemDTO{" +
                "workItemId='" + workItemId + '\'' +
                ", workId='" + workId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", context='" + context + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}