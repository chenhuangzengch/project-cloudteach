package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/17 0017.
 *    {
        "resHeight": 1,
        "resWidth": 0,
        "name": "《古诗三首》作者照片：李白",
        "type": 2,
        "uploadDate": null,
        "path": "http://panfile.xuele.net/s/34413844453544444346313830323641434644413842383437303930363641462e6a7067",
         "txt": "",
        "bytes": 0,
        "isRect": 1,
        "ex": "jpg",
        "resY": 475,
        "mid": "4",
        "resX": 424
     }
 */
public class SinglePageEBook implements Serializable{

    private int resHeight;
    private int resWidth;
    private String name;
    private int type;
    private String uploadDate;
    private String path;
    private String txt;
    private int bytes;
    private int isRect;
    private String ex;
    private int resY;
    private String mid;
    private int resX;

    public int getResHeight() {
        return resHeight;
    }

    public void setResHeight(int resHeight) {
        this.resHeight = resHeight;
    }

    public int getResWidth() {
        return resWidth;
    }

    public void setResWidth(int resWidth) {
        this.resWidth = resWidth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getIsRect() {
        return isRect;
    }

    public void setIsRect(int isRect) {
        this.isRect = isRect;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public int getResY() {
        return resY;
    }

    public void setResY(int resY) {
        this.resY = resY;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getResX() {
        return resX;
    }

    public void setResX(int resX) {
        this.resX = resX;
    }
}
