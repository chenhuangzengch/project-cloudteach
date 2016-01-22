package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.BankItemService;
import net.xuele.cloudteach.service.CloudDiskService;
import net.xuele.cloudteach.view.BankItemView;
import net.xuele.common.exceptions.CloudteachException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * BankItemServiceImpl
 * 题库服务
 * 用于处理预习、电子作业、口语作业的题库相关的业务
 *
 * @author sunxh
 * @date 15/7/25
 */

@Service
public class BankItemServiceImpl implements BankItemService {

    @Autowired
    CloudDiskService cloudDiskService;

    @Autowired
    CtBankItemMapper ctBankItemMapper;

    @Autowired
    CtBankItemShareMapper ctBankItemShareMapper;

    @Autowired
    CtBankItemShareFilesMapper ctBankItemShareFilesMapper;

    @Autowired
    CtBankItemShareStatisticsMapper ctBankItemShareStatisticsMapper;//分享统计表Mapper

    @Autowired
    CtBankItemFilesMapper ctBankItemFilesMapper;

    @Autowired
    CtBankItemShareCollectMapper ctBankItemShareCollectMapper;

    private static Logger logger = LoggerFactory.getLogger(BankItemServiceImpl.class);


    /**
     * 我的题库列表
     *
     * @param userId   用户ID
     * @param schoolId 学校ID【用于分片查询】
     * @param unitId   课程ID
     * @param itemType 题目类型：1预习 4电子作业 7口语作业
     * @return bankItemDTOList  题目DTO列表
     */
    @Override
    public List<BankItemDTO> queryItemList(String userId, String schoolId, String unitId, Integer itemType) {
        logger.info("查询我的题目列表：schoolId=" + schoolId + ";userId=" + userId + ";unitId=" + unitId + ";itemType=" + itemType);

        List<BankItemDTO> bankItemDTOList;

        List<BankItemView> bankItems = ctBankItemMapper.selectMyItemList(schoolId, userId, unitId, itemType, null);

        bankItemDTOList = entityListToDtoList(bankItems);

        return bankItemDTOList;
    }

    /**
     * 我的题库列表【判断是否收藏】
     *
     * @param userId    用户ID
     * @param schoolId  学校ID【用于分片查询】
     * @param unitId    课程ID
     * @param itemType  题目类型：1预习 4电子作业 7口语作业
     * @param isCollect 是否收藏：1来自收藏 0非收藏 null全部
     * @return bankItemDTOList  题目DTO列表
     */
    @Override
    public List<BankItemDTO> queryItemList(String userId, String schoolId, String unitId, Integer itemType, Integer isCollect) {
        List<BankItemDTO> bankItemDTOList;

        List<BankItemView> bankItems = ctBankItemMapper.selectMyItemList(schoolId, userId, unitId, itemType, isCollect);

        bankItemDTOList = entityListToDtoList(bankItems);

        return bankItemDTOList;
    }

    /**
     * 创建题目
     *
     * @param itemCreateDTO 题目创建DTO
     * @return String 题目ID
     * @throws CloudteachException
     */
    @Override
    public String createBankItem(BankItemCreateDTO itemCreateDTO) throws CloudteachException {
        logger.info("创建题目：" + itemCreateDTO);

        CtBankItem ctBankItem = toCtBankItem(itemCreateDTO);

        //初始化预习题数据
        ctBankItem.setItemId(UUID.randomUUID().toString().replace("-", ""));        //题目ID
        ctBankItem.setStatus(1);                                                    //有效
        ctBankItem.setCreateTime(new Date());                                       //创建时间
        ctBankItem.setUpdateTime(ctBankItem.getCreateTime());                   //修改时间
        ctBankItem.setIsCollect(0);                                                 //非收藏
        ctBankItem.setPid("");                                                      //源ID
        ctBankItem.setUserId(ctBankItem.getCreator());                          //所属用户
        //ctBankItem.setSubOther(0);                                                  //其他提交方式[预留]       /** 注: 20151009, 将subOther改成不限制提交方式, 由页面勾选 **/
        ctBankItem.setStickStatus(0);                                               //非置顶
        ctBankItem.setShareStatus(0);                                               //私有

        logger.info("[新建作业题目ID: {}]", ctBankItem.getItemId());

        int result = ctBankItemMapper.insert(ctBankItem);

        if (result < 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_CTEATE_FAILED);
        }

