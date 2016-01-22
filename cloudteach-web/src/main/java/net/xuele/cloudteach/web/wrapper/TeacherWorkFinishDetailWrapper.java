package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.*;

import java.util.List;

/**
 * Created by Administrator on 2015/8/10 0010.
 */
public class TeacherWorkFinishDetailWrapper {

    // 教师作业基本信息
    BasicWorkInfoViewDTO basicWorkInfo;

    // 教师录音信息
    WorkTapeFilesDTO workTapeFilesDTO;

    // 题目附件信息
    List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewList;

    // 学生回答信息
    TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO;

    // 学生添加附件信息
    List<TeacherWorkItemAnswerFileDTO> ctTeacherWorkItemAnswerFileList;

    public BasicWorkInfoViewDTO getBasicWorkInfo() {
        return basicWorkInfo;
    }

    public void setBasicWorkInfo(BasicWorkInfoViewDTO basicWorkInfo) {
        this.basicWorkInfo = basicWorkInfo;
    }

    public List<TeacherWorkItemAnswerFileDTO> getCtTeacherWorkItemAnswerFileList() {
        return ctTeacherWorkItemAnswerFileList;
    }

    public void setCtTeacherWorkItemAnswerFileList(List<TeacherWorkItemAnswerFileDTO> ctTeacherWorkItemAnswerFileList) {
        this.ctTeacherWorkItemAnswerFileList = ctTeacherWorkItemAnswerFileList;
    }

    public List<TeacherWorkFileDetailViewDTO> getTeacherWorkFileDetailViewList() {
        return teacherWorkFileDetailViewList;
    }

    public void setTeacherWorkFileDetailViewList(List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewList) {
        this.teacherWorkFileDetailViewList = teacherWorkFileDetailViewList;
    }

    public TeacherWorkItemAnswerDTO getTeacherWorkItemAnswerDTO() {
        return teacherWorkItemAnswerDTO;
    }

    public void setTeacherWorkItemAnswerDTO(TeacherWorkItemAnswerDTO teacherWorkItemAnswerDTO) {
        this.teacherWorkItemAnswerDTO = teacherWorkItemAnswerDTO;
    }

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }
}
