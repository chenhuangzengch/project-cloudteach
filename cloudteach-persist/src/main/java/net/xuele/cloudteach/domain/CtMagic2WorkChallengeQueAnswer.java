package net.xuele.cloudteach.domain;

public class CtMagic2WorkChallengeQueAnswer {
    private String cqaId;

    private String cqId;

    private String challengeId;

    private String userId;

    private String queId;

    private String aId;

    private Integer sort;

    private Integer iscorrect;

    private Integer isstuans;

    private String schoolId;

    private String content;

    private String scontent;

    public String getCqaId() {
        return cqaId;
    }

    public void setCqaId(String cqaId) {
        this.cqaId = cqaId == null ? null : cqaId.trim();
    }

    public String getCqId() {
        return cqId;
    }

    public void setCqId(String cqId) {
        this.cqId = cqId == null ? null : cqId.trim();
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId == null ? null : challengeId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId == null ? null : queId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(Integer iscorrect) {
        this.iscorrect = iscorrect;
    }

    public Integer getIsstuans() {
        return isstuans;
    }

    public void setIsstuans(Integer isstuans) {
        this.isstuans = isstuans;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getScontent() {
        return scontent;
    }

    public void setScontent(String scontent) {
        this.scontent = scontent;
    }
}