package net.xuele.cloudteach.dto;

import net.xuele.member.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/8/8 0008.
 */
public class SynclassWorkFinishDetailViewDTO implements Serializable {

    private static final long serialVersionUID = -2529982285708060687L;

    // 同步课堂作业基本信息
    BasicWorkInfoViewDTO basicWorkInfo;

    // 教师录音信息
    WorkTapeFilesDTO workTapeFilesDTO;

    // 同步课堂游戏信息
    List<SynclassWorkGameInfoViewDTO> synclassGameInfo;

    // 学生作业回答信息
    SynclassWorkStudentDTO stuAnswerInfo;

    // 同步课堂，学生玩成游戏详细
    List<SynclassWorkGamePlayInfoViewDTO> stuGamePlayResult;

    // 学生回答统计信息
    SynclassWorkStudentStatisticsDTO synclassWorkStudentStatisticsDTO;

    // 学生信息(移动端用)
    UserDTO stuInfo;

    public BasicWorkInfoViewDTO getBasicWorkInfo() {
        return basicWorkInfo;
    }

    public void setBasicWorkInfo(BasicWorkInfoViewDTO basicWorkInfo) {
        this.basicWorkInfo = basicWorkInfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SynclassWorkStudentDTO getStuAnswerInfo() {
        return stuAnswerInfo;
    }

    public void setStuAnswerInfo(SynclassWorkStudentDTO stuAnswerInfo) {
        this.stuAnswerInfo = stuAnswerInfo;
    }

    public List<SynclassWorkGamePlayInfoViewDTO> getStuGamePlayResult() {
        return stuGamePlayResult;
    }

    public void setStuGamePlayResult(List<SynclassWorkGamePlayInfoViewDTO> stuGamePlayResult) {
        this.stuGamePlayResult = stuGamePlayResult;
    }

    public List<SynclassWorkGameInfoViewDTO> getSynclassGameInfo() {
        return synclassGameInfo;
    }

    public void setSynclassGameInfo(List<SynclassWorkGameInfoViewDTO> synclassGameInfo) {
        this.synclassGameInfo = synclassGameInfo;
    }

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }

    public SynclassWorkStudentStatisticsDTO getSynclassWorkStudentStatisticsDTO() {
        return synclassWorkStudentStatisticsDTO;
    }

    public void setSynclassWorkStudentStatisticsDTO(SynclassWorkStudentStatisticsDTO synclassWorkStudentStatisticsDTO) {
        this.synclassWorkStudentStatisticsDTO = synclassWorkStudentStatisticsDTO;
    }

    public UserDTO getStuInfo() {
        return stuInfo;
    }

    public void setStuInfo(UserDTO stuInfo) {
        this.stuInfo = stuInfo;
    }

    @Override
    public String toString() {
        return "SynclassWorkFinishDetailViewDTO{" +
                "basicWorkInfo=" + basicWorkInfo +
                ", workTapeFilesDTO=" + workTapeFilesDTO +
                ", synclassGameInfo=" + synclassGameInfo +
                ", stuAnswerInfo=" + stuAnswerInfo +
                ", stuGamePlayResult=" + stuGamePlayResult +
                ", synclassWorkStudentStatisticsDTO=" + synclassWorkStudentStatisticsDTO +
                ", stuInfo=" + stuInfo +
                '}';
    }
}
