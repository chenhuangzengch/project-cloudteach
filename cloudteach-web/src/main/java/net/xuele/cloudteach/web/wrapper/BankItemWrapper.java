package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.BankItemDTO;

import java.util.Date;

/**
 * BankItemWrapper
 *
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemWrapper {

    /**
     * 预习项ID
     */
    private String itemId;

    /**
     * 题目类型
     */
    private Integer itemType;

    /**
     * 创建者userid
     */
    private String creator;

    /**
     * 对应的课程ID
     */
    private String unitId;

    /**
     * 是否为收藏
     */
    private Integer isCollect;

    /**
     * 所属用户ID
     */
    private String userId;

    /**
     * 收藏预习项来源id
     */
    private String pid;

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
     * 置顶标志
     */
    private Integer stickStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 分享状态
     */
    private Integer shareStatus;

    /**
     * 学校Id
     */
    private String schoolId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发布次数
     */
    private Integer releases;

    /**
     * 口语作业内容
     */
    private String voiceContext = "";

    /**
     * 附件总数
     */
    private Long filesAmount;

    /**
     * 初始化
     *
     * @param bankItemDTO
     */
    public BankItemWrapper(BankItemDTO bankItemDTO) {

        this.setItemId(bankItemDTO.getItemId());
        this.setCreator(bankItemDTO.getCreator());
        this.setUnitId(bankItemDTO.getUnitId());
        this.setIsCollect(bankItemDTO.getIsCollect());
        this.setUserId(bankItemDTO.getUserId());
        this.setPid(bankItemDTO.getPid());
        this.setItemType(bankItemDTO.getItemType());
        this.setSubImage(bankItemDTO.getSubImage());
        this.setSubTape(bankItemDTO.getSubTape());
        this.setSubVideo(bankItemDTO.getSubVideo());
        this.setSubOther(bankItemDTO.getSubOther());
        this.setStickStatus(bankItemDTO.getStickStatus());
        this.setCreateTime(bankItemDTO.getCreateTime());
        this.setUpdateTime(bankItemDTO.getUpdateTime());
        this.setShareStatus(bankItemDTO.getShareStatus());
        this.setSchoolId(bankItemDTO.getSchoolId());
        this.setStatus(bankItemDTO.getStatus());
        this.setFilesAmount(bankItemDTO.getFilesAmount());

        //TODO 长度为3000的描述类信息，在列表中显示后台截取指定长度后传到前端
        this.setContext(bankItemDTO.getContext()/** .substring(0) */);

        this.setVoiceContext(bankItemDTO.getVoiceContext());
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

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

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public Integer getStickStatus() {
        return stickStatus;
    }

    public void setStickStatus(Integer stickStatus) {
        this.stickStatus = stickStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReleases() {
        return releases;
    }

    public void setReleases(Integer releases) {
        this.releases = releases;
    }

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
    }

    public Long getFilesAmount() {
        return filesAmount;
    }

    public void setFilesAmount(Long filesAmount) {
        this.filesAmount = filesAmount;
    }

    public Integer getHasFiles() {
        return (filesAmount != null && filesAmount > 0) ? 1 : 0;
    }


}
