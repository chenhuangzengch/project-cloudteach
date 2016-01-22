package net.xuele.cloudteach.web.form;

import java.util.Date;
import java.util.List;

/**
 * Created by cm.wang on 2015/7/29 0029.
 */
public class CourseReappearForm {

    /**
     * 课件发布ID
     */
    private String reappearId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 描述内容
     */
    private String context;

    /**
     * 班级列表
     */
    private List<String> classList;

    /**
     * 课件列表
     */
    private List<String> coursewaresIdList;

    /**
     * 班级IDNameJson格式
     */
    private  String classJson;

    public String getReappearId() {
        return reappearId;
    }

    public void setReappearId(String reappearId) {
        this.reappearId = reappearId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    public List<String> getCoursewaresIdList() {
        return coursewaresIdList;
    }

    public void setCoursewaresIdList(List<String> coursewaresIdList) {
        this.coursewaresIdList = coursewaresIdList;
    }

    public String getClassJson() {
        return classJson;
    }

    public void setClassJson(String classJson) {
        this.classJson = classJson;
    }
}
