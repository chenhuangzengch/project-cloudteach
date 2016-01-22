package net.xuele.cloudteach.domain;

/**
 * CtCloudDiskAmount
 *
 * @author sunxh
 * @date 15/9/16
 */
public class CtCloudDiskAmount {

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
