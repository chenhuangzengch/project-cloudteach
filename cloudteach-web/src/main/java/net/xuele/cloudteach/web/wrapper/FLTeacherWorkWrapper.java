package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by sunxinhe on 15/8/22.
 */
public class FLTeacherWorkWrapper {

    private String workId;

    private Integer workType;

    /**
     * 班级列表
     */
    private List<FLClassWrapper> classList;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 题目列表
     */
    private List<FLBankItemWrapper> itemList;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public List<FLClassWrapper> getClassList() {
        return classList;
    }

    public void setClassList(List<FLClassWrapper> classList) {
        this.classList = classList;
    }

    public List<FLBankItemWrapper> getItemList() {
        return itemList;
    }

    public void setItemList(List<FLBankItemWrapper> itemList) {
        this.itemList = itemList;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
