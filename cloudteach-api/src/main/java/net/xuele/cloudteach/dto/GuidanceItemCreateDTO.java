package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * GuidanceItemCreateDTO
 *
 * @author sunxh
 * @date 2015/7/16 0016
 */
public class GuidanceItemCreateDTO implements Serializable {
    private static final long serialVersionUID = -8188613270691996184L;

    /**
     * 创建者userid
     */
    private String creator;

    /**
     * 对应的课程ID
     */
    private String unitId;

    /**
     * 提交方式：图片
     */
    private Integer subImage;

    /**
     * 提交方式：录音
     */
    private Integer subTape;

    /**
     * 提交方式：视频
     */
    private Integer subVideo;

    /**
     * 提交方式：其他
     */
    private Integer subOther;

    /**
     * 描述内容
     */
    private String context;

    /**
     * 附件
     */
    private List<String> diskIds;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getSubImage() {
        return subImage;
    }

    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    public Integer getSubTape() {
        return subTape;
    }

    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    public Integer getSubVideo() {
        return subVideo;
    }

    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    public Integer getSubOther() {
        return subOther;
    }

    public void setSubOther(Integer subOther) {
        this.subOther = subOther;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<String> getDiskIds() {
        return diskIds;
    }

    public void setDiskIds(List<String> diskIds) {
        this.diskIds = diskIds;
    }

    @Override
    public String toString() {
        return "GuidanceItemCreateDTO{" +
                "creator='" + creator + '\'' +
                ", unitId='" + unitId + '\'' +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", context='" + context + '\'' +
                ", diskIds=" + diskIds +
                '}';
    }
}
