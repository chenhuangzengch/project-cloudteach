package net.xuele.cloudteach.web.form;


import java.util.List;

/**
 * BankItemEditForm
 *
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemEditForm {

    /**
     * 题目ID
     */
    private String itemId;

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

    private String fileJson;


    public String getFileJson() {
        return fileJson;
    }

    public void setFileJson(String fileJson) {
        this.fileJson = fileJson;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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
        return subImage == null ? 0 : subImage;
    }

    public void setSubImage(Integer subImage) {
        this.subImage = subImage;
    }

    public Integer getSubTape() {
        return subTape == null ? 0 : subTape;
    }

    public void setSubTape(Integer subTape) {
        this.subTape = subTape;
    }

    public Integer getSubVideo() {
        return subVideo == null ? 0 : subVideo;
    }

    public void setSubVideo(Integer subVideo) {
        this.subVideo = subVideo;
    }

    public Integer getSubOther() {
        return subOther == null ? 0 : subOther;
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

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
    }
}
