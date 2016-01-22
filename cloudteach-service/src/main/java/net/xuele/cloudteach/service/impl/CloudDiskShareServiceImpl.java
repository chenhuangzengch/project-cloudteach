package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.DiskFileOperationEnum;
import net.xuele.cloudteach.constant.DiskShareStatusEnum;
import net.xuele.cloudteach.constant.ExtensionTypeUtil;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CloudDiskSharePageRequest;
import net.xuele.cloudteach.dto.page.TcExtFLShareDiskPageRequest;
import net.xuele.cloudteach.persist.CtCloudDiskMapper;
import net.xuele.cloudteach.persist.CtCloudDiskShareMapper;
import net.xuele.cloudteach.persist.CtCloudDiskShareStatisticsMapper;
import net.xuele.cloudteach.persist.CtDiskSharePraiseMapper;
import net.xuele.cloudteach.service.CloudDiskShareService;
import net.xuele.cloudteach.view.CtCloudDiskShareView;
import net.xuele.cloudteach.view.TcExtFLCloudDiskShareView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * SharedResourceServiceImpl
 * 云盘--大家的分享服务
 * @author panglx
 * @date 2015/6/24 0024
 */
@Service
public class CloudDiskShareServiceImpl implements CloudDiskShareService {

    @Autowired
    private CtCloudDiskShareMapper ctCloudDiskShareMapper;

    @Autowired
    private CtDiskSharePraiseMapper ctDiskSharePraiseMapper;

    @Autowired
    private CtCloudDiskShareStatisticsMapper ctCloudDiskShareStatisticsMapper;

    @Autowired
    private CtCloudDiskMapper ctCloudDiskMapper;

