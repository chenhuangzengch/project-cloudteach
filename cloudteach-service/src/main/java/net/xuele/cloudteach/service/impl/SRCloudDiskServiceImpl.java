package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.SRCloudDiskPageRequest;
import net.xuele.cloudteach.persist.CtCloudDiskMapper;
import net.xuele.cloudteach.service.SRCloudDiskService;
import net.xuele.cloudteach.service.util.DateTimeUtil;
import net.xuele.cloudteach.view.SRResourceSharedView;
import net.xuele.cloudteach.view.SRResourceStatisticsNumView;
import net.xuele.cloudteach.view.SRSchoolResourceStatisticsView;
import net.xuele.cloudteach.view.SRTeacherResourceStatisticsView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SRCloudDiskServiceImpl
 * 云盘资源统计服务
 *
 * @author duzg
 * @date 2015/8/5
 */

@Service
public class SRCloudDiskServiceImpl implements SRCloudDiskService {

    private static Logger logger = LoggerFactory.getLogger(SRCloudDiskServiceImpl.class);

    @Autowired
    CtCloudDiskMapper ctCloudDiskMapper;

    /**
     * 获取区划下的资源数量
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param areaId 区域ID
     * @return
     */
    @Override
    public SRAreaResourceAmountDTO getAreaUnderResourceAmount(Date startDate, Date endDate, String areaId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下的资源数量,startDate:"+startDate+",endDate:"+endDate+",areaId:"+areaId);
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取资源数量
        SRAreaResourceAmountDTO sRAreaResourceAmountDTO = new SRAreaResourceAmountDTO();
        sRAreaResourceAmountDTO.setResourceAmount(ctCloudDiskMapper.getAreaUnderResourceAmount(areaId,begTime,endTime));
        sRAreaResourceAmountDTO.setSchoolAmount(ctCloudDiskMapper.getSchoolResourceStatisticsCount(areaId,begTime,endTime));
        sRAreaResourceAmountDTO.setTeacherAmount(ctCloudDiskMapper.getTeacherAmount(areaId, null));
        return sRAreaResourceAmountDTO;
    }

    /**
     * 获取区划下的分享资源数量
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param areaId 区域ID
     * @return
     */
    @Override
    public SRAreaResourceAmountDTO getAreaUnderShareResourceAmount(Date startDate, Date endDate, String areaId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下的资源数量,startDate:"+startDate+",endDate:"+endDate+",areaId:"+areaId);
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取资源数量
        SRAreaResourceAmountDTO sRAreaResourceAmountDTO = new SRAreaResourceAmountDTO();
        sRAreaResourceAmountDTO.setResourceAmount(ctCloudDiskMapper.getSharedResourceCount(areaId, null, null, begTime, endTime));
        sRAreaResourceAmountDTO.setSchoolAmount(ctCloudDiskMapper.getShareResourceSchoolAmount(areaId, begTime, endTime));
        sRAreaResourceAmountDTO.setTeacherAmount(ctCloudDiskMapper.getShareResourceTeacherAmount(areaId,null, begTime, endTime));
        return sRAreaResourceAmountDTO;
    }

    /**
     * 获取区划下的最新分享（分页请求）
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,区域ID:String areaId)
     * @return
     */
    @Override
    public PageResponse<SRResourceSharedDTO> getAreaUnderShared(SRCloudDiskPageRequest request) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下的最新分享,request:"+request);
        String areaId = request.getAreaId();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        int count = ctCloudDiskMapper.getSharedResourceCount(areaId, null,null, begTime, endTime);
        List<SRResourceSharedView> areaUnderSharedlist = ctCloudDiskMapper.getSharedResource(pagesize, page, areaId, null,null, begTime, endTime);

