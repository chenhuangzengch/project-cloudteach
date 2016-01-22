package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.FileMetaData;

/**
 * Created by Administrator on 2015/6/12 0012.
 * 学乐文件服务统一抽象
 */
public interface XueleFileService {

    /**
     * 保存文件
     *
     * @param buf
     * @return
     */
    FileMetaData saveFile(byte[] buf);


    /**
     * 读取文件
     *
     * @param fileMetaData
     * @return
     */
    byte[] readFile(FileMetaData fileMetaData);

}
