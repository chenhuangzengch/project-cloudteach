package net.xuele.cloudteach.service.util.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HttpClientUtil
 *
 * @author sunxh
 * @date 15/8/14
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static Integer socketTimeout = 50000;
    private static Integer connectTimeout = 60000;
    private static Integer connectionRequestTimeout = 50000;

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     *
     * @param url 完整的URL地址
     * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseContent getUrlRespContent(String url) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent response = null;
        try {
            response = hw.getResponse(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     *
     * @param url         完整的URL地址
     * @param urlEncoding 编码，可以为null
     * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseContent getUrlRespContent(String url, String urlEncoding) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        ResponseContent response = null;
        try {
            response = hw.getResponse(url, urlEncoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 将参数拼装在url中，进行post请求。
     *
     * @param url
     * @return
     */
    public static ResponseContent postUrl(String url) {
        HttpClientWrapper hw = new HttpClientWrapper();
        ResponseContent ret = null;
        try {
            setParams(url, hw);
            ret = hw.postNV(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static void setParams(String url, HttpClientWrapper hw) {
        String[] paramStr = url.split("[?]", 2);
        if (paramStr == null || paramStr.length != 2) {
            return;
        }
        String[] paramArray = paramStr[1].split("[&]");
        if (paramArray == null) {
            return;
        }
        for (String param : paramArray) {
            if (param == null || "".equals(param.trim())) {
                continue;
            }
            String[] keyValue = param.split("[=]", 2);
            if (keyValue == null || keyValue.length != 2) {
                continue;
            }
            hw.addNV(keyValue[0], keyValue[1]);
        }
    }

    /**
     * 上传文件（包括图片）
     *
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return
     */
    public static ResponseContent postEntity(String url, Map<String, Object> paramsMap) {
        HttpClientWrapper hw = new HttpClientWrapper();
        ResponseContent ret = null;
        try {
            setParams(url, hw);
            Iterator<String> iterator = paramsMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = paramsMap.get(key);
                if (value instanceof File) {
                    FileBody fileBody = new FileBody((File) value);
                    hw.getContentBodies().add(fileBody);
                } else if (value instanceof byte[]) {
                    byte[] byteVlue = (byte[]) value;
                    ByteArrayBody byteArrayBody = new ByteArrayBody(byteVlue, key);
                    hw.getContentBodies().add(byteArrayBody);
                } else {
                    if (value != null && !"".equals(value)) {
                        hw.addNV(key, String.valueOf(value));
                    } else {
                        hw.addNV(key, "");
                    }
                }
            }
            ret = hw.postEntity(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 使用post方式，发布对象转成的json给Rest服务。
     *
     * @param url
     * @param jsonBody
     * @return
     */
    public static ResponseContent postJsonEntity(String url, String jsonBody) {
        return postEntity(url, jsonBody, "application/json");
    }

    /**
     * 使用post方式，发布对象转成的xml给Rest服务
     *
     * @param url     URL地址
     * @param xmlBody xml文本字符串
     * @return ResponseContent 如果发生异常则返回空，否则返回ResponseContent对象
     */
    public static ResponseContent postXmlEntity(String url, String xmlBody) {
        return postEntity(url, xmlBody, "application/xml");
    }

    private static ResponseContent postEntity(String url, String body, String contentType) {
        HttpClientWrapper hw = new HttpClientWrapper();
        ResponseContent ret = null;
        try {
            hw.addNV("body", body);
            ret = hw.postNV(url, contentType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 下载文件
     *
     * @param url      远程文件URL
     * @param filePath 本地保存路径
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static void getFile(String url, String filePath) throws ClientProtocolException, IOException {

        // 创建httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        HttpResponse response = httpclient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        InputStream in = entity.getContent();

        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
            }
        }


        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {

            FileOutputStream fout = new FileOutputStream(file);

            int l = -1;

            byte[] tmp = new byte[1024];

            while ((l = in.read(tmp)) != -1) {

                // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
                fout.write(tmp, 0, l);
            }

            fout.flush();

            fout.close();

        } finally {

            // 关闭流。
            in.close();
        }

        httpclient.close();
    }

    public static void main(String[] args) {
//        testGet();
        testUploadFile();
    }

    //test
    public static void testGet() {
        String url = "http://www.baidu.com/";
        ResponseContent responseContent = getUrlRespContent(url);
        try {
            System.out.println(responseContent.getContent());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //test
    public static void testUploadFile() {
        String url = "http://dl.xueleyun.com/files/dl/67677f0e18a105a0a7d249d992534910.jpg";

        try {
            getFile(url,"/app/tmp/67677f0e18a105a0a7d249d992534910.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
