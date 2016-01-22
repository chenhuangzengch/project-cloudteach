package net.xuele.cloudteach.web.wrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/18 0018.
 * 单个学生电子作业
 */
public class SingleStudentElectronicJob {

    //用户编号
    private String userid;

    //用户姓名
    private String name;

    //用户作业信息
    /*
      "url": "http://xlshisheng.xuele.net/schoolid-10001/5878/showfile",
      "type": 3,
      "ex": "mp3"
     */
    private Map<String,String> file = new HashMap<String,String>();


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getFile() {
        return file;
    }

    public void setFile(Map<String, String> file) {
        this.file = file;
    }
}
