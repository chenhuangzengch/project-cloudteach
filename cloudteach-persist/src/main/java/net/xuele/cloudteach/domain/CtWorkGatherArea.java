package net.xuele.cloudteach.domain;

import java.util.Date;

public class CtWorkGatherArea {
    /**
     * 作业ID
     */
    private String workId;

    /**
     * 教师用户名称
     */
    private String userName;

    /**
     * 教师用户ID
     */
    private String userId;

    private String userIcon;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 科目ID
     */
    private String subjectId;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 课程名称
     */
    private String unitName;

    /**
     * 作业描述
     */
    private String context;

    /**
     * 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    private Integer workType;

    /**
     * 预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    private Integer workItemNum;

    /**
     * 班级数
     */
    private Integer workClassNum;

    /**
     * [{"classId":"123","className":"一（二）班"}]
     */
    private String workClassJson;

    /**
     * 作业对应学生数
     */
    private Integer workStudentNum;

    private Integer workSubStudentNum;

    private Integer workCorrectStudentNum;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    private String schoolName;

    /**
     * 状态:0删除 1有效
     */
    private Integer status;

    /**
     * 地市区域ID（用于分片）
     */
    private String areaCode;

    /**
     * 区县区域ID
     */
    private String areaCode2;

    // 年级
    private int grade;

    // 职务名称
    private String positionName;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 作业ID
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性作业ID
     */
    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 教师用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性教师用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 教师用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性教师用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 科目ID
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性科目ID
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId == null ? null : subjectId.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 科目名称
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性科目名称
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 课程ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性课程ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 课程名称
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性课程名称
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 作业描述
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性作业描述
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    public Integer getWorkType() {
        return workType;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
     */
    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    public Integer getWorkItemNum() {
        return workItemNum;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
     */
    public void setWorkItemNum(Integer workItemNum) {
        this.workItemNum = workItemNum;
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 班级数
     */
    public Integer getWorkClassNum() {
        return workClassNum;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性班级数
     */
    public void setWorkClassNum(Integer workClassNum) {
        this.workClassNum = workClassNum;
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 [{"classId":"123","className":"一（二）班"}]
     */
    public String getWorkClassJson() {
        return workClassJson;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性[{"classId":"123","className":"一（二）班"}]
     */
    public void setWorkClassJson(String workClassJson) {
        this.workClassJson = workClassJson == null ? null : workClassJson.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 作业对应学生数
     */
    public Integer getWorkStudentNum() {
        return workStudentNum;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性作业对应学生数
     */
    public void setWorkStudentNum(Integer workStudentNum) {
        this.workStudentNum = workStudentNum;
    }

    public Integer getWorkSubStudentNum() {
        return workSubStudentNum;
    }

    public void setWorkSubStudentNum(Integer workSubStudentNum) {
        this.workSubStudentNum = workSubStudentNum;
    }

    public Integer getWorkCorrectStudentNum() {
        return workCorrectStudentNum;
    }

    public void setWorkCorrectStudentNum(Integer workCorrectStudentNum) {
        this.workCorrectStudentNum = workCorrectStudentNum;
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 学校ID:用于数据库分片存储
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性学校ID:用于数据库分片存储
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * 获取 [CT_WORK_GATHER_AREA] 的属性 状态:0删除 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_WORK_GATHER_AREA]的属性状态:0删除 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAreaCode2() {
        return areaCode2;
    }

    public void setAreaCode2(String areaCode2) {
        this.areaCode2 = areaCode2;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}