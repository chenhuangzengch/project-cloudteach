package net.xuele.cloudteach.dto;

import java.io.Serializable;
/**
 * ExerciseWorkFilesDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class ExerciseWorkFilesDTO implements Serializable {
    private static final long serialVersionUID = -6749007222471421611L;
    private String fileId;

    private String workId;

    private String diskId;

    private Integer status;

    private String schoolId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId == null ? null : diskId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExerciseWorkFilesDTO{" +
                "fileId='" + fileId + '\'' +
                ", workId='" + workId + '\'' +
                ", diskId='" + diskId + '\'' +
                ", status=" + status +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}