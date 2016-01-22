package net.xuele.cloudteach.web.wrapper;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class StudentResponse {
    private String studentName;
    private String studentId;
    private String iconUrl;

    /**
     * 提交状态:0未提交1已提交
     */
    private Integer subStatus;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
