package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.*;

import java.util.List;

/**
 * Created by hujx on 2015/8/10 0010.
 */
public class TeacherWorkWriteDetailWrapper {

    // 教师作业基本信息
    BasicWorkInfoViewDTO basicWorkInfo;

    // 教师录音信息
    WorkTapeFilesDTO workTapeFilesDTO;

    // 题目附件信息
    List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewList;

    public BasicWorkInfoViewDTO getBasicWorkInfo() {
        return basicWorkInfo;
    }

    public void setBasicWorkInfo(BasicWorkInfoViewDTO basicWorkInfo) {
        this.basicWorkInfo = basicWorkInfo;
    }


    public List<TeacherWorkFileDetailViewDTO> getTeacherWorkFileDetailViewList() {
        return teacherWorkFileDetailViewList;
    }

    public void setTeacherWorkFileDetailViewList(List<TeacherWorkFileDetailViewDTO> teacherWorkFileDetailViewList) {
        this.teacherWorkFileDetailViewList = teacherWorkFileDetailViewList;
    }

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }
}
