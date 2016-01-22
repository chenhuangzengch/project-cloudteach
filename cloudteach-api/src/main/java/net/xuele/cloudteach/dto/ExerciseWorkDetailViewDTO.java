package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * ExerciseWorkDTO
 *
 * @author duzg
 * @date 2015/7/7 0002
 */
public class ExerciseWorkDetailViewDTO extends ExerciseWorkDTO  implements Serializable{

    private static final long serialVersionUID = -240828648823788602L;

    @Override
    public String toString() {
        return "ExerciseWorkDetailViewDTO{" +
                "unitName='" + unitName + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

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
}