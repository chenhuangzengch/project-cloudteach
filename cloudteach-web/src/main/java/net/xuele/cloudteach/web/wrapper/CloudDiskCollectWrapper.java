package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.CloudDiskCollectDTO;
import net.xuele.cloudteach.dto.CloudDiskDTO;
import net.xuele.common.exceptions.CloudteachException;
import org.springframework.beans.BeanUtils;

/**
 * UserFileCollectWrapper
 * 用户文件收藏包装类
 * @author sunxh
 * @date 2015/6/26 0026
 */
public class CloudDiskCollectWrapper {
    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String diskId;

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

    /**
     *
     * @param obj
     * 初始化UserFileCollectWrapper
     *
     */
    public CloudDiskCollectWrapper(Object obj) {
        if(obj instanceof CloudDiskDTO){
            BeanUtils.copyProperties((CloudDiskDTO)obj,this);
        }else if(obj instanceof CloudDiskCollectDTO){
            BeanUtils.copyProperties((CloudDiskCollectDTO)obj,this);
        }
    }

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
