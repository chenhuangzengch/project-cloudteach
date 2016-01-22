package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * GuidanceWorkViewDTO
 * 作业管理-预习作业
 * @author duzg
 * @date 2015/7/12 0002
 */
public class GuidanceWorkDetailViewDTO extends GuidanceWorkDTO   implements Serializable {

    private static final long serialVersionUID = 6500949030569497244L;

    /**
     * 课程名
     */
    private String unitName;
    /**
     * 科目号
     */
    private String subjectId;
    /**
     * 科目名
     */
    private String subjectName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "GuidanceWorkDetailViewDTO{" +
                "unitName='" + unitName + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}