package net.xuele.cloudteach.web.wrapper;


import java.util.Date;

/**
 * Created by dj on 2015/7/31 0031.
 */
public class ParentCloudWorkPageWrapper {

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

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherHeadIcon() {
        return teacherHeadIcon;
    }

    public void setTeacherHeadIcon(String teacherHeadIcon) {
        this.teacherHeadIcon = teacherHeadIcon;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getWorkItemCount() {
        return workItemCount;
    }

    public void setWorkItemCount(int workItemCount) {
        this.workItemCount = workItemCount;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getLastCommitDays() {
        return lastCommitDays;
    }

    public void setLastCommitDays(long lastCommitDays) {
        this.lastCommitDays = lastCommitDays;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
