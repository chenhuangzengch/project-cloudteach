package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CloudDiskPageRequest;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.service.CloudDiskShareService;
import net.xuele.cloudteach.web.form.CloudDiskForm;
import net.xuele.cloudteach.web.form.CloudDiskUploadAdapterForm;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import net.xuele.common.security.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.SessionScope;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/6/10 0010.
 * 云盘模块控制器，云盘相关的mvc都在这里统一分发
 */

@Controller
@RequestMapping(value = "cloudDisk/file")
public class CloudDiskController {

    @Autowired
    private CloudDiskService cloudDiskService;

    @Autowired
    private CloudDiskShareService cloudDiskShareService;

    @Value("${file.download.url}")
    private String dlDomainName;

    private static Logger logger = LoggerFactory.getLogger(CloudDiskController.class);

    /**
     * @param map 【请求参数】
     * @return 云教学主页
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/cloudDisk/file/index");

        map.put("schoolName", SessionUtil.getUserSession().getSchoolName());
        map.put("userId", SessionUtil.getUserSession().getUserId());
        map.put("schoolId", SessionUtil.getUserSession().getSchoolId());
        return "/disk/index";
    }


    /**
     * 获取用户的地区ID及名称
     *
     * @return
     */
    @RequestMapping(value = "userArea")
    @ResponseBody
    public AjaxResponse<AreaWrapper> userArea() {
        AjaxResponse<AreaWrapper> areaWrapperAjaxResponse = new AjaxResponse<>();

        areaWrapperAjaxResponse.setWrapper(new AreaWrapper(SessionUtil.getUserSession().getArea(),
                SessionUtil.getUserSession().getAreaName()));
        areaWrapperAjaxResponse.setStatus("1");
        return areaWrapperAjaxResponse;
    }

    /**
     * @param request 【请求参数】
     * @return 分页获得我的云盘资源
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "pageList")
    @ResponseBody
    public AjaxResponse<PageResponse<CloudDiskDTO>> queryMyFilesByPage(CloudDiskPageRequest request) {
        //TODO DTO ==> wrapper

        AjaxResponse<PageResponse<CloudDiskDTO>> userFilePageAjaxResponse = new AjaxResponse<>();

        //取当前UserID
        request.setUserId(SessionUtil.getUserSession().getUserId());
        //传入schoolID用于数据库分片
        request.setSchoolId(SessionUtil.getUserSession().getSchoolId());

        userFilePageAjaxResponse.setWrapper(cloudDiskService.queryMyFilesByPage(request));
        userFilePageAjaxResponse.setStatus("1");//设置状态为成功
        return userFilePageAjaxResponse;
    }

    /**
     * @param cloudDiskForm 【请求参数】
     * @return 不分页获得我的云盘资源
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public AjaxResponse<TotalWrapper<CloudDiskWrapper>> queryMyFiles(CloudDiskForm cloudDiskForm) {
        AjaxResponse<TotalWrapper<CloudDiskWrapper>> cloudDiskWrapperAjaxResponse = new AjaxResponse<>();
        TotalWrapper<CloudDiskWrapper> cloudDiskTotals;

        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
        BeanUtils.copyProperties(cloudDiskForm, cloudDiskDTO);
        cloudDiskDTO.setUserId(SessionUtil.getUserSession().getUserId());            //用户ID
        cloudDiskDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());        //学校ID

        List<CloudDiskDTO> cloudDiskDTOList = cloudDiskService.queryMyFiles(cloudDiskDTO);
        List<CloudDiskWrapper> cloudDiskWrapperList = new ArrayList<>();

        //转换Wrapper
        for (CloudDiskDTO cdDTO : cloudDiskDTOList) {
            CloudDiskWrapper cloudDiskWrapper = new CloudDiskWrapper(cdDTO);
            cloudDiskWrapperList.add(cloudDiskWrapper);
        }

        cloudDiskTotals = new TotalWrapper<>(cloudDiskWrapperList);

        cloudDiskWrapperAjaxResponse.setWrapper(cloudDiskTotals);
        cloudDiskWrapperAjaxResponse.setStatus("1");//设置状态为成功
        return cloudDiskWrapperAjaxResponse;
    }

    /**
     * 按资源类型分类获取我的云盘资源数量
     * @param unitId    课程ID
     * @return AjaxResponse<List<CloudDiskAmountWrapper>>
     */
    @RequestMapping(value = "listDiskAmount")
    @ResponseBody
    public AjaxResponse<List<CloudDiskAmountWrapper>> listDiskAmount(@RequestParam("unitId")String unitId){
        logger.info("");
        AjaxResponse<List<CloudDiskAmountWrapper>> amountWrapperAjaxResponse = new AjaxResponse<>();
        List<CloudDiskAmountWrapper> amountWrapperList = new ArrayList<>();

        List<CloudDiskAmountDTO> amountDTOList = cloudDiskService.listDiskAmout(
                SessionUtil.getUserSession().getSchoolId(), SessionUtil.getUserSession().getUserId(),unitId);

        //DTO 转wrapper
        for (CloudDiskAmountDTO diskAmountDTO : amountDTOList) {
            CloudDiskAmountWrapper amountWrapper = new CloudDiskAmountWrapper();
            amountWrapper.setFileType(diskAmountDTO.getFileType());
            amountWrapper.setNum(diskAmountDTO.getNum());

            amountWrapperList.add(amountWrapper);
        }

        amountWrapperAjaxResponse.setStatus("1");
        amountWrapperAjaxResponse.setWrapper(amountWrapperList);
        return amountWrapperAjaxResponse;
    }


