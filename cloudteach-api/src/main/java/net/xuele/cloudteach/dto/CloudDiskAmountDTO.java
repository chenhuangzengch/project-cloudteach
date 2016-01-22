package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CloudDiskAmountDTO
 *
 * @author sunxh
 * @date 15/9/16
 */
public class CloudDiskAmountDTO implements Serializable {

    private static final long serialVersionUID = -5234928457903391254L;

    /**
     * 资源类型
     */
    private Integer fileType;

    /**
     * 资源数量
     */
    private Integer num;

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
