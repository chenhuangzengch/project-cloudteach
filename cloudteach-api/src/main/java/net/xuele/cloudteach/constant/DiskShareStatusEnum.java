package net.xuele.cloudteach.constant;

/**
 * DiskShareStatusEnum
 *      分享状态:ShareFail = 3,分享审核不通过;Nomal = 0,正常可分享;Share = 1,审核中; Success = 2审核成功
 * @author sunxh
 * @date 2015/11/11
 */
public enum DiskShareStatusEnum {

    /**
     *  正常可分享
     */
    NOMAL(0),

    /**
     *  审核中
     */
    SHARE(1),

    /**
     * 审核成功
     */
    SUCCESS(2),

    /**
     * 分享审核不通过
     */
    SHAREFAIL(3)
    ;

    private Integer status;

    DiskShareStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