    private static Logger logger = LoggerFactory.getLogger(CloudDiskShareServiceImpl.class);
    /**
     * 分页
     *
     * @param request
     * @return
     * @throws CloudteachException
     * @author panglx 2015/06/25
     */
    @Override
    public PageResponse<CloudDiskShareDTO> querySharedResource(CloudDiskSharePageRequest request) throws CloudteachException {
        logger.info("分页查询大家分享的云盘资源：querySharedResource");
        CtCloudDiskShare cloudDiskShare = new CtCloudDiskShare();
        cloudDiskShare.setUserId(request.getUserId());//用户ID
        cloudDiskShare.setUnitId(request.getUnitId());//课程编号
        Integer fileType = request.getFileType()==0?null:request.getFileType();
        cloudDiskShare.setFileType(fileType);//文件类型
        cloudDiskShare.setAreaId(request.getAreaId());//地区
        cloudDiskShare.setSchoolId(request.getSchoolId());//学校
        cloudDiskShare.setExtType(request.getExtType());
        cloudDiskShare.setShareType(request.getShareType());
        int order = request.getOrder();
        int rows = 0;
        //获取数据
        Page page = PageUtils.buildPage(request);
        page.setPage(request.getPage());

        logger.info("查询我收藏的云盘资源pid：");
        List<String> pids = ctCloudDiskMapper.queryMyCollectPid(request.getSchoolId(), request.getUnitId(), request.getUserId(), 1, fileType, request.getExtType());
        logger.info("查询我收藏的云盘资源pid："+pids);
        List<CtCloudDiskShareView> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pids) && order == 4){
            rows = ctCloudDiskShareMapper.myCount(cloudDiskShare,pids);
            logger.info("收藏数 order == 4 ："+rows);
            list = ctCloudDiskShareMapper.query(request.getPageSize(), page, cloudDiskShare, order,pids);
            for (CtCloudDiskShareView view:list){
                view.setCollectState(1);
            }
        }else if (order != 4){
            rows = ctCloudDiskShareMapper.count(cloudDiskShare);
            logger.info("总记录数 order != 4："+rows);
            list = ctCloudDiskShareMapper.query(request.getPageSize(), page, cloudDiskShare, order,pids);
            for (CtCloudDiskShareView view:list){
                if (pids.contains(view.getShareId())){
                    view.setCollectState(1);
                }
            }
        }
        //返回PageResponse
        PageResponse<CloudDiskShareDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRecords(rows);
        response.setRows(entityListToDtoList(list));
        return response;

    }

    /**
     * @param request
     * @return PageResponse<CloudDiskShareDTO>
     * @课件分页获取大家分享的云盘资源
     */
    @Override
    public PageResponse<TcExtFLCloudDiskShareDTO> queryTcExtFLSharedResource(TcExtFLShareDiskPageRequest request) throws CloudteachException {
        logger.info("分页查询大家分享的云盘资源：queryTcExtFLSharedResource");
        CtCloudDiskShare cloudDiskShare = new CtCloudDiskShare();
        cloudDiskShare.setUserId(request.getUserId());//用户ID
        cloudDiskShare.setUnitId(request.getUnitId());//课程编号
        cloudDiskShare.setAreaId(request.getAreaId());//地区
        cloudDiskShare.setSchoolId(request.getSchoolId());//学校

        if(request.getExtType()!=null
                && request.getExtType().intValue()!=2
                && request.getExtType().intValue()!=4
                && request.getExtType().intValue()!=5
                && request.getExtType().intValue()!=6
                && request.getExtType().intValue()!=7){
            request.setExtType(null);
        }
        cloudDiskShare.setExtType(request.getExtType());

        int rows = 0;
        //获取数据
        Page page = PageUtils.buildPage(request);
        page.setPage(request.getPage());

        rows = ctCloudDiskShareMapper.queryTcExtFLCount(cloudDiskShare.getSchoolId(),cloudDiskShare.getUnitId(),cloudDiskShare.getAreaId(),cloudDiskShare.getExtType());
        logger.info("收藏数 order == 4 ："+rows);
        List<TcExtFLCloudDiskShareView> list = ctCloudDiskShareMapper.queryTcExtFL(request.getPageSize(), page, cloudDiskShare.getSchoolId(),cloudDiskShare.getUnitId(),cloudDiskShare.getAreaId(),cloudDiskShare.getExtType());

        //返回PageResponse
        PageResponse<TcExtFLCloudDiskShareDTO> response = new PageResponse<>();
        PageUtils.buldPageResponse(request, response);
        response.setRecords(rows);
        response.setRows(entityTcExtFLListToDtoList(list));
        return response;

    }

    /**
     * 云盘分享统计
     * @param unitId
     * @param schoolId
     * @param areaId
     * @return
     * @throws CloudteachException
     */
    public int selectShareNum(String unitId,String schoolId,String areaId)throws CloudteachException {
        logger.info("云盘分享统计：selectShareNum");
        CtCloudDiskShare cloudDiskShare = new CtCloudDiskShare();

        cloudDiskShare.setUnitId(unitId);//课程编号
        cloudDiskShare.setAreaId(areaId);//地区
        cloudDiskShare.setSchoolId(schoolId);//学校

        return ctCloudDiskShareMapper.count(cloudDiskShare);
    }

    /**
     * 根据fileType分类计数
     * @param unitId
     * @param schoolId
     * @param areaId
     * @return
     */
    public List<CloudDiskShareCountDTO> countByFileType(String unitId,String schoolId,String areaId){
        logger.info("根据fileType分类计数：countByFileType");
        List<CtCloudDiskShareCount> shareCountList = ctCloudDiskShareMapper.countByFileType(unitId,schoolId,areaId);
        List<CloudDiskShareCountDTO> countDTOs = new ArrayList<>();
        logger.info("如果shareCountList不为空，重新给初始化countDTOs赋值");
        Map<Integer, CtCloudDiskShareCount> amountDTOMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(shareCountList)){
            for (CtCloudDiskShareCount shareCount : shareCountList) {
                amountDTOMap.put(shareCount.getFileType(), shareCount);
            }
        }
        int total=0;
        CloudDiskShareCountDTO countDTO;
        logger.info("给countDTOs赋值");
        for (int i = 1; i < 7; i++) {
            countDTO = new CloudDiskShareCountDTO();
            countDTO.setFileType(i);
            Integer num = amountDTOMap.get(i) == null ? 0 : amountDTOMap.get(i).getNum();
            countDTO.setNum(num);
            countDTOs.add(countDTO);
            total += countDTO.getNum();
        }
        logger.info("全部计数total："+total);
        countDTO = new CloudDiskShareCountDTO();
        countDTO.setFileType(0);
        countDTO.setNum(total);
        countDTOs.add(countDTO);
        return countDTOs;
    }

    /**
     * 查询统计数
     *
     * @param shareId
     * @return
     */
    @Override
    public CloudDiskShareStatisticsDTO selectDiskShareStatistics(String shareId) {
        CloudDiskShareStatisticsDTO record = new CloudDiskShareStatisticsDTO();
        BeanUtils.copyProperties(ctCloudDiskShareStatisticsMapper.selectByPrimaryKey(shareId), record);
        return record;
    }

    /**
     * 新增统计记录
     *
     * @param record
     * @return
     */
    @Override
    public int insertDiskShareStatistics(CloudDiskShareStatisticsDTO record) {
        CtCloudDiskShareStatistics cloudDiskShareStatistics = new CtCloudDiskShareStatistics();
        BeanUtils.copyProperties(record, cloudDiskShareStatistics);
        return ctCloudDiskShareStatisticsMapper.insert(cloudDiskShareStatistics);
    }

    /**
     * CtCloudDiskShare list转为CloudDiskShareDTO list
     *
     * @param ctCloudDiskShareViews
     * @return
     */
    private List<CloudDiskShareDTO> entityListToDtoList(List<CtCloudDiskShareView> ctCloudDiskShareViews) {
        List<CloudDiskShareDTO> CloudDiskShareDTOList = new ArrayList<>();
        for (CtCloudDiskShareView ctCloudDiskShareView : ctCloudDiskShareViews) {
//            CloudDiskShareStatisticsDTO cloudDiskShareStatisticsDTO = new CloudDiskShareStatisticsDTO();
            CloudDiskShareDTO cloudDiskShareDTO = new CloudDiskShareDTO();
//            BeanUtils.copyProperties(CtCloudDiskShare.getCtCloudDiskShareStatistics(), cloudDiskShareStatisticsDTO);
            BeanUtils.copyProperties(ctCloudDiskShareView, cloudDiskShareDTO);
//            cloudDiskShareDTO.setCloudDiskShareStatisticsDTO(cloudDiskShareStatisticsDTO);
            CloudDiskShareDTOList.add(cloudDiskShareDTO);
        }
        return CloudDiskShareDTOList;
    }

    private List<TcExtFLCloudDiskShareDTO> entityTcExtFLListToDtoList(List<TcExtFLCloudDiskShareView> tcExtFLCloudDiskShareViews) {
        List<TcExtFLCloudDiskShareDTO> CloudDiskShareDTOList = new ArrayList<>();
        for (TcExtFLCloudDiskShareView tcExtFLCloudDiskShareView : tcExtFLCloudDiskShareViews) {
            TcExtFLCloudDiskShareDTO cloudDiskShareDTO = new TcExtFLCloudDiskShareDTO();
            BeanUtils.copyProperties(tcExtFLCloudDiskShareView, cloudDiskShareDTO);
            CloudDiskShareDTOList.add(cloudDiskShareDTO);
        }
        return CloudDiskShareDTOList;
    }

    /**
     * 取消文件分享
     *
     * @param diskId
     * @return
     */
    public void cancelShareFile(String schoolId,String diskId,String userId) {
        logger.info("云盘分享取消文件分享");
        CtCloudDiskShare cloudDiskShare = ctCloudDiskShareMapper.selectByDiskId(diskId);

        logger.info("/** 判断是否自己上传 */");
        if (!cloudDiskShare.getUserId().equals(userId)) {
            //不是该用户的资源
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DISKNOTBELONGTOUSER);
        }
        logger.info("/**云盘分享表分享状态改为0*/");
        ctCloudDiskShareMapper.cancelShare(diskId);
        logger.info("/**云盘资源表分享状态改为0*/");
        ctCloudDiskMapper.shareUpdate(schoolId,diskId, 0);
    }

    /**
     * 点赞
     *
     * @param shareId
     * @param userId
     */
    public void praise(String shareId, String userId) throws CloudteachException {
        logger.info("/** 判断是否自己分享 */");
        int isMyShared = ctCloudDiskShareMapper.isMyShare(shareId, userId);
        if (isMyShared == 1) {
            throw new CloudteachException(CloudTeachErrorEnum.USERSHARENOPRAISE.getMsg(), CloudTeachErrorEnum.USERSHARENOPRAISE.getCode());
        }
        logger.info("/** 判断点赞记录是否存在 */");
        CtDiskSharePraise diskSharePraise = ctDiskSharePraiseMapper.selectByPrimaryKey(shareId, userId);
        //已经点赞
        if (null != diskSharePraise && diskSharePraise.getStatus() == 1) {
            throw new CloudteachException(CloudTeachErrorEnum.ALREADYPRAISED.getMsg(), CloudTeachErrorEnum.ALREADYPRAISED.getCode());
        }
        //存在点赞记录
        else if (null != diskSharePraise) {
            logger.info("存在点赞记录,点赞状态改为1");
            diskSharePraise.setStatus(1);
            ctDiskSharePraiseMapper.updateByPrimaryKey(diskSharePraise);
        }
        //没有点赞记录
        else {
            logger.info("没有点赞记录，新增点赞记录");
            diskSharePraise = new CtDiskSharePraise();
            diskSharePraise.setSharePraiseId(UUID.randomUUID().toString().replace("-",""));
            diskSharePraise.setShareId(shareId);
            diskSharePraise.setUserId(userId);
            diskSharePraise.setStatus(1);
            /** 赞--点赞表中插入一条数据*/
            ctDiskSharePraiseMapper.insert(diskSharePraise);
        }
        logger.info("/** 统计表点赞数+1*/");
        CtCloudDiskShareStatistics cloudDiskShareStatistics = new CtCloudDiskShareStatistics();
        cloudDiskShareStatistics.setShareId(shareId);
        cloudDiskShareStatistics.setPraiseTimes(1);
        ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(cloudDiskShareStatistics);
    }

    /**
     * 取消赞
     *
     * @param shareId
     * @param userId
     */
    public void cancelSharePraise(String shareId, String userId) {
        logger.info("/**判断是否点赞 */");
        CtDiskSharePraise diskSharePraise = ctDiskSharePraiseMapper.selectByPrimaryKey(shareId, userId);
        if (null == diskSharePraise || diskSharePraise.getStatus() == 0) {
            //未点赞不能取消点赞
            throw new CloudteachException(CloudTeachErrorEnum.UNPRAISED.getMsg(), CloudTeachErrorEnum.UNPRAISED.getCode());
        }
        logger.info("/** 点赞表状态改为取消赞--0 */");
        CtDiskSharePraise ctDiskSharePraise = new CtDiskSharePraise();
        ctDiskSharePraise.setShareId(shareId);
        ctDiskSharePraise.setUserId(userId);
        ctDiskSharePraise.setStatus(0);
        ctDiskSharePraiseMapper.updateByPrimaryKey(ctDiskSharePraise);
        logger.info("/** 统计表点赞数-1 */");
        CtCloudDiskShareStatistics cloudDiskShareStatistics = new CtCloudDiskShareStatistics();
        cloudDiskShareStatistics.setShareId(shareId);
        cloudDiskShareStatistics.setPraiseTimes(-1);
        ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(cloudDiskShareStatistics);
    }

    /**
     * @param diskId
     * @param userId
     * @return
     * @throws net.xuele.common.exceptions.CloudteachException 收藏资源，如果资源的来源用户是自己，会抛出异常
     */
    @Override
    public CloudDiskDTO collectFile(String shareId, String diskId, String userId,String schoolId) throws CloudteachException {
        logger.info("判断资源是否已收藏");
        if (ctCloudDiskMapper.isMyCollect(shareId, userId,schoolId) > 0) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ALREADYCILLECTED);
        }
        logger.info("判断要收藏的资源是不是自己的，如果是，抛出异常，如果不是，增加一条资源并标记collectState为1");
        if (0 < ctCloudDiskMapper.isMyFile(diskId, userId,schoolId)) {
            //要收藏的资源是我自己的资源
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.CANNOTCOLLECTBYSELF);
        }
        logger.info("如果上述判断通过，取出该分享，插入到disk表");
        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        CtCloudDiskShare ctCloudDiskShare = ctCloudDiskShareMapper.selectByPrimaryKey(shareId);

        ctCloudDisk.setDiskId(UUID.randomUUID().toString().replace("-", ""));  //ID
        ctCloudDisk.setName(ctCloudDiskShare.getName());                       //文件名
        ctCloudDisk.setDescription(ctCloudDiskShare.getDescription());         //描述
        ctCloudDisk.setSize(ctCloudDiskShare.getSize());                       //大小
        ctCloudDisk.setUserId(userId);                                         //用户ID
        ctCloudDisk.setUnitId(ctCloudDiskShare.getUnitId());                   //单元ID
        ctCloudDisk.setFilePk(ctCloudDiskShare.getFilePk());                   //文件唯一性编号
        ctCloudDisk.setFileType(ctCloudDiskShare.getFileType());               //资源类型
        ctCloudDisk.setFileUri(ctCloudDiskShare.getFileUri());                 //HDFS资源URI
        ctCloudDisk.setExtension(ctCloudDiskShare.getExtention());             //扩展名
        ctCloudDisk.setExtType(ctCloudDiskShare.getExtType());                 //扩展名类型
        ctCloudDisk.setExtIconType(ctCloudDiskShare.getExtIconType());         //图标类型
        ctCloudDisk.setAddTime(ctCloudDiskShare.getAddTime());                 //收藏时间
        ctCloudDisk.setUpdateTime(ctCloudDisk.getAddTime());                   //修改时间
        ctCloudDisk.setShareTime(ctCloudDiskShare.getShareTime());             //分享时间
        ctCloudDisk.setCollectStatus(1);                                       //来自大家的分享
        ctCloudDisk.setCreator(ctCloudDiskShare.getUserId());                  //创建者
        ctCloudDisk.setPid(ctCloudDiskShare.getShareId());                     //源ID
        ctCloudDisk.setShareStatus(0);                                         //私有
        ctCloudDisk.setStatus(1);                                              //有效
        ctCloudDisk.setStickyStatus(0);                                        //非置顶
        ctCloudDisk.setSchoolId(schoolId);                                     //学校id
        ctCloudDisk.setAuditInstructions("");                                  //审核说明
        int result = ctCloudDiskMapper.insert(ctCloudDisk);
        if (1 < result) {
            //插入失败异常
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.INSERTDISKFAILED);
        }
        logger.info("/** 统计表收藏数+1*/");
        CtCloudDiskShareStatistics cloudDiskShareStatistics = new CtCloudDiskShareStatistics();
        cloudDiskShareStatistics.setShareId(shareId);
        cloudDiskShareStatistics.setSaveTimes(1);
        ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(cloudDiskShareStatistics);

        //返回结果
        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
        BeanUtils.copyProperties(ctCloudDisk, cloudDiskDTO);
        return cloudDiskDTO;
    }

    /**
     * 后台置顶
     *
     * @param shareId
     */
    @Override
    public void stickyShareFile(String shareId) {
        CtCloudDiskShare cloudDiskShare;
        cloudDiskShare = ctCloudDiskShareMapper.selectByPrimaryKey(shareId);
        if (cloudDiskShare == null){
            //查找的对象不存在
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.OBJECTNOTFOUND);
        }else if (cloudDiskShare.getBoutique()==1){//已经置顶
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ALREADYSTICKY);
        }
        cloudDiskShare.setBoutique(1);
        int result = ctCloudDiskShareMapper.stickyShareFile(cloudDiskShare);
        if (result != 1){
            //置顶失败
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.STICKYFAILED);
        }

    }

    /**
     * 后台取消置顶
     *
     * @param shareId
     */
    @Override
    public void cancelStickyShareFile(String shareId) {

        CtCloudDiskShare cloudDiskShare;
        cloudDiskShare = ctCloudDiskShareMapper.selectByPrimaryKey(shareId);
        if (cloudDiskShare == null){
            //查找的对象不存在
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.OBJECTNOTFOUND);
        }else if (cloudDiskShare.getBoutique()==0){//未置顶
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.UNSTICKY);
        }
        cloudDiskShare.setBoutique(0);
        int result = ctCloudDiskShareMapper.stickyShareFile(cloudDiskShare);
        if (result != 1){
            //取消置顶失败
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.STICKYFAILED);
        }
    }

    /**
     * 预览别人分享的文件
     *
     * @param shareId
     * @param currentUserId
     * @return
     */
    @Override
    public CloudDiskPreviewDTO previewFile(String shareId, String currentUserId) {
        logger.info("预览分享的文件，生成url  计数");
        CloudDiskPreviewDTO cloudDiskPreviewDTO = new CloudDiskPreviewDTO();

        CtCloudDiskShare ctCloudDiskShare = ctCloudDiskShareMapper.selectByPrimaryKey(shareId);
        BeanUtils.copyProperties(ctCloudDiskShare, cloudDiskPreviewDTO);
        cloudDiskPreviewDTO.setUrl(ExtensionTypeUtil.getInstance().getUrl(ctCloudDiskShare.getExtention(), ctCloudDiskShare.getFileUri(), DiskFileOperationEnum.RELATEDVIEW));
        logger.info("增加预览数");
        if (!ctCloudDiskShare.getUserId().equals(currentUserId)) {
            CtCloudDiskShareStatistics ctCloudDiskShareStatistics = new CtCloudDiskShareStatistics();
            ctCloudDiskShareStatistics.setShareId(shareId);
            ctCloudDiskShareStatistics.setReadTimes(1);
            ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(ctCloudDiskShareStatistics);
        }

        return cloudDiskPreviewDTO;
    }

    /**
     * 取消收藏
     *
     * @param shareId
     * @param userId
     * @return
     */
    @Override
    public CloudDiskCollectDTO cancelSharedCollectFile(String shareId, String userId,String schoolId) throws CloudteachException {
        CloudDiskCollectDTO cloudDiskCollectDTO = new CloudDiskCollectDTO();
        List<CtCloudDisk> diskList = ctCloudDiskMapper.queryMyCollect(shareId, userId,schoolId);
        logger.info("判断是否自己的收藏资源");
        if (CollectionUtils.isEmpty(diskList)  ) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DISKNOTBELONGTOUSER);
        }
        CtCloudDiskShareStatistics ctCloudDiskShareStatistics;
        for (CtCloudDisk ctCloudDisk:diskList){
            ctCloudDisk.setStatus(0);//删除
            ctCloudDiskMapper.updateByPrimaryKey(ctCloudDisk);
            logger.info("/** 统计表收藏数-1*/");
            ctCloudDiskShareStatistics = new CtCloudDiskShareStatistics();
            ctCloudDiskShareStatistics.setShareId(ctCloudDisk.getPid());
            ctCloudDiskShareStatistics.setSaveTimes(-1);
            ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(ctCloudDiskShareStatistics);
        }
        BeanUtils.copyProperties(diskList.get(0), cloudDiskCollectDTO);
        return cloudDiskCollectDTO;

    }

    /**
     * 更新资源收藏数
     *      statistic+1时加一 -1时减一
     * @param shareId
     * @param statistic
     */
    @Override
    public void updateCollectStatistics(String shareId, Integer statistic) {
        CtCloudDiskShareStatistics ctCloudDiskShareStatistics = new CtCloudDiskShareStatistics();
        ctCloudDiskShareStatistics.setShareId(shareId);
        ctCloudDiskShareStatistics.setSaveTimes(statistic);
        ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(ctCloudDiskShareStatistics);
    }

    /**
     * 分享云盘资源
     * @param cloudDiskShareUserInfoDTO
     */
    @Override
    public Integer shareFile(UserShareInfoDTO cloudDiskShareUserInfoDTO) {
        CtCloudDisk ctCloudDisk = ctCloudDiskMapper.selectByPrimaryKeyForUpdate(cloudDiskShareUserInfoDTO.getSchoolId(),cloudDiskShareUserInfoDTO.getItemId());

        /** 判断是否自己上传 */
        if (ctCloudDisk.getCollectStatus() == 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DISKNOTBELONGTOUSER);
        }
        /** 判断是否已分享 */
        if (ctCloudDisk.getShareStatus() == 1||ctCloudDisk.getShareStatus()==2) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ALREADYSHARED);
        }



        /** 分享表新增记录 */
        CtCloudDiskShare record = toCtCloudDiskShare(ctCloudDisk);

        record.setShareId(UUID.randomUUID().toString().replace("-", ""));   //分享ID
        record.setBoutique(0);                                              //默认不置顶
        Date date = new Date();
        record.setShareTime(date);
        record.setUpdateTime(date);
        record.setShareType(cloudDiskShareUserInfoDTO.getShareType());      //分享范围

        record.setStatus(DiskShareStatusEnum.SHARE.getStatus());                                                //审核中
        record.setAuditInstructions("");                                    //审核说明

        record.setUserName(cloudDiskShareUserInfoDTO.getUserName());        //用户名
        record.setSchoolId(cloudDiskShareUserInfoDTO.getSchoolId());        //学校编号
        record.setSchoolName(cloudDiskShareUserInfoDTO.getSchoolName());    //学校名称
        record.setAreaId(cloudDiskShareUserInfoDTO.getAreaId());            //地区编号
        record.setAreaName(cloudDiskShareUserInfoDTO.getAreaName());        //地区名称

        //TODO 临时允许所有用户分享的文件默认为分享审核通过，auditUserId=0，来自运营的需求，当运营需要关闭此功能时，需删除下面的代码
        //临时允许所有用户分享的文件默认为分享审核通过，auditUserId=0 start
        record.setStatus(DiskShareStatusEnum.SUCCESS.getStatus());
        record.setAuditUserId("0");
        record.setAuditInstructions("");
        //临时允许所有用户分享的文件默认为分享审核通过，auditUserId=0 end

        ctCloudDiskShareMapper.insert(record);//分享表新增记录

        logger.warn("成功插入云盘分享数据：record={}", record);


        /** 统计表新增数据*/
        CtCloudDiskShareStatistics cloudDiskShareStatistics = new CtCloudDiskShareStatistics();
        cloudDiskShareStatistics.setShareId(record.getShareId());
        cloudDiskShareStatistics.setPraiseTimes(0);
        cloudDiskShareStatistics.setDownloadTimes(0);
        cloudDiskShareStatistics.setReadTimes(0);
        cloudDiskShareStatistics.setSaveTimes(0);
        ctCloudDiskShareStatisticsMapper.insert(cloudDiskShareStatistics);

        logger.warn("成功插入云盘分享统计表数据：cloudDiskShareStatistics={}", cloudDiskShareStatistics);

        /** 云盘资源表update 分享状态，分享时间*/
        ctCloudDiskMapper.shareUpdate(cloudDiskShareUserInfoDTO.getSchoolId(),cloudDiskShareUserInfoDTO.getItemId(), record.getStatus());

        logger.warn("成功更新云盘数据分享状态：ItemId={}，status={}", cloudDiskShareUserInfoDTO.getItemId(), record.getStatus());

        return record.getStatus();
    }

    private CtCloudDiskShare toCtCloudDiskShare(CtCloudDisk ctCloudDisk) {
        if (ctCloudDisk == null) {
            return null;
        }
        CtCloudDiskShare ctCloudDiskShare = new CtCloudDiskShare();
        ctCloudDiskShare.setDiskId(ctCloudDisk.getDiskId());
        ctCloudDiskShare.setUserId(ctCloudDisk.getUserId());
        ctCloudDiskShare.setUnitId(ctCloudDisk.getUnitId());
        ctCloudDiskShare.setFilePk(ctCloudDisk.getFilePk());
        ctCloudDiskShare.setFileUri(ctCloudDisk.getFileUri());
        ctCloudDiskShare.setFileType(ctCloudDisk.getFileType());
        ctCloudDiskShare.setName(ctCloudDisk.getName());
        ctCloudDiskShare.setDescription(ctCloudDisk.getDescription());
        ctCloudDiskShare.setExtention(ctCloudDisk.getExtension());
        ctCloudDiskShare.setExtType(ctCloudDisk.getExtType());
        ctCloudDiskShare.setExtIconType(ctCloudDisk.getExtIconType());
        ctCloudDiskShare.setSize(ctCloudDisk.getSize());
        ctCloudDiskShare.setAddTime(ctCloudDisk.getAddTime());
        ctCloudDiskShare.setUpdateTime(ctCloudDisk.getUpdateTime());
        ctCloudDiskShare.setShareTime(ctCloudDisk.getShareTime());
        ctCloudDiskShare.setAuditInstructions(ctCloudDisk.getAuditInstructions());
        ctCloudDiskShare.setSchoolId(ctCloudDisk.getSchoolId());
        ctCloudDiskShare.setStatus(ctCloudDisk.getStatus());
        return ctCloudDiskShare;
    }

}
