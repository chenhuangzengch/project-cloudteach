package net.xuele.cloudteach.web.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/16 0016.
 * 资源返回包装
 * {
    "state":1,
    "resources":[
    {
        "id":24850,
        "code":"48A4746526D27A6760A89A0411653708",
        "nm":"拼音第一课过关测试卷",
        "ex":"doc",
        "mid":3,
        "type":1,
        "url":"http://panfile.xuele.net/s/34384134373436353236443237413637363041383941303431313635333730382e646f63"
    },
    {
         "id":17468,
         "code":"54E23EA83E0F678DEFC7F057B2C02872",
         "nm":"1aoe",
         "ex":"doc",
         "mid":3,
         "type":1,
         "url":"http://panfile.xuele.net/s/35344532334541383345304636373844454643374630353742324330323837322e646f63"
    }
   ]
  }
 */
public class ResourceWrapper implements Serializable {

    private int state;

    private List<SingleResource> resources = new ArrayList<SingleResource>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<SingleResource> getResources() {
        return resources;
    }

    public void setResources(List<SingleResource> resources) {
        this.resources = resources;
    }
}
