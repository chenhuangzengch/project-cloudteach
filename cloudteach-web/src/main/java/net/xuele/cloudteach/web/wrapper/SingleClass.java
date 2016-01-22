package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/16 0016.
 * 单个授课信息包装
 * {
 * "name":"",
 * "style":1,
 * "bg":1,
 * "pages":[],
 * "resources":[]
 * }
 *
 */
public class SingleClass implements Serializable {

    private String name;

    private int style;

    private int bg;

    private int[] pages;

    private ResourceWrapper[] resources;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int[] getPages() {
        return pages;
    }

    public void setPages(int[] pages) {
        this.pages = pages;
    }

    public ResourceWrapper[] getResources() {
        return resources;
    }

    public void setResources(ResourceWrapper[] resources) {
        this.resources = resources;
    }
}
