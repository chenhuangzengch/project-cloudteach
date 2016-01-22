package net.xuele.cloudteach.service.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.xuele.cloudteach.dto.Magic2StuAnsJsonDTO;
import net.xuele.cloudteach.dto.Magic2SubmitJsonDTO;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hujx on 2015/10/23 0023.
 * bean-json 转化工具类
 */
public class JsonUtil<E> {

    private E obj;

    public JsonUtil() {

    }

    public JsonUtil(E obj) {
        this.obj = obj;
    }

    public E getObj() {
        return obj;
    }

    public void setObj(E obj) {
        this.obj = obj;
    }

    /**
     * 提分宝2学生提交答案JSON格式转化
     *
     * @param json
     * @return
     */
    public static List<Magic2StuAnsJsonDTO> getModifiedMagic2AnsInfo(String json) {
        List<Magic2StuAnsJsonDTO> l1 = new ArrayList<>();
        // google.gson
        Gson gson = new Gson();
        Type t1 = new TypeToken<List<Magic2StuAnsJsonDTO>>() {
        }.getType();
        l1 = gson.fromJson(json, t1);
        return l1;
    }

    /**
     * 提分宝2学生提交答案DTO转JSON
     * @param dto
     * @return
     */
    public static String getMagic2SubmitJson(List<Magic2SubmitJsonDTO> dto) {
        // google.gson
        Gson gson = new Gson();
        String l1 = gson.toJson(dto);
        return l1;
    }

    /**
     * 通用，将json转化成对应的bean
     *
     * @param json
     * @param classz
     * @param <E>
     * @return
     */
    public static <E> E getJsonToSingleBean(String json, Class<E> classz) {
        Gson g = new Gson();
        E bean = g.fromJson(json, classz);
        return bean;
    }

    /**
     * 通用，将json转化成对应的list<bean>
     *
     * @param json
     * @param classz
     * @param <E>
     * @return
     */
    public static <E> List<E> getJsonToListBean(String json, Class<E[]> classz) {
        List<E> list = new ArrayList<>();
        // google.gson
        Gson g = new Gson();
        E[] array = g.fromJson(json, classz);
        Collections.addAll(list, array);
        return list;
    }


    /**
     * 通用，对象转化成json
     *
     * @param obj
     * @param <E>
     * @return
     * @throws IOException
     */
    public static <E> String getBeanToJson(E obj) throws IOException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static void main(String[] args) throws IOException {
        List<Magic2SubmitJsonDTO> dtos = new ArrayList<>();
        for(int i=0;i<4;i++){
            Magic2SubmitJsonDTO dto = new Magic2SubmitJsonDTO();
            List<String> aIds = new ArrayList<>();
            dto.setQueId("10001"+i);
            dto.setType(3);
            dto.setRw(1 / (i + 1));
            aIds.add("答案" + i);
            aIds.add("答案"+i);
            dto.setaIds(aIds);
            dtos.add(dto);
        }

        System.out.print(getMagic2SubmitJson(dtos));

    }

}
