package net.xuele.cloudteach.web.form;

/**
 * CloudDiskForm
 *
 * @author sunxh
 * @date 2015/7/2 0002
 */
public class CloudDiskForm {

    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String diskId;

    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String userId;

    /**
     * 课程编号
     */
    private String unitId;

    /**
     * 云盘文件类型：1其他,2,教案3学案4课件5习题6课程素材
     */
    private Integer fileType;

    /**
     * 扩展名类型(1其他,2ppt,3word,4视频,5录音,6图片)
     */
    private Integer extType;

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
}
