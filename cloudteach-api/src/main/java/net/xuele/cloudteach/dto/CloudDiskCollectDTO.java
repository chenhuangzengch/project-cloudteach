package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CloudDiskCollectDTO
 *
 * @author sunxh
 * @date 2015/7/1 0001
 */
public class CloudDiskCollectDTO implements Serializable{
    private static final long serialVersionUID = 5374351732781440638L;

    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String diskId;

    @Override
    public String toString() {
        return "CloudDiskCollectDTO{" +
                "diskId='" + diskId + '\'' +
                ", fileType=" + fileType +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }

    /**
     * 1	其他
     2	教案
     3	学案
     4	课件
     5	习题
     6	课程素材
     */
    private Integer fileType;

    /**
     * 文件名
     */
    private String name;

    /**
     * 如果是收藏来的资源，也会在这张表插入一条记录，但是ufOriginId不为空，表示源文件的ID
     */
    private String pid;

    /**
     * 同ufOriginId，收藏的资源，记录上传者
     */
    private String creator;

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
