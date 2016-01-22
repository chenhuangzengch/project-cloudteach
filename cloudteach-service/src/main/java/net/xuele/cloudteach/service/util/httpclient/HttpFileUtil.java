package net.xuele.cloudteach.service.util.httpclient;

import net.xuele.cloudteach.service.util.httpclient.blockupload.BlockUploadUtil;
import net.xuele.cloudteach.service.util.httpclient.blockupload.UploadMergeWrapper;
import net.xuele.cloudteach.service.util.httpclient.directupload.DirectUploadUtil;
import net.xuele.cloudteach.service.util.httpclient.directupload.UploadWrapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * HttpFileUtil
 *
 * @author sunxh
 * @date 15/10/25
 */
public class HttpFileUtil {

    private final static int BUFFER = 1024;

    /**
     * 上传文件到文件服务器
     *
     * @param url       上传路径
     * @param filePath  本地文件路径
     * @param extension 文件扩展名
     * @param blockSize 块大小【单位：byte】为空表示使用直传模式
     * @return UploadResult
     * @throws IOException
     */
    public static UploadResult upload(String url, String filePath, String extension, Long blockSize) throws IOException {

        UploadResult uploadResult = new UploadResult();

        if (blockSize == null) {

            //直传

            url += "/upload";

            DirectUploadUtil directUploadUtil = new DirectUploadUtil();

            UploadWrapper uploadWrapper = directUploadUtil.upload(url, filePath, extension);

            if (uploadWrapper != null) {

                uploadResult.setStatus(uploadWrapper.getStatus());

                uploadResult.setError(uploadWrapper.getError());

                uploadResult.setFileKey(uploadWrapper.getFileKey());

                uploadResult.setExtParam(uploadWrapper.getExtParam());

            }


        } else {

            //分块

            url += "/block/init";

            BlockUploadUtil blockUploadUtil = new BlockUploadUtil();

            UploadMergeWrapper uploadMergeWrapper = blockUploadUtil.upload(url, filePath, extension, blockSize);

            if (uploadMergeWrapper != null) {

                uploadResult.setStatus(uploadMergeWrapper.getStatus());

                uploadResult.setError(uploadMergeWrapper.getError());

                uploadResult.setFileKey(uploadMergeWrapper.getFileKey());

                uploadResult.setExtParam(uploadMergeWrapper.getExtParam());

            }
        }

        return uploadResult;

    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    public static void download(String url, String filePath) throws IOException {

        HttpClient client = new HttpClient();
        GetMethod httpGet = new GetMethod(url);
        client.executeMethod(httpGet);

        InputStream in = httpGet.getResponseBodyAsStream();

        FileOutputStream out = new FileOutputStream(new File(filePath));

        byte[] b = new byte[BUFFER];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();

        httpGet.releaseConnection();

    }

    public static void main(String[] args) {
        String url = "http://avatar.xueleyun.com/images/314a2b9643695c3943694be2dcf44f8a.jpg";

        try {
            download(url, "/app/tmp/314a2b9643695c3943694be2dcf44f8a.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
