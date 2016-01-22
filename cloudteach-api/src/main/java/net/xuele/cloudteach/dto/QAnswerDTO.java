package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * QAnswerDTO
 *
 * @author panglx
 * @date on 2015/10/22 0022.
 */
public class QAnswerDTO implements Serializable {

    private static final long serialVersionUID = 1401167481555876264L;
    private String aId;

    private String qId;

    private Integer sortid;

    private Integer iscorrect;

    private String aContent;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId == null ? null : aId.trim();
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId == null ? null : qId.trim();
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public Integer getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(Integer iscorrect) {
        this.iscorrect = iscorrect;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent == null ? null : aContent.trim();
    }

    @Override
    public String toString() {
        return "QAnswerDTO{" +
                "aContent='" + aContent + '\'' +
                ", aId='" + aId + '\'' +
                ", qId='" + qId + '\'' +
                ", sortid=" + sortid +
                ", iscorrect=" + iscorrect +
                '}';
    }
}
