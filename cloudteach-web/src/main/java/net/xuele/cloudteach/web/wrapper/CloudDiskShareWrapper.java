package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

/**
 * UserSharedCollectWrapper
 * Created by hujx on 2015/7/1 0001.
 */
public class CloudDiskShareWrapper {

    /**
     * 分享ID
     */
    private String shareId;
    /**
     * 云盘编号
     */
    private String diskId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 点赞总数
     */
    private Integer praiseTimes;

    /**
     * 点赞状态  add by panglx 2015/7/2
     * 0--未赞
     * 1--已赞
     */
    private int praiseState;

    /**
     * 收藏状态 add by panglx 2015/7/2
     * 0--未收藏
     * 1--已收藏
     */
    private int collectState;
    /**
     * 收藏数
     */
    private Integer saveTimes;

    /**
     * 分享时间
     */
    private Date shareTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 学校名称
     * @return
     */
    private String schoolName;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 用户分享状态--是否自己收藏
     */
    private int shareState;

    /**
     * 扩展名图标类型
     */
    private String extIconType;

    /**
     * HDFS文件key
     */
    private String fileUri;

    /**
     * 扩展名
     */
    private String extention;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 运维人员对分享资源进行加精(置顶)
     */
    private Integer boutique;

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getBoutique() {
        return boutique;
    }

    public void setBoutique(Integer boutique) {
        this.boutique = boutique;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getShareState() {
        return shareState;
    }

    public void setShareState(int shareState) {
        this.shareState = shareState;
    }

    public int getPraiseState() {
        return praiseState;
    }

    public void setPraiseState(int praiseState) {
        this.praiseState = praiseState;
    }

    public Integer getSaveTimes() {
        return saveTimes;
    }

    public void setSaveTimes(Integer saveTimes) {
        this.saveTimes = saveTimes;
    }

    public int getCollectState() {
        return collectState;
    }

    public void setCollectState(int collectState) {
        this.collectState = collectState;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }
}
