package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * SRAreaResourceAmountDTO
 * 区划下的资源统计数量
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRAreaResourceAmountDTO implements Serializable {

    private static final long serialVersionUID = -632038065670273288L;

    //学校数量
    private Integer schoolAmount;
    //教师数量
    private Integer teacherAmount;
    //资源数量
    private Integer resourceAmount;

    public Integer getSchoolAmount() {
        return schoolAmount;
    }

    public void setSchoolAmount(Integer schoolAmount) {
        this.schoolAmount = schoolAmount;
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

    @Override
    public String toString() {
        return "SRAreaResourceAmountDTO{" +
                "schoolAmount=" + schoolAmount +
                ", teacherAmount=" + teacherAmount +
                ", resourceAmount=" + resourceAmount +
                '}';
    }
}
