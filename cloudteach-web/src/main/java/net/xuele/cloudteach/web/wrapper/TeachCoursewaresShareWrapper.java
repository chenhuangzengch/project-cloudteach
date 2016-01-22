package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

public class TeachCoursewaresShareWrapper {

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 分享授课课件ID
     */
    private String shareId;

    /**
     * 用户ID:（分享人）userid
     */
    private String creator;

    /**
     * 原课件ID：对应我的授课课件ID
     */
    private String coursewaresId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 课件名称
     */
    private String title;

    /**
     * 置顶标志:0否 1是
     */
    private Integer stickStatus;

    /**
     * 分享时间
     */
    private Date shareTime;

/*******************************关联字段*******************************************/
    /**
     * 收藏次数
     */
    private Integer collectTimes;

    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 用户名称
     */
    private String creatorName;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 收藏状态
     */
    private int collectState;

    /**
     * 点赞状态
     */
    private int praiseState;

    /**
     * 是否我分享的
     */
    private int isMyShare;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getCollectTimes() {
        return collectTimes;
    }

    public void setCollectTimes(Integer collectTimes) {
        this.collectTimes = collectTimes;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getCollectState() {
        return collectState;
    }

    public void setCollectState(int collectState) {
        this.collectState = collectState;
    }

    public int getPraiseState() {
        return praiseState;
    }

    public void setPraiseState(int praiseState) {
        this.praiseState = praiseState;
    }

    public int getIsMyShare() {
        return isMyShare;
    }

    public void setIsMyShare(int isMyShare) {
        this.isMyShare = isMyShare;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 分享授课课件ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性分享授课课件ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 用户ID:（分享人）userid
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性用户ID:（分享人）userid
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 原课件ID：对应我的授课课件ID
     */
    public String getCoursewaresId() {
        return coursewaresId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性原课件ID：对应我的授课课件ID
     */
    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId == null ? null : coursewaresId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 课件名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性课件名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 置顶标志:0否 1是
     */
    public Integer getStickStatus() {
        return stickStatus;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性置顶标志:0否 1是
     */
    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE] 的属性 分享时间
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE]的属性分享时间
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

}