package net.xuele.cloudteach.web.wrapper;

/**
 * UserFileStickyWrapper
 * 用户文件置顶包装类
 * @author sunxh
 * @date 2015/6/26 0026
 */
public class UserFileStickyWrapper {

    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String ufId;

    /**
     * 用户文件的置顶状态
     0：不置顶
     1：置顶

     */
    private Byte stickyState;

    public String getUfId() {
        return ufId;
    }

    public void setUfId(String ufId) {
        this.ufId = ufId;
    }

    public Byte getStickyState() {
        return stickyState;
    }

    public void setStickyState(Byte stickyState) {
        this.stickyState = stickyState;
    }
}
