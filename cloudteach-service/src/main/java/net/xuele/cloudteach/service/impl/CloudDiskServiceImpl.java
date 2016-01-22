package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.constant.Constants;
import net.xuele.cloudteach.constant.DiskFileOperationEnum;
import net.xuele.cloudteach.constant.ExtensionTypeUtil;
import net.xuele.cloudteach.domain.CtCloudDisk;
import net.xuele.cloudteach.domain.CtCloudDiskAmount;
import net.xuele.cloudteach.domain.CtCloudDiskShareStatistics;
import net.xuele.cloudteach.domain.CtDiskDownloads;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.CloudDiskPageRequest;
import net.xuele.cloudteach.persist.CtCloudDiskMapper;
import net.xuele.cloudteach.persist.CtCloudDiskShareMapper;
import net.xuele.cloudteach.persist.CtCloudDiskShareStatisticsMapper;
import net.xuele.cloudteach.persist.CtDiskDownloadsMapper;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.service.CloudDiskShareService;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import net.xuele.cloudteach.service.util.StringUtil;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by Administrator on 2015/6/9 0009.
 * * 云盘服务的实现，类头上加入@Service 表示该服务可暴露成dubbo服务
 *
 * @Autowired 表示自动注入依赖
 */

@Service
public class CloudDiskServiceImpl implements CloudDiskService {

    @Autowired
    private CtCloudDiskMapper ctCloudDiskMapper;

    @Autowired
    private CtDiskDownloadsMapper ctDiskDownloadsMapper;

    @Autowired
    private CloudDiskShareService cloudDiskShareService;

    @Autowired
    private CtCloudDiskShareMapper ctCloudDiskShareMapper;

    @Autowired
    private CloudTeachRedisService cloudTeachRedisService;

    @Autowired
    private CtCloudDiskShareStatisticsMapper ctCloudDiskShareStatisticsMapper;

    private static Logger logger = LoggerFactory.getLogger(CloudDiskServiceImpl.class);


    @Override
    public PageResponse<CloudDiskDTO> queryMyFilesByPage(CloudDiskPageRequest request) throws CloudteachException {

        logger.warn("分页查询云盘文件，请求参数：CloudDiskPageRequest={}",request);

        //获取总条数
        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        ctCloudDisk.setSchoolId(request.getSchoolId());
        ctCloudDisk.setUnitId(request.getUnitId());
        ctCloudDisk.setUserId(request.getUserId());
        ctCloudDisk.setFileType(request.getFileType());
        ctCloudDisk.setExtType(request.getExtType());
        long count = ctCloudDiskMapper.selectCount(ctCloudDisk);

        //获取数据
        Page page = PageUtils.buildPage(request);
        int pageSize = request.getPageSize();

        logger.warn("分页查询云盘文件，分页信息：page={}，pageSize={}", page, pageSize);
        List<CtCloudDisk> activities = ctCloudDiskMapper.selectPage(pageSize, page, ctCloudDisk);
        List<CloudDiskDTO> cloudDiskDTOList = entityListToDtoList(activities);

        //返回PageResponse
        PageResponse<CloudDiskDTO> pageResponse = new PageResponse<>();
        PageUtils.buldPageResponse(request, pageResponse);
        pageResponse.setRows(cloudDiskDTOList);
        pageResponse.setRecords(count);
        return pageResponse;
    }

    /**
     * @param cloudDiskDTO
     * @return 查询符合条件的所有云盘信息
     */
    @Override
    public List<CloudDiskDTO> queryMyFiles(CloudDiskDTO cloudDiskDTO) {
        logger.info("获取我的云盘文件列表：" + cloudDiskDTO);

        //获取数据
        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        BeanUtils.copyProperties(cloudDiskDTO, ctCloudDisk);
        List<CtCloudDisk> activities = ctCloudDiskMapper.selectAll(ctCloudDisk);
        List<CloudDiskDTO> cloudDiskDTOList = entityListToDtoList(activities);

        return cloudDiskDTOList;
    }

