package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * SRSchoolResourceStatisticsDTO
 * 区划下学校资源统计
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRSchoolResourceStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -8036458958919499026L;
    /**
     * 学校ID
     */
    private String schoolId;
    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 校长
     */
    private String schoolMaster;

    /**
     * 教师数量
     */
    private Integer teacherAmount;

    /**
     * 资源数量
     */
    private Integer resourceAmount;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolMaster() {
        return schoolMaster;
    }

    public void setSchoolMaster(String schoolMaster) {
        this.schoolMaster = schoolMaster;
    }

    public Integer getTeacherAmount() {
        return teacherAmount;
    }

    public void setTeacherAmount(Integer teacherAmount) {
        this.teacherAmount = teacherAmount;
    }

    public Integer getResourceAmount() {
        return resourceAmount;
    }

    public void setResourceAmount(Integer resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "SRSchoolResourceStatisticsDTO{" +
                "schoolID='" + schoolId + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolMaster='" + schoolMaster + '\'' +
                ", teacherAmount='" + teacherAmount + '\'' +
                ", resourceAmount=" + resourceAmount +
                '}';
    }
}
