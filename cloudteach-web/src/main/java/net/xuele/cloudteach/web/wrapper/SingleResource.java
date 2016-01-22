package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/17 0017.
 *     {
        "id":17468,
        "code":"54E23EA83E0F678DEFC7F057B2C02872",
        "nm":"1aoe",
        "ex":"doc",
        "mid":3,
        "type":1,
        "url":"http://panfile.xuele.net/s/35344532334541383345304636373844454643374630353742324330323837322e646f63"
        }
 */
public class SingleResource implements Serializable {

    private long id;

    private String code;

    private String name;

    private String ex;

    private int mid;

    private int type;

    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
