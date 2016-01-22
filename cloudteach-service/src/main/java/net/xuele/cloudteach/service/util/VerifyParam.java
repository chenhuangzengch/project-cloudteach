package net.xuele.cloudteach.service.util;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * 验证参数合法性
 * Created by duzg on 2015/7/30 0002.
 */
public class VerifyParam {

    /**
     * @param classList
     * @param classJson
     * @return boolean
     * 验证作业发布班级班级ID信息List与json数据是否一致
     */
    public static boolean verifyClassInfo(List<String> classList,String classJson) throws IOException {
        if(classList==null || classList.size()<=0){
            return false;
        }
        if(StringUtils.isEmpty(classJson)){
            return false;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JSONArray classJsonList = JSON.parseArray(classJson);

        if(classJsonList!=null && classJsonList.size()!=classList.size()){
            return false;
        }

        for (int i = 0; i < classJsonList.size(); i++) {
            JSONObject o = classJsonList.getJSONObject(i);
            String jsonClassId = (String) o.get("classId");
            String jsonClassName = (String) o.get("className");

            boolean hasId = false;
            for(String classId2 : classList){
                if(classId2.equals(jsonClassId)){
                    hasId = true;
                    break;
                }
            }
            if(!hasId){
                return false;
            }
        }
        return true;
    }

}
