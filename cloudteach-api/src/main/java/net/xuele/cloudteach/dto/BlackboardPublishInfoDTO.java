package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cm.wang on 2015/7/14 0014.
 */
public class BlackboardPublishInfoDTO implements Serializable {

    private static final long serialVersionUID = 7722638436089677587L;

    //板书发布表
    public BlackboardPublishDTO blackboardPublishDTO;

    @Override
    public String toString() {
        return "BlackboardPublishInfoDTO{" +
                "blackboardPublishDTO=" + blackboardPublishDTO +
                ", filesDTOList=" + filesDTOList +
                ", blackboardPublishClassDTOList=" + blackboardPublishClassDTOList +
                ", classJson='" + classJson + '\'' +
                '}';
    }

    //板书发布附件
    private List<BlackboardPublishFilesDTO> filesDTOList;

    //板书发布班级
    public List<BlackboardPublishClassDTO> blackboardPublishClassDTOList;

    private  String classJson;

    public BlackboardPublishDTO getBlackboardPublishDTO() {
        return blackboardPublishDTO;
    }

    public void setBlackboardPublishDTO(BlackboardPublishDTO blackboardPublishDTO) {
        this.blackboardPublishDTO = blackboardPublishDTO;
    }

    public List<BlackboardPublishClassDTO> getBlackboardPublishClassDTOList() {
        return blackboardPublishClassDTOList;
    }

    public void setBlackboardPublishClassDTOList(List<BlackboardPublishClassDTO> blackboardPublishClassDTOList) {
        this.blackboardPublishClassDTOList = blackboardPublishClassDTOList;
    }

    public String getClassJson() {
        return classJson;
    }

    public void setClassJson(String classJson) {
        this.classJson = classJson;
    }

    public List<BlackboardPublishFilesDTO> getFilesDTOList() {
        return filesDTOList;
    }

    public void setFilesDTOList(List<BlackboardPublishFilesDTO> filesDTOList) {
        this.filesDTOList = filesDTOList;
    }
}
