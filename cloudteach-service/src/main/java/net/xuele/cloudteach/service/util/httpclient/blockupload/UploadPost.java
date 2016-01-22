package net.xuele.cloudteach.service.util.httpclient.blockupload;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * UploadPost
 *
 * @author sunxh
 * @date 15/10/23
 */
public class UploadPost {

    /**
     * 表单参数json序列化之后Base64 的值
     */
    private String policy;

    /**
     * Policy加上zjPUYq9L36oA9zke的MD5值
     */
    private String signature;

    /**
     * 请求的过期时间(Unix时间戳－秒)
     * [取当前时间戳截取到秒即可]
     */
    private String time;

    public UploadPost(String policy) {

        this.setPolicy(policy);

        this.setSignature(DigestUtils.md5Hex(policy + "zjPUYq9L36oA9zke"));

        this.setTime(String.valueOf(System.currentTimeMillis()));
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取用于get请求的url
     *
     * @param baseUrl 原始url，不带参数
     * @return String 带querystring的url
     */
    public String getUrl(String baseUrl) {

        baseUrl += "?policy=" + policy;

        baseUrl += "&signature=" + signature;

        baseUrl += "&time=" + time;

        return baseUrl;
    }
}
