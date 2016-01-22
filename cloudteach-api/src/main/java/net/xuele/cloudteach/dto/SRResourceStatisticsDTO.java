package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * SRResourceStatisticsDTO
 * 区划下的资源统计
 * @author duzg
 * @date on 2015/8/5.
 */
public class SRResourceStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -2711069037176043851L;

    /**
     * 资源总数
     */
    private Integer resourceTotal;

    /**
     * 课件数
     */
    private Integer coursewareAmount;

    /**
     * 习题数
     */
    private Integer exercisesAmount;

    /**
     * 课程素材数
     */
    private Integer fodderAmount;

    /**
     * 教案数
     */
    private Integer teachPlanAmount;

    /**
     * 学案数
     */
    private Integer learnPlanAmount;

    /**
     * 其他资源数
     */
    private Integer resourceOtherAmount;


    /**
     * 图片数（资源类型）
     */
    private Integer pictureAmount;

    /**
     * word文档数
     */
    private Integer wordAmount;

    /**
     * PPT数
     */
    private Integer pptAmount;

    /**
     * 视频数
     */
    private Integer videoAmount;

    /**
     * 音频数
     */
    private Integer vioceAmount;

    /**
     * 其他类型资源数
     */
    private Integer typeOtherAmount;

    /**
     * 已分享数
     */
    private Integer sharedAmount;

    /**
     * 未分享数
     */
    private Integer unSharedAmount;

    public Integer getResourceTotal() {
        return resourceTotal;
    }

    public void setResourceTotal(Integer resourceTotal) {
        this.resourceTotal = resourceTotal;
    }

    public Integer getCoursewareAmount() {
        return coursewareAmount;
    }

    public void setCoursewareAmount(Integer coursewareAmount) {
        this.coursewareAmount = coursewareAmount;
    }

    public Integer getExercisesAmount() {
        return exercisesAmount;
    }

    public void setExercisesAmount(Integer exercisesAmount) {
        this.exercisesAmount = exercisesAmount;
    }

    public Integer getFodderAmount() {
        return fodderAmount;
    }

    public void setFodderAmount(Integer fodderAmount) {
        this.fodderAmount = fodderAmount;
    }

    public Integer getTeachPlanAmount() {
        return teachPlanAmount;
    }

    public void setTeachPlanAmount(Integer teachPlanAmount) {
        this.teachPlanAmount = teachPlanAmount;
    }

    public Integer getLearnPlanAmount() {
        return learnPlanAmount;
    }

    public void setLearnPlanAmount(Integer learnPlanAmount) {
        this.learnPlanAmount = learnPlanAmount;
    }

    public Integer getResourceOtherAmount() {
        return resourceOtherAmount;
    }

    public void setResourceOtherAmount(Integer resourceOtherAmount) {
        this.resourceOtherAmount = resourceOtherAmount;
    }

    public Integer getPictureAmount() {
        return pictureAmount;
    }

    public void setPictureAmount(Integer pictureAmount) {
        this.pictureAmount = pictureAmount;
    }

    public Integer getWordAmount() {
        return wordAmount;
    }

    public void setWordAmount(Integer wordAmount) {
        this.wordAmount = wordAmount;
    }

    public Integer getPptAmount() {
        return pptAmount;
    }

    public void setPptAmount(Integer pptAmount) {
        this.pptAmount = pptAmount;
    }

    public Integer getVideoAmount() {
        return videoAmount;
    }

    public void setVideoAmount(Integer videoAmount) {
        this.videoAmount = videoAmount;
    }

    public Integer getVioceAmount() {
        return vioceAmount;
    }

    public void setVioceAmount(Integer vioceAmount) {
        this.vioceAmount = vioceAmount;
    }

    public Integer getTypeOtherAmount() {
        return typeOtherAmount;
    }

    public void setTypeOtherAmount(Integer typeOtherAmount) {
        this.typeOtherAmount = typeOtherAmount;
    }

    public Integer getSharedAmount() {
        return sharedAmount;
    }

    public void setSharedAmount(Integer sharedAmount) {
        this.sharedAmount = sharedAmount;
    }

    public Integer getUnSharedAmount() {
        return unSharedAmount;
    }

    public void setUnSharedAmount(Integer unSharedAmount) {
        this.unSharedAmount = unSharedAmount;
    }

    @Override
    public String toString() {
        return "SRResourceStatisticsDTO{" +
                "resourceTotal=" + resourceTotal +
                ", coursewareAmount=" + coursewareAmount +
                ", exercisesAmount=" + exercisesAmount +
                ", fodderAmount=" + fodderAmount +
                ", teachPlanAmount=" + teachPlanAmount +
                ", learnPlanAmount=" + learnPlanAmount +
                ", resourceOtherAmount=" + resourceOtherAmount +
                ", pictureAmount=" + pictureAmount +
                ", wordAmount=" + wordAmount +
                ", pptAmount=" + pptAmount +
                ", videoAmount=" + videoAmount +
                ", vioceAmount=" + vioceAmount +
                ", typeOtherAmount=" + typeOtherAmount +
                ", sharedAmount=" + sharedAmount +
                ", unSharedAmount=" + unSharedAmount +
                '}';
    }
}
