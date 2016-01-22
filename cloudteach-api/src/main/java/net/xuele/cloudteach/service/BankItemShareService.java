package net.xuele.cloudteach.service;

import net.xuele.cloudteach.dto.BankItemShareDTO;
import net.xuele.cloudteach.dto.BankItemSharePraiseViewDTO;
import net.xuele.cloudteach.dto.BankItemShareDetailDTO;
import net.xuele.cloudteach.dto.page.BankItemSharePageRequest;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.PageResponse;

import java.util.List;

/**
 * BankItemShareService
 * 分享的题库服务
 *
 * @author panglx
 * @date on 2015/7/24 0024.
 */
public interface BankItemShareService {

    /**
     * 分页获取大家分享的题目
     *
     * @param request
     * @return
     * @throws CloudteachException
     */
    PageResponse<BankItemShareDetailDTO> queryItemShareResource(BankItemSharePageRequest request) throws CloudteachException;

    /**
     * 查看题目的详情
     *
     * @param shareId
     * @return
     * @throws CloudteachException
     */
    BankItemShareDetailDTO getDetailItem(String shareId) throws CloudteachException;

    /**
     * 获取点赞用户信息
     *
     * @param shareId
     * @return
     * @throws CloudteachException
     */
    List<BankItemSharePraiseViewDTO> getDetailPraiseUser(String shareId) throws CloudteachException;

    /**
     * 点赞
     *
     * @param shareId
     * @param creator
     * @return
     * @throws CloudteachException 有可能抛出异常 【已经点赞不能点赞】，【自己分享的不能点赞】
     */
    int praise(String shareId, String creator) throws CloudteachException;

    /**
     * 取消赞
     *
     * @param shareId
     * @param creator
     * @return
     * @throws CloudteachException 有可能抛出异常 【未点赞不能取消点赞】
     */
    int cancelPraise(String shareId, String creator) throws CloudteachException;

    /**
     * 收藏
     *
     * @param shareId
     * @param creator
     * @return
     * @throws CloudteachException 可能抛出的异常 【不能收藏自己的资源】【已经收藏】
     */
    String collectItem(String shareId, String creator, String schoolId) throws CloudteachException;

    /**
     * 取消收藏
     *
     * @param shareId
     * @param creator
     * @return
     * @throws CloudteachException 可能抛出的异常 【未收藏】
     */
    String cancelCollect(String shareId, String creator, String schoolId) throws CloudteachException;

    /**
     * 取消分享
     *
     * @param shareId
     * @param creator
     * @param schoolId
     * @return
     */
    String cancelShare(String shareId, String creator, String schoolId);

    /**
     * @param itemId 预习项号
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 分享我的预习项审核成功对应大家分享中的记录状态修改为已分享，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int shareGuidanceItemSuc(String itemId) throws CloudteachException;

    /**
     * @param itemId 预习项号
     * @return int
     * @throws net.xuele.common.exceptions.CloudteachException 分享我的预习项审核失败对应大家分享中的记录状态修改为审核失败，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    int shareGuidanceItemFail(String itemId) throws CloudteachException;

    /**
     * @param shareId 分享预习项号
     * @return GuidanceItemShareDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据ID获取大家分享的预习项信息，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    BankItemShareDTO queryGuidanceItemShare(String shareId) throws CloudteachException;

    /**
     * @param itemId 预习项号
     * @return GuidanceItemShareDTO
     * @throws net.xuele.common.exceptions.CloudteachException 根据预习项号获取处于审核中或已分享的记录，
     *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
     */
    BankItemShareDTO querySharedRecordByItemid(String itemId) throws CloudteachException;


}
