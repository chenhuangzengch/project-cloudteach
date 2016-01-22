package net.xuele.cloudteach.dto;

import java.io.Serializable;

public class SynUnitViewDTO implements Serializable {
    private static final long serialVersionUID = -1003513498804322881L;
    /**
     * 课程编号
     */
    private String uCode;
    /**
     * 教材版本号
     */
    private Integer editionId;
    /**
     * 年级学期编号
     */
    private String semestercode;
    /**
     * 课程名称
     */
    private String uName;
    /**
     * 顺序号
     */
    private String uOrder;

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public Integer getEditionId() {
        return editionId;
    }

    public void setEditionId(Integer editionId) {
        this.editionId = editionId;
    }

    public String getSemestercode() {
        return semestercode;
    }

    public void setSemestercode(String semestercode) {
        this.semestercode = semestercode;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuOrder() {
        return uOrder;
    }

    public void setuOrder(String uOrder) {
        this.uOrder = uOrder;
    }

    @Override
    public String toString() {
        return "SynUnitViewDTO{" +
                "uCode='" + uCode + '\'' +
                ", editionId=" + editionId +
                ", semestercode='" + semestercode + '\'' +
                ", uName='" + uName + '\'' +
                ", uOrder='" + uOrder + '\'' +
                '}';
    }
}