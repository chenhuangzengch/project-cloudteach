package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.AcOfficialResourcePageRequest;
import net.xuele.cloudteach.dto.page.CloudDiskSharePageRequest;
import net.xuele.cloudteach.service.AcOfficialResourceService;
import net.xuele.cloudteach.service.CloudDiskShareService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.*;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * SharedResourceController
 *
 * @author panglx
 * @date 2015/6/24 0024
 */

@Controller
@RequestMapping(value = "cloudDisk/share")
public class CloudDiskShareController {
    @Autowired
    private CloudDiskShareService cloudDiskShareService;
    @Autowired
    private AcOfficialResourceService acOfficialResourceService;

    private static Logger logger = LoggerFactory.getLogger(CloudDiskController.class);

    /**
     *
     * @param map  【请求参数】
     * @return 大家的分享
     *
     */
    @RequestMapping(value = "index")
    public String index(HttpServletRequest request,ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/cloudDisk/file/index");

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();
        map.put("userId",userId);
        map.put("schoolId",schoolId);
        map.put("areaId",areaId);
        map.put("test","test");
        return "/disk/share";
    }

    /**
     * 查询大家的分享总数
     * @param unitId
     * @return
     */
    @RequestMapping(value = "shareNum")
    @ResponseBody
    public AjaxResponse shareNum(String unitId){
        logger.info("查询大家的分享总数:shareNum,unitId= "+unitId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();
        int num = cloudDiskShareService.selectShareNum(unitId,schoolId,areaId);
        AjaxResponse response = new AjaxResponse();
        response.setStatus("1");
        response.setWrapper(num);
        return response;
    }
    /**
     * 根据fileType分类计数
     * @param unitId
     * @return
     */
    @RequestMapping(value = "countByFileType")
    @ResponseBody
    public AjaxResponse countByFileType(String unitId){
        logger.info("根据fileType分类计数:countByFileType,unitId= "+unitId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();
        List<CloudDiskShareCountDTO> countDTOs = cloudDiskShareService.countByFileType(unitId,schoolId,areaId);
        AjaxResponse response = new AjaxResponse();
        response.setStatus("1");
        response.setWrapper(countDTOs);
        return response;
    }
    /**
     * @author panglx 2015/06/25
     * @param request  【请求参数】
     * @return
     * 分页获得大家分享的云盘资源
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     * * edited by hujx on 2015/07/01
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public AjaxResponse<PageResponse<CloudDiskShareWrapper>> querySharedResource(CloudDiskSharePageRequest request) {
        logger.info("分页获得大家分享的云盘资源:querySharedResource");
        AjaxResponse<PageResponse<CloudDiskShareWrapper>> userFileShareAjaxResponse = new AjaxResponse<>();

        request.setUserId(GetSessionContentUtil.getInstance().currentUserID());
        request.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
        request.setAreaId(GetSessionContentUtil.getInstance().currentAreaID());
        logger.info("schoolId:"+request.getSchoolId());
        logger.info("areaId:"+request.getAreaId());
        PageResponse<CloudDiskShareDTO> pageResponse = cloudDiskShareService.querySharedResource(request);

        List<CloudDiskShareDTO> cloudDiskShareDTOList = pageResponse.getRows();
        List<CloudDiskShareWrapper> cloudDiskShareWrapperList = new ArrayList<>();
        CloudDiskShareWrapper cloudDiskShareWrapper;
        //转换Wrapper
        for(CloudDiskShareDTO cdDTO : cloudDiskShareDTOList){
            cloudDiskShareWrapper = new CloudDiskShareWrapper();
            BeanUtils.copyProperties(cdDTO,cloudDiskShareWrapper);
            cloudDiskShareWrapper.setFileName(cdDTO.getName());//文件名
            cloudDiskShareWrapperList.add(cloudDiskShareWrapper);
        }
        PageResponse<CloudDiskShareWrapper> pageResponse1 = new PageResponse<>();
        BeanUtils.copyProperties(pageResponse,pageResponse1);
        pageResponse1.setRows(cloudDiskShareWrapperList);

        userFileShareAjaxResponse.setWrapper(pageResponse1);
        userFileShareAjaxResponse.setStatus("1");//设置状态为成功
        return userFileShareAjaxResponse;
    }

    /**
     * 取消赞
     * @author panglx 2015/06/27
     * @param shareId
     */
    @RequestMapping(value = "canclePraise")
    @ResponseBody
    public AjaxResponse<SharePraiseWrapper> cancelSharePraise(String shareId){
        logger.info("大家分享的云盘资源取消赞:canclePraise,shareId"+shareId);
        AjaxResponse<SharePraiseWrapper> ajaxResponse = new AjaxResponse<>();
        cloudDiskShareService.cancelSharePraise(shareId,GetSessionContentUtil.getInstance().currentUserID());
        SharePraiseWrapper sharePraiseWrapper = new SharePraiseWrapper();
        sharePraiseWrapper.setShareId(shareId);
        sharePraiseWrapper.setPraiseState(0);//点赞状态0--未赞
        ajaxResponse.setStatus("1");
        ajaxResponse.setWrapper(sharePraiseWrapper);
        return ajaxResponse;
    }

    /**
     * 点赞
     * @param shareId
     * @return
     */
    @RequestMapping(value = "praise")
    @ResponseBody
    public AjaxResponse<SharePraiseWrapper> praise(String shareId){
        logger.info("大家分享的云盘资源点赞:praise,shareId"+shareId);
        AjaxResponse<SharePraiseWrapper> ajaxResponse = new AjaxResponse<>();

        cloudDiskShareService.praise(shareId,GetSessionContentUtil.getInstance().currentUserID());
        SharePraiseWrapper sharePraiseWrapper = new SharePraiseWrapper();
        sharePraiseWrapper.setShareId(shareId);
        sharePraiseWrapper.setPraiseState(1);//点赞状态1--已赞
        ajaxResponse.setWrapper(sharePraiseWrapper);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }
    /**
     * 取消分享用户文件
     * @author panglx 2015/06/27
     * @param diskId
     * @return
     */
    @RequestMapping(value = "cancelShare")
    @ResponseBody
    public AjaxResponse<UserFileShareWrapper> cancelShareFile(String diskId) {
        logger.info("大家分享的云盘资源取消分享用户文件:cancelShare,diskId"+diskId);
        AjaxResponse<UserFileShareWrapper> cloudDiskShareWrapperAjaxResponse = new AjaxResponse<>();

        UserFileShareWrapper userFileShareWrapper = new UserFileShareWrapper();
        logger.info("调用服务取消分享 cancelShareFile");
        cloudDiskShareService.cancelShareFile(GetSessionContentUtil.getInstance().currentSchoolID(),diskId,GetSessionContentUtil.getInstance().currentUserID());
        cloudDiskShareWrapperAjaxResponse.setStatus("1");
        userFileShareWrapper.setUfId(diskId);//分享编号
        userFileShareWrapper.setUfShareState(0);//分享状态
        cloudDiskShareWrapperAjaxResponse.setWrapper(userFileShareWrapper);
        return cloudDiskShareWrapperAjaxResponse;
    }

    /**
     * @param diskId 【文件ID】
     * @return 收藏文件
     * 用例文档 ：【http://confluence.neiwang.com:8081/pages/viewpage.action?pageId=1769784】
     * 不可以收藏自己的资源
     * 收藏的资源collectState为1
     */
    @RequestMapping(value = "collect")
    @ResponseBody
    public AjaxResponse<CloudDiskCollectWrapper> collectFile(@RequestParam("shareId") String shareId,@RequestParam("diskId") String diskId) {
        logger.info("大家分享的云盘收藏文件:collectFile shareId="+shareId+",diskId="+diskId);
        AjaxResponse<CloudDiskCollectWrapper> userFileWrapperAjaxResponse = new AjaxResponse<>();
        CloudDiskDTO cloudDiskDTO = cloudDiskShareService.collectFile(shareId,diskId, GetSessionContentUtil.getInstance().currentUserID(),GetSessionContentUtil.getInstance().currentSchoolID());
        CloudDiskCollectWrapper cloudDiskCollectWrapper = new CloudDiskCollectWrapper(cloudDiskDTO);
        userFileWrapperAjaxResponse.setWrapper(cloudDiskCollectWrapper);
        userFileWrapperAjaxResponse.setStatus("1");
        return userFileWrapperAjaxResponse;
    }
    /**
     * @param shareId 【文件ID】
     * @return 取消收藏文件
     * 用例文档 ：【http://192.168.1.249:8081/pages/viewpage.action?pageId=1212626】
     */
    @RequestMapping(value = "cancelCollect")
    @ResponseBody
    public AjaxResponse<CloudDiskCollectWrapper> cancelCollectFile(@RequestParam("shareId") String shareId) {
        logger.info("大家分享的云盘取消收藏文件:cancelCollect shareId="+shareId);
        AjaxResponse<CloudDiskCollectWrapper> userFileWrapperAjaxResponse = new AjaxResponse<>();

        //取消收藏
        CloudDiskCollectDTO cloudDiskCollectDTO = cloudDiskShareService.cancelSharedCollectFile(shareId,
                GetSessionContentUtil.getInstance().currentUserID(),//用户id
                GetSessionContentUtil.getInstance().currentSchoolID());//学校id

        //封装ajax返回
        CloudDiskCollectWrapper cloudDiskCollectWrapper = new CloudDiskCollectWrapper(cloudDiskCollectDTO);
        userFileWrapperAjaxResponse.setStatus("1");
        userFileWrapperAjaxResponse.setWrapper(cloudDiskCollectWrapper);
        return userFileWrapperAjaxResponse;
    }
    /**
     * 预览别人分享的文件
     * @param shareId
     * @return
     */
    @RequestMapping(value = "preview")
    @ResponseBody
    public AjaxResponse<CloudDiskPreviewWrapper> previewFile(@RequestParam("shareId")String shareId){
        logger.info("预览别人分享的文件:previewFile shareId="+shareId);
        AjaxResponse<CloudDiskPreviewWrapper> previewWrapperAjaxResponse = new AjaxResponse<>();
        CloudDiskPreviewWrapper cloudDiskPreviewWrapper = new CloudDiskPreviewWrapper();
        CloudDiskPreviewDTO cloudDiskPreviewDTO;

        cloudDiskPreviewDTO= cloudDiskShareService.previewFile(shareId,GetSessionContentUtil.getInstance().currentUserID());
        BeanUtils.copyProperties(cloudDiskPreviewDTO, cloudDiskPreviewWrapper);
        previewWrapperAjaxResponse.setWrapper(cloudDiskPreviewWrapper);
        previewWrapperAjaxResponse.setStatus("1");
        return previewWrapperAjaxResponse;
    }
/************************************官方资源****************************************************************/
    /**
     *
     * @param map  【请求参数】
     * @return 大家的分享
     *
     */
    @RequestMapping(value = "acOfficialResource")
    public String acOfficialResource(HttpServletRequest request,ModelMap map) {

        /** 二级导航菜单定位 */
        request.getSession().setAttribute("lastUrl", "/cloudDisk/file/index");

        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();
        map.put("userId",userId);
        map.put("schoolId",schoolId);
        map.put("areaId",areaId);
        return "/disk/acOfficial";
    }
    /**
     * @author panglx 2015/06/25
     * @param request  【请求参数】
     * @return
     * 分页获得官方资源
     */
    @RequestMapping(value = "acOfficialList")
    @ResponseBody
    public AjaxResponse<PageResponse<AcOfficialResourceWrapper>> queryAcOfficialResource(AcOfficialResourcePageRequest request) {
        logger.info("分页获得官方资源:queryAcOfficialResource request:"+request);
        AjaxResponse<PageResponse<AcOfficialResourceWrapper>> ajaxResponse = new AjaxResponse<>();

        String userId = GetSessionContentUtil.getInstance().currentUserID();//用户id
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();//学校id
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();//地区id

        request.setUserId(userId);
        request.setSchoolId(schoolId);
        request.setAreaId(areaId);
        logger.info("调用服务分页查询官方资源");
        PageResponse<AcOfficialResourceViewDTO> pageResponse = acOfficialResourceService.queryAcOfficialResource(request);

        List<AcOfficialResourceViewDTO> acOfficialResourceViewDTOList = pageResponse.getRows();
        List<AcOfficialResourceWrapper> wrapperList= new ArrayList<>();
        AcOfficialResourceWrapper wrapper;
        //转换Wrapper
        for(AcOfficialResourceViewDTO cdDTO : acOfficialResourceViewDTOList){
            wrapper = new AcOfficialResourceWrapper();
            BeanUtils.copyProperties(cdDTO,wrapper);
            wrapperList.add(wrapper);
        }
        PageResponse<AcOfficialResourceWrapper> resultResponse = new PageResponse<>();
        BeanUtils.copyProperties(pageResponse,resultResponse);
        resultResponse.setRows(wrapperList);

        ajaxResponse.setWrapper(resultResponse);
        ajaxResponse.setStatus("1");//设置状态为成功
        return ajaxResponse;
    }
    /**
     * @param resourceId 【文件ID】
     * @return 收藏文件
     * 收藏的资源collectState为2
     */
    @RequestMapping(value = "collectAcOfficialResource")
    @ResponseBody
    public AjaxResponse<CloudDiskCollectWrapper> collectAcOfficialResource(@RequestParam("resourceId") String resourceId) {
        logger.info("官方资源收藏文件:collectAcOfficialResource resourceId:"+resourceId);
        AjaxResponse<CloudDiskCollectWrapper> ajaxResponse = new AjaxResponse<>();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("调用服务收藏官方资源:collectFile");
        CloudDiskCollectDTO cloudDiskCollectDTO = acOfficialResourceService.collectFile(resourceId,userId ,schoolId);

        CloudDiskCollectWrapper cloudDiskCollectWrapper = new CloudDiskCollectWrapper(cloudDiskCollectDTO);
        ajaxResponse.setWrapper(cloudDiskCollectWrapper);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * @param resourceId 【文件ID】
     * @return 取消收藏文件
     * 收藏的资源collectState为2
     */
    @RequestMapping(value = "unCollectAcOfficialResource")
    @ResponseBody
    public AjaxResponse<CloudDiskCollectWrapper> unCollectAcOfficialResource(@RequestParam("resourceId") String resourceId) {
        logger.info("官方资源取消收藏文件:unCollectAcOfficialResource resourceId:"+resourceId);
        AjaxResponse<CloudDiskCollectWrapper> ajaxResponse = new AjaxResponse<>();
        String userId = GetSessionContentUtil.getInstance().currentUserID();
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        logger.info("调用服务取消收藏官方资源:unCollectFile");
        CloudDiskCollectDTO cloudDiskCollectDTO = acOfficialResourceService.unCollectFile(resourceId, userId ,schoolId);

        CloudDiskCollectWrapper cloudDiskCollectWrapper = new CloudDiskCollectWrapper(cloudDiskCollectDTO);
        ajaxResponse.setWrapper(cloudDiskCollectWrapper);
        ajaxResponse.setStatus("1");
        return ajaxResponse;
    }

    /**
     * 根据fileType分类计数
     * @param unitId
     * @return
     */
    @RequestMapping(value = "countByFileTypeAc")
    @ResponseBody
    public AjaxResponse countByFileTypeAc(String unitId){
        logger.info("官方资源根据fileType分类计数:countByFileType,unitId= "+unitId);
        String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
        String areaId = GetSessionContentUtil.getInstance().currentAreaID();
        List<CloudDiskShareCountDTO> countDTOs = acOfficialResourceService.countByFileType(unitId,schoolId,areaId);
        AjaxResponse response = new AjaxResponse();
        response.setStatus("1");
        response.setWrapper(countDTOs);
        return response;
    }
}
