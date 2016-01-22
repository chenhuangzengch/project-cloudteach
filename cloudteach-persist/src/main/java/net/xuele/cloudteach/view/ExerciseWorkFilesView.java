package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtCloudDisk;

/**
 * CtExerciseWorkFiles
 *
 * @author duzg
 * @date 2015/7/18 0002
 */
public class ExerciseWorkFilesView extends CtCloudDisk {
    private String fileId;

    private String workId;

    private String diskId;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}