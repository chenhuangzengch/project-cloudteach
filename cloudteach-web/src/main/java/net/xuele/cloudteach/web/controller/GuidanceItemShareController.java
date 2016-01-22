package net.xuele.cloudteach.web.controller;

import net.xuele.cloudteach.dto.BankItemShareDetailDTO;
import net.xuele.cloudteach.dto.BankItemSharePraiseViewDTO;
import net.xuele.cloudteach.dto.page.BankItemSharePageRequest;
import net.xuele.cloudteach.service.BankItemShareService;
import net.xuele.cloudteach.web.common.GetSessionContentUtil;
import net.xuele.cloudteach.web.wrapper.BankItemSharePraiseWrapper;
import net.xuele.cloudteach.web.wrapper.BankItemShareWrapper;
import net.xuele.cloudteach.web.wrapper.ShareCollectWrapper;
import net.xuele.cloudteach.web.wrapper.SharePraiseWrapper;
import net.xuele.common.ajax.AjaxResponse;
import net.xuele.common.page.PageResponse;
import net.xuele.member.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * PreviewQueShareController
 * 教师预习题目
 * @author panglx
 * @date on 2015/7/25 0025.
 */

@Controller
@RequestMapping(value = "guidance/itemShare")
public class GuidanceItemShareController {

	@Autowired
	private BankItemShareService itemShareService;

	@Autowired
	private UserService userService;

	private static Logger logger = LoggerFactory.getLogger(GuidanceItemShareController.class);


	/**
	 * @param map 【请求参数】
	 * @return 大家分享的预习题
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request,ModelMap map) {

		/** 二级导航菜单定位 */
		request.getSession().setAttribute("lastUrl", "/guidance/item/index");

