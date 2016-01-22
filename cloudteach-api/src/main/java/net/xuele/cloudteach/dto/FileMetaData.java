package net.xuele.cloudteach.dto;

import java.util.Arrays;

/**
 * Created by Administrator on 2015/6/12 0012.
 * 文件源信息，描述文件的属性
 *
 */
public class FileMetaData {

    //文件名称
    private String fileName;

    //文件内容
    private byte[] fileContent;

    //文件大小
    private long fileSize;

    //文件路径
    private String filePath;

    public FileMetaData(String filePath){
        super();
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "FileMetaData{" +
                "fileName='" + fileName + '\'' +
                ", fileContent=" + Arrays.toString(fileContent) +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