    /**
     * @param diskId 【文件ID】
     * @return 取消收藏文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "cancelCollect")
    @ResponseBody
    public AjaxResponse<CloudDiskCollectWrapper> cancelCollectFile(@RequestParam("diskId") String diskId) {
        AjaxResponse<CloudDiskCollectWrapper> userFileWrapperAjaxResponse = new AjaxResponse<>();

        //取消收藏
        CloudDiskCollectDTO cloudDiskCollectDTO = cloudDiskService.cancelCollectFile(SessionUtil.getUserSession().getSchoolId(),
                diskId, SessionUtil.getUserSession().getUserId());

        //封装ajax返回
        CloudDiskCollectWrapper cloudDiskCollectWrapper = new CloudDiskCollectWrapper(cloudDiskCollectDTO);
        userFileWrapperAjaxResponse.setStatus("1");
        userFileWrapperAjaxResponse.setWrapper(cloudDiskCollectWrapper);
        return userFileWrapperAjaxResponse;
    }

    /**
     * @param diskId 【文件ID】
     * @param name   【文件名】
     * @return 重命名文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "rename")
    @ResponseBody
    public AjaxResponse renameFile(@RequestParam("diskId") String diskId, @RequestParam("name") String name) {
        //文件名如果不合法，抛出异常
        if (name == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ILLEGALNAME);
        }

        AjaxResponse userFileWrapperAjaxResponse = new AjaxResponse();

        //初始化数据
        CloudDiskUpdateDTO cloudDiskUpdateDTO = new CloudDiskUpdateDTO();
        cloudDiskUpdateDTO.setUserId(SessionUtil.getUserSession().getUserId());
        cloudDiskUpdateDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());//学校ID用于分片
        cloudDiskUpdateDTO.setDiskId(diskId);
        cloudDiskUpdateDTO.setName(name);

        //修改资源名
        cloudDiskService.renameFile(cloudDiskUpdateDTO);

        //
        userFileWrapperAjaxResponse.setStatus("1");
        return userFileWrapperAjaxResponse;
    }

    /**
     * @param diskId   【文件ID】
     * @param fileType 【文件类型】
     * @return 移动文件：其实这里的移动是指改变文件类型
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "move")
    @ResponseBody
    public AjaxResponse moveFile(@RequestParam("diskId") String diskId, @RequestParam("fileType") Integer fileType, @RequestParam("unitId") String unitId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        CloudDiskUpdateDTO cloudDiskUpdateDTO = new CloudDiskUpdateDTO();
        cloudDiskUpdateDTO.setUserId(SessionUtil.getUserSession().getUserId());
        cloudDiskUpdateDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());//学校ID，用于分片查找
        cloudDiskUpdateDTO.setDiskId(diskId);
        cloudDiskUpdateDTO.setFileType(fileType);
        cloudDiskUpdateDTO.setUnitId(unitId);
        cloudDiskService.moveFile(cloudDiskUpdateDTO);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param diskId    【文件ID】
     * @param shareType 【文件ID】
     * @return 分享用户文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "share")
    @ResponseBody
    public AjaxResponse<CloudShareCollectWrapper> shareFile(@RequestParam("diskId") String diskId, @RequestParam("shareType") Integer shareType) {
        AjaxResponse<CloudShareCollectWrapper> userFileShareWrapperAjaxResponse = new AjaxResponse<>();

        //包装分享相关的用户信息
        UserShareInfoDTO userShareInfoDTO = new UserShareInfoDTO();

        userShareInfoDTO.setItemId(diskId);                                                        //云盘资源ID
        userShareInfoDTO.setShareType(shareType);                                                  //分享类型

        userShareInfoDTO.setUserId(SessionUtil.getUserSession().getUserId());           //用户ID
        userShareInfoDTO.setUserName(SessionUtil.getUserSession().getRealName());       //用户名

        userShareInfoDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());       //学校ID
        userShareInfoDTO.setSchoolName(SessionUtil.getUserSession().getSchoolName());   //学校名称

        userShareInfoDTO.setAreaId(SessionUtil.getUserSession().getArea());             //地区ID
        userShareInfoDTO.setAreaName(SessionUtil.getUserSession().getAreaName());       //地区名称


        Integer shareStatus = cloudDiskShareService.shareFile(userShareInfoDTO);

        CloudShareCollectWrapper cloudShareCollectWrapper = new CloudShareCollectWrapper();
        cloudShareCollectWrapper.setDiskId(diskId);
        cloudShareCollectWrapper.setShareState(shareStatus);//分享状态
        userFileShareWrapperAjaxResponse.setStatus("1");
        userFileShareWrapperAjaxResponse.setWrapper(cloudShareCollectWrapper);
        return userFileShareWrapperAjaxResponse;
    }

    /**
     * @param diskId 【文件ID】
     * @return 删除文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "remove")
    @ResponseBody
    public AjaxResponse removeFile(@RequestParam("diskId") String diskId) {

        AjaxResponse ajaxResponse = new AjaxResponse();
        CloudDiskUpdateDTO cloudDiskUpdateDTO = new CloudDiskUpdateDTO();
        cloudDiskUpdateDTO.setUserId(SessionUtil.getUserSession().getUserId());
        cloudDiskUpdateDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());      //学校ID
        cloudDiskUpdateDTO.setDiskId(diskId);
        cloudDiskService.removeFile(cloudDiskUpdateDTO);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param diskId 【文件ID】
     * @return 置顶文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "sticky")
    @ResponseBody
    public AjaxResponse stickyFile(@RequestParam("diskId") String diskId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        CloudDiskUpdateDTO cloudDiskUpdateDTO = new CloudDiskUpdateDTO();
        cloudDiskUpdateDTO.setUserId(SessionUtil.getUserSession().getUserId());
        cloudDiskUpdateDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());      //学校ID
        cloudDiskUpdateDTO.setDiskId(diskId);
        cloudDiskService.stickyFile(cloudDiskUpdateDTO);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param diskId 【文件ID】
     * @return 取消置顶文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "cancelSticky")
    @ResponseBody
    public AjaxResponse<UserFileStickyWrapper> cancelStickyFile(@RequestParam("diskId") String diskId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        CloudDiskUpdateDTO cloudDiskUpdateDTO = new CloudDiskUpdateDTO();
        cloudDiskUpdateDTO.setUserId(SessionUtil.getUserSession().getUserId());
        cloudDiskUpdateDTO.setSchoolId(SessionUtil.getUserSession().getSchoolId());      //学校ID
        cloudDiskUpdateDTO.setDiskId(diskId);
        cloudDiskService.cancelStickyFile(cloudDiskUpdateDTO);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 下载文件
     *
     * @param diskId
     * @return
     */
    @RequestMapping(value = "download")
    @ResponseBody
    public AjaxResponse<CloudDiskDownloadWrapper> downloadFile(@RequestParam("diskId") String diskId) {
        AjaxResponse<CloudDiskDownloadWrapper> downloadWrapperAjaxResponse = new AjaxResponse<>();
        CloudDiskDownloadWrapper cloudDiskDownloadWrapper = new CloudDiskDownloadWrapper();
        CloudDiskDownloadDTO cloudDiskDownloadDTO;

        cloudDiskDownloadDTO = cloudDiskService.downloadFile(SessionUtil.getUserSession().getSchoolId(),
                diskId, SessionUtil.getUserSession().getUserId());
        BeanUtils.copyProperties(cloudDiskDownloadDTO, cloudDiskDownloadWrapper);
        cloudDiskDownloadWrapper.setUrl(dlDomainName + cloudDiskDownloadWrapper.getUrl());
        downloadWrapperAjaxResponse.setWrapper(cloudDiskDownloadWrapper);
        downloadWrapperAjaxResponse.setStatus("1");
        return downloadWrapperAjaxResponse;
    }

