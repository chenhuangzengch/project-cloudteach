package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.*;

import java.util.Date;
import java.util.List;

/**
 * Created by hujx on 2015/8/4 0004.
 */
public class StudentWorkListWrapper {

    /**
     * 以下用于学生已经完成了该作业的显示
     *
     * @return
     */
    // 学生名字
    //private String studentName;

    // 学生头像
    //private String studentHeadIcon;

    // 学生提交作业时间
    private Date submitTime;

    // 学生作业回答
    private String answerContext;

    // 学生获得分数
    private int score;

    // 学生回答附件信息
    private List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList;

    // 学生提分宝挑战记录
    private MagicWorkChallengeTimesViewDTO magicWorkChallengeTimesViewDTO;

    // 提分宝作业附件
    private List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList;

    // 学生同步课堂挑战记录
    private List<SynclassWorkPlayViewDTO> synclassWorkPlayViewDTOList;

    // 作业ID
    private String workId;

    // 教师名字
    private String teacherName;

    // 教师头像
    private String teacherHeadIcon;

    // 作业类型: 1预习作业，2提分宝作业，3同步课堂作业，4电子作业
    private int workType;

    // 发布时间
    private Date publishTime;

    // 作业描述
    private String context;

    // 作业项个数
    private int workItemCount;

    // 最晚提交时间
    private Date endTime;

    // 最晚提交天数，通过endTime-publishTime计算得到(毫秒级别)
    private long lastCommitDays;

    // 课程名称
    private String unitName;

    // 科目名称
    private String subjectName;

    // 教师职称
    private String positionName;

    // 提交状态
    private int subStatus;

    // 教师录音附件
    private WorkTapeFilesDTO workTapeFilesDTO;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherHeadIcon() {
        return teacherHeadIcon;
    }

    public void setTeacherHeadIcon(String teacherHeadIcon) {
        this.teacherHeadIcon = teacherHeadIcon;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public int getWorkItemCount() {
        return workItemCount;
    }

    public void setWorkItemCount(int workItemCount) {
        this.workItemCount = workItemCount;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

//    public String getStudentHeadIcon() {
//        return studentHeadIcon;
//    }
//
//    public void setStudentHeadIcon(String studentHeadIcon) {
//        this.studentHeadIcon = studentHeadIcon;
//    }
//
//    public String getStudentName() {
//        return studentName;
//    }
//
//    public void setStudentName(String studentName) {
//        this.studentName = studentName;
//    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public List<MagicWorkAnswerFilesDTO> getMagicWorkAnswerFilesDTOList() {
        return magicWorkAnswerFilesDTOList;
    }

    public void setMagicWorkAnswerFilesDTOList(List<MagicWorkAnswerFilesDTO> magicWorkAnswerFilesDTOList) {
        this.magicWorkAnswerFilesDTOList = magicWorkAnswerFilesDTOList;
    }

    public MagicWorkChallengeTimesViewDTO getMagicWorkChallengeTimesViewDTO() {
        return magicWorkChallengeTimesViewDTO;
    }

    public void setMagicWorkChallengeTimesViewDTO(MagicWorkChallengeTimesViewDTO magicWorkChallengeTimesViewDTO) {
        this.magicWorkChallengeTimesViewDTO = magicWorkChallengeTimesViewDTO;
    }

    public List<SynclassWorkPlayViewDTO> getSynclassWorkPlayViewDTOList() {
        return synclassWorkPlayViewDTOList;
    }

    public void setSynclassWorkPlayViewDTOList(List<SynclassWorkPlayViewDTO> synclassWorkPlayViewDTOList) {
        this.synclassWorkPlayViewDTOList = synclassWorkPlayViewDTOList;
    }

    public List<TeacherWorkItemAnswerFileDTO> getTeacherWorkItemAnswerFileDTOList() {
        return teacherWorkItemAnswerFileDTOList;
    }

    public void setTeacherWorkItemAnswerFileDTOList(List<TeacherWorkItemAnswerFileDTO> teacherWorkItemAnswerFileDTOList) {
        this.teacherWorkItemAnswerFileDTOList = teacherWorkItemAnswerFileDTOList;
    }

    public WorkTapeFilesDTO getWorkTapeFilesDTO() {
        return workTapeFilesDTO;
    }

    public void setWorkTapeFilesDTO(WorkTapeFilesDTO workTapeFilesDTO) {
        this.workTapeFilesDTO = workTapeFilesDTO;
    }

    public long getLastCommitDays() {
        return lastCommitDays;
    }

    public void setLastCommitDays(long lastCommitDays) {
        this.lastCommitDays = lastCommitDays;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(int subStatus) {
        this.subStatus = subStatus;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
