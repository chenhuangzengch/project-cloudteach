package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.dto.BlackboardPublishFilesDTO;

/**
 * Created by cm.wang on 2015/7/24 0024.
 */
public class BlackboardPublishUploadForm {


    /**
     * HDFS文件url
     */
    private String fileKey;

    /**
     * 文件名
     */
    private String name;

    /**
     * extension
     * 扩展名
     */
    private String type;

    /**
     * 文件大小
     */
    private Integer size;

    public BlackboardPublishFilesDTO parseBlackboardPublishFilesDTO(String schoolId){
        BlackboardPublishFilesDTO filesDTO = new BlackboardPublishFilesDTO();
        filesDTO.setFileName(this.getName());
        filesDTO.setUrl(this.getFileKey());
        filesDTO.setExtension(this.getType().substring(1, this.getType().length()));
        filesDTO.setSize(this.getSize());
        filesDTO.setSchoolId(schoolId);
        return filesDTO;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
