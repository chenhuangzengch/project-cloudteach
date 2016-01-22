package net.xuele.cloudteach.web.wrapper;

/**
 * Created by Administrator on 2015/6/18 0018.
 * 单道讨论作业信息
 */
public class SingleDiscussJob {

    //讨论作业编号
    private int id;

    //讨论作业题目
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
