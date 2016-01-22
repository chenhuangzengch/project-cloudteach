package net.xuele.cloudteach.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.xuele.cloudteach.view.AttachmentInfoView;
import net.xuele.cloudteach.view.ClassIdNameView;
import net.xuele.cloudteach.view.FileInfoView;
import net.xuele.cloudteach.view.UnitBookNameView;

import java.util.List;

/**
 * Created by cm.wang on 2015/7/17 0017.
 */
public class TranslateJson {

    // 将班级信息封装成json格式
    public static String getClassJson(List<ClassIdNameView> classIdNameViewList){

        ObjectMapper objectMapper = new ObjectMapper();
        String workClassJson = null;
        try {
            workClassJson = objectMapper.writeValueAsString(classIdNameViewList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return workClassJson;
    }

    public static String getFileJson(List<FileInfoView> fileInfoViewList){

        ObjectMapper objectMapper = new ObjectMapper();
        String fileJson = null;
        try {
            fileJson = objectMapper.writeValueAsString(fileInfoViewList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return fileJson;
    }

    // 将附件信息封账成json格式：{id:'id',uri:'uri',ext:'ext'}
    public static String getAttachmentInfoJson(List<AttachmentInfoView> attachmentInfoViewList) {

        String attachmentJson = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            attachmentJson = objectMapper.writeValueAsString(attachmentInfoViewList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return attachmentJson;
    }
}
