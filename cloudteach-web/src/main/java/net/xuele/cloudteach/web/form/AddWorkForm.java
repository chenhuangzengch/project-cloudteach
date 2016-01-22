package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.web.wrapper.WorkTapeFiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hujx
 * @date 2015/7/7 0001
 */
public class AddWorkForm {


    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 最晚提交时间
     */
    private int lastCommitRange;

    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 班级列表
     */
    private List<String> classList;

    /**
     * 题目列表
     */
    private List<String> itemList;

    /**
     * 教师录音附件
     */
    private WorkTapeFiles workTapeFiles;

    /**
     * json格式班级信息
     */
    private String classJson;


    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public int getLastCommitRange() {
        return lastCommitRange;
    }

    public void setLastCommitRange(int lastCommitRange) {
        this.lastCommitRange = lastCommitRange;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public WorkTapeFiles getWorkTapeFiles() {
        return workTapeFiles;
    }

    public void setWorkTapeFiles(WorkTapeFiles workTapeFiles) {
        this.workTapeFiles = workTapeFiles;
    }

    public String getClassJson() {
        return classJson;
    }

    public void setClassJson(String classJson) {
        this.classJson = classJson;
    }

    @Override
    public String toString() {
        return "AddWorkForm{" +
                "unitId='" + unitId + '\'' +
                ", lastCommitRange=" + lastCommitRange +
                ", publishTime='" + publishTime + '\'' +
                ", classList=" + classList +
                ", itemList=" + itemList +
                ", workTapeFiles=" + workTapeFiles +
                ", classJson='" + classJson + '\'' +
                '}';
    }
    /**
     * 教师的用户ID
     */
    //private String userId;

    /**
     * 教师学校ID
     */
    //private String schoolId;

    /**
     * 作业描述
     */
    //private String context;

    /**
     * 作业发布类型
     * 0，定时发布，1，即时发布
     */
    //private int workType;

    /*public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }*/

}