    /**
     * 上传云盘资源
     * 【资源信息初始化，至少需包含
     * userId:文件所属用户id、unitId:课程编号、fileUri:HDFS文件uri、
     * fileType:云盘文件类型、name：文件名、extension：扩展名、size：文件大小】
     *
     * @param cloudDiskDTO
     * @return CloudDiskDTO
     * @throws CloudteachException
     */
    @Override
    public CloudDiskDTO uploadFile(CloudDiskDTO cloudDiskDTO) throws CloudteachException {
        logger.info("上传云盘文件：" + cloudDiskDTO.toString());

        //生成云盘资源主键，处理部分需初始化的状态，插入数据到CloudDisk表，返回成功后的DTO
        CtCloudDisk ctCloudDisk = new CtCloudDisk();

        //生成主键
        cloudDiskDTO.setDiskId(UUID.randomUUID().toString().replace("-", ""));

        //状态初始化

        //扩展名不能为空，不带.
        if (cloudDiskDTO.getExtension() == null || cloudDiskDTO.getExtension().equals("")) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        cloudDiskDTO.setExtension(cloudDiskDTO.getExtension().replace(".", ""));

        cloudDiskDTO.setAddTime(new Date());
        cloudDiskDTO.setShareTime(cloudDiskDTO.getAddTime());   //新增资源时分享时间初始化为新增时间，索引性能考虑
        cloudDiskDTO.setCreator("");            //创建者
        cloudDiskDTO.setFilePk("");             //旧版系统的文件KEY
        cloudDiskDTO.setDescription("");        //描述
        cloudDiskDTO.setAuditInstructions("");  //审核说明
        cloudDiskDTO.setPid("");                //来源ID
        // 课外作业资源不与任何课程挂钩，unitId为空，设置status为0
        if (cloudDiskDTO.getUnitId() == "") {
            cloudDiskDTO.setStatus(0);
        } else {
            cloudDiskDTO.setStatus(1);              //1正常0  已删除
        }
        cloudDiskDTO.setShareStatus(0);         //私有文件，可分享
        cloudDiskDTO.setCollectStatus(0);       //收藏状态(非收藏文件)
        cloudDiskDTO.setStickyStatus(0);        //非置顶
        cloudDiskDTO.setExtType(ExtensionTypeUtil.getInstance().getExtTypeCode(cloudDiskDTO.getExtension()));//扩展名类型
        cloudDiskDTO.setExtIconType(ExtensionTypeUtil.getInstance().getIcon(cloudDiskDTO.getExtension()));//扩展名图标类型
        cloudDiskDTO.setUpdateTime(cloudDiskDTO.getAddTime());


        //上传的文件名按规则截取长度
//        cloudDiskDTO.setName(cloudDiskDTO.getName().replaceAll("[^a-zA-Z0-9_\\u4e00-\\u9fa5]",""));
        cloudDiskDTO.setName(StringUtil.fileNameSubstring(cloudDiskDTO.getName(), Constants.MAX_FILE_NAME_LENGTH));


        BeanUtils.copyProperties(cloudDiskDTO, ctCloudDisk);
        int result = ctCloudDiskMapper.insert(ctCloudDisk);
        if (1 != result) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.INSERTDISKFAILED);
        }
        return cloudDiskDTO;
    }

    /**
     * 取消收藏资源
     * 如果传入的diskId不属于当前用户，会抛出异常
     *
     * @param schoolId
     * @param diskId
     * @param currentUserID
     * @return
     * @throws CloudteachException
     */
    @Override
    public CloudDiskCollectDTO cancelCollectFile(String schoolId, String diskId, String currentUserID) {
        logger.info("取消收藏云盘文件：schoolId=" + schoolId + ";diskId=" + diskId + ";userId=" + currentUserID);

        CloudDiskCollectDTO cloudDiskCollectDTO = new CloudDiskCollectDTO();
        CtCloudDisk ctCloudDisk = ctCloudDiskMapper.selectByPrimaryKey(schoolId, diskId);

        //判断是否自己的收藏资源
        if (null == ctCloudDisk.getUserId() || (!ctCloudDisk.getUserId().equals(currentUserID))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DISKNOTBELONGTOUSER);
        }

        ctCloudDisk.setStatus(0);//删除
        ctCloudDiskMapper.updateByPrimaryKey(ctCloudDisk);
        if (ctCloudDisk.getCollectStatus() == 1) {//来自大家的分享收藏
            /** 统计表收藏数-1*/
            CtCloudDiskShareStatistics ctCloudDiskShareStatistics = new CtCloudDiskShareStatistics();
            ctCloudDiskShareStatistics.setShareId(ctCloudDisk.getPid());
            ctCloudDiskShareStatistics.setSaveTimes(-1);
            ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(ctCloudDiskShareStatistics);
        }
        BeanUtils.copyProperties(ctCloudDisk, cloudDiskCollectDTO);
        return cloudDiskCollectDTO;
    }

    /**
     * @param cloudDiskUpdateDTO 移动云盘资源
     */
    @Override
    public void moveFile(CloudDiskUpdateDTO cloudDiskUpdateDTO) {
        logger.info("移动云盘资源：" + cloudDiskUpdateDTO);

        if (cloudDiskUpdateDTO == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (cloudDiskUpdateDTO.getSchoolId() == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.SCHOOLIDCANNOTBENULL, "移动云盘资源时传入了空参数");
        }

        if (cloudDiskUpdateDTO.getUserId() == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.USERIDCANNOTBENULL, "移动云盘资源时传入了空参数");
        }
        if (cloudDiskUpdateDTO.getFileType() == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.FILETYPECANNOTBENULL, "移动云盘资源时传入了空参数");
        }

        //unitId为空时移动到原来的unit
        if (cloudDiskUpdateDTO.getUnitId() != null && cloudDiskUpdateDTO.getUnitId().trim().equals("")) {
            cloudDiskUpdateDTO.setUnitId(null);
        }

        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        BeanUtils.copyProperties(cloudDiskUpdateDTO, ctCloudDisk);
        ctCloudDiskMapper.moveFile(ctCloudDisk);
    }

    /**
     * 重命名资源
     *
     * @param cloudDiskUpdateDTO
     */
    @Override
    public void renameFile(CloudDiskUpdateDTO cloudDiskUpdateDTO) {
        logger.info("重命名资源：" + cloudDiskUpdateDTO.toString());

        //文件名如果不合法，抛出异常
        if (cloudDiskUpdateDTO.getName() == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.ILLEGALNAME);
        }

        //文件名按规则截取长度
        cloudDiskUpdateDTO.setName(StringUtil.fileNameSubstring(cloudDiskUpdateDTO.getName(), Constants.MAX_FILE_NAME_LENGTH));

        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        BeanUtils.copyProperties(cloudDiskUpdateDTO, ctCloudDisk);
        ctCloudDiskMapper.renameFile(ctCloudDisk);
    }

    /**
     * 删除资源
     *
     * @param cloudDiskUpdateDTO
     */
    @Override
    public void removeFile(CloudDiskUpdateDTO cloudDiskUpdateDTO) {

        logger.info("删除云盘资源：cloudDiskUpdateDTO:" + cloudDiskUpdateDTO);

        CtCloudDisk ctCloudDisk = ctCloudDiskMapper.selectByPrimaryKey(cloudDiskUpdateDTO.getSchoolId(), cloudDiskUpdateDTO.getDiskId());

        if (null == ctCloudDisk) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.OBJECTNOTFOUND);
        }

        //判断是否自己的收藏资源
        if (null == ctCloudDisk.getUserId() || (!ctCloudDisk.getUserId().equals(cloudDiskUpdateDTO.getUserId()))) {
            logger.error("删除云盘资源失败，相关参数：cloudDiskUpdateDTO = {}", cloudDiskUpdateDTO);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DISKNOTBELONGTOUSER);
        }

        ctCloudDisk.setStatus(0);//删除
        ctCloudDiskMapper.updateByPrimaryKey(ctCloudDisk);

        if (ctCloudDisk.getCollectStatus() != null && ctCloudDisk.getCollectStatus() == 1) {//来自大家的分享收藏
            /** 统计表收藏数-1*/
            CtCloudDiskShareStatistics ctCloudDiskShareStatistics = new CtCloudDiskShareStatistics();
            ctCloudDiskShareStatistics.setShareId(ctCloudDisk.getPid());
            ctCloudDiskShareStatistics.setSaveTimes(-1);
            ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(ctCloudDiskShareStatistics);
        }
    }

    /**
     * 置顶我的资源：仅在我的云盘中显示置顶
     *
     * @param cloudDiskUpdateDTO
     */
    @Override
    public void stickyFile(CloudDiskUpdateDTO cloudDiskUpdateDTO) {
        logger.info("置顶我的资源：" + cloudDiskUpdateDTO.toString());

        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        BeanUtils.copyProperties(cloudDiskUpdateDTO, ctCloudDisk);
        ctCloudDiskMapper.stickyFile(ctCloudDisk);
    }

    /**
     * 取消置顶我的资源
     *
     * @param cloudDiskUpdateDTO
     */
    @Override
    public void cancelStickyFile(CloudDiskUpdateDTO cloudDiskUpdateDTO) {
        logger.info("取消置顶我的资源：" + cloudDiskUpdateDTO);

        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        BeanUtils.copyProperties(cloudDiskUpdateDTO, ctCloudDisk);
        ctCloudDiskMapper.cancelStickyFile(ctCloudDisk);
    }

    /**
     * 批量移动资源
     *
     * @param schoolId
     * @param diskIds
     * @param fileType
     * @param unitId
     * @param currentUserID
     */
    @Override
    public void batchMoveFiles(String schoolId, List<String> diskIds, Integer fileType, String unitId, String currentUserID) {
        logger.info("批量移动资源：schoolId=" + schoolId + ";userId=" + currentUserID + ";unitId=" + unitId + ";fileType=" + fileType + ";diskIds=" + diskIds.toString());
        ctCloudDiskMapper.batchMoveFiles(schoolId, diskIds, fileType, unitId, currentUserID);
    }

    /**
     * 批量删除文件
     *
     * @param schoolId
     * @param diskIds
     * @param currentUserID
     */
    @Override
    public void batchRemoveFiles(String schoolId, List<String> diskIds, String currentUserID) {
        logger.info("批量删除资源：schoolId=" + schoolId + ";userId=" + currentUserID + ";diskIds=" + diskIds);

        ctCloudDiskMapper.batchRemoveFiles(schoolId, diskIds, currentUserID);

        //如果是取消收藏，更新收藏计数
        List<CtCloudDisk> diskList = ctCloudDiskMapper.queryFilesByIds(schoolId, diskIds);
        for (CtCloudDisk cloudDisk : diskList) {
            if (cloudDisk.getCollectStatus() != null && cloudDisk.getCollectStatus() == 1 && cloudDisk.getPid() != null && (!cloudDisk.getPid().trim().equals(""))) {
                cloudDiskShareService.updateCollectStatistics(cloudDisk.getPid(), -1);
            }
        }

    }

    /**
     * 下载文件
     * 如果是收藏来的资源，在对应的分享计数中更新计数值
     *
     * @param diskId
     * @param currentUserID
     * @return
     * @throws net.xuele.common.exceptions.CloudteachException
     */
    @Override
    public CloudDiskDownloadDTO downloadFile(String schoolId, String diskId, String currentUserID) throws CloudteachException {
        logger.info("下载文件：schoolId=" + schoolId + ";userId=" + currentUserID + ";diskIds=" + diskId);

        CloudDiskDownloadDTO cloudDiskDownloadDTO = new CloudDiskDownloadDTO();

        //TODO 从配置表读取配置参数
//        Integer time = 10;
//        Integer times = 3;
//        long timeMs = time * 1000;//限制时间的毫秒数

        Integer time = 2;
        Integer times = 30;
        long timeMs = time * 3600000;//限制时间的毫秒数

        String preFix = "CLOUDDISK:DOWNLOADLIMIT:" + schoolId + ":";
        String key = preFix + currentUserID;
        boolean limited = false; //是否被限制下载

        //从缓存中查询用户是否被限制 如果限制 直接抛出异常
        try {
            limited = cloudTeachRedisService.exists(key);
        } catch (Exception e) {
            //如果连接redis出现异常，查数据库判断
            long count = ctDiskDownloadsMapper.countDownload(schoolId, currentUserID, new Date(new Date().getTime() - timeMs));
            if (times < count) {
                limited = true;
            }
        }

        if (limited) {
            cloudDiskDownloadDTO.setLimited(1);
            return cloudDiskDownloadDTO;
//            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.DOWNLOADTOOFREQUENT);
        } else {
            cloudDiskDownloadDTO.setLimited(0);
        }

        //获取云盘资源信息，封装URL
        CtCloudDisk ctCloudDisk = ctCloudDiskMapper.selectByPrimaryKey(schoolId, diskId);
        BeanUtils.copyProperties(ctCloudDisk, cloudDiskDownloadDTO);
        cloudDiskDownloadDTO.setUrl(ExtensionTypeUtil.getInstance().getUrl(ctCloudDisk.getExtension(), ctCloudDisk.getFileUri(), DiskFileOperationEnum.DOWNLOAD));

        //插入下载记录
        CtDiskDownloads ctDiskDownloads = new CtDiskDownloads();
        ctDiskDownloads.setDownloadId(UUID.randomUUID().toString().replace("-", ""));
        ctDiskDownloads.setSchoolId(schoolId);
        ctDiskDownloads.setFileId(diskId);
        ctDiskDownloads.setUserId(currentUserID);
        ctDiskDownloads.setDownloadTime(new Date());
        ctDiskDownloadsMapper.insert(ctDiskDownloads);

        //如果是收藏来的资源，在对应的分享计数中更新计数值
        if (1 == ctCloudDisk.getCollectStatus() && null != ctCloudDisk.getPid()) {
            CtCloudDiskShareStatistics ctCloudDiskShareStatistics = new CtCloudDiskShareStatistics();
            ctCloudDiskShareStatistics.setShareId(ctCloudDisk.getPid());
            ctCloudDiskShareStatistics.setDownloadTimes(1);
            ctCloudDiskShareStatisticsMapper.updateByPrimaryKey(ctCloudDiskShareStatistics);
        }

        //如果超过限制，插入缓存到redis，生存时间为限制时间
        long count = ctDiskDownloadsMapper.countDownload(schoolId, currentUserID, new Date(new Date().getTime() - timeMs));
        if (times < count) {
            cloudTeachRedisService.set(key, "", timeMs / 1000);//这里要注意redis的生存时间单位是秒
        }

        return cloudDiskDownloadDTO;
    }

    /**
     * 预览文件
     *
     * @param diskId
     * @return
     */
    @Override
    public CloudDiskPreviewDTO previewFile(String schoolId, String diskId) {
        logger.info("预览文件：schoolId=" + schoolId + ";diskIds=" + diskId);

        CloudDiskPreviewDTO cloudDiskPreviewDTO = new CloudDiskPreviewDTO();

        CtCloudDisk ctCloudDisk = ctCloudDiskMapper.selectByPrimaryKey(schoolId, diskId);
        BeanUtils.copyProperties(ctCloudDisk, cloudDiskPreviewDTO);
        cloudDiskPreviewDTO.setUrl(ExtensionTypeUtil.getInstance().getUrl(ctCloudDisk.getExtension(), ctCloudDisk.getFileUri(), DiskFileOperationEnum.RELATEDVIEW));

        return cloudDiskPreviewDTO;
    }

    /**
     * 获取一组云盘资源引用数据
     *
     * @param diskIds
     * @return
     */
    @Override
    public List<CloudDiskReferDTO> queryFilesByIds(String schoolId, List<String> diskIds) {
        logger.info("获取一组云盘资源引用数据：schoolId=" + schoolId + ";diskIds=" + diskIds);

        List<CloudDiskReferDTO> diskReferDTOList = new ArrayList<>();
        List<CtCloudDisk> diskList = ctCloudDiskMapper.queryFilesByIds(schoolId, diskIds);
        for (CtCloudDisk cloudDisk : diskList) {
            CloudDiskReferDTO diskReferDTO = new CloudDiskReferDTO();
            BeanUtils.copyProperties(cloudDisk, diskReferDTO);
            diskReferDTOList.add(diskReferDTO);
        }

        logger.info("获取一组云盘资源引用数据__结果详情:" + diskReferDTOList.toString());
        return diskReferDTOList;
    }

    /**
     * 根据ID获取一个云盘资源[schoolId用于分片机制]
     *
     * @param schoolId
     * @param diskId
     * @return
     */
    @Override
    public CloudDiskDTO getFileById(String schoolId, String diskId) {
        logger.info("根据ID获取一个云盘资源：schoolId=" + schoolId + ";diskId=" + diskId);

        CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();

        CtCloudDisk ctCloudDisk = ctCloudDiskMapper.selectByPrimaryKey(schoolId, diskId);
        BeanUtils.copyProperties(ctCloudDisk, cloudDiskDTO);

        return cloudDiskDTO;
    }

    /**
     * 查询我收藏的云盘文件列表【来自大家的分享】
     * 可处理的条件：
     * unitId：课程ID，不能为空
     * userId：用户ID，不能为空
     * schoolId：学校ID，不能为空
     * fileType：资源类型[该项为空表示查询所有资源类型]
     *
     * @param cloudDiskDTO 云盘资源DTO
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<CloudDiskDTO> queryMyCollectFrShare(CloudDiskDTO cloudDiskDTO) throws CloudteachException {
        if (cloudDiskDTO == null || cloudDiskDTO.getSchoolId() == null || cloudDiskDTO.getUserId() == null || cloudDiskDTO.getUnitId() == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        logger.info("查询我收藏的云盘文件列表__来自大家的分享：schoolId=" + cloudDiskDTO.getSchoolId() + ";userId=" + cloudDiskDTO.getUserId() + ";unitId=" + cloudDiskDTO.getUnitId()
                + ";fileType=" + cloudDiskDTO.getFileType() != null ? cloudDiskDTO.getFileType().toString() : "null");

        //获取数据
        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        ctCloudDisk.setSchoolId(cloudDiskDTO.getSchoolId());    //学校ID
        ctCloudDisk.setUserId(cloudDiskDTO.getUserId());        //用户ID
        ctCloudDisk.setUnitId(cloudDiskDTO.getUnitId());        //课程ID
        ctCloudDisk.setFileType(cloudDiskDTO.getFileType());    //资源类型

        ctCloudDisk.setCollectStatus(1);                        //1：收藏来自大家的分享  2：收藏来自官方资源

        List<CtCloudDisk> activities = ctCloudDiskMapper.selectMyCollect(ctCloudDisk);
        List<CloudDiskDTO> cloudDiskDTOList = entityListToDtoList(activities);

        return cloudDiskDTOList;
    }

    /**
     * 查询我收藏的云盘文件列表【来自官方资源】
     * 可处理的条件：
     * unitId：课程ID，不能为空
     * userId：用户ID，不能为空
     * schoolId：学校ID，不能为空
     * fileType：资源类型[该项为空表示查询所有资源类型]
     *
     * @param cloudDiskDTO 云盘资源DTO
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<CloudDiskDTO> queryMyCollectFrOfficial(CloudDiskDTO cloudDiskDTO) throws CloudteachException {
        if (cloudDiskDTO == null || cloudDiskDTO.getSchoolId() == null || cloudDiskDTO.getUserId() == null || cloudDiskDTO.getUnitId() == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        logger.info("查询我收藏的云盘文件列表__来自官方资源：schoolId=" + cloudDiskDTO.getSchoolId() + ";userId=" + cloudDiskDTO.getUserId() + ";unitId=" + cloudDiskDTO.getUnitId()
                + ";fileType=" + cloudDiskDTO.getFileType() != null ? cloudDiskDTO.getFileType().toString() : "null");

        //获取数据
        CtCloudDisk ctCloudDisk = new CtCloudDisk();
        ctCloudDisk.setSchoolId(cloudDiskDTO.getSchoolId());    //学校ID
        ctCloudDisk.setUserId(cloudDiskDTO.getUserId());        //用户ID
        ctCloudDisk.setUnitId(cloudDiskDTO.getUnitId());        //课程ID
        ctCloudDisk.setFileType(cloudDiskDTO.getFileType());    //资源类型

        ctCloudDisk.setCollectStatus(2);                        //1：收藏来自大家的分享  2：收藏来自官方资源

        List<CtCloudDisk> activities = ctCloudDiskMapper.selectMyCollect(ctCloudDisk);
        List<CloudDiskDTO> cloudDiskDTOList = entityListToDtoList(activities);

        return cloudDiskDTOList;
    }

    /**
     * 列出所有资源类型对应的我的云盘资源数量
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param unitId   课程ID【非空】
     * @return List<CloudDiskAmountDTO> 资源数列表
     * @throws CloudteachException
     */
    @Override
    public List<CloudDiskAmountDTO> listDiskAmout(String schoolId, String userId, String unitId) throws CloudteachException {
        logger.info("列出所有资源类型对应的我的云盘资源数量 : schoolId=" + schoolId + ";userId=" + userId + ";unitId=" + unitId);

        if (schoolId == null || userId == null || unitId == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        List<CtCloudDiskAmount> diskAmounts = ctCloudDiskMapper.countByFileType(schoolId, userId, unitId);

        List<CloudDiskAmountDTO> diskAmountDTOList = toCloudDiskAmountDTOList(diskAmounts);

        return diskAmountDTOList;
    }


    /**
     * 查询用户在任务时间内上传的云盘资源数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 上传次数
     * @throws CloudteachException
     */
    @Override
    public Long getDiskUploadAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
        logger.info("查询用户在任务时间内上传的云盘资源数，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);

        if (schoolId == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：schoolId为空，校验未通过");
        }

        if (userId == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：userId为空，校验未通过");
        }

        if (start == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：start为空，校验未通过");
        }

        if (end == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：end为空，校验未通过");
        }

//        如果start>end,二者交换，增加代码健壮性
        if (start.compareTo(end) > 0) {
            logger.warn("传入的开始时间大于结束时间，为保证结果正确性，二者将被交换：start = {} ，end = {} ", start, end);
            Date tmp = end;
            end = start;
            start = tmp;
            logger.warn("开始时间和结束时间已完成交换：start = {} ，end = {} ", start, end);
        }

        Long times = ctCloudDiskMapper.selectUploadCount(schoolId, userId, start, end);

        if (times == null) {
            times = 0L;
        }

        logger.info("用户 {} 在 {} 至 {} 内 上传的云盘资源数为 {} ", userId, start, end, times);

        return times;
    }

    /**
     * 查询用户在任务时间内{分享成功}的云盘资源数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 分享次数
     * @throws CloudteachException
     */
    @Override
    public Long getDiskSharedAmount(String schoolId, String userId, Date start, Date end) throws CloudteachException {
        logger.info("查询用户在任务时间内分享成功的云盘资源数，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);

        if (schoolId == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：schoolId为空，校验未通过");
        }

        if (userId == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：userId为空，校验未通过");
        }

        if (start == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：start为空，校验未通过");
        }

        if (end == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , start = {} , end = {} ", schoolId, userId, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：end为空，校验未通过");
        }

//        如果start>end,二者交换，增加代码健壮性
        if (start.compareTo(end) > 0) {
            logger.warn("传入的开始时间大于结束时间，为保证结果正确性，二者将被交换：start = {} ，end = {} ", start, end);
            Date tmp = end;
            end = start;
            start = tmp;
            logger.warn("开始时间和结束时间已完成交换：start = {} ，end = {} ", start, end);
        }

        Long times = ctCloudDiskMapper.selectSharedCount(schoolId, userId, start, end);

        if (times == null) {
            times = 0L;
        }

        logger.info("用户 {} 在 {} 至 {} 内 分享成功的云盘资源数为 {} ", userId, start, end, times);

        return times;
    }


    //=======================private helper================================//

    private List<CloudDiskDTO> entityListToDtoList(List<CtCloudDisk> ctCloudDisks) {

        List<CloudDiskDTO> cloudDiskDTOList = new ArrayList<CloudDiskDTO>();
        for (CtCloudDisk mRes : ctCloudDisks) {
            CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
            BeanUtils.copyProperties(mRes, cloudDiskDTO);
            cloudDiskDTO.setExtension(cloudDiskDTO.getExtension() != null ? cloudDiskDTO.getExtension().replace(".", "") : "");
            cloudDiskDTOList.add(cloudDiskDTO);
        }
        return cloudDiskDTOList;
    }


    /**
     * diskAmount Domain对象列表 转DTO列表
     *
     * @param diskAmounts
     * @return
     */
    private List<CloudDiskAmountDTO> toCloudDiskAmountDTOList(List<CtCloudDiskAmount> diskAmounts) {

        Map<Integer, CtCloudDiskAmount> amountDTOMap = new HashMap<>();
        for (CtCloudDiskAmount diskAmount : diskAmounts) {
            amountDTOMap.put(diskAmount.getFileType(), diskAmount);
        }

        List<CloudDiskAmountDTO> diskAmountDTOList = new ArrayList<>();
        Integer all = 0;
        for (int i = 1; i < 7; i++) {
            CloudDiskAmountDTO diskAmountDTO = new CloudDiskAmountDTO();
            diskAmountDTO.setFileType(i);
            Integer num = amountDTOMap.get(i) == null ? 0 : amountDTOMap.get(i).getNum();
            all += num;
            diskAmountDTO.setNum(num);

            diskAmountDTOList.add(diskAmountDTO);
        }

        //全部分类的资源数
        CloudDiskAmountDTO diskAmountDTO = new CloudDiskAmountDTO();
        diskAmountDTO.setFileType(0);
        diskAmountDTO.setNum(all);
        diskAmountDTOList.add(diskAmountDTO);

        return diskAmountDTOList;
    }

    /**
     * @param subjectId
     * @param userId
     * @param schoolId
     * @return
     * @throws CloudteachException
     */
    @Override
    public List<CloudDiskDTO> getAllResourceBySubject(String subjectId, String userId, String schoolId) throws CloudteachException {

        logger.info("[验证参数:]");
        if (StringUtils.isEmpty(subjectId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(schoolId)) {
            throw new CloudteachException(CloudTeachErrorEnum.PARAMERROR.getMsg() + "科目ID,用户ID,学校ID都不能为空!",
                    CloudTeachErrorEnum.PARAMERROR.getCode());
        }

        List<CloudDiskDTO> dtoList = new ArrayList<>();
        logger.info("[获取科目下所有云盘资源:]");
        List<CtCloudDisk> domainList = ctCloudDiskMapper.selectAllResourceBySubject(subjectId, userId, schoolId);

        // 对象转换
        for (CtCloudDisk cloudDisk : domainList) {
            CloudDiskDTO diskDTO = new CloudDiskDTO();
            BeanUtils.copyProperties(cloudDisk, diskDTO);
            dtoList.add(diskDTO);
        }
        return dtoList;
    }
}