		map.put("schoolName", GetSessionContentUtil.getInstance().currentSchoolName());
		map.put("userId", GetSessionContentUtil.getInstance().currentUserID());
		map.put("schoolId", GetSessionContentUtil.getInstance().currentSchoolID());
		return "/guidance/item/share";
	}


	@RequestMapping(value = "pageList")
	@ResponseBody
	public AjaxResponse<PageResponse<BankItemShareWrapper>> queryGuidanceItemShareByPage(BankItemSharePageRequest request) {

		AjaxResponse<PageResponse<BankItemShareWrapper>> ajaxResponse = new AjaxResponse<>();

		/**从session中获取用户id，学校id，地区id*/
		request.setUserId(GetSessionContentUtil.getInstance().currentUserID());
		request.setSchoolId(GetSessionContentUtil.getInstance().currentSchoolID());
		request.setAreaId(GetSessionContentUtil.getInstance().currentAreaID());

		/******************************* 测试数据*********************************************************************/
		/*request.setUserId("143647367136");
		request.setSchoolId("001");
		request.setAreaId("001");
		request.setUnitId("0101");
		request.setSeltype(0);*/

		/************************************************************************************************************/
		request.setItemType(1);//题目类型--预习题目
		//调用服务获取大家分享的预习项
		PageResponse<BankItemShareDetailDTO> sharePage = itemShareService.queryItemShareResource(request);
		List<BankItemShareDetailDTO> viewDTOList = sharePage.getRows();
		/**DTO对象转为Wrapper*/
		List<BankItemShareWrapper> wrappers = new ArrayList<>();//返回前端Wapper
		BankItemShareWrapper wrapper;
		for (BankItemShareDetailDTO viewDTO:viewDTOList){
			wrapper = new BankItemShareWrapper();
			wrapper.setBankItemShareDTO(viewDTO.getBankItemShareViewDTO());
			wrapper.setFilesDTOs(viewDTO.getFilesDTOs());
			wrappers.add(wrapper);
		}
		/**返回前端PageResponse*/
		PageResponse<BankItemShareWrapper> resultPage = new PageResponse<>();
		BeanUtils.copyProperties(sharePage, resultPage);
		resultPage.setRows(wrappers);
		ajaxResponse.setWrapper(resultPage);
		ajaxResponse.setStatus("1");//获取数据成功
		return ajaxResponse;

	}

	/**
	 * 获取点赞用户名
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "praiseUsers")
	@ResponseBody
	public AjaxResponse<List<BankItemSharePraiseWrapper>> getPraiseDetailUser(String shareId){
		AjaxResponse<List<BankItemSharePraiseWrapper>> ajaxResponse = new AjaxResponse<>();
		//查询点赞用户
		List<BankItemSharePraiseViewDTO> itemSharePraiseDTOs = itemShareService.getDetailPraiseUser(shareId);
		List<BankItemSharePraiseWrapper> sharePraises = new ArrayList<>();
		for(BankItemSharePraiseViewDTO praiseViewDTO:itemSharePraiseDTOs){
			sharePraises.add(new BankItemSharePraiseWrapper(praiseViewDTO));
		}
		ajaxResponse.setStatus("1");
		ajaxResponse.setWrapper(sharePraises);
		return ajaxResponse;
	}

	/**
	 * 点赞题目
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "praise")
	@ResponseBody
	public AjaxResponse<SharePraiseWrapper> praiseGuidanceItemShare(String shareId) {
		//从session中获取用户id
		String creator = GetSessionContentUtil.getInstance().currentUserID();
		//String creator = "143647367136";//测试
		AjaxResponse<SharePraiseWrapper> praiseGuidanceItemShareWrapperAjaxResponse = new AjaxResponse<>();
		//调用服务点赞预习项
		itemShareService.praise(shareId,creator );
		/**返回Wrapper*/
		SharePraiseWrapper sharePraiseWrapper = new SharePraiseWrapper();
		sharePraiseWrapper.setShareId(shareId);
		sharePraiseWrapper.setPraiseState(1);//点赞状态1--已赞
		praiseGuidanceItemShareWrapperAjaxResponse.setWrapper(sharePraiseWrapper);
		praiseGuidanceItemShareWrapperAjaxResponse.setStatus("1");//点赞成功
		return praiseGuidanceItemShareWrapperAjaxResponse;
	}

	/**
	 * 取消点赞题目
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "unpraise")
	@ResponseBody
	public AjaxResponse<SharePraiseWrapper> unpraiseGuidanceItemShare(String shareId) {
		//从session中获取用户id
		String creator = GetSessionContentUtil.getInstance().currentUserID();
		//String creator = "143647367136";//测试
		AjaxResponse<SharePraiseWrapper> unpraiseGuidanceItemShareWrapperAjaxResponse = new AjaxResponse<>();
		//调用服务取消点赞预习项
		itemShareService.cancelPraise(shareId, creator);
		/**返回Wrapper*/
		SharePraiseWrapper sharePraiseWrapper = new SharePraiseWrapper();
		sharePraiseWrapper.setShareId(shareId);
		sharePraiseWrapper.setPraiseState(0);//点赞状态1--未赞
		unpraiseGuidanceItemShareWrapperAjaxResponse.setWrapper(sharePraiseWrapper);
		unpraiseGuidanceItemShareWrapperAjaxResponse.setStatus("1");//取消点赞成功
		return unpraiseGuidanceItemShareWrapperAjaxResponse;
	}

	/**
	 * @param shareId 【请求参数】
	 * @return 收藏题目
	 */
	@RequestMapping(value = "collect")
	@ResponseBody
	public AjaxResponse<ShareCollectWrapper> collectGuidanceItem(String shareId) {
		AjaxResponse<ShareCollectWrapper> ajaxResponse = new AjaxResponse<>();
		//从session中获取用户id及学校id
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
		//String userId = "143647367136";//测试
		//String schoolId = "001";
		//调用服务收藏题目
		String itemId = itemShareService.collectItem(shareId,userId,schoolId);
		ShareCollectWrapper collectWrapper = new ShareCollectWrapper();
		collectWrapper.setItemId(itemId);//题目id
		collectWrapper.setPid(shareId);//收藏来源id（分享id）
		collectWrapper.setCollectState(1);//收藏状态 1--已收藏

		ajaxResponse.setWrapper(collectWrapper);
		ajaxResponse.setStatus("1");//收藏成功
		return ajaxResponse;
	}

	/**
	 * @param shareId 【请求参数】
	 * @return 取消收藏题目
	 */
	@RequestMapping(value = "uncollect")
	@ResponseBody
	public AjaxResponse<ShareCollectWrapper> uncollectGuidanceItem(String shareId) {
		AjaxResponse<ShareCollectWrapper> ajaxResponse = new AjaxResponse<>();
		//从session中获取用户id及学校id
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
		//String userId = "143647367136";//测试
		//String schoolId = "001";
		//调用服务取消收藏预习项
		String itemId = itemShareService.cancelCollect(shareId,userId,schoolId);

		ShareCollectWrapper collectWrapper = new ShareCollectWrapper();
		collectWrapper.setItemId(itemId);//预习项id
		collectWrapper.setPid(shareId);//收藏来源id（分享id）
		collectWrapper.setCollectState(0);//收藏状态 0--未收藏

		ajaxResponse.setWrapper(collectWrapper);
		ajaxResponse.setStatus("1");//取消收藏成功
		return ajaxResponse;
	}

	/**
	 * @param shareId 【请求参数】
	 * @return 查看分享的题目详情
	 */
	@RequestMapping(value = "detailItem")
	@ResponseBody
	public AjaxResponse<BankItemShareWrapper> getDetailItem(String shareId) {
		AjaxResponse<BankItemShareWrapper> ajaxResponse = new AjaxResponse<>();
		//调用服务查看题目
		BankItemShareDetailDTO viewDTO = itemShareService.getDetailItem(shareId);
		//题目详情wrapper
		BankItemShareWrapper wrapper = new BankItemShareWrapper();
		wrapper.setBankItemShareDTO(viewDTO.getBankItemShareViewDTO());
		wrapper.setFilesDTOs(viewDTO.getFilesDTOs());

		ajaxResponse.setWrapper(wrapper);
		ajaxResponse.setStatus("1");//获取信息成功
		return ajaxResponse;
	}

	/**
	 * 取消分享
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "cancelShare")
	@ResponseBody
	public AjaxResponse cancelShare(String shareId) {
		//从session中得到schoolId
		String schoolId = GetSessionContentUtil.getInstance().currentSchoolID();
		String userId = GetSessionContentUtil.getInstance().currentUserID();
		//String schoolId = "001";
		//String userId = "001";
		//调用服务取消分享
		String itemId = itemShareService.cancelShare(shareId,userId,schoolId);

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setWrapper(itemId);//题目id
		ajaxResponse.setStatus("1");
		return ajaxResponse;
	}

}
