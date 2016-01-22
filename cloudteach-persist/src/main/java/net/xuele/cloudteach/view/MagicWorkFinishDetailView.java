package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtMagicQuestion;
import net.xuele.cloudteach.domain.CtMagicWorkAnswer;
import net.xuele.cloudteach.domain.CtMagicWorkAnswerFiles;
import net.xuele.cloudteach.domain.CtMagicWorkChallenge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/8/8 0008.
 */
public class MagicWorkFinishDetailView implements Serializable {

    // 提分宝作业基本信息
    BasicWorkInfoView basicMagicWorkInfo;

    // 提分宝题目详细
    List<CtMagicQuestion> magicQuestionDTOList;

    // 学生回答信息
    CtMagicWorkAnswer stuAnswerInfo;

    // 提交的挑战记录信息
    CtMagicWorkChallenge stuSubmitChallengeInfo;

    // 学生提交的附件
    List<CtMagicWorkAnswerFiles> stuSubmitFilesList;

    public BasicWorkInfoView getBasicMagicWorkInfo() {
        return basicMagicWorkInfo;
    }

    public void setBasicMagicWorkInfo(BasicWorkInfoView basicMagicWorkInfo) {
        this.basicMagicWorkInfo = basicMagicWorkInfo;
    }

    public List<CtMagicQuestion> getMagicQuestionDTOList() {
        return magicQuestionDTOList;
    }

    public void setMagicQuestionDTOList(List<CtMagicQuestion> magicQuestionDTOList) {
        this.magicQuestionDTOList = magicQuestionDTOList;
    }

    public CtMagicWorkAnswer getStuAnswerInfo() {
        return stuAnswerInfo;
    }

    public void setStuAnswerInfo(CtMagicWorkAnswer stuAnswerInfo) {
        this.stuAnswerInfo = stuAnswerInfo;
    }

    public CtMagicWorkChallenge getStuSubmitChallengeInfo() {
        return stuSubmitChallengeInfo;
    }

    public void setStuSubmitChallengeInfo(CtMagicWorkChallenge stuSubmitChallengeInfo) {
        this.stuSubmitChallengeInfo = stuSubmitChallengeInfo;
    }

    public List<CtMagicWorkAnswerFiles> getStuSubmitFilesList() {
        return stuSubmitFilesList;
    }

    public void setStuSubmitFilesList(List<CtMagicWorkAnswerFiles> stuSubmitFilesList) {
        this.stuSubmitFilesList = stuSubmitFilesList;
    }
}
