package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CloudDiskRenameDTO
 *
 * @author sunxh
 * @date 2015/7/2 0002
 */
public class CloudDiskUpdateDTO implements Serializable {
    private static final long serialVersionUID = 5641512165649104543L;

    /**
     * 文件编号
     */
    private String diskId;

    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String userId;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
     */
    private Integer fileType;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 用户文件的置顶状态
     * 0：不置顶
     * 1：置顶
     */
    private Integer stickyStatus;

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStickyStatus() {
        return stickyStatus;
    }

    public void setStickyStatus(Integer stickyStatus) {
        this.stickyStatus = stickyStatus;
    }

    @Override
    public String toString() {
        return "CloudDiskUpdateDTO{" +
                "diskId='" + diskId + '\'' +
                ", userId='" + userId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", fileType=" + fileType +
                ", unitId='" + unitId + '\'' +
                ", name='" + name + '\'' +
                ", stickyStatus=" + stickyStatus +
                '}';
    }
}
