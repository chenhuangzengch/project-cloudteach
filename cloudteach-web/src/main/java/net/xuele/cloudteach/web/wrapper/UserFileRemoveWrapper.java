package net.xuele.cloudteach.web.wrapper;

/**
 * UserFileDeleteWrapper
 * 返回用户删除的结果
 *
 * @author sunxh
 * @date 2015/6/26 0026
 */
public class UserFileRemoveWrapper {

    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String ufId;

    /**
     * 5  正常
     * -100  已删除
     */
    private Integer ufState;

    public String getUfId() {
        return ufId;
    }

    public void setUfId(String ufId) {
        this.ufId = ufId;
    }

    public Integer getUfState() {
        return ufState;
    }

    public void setUfState(Integer ufState) {
        this.ufState = ufState;
    }
}
