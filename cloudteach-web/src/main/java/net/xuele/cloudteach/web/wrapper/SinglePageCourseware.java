package net.xuele.cloudteach.web.wrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/17 0017.
 * 单页课件信息
 *  {
      "text": "《小白兔和小灰兔》课件1",
      "x": 140,
      "y": 34,
      "property": {
      "path": "http://panfile.xuele.net/s/43313344323531363131333642423837463441303844323246463445354146462e707074",
      "width": 720,
      "height": 540
     },
      "type": 1,
      "index": 3,
      "isOpen": 1
    }
 */
public class SinglePageCourseware {

    private String text;
    private int x;
    private int y;
    private Map<String,String> property = new HashMap<String,String>();
    private int type;
    private int index;
    private int isOpen;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Map<String, String> getProperty() {
        return property;
    }

    public void setProperty(Map<String, String> property) {
        this.property = property;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
}
