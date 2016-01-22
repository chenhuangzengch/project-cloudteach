package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/25 0025.
 */
public class TeacherWorkItemDTO implements Serializable {

    private static final long serialVersionUID = -7094281666936130024L;
    /**
     * 教师作业题目ID
     */
    private String workItemId;

    /**
     * 作业（对应教师作业表主键）
     */
    private String workId;

    /**
     * 题目ID
     */
    private String itemId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 题目类型：1预习 4电子作业 7口语作业
     */
    private Integer itemType;

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
     * 口语作业内容
     */
    private String voiceContext = "";

    /**
     * 描述内容
     */
    private String context;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 教师作业题目ID
     */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性教师作业题目ID
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId == null ? null : workItemId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 作业（对应教师作业表主键）
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性作业（对应教师作业表主键）
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 题目ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性题目ID
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 题目类型：1预习 4电子作业 7口语作业
     */
    public Integer getItemType() {
        return itemType;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性题目类型：1预习 4电子作业 7口语作业
     */
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 提交方式：图片
     */
    public Integer getSubImage() {
        return subImage;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性提交方式：图片
     */
    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 提交方式：录音
     */
    public Integer getSubTape() {
        return subTape;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性提交方式：录音
     */
    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 提交方式：视频
     */
    public Integer getSubVideo() {
        return subVideo;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性提交方式：视频
     */
    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 提交方式：其他
     */
    public Integer getSubOther() {
        return subOther;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性提交方式：其他
     */
    public void setSubOther(Integer subOther) {
        this.subOther = subOther;
    }

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 描述内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性描述内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * 获取 [CT_TEACHER_WORK_ITEM] 的属性 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_TEACHER_WORK_ITEM]的属性状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeacherWorkItemDTO{" +
                "workItemId='" + workItemId + '\'' +
                ", workId='" + workId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", itemType=" + itemType +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", voiceContext='" + voiceContext + '\'' +
                ", context='" + context + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", status=" + status +
                '}';
    }
}