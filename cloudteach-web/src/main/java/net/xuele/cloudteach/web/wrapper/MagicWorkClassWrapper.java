package net.xuele.cloudteach.web.wrapper;
/**
 * MagicWorkClassWrapper
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class MagicWorkClassWrapper {
    private String workClassId;

    private String workId;

    private String classId;

    private Integer status;

    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId == null ? null : workClassId.trim();
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}