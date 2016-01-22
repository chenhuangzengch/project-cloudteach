package net.xuele.cloudteach.web.wrapper;

/**
 * UserFileWrapper
 * 用户网盘文件，用于包装返回给前端的数据
 * @author sunxh
 * @date 2015/6/26 0026
 */
public class UserFileWrapper {

    /**
     * 文件编号，老数据库为自增ID，新库可以使用uuid
     */
    private String ufId;

    /**
     * 1	其他
     2	教案
     3	学案
     4	课件
     5	习题
     6	课程素材
     */
    private Integer ufType;

    /**
     * 文件名
     */
    private String ufName;


    public String getUfId() {
        return ufId;
    }

    public void setUfId(String ufId) {
        this.ufId = ufId;
    }

    public Integer getUfType() {
        return ufType;
    }

    public void setUfType(Integer ufType) {
        this.ufType = ufType;
    }

    public String getUfName() {
        return ufName;
    }

    public void setUfName(String ufName) {
        this.ufName = ufName;
    }

}
