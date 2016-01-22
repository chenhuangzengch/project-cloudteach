package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtWorkTapeFiles;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hujx on 2015/8/8 0008.
 * 作业信息通用
 */
public class BasicWorkInfoView {

    // 作业ID
    private String workId;

    // 教师ID
    private String teacherId;

    // 教师名字
    private String teacherName;

    // 教师头像
    private String teacherHeadIcon;

    // 作业类型: 1预习作业，2提分宝作业，3同步课堂作业，4电子作业
    private int workType;

    // 发布时间
    private Date publishTime;

    // 作业描述
    private String context;

    // 作业项个数
    private int workItemCount;

    // 最晚提交时间，通过endTime-publishTime算出最晚提交天数(毫秒)
    private Date endTime;

    //课程ID
    private String unitId;

    // 课程名称
    private String unitName;

    // 科目名称
    private String subjectName;

    // 教师职称
    private String positionName;

    // 提分宝题库ID(提分宝专用)
    private String bankId;

    // 提交方式:图片
    private int subImage;

    // 提交方式:录音
    private int subTape;

    // 提交方式:视频
    private int subVideo;

    // 提交方式:其他
    private int subOther;

    // 口语单词(口语作业专用)
    private String voiceContext;

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
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
        this.context = context;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherHeadIcon() {
        return teacherHeadIcon;
    }

    public void setTeacherHeadIcon(String teacherHeadIcon) {
        this.teacherHeadIcon = teacherHeadIcon;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public int getWorkItemCount() {
        return workItemCount;
    }

    public void setWorkItemCount(int workItemCount) {
        this.workItemCount = workItemCount;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public int getSubImage() {
        return subImage;
    }

    public void setSubImage(int subImage) {
        this.subImage = subImage;
    }

    public int getSubOther() {
        return subOther;
    }

    public void setSubOther(int subOther) {
        this.subOther = subOther;
    }

    public int getSubTape() {
        return subTape;
    }

    public void setSubTape(int subTape) {
        this.subTape = subTape;
    }

    public int getSubVideo() {
        return subVideo;
    }

    public void setSubVideo(int subVideo) {
        this.subVideo = subVideo;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
