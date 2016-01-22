package net.xuele.cloudteach.web.wrapper;

import java.util.Date;

public class MagicQuestionImageWrapper {
    /**
     * 图片id
     */
    private String imageId;

    /**
     * 图片地址
     */
    private String ftpurl;

    /**
     * 关联题目id
     */
    private String queId;

    /**
     * 记录创建者
     */
    private String createUser;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 状态（1，有效，0，无效）
     */
    private Integer status;

    /**
     * 获取 [CT_MAGIC_QUESTION_IMAGE] 的属性 图片id
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_IMAGE]的属性图片id
     */
    public void setImageId(String imageId) {
        this.imageId = imageId == null ? null : imageId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_IMAGE] 的属性 图片地址
     */
    public String getFtpurl() {
        return ftpurl;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_IMAGE]的属性图片地址
     */
    public void setFtpurl(String ftpurl) {
        this.ftpurl = ftpurl == null ? null : ftpurl.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_IMAGE] 的属性 关联题目id
     */
    public String getQueId() {
        return queId;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_IMAGE]的属性关联题目id
     */
    public void setQueId(String queId) {
        this.queId = queId == null ? null : queId.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_IMAGE] 的属性 记录创建者
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_IMAGE]的属性记录创建者
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_IMAGE] 的属性 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_IMAGE]的属性记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 [CT_MAGIC_QUESTION_IMAGE] 的属性 状态（1，有效，0，无效）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置[CT_MAGIC_QUESTION_IMAGE]的属性状态（1，有效，0，无效）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}