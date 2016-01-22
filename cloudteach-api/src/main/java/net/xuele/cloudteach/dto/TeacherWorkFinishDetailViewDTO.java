package net.xuele.cloudteach.dto;

import net.xuele.member.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/8/7 0007.
 */
public class TeacherWorkFinishDetailViewDTO implements Serializable {

    private static final long serialVersionUID = 7614049283489792321L;

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

    // 学生作业统计信息
    TeacherWorkItemAnswerStatisticsDTO teacherWorkItemAnswerStatisticsDTO;

    // 获取学生基本信息
    UserDTO stuInfo;

    public BasicWorkInfoViewDTO getBasicWorkInfo() {
        return basicWorkInfo;
    }

    public void setBasicWorkInfo(BasicWorkInfoViewDTO basicWorkInfo) {
        this.basicWorkInfo = basicWorkInfo;
    }

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
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

    public List<TeacherWorkItemAnswerFileDTO> getCtTeacherWorkItemAnswerFileList() {
        return ctTeacherWorkItemAnswerFileList;
    }

    public void setCtTeacherWorkItemAnswerFileList(List<TeacherWorkItemAnswerFileDTO> ctTeacherWorkItemAnswerFileList) {
        this.ctTeacherWorkItemAnswerFileList = ctTeacherWorkItemAnswerFileList;
    }

    public TeacherWorkItemAnswerStatisticsDTO getTeacherWorkItemAnswerStatisticsDTO() {
        return teacherWorkItemAnswerStatisticsDTO;
    }

    public void setTeacherWorkItemAnswerStatisticsDTO(TeacherWorkItemAnswerStatisticsDTO teacherWorkItemAnswerStatisticsDTO) {
        this.teacherWorkItemAnswerStatisticsDTO = teacherWorkItemAnswerStatisticsDTO;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UserDTO getStuInfo() {
        return stuInfo;
    }

    public void setStuInfo(UserDTO stuInfo) {
        this.stuInfo = stuInfo;
    }

    @Override
    public String toString() {
        return "TeacherWorkFinishDetailViewDTO{" +
                "basicWorkInfo=" + basicWorkInfo +
                ", workTapeFilesDTO=" + workTapeFilesDTO +
                ", teacherWorkFileDetailViewList=" + teacherWorkFileDetailViewList +
                ", teacherWorkItemAnswerDTO=" + teacherWorkItemAnswerDTO +
                ", ctTeacherWorkItemAnswerFileList=" + ctTeacherWorkItemAnswerFileList +
                ", teacherWorkItemAnswerStatisticsDTO=" + teacherWorkItemAnswerStatisticsDTO +
                ", stuInfo=" + stuInfo +
                '}';
    }
}
