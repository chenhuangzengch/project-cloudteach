package net.xuele.cloudteach.web.form;

/**
 * Created by Administrator on 2015/6/17 0017.
 * 授课神器 相关 页面参数抽象
 *
 */
public class OnlineTeachParam {

    //课程编号
    private String kcid;

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid;
    }

    //课件编号， 一个课程可以有很多个课件
    private int id;

    //课件内容，json格式，
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
