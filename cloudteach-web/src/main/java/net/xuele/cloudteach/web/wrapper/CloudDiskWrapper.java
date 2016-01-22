package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.CloudDiskDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * CloudDiskWrapper
 *
 * @author sunxh
 * @date 2015/7/2 0002
 */
public class CloudDiskWrapper {

    /**
     * 文件编号
     */
    private String diskId;

    /**
     * 文件用户编号
     */
    private String creator;

    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String userId;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 文件唯一性编号
     */
    private String filePk;

    /**
     * HDFS文件uri
     */
    private String fileUri;

    /**
     * 云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
     */
    private Integer fileType;

    /**
     * 文件名
     */
    private String name;

    /**
     * 简介
     */
    private String description;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 扩展名类型
     */
    private Integer extType;

    /**
     * 扩展名图标类型
     */
    private String extIconType;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 用户文件的置顶状态
     0：不置顶
     1：置顶

     */
    private Integer stickyStatus;

    /**
     *  分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
     */
    private Integer shareStatus;

    /**
     * 最后一次分享的更新
     */
    private Date shareTime;

    /**
     * 0：非收藏
     1：收藏
     */
    private Integer collectStatus;

    /**
     * 收藏的源分享ID
     */
    private String pid;

    /**
     * 1正常0  已删除
     */
    private Integer status;

    public CloudDiskWrapper(CloudDiskDTO cloudDiskDTO) {
        BeanUtils.copyProperties(cloudDiskDTO,this);
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getFilePk() {
        return filePk;
    }

    public void setFilePk(String filePk) {
        this.filePk = filePk;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }

    public String getExtIconType() {
        return extIconType;
    }

    public void setExtIconType(String extIconType) {
        this.extIconType = extIconType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStickyStatus() {
        return stickyStatus;
    }

    public void setStickyStatus(Integer stickyStatus) {
        this.stickyStatus = stickyStatus;
    }

    public Integer getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
