package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.web.common.CloudTeachEncryptUtil;

/**
 * CloudDiskUploadForm
 * 文件上传表单
 *
 * @author sunxh
 * @date 2015/7/22 0022
 */
public class CloudDiskUploadForm {
    /**
     * 学校ID
     */
    private String schoolId;

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
     * HDFS文件uri
     */
    private String fileUri;

    /**
     * 文件名
     */
    private String name;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 文件大小
     */
    private Integer size;

    public String getSchoolId() {
        return schoolId == null ? null : CloudTeachEncryptUtil.decrypt(schoolId);
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUserId() {
        return userId == null ? null : CloudTeachEncryptUtil.decrypt(userId);
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

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CloudDiskUploadForm{" +
                "schoolId='" + schoolId + '\'' +
                ", userId='" + userId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", fileType=" + fileType +
                ", fileUri='" + fileUri + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                '}';
    }
}
