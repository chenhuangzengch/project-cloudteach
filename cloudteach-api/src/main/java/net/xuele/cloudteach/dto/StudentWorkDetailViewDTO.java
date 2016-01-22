package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/7/15 0015.
 */
public class StudentWorkDetailViewDTO implements Serializable {
    private static final long serialVersionUID = 6940717693600804876L;

    // 预习作业ID
    private String workId;

    // 预习作业导学项ID
    private String workItemId;

    // 预习作业学生回答ID
    private String answerId;

    // 学生ID
    private String studentId;

    // 预习项描述
    private String guidanceContext;

    // 提交方式图片
    private int subImage;

    // 提交方式录音
    private int subTape;

    // 提交方式视频
    private int subVideo;

    // 提交方式其他
    private int subOther;

    // 学生头像
    private String studentHeadIcon;

    // 学生回答描述
    private String answerContext;

    // 附件列表封装
    List<GuidanceItemFilesDetailDTO> guidanceItemFilesDetailDTOList;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public String getGuidanceContext() {
        return guidanceContext;
    }

    public void setGuidanceContext(String guidanceContext) {
        this.guidanceContext = guidanceContext;
    }

    public List<GuidanceItemFilesDetailDTO> getGuidanceItemFilesDetailDTOList() {
        return guidanceItemFilesDetailDTOList;
    }

    public void setGuidanceItemFilesDetailDTOList(List<GuidanceItemFilesDetailDTO> guidanceItemFilesDetailDTOList) {
        this.guidanceItemFilesDetailDTOList = guidanceItemFilesDetailDTOList;
    }

    public String getStudentHeadIcon() {
        return studentHeadIcon;
    }

    public void setStudentHeadIcon(String studentHeadIcon) {
        this.studentHeadIcon = studentHeadIcon;
    }

    public int getSubImage() {
        return subImage;
    }

    public void setSubImage(int subImage) {
        this.subImage = subImage;
    }

    public int getSubOther() {
        return subOther;
    }

    public void setSubOther(int subOther) {
        this.subOther = subOther;
    }

    public int getSubTape() {
        return subTape;
    }

    public void setSubTape(int subTape) {
        this.subTape = subTape;
    }

    public int getSubVideo() {
        return subVideo;
    }

    public void setSubVideo(int subVideo) {
        this.subVideo = subVideo;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "StudentWorkDetailViewDTO{" +
                "workId='" + workId + '\'' +
                ", workItemId='" + workItemId + '\'' +
                ", answerId='" + answerId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", guidanceContext='" + guidanceContext + '\'' +
                ", subImage=" + subImage +
                ", subTape=" + subTape +
                ", subVideo=" + subVideo +
                ", subOther=" + subOther +
                ", studentHeadIcon='" + studentHeadIcon + '\'' +
                ", answerContext='" + answerContext + '\'' +
                ", guidanceItemFilesDetailDTOList=" + guidanceItemFilesDetailDTOList +
                '}';
    }
}
