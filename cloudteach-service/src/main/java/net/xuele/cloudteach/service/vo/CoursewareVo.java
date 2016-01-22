package net.xuele.cloudteach.service.vo;

//import com.alibaba.dubbo.common.json.JSONArray;
//import com.alibaba.dubbo.common.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.xuele.cloudteach.service.util.JaxbUtil;
import sun.rmi.rmic.iiop.IDLNames;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "shouke")
public class CoursewareVo {
    private String name;
    private Integer style;
    private Integer bg;
    private List<PagesVo> pages;
    private List<ResourcesVo> resources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getBg() {
        return bg;
    }

    public void setBg(Integer bg) {
        this.bg = bg;
    }

    @XmlElementWrapper(name = "pages")
    @XmlElement(name = "page")
    public List<PagesVo> getPages() {
        return pages;
    }

    public void setPages(List<PagesVo> pages) {
        this.pages = pages;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public List<ResourcesVo> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesVo> resources) {
        this.resources = resources;
    }

    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException, JAXBException {
        test2();
    }

    public static void test2() throws IOException, InvocationTargetException, IllegalAccessException, JAXBException {
        File file = new File("F:\\testZip\\CoursewareGetByID.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        Map dataMap = JSON.parseObject(sb.toString(), Map.class);
        CoursewareVo courseWareVo = new CoursewareVo();
        courseWareVo.setBg((Integer) dataMap.get("bgID"));
        courseWareVo.setName((String) dataMap.get("coursewaresName"));
        courseWareVo.setStyle((Integer) dataMap.get("bgStyle"));
        JSONArray resources = (JSONArray) dataMap.get("resources");
        //resources列表
        if (null != resources && resources.size() > 0) {
            int resSize = resources.size();
            List<ResourcesVo> resList = new ArrayList<>(resSize);
            for (int resIndex = 0; resIndex < resSize; resIndex++) {
                ResourcesVo res = resources.getObject(resIndex, ResourcesVo.class);
                resList.add(res);
            }
            courseWareVo.setResources(resList);
        }
        //pages列表
        JSONArray pages = (JSONArray) dataMap.get("pages");
        if (null != pages && pages.size() > 0) {
            int pagesLen = pages.size();
            List<PagesVo> pageList = new ArrayList<>(pagesLen);
            for (int index = 0; index < pagesLen; index++) {
                JSONArray resArr = (JSONArray) pages.get(index);
                if (null == resArr) {//空page处理
//                    pageList.add(pagesVo);
                    continue;
                }
                //page的资源处理
                PagesVo pagesVo = new PagesVo();
                int pageResSize = resArr.size();
                if (pageResSize > 0) {
                    List<PageVo> pageResList = new ArrayList<>(pageResSize);
                    for (int secIndex = 0; secIndex < pageResSize; secIndex++) {
                        PageVo pageRes = resArr.getObject(secIndex, PageVo.class);
                        pageResList.add(pageRes);
                    }
                    pagesVo.setPage(pageResList);
                }
                pageList.add(pagesVo);
            }
            courseWareVo.setPages(pageList);
        }
        JaxbUtil jaxbUtil = new JaxbUtil(CoursewareVo.class);
        StringWriter stringWriter = (StringWriter) jaxbUtil.toXml(courseWareVo, "utf-8");
        System.out.println(stringWriter.toString());
        if (null != stringWriter) stringWriter.close();


    }


}