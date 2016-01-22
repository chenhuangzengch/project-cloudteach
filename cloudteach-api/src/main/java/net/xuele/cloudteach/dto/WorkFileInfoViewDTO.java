package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用于显示作业详情中包含的附件信息
 * Created by hujx on 2015/7/29 0029.
 */
public class WorkFileInfoViewDTO implements Serializable {

    private static final long serialVersionUID = -5540204485607009329L;

    /**
     * 预习，电子，语音作业附件详细
     */
    List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewDTOList;

    /**
     *  提分宝题目详细
     */
    List<MagicQuestionDTO> magicQuestionDTOList;

    public List<MagicQuestionDTO> getMagicQuestionDTOList() {
        return magicQuestionDTOList;
    }

    public void setMagicQuestionDTOList(List<MagicQuestionDTO> magicQuestionDTOList) {
        this.magicQuestionDTOList = magicQuestionDTOList;
    }

    public List<TeacherWorkFileDetailViewDTO> getTeacherWorkFileDetailViewDTOList() {
        return teacherWorkFileDetailViewDTOList;
    }

    public void setTeacherWorkFileDetailViewDTOList(List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewDTOList) {
        this.teacherWorkFileDetailViewDTOList = teacherWorkFileDetailViewDTOList;
    }

    @Override
    public String toString() {
        return "WorkFileInfoViewDTO{" +
                "teacherWorkFileDetailViewDTOList=" + teacherWorkFileDetailViewDTOList +
                ", magicQuestionDTOList=" + magicQuestionDTOList +
                '}';
    }
}
