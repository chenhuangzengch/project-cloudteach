package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * SRSchoolResourceAmountDTO
 * 学校下的资源统计数量
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRSchoolResourceAmountDTO implements Serializable {

    private static final long serialVersionUID = 6739100457138297534L;

    //教师数量
    private Integer teacherAmount;
    //资源数量
    private Integer resourceAmount;

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
        return "SRSchoolResourceAmountDTO{" +
                "teacherAmount=" + teacherAmount +
                ", resourceAmount=" + resourceAmount +
                '}';
    }
}
