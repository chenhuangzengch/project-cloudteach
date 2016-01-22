package net.xuele.cloudteach.web.wrapper;

/**
 * PCCoursewareWrapper
 *
 * @author sunxh
 * @date 15/8/13
 */
public class PCCoursewareWrapper {

    /**
     * 课件ID
     */
    private String skid;

    /**
     * 用户ID 如果是收藏，分享的用户id
     */
    private String userid;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private String time;

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
