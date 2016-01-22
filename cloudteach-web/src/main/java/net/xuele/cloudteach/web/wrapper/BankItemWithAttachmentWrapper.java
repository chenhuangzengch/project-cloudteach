package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.BankItemWithAttachmentDTO;
import net.xuele.cloudteach.dto.CloudDiskReferDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BankItemWithAttachmentWrapper
 *
 * @author sunxh
 * @date 15/7/27
 */
public class BankItemWithAttachmentWrapper {
    /**
     * 题目ID
     */
    private String itemId;

    /**
     * 创建者（题目所属人）userid
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
     * 所属用户（题目原作者）ID
     */
    private String userId;

    /**
     * 收藏题目来源id（对应分享教师题目表分享题目ID）
     */
    private String pid;

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
     * 学校ID:用于数据库分片存储
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
     * 附件列表
     */
    private List<CloudDiskReferWrapper> diskReferList;

    /**
     * 口语作业内容
     */
    private String voiceContext = "";

    /**
     * 由GuidanceItemWithFilesDTO初始化
     *
     * @param itemWithAttachmentDTO
     */
    public BankItemWithAttachmentWrapper(BankItemWithAttachmentDTO itemWithAttachmentDTO) {

        this.setItemId(itemWithAttachmentDTO.getItemId());
        this.setCreator(itemWithAttachmentDTO.getCreator());
        this.setUnitId(itemWithAttachmentDTO.getUnitId());
        this.setIsCollect(itemWithAttachmentDTO.getIsCollect());
        this.setUserId(itemWithAttachmentDTO.getUserId());
        this.setPid(itemWithAttachmentDTO.getPid());
        this.setItemType(itemWithAttachmentDTO.getItemType());
        this.setSubImage(itemWithAttachmentDTO.getSubImage());
        this.setSubTape(itemWithAttachmentDTO.getSubTape());
        this.setSubVideo(itemWithAttachmentDTO.getSubVideo());
        this.setSubOther(itemWithAttachmentDTO.getSubOther());
        this.setContext(itemWithAttachmentDTO.getContext());
        this.setStickStatus(itemWithAttachmentDTO.getStickStatus());
        this.setCreateTime(itemWithAttachmentDTO.getCreateTime());
        this.setUpdateTime(itemWithAttachmentDTO.getUpdateTime());
        this.setShareStatus(itemWithAttachmentDTO.getShareStatus());
        this.setSchoolId(itemWithAttachmentDTO.getSchoolId());
        this.setStatus(itemWithAttachmentDTO.getStatus());
        this.setReleases(itemWithAttachmentDTO.getReleases());

        this.setVoiceContext(itemWithAttachmentDTO.getVoiceContext());

        List<CloudDiskReferWrapper> diskReferWrapperList = new ArrayList<>();

        //如果附件非空，拷贝附件
        if (CollectionUtils.isNotEmpty(itemWithAttachmentDTO.getAttachmentList())) {
            for (CloudDiskReferDTO diskReferDTO : itemWithAttachmentDTO.getAttachmentList()) {
                CloudDiskReferWrapper diskReferWrapper = new CloudDiskReferWrapper();
                BeanUtils.copyProperties(diskReferDTO, diskReferWrapper);
                diskReferWrapperList.add(diskReferWrapper);
            }
        }

        this.setDiskReferList(diskReferWrapperList);

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

    public List<CloudDiskReferWrapper> getDiskReferList() {
        return diskReferList;
    }

    public void setDiskReferList(List<CloudDiskReferWrapper> diskReferList) {
        this.diskReferList = diskReferList;
    }

    public String getVoiceContext() {
        return voiceContext;
    }

    public void setVoiceContext(String voiceContext) {
        this.voiceContext = voiceContext;
    }

    public String getDiskIds(){
        String diskIds = "";
        if(CollectionUtils.isNotEmpty(diskReferList)){

            for (CloudDiskReferWrapper cloudDiskReferWrapper : diskReferList) {
                diskIds+= cloudDiskReferWrapper.getDiskId()+",";
            }
            diskIds = diskIds.substring(0,diskIds.length()-1);

        }
        return diskIds;
    }
}
