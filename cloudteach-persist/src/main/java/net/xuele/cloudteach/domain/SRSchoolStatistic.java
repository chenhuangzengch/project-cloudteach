package net.xuele.cloudteach.domain;

/**
 * Created by cm.wang on 2015/8/13 0013.
 */
public class SRSchoolStatistic {

    private String schoolId;

    private String schoolName;

    private String headmasterName;

    private Integer teacherNum;

    private Integer workNum;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getHeadmasterName() {
        return headmasterName;
    }

    public void setHeadmasterName(String headmasterName) {
        this.headmasterName = headmasterName;
    }

    public Integer getTeacherNum() {
        if(this.teacherNum == null) {
            this.teacherNum = 0;
        }
        return this.teacherNum;
    }

    public void setTeacherNum(Integer teacherNum) {
        this.teacherNum = teacherNum;
    }

    public Integer getWorkNum() {
        if(this.workNum == null) {
            this.workNum = 0;
        }
        return this.workNum;
    }

    public void setWorkNum(Integer workNum) {
        this.workNum = workNum;
    }

}
