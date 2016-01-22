package net.xuele.cloudteach.dto.page;

import net.xuele.common.page.PageRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * CloudDiskPageRequest
 *
 * @author sunxh
 * @date 2015/6/24 0024
 */
public class CloudDiskPageRequest extends PageRequest implements Serializable{

    private static final long serialVersionUID = -6840667883181879765L;
    
    /**
     * 用户编号
     */
    private String userId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 1	其他
     * 2	教案
     * 3	学案
     * 4	课件
     * 5	习题
     * 6	课程素材
     */
    private Integer fileType;

    /**
     * 扩展名类型
     */
    private Integer extType;

    /**
     * 1正常0  已删除
     */
    private Integer status;

    /**
     * 用户文件的置顶状态
     0：不置顶
     1：置顶

     */
    private Integer stickyStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStickyStatus() {
        return stickyStatus;
    }

    public void setStickyStatus(Integer stickyStatus) {
        this.stickyStatus = stickyStatus;
    }

    @Override
    public String toString() {
        return "CloudDiskPageRequest{" +
                "userId='" + userId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", fileType=" + fileType +
                ", extType=" + extType +
                ", status=" + status +
                ", stickyStatus=" + stickyStatus +
                '}';
    }
}