        //插入预习题附件
        if (CollectionUtils.isNotEmpty(itemCreateDTO.getDiskIds())) {
            result = ctBankItemFilesMapper.batchInsert(ctBankItem.getSchoolId(), CloudTeachBankEnum.FILE_TYPE_TEACHERQUE.getCode(), ctBankItem.getItemId(), itemCreateDTO.getDiskIds());
            if (result < 1) {
                CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_CTEATE_FAILED);
            }
        }

        // /** 20151016 课外作业上传本地附件处理 **/
        List<BankItemAppFilesDTO> filesList = itemCreateDTO.getFilesList();
        if (CollectionUtils.isNotEmpty(filesList)) {
            logger.info("[课外作业上传本地附件处理:]");
            for (BankItemAppFilesDTO filesDTO : filesList) {
                logger.info("[获取文件filesDTO:{}]", filesDTO);
                // 文件存入云盘
                logger.info("[上传文件夹存入云盘]");
                CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
                cloudDiskDTO.setExtension(filesDTO.getExtension());//扩展名
                cloudDiskDTO.setFileUri(filesDTO.getUrl());//文件uri
                cloudDiskDTO.setSize(filesDTO.getSize());
                cloudDiskDTO.setName(filesDTO.getFileName());
                cloudDiskDTO.setUnitId("");
                cloudDiskDTO.setFileType(999);
                cloudDiskDTO.setUserId(itemCreateDTO.getCreator());
                cloudDiskDTO.setSchoolId(itemCreateDTO.getSchoolId());
                CloudDiskDTO newCloudDisk = cloudDiskService.uploadFile(cloudDiskDTO);
                if (newCloudDisk == null) {
                    CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.UPLOADFAILED);
                }
                String diskId = newCloudDisk.getDiskId();
                // 课外作业题目附件
                logger.info("[课外作业题目附件处理:]");
                List<String> diskIds = new ArrayList<>();
                diskIds.add(diskId);
                result = ctBankItemFilesMapper.batchInsert(ctBankItem.getSchoolId(), CloudTeachBankEnum.FILE_TYPE_TEACHERQUE.getCode(), ctBankItem.getItemId(), diskIds);
                if (result < 1) {
                    CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_CTEATE_FAILED);
                }
            }
        }

        return ctBankItem.getItemId();
    }


    /**
     * 获取题目详情
     *
     * @param schoolId 学校ID【用于分片查询】
     * @param itemId   题目ID
     * @return bankItemWithAttachmentDTO   带附件的题目
     * @throws CloudteachException
     */
    @Override
    public BankItemWithAttachmentDTO getItemById(String schoolId, String itemId, CloudTeachBankEnum bankEnum) throws CloudteachException {
        logger.info("获取题目详情：schoolId=" + schoolId + ";itemId=" + itemId + ";题目类型：" + bankEnum.getCode() + "--" + bankEnum.getDescription());

        BankItemWithAttachmentDTO bankItemWithAttachmentDTO;
        List<CloudDiskReferDTO> diskReferDTOList = new ArrayList<>();
        CtBankItem ctBankItem;

        //获取题目
        ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, itemId);
        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND);
        }

        //获取附件
        List<CtBankItemFiles> itemFiles = ctBankItemFilesMapper.selectAttachment(schoolId, itemId, bankEnum.getCode());

        if (CollectionUtils.isNotEmpty(itemFiles)) {

            List<String> diskIds = new ArrayList<>();
            for (CtBankItemFiles itemFile : itemFiles) {
                diskIds.add(itemFile.getDiskId());
            }

            List<CloudDiskReferDTO> diskReferDTOs = cloudDiskService.queryFilesByIds(schoolId, diskIds);

            //绑定referId
            Map<String, CloudDiskReferDTO> diskMap = new HashMap<>();
            for (CloudDiskReferDTO diskReferDTO : diskReferDTOs) {
                diskMap.put(diskReferDTO.getDiskId(), diskReferDTO);
            }

            if (CollectionUtils.isNotEmpty(diskReferDTOs)) {
                Iterator<CtBankItemFiles> it = itemFiles.iterator();
                while (it.hasNext()) {
                    CtBankItemFiles itemFile = it.next();
                    CloudDiskReferDTO diskReferDTO = diskMap.get(itemFile.getDiskId());
                    if (diskReferDTO != null) {
                        diskReferDTO.setReferId(itemFile.getRelationId());
                        diskReferDTOList.add(diskReferDTO);
                    } else {
                        logger.info("获取题目附件详情: 由于云盘数据问题  移除一条附件信息:" + itemFile.getRelationId() + ":该附件在运盘中找不到关联的对象！");
                        it.remove();
                    }
                }
            }


        }

        //包装带附件的题目
        bankItemWithAttachmentDTO = toBankItemWithAttachmentDTO(ctBankItem, diskReferDTOList);


        return bankItemWithAttachmentDTO;
    }


    /**
     * 编辑题目
     *
     * @param bankItemEditDTO 题目编辑DTO
     * @param schoolId        学校ID
     * @param userId          用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    @Override
    public void editItem(BankItemEditDTO bankItemEditDTO, String schoolId, String userId) throws CloudteachException {
        logger.info("编辑题目：schoolId=" + schoolId + ";userId=" + userId + ";题目DTO:" + bankItemEditDTO);

        //更新主表
        CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, bankItemEditDTO.getItemId());

        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_EXIST);
        }

        if (!(ctBankItem.getCreator() != null && ctBankItem.getCreator().equals(userId))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_YOURS);
        }

        /** 如果提交方式为空，或非0非1  抛异常*/

        if (bankItemEditDTO.getSubImage() == null || bankItemEditDTO.getSubImage() < 0 || bankItemEditDTO.getSubImage() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (bankItemEditDTO.getSubTape() == null || bankItemEditDTO.getSubTape() < 0 || bankItemEditDTO.getSubTape() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (bankItemEditDTO.getSubVideo() == null || bankItemEditDTO.getSubVideo() < 0 || bankItemEditDTO.getSubVideo() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (bankItemEditDTO.getSubOther() == null || bankItemEditDTO.getSubOther() < 0 || bankItemEditDTO.getSubOther() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        ctBankItem.setSubImage(bankItemEditDTO.getSubImage());
        ctBankItem.setSubTape(bankItemEditDTO.getSubTape());
        ctBankItem.setSubVideo(bankItemEditDTO.getSubVideo());
        ctBankItem.setSubOther(bankItemEditDTO.getSubOther());

        /** 内容如果有null，为“” */
        ctBankItem.setContext(bankItemEditDTO.getContext() != null ? bankItemEditDTO.getContext() : "");

        /** 语音内容如果null 为“” */
        ctBankItem.setVoiceContext(bankItemEditDTO.getVoiceContext() != null ? bankItemEditDTO.getVoiceContext() : "");

        ctBankItem.setUpdateTime(new Date());
        ctBankItemMapper.updateByPrimaryKey(ctBankItem);

        //删除原来的附件
        ctBankItemFilesMapper.deleteByItemId(schoolId, ctBankItem.getItemId());

        //插入附件
        if (CollectionUtils.isNotEmpty(bankItemEditDTO.getDiskIds())) {
            ctBankItemFilesMapper.batchInsert(ctBankItem.getSchoolId(), CloudTeachBankEnum.FILE_TYPE_TEACHERQUE.getCode(), ctBankItem.getItemId(), bankItemEditDTO.getDiskIds());
        }

        // /** 20151016 课外作业上传本地附件处理 **/
        List<BankItemAppFilesDTO> filesList = bankItemEditDTO.getFilesList();
        if (CollectionUtils.isNotEmpty(filesList)) {
            logger.info("[课外作业上传本地附件处理:]");
            for (BankItemAppFilesDTO filesDTO : filesList) {
                logger.info("[获取文件filesDTO:{}]", filesDTO);
                // 文件存入云盘
                logger.info("[上传文件夹存入云盘]");
                CloudDiskDTO cloudDiskDTO = new CloudDiskDTO();
                cloudDiskDTO.setExtension(filesDTO.getExtension());//扩展名
                cloudDiskDTO.setFileUri(filesDTO.getUrl());//文件uri
                cloudDiskDTO.setSize(filesDTO.getSize());
                cloudDiskDTO.setName(filesDTO.getFileName());
                cloudDiskDTO.setUnitId("");
                cloudDiskDTO.setFileType(999);
                cloudDiskDTO.setUserId(userId);
                cloudDiskDTO.setSchoolId(schoolId);
                CloudDiskDTO newCloudDisk = cloudDiskService.uploadFile(cloudDiskDTO);
                if (newCloudDisk == null) {
                    CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.UPLOADFAILED);
                }
                String diskId = newCloudDisk.getDiskId();
                // 课外作业题目附件
                logger.info("[课外作业题目附件处理:]");
                List<String> diskIds = new ArrayList<>();
                diskIds.add(diskId);
                int result = ctBankItemFilesMapper.batchInsert(ctBankItem.getSchoolId(), CloudTeachBankEnum.FILE_TYPE_TEACHERQUE.getCode(), ctBankItem.getItemId(), diskIds);
                if (result < 1) {
                    CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_CTEATE_FAILED);
                }
            }
        }
    }

    /**
     * 删除题目
     *
     * @param schoolId 学校ID
     * @param itemId   题目ID
     * @param userId   用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    @Override
    public void removeItem(String schoolId, String itemId, String userId) throws CloudteachException {

        logger.info("删除题目：schoolId=" + schoolId + ";itemId=" + itemId + ";userId=" + userId);

        CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, itemId);

        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND);
        }

        if (!(ctBankItem.getCreator() != null && ctBankItem.getCreator().equals(userId))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_YOURS);
        }

        ctBankItemMapper.removeItem(schoolId, itemId);

        if (ctBankItem.getIsCollect() != null && ctBankItem.getIsCollect() == 1) {
            //更新分享计数
            CtBankItemShareStatistics ctBankItemShareStatistics = new CtBankItemShareStatistics();
            ctBankItemShareStatistics.setShareId(ctBankItem.getPid());
            ctBankItemShareStatistics.setCollectTimes(-1);
            ctBankItemShareStatisticsMapper.updateCount(ctBankItemShareStatistics);

            /**查询收藏记录表*/
            CtBankItemShareCollect itemShareCollect = ctBankItemShareCollectMapper.selectByPrimaryKey(ctBankItem.getPid(), userId);

            if (null != itemShareCollect && itemShareCollect.getCollectStatus() == 1) {
                //有收藏记录 update收藏记录表状态，改为0--未收藏
                itemShareCollect.setCollectStatus(0);
                ctBankItemShareCollectMapper.updateByPrimaryKey(itemShareCollect);
            } else {
                logger.info("取消收藏题目时收藏记录表不存在该数据：shareId=" + ctBankItem.getPid() + ";itemId=" + itemId + ";userId=" + userId);
            }
        }
    }

    /**
     * 置顶题目
     *
     * @param schoolId 学校ID
     * @param itemId   题目ID
     * @param userId   用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    @Override
    public void stickyItem(String schoolId, String itemId, String userId) throws CloudteachException {

        logger.info("置顶题目：schoolId=" + schoolId + ";itemId=" + itemId + ";userId=" + userId);

        CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, itemId);

        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND);
        }

        if (!(ctBankItem.getCreator() != null && ctBankItem.getCreator().equals(userId))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_YOURS);
        }

        ctBankItemMapper.stickyItem(schoolId, itemId);
    }

    /**
     * 取消置顶我的题目
     *
     * @param schoolId 学校Id
     * @param itemId   题目ID
     * @param userId   用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    @Override
    public void cancelStickyItem(String schoolId, String itemId, String userId) throws CloudteachException {
        logger.info("取消置顶题目：schoolId=" + schoolId + ";itemId=" + itemId + ";userId=" + userId);

        CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, itemId);

        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND);
        }

        if (!(ctBankItem.getCreator() != null && ctBankItem.getCreator().equals(userId))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_YOURS);
        }

        ctBankItemMapper.cancelStickyItem(schoolId, itemId);
    }

    /**
     * 分享题目
     *
     * @param userShareInfoDTO 用户分享信息DTO
     * @throws CloudteachException
     */
    @Override
    public void shareItem(UserShareInfoDTO userShareInfoDTO) throws CloudteachException {

        logger.info("分享题目：" + userShareInfoDTO.toString());

        CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(userShareInfoDTO.getSchoolId(), userShareInfoDTO.getItemId());

        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND);
        }

        if (!(ctBankItem.getUserId() != null && ctBankItem.getUserId().equals(userShareInfoDTO.getUserId()))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_YOURS);
        }

        //是否可分享
        if (ctBankItem.getShareStatus() != null && (ctBankItem.getShareStatus() == 1 || ctBankItem.getShareStatus() == 2)) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_ALREADY_SHARED);
        }

        CtBankItemShare ctBankItemShare = toCtBankItemShare(ctBankItem);
        //初始化数据
        ctBankItemShare.setShareId(UUID.randomUUID().toString().replace("-", "").trim());
        ctBankItemShare.setCreator(userShareInfoDTO.getUserId());
        ctBankItemShare.setCreatorName(userShareInfoDTO.getUserName());
        ctBankItemShare.setSchoolId(userShareInfoDTO.getSchoolId());
        ctBankItemShare.setSchoolName(userShareInfoDTO.getSchoolName());
        ctBankItemShare.setAreaId(userShareInfoDTO.getAreaId());
        ctBankItemShare.setAreaName(userShareInfoDTO.getAreaName());
        ctBankItemShare.setShareType(userShareInfoDTO.getShareType());
        ctBankItemShare.setStatus(1);
        ctBankItemShare.setShareTime(new Date());
        ctBankItemShare.setOpinion("");


        /** 新增一条记录到大家分享的题目中（包括copy题目附件）**/
        ctBankItemShareMapper.insert(ctBankItemShare);

        //统计表
        CtBankItemShareStatistics ctBankItemShareStatistics = new CtBankItemShareStatistics();
        ctBankItemShareStatistics.setShareId(ctBankItemShare.getShareId());
        ctBankItemShareStatistics.setCollectTimes(0);
        ctBankItemShareStatistics.setPraiseTimes(0);
        ctBankItemShareStatistics.setVewingTimes(0);
        ctBankItemShareStatisticsMapper.insert(ctBankItemShareStatistics);

        //copy 题目附件
        List<CtBankItemFiles> itemFilesList = ctBankItemFilesMapper.selectAttachment(userShareInfoDTO.getSchoolId(),
                userShareInfoDTO.getItemId(), CloudTeachBankEnum.FILE_TYPE_TEACHERQUE.getCode());
        if (CollectionUtils.isNotEmpty(itemFilesList)) {

            List<String> diskIds = new ArrayList<>();
            for (CtBankItemFiles ctBankItemFiles : itemFilesList) {
                diskIds.add(ctBankItemFiles.getDiskId());
            }
            ctBankItemShareFilesMapper.batchInsert(ctBankItemShare.getShareId(), diskIds);
        }


        /** 预习项表中分享状态改为1--审核中*/
        ctBankItemMapper.shareItem(userShareInfoDTO.getSchoolId(), userShareInfoDTO.getItemId(), ctBankItemShare.getShareTime());

    }

    /**
     * 取消收藏题目
     *
     * @param schoolId 学校ID
     * @param userId   用户ID，必须为题目所属用户
     * @param itemId   题目ID
     * @throws CloudteachException
     */
    @Override
    public void cancelCollectItem(String schoolId, String userId, String itemId) throws CloudteachException {
        logger.info("取消收藏题目：schoolId=" + schoolId + ";itemId=" + itemId + ";userId=" + userId);

        //取消收藏item 实际为删除
        CtBankItem ctBankItem = ctBankItemMapper.selectByPrimaryKey(schoolId, itemId);

        if (ctBankItem == null) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND);
        }

        if (!(ctBankItem.getCreator() != null && ctBankItem.getCreator().equals(userId))) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.BANK_ITEM_NOT_YOURS);
        }

        ctBankItemMapper.removeItem(schoolId, itemId);

        //更新分享计数
        CtBankItemShareStatistics ctBankItemShareStatistics = new CtBankItemShareStatistics();
        ctBankItemShareStatistics.setShareId(ctBankItem.getPid());
        ctBankItemShareStatistics.setCollectTimes(-1);
        ctBankItemShareStatisticsMapper.updateCount(ctBankItemShareStatistics);

        /**查询收藏记录表*/
        CtBankItemShareCollect itemShareCollect = ctBankItemShareCollectMapper.selectByPrimaryKey(ctBankItem.getPid(), userId);

        if (null != itemShareCollect && itemShareCollect.getCollectStatus() == 1) {
            //有收藏记录 update收藏记录表状态，改为0--未收藏
            itemShareCollect.setCollectStatus(0);
            ctBankItemShareCollectMapper.updateByPrimaryKey(itemShareCollect);
        } else {
            logger.info("取消收藏题目时收藏记录表不存在该数据：shareId=" + ctBankItem.getPid() + ";itemId=" + itemId + ";userId=" + userId);
        }
    }


    /**
     * 查询用户在任务时间内分享成功的题目数
     * 用于积分接口
     * 如果开始时间大于结束时间，将被自动交换
     *
     * @param schoolId 学校ID【非空】
     * @param userId   用户ID【非空】
     * @param itemType 题目类型【为空或0表示所有题目类型】
     * @param start    开始时间【非空】
     * @param end      结束时间【非空】
     * @return Long 分享次数
     * @throws CloudteachException
     */
    @Override
    public Long getBankItemSharedAmount(String schoolId, String userId, Integer itemType, Date start, Date end) throws CloudteachException {
        logger.info("查询用户在任务时间内分享成功的题目数，参数为：schoolId = {} ，userId = {} , itemType = {} , start = {} , end = {} ", schoolId, userId, itemType, start, end);

        if (schoolId == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , itemType = {} , start = {} , end = {} ", schoolId, userId, itemType, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：schoolId为空，校验未通过");
        }

        if (userId == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , itemType = {} , start = {} , end = {} ", schoolId, userId, itemType, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：userId为空，校验未通过");
        }

        if (start == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , itemType = {} , start = {} , end = {} ", schoolId, userId, itemType, start, end);
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR, "参数非法：start为空，校验未通过");
        }

        if (end == null) {
            logger.error("参数错误，参数为：schoolId = {} ，userId = {} , itemType = {} , start = {} , end = {} ", schoolId, userId, itemType, start, end);
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

        Long times = ctBankItemMapper.selectSharedCount(schoolId, userId, itemType, start, end);

        if (times == null) {
            times = 0L;
        }

        logger.info("用户 {} 在 {} 至 {} 内 分享成功的 {}类型 题目数为 {} ", userId, start, itemType, end, times);

        return times;
    }


