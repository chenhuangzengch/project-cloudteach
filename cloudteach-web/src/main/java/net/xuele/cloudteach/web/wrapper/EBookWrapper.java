package net.xuele.cloudteach.web.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/17 0017.
 * 电子课本包装
 * 电子课本 属于电子课件，可以理解为电子课件中的一个资源
 *
 *
 */
public class EBookWrapper {

    private int state;
    private String list;//历史原因取名为list 事实上这个字段的意思是这个电子课本的素材
    private List<String> pages = new ArrayList<String>();//电子课本页码，授课神器展示出来的页码
    private List<SinglePageEBook> resources = new ArrayList<SinglePageEBook>();//单页信息

    public List<SinglePageEBook> getResources() {
        return resources;
    }

    public void setResources(List<SinglePageEBook> resources) {
        this.resources = resources;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }
}
