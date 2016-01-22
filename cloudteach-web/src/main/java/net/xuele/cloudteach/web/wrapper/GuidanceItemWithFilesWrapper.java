package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.CloudDiskReferDTO;
import net.xuele.cloudteach.dto.GuidanceItemWithFilesDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GuidanceItemWithFilesWrapper
 *
 * @author sunxh
 * @date 2015/7/17 0017
 */
public class GuidanceItemWithFilesWrapper {

    /**
     * 预习项ID
     */
    private String itemId;

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
     * 状态
     */
    private Integer status;

    /**
     * 附件列表
     */
    private List<CloudDiskReferWrapper> diskReferList;

    /**
     * 由GuidanceItemWithFilesDTO初始化
     *
     * @param itemWithFilesDTO
     */
    public GuidanceItemWithFilesWrapper(GuidanceItemWithFilesDTO itemWithFilesDTO) {

        BeanUtils.copyProperties(itemWithFilesDTO, this);
        List<CloudDiskReferWrapper> diskReferWrapperList = new ArrayList<>();

        //如果附件非空，拷贝附件
        if (CollectionUtils.isNotEmpty(itemWithFilesDTO.getDiskReferDTOList())) {
            for (CloudDiskReferDTO diskReferDTO : itemWithFilesDTO.getDiskReferDTOList()) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CloudDiskReferWrapper> getDiskReferList() {
        return diskReferList;
    }

    public void setDiskReferList(List<CloudDiskReferWrapper> diskReferList) {
        this.diskReferList = diskReferList;
    }
}
