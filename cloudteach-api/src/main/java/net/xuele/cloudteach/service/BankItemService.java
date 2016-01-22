package net.xuele.cloudteach.service;

import net.xuele.cloudteach.constant.CloudTeachBankEnum;
import net.xuele.cloudteach.dto.*;
import net.xuele.common.exceptions.CloudteachException;

import java.util.Date;
import java.util.List;

/**
 * BankItemService
 * 题库服务
 * 用于处理预习、电子作业、口语作业的题库相关的业务
 *
 * @author sunxh
 * @date 15/7/25
 */
public interface BankItemService {


    /**
     * 我的题库列表
     *
     * @param userId   用户ID
     * @param schoolId 学校ID【用于分片查询】
     * @param unitId   课程ID
     * @param itemType 题目类型：1预习 4电子作业 7口语作业
     * @return bankItemDTOList  题目DTO列表
     */
    List<BankItemDTO> queryItemList(String userId, String schoolId, String unitId, Integer itemType);

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
    List<BankItemDTO> queryItemList(String userId, String schoolId, String unitId, Integer itemType, Integer isCollect);

    /**
     * 创建题目
     *
     * @param itemCreateDTO 题目创建DTO
     * @throws CloudteachException
     */
    String createBankItem(BankItemCreateDTO itemCreateDTO) throws CloudteachException;

    /**
     * 获取题目详情
     *
     * @param schoolId 学校ID【用于分片查询】
     * @param itemId   题目ID
     * @param bankEnum 题目类型：1预习 4电子作业 7口语作业
     * @return bankItemWithAttachmentDTO   带附件的题目
     * @throws CloudteachException
     */
    BankItemWithAttachmentDTO getItemById(String schoolId, String itemId, CloudTeachBankEnum bankEnum) throws CloudteachException;

    /**
     * 编辑题目
     *
     * @param bankItemEditDTO 题目编辑DTO
     * @param schoolId        学校ID
     * @param userId          用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    void editItem(BankItemEditDTO bankItemEditDTO, String schoolId, String userId) throws CloudteachException;

    /**
     * 删除题目
     *
     * @param schoolId 学校ID
     * @param itemId   题目ID
     * @param userID   用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    void removeItem(String schoolId, String itemId, String userID) throws CloudteachException;

    /**
     * 置顶题目
     *
     * @param schoolId 学校ID
     * @param itemId   题目ID
     * @param userId   用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    void stickyItem(String schoolId, String itemId, String userId) throws CloudteachException;

    /**
     * 取消置顶我的题目
     *
     * @param schoolId 学校Id
     * @param itemId   题目ID
     * @param userId   用户ID，必须为题目所属用户
     * @throws CloudteachException
     */
    void cancelStickyItem(String schoolId, String itemId, String userId) throws CloudteachException;

    /**
     * 分享题目
     *
     * @param userShareInfoDTO 用户分享信息DTO
     */
    void shareItem(UserShareInfoDTO userShareInfoDTO) throws CloudteachException;

    /**
     * 取消收藏题目
     *
     * @param schoolId 学校ID
     * @param userId   用户ID，必须为题目所属用户
     * @param itemId   题目ID
     * @throws CloudteachException
     */
    void cancelCollectItem(String schoolId, String userId, String itemId) throws CloudteachException;

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
    Long getBankItemSharedAmount(String schoolId, String userId, Integer itemType, Date start, Date end) throws CloudteachException;

}
