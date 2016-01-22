package net.xuele.cloudteach.constant;

/**
 * DiskFileOperationEnum
 *
 * @author sunxh
 * @date 2015/7/4 0004
 */
public enum DiskFileOperationEnum {

    /**
     * 下载
     */
    DOWNLOAD(1),

    /**
     * 预览
     */
    VIEW(2),

    /**
     * 关联预览
     */
    RELATEDVIEW(3)
    ;

    private Integer operationCode;

    DiskFileOperationEnum(Integer operationCode) {
        this.operationCode = operationCode;
    }

    public Integer getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(Integer operationCode) {
        this.operationCode = operationCode;
    }
}
