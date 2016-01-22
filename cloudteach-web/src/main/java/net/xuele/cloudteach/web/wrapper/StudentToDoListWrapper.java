package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * Created by hujx on 2015/7/10 0010.
 */
public class StudentToDoListWrapper {

    // 作业ID
    private String workId;

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

    // 最晚提交时间
    private Date endTime;

    // 最晚提交天数，通过endTime-publishTime计算得到(毫秒级别)
    private long lastCommitDays;

    // 课程名称
    private String unitName;

    // 科目名称
    private String subjectName;

    // 教师职称
    private String positionName;

    /*// 提交方式图片
    private int subImage;

    //提交方式录音
    private int subTape;

    //提交方式视频
    private int subVideo;

    //提交方式其他
    private int subOther;*/

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

    /*public int getSubImage() {
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
    }*/

    public String getTeacherHeadIcon() {
        return teacherHeadIcon;
    }

    public void setTeacherHeadIcon(String teacherHeadIcon) {
        this.teacherHeadIcon = teacherHeadIcon;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public long getLastCommitDays() {
        return lastCommitDays;
    }

    public void setLastCommitDays(long lastCommitDays) {
        this.lastCommitDays = lastCommitDays;
    }
}
