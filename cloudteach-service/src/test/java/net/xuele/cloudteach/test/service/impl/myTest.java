package net.xuele.cloudteach.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.xuele.cloudteach.dto.EditionsDTO;
import net.xuele.cloudteach.dto.Magic2StuAnsJsonDTO;
import net.xuele.cloudteach.service.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/6/30 0030.
 */
public class myTest {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(myTest.class);

    public static void main(String[] arg) throws Exception {


        // TODO Auto-generated method stub
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 4532; i++)    //55是一个动态变量 测试的时候先写死
            list.add(i);

        int count = (int)Math.floor((double)list.size()/1000);
        int yu = list.size() % 1000;
        List<Integer> subList;
        for (int i = 0; i < count; i++) {
            subList = list.subList(i * 1000, 1000 * (i + 1));
            System.out.println(subList);
        }
        subList = list.subList(count * 1000, count * 1000 + yu);
        System.out.print(subList);

//        // 题目id：queId；原题id：parentId；题目用时：queTime；答案id：aId ,'aId':'{['2353','123155','34565685','67897867','6797890']}'
//        String json1 = "[{'queId':'123','parentId':'1234','queTime':'14002342356','rw':'0','aIds':['2353','123155','34565685','67897867','6797890']}," +
//                "{'queId':'awda5443','parentId':'wfwrg3434f','queTime':'14002342356','rw':'1','aIds':['qweqwe','1qwe23523155','tgh5ry54','6789434rtawfw37867','679346y7gsdfd7890']}]";
//        String json2 = "{\"editionId\":\"1dsrr34gv5t5t467gghhadf346t5y\",\"pressName\":\"qweqwt2345345yegdfhtyu56y7545tygdrgh\",\"editionName\":\"qweqw90asdfjivekfhj546978390yfhwe\"}";
//
//        logger.error("#-----------------------------------------------------------------------------------------------------#");
//        logger.error("[json->list<bean>]");
//        List<Magic2StuAnsJsonDTO> l1 = JsonUtil.getJsonToListBean(json1, Magic2StuAnsJsonDTO[].class);
//        logger.error("List<Magic2StuAnsJsonDTO>:{}", l1);
//
//        logger.error("#-----------------------------------------------------------------------------------------------------#");
//        logger.error("[json->bean]");
//        EditionsDTO e1 = JsonUtil.getJsonToSingleBean(json2, EditionsDTO.class);
//        logger.error("EditionsDTO:{}", e1.toString());
//
//        logger.error("#-----------------------------------------------------------------------------------------------------#");
//        EditionsDTO e2 = new EditionsDTO();
//        e2.setEditionId("1dsrr34gv5t5t467gghhadf346t5y");
//        e2.setEditionName("qweqw90asdfjivekfhj546978390yfhwe");
//        e2.setPressName("qweqwt2345345yegdfhtyu56y7545tygdrgh");
//        logger.error("json1:{}", JsonUtil.getBeanToJson(l1));
//        logger.error("json2:{}", JsonUtil.getBeanToJson(e2));
    }
}
