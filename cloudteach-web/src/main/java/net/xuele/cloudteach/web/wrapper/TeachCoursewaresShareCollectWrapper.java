package net.xuele.cloudteach.web.wrapper;


public class TeachCoursewaresShareCollectWrapper {
    /**
     * 分享授课课件ID
     */
    private String shareId;

    /**
     * 授课课件id
     */
    private String coursewaresId;

    /**
     * 用户收藏状态：0取消收藏，1已收藏
     */
    private Integer collectStatus;

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_COLLECT] 的属性 分享授课课件ID
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_COLLECT]的属性分享授课课件ID
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    public String getCoursewaresId() {
        return coursewaresId;
    }

    public void setCoursewaresId(String coursewaresId) {
        this.coursewaresId = coursewaresId;
    }

    /**
     * 获取 [CT_TEACH_COURSEWARES_SHARE_COLLECT] 的属性 用户收藏状态：0取消收藏，1已收藏
     */
    public Integer getCollectStatus() {
        return collectStatus;
    }

    /**
     * 设置[CT_TEACH_COURSEWARES_SHARE_COLLECT]的属性用户收藏状态：0取消收藏，1已收藏
     */
    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }
}