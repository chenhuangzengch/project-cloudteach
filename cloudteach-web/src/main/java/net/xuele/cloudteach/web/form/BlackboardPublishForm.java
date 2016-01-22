package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.dto.BlackboardPublishDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by cm.wang on 2015/7/24 0024.
 */
public class BlackboardPublishForm {

    /**
     * 板书ID
     */
    private String blackboardId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 描述内容
     */
    private String context;

    private  String classJson;

    private List<String> classList;

    private String fileJson;

    private Integer addLessonPlan;


    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    public String getBlackboardId() {
        return blackboardId;
    }

    public void setBlackboardId(String blackboardId) {
        this.blackboardId = blackboardId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getClassJson() {
        return classJson;
    }

    public void setClassJson(String classJson) {
        this.classJson = classJson;
    }

    public String getFileJson() {
        return fileJson;
    }

    public void setFileJson(String fileJson) {
        this.fileJson = fileJson;
    }

    public Integer getAddLessonPlan() {
        return addLessonPlan;
    }

    public void setAddLessonPlan(Integer addLessonPlan) {
        this.addLessonPlan = addLessonPlan;
    }
}
