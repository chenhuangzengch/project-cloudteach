package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * SRTeacherResourceAmountDTO
 * 教师资源数量
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRTeacherResourceAmountDTO implements Serializable {

    private static final long serialVersionUID = -5181442424987476024L;
    /**
     * 已分享数
     */
    private Integer sharedAmount;

    /**
     * 资源总数
     */
    private Integer resourceTotal;

    public Integer getSharedAmount() {
        return sharedAmount;
    }

    public void setSharedAmount(Integer sharedAmount) {
        this.sharedAmount = sharedAmount;
    }

    public Integer getResourceTotal() {
        return resourceTotal;
    }

    public void setResourceTotal(Integer resourceTotal) {
        this.resourceTotal = resourceTotal;
    }

    @Override
    public String toString() {
        return "SRTeacherResourceAmountDTO{" +
                "sharedAmount=" + sharedAmount +
                ", resourceTotal=" + resourceTotal +
                '}';
    }
}
