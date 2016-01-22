package net.xuele.cloudteach.web.wrapper;

/**
 * PCTeacherInfoWrapper
 *      pc端教师信息
 * @author sunxh
 * @date 15/8/13
 */
public class PCTeacherInfoWrapper {

    /**
     * 处理状态：1，成功；-1，失败。
     */
    private String state;

    /**
     * 用户ID 【加密】
     */
    private String userid;

    /**
     * 用户名
     */
    private String name;

    /**
     * 教材ID
     */
    private String jcid;

    /**
     * 教材名称
     */
    private String jcname;

    /**
     * 课程ID
     */
    private String kcid;

    /**
     * 课程名称
     */
    private String kcname;

    /**
     * 学校ID 【加密】
     */
    private String schoolId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getJcid() {
        return jcid;
    }

    public void setJcid(String jcid) {
        this.jcid = jcid;
    }

    public String getJcname() {
        return jcname;
    }

    public void setJcname(String jcname) {
        this.jcname = jcname;
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid;
    }

    public String getKcname() {
        return kcname;
    }

    public void setKcname(String kcname) {
        this.kcname = kcname;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