    /**
     * 预览文件  这里是预览自己的文件，不累计预览次数
     *
     * @param diskId
     * @return
     */
    @RequestMapping(value = "preview")
    @ResponseBody
    public AjaxResponse<CloudDiskPreviewWrapper> previewFile(@RequestParam("diskId") String diskId) {
        AjaxResponse<CloudDiskPreviewWrapper> previewWrapperAjaxResponse = new AjaxResponse<>();
        CloudDiskPreviewWrapper cloudDiskPreviewWrapper = new CloudDiskPreviewWrapper();
        CloudDiskPreviewDTO cloudDiskPreviewDTO;

        cloudDiskPreviewDTO = cloudDiskService.previewFile(SessionUtil.getUserSession().getSchoolId(), diskId);
        BeanUtils.copyProperties(cloudDiskPreviewDTO, cloudDiskPreviewWrapper);
        previewWrapperAjaxResponse.setWrapper(cloudDiskPreviewWrapper);
        previewWrapperAjaxResponse.setStatus("1");
        return previewWrapperAjaxResponse;
    }


    /**
     * 批量移动文件
     *
     * @param fileType
     * @param diskIds
     * @return
     */
    @RequestMapping(value = "batchMove")
    @ResponseBody
    public AjaxResponse batchMoveFiles(@RequestParam("diskIds") List<String> diskIds, @RequestParam("fileType") Integer fileType, @RequestParam("unitId") String unitId) {
        AjaxResponse ajaxResponse = new AjaxResponse();

        cloudDiskService.batchMoveFiles(SessionUtil.getUserSession().getSchoolId(), diskIds, fileType, unitId, SessionUtil.getUserSession().getUserId());

        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param diskIds 【文件ID】
     * @return 批量删除文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "batchRemove")
    @ResponseBody
    public AjaxResponse batchRemoveFiles(@RequestParam("diskIds") List<String> diskIds) {
        AjaxResponse ajaxResponse = new AjaxResponse();

        cloudDiskService.batchRemoveFiles(SessionUtil.getUserSession().getSchoolId(), diskIds, SessionUtil.getUserSession().getUserId());

        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

}
