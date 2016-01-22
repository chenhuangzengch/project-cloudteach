package net.xuele.cloudteach.dto;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * BankItemCreateDTO
 *
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemCreateDTO implements Serializable {
    private static final long serialVersionUID = -6745363632363015446L;

    /**
     * 创建者userid
     */
    private String creator;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 对应的课程ID
     */
    private String unitId;

    /**
     * 题目类型：1预习 4电子作业 7口语作业
     */
    private Integer itemType;

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

    /**
     * 口语作业内容
     */
    private String voiceContext = "";

    private List<BankItemAppFilesDTO> filesList;

    public List<BankItemAppFilesDTO> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<BankItemAppFilesDTO> filesList) {
        this.filesList = filesList;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
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
        //去掉所有null
        if (!CollectionUtils.isEmpty(diskIds)) {
            diskIds.removeAll(Collections.singleton(null));
            diskIds.removeAll(Collections.singleton(""));
        }
        this.diskIds = diskIds;
    }

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
    }

    @Override
    public String toString() {
        return "BankItemCreateDTO{" +
                "context='" + context + '\'' +
                ", creator='" + creator + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", itemType=" + itemType +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", diskIds=" + diskIds +
                ", voiceContext='" + voiceContext + '\'' +
                ", filesList='" + filesList + '\'' +
                '}';
    }
}