        //返回PageResponse
        PageResponse<SRResourceSharedDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entitySRResourceSharedListToDtoList(areaUnderSharedlist));
        response.setRecords(count);
        return response;
    }

    /**
     * 获取区划下的资源统计
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param areaId 区域ID
     * @return
     */
    @Override
    public SRResourceStatisticsDTO getAreaUnderResourceStatistics(Date startDate, Date endDate, String areaId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下的资源统计,startDate:"+startDate+",endDate:"+endDate+",areaId:"+areaId);
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //文件类型分类数据
        List<SRResourceStatisticsNumView> fileTypeStatisticsList = ctCloudDiskMapper.getResourceSortFileType(areaId,null,null,begTime,endTime);
        //后最类型分类数据
        List<SRResourceStatisticsNumView> extTypeStatisticsList = ctCloudDiskMapper.getResourceSortExtType(areaId, null, null, begTime, endTime);
        //资源总数
        int resourceNum = ctCloudDiskMapper.getAreaUnderResourceAmount(areaId, begTime, endTime);
        //已分享数
        int sharedNum = ctCloudDiskMapper.getSharedResourceCount(areaId,null,null,begTime,endTime);

        SRResourceStatisticsDTO sRResourceStatisticsDTO = new SRResourceStatisticsDTO();
        sRResourceStatisticsDTO.setResourceTotal(resourceNum);
        sRResourceStatisticsDTO.setSharedAmount(sharedNum);
        if(sRResourceStatisticsDTO.getResourceTotal().intValue()<sRResourceStatisticsDTO.getSharedAmount().intValue()){
            sRResourceStatisticsDTO.setResourceTotal(sharedNum);
        }
        sRResourceStatisticsDTO.setUnSharedAmount(sRResourceStatisticsDTO.getResourceTotal().intValue()-sRResourceStatisticsDTO.getSharedAmount().intValue());

        for(SRResourceStatisticsNumView viewObj : fileTypeStatisticsList){
            if(viewObj.getTypeCode() == null){
                continue;
            }
            switch (viewObj.getTypeCode().intValue()){
                case 1://1其他
                    sRResourceStatisticsDTO.setResourceOtherAmount(viewObj.getTypeNum());
                    break;
                case 2://2教案
                    sRResourceStatisticsDTO.setTeachPlanAmount(viewObj.getTypeNum());
                    break;
                case 3://3学案
                    sRResourceStatisticsDTO.setLearnPlanAmount(viewObj.getTypeNum());
                    break;
                case 4://4课件
                    sRResourceStatisticsDTO.setCoursewareAmount(viewObj.getTypeNum());
                    break;
                case 5://5习题
                    sRResourceStatisticsDTO.setExercisesAmount(viewObj.getTypeNum());
                    break;
                case 6://6课程素材
                    sRResourceStatisticsDTO.setFodderAmount(viewObj.getTypeNum());
                    break;
                default:
                    sRResourceStatisticsDTO.setResourceOtherAmount(viewObj.getTypeNum());
            }
        }
        for(SRResourceStatisticsNumView viewObj : extTypeStatisticsList){
            if(viewObj.getTypeCode() == null){
                continue;
            }
            switch (viewObj.getTypeCode().intValue()){
                case 1://1其他
                    sRResourceStatisticsDTO.setTypeOtherAmount(viewObj.getTypeNum());
                    break;
                case 2://2ppt
                    sRResourceStatisticsDTO.setPptAmount(viewObj.getTypeNum());
                    break;
                case 3://3word
                    sRResourceStatisticsDTO.setWordAmount(viewObj.getTypeNum());
                    break;
                case 4://4视频
                    sRResourceStatisticsDTO.setVideoAmount(viewObj.getTypeNum());
                    break;
                case 5://5录音
                    sRResourceStatisticsDTO.setVioceAmount(viewObj.getTypeNum());
                    break;
                case 6://6图片
                    sRResourceStatisticsDTO.setPictureAmount(viewObj.getTypeNum());
                    break;
                default:
                    sRResourceStatisticsDTO.setTypeOtherAmount(viewObj.getTypeNum());
            }
        }

        return sRResourceStatisticsDTO;
    }

    /**
     * 获取区划下学校资源统计（分页请求）
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,区域ID:String areaId)
     * @return
     */
    @Override
    public PageResponse<SRSchoolResourceStatisticsDTO> getSchoolResourceStatistics(SRCloudDiskPageRequest request) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下学校资源统计,request:"+request);
        String areaId = request.getAreaId();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        int count = ctCloudDiskMapper.getSchoolResourceStatisticsCount(areaId, begTime, endTime);
        List<SRSchoolResourceStatisticsView> schoolresourceStatisticslist = ctCloudDiskMapper.getSchoolResourceStatistics(pagesize, page, areaId, begTime, endTime);

        //返回PageResponse
        PageResponse<SRSchoolResourceStatisticsDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entitySRResourceStatisticsListToDtoList(schoolresourceStatisticslist));
        response.setRecords(count);
        return response;

    }

    /**
     * 获取区划下教师资源统计（分页请求）
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,区域ID:String areaId)
     * @return
     */
    @Override
    public PageResponse<SRTeacherResourceStatisticsDTO> getTeacherResourceStatistics(SRCloudDiskPageRequest request) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下教师资源统计,request:"+request);
        String areaId = request.getAreaId();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        int count = ctCloudDiskMapper.getTeacherResourceStatisticsCount(areaId, null, begTime, endTime);
        List<SRTeacherResourceStatisticsView> teacherResourceStatisticslist = ctCloudDiskMapper.getTeacherResourceStatistics(pagesize, page, areaId, null,begTime, endTime);

        //返回PageResponse
        PageResponse<SRTeacherResourceStatisticsDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entitySRTeacherResourceStatisticsListToDtoList(teacherResourceStatisticslist));
        response.setRecords(count);
        return response;
    }

    /**
     * 获取学校下最新分享（分页请求）
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,学校ID:String schoolId)
     * @return
     */
    @Override
    public PageResponse<SRResourceSharedDTO> getSchoolUnderShared(SRCloudDiskPageRequest request) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取学校下最新分享,request:"+request);
        String schoolId = request.getSchoolId();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        int count = ctCloudDiskMapper.getSharedResourceCount(null,schoolId,null,begTime,endTime);
        List<SRResourceSharedView> areaUnderSharedlist = ctCloudDiskMapper.getSharedResource(pagesize, page, null, schoolId, null,begTime, endTime);

        //返回PageResponse
        PageResponse<SRResourceSharedDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entitySRResourceSharedListToDtoList(areaUnderSharedlist));
        response.setRecords(count);
        return response;
    }

    /**
     * 获取学校下的资源数量
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public SRSchoolResourceAmountDTO getSchoolUnderResourceAmount(Date startDate, Date endDate, String schoolId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取学校下的资源数量,startDate:"+startDate+",endDate:"+endDate+",schoolId:"+schoolId);
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取资源数量
        SRSchoolResourceAmountDTO sRSchoolResourceAmountDTO = new SRSchoolResourceAmountDTO();
        sRSchoolResourceAmountDTO.setResourceAmount(ctCloudDiskMapper.getSchoolUnderResourceAmount(schoolId, begTime, endTime));
        sRSchoolResourceAmountDTO.setTeacherAmount(ctCloudDiskMapper.getTeacherAmount(null,schoolId));
        return sRSchoolResourceAmountDTO;
    }

    /**
     * 获取学校下的分享资源数量
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public SRSchoolResourceAmountDTO getSchoolUnderShareResourceAmount(Date startDate, Date endDate, String schoolId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取学校下的资源数量,startDate:"+startDate+",endDate:"+endDate+",schoolId:"+schoolId);
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取资源数量
        SRSchoolResourceAmountDTO sRSchoolResourceAmountDTO = new SRSchoolResourceAmountDTO();
        sRSchoolResourceAmountDTO.setResourceAmount(ctCloudDiskMapper.getSharedResourceCount(null,schoolId,null, begTime, endTime));
        sRSchoolResourceAmountDTO.setTeacherAmount(ctCloudDiskMapper.getShareResourceTeacherAmount(null, schoolId, begTime, endTime));
        return sRSchoolResourceAmountDTO;
    }

    /**
     * 获取学校下资源统计
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param schoolId 学校ID
     * @return
     */
    @Override
    public SRResourceStatisticsDTO getSchoolUnderResourceStatistics(Date startDate, Date endDate, String schoolId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取学校下资源统计,startDate:"+startDate+",endDate:"+endDate+",schoolId:"+schoolId);
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //文件类型分类数据
        List<SRResourceStatisticsNumView> fileTypeStatisticsList = ctCloudDiskMapper.getResourceSortFileType(null,schoolId,null,begTime,endTime);
        //后最类型分类数据
        List<SRResourceStatisticsNumView> extTypeStatisticsList = ctCloudDiskMapper.getResourceSortExtType(null,schoolId, null, begTime, endTime);
        //资源总数
        int resourceNum = ctCloudDiskMapper.getSchoolUnderResourceAmount(schoolId, begTime, endTime);
        //已分享数
        int sharedNum = ctCloudDiskMapper.getSharedResourceCount(null,schoolId,null,begTime,endTime);

        SRResourceStatisticsDTO sRResourceStatisticsDTO = new SRResourceStatisticsDTO();
        sRResourceStatisticsDTO.setResourceTotal(resourceNum);
        sRResourceStatisticsDTO.setSharedAmount(sharedNum);
        if(sRResourceStatisticsDTO.getResourceTotal().intValue()<sRResourceStatisticsDTO.getSharedAmount().intValue()){
            sRResourceStatisticsDTO.setResourceTotal(sharedNum);
        }
        sRResourceStatisticsDTO.setUnSharedAmount(sRResourceStatisticsDTO.getResourceTotal().intValue()-sRResourceStatisticsDTO.getSharedAmount().intValue());

        for(SRResourceStatisticsNumView viewObj : fileTypeStatisticsList){
            if(viewObj.getTypeCode() == null){
                continue;
            }
            switch (viewObj.getTypeCode().intValue()){
                case 1://1其他
                    sRResourceStatisticsDTO.setResourceOtherAmount(viewObj.getTypeNum());
                    break;
                case 2://2教案
                    sRResourceStatisticsDTO.setTeachPlanAmount(viewObj.getTypeNum());
                    break;
                case 3://3学案
                    sRResourceStatisticsDTO.setLearnPlanAmount(viewObj.getTypeNum());
                    break;
                case 4://4课件
                    sRResourceStatisticsDTO.setCoursewareAmount(viewObj.getTypeNum());
                    break;
                case 5://5习题
                    sRResourceStatisticsDTO.setExercisesAmount(viewObj.getTypeNum());
                    break;
                case 6://6课程素材
                    sRResourceStatisticsDTO.setFodderAmount(viewObj.getTypeNum());
                    break;
                default:
                    sRResourceStatisticsDTO.setResourceOtherAmount(viewObj.getTypeNum());
            }
        }
        for(SRResourceStatisticsNumView viewObj : extTypeStatisticsList){
            if(viewObj.getTypeCode() == null){
                continue;
            }
            switch (viewObj.getTypeCode().intValue()){
                case 1://1其他
                    sRResourceStatisticsDTO.setTypeOtherAmount(viewObj.getTypeNum());
                    break;
                case 2://2ppt
                    sRResourceStatisticsDTO.setPptAmount(viewObj.getTypeNum());
                    break;
                case 3://3word
                    sRResourceStatisticsDTO.setWordAmount(viewObj.getTypeNum());
                    break;
                case 4://4视频
                    sRResourceStatisticsDTO.setVideoAmount(viewObj.getTypeNum());
                    break;
                case 5://5录音
                    sRResourceStatisticsDTO.setVioceAmount(viewObj.getTypeNum());
                    break;
                case 6://6图片
                    sRResourceStatisticsDTO.setPictureAmount(viewObj.getTypeNum());
                    break;
                default:
                    sRResourceStatisticsDTO.setTypeOtherAmount(viewObj.getTypeNum());
            }
        }

        return sRResourceStatisticsDTO;
    }

    /**
     * 获取学校下教师资源统计（分页请求）
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,学校ID:String schoolId)
     * @return
     */
    @Override
    public PageResponse<SRTeacherResourceStatisticsDTO> getSchoolUnderTeacherStatistics(SRCloudDiskPageRequest request) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取学校下教师资源统计,request:"+request);
        String schoolId = request.getSchoolId();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        int count = ctCloudDiskMapper.getTeacherResourceStatisticsCount( null,schoolId, begTime, endTime);
        List<SRTeacherResourceStatisticsView> teacherResourceStatisticslist = ctCloudDiskMapper.getTeacherResourceStatistics(pagesize, page, null,schoolId,begTime, endTime);

        //返回PageResponse
        PageResponse<SRTeacherResourceStatisticsDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entitySRTeacherResourceStatisticsListToDtoList(teacherResourceStatisticslist));
        response.setRecords(count);
        return response;
    }

    /**
     * 获取当前教师最新分享（分页请求）
     * @param request (统计开始日期:Date startDate,统计截止日期:Date endDate,教师ID:String userId,学校ID:String schoolId)
     * @return
     */
    @Override
    public PageResponse<SRResourceSharedDTO> getTeacherUnderShared(SRCloudDiskPageRequest request) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取当前教师最新分享,request:"+request);
        String schoolId = request.getSchoolId();
        String userId = request.getUserId();
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(userId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":教师ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取数据
        Page page = PageUtils.buildPage(request);
        int pagesize = request.getPageSize();
        int count = ctCloudDiskMapper.getSharedResourceCount(null,schoolId,userId,begTime,endTime);
        List<SRResourceSharedView> areaUnderSharedlist = ctCloudDiskMapper.getSharedResource(pagesize, page, null, schoolId,userId, begTime, endTime);

        //返回PageResponse
        PageResponse<SRResourceSharedDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRows(entitySRResourceSharedListToDtoList(areaUnderSharedlist));
        response.setRecords(count);
        return response;
    }

    /**
     * 获取教师资源数量
     * @param startDate 统计开始日期
     * @param endDate 统计截止日期
     * @param teacherId 教师ID
     * @return
     */
    @Override
    public SRTeacherResourceAmountDTO getTeacherUnderResourceAmount(Date startDate, Date endDate, String teacherId, String schoolId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取教师资源数量");
        if(StringUtils.isEmpty(teacherId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":教师ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        //获取资源数量
        SRTeacherResourceAmountDTO sRTeacherResourceAmountDTO = new SRTeacherResourceAmountDTO();
        sRTeacherResourceAmountDTO.setResourceTotal(ctCloudDiskMapper.getTeacherUnderResourceAmount(schoolId, teacherId, begTime, endTime));
        sRTeacherResourceAmountDTO.setSharedAmount(ctCloudDiskMapper.getTeacherUnderShareResourceAmount(schoolId, teacherId, begTime, endTime));
        return sRTeacherResourceAmountDTO;
    }

    /**
     *  获取区划下学校资源统计列表(导出Excel)
     */
    @Override
    public SRCloudDiskExcelDTO<SRSchoolResourceStatisticsDTO> getSchoolResourceStatisticsExport(Date startDate, Date endDate, String areaId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下学校资源统计列表");
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        List<SRSchoolResourceStatisticsView> schoolresourceStatisticslist = ctCloudDiskMapper.getSchoolResourceStatistics(-1,null,areaId, begTime, endTime);
        List<SRSchoolResourceStatisticsDTO> list = entitySRResourceStatisticsListToDtoList(schoolresourceStatisticslist);
        SRCloudDiskExcelDTO<SRSchoolResourceStatisticsDTO> excelDTO = new SRCloudDiskExcelDTO<>();
        excelDTO.setList(list);
        return excelDTO;
    }

    /**
     *  获取区划下教师资源统计列表(导出Excel)
     */
    @Override
    public SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO> getTeacherResourceStatisticsExport(Date startDate, Date endDate, String areaId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:获取区划下教师资源统计列表");
        if(StringUtils.isEmpty(areaId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":区划ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        List<SRTeacherResourceStatisticsView> teacherResourceStatisticslist = ctCloudDiskMapper.getTeacherResourceStatistics(-1, null, areaId, null,begTime, endTime);
        List<SRTeacherResourceStatisticsDTO> list = entitySRTeacherResourceStatisticsListToDtoList(teacherResourceStatisticslist);
        SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO> excelDTO = new SRCloudDiskExcelDTO<>();
        excelDTO.setList(list);
        return excelDTO;
    }

    /**
     * 学校下教师资源统计列表（导出Excel）
     */
    @Override
    public SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO> getSchoolUnderTeacherStatisticsExport(Date startDate, Date endDate, String schoolId) throws CloudteachException {
        logger.info("SRCloudDiskServiceImpl:学校下教师资源统计列表");
        if(StringUtils.isEmpty(schoolId)){
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg()+":学校ID不能为空",CloudTeachErrorEnum.PARAMERROR.getCode());
        }
        String begTime = "";
        String endTime = "";
        if(startDate==null || startDate.getTime() == 0){
            begTime = null;
        }else{
            begTime = DateTimeUtil.DateToStringForMycat(startDate);
        }
        if(endDate==null || endDate.getTime() == 0){
            endTime = null;
        }else{
            endTime = DateTimeUtil.DateToStringForMycat(endDate);
        }
        List<SRTeacherResourceStatisticsView> teacherResourceStatisticslist = ctCloudDiskMapper.getTeacherResourceStatistics(-1, null, null,schoolId,begTime, endTime);
        List<SRTeacherResourceStatisticsDTO> list = entitySRTeacherResourceStatisticsListToDtoList(teacherResourceStatisticslist);
        SRCloudDiskExcelDTO<SRTeacherResourceStatisticsDTO> excelDTO = new SRCloudDiskExcelDTO<>();
        excelDTO.setList(list);
        return excelDTO;
    }


    //*************************private methods****************************//
    private List<SRResourceSharedDTO> entitySRResourceSharedListToDtoList(List<SRResourceSharedView> datalist) {
        List<SRResourceSharedDTO> resList = new ArrayList<>();
        for (SRResourceSharedView objDATA : datalist) {
            SRResourceSharedDTO objDTO = new SRResourceSharedDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    private List<SRSchoolResourceStatisticsDTO> entitySRResourceStatisticsListToDtoList(List<SRSchoolResourceStatisticsView> datalist) {
        List<SRSchoolResourceStatisticsDTO> resList = new ArrayList<>();
        for (SRSchoolResourceStatisticsView objDATA : datalist) {
            SRSchoolResourceStatisticsDTO objDTO = new SRSchoolResourceStatisticsDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }

    private List<SRTeacherResourceStatisticsDTO> entitySRTeacherResourceStatisticsListToDtoList(List<SRTeacherResourceStatisticsView> datalist) {
        List<SRTeacherResourceStatisticsDTO> resList = new ArrayList<>();
        for (SRTeacherResourceStatisticsView objDATA : datalist) {
            SRTeacherResourceStatisticsDTO objDTO = new SRTeacherResourceStatisticsDTO();
            BeanUtils.copyProperties(objDATA, objDTO);
            resList.add(objDTO);
        }
        return resList;
    }
}
