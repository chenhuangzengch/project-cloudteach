package net.xuele.cloudteach.service.util.httpclient.directupload;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * DirectUploadUtil
 * 直传工具类
 *
 * @author sunxh
 * @date 15/10/25
 */
public class DirectUploadUtil {


    /**
     * @param url
     * @param filePath
     * @param extension
     * @return
     * @throws IOException
     */
    public UploadWrapper upload(String url, String filePath, String extension) throws IOException {

        UploadWrapper uploadWrapper = null;

        MultipartEntity entity = new MultipartEntity();

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);


        //初始化表单
        UploadPost uploadPost;

        UploadForm uploadForm = new UploadForm();

        //时间戳，精确到秒
        uploadForm.setExpiration(System.currentTimeMillis());

        //文件类型
        uploadForm.setFileType(extension);

        //文件长度
        Long fileSize = 0L;

        File f = new File(filePath);
        if (f.exists() && f.isFile()) {
            fileSize = f.length();
        }

        uploadForm.setFileSize(fileSize);


        //计算MD5
        FileInputStream fis = new FileInputStream(filePath);
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        IOUtils.closeQuietly(fis);

        uploadForm.setFileHash(md5);

        //封装post数据
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(uploadForm);

        String policy = new String(Base64.encodeBase64(json.getBytes()));

        uploadPost = new UploadPost(policy);

        //通过以下方法可以模拟页面参数提交
        entity.addPart("Filedata", new FileBody(f));

        entity.addPart("Policy", new StringBody(uploadPost.getPolicy()));
        entity.addPart("Signature", new StringBody(uploadPost.getSignature()));
        entity.addPart("Time", new StringBody(uploadPost.getTime()));

        post.setEntity(entity);
        HttpResponse response = httpclient.execute(post);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {

            HttpEntity entitys = response.getEntity();
            if (entity != null) {
                System.out.println(entitys.getContentLength());

                uploadWrapper = objectMapper.readValue(EntityUtils.toString(entitys), UploadWrapper.class);
            }
        }
        httpclient.getConnectionManager().shutdown();


        return uploadWrapper;
    }


    public static void main(String args[]) {

        String url = "http://ul.xueleyun.com:8090/upload";
//        String url = "http://ul.xueleyun.com/upload";
//        String url = "http://192.168.1.175/upload";
//        String url = "http://192.168.1.225:8090/upload";

//        String filePath = "/app/tmp/systemResList.xml.zip";
        String filePath = "/app/tmp/语文人教版四年级上1、观潮201510161325.zip";
//        String filePath = "/app/tmp/00000000000000000000000000000000.jpg.zip";

        String extension = "zip";

        DirectUploadUtil directUploadUtil = new DirectUploadUtil();

        UploadWrapper uploadWrapper = null;

        try {
            uploadWrapper = directUploadUtil.upload(url, filePath, extension);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(uploadWrapper);
    }

}
