package net.xuele.cloudteach.web.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/18 0018.
 * 单道电子作业信息
 */
public class SingleElectronicJob {

    //电子作业编号
    private int id;

    //电子作业
    private String content;

    //作业类型 【】
    private int type;

    //学生作业完成情况
    private List<SingleStudentElectronicJob> tlist = new ArrayList<SingleStudentElectronicJob>();


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SingleStudentElectronicJob> getTlist() {
        return tlist;
    }

    public void setTlist(List<SingleStudentElectronicJob> tlist) {
        this.tlist = tlist;
    }
}
