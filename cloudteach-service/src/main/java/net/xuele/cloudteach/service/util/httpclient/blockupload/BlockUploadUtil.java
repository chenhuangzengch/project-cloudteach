package net.xuele.cloudteach.service.util.httpclient.blockupload;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.xuele.cloudteach.service.util.httpclient.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * BlockUploadUtil
 * 分块上传文件工具
 *
 * @author sunxh
 * @date 15/10/23
 */
public class BlockUploadUtil {

    private static Logger logger = LoggerFactory.getLogger(BlockUploadUtil.class);

    /**
     * @param url
     * @param filePath
     * @param extension
     * @param blockSize
     * @throws FileNotFoundException
     * @throws IOException
     */
    public UploadMergeWrapper upload(String url, String filePath, String extension, Long blockSize) throws IOException {
        UploadMergeWrapper uploadMergeWrapper = null;

        /** 分块上传初始化*/

        //默认块大小1M
        if (blockSize == null || blockSize == 0) {

            blockSize = Long.valueOf(1024 * 1024);

        }

        //初始化表单
        UploadPost uploadPost;

        UploadInitForm initForm = new UploadInitForm();

        //时间戳，精确到秒
        initForm.setExpiration(System.currentTimeMillis());

        //文件类型
        initForm.setFileType(extension);

        //文件长度
        Long fileSize = 0L;

        File f = new File(filePath);
        if (f.exists() && f.isFile()) {
            fileSize = f.length();
        }

        initForm.setFileSize(fileSize);

        //文件块数
        Double fileBlocksDouble = Math.ceil(fileSize.doubleValue() / blockSize.doubleValue());
        Integer fileBlocks = fileBlocksDouble.intValue();

        initForm.setFileBlocks(fileBlocks);

        //计算MD5
        FileInputStream fis = new FileInputStream(filePath);
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        IOUtils.closeQuietly(fis);
        if(fis!=null)fis.close();

        initForm.setFileHash(md5);

        //封装post数据
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(initForm);

        String policy = new String(Base64.encodeBase64(json.getBytes()));

        uploadPost = new UploadPost(policy);

        /** 发送上传初始化请求*/

        url = uploadPost.getUrl(url);

        String content = HttpClientUtil.getUrlRespContent(url).getContent();

        UploadInitWrapper initWrapper = objectMapper.readValue(content, UploadInitWrapper.class);

        String saveToken = initWrapper.getSaveToken();

        String host = initWrapper.getHost();


        /** 分块上传*/

        FileCuttingUtil fileCuttingUtil = new FileCuttingUtil();

        List<String> fileNameList = fileCuttingUtil.splitBySizeSync(filePath, blockSize.intValue());

        if (CollectionUtils.isNotEmpty(fileNameList)) {

            for (int i = 0; i < fileNameList.size(); i++) {

                UploadBlockWrapper blockWrapper = null;

                UploadBlockForm blockForm = new UploadBlockForm();

                blockForm.setExpiration(System.currentTimeMillis());

                blockForm.setBlockIndex(Long.valueOf(i));

                blockForm.setSaveToken(saveToken);

                String blockName = fileNameList.get(i);

                FileInputStream fisBlock = new FileInputStream(blockName);
                String md5Block = DigestUtils.md5Hex(IOUtils.toByteArray(fisBlock));
                IOUtils.closeQuietly(fisBlock);

                blockForm.setBlockHash(md5Block);

                String jsonBlock = objectMapper.writeValueAsString(blockForm);

                String policyBlock = new String(Base64.encodeBase64(jsonBlock.getBytes()));

                UploadPost uploadPostBlock = new UploadPost(policyBlock);

                //TODO post 上传文件
                String blockUrl = host + "/block/bput";

                MultipartEntity entity = new MultipartEntity();

                HttpClient httpclient = new DefaultHttpClient();

                HttpPost post = new HttpPost(blockUrl);

                entity.addPart("Filedata", new FileBody(new File(blockName)));

                entity.addPart("Policy", new StringBody(uploadPostBlock.getPolicy()));
                entity.addPart("Signature", new StringBody(uploadPostBlock.getSignature()));
                entity.addPart("Time", new StringBody(uploadPostBlock.getTime()));

                post.setEntity(entity);
                HttpResponse response = httpclient.execute(post);
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {

                    HttpEntity entitys = response.getEntity();
                    if (entity != null) {
//                        System.out.println(entitys.getContentLength());

                        blockWrapper = objectMapper.readValue(EntityUtils.toString(entitys), UploadBlockWrapper.class);
                    }
                }
                httpclient.getConnectionManager().shutdown();


                if (!blockWrapper.getStatus()) {

                    logger.error("第 {} 块文件上传失败，blockUrl:{}，filename：{},blockWrapper:{},blockForm:{}", i, blockUrl, blockName, blockWrapper, blockForm);

                } else {
                    logger.info("第 {} 块文件上传完成，blockUrl:{}，filename：{},blockWrapper:{},blockForm:{}", i, blockUrl, blockName, blockWrapper, blockForm);

                }


            }

        }


        /** 文件合并*/
        UploadMergeForm mergeForm = null;

        UploadPost uploadPostMerge = null;

        String mergeUrl = host + "/block/mkfile";

        try {
            mergeForm = new UploadMergeForm();

            mergeForm.setSaveToken(saveToken);

            mergeForm.setExpiration(System.currentTimeMillis());

            String jsonMerge = objectMapper.writeValueAsString(mergeForm);

            String policyMerge = new String(Base64.encodeBase64(jsonMerge.getBytes()));

            uploadPostMerge = new UploadPost(policyMerge);

            mergeUrl = uploadPostMerge.getUrl(mergeUrl);

            String contentMerge = HttpClientUtil.getUrlRespContent(mergeUrl).getContent();

            uploadMergeWrapper = objectMapper.readValue(contentMerge, UploadMergeWrapper.class);

            logger.warn("文件合并完成，mergeForm：{},uploadMergeWrapper:{}", mergeForm, uploadMergeWrapper);

        } catch (Exception e) {

            logger.error("分块上传合并失败,mergeForm：{},mergeUrl:{},uploadMergeWrapper:{}", mergeForm, mergeUrl, uploadMergeWrapper);

            logger.error("分块上传合并失败", e);
        }


        return uploadMergeWrapper;
    }

    public static void main(String[] args) {

        BlockUploadUtil uploadByBlock = new BlockUploadUtil();

        try {
//            uploadByBlock.upload("http://192.168.1.225:8090/block/init", "/app/tmp/apache-maven-3.0.4.zip", "zip", 1024 * 1024L);
            uploadByBlock.upload("http://ul.xueleyun.com/block/init", "/app/tmp/apache-maven-3.0.4.zip", "zip", 1024 * 1024L);
//            uploadByBlock.upload("http://192.168.1.225:8090/block/init", "/app/tmp/语文人教版四年级上1、观潮201510161325.zip", "zip", 1024 * 1024L);
//            String content = HttpClientUtil.getUrlRespContent("http://ebookfile.xueleyun.com/fzkbList.ashx?kcid=010004002001001001002").getContent();

//            logger.error("content:{}", content);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
