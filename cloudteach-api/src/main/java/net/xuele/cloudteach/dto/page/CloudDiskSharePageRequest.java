package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by panglx on 2015/6/8 0008.
 * panfile 分享请求对象
 */
public class CloudDiskSharePageRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 4723852646513401602L;
    /**
     * 分享用户编号
     */
    private String userId;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 文件类型
     * 1	其他
     * 2	教案
     * 3	学案
     * 4	课件
     * 5	习题
     * 6	课程素材
     */
    private Integer fileType;

    /**
     * 分享时间
     */
    private Date shareTime;

    /**
     * 运维人员对分享资源进行加精(置顶)
     * 0：不置顶
     * 1：置顶
     */
    private Integer boutique;

    /**
     * 分享范围状态
     * ALL = 0, 所有人
     * SCHOOL = 1,学校
     * AREA = 2   地区
     */
    private Integer shareType;

    /**
     * 地区编号
     */
    private String areaId;

    /**
     * 学校id
     */
    private String schoolId;

    /**
     * 扩展名类型
     */
    private Integer extType;

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }

    /**
     * 页面排序
     * 0，默认排序
     * 1，点赞最多(点赞数倒序)
     * 2，收藏最多(收藏数倒序)
     * 3，最新分享(分享时间倒序)
     * 4, 我收藏的
     * @return
     */
    private int order;

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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Integer getBoutique() {
        return boutique;
    }

    public void setBoutique(Integer boutique) {
        this.boutique = boutique;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

//	public int getPraiseTimes() {
//		return ufLikeCount;
//	}
//
//	public void setPraiseTimes(int ufLikeCount) {
//		this.ufLikeCount = ufLikeCount;
//	}
//
//	public int getSaveTimes() {
//		return ufSaved;
//	}
//
//	public void setSaveTimes(int ufSaved) {
//		this.ufSaved = ufSaved;
//	}

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "CloudDiskSharePageRequest{" +
                "userId='" + userId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", fileType=" + fileType +
                ", shareTime=" + shareTime +
                ", boutique=" + boutique +
                ", shareType=" + shareType +
                ", areaId='" + areaId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", extType=" + extType +
                ", order=" + order +
                '}';
    }
}
