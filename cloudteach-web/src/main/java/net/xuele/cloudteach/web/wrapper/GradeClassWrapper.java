package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/16 0016.
 * 年级和班级包装
 *
 * {
 * "name":"1年级1班",
 * "g":2014,
 * "c":1
 * },
 *
 */
public class GradeClassWrapper implements Serializable {

    private String name;
    private int g;
    private int c;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
