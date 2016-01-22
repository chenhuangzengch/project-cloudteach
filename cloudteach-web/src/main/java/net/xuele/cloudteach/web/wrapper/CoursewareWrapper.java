package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/6/17 0017.
 * 课件信息包装
 *
 */
public class CoursewareWrapper {

    private int style;
    private SinglePageCourseware[] pages;
    private int bg;
    private String name;
    private List<ResourceWrapper> resources;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public SinglePageCourseware[] getPages() {
        return pages;
    }

    public void setPages(SinglePageCourseware[] pages) {
        this.pages = pages;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceWrapper> getResources() {
        return resources;
    }

    public void setResources(List<ResourceWrapper> resources) {
        this.resources = resources;
    }
}