//  <===============================私有方法===============================>

    /**
     * 题目domain对象转DTO
     *
     * @param ctBankItem 题目domain对象
     * @return BankItemDTO 题目DTO
     */
    private BankItemDTO toBankItemDTO(CtBankItem ctBankItem) {
        if (ctBankItem == null) {
            return null;
        }
        BankItemDTO bankItemDTO = new BankItemDTO();
        bankItemDTO.setItemId(ctBankItem.getItemId());
        bankItemDTO.setCreator(ctBankItem.getCreator());
        bankItemDTO.setUnitId(ctBankItem.getUnitId());
        bankItemDTO.setIsCollect(ctBankItem.getIsCollect());
        bankItemDTO.setUserId(ctBankItem.getUserId());
        bankItemDTO.setPid(ctBankItem.getPid());
        bankItemDTO.setItemType(ctBankItem.getItemType());
        bankItemDTO.setSubImage(ctBankItem.getSubImage());
        bankItemDTO.setSubTape(ctBankItem.getSubTape());
        bankItemDTO.setSubVideo(ctBankItem.getSubVideo());
        bankItemDTO.setSubOther(ctBankItem.getSubOther());
        bankItemDTO.setContext(ctBankItem.getContext());
        bankItemDTO.setVoiceContext(ctBankItem.getVoiceContext());
        bankItemDTO.setStickStatus(ctBankItem.getStickStatus());
        bankItemDTO.setCreateTime(ctBankItem.getCreateTime());
        bankItemDTO.setUpdateTime(ctBankItem.getUpdateTime());
        bankItemDTO.setShareStatus(ctBankItem.getShareStatus());
        bankItemDTO.setSchoolId(ctBankItem.getSchoolId());
        bankItemDTO.setStatus(ctBankItem.getStatus());
        if (ctBankItem instanceof BankItemView) {
            bankItemDTO.setFilesAmount(((BankItemView) ctBankItem).getFilesAmount());
            bankItemDTO.setReleases(((BankItemView) ctBankItem).getReleases());
        }
        return bankItemDTO;
    }

    /**
     * 包装带附件的题目DTO
     *
     * @param ctBankItem       题目domain对象
     * @param diskReferDTOList 附件列表
     * @return BankItemWithAttachmentDTO 带附件的题目DTO
     */
    public BankItemWithAttachmentDTO toBankItemWithAttachmentDTO(CtBankItem ctBankItem, List<CloudDiskReferDTO> diskReferDTOList) {
        if (ctBankItem == null) {
            return null;
        }
        BankItemWithAttachmentDTO bankItemWithAttachmentDTO = new BankItemWithAttachmentDTO();
        bankItemWithAttachmentDTO.setItemId(ctBankItem.getItemId());
        bankItemWithAttachmentDTO.setCreator(ctBankItem.getCreator());
        bankItemWithAttachmentDTO.setUnitId(ctBankItem.getUnitId());
        bankItemWithAttachmentDTO.setIsCollect(ctBankItem.getIsCollect());
        bankItemWithAttachmentDTO.setUserId(ctBankItem.getUserId());
        bankItemWithAttachmentDTO.setPid(ctBankItem.getPid());
        bankItemWithAttachmentDTO.setItemType(ctBankItem.getItemType());
        bankItemWithAttachmentDTO.setSubImage(ctBankItem.getSubImage());
        bankItemWithAttachmentDTO.setSubTape(ctBankItem.getSubTape());
        bankItemWithAttachmentDTO.setSubVideo(ctBankItem.getSubVideo());
        bankItemWithAttachmentDTO.setSubOther(ctBankItem.getSubOther());
        bankItemWithAttachmentDTO.setContext(ctBankItem.getContext());
        bankItemWithAttachmentDTO.setStickStatus(ctBankItem.getStickStatus());
        bankItemWithAttachmentDTO.setCreateTime(ctBankItem.getCreateTime());
        bankItemWithAttachmentDTO.setUpdateTime(ctBankItem.getUpdateTime());
        bankItemWithAttachmentDTO.setShareStatus(ctBankItem.getShareStatus());
        bankItemWithAttachmentDTO.setSchoolId(ctBankItem.getSchoolId());
        bankItemWithAttachmentDTO.setStatus(ctBankItem.getStatus());
        bankItemWithAttachmentDTO.setAttachmentList(diskReferDTOList);
        bankItemWithAttachmentDTO.setVoiceContext(ctBankItem.getVoiceContext());
        return bankItemWithAttachmentDTO;
    }


    /**
     * domain对象列表转DTO
     *
     * @param datalist 题目view对象列表【包含附件总数和发布次数】
     * @return List<BankItemDTO> 题目DTO列表
     */
    private List<BankItemDTO> entityListToDtoList(List<BankItemView> datalist) {

        List<BankItemDTO> resList = new ArrayList<>();

        for (CtBankItem bankItem : datalist) {
            resList.add(toBankItemDTO(bankItem));
        }
        return resList;
    }

    /**
     * 题目DTO转DOAMIN
     *
     * @param itemCreateDTO 题目创建DTO
     * @return CtBankItem 题目domain对象
     */
    private CtBankItem toCtBankItem(BankItemCreateDTO itemCreateDTO) {
        if (itemCreateDTO == null) {
            return null;
        }
        CtBankItem ctBankItem = new CtBankItem();
        ctBankItem.setCreator(itemCreateDTO.getCreator());
        ctBankItem.setSchoolId(itemCreateDTO.getSchoolId());
        ctBankItem.setUnitId(itemCreateDTO.getUnitId());
        ctBankItem.setItemType(itemCreateDTO.getItemType());

        /** 如果提交方式为空，或非0非1  抛异常*/

        if (itemCreateDTO.getSubImage() == null || itemCreateDTO.getSubImage() < 0 || itemCreateDTO.getSubImage() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (itemCreateDTO.getSubTape() == null || itemCreateDTO.getSubTape() < 0 || itemCreateDTO.getSubTape() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (itemCreateDTO.getSubVideo() == null || itemCreateDTO.getSubVideo() < 0 || itemCreateDTO.getSubVideo() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        if (itemCreateDTO.getSubOther() == null || itemCreateDTO.getSubOther() < 0 || itemCreateDTO.getSubOther() > 1) {
            CloudTeachErrorEnum.throwException(CloudTeachErrorEnum.PARAMERROR);
        }

        ctBankItem.setSubImage(itemCreateDTO.getSubImage());
        ctBankItem.setSubTape(itemCreateDTO.getSubTape());
        ctBankItem.setSubVideo(itemCreateDTO.getSubVideo());
        ctBankItem.setSubOther(itemCreateDTO.getSubOther());

        /** 如果题目描述为空 设为“” */
        ctBankItem.setContext(itemCreateDTO.getContext() == null ? "" : itemCreateDTO.getContext());

        /** 如果语音内容为空 设为“” */
        ctBankItem.setVoiceContext(itemCreateDTO.getVoiceContext() == null ? "" : itemCreateDTO.getVoiceContext());
        return ctBankItem;
    }

    /**
     * 题目信息转分享信息
     *
     * @param ctBankItem 题目domain对象
     * @return CtBankItemShare 题目分享domain对象
     */
    private CtBankItemShare toCtBankItemShare(CtBankItem ctBankItem) {
        if (ctBankItem == null) {
            return null;
        }
        CtBankItemShare ctBankItemShare = new CtBankItemShare();
        ctBankItemShare.setItemId(ctBankItem.getItemId());
        ctBankItemShare.setCreator(ctBankItem.getCreator());
        ctBankItemShare.setUnitId(ctBankItem.getUnitId());
        ctBankItemShare.setItemType(ctBankItem.getItemType());
        ctBankItemShare.setSubImage(ctBankItem.getSubImage());
        ctBankItemShare.setSubTape(ctBankItem.getSubTape());
        ctBankItemShare.setSubVideo(ctBankItem.getSubVideo());
        ctBankItemShare.setSubOther(ctBankItem.getSubOther());
        ctBankItemShare.setContext(ctBankItem.getContext());
        ctBankItemShare.setStickStatus(ctBankItem.getStickStatus());
        ctBankItemShare.setSchoolId(ctBankItem.getSchoolId());
        ctBankItemShare.setStatus(ctBankItem.getStatus());
        return ctBankItemShare;
    }

}
