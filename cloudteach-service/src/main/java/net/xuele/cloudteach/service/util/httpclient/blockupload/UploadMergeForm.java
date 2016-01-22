package net.xuele.cloudteach.service.util.httpclient.blockupload;

/**
 * UploadMergeForm
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadMergeForm {

    /**
     * 即初始化上传请求返回结果中的 SaveToken
     */
    private String saveToken;

    /**
     * Unix时间戳，授权有效期，超过这个时间之后授权将会失效
     */
    private Long expiration;

    /**
     * 额外扩展参数(原样返回)
     */
    private String extParam;

    public String getSaveToken() {
        return saveToken;
    }

    public void setSaveToken(String saveToken) {
        this.saveToken = saveToken;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    @Override
    public String toString() {
        return "UploadMergeForm{" +
                "saveToken='" + saveToken + '\'' +
                ", expiration=" + expiration +
                ", extParam='" + extParam + '\'' +
                '}';
    }
}
