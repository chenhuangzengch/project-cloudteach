package net.xuele.cloudteach.dto;

import net.xuele.member.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/8/8 0008.
 */
public class MagicWorkFinishDetailViewDTO implements Serializable {

    private static final long serialVersionUID = 1818734141376629138L;

    // 提分宝作业基本信息
    BasicWorkInfoViewDTO basicMagicWorkInfo;

    // 教师录音信息
    WorkTapeFilesDTO workTapeFilesDTO;

    // 提分宝题目详细
    List<MagicQuestionDTO> magicQuestionDTOList;

    // 学生回答信息
    MagicWorkAnswerDTO stuAnswerInfo;

    // 提交的挑战记录信息
    MagicWorkChallengeDTO stuSubmitChallengeInfo;

    // 获取学生已经提交的提分宝题目以及题目讲解
    List<MagicWorkChallengeQueViewDTO> stuSubmitMQA;

    // 学生提交的附件
    List<MagicWorkAnswerFilesDTO> stuSubmitFilesList;

    // 学生作业统计信息
    MagicWorkAnswerStatisticsDTO magicWorkAnswerStatisticsDTO;

    // 学生信息(移动端用)
    UserDTO stuInfo;

    public BasicWorkInfoViewDTO getBasicMagicWorkInfo() {
        return basicMagicWorkInfo;
    }

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }

    public void setBasicMagicWorkInfo(BasicWorkInfoViewDTO basicMagicWorkInfo) {
        this.basicMagicWorkInfo = basicMagicWorkInfo;
    }

    public List<MagicQuestionDTO> getMagicQuestionDTOList() {
        return magicQuestionDTOList;
    }

    public void setMagicQuestionDTOList(List<MagicQuestionDTO> magicQuestionDTOList) {
        this.magicQuestionDTOList = magicQuestionDTOList;
    }

    public MagicWorkAnswerDTO getStuAnswerInfo() {
        return stuAnswerInfo;
    }

    public void setStuAnswerInfo(MagicWorkAnswerDTO stuAnswerInfo) {
        this.stuAnswerInfo = stuAnswerInfo;
    }

    public MagicWorkChallengeDTO getStuSubmitChallengeInfo() {
        return stuSubmitChallengeInfo;
    }

    public void setStuSubmitChallengeInfo(MagicWorkChallengeDTO stuSubmitChallengeInfo) {
        this.stuSubmitChallengeInfo = stuSubmitChallengeInfo;
    }

    public List<MagicWorkAnswerFilesDTO> getStuSubmitFilesList() {
        return stuSubmitFilesList;
    }

    public void setStuSubmitFilesList(List<MagicWorkAnswerFilesDTO> stuSubmitFilesList) {
        this.stuSubmitFilesList = stuSubmitFilesList;
    }

    public MagicWorkAnswerStatisticsDTO getMagicWorkAnswerStatisticsDTO() {
        return magicWorkAnswerStatisticsDTO;
    }

    public void setMagicWorkAnswerStatisticsDTO(MagicWorkAnswerStatisticsDTO magicWorkAnswerStatisticsDTO) {
        this.magicWorkAnswerStatisticsDTO = magicWorkAnswerStatisticsDTO;
    }

    public List<MagicWorkChallengeQueViewDTO> getStuSubmitMQA() {
        return stuSubmitMQA;
    }

    public void setStuSubmitMQA(List<MagicWorkChallengeQueViewDTO> stuSubmitMQA) {
        this.stuSubmitMQA = stuSubmitMQA;
    }

    public UserDTO getStuInfo() {
        return stuInfo;
    }

    public void setStuInfo(UserDTO stuInfo) {
        this.stuInfo = stuInfo;
    }

    @Override
    public String toString() {
        return "MagicWorkFinishDetailViewDTO{" +
                "basicMagicWorkInfo=" + basicMagicWorkInfo +
                ", workTapeFilesDTO=" + workTapeFilesDTO +
                ", magicQuestionDTOList=" + magicQuestionDTOList +
                ", stuAnswerInfo=" + stuAnswerInfo +
                ", stuSubmitChallengeInfo=" + stuSubmitChallengeInfo +
                ", stuSubmitMQA=" + stuSubmitMQA +
                ", stuSubmitFilesList=" + stuSubmitFilesList +
                ", magicWorkAnswerStatisticsDTO=" + magicWorkAnswerStatisticsDTO +
                ", stuInfo=" + stuInfo +
                '}';
    }
}
