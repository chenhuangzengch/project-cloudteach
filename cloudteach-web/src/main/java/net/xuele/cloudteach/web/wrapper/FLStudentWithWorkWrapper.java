package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * FLStudentWithWorkWrapper
 *
 * @author sunxh
 * @date 15/9/14
 */
public class FLStudentWithWorkWrapper {
    private String studentName;
    private String studentId;
    private String studentIcon;

    /**
     * 提交状态:0未提交1已提交
     */
    private Integer subStatus;

    /**
     * 回答描述
     */
    private String context;

    /**
     * 附件列表
     */
    private List<FLFileReferWrapper> fileList;

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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<FLFileReferWrapper> getFileList() {
        return fileList;
    }

    public void setFileList(List<FLFileReferWrapper> fileList) {
        this.fileList = fileList;
    }

    public String getStudentIcon() {
        return studentIcon;
    }

    public void setStudentIcon(String studentIcon) {
        this.studentIcon = studentIcon;
    }
}
