package net.xuele.cloudteach.web.common;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * CloudTeachHTTPClientUtil
 *
 * @author sunxh
 * @date 15/8/14
 */
public class CloudTeachHTTPClientUtil {


    private static final Logger logger = LoggerFactory.getLogger(CloudTeachHTTPClientUtil.class);

    /**
     * 发送post请求获取结果
     * @param formparams
     * @param url
     * @return
     */
    public String post(List<BasicNameValuePair> formparams,String url) {
        String result ="";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            logger.info("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    logger.info("--------------------------------------");
                    result = EntityUtils.toString(entity, "UTF-8");
                    logger.info("Response content: " + result);
                    logger.info("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("Http连接错误：", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码：", e);
        } catch (IOException e) {
            logger.error("IO异常 ：", e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭连接时出现IO异常：", e);
            }
        }
        return result;
    }
}
