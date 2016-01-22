package net.xuele.cloudteach.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.xuele.cloudteach.view.StudentNumView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cm.wang on 2015/8/23 0023.
 */
public class TranslateCommon {

    public static List<String> getSchoolIdList(List<StudentNumView> studentNumViewList){

        List<String> list = new ArrayList<String>();
        for(StudentNumView view : studentNumViewList){
            if(view.getStudentNum()>0){
                list.add(view.getClassId());
            }
        }
        return list;
    }

    public static String getClassInfoById(List<String> classList,String classJson) throws IOException {
        String classInfo = "";
        /*if(classList==null || classList.size()<=0){
            return classInfo;
        }if("[]".equals(classJson)){
            return classInfo;
        }*/
        JSONArray classJsonList = JSON.parseArray(classJson);


        for (int i = 0; i < classJsonList.size(); i++) {
            JSONObject o = classJsonList.getJSONObject(i);
            String jsonClassId = (String) o.get("classId");
            String jsonClassName = (String) o.get("className");
            if(!classList.contains(jsonClassId)){
                classInfo += jsonClassName+",";
            }
        }
        return classInfo.substring(0,classInfo.length()-1)+"没有学生";
    }
}
