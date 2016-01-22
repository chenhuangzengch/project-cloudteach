package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.DiskFileOperationEnum;
import net.xuele.cloudteach.constant.ExtensionTypeUtil;
import net.xuele.cloudteach.web.wrapper.FileShowWrapper;
import net.xuele.common.ajax.AjaxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FileOperationController
 * 云教学文件处理控制器
 *      下载
 *      预览
 *      关联预览
 *      缩略图
 * @author sunxh
 * @date 2015/7/20 0020
 */
@Controller
@RequestMapping(value = "file")
public class FileShowController {

    @Value("${file.download.url}")
    private String dlDomainName;

    /**
     * 下载
     * @param fileUri 文件URI
     * @param name  文件名
     * @param extension 扩展名
     * @return
     */
    @RequestMapping("download")
    @ResponseBody
    public AjaxResponse<FileShowWrapper> download(@RequestParam("fileUri")String fileUri,@RequestParam("name")String name,@RequestParam("extension")String extension){
        AjaxResponse<FileShowWrapper> fileShowWrapperAjaxResponse = new AjaxResponse<>();
        FileShowWrapper fileShowWrapper = new FileShowWrapper();

        //文件名称
        fileShowWrapper.setName(name);
        //扩展名
        fileShowWrapper.setExtension(extension);
        //URL
        fileShowWrapper.setUrl(dlDomainName+ ExtensionTypeUtil.getInstance().getUrl(extension,fileUri, DiskFileOperationEnum.DOWNLOAD));
        //扩展名类型
        fileShowWrapper.setExtType(ExtensionTypeUtil.getInstance().getExtType(extension));
        //图标类型
        fileShowWrapper.setExtIconType(ExtensionTypeUtil.getInstance().getIcon(extension));

        fileShowWrapperAjaxResponse.setWrapper(fileShowWrapper);
        fileShowWrapperAjaxResponse.setStatus("1");
        return fileShowWrapperAjaxResponse;
    }

    /**
     * 预览
     * @param fileUri 文件URI
     * @param name  文件名
     * @param extension 扩展名
     * @return
     */
    @RequestMapping("view")
    @ResponseBody
    public AjaxResponse view(@RequestParam("fileUri")String fileUri,@RequestParam("name")String name,@RequestParam("extension")String extension){
        AjaxResponse<FileShowWrapper> fileShowWrapperAjaxResponse = new AjaxResponse<>();
        FileShowWrapper fileShowWrapper = new FileShowWrapper();

        //文件名称
        fileShowWrapper.setName(name);
        //扩展名
        fileShowWrapper.setExtension(extension);
        //URL
        fileShowWrapper.setUrl(dlDomainName+ ExtensionTypeUtil.getInstance().getUrl(extension,fileUri, DiskFileOperationEnum.VIEW));
        //扩展名类型
        fileShowWrapper.setExtType(ExtensionTypeUtil.getInstance().getExtType(extension));
        //图标类型
        fileShowWrapper.setExtIconType(ExtensionTypeUtil.getInstance().getIcon(extension));

        fileShowWrapperAjaxResponse.setWrapper(fileShowWrapper);
        fileShowWrapperAjaxResponse.setStatus("1");
        return fileShowWrapperAjaxResponse;
    }

    /**
     * 关联预览
     * @param fileUri 文件URI
     * @param name  文件名
     * @param extension 扩展名
     * @return
     */
    @RequestMapping("relatedView")
    @ResponseBody
    public AjaxResponse relatedView(@RequestParam("fileUri")String fileUri,@RequestParam("name")String name,@RequestParam("extension")String extension){
        AjaxResponse<FileShowWrapper> fileShowWrapperAjaxResponse = new AjaxResponse<>();
        FileShowWrapper fileShowWrapper = new FileShowWrapper();

        //文件名称
        fileShowWrapper.setName(name);
        //扩展名
        fileShowWrapper.setExtension(extension);
        //URL
        fileShowWrapper.setUrl(dlDomainName+ ExtensionTypeUtil.getInstance().getUrl(extension,fileUri, DiskFileOperationEnum.RELATEDVIEW));
        //扩展名类型
        fileShowWrapper.setExtType(ExtensionTypeUtil.getInstance().getExtType(extension));
        //图标类型
        fileShowWrapper.setExtIconType(ExtensionTypeUtil.getInstance().getIcon(extension));

        fileShowWrapperAjaxResponse.setWrapper(fileShowWrapper);
        fileShowWrapperAjaxResponse.setStatus("1");
        return fileShowWrapperAjaxResponse;
    }

    /**
     * 缩略图获取
     * @param fileUri 文件URI
     * @param name  文件名
     * @param extension 扩展名
     * @param size 1000x1000
     * @return
     */
    @RequestMapping("thumb")
    @ResponseBody
    public AjaxResponse thumb(@RequestParam("fileUri")String fileUri,@RequestParam("name")String name,@RequestParam("extension")String extension,@RequestParam("size")String size){
        AjaxResponse<FileShowWrapper> fileShowWrapperAjaxResponse = new AjaxResponse<>();
        FileShowWrapper fileShowWrapper = new FileShowWrapper();

        //文件名称
        fileShowWrapper.setName(name);
        //扩展名
        fileShowWrapper.setExtension(extension);
        //URL
        fileShowWrapper.setUrl(dlDomainName+ ExtensionTypeUtil.getInstance().getUrl(extension,fileUri, DiskFileOperationEnum.VIEW,size));
        //扩展名类型
        fileShowWrapper.setExtType(ExtensionTypeUtil.getInstance().getExtType(extension));
        //图标类型
        fileShowWrapper.setExtIconType(ExtensionTypeUtil.getInstance().getIcon(extension));

        fileShowWrapperAjaxResponse.setWrapper(fileShowWrapper);
        fileShowWrapperAjaxResponse.setStatus("1");
        return fileShowWrapperAjaxResponse;
    }


}
