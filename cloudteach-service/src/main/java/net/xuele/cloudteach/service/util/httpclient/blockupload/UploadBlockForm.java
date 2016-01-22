package net.xuele.cloudteach.service.util.httpclient.blockupload;

/**
 * UploadForm
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadBlockForm {

    /**
     * 即初始化上传请求返回结果中的 SaveToken
     */
    private String saveToken;

    /**
     * Unix时间戳，授权有效期，超过这个时间之后授权将会失效
     */
    private Long expiration;

    /**
     * 该分块在完整文件中的分块索引
     */
    private Long blockIndex;

    /**
     * 该分块文件的 md5 值
     */
    private String blockHash;

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

    public Long getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(Long blockIndex) {
        this.blockIndex = blockIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    @Override
    public String toString() {
        return "UploadBlockForm{" +
                "saveToken='" + saveToken + '\'' +
                ", expiration=" + expiration +
                ", blockIndex=" + blockIndex +
                ", blockHash='" + blockHash + '\'' +
                '}';
    }
}
