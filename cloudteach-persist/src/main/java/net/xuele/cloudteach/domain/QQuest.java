package net.xuele.cloudteach.domain;

import java.util.Date;

public class QQuest {
    private String qId;

    private Integer type;

    private String kpTag;

    private String unitId;

    private Integer hasSub;

    private String parentId;

    /**
     * SYNC_CLASS|SYNC_MAGIC_WORK|...(同步课堂|提分宝|...)
     */
    private String syncModel;

    private Integer difficulty;

    private Float score;

    private Integer sort;

    private Date addTime;

    private Date updateTime;

    /**
     * 0：作废| 1：启用
     */
    private Integer status;

    private String locateTag;

    private String content;

    private String solution;

    /**
     * 0：纯文本|1：音频
     */
    private Integer ctentType;

    public Integer getCtentType() {
        return ctentType;
    }

    public void setCtentType(Integer ctentType) {
        this.ctentType = ctentType;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId == null ? null : qId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKpTag() {
        return kpTag;
    }

    public void setKpTag(String kpTag) {
        this.kpTag = kpTag == null ? null : kpTag.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public Integer getHasSub() {
        return hasSub;
    }

    public void setHasSub(Integer hasSub) {
        this.hasSub = hasSub;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * 获取 [Q_QUEST] 的属性 SYNC_CLASS|SYNC_MAGIC_WORK|...(同步课堂|提分宝|...)
     */
    public String getSyncModel() {
        return syncModel;
    }

    /**
     * 设置[Q_QUEST]的属性SYNC_CLASS|SYNC_MAGIC_WORK|...(同步课堂|提分宝|...)
     */
    public void setSyncModel(String syncModel) {
        this.syncModel = syncModel == null ? null : syncModel.trim();
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 [Q_QUEST] 的属性 0：作废| 1：启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[Q_QUEST]的属性0：作废| 1：启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocateTag() {
        return locateTag;
    }

    public void setLocateTag(String locateTag) {
        this.locateTag = locateTag == null ? null : locateTag.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
    }
}