package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.dto.CloudDiskDTO;

import java.io.UnsupportedEncodingException;

/**
 * Created by sunxh on 2015/7/12 0012.
 */
public class CloudDiskUploadAdapterForm {

    /**
     * 文件所属用户id，如果为创建者，所属用户id=创建者id
     */
    private String encodeVar;

    /**
     * 课程编号
     * 课程ID,文件类型,文件夹ID（例子  010001001001001001001,1,11 ）
     */
    private String aid;

    /**
     * 文件名
     */
    private String name;

    /**
     * HDFS文件uri
     */
    private String fileKey;

    /**
     * 大小
     */
    private Integer size;

    /**
     * extension
     * 扩展名
     */
    private String type;

    /**
     * 从flash返回结果包装云盘DTO
     *
     * @return
     */
    public CloudDiskDTO parseCloudDiskDTO(){
        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
        cloudDiskDTO.setExtension(this.getType().substring(1,this.getType().length()));//扩展名
        cloudDiskDTO.setFileUri(this.getFileKey());//文件uri
        cloudDiskDTO.setSize(this.getSize());
        //这里如果使用tomcat，可能会出现中文乱码
        cloudDiskDTO.setName(this.getName().substring(0,
                this.getName().lastIndexOf('.')>=0?this.getName().lastIndexOf('.'):this.getName().length()));

        //aid拆分
        String[] arr = this.getAid().split(",");
        cloudDiskDTO.setUnitId(arr[0]);
        cloudDiskDTO.setFileType(Integer.valueOf(arr[1]));
        cloudDiskDTO.setUserId(arr[2]);
        cloudDiskDTO.setSchoolId(arr[3]);

        return cloudDiskDTO;
    }

    public String getEncodeVar() {
        return encodeVar;
    }

    public void setEncodeVar(String encodeVar) {
        this.encodeVar = encodeVar;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CloudDiskUploadAdapterForm{" +
                "encodeVar='" + encodeVar + '\'' +
                ", aid='" + aid + '\'' +
                ", name='" + name + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                '}';
    }
}
