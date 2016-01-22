package net.xuele.cloudteach.domain;

public class CtMagicBankFinish {
    /**
     * 完成状态ID（
     */
    private String finishId;


    /**
     * 学生ID
     */
    private String userId;

    /**
     * 题库ID
     */
    private String bankId;

    /**
     * 课程ID
     */
    private String unitId;

    /**
     * 课本ID
     */
    private String bookId;

    /**
     * 最高分
     */
    private Integer maxScore;

    /**
     * 最高分描述
     */
    private String maxScorecontext;

    /**
     * 学校ID:用于数据库分片存储
     */
    private String schoolId;

    /**
     * 完成状态：0为完成 1已完成
     */
    private Integer finishStatus;

    public String getFinishId() {
        return finishId;
    }

    public void setFinishId(String finishId) {
        this.finishId = finishId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public String getMaxScorecontext() {
        return maxScorecontext;
    }

    public void setMaxScorecontext(String maxScorecontext) {
        this.maxScorecontext = maxScorecontext;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }
}