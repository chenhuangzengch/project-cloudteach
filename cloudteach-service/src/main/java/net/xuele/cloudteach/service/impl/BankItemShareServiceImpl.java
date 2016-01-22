package net.xuele.cloudteach.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.constant.CloudTeachErrorEnum;
import net.xuele.cloudteach.domain.*;
import net.xuele.cloudteach.dto.*;
import net.xuele.cloudteach.dto.page.BankItemSharePageRequest;
import net.xuele.cloudteach.persist.*;
import net.xuele.cloudteach.service.BankItemShareService;
import net.xuele.cloudteach.view.BankItemShareView;
import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.page.Page;
import net.xuele.common.page.PageResponse;
import net.xuele.common.utils.PageUtils;
import net.xuele.member.dto.UserDTO;
import net.xuele.member.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * BankItemShareServiceImpl
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
@Service
public class BankItemShareServiceImpl implements BankItemShareService{

	@Autowired
	private CtBankItemShareMapper ctBankItemShareMapper;//分享的题目Mapper

	@Autowired
	private CtBankItemMapper ctBankItemMapper;//题库Mapper

	@Autowired
	private CtBankItemShareFilesMapper ctBankItemShareFilesMapper;//分享题目附件Mapper

	@Autowired
	CtBankItemSharePraiseMapper ctBankItemSharePraiseMapper;//点赞记录Mapper

	@Autowired
	CtBankItemShareStatisticsMapper ctBankItemShareStatisticsMapper;//分享统计表Mapper

	@Autowired
	CtBankItemShareCollectMapper ctBankItemShareCollectMapper;//题目分享收藏记录Mapper

	@Autowired
	CtBankItemFilesMapper ctBankItemFilesMapper;//题目附件Mapper

	@Autowired
	UserService userService; //用户信息service

	@Autowired
	CtCloudDiskMapper cloudDiskMapper;//云盘Mapper



	/**
	 * 分页获取大家分享的题目
	 * @param request
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public PageResponse<BankItemShareDetailDTO> queryItemShareResource(BankItemSharePageRequest request) throws CloudteachException {
		CtBankItemShare bankItemShare = new CtBankItemShare();
		bankItemShare.setCreator(request.getUserId());//用户id
		bankItemShare.setUnitId(request.getUnitId());//课程id
		bankItemShare.setSchoolId(request.getSchoolId());//学校id
		bankItemShare.setAreaId(request.getAreaId());//地区id
		bankItemShare.setItemType(request.getItemType());//题目类型：1预习 4电子作业 7口语作业
		int seltype = request.getSeltype();//筛选类型
		//获取分页信息
		//获取数据
		Page page = PageUtils.buildPage(request);
		page.setPage(request.getPage());
		int pagesize = request.getPageSize();//页面大小
		//大家分享的题目
		List<BankItemShareView> bankItemShareViews = ctBankItemShareMapper.selectByPage(pagesize,page,bankItemShare,seltype);
		//总记录数
		int records =ctBankItemShareMapper.selectCount(bankItemShare,seltype);
		//返回dto
		List<BankItemShareDetailDTO> bankItemShareViewDTOs = new ArrayList<>();
		BankItemShareDetailDTO viewDTO;
		for (BankItemShareView g:bankItemShareViews){
			//调用接口获取用户头像
			UserDTO userDTO = userService.getByUserId(g.getCreator());//用户信息
			g.setIcon(userDTO.getIcon());//用户头像

			//获取附件详情
			List<BankItemShareFileViewDTO> fileViewDTOs = this.getDetailFiles(g.getShareId(),g.getSchoolId());
			viewDTO = new BankItemShareDetailDTO();
			viewDTO.setFilesDTOs(fileViewDTOs);//附件详情
			viewDTO.setBankItemShareViewDTO(this.viewToDTO(g));//题目dto
			bankItemShareViewDTOs.add(viewDTO);
		}
		//返回PageResponse
		PageResponse<BankItemShareDetailDTO> response = new PageResponse<>();
		PageUtils.buldPageResponse(request, response);
		response.setRecords(records);
		response.setRows(bankItemShareViewDTOs);
		return response;
	}

	/**
	 * 查看题目的详情
	 * @param shareId
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public BankItemShareDetailDTO getDetailItem(String shareId) throws CloudteachException {
		//获取题目详情
		BankItemShareView shareView = ctBankItemShareMapper.showDetail(shareId);
		if (null == shareView){
			//对象不存在
			throw new CloudteachException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getMsg(), CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getCode());
		}
        //调用接口获取用户头像
		UserDTO userDTO = userService.getByUserId(shareView.getCreator());//用户信息
		shareView.setIcon(userDTO.getIcon());//用户头像

		BankItemShareDetailDTO viewDTO = new BankItemShareDetailDTO();
		BankItemShareViewDTO shareDTO = new BankItemShareViewDTO();
		BeanUtils.copyProperties(shareView,shareDTO);
		//获取附件信息
		List<BankItemShareFileViewDTO> fileViewDTOs = this.getDetailFiles(shareId, shareView.getSchoolId());
		viewDTO.setBankItemShareViewDTO(shareDTO);//题目信息
		viewDTO.setFilesDTOs(fileViewDTOs);//附件信息
		//浏览数+1
		this.modifyStatisticsTimes(shareId,1,3);

		return viewDTO;//题目详情dto
	}

	/**
	 * 获取点赞用户信息
	 * @param shareId
	 * @return
	 * @throws CloudteachException
	 */
	@Override
	public List<BankItemSharePraiseViewDTO> getDetailPraiseUser(String shareId) throws CloudteachException {
		List<CtBankItemSharePraise>  users = ctBankItemSharePraiseMapper.selectPraiseUser(shareId);//点赞用户信息
		if (null == users){//不存在点赞用户
			return null;
		}
		List<BankItemSharePraiseViewDTO> praiseDTOs = new ArrayList<>();//点赞用户详细信息列表
		BankItemSharePraiseViewDTO udto ;//点赞用户
		for (CtBankItemSharePraise u:users){
			//调用接口获取用户信息
			UserDTO userDTO = userService.getByUserId( u.getUserId());//用户信息
			udto = new BankItemSharePraiseViewDTO();
			BeanUtils.copyProperties(u,udto);
			udto.setUserName(userDTO.getRealName());//用户名
			praiseDTOs.add(udto);
		}
		return praiseDTOs;
	}

	/**
	 * 点赞
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException  有可能抛出异常 【已经点赞不能点赞】，【自己分享的不能点赞】
	 */
	@Override
	public int praise(String shareId, String creator) throws CloudteachException {
		//获取当前点赞的题目信息
		CtBankItemShare itemShare = ctBankItemShareMapper.selectByPrimaryKey(shareId);
		if (null == itemShare){
			//对象不存在
			throw new CloudteachException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getMsg(), CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getCode());
		}else if (itemShare.getCreator().equals(creator)){
			//自己分享的不能点赞
			throw new CloudteachException(CloudTeachErrorEnum.USERSHARENOPRAISE.getMsg(), CloudTeachErrorEnum.USERSHARENOPRAISE.getCode());
		}
		//查询点赞记录
		CtBankItemSharePraise itemSharePraise = ctBankItemSharePraiseMapper.selectByPrimaryKey(shareId,creator);
		if (null ==itemSharePraise){
			//没有点赞记录--插入一条点赞记录
			itemSharePraise = new CtBankItemSharePraise();
			itemSharePraise.setPraiseStatus(1);
			itemSharePraise.setPraiseTime(new Date());
			itemSharePraise.setShareId(shareId);
			itemSharePraise.setUserId(creator);
			ctBankItemSharePraiseMapper.insert(itemSharePraise);//新增数据
		}else if (itemSharePraise.getPraiseStatus()==1){
			//已经点赞不能再次点赞
			throw new CloudteachException(CloudTeachErrorEnum.ALREADYPRAISED.getMsg(), CloudTeachErrorEnum.ALREADYPRAISED.getCode());
		}else{
			//已经存在点赞记录，点赞状态改为1
			itemSharePraise.setPraiseStatus(1);
			itemSharePraise.setPraiseTime(new Date());
			ctBankItemSharePraiseMapper.updateByPrimaryKey(itemSharePraise);
		}
		/** 题目分享统计表点赞数+1*/
		return this.modifyStatisticsTimes(shareId,1,2);

	}

	/**
	 * 取消赞
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException 有可能抛出异常 【未点赞不能取消点赞】
	 */
	@Override
	public int cancelPraise(String shareId, String creator) throws CloudteachException {
		//查询点赞记录--判断是否点赞
		CtBankItemSharePraise itemSharePraise = ctBankItemSharePraiseMapper.selectByPrimaryKey(shareId,creator);
		if (null == itemSharePraise || itemSharePraise.getPraiseStatus()==0){
			//未点赞不能取消点赞
			throw new CloudteachException(CloudTeachErrorEnum.UNPRAISED.getMsg(), CloudTeachErrorEnum.UNPRAISED.getCode());
		}else{
			//点赞记录表状态改为0--未赞
			itemSharePraise.setPraiseStatus(0);
			ctBankItemSharePraiseMapper.updateByPrimaryKey(itemSharePraise);
			//预习项分享统计表点赞数-1
			return this.modifyStatisticsTimes(shareId,-1,2);
		}
	}

	/**
	 * 收藏
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException 可能抛出的异常 【不能收藏自己的资源】【已经收藏】
	 */
	@Override
	public String collectItem(String shareId, String creator, String schoolId) throws CloudteachException {
		//获取当前点赞的题目信息
		CtBankItemShare itemShare = ctBankItemShareMapper.selectByPrimaryKey(shareId);
		if (null == itemShare){
			//查找的对象不存在
			throw new CloudteachException(CloudTeachErrorEnum.OBJECTNOTFOUND.getMsg(), CloudTeachErrorEnum.OBJECTNOTFOUND.getCode());
		}else if (itemShare.getCreator().equals(creator)){
			//不能收藏自己的资源
			throw new CloudteachException(CloudTeachErrorEnum.CANNOTCOLLECTBYSELF.getMsg(), CloudTeachErrorEnum.CANNOTCOLLECTBYSELF.getCode());
		}
		/**查询收藏记录表*/
		CtBankItemShareCollect itemShareCollect = ctBankItemShareCollectMapper.selectByPrimaryKey(shareId,creator);
		if (null ==itemShareCollect){
			//没有收藏记录，收藏表insert
			itemShareCollect = new CtBankItemShareCollect();
			itemShareCollect.setCollectStatus(1);
			itemShareCollect.setCollectTime(new Date());
			itemShareCollect.setShareId(shareId);
			itemShareCollect.setUserId(creator);
			ctBankItemShareCollectMapper.insert(itemShareCollect);//新增数据
		}else if (itemShareCollect.getCollectStatus()==1){
			//已经收藏
			throw new CloudteachException(CloudTeachErrorEnum.ALREADYCILLECTED.getMsg(), CloudTeachErrorEnum.ALREADYCILLECTED.getCode());
		}else{
			//有收藏记录 update收藏记录表状态，改为1--已收藏
			itemShareCollect.setCollectStatus(1);
			itemShareCollect.setCollectTime(new Date());
			ctBankItemShareCollectMapper.updateByPrimaryKey(itemShareCollect);
		}
		// 我的题库新增记录
		CtBankItem bankItem = new CtBankItem();
		BeanUtils.copyProperties(itemShare,bankItem);
		bankItem.setCreator(creator);//收藏用户
		bankItem.setUserId(itemShare.getCreator());//收藏来源用户id
		bankItem.setPid(shareId);//收藏来源id
		bankItem.setStickStatus(0);
		bankItem.setIsCollect(1);//收藏状态
		bankItem.setStatus(1);
		bankItem.setShareStatus(0);//分享状态设为私有
		bankItem.setCreateTime(new Date());
		bankItem.setUpdateTime(new Date());
		bankItem.setSchoolId(schoolId);//学校id--收藏用户所在学校id
		bankItem.setItemId(UUID.randomUUID().toString().replace("-", ""));//预习项id
		ctBankItemMapper.insert(bankItem);
		// 题目附件copy
		this.copyItemFile(shareId,bankItem.getItemId(),schoolId);
		//收藏次数+1
		this.modifyStatisticsTimes(shareId,1,1);

		return bankItem.getItemId();//返回收藏生成的题目id
	}

	/**
	 * 取消收藏
	 * @param shareId
	 * @param creator
	 * @return
	 * @throws CloudteachException  可能抛出的异常 【未收藏】
	 */
	@Override
	public String cancelCollect(String shareId, String creator, String schoolId) throws CloudteachException {
		/**查询收藏记录表*/
		CtBankItemShareCollect itemShareCollect = ctBankItemShareCollectMapper.selectByPrimaryKey(shareId,creator);
		if (null == itemShareCollect || itemShareCollect.getCollectStatus()==0){
			//未收藏
			throw new CloudteachException(CloudTeachErrorEnum.UNCOLLECTED.getMsg(), CloudTeachErrorEnum.UNCOLLECTED.getCode());
		}else{
			//我的题目逻辑删除，状态改为0--删除
			List<CtBankItem> bankItem = ctBankItemMapper.selectMyCollect(shareId, creator, schoolId);
			CtBankItem item = bankItem.get(0);
			item.setStatus(0);
			ctBankItemMapper.updateByPrimaryKey(item);
			//预习项分享统计 收藏数-1
			this.modifyStatisticsTimes(shareId,-1,1);
			//收藏状态改为0--未收藏
			itemShareCollect.setCollectStatus(0);
			ctBankItemShareCollectMapper.updateByPrimaryKey(itemShareCollect);
			return item.getItemId();//返回逻辑删除的题目id
		}
	}

	/**
	 * 取消分享
	 * @param shareId
	 * @param schoolId
	 * @return
	 */
	@Override
	public String cancelShare(String shareId,String creator,String schoolId){
		CtBankItemShare itemShare = ctBankItemShareMapper.selectByPrimaryKey(shareId);
		if (itemShare == null){
			//题目不存在
			throw new CloudteachException(CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getMsg(), CloudTeachErrorEnum.BANK_ITEM_NOT_FOUND.getCode());
		}else if ( itemShare.getStatus() != 2){
			//题目未分享成功，不可取消分享
			throw new CloudteachException(CloudTeachErrorEnum.BANK_ITEM_UNSHARED.getMsg(), CloudTeachErrorEnum.BANK_ITEM_UNSHARED.getCode());
		}else if (! itemShare.getCreator().equals(creator)){
			//判断是否我分享的(不是自己分享的不能取消分享)
			throw new CloudteachException(CloudTeachErrorEnum.NOTMYSHARE.getMsg(), CloudTeachErrorEnum.NOTMYSHARE.getCode());
		}
		//分享教师题目表状态改为0
		ctBankItemShareMapper.unShareBankItem(shareId);
		//教师题目表分享状态改为0--私有状态
		ctBankItemMapper.unShareItem(schoolId,itemShare.getItemId());

		//返回题目id
		return itemShare.getItemId();
	}

	/**
	 * @param itemId 题目号
	 * @return int
	 * @throws net.xuele.common.exceptions.CloudteachException 分享我的题目审核成功对应大家分享中的记录状态修改为已分享，
	 *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
	 */
	@Override
	public int shareGuidanceItemSuc(String itemId) throws CloudteachException {
		BankItemShareDTO itemShareDTO = this.querySharedRecordByItemid(itemId);
		if (null != itemShareDTO && itemShareDTO.getStatus()==1){
			//状态处于审核中才允许审核
			ctBankItemShareMapper.shareBankItemSuc(itemShareDTO.getShareId());//审核通过
		}else{
			//状态不正确，抛错
			throw new CloudteachException(CloudTeachErrorEnum.GUIDANCE_ITEM_STATUS_ERROR.getMsg(), CloudTeachErrorEnum.GUIDANCE_ITEM_STATUS_ERROR.getCode());
		}
		return 1;
	}

	/**
	 * @param itemId 题目号
	 * @return int
	 * @throws net.xuele.common.exceptions.CloudteachException 分享我的题目审核失败对应大家分享中的记录状态修改为审核失败，
	 *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
	 */
	@Override
	public int shareGuidanceItemFail(String itemId) throws CloudteachException {
		BankItemShareDTO itemShareDTO = this.querySharedRecordByItemid(itemId);
		if (null != itemShareDTO && itemShareDTO.getStatus()==1){
			//状态处于审核中才允许审核
			ctBankItemShareMapper.shareBankItemFail(itemShareDTO.getShareId());//审核不通过
		}else{
			//状态不正确，抛错
			throw new CloudteachException(CloudTeachErrorEnum.GUIDANCE_ITEM_STATUS_ERROR.getMsg(), CloudTeachErrorEnum.GUIDANCE_ITEM_STATUS_ERROR.getCode());
		}
		return 1;
	}

	/**
	 * @param shareId 分享题目号
	 * @return GuidanceItemShareDTO
	 * @throws net.xuele.common.exceptions.CloudteachException 根据ID获取大家分享的题目信息，
	 *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
	 */
	@Override
	public BankItemShareDTO queryGuidanceItemShare(String shareId) throws CloudteachException {
		CtBankItemShare itemShare = ctBankItemShareMapper.selectByPrimaryKey(shareId);
		if (null == itemShare){
			itemShare = new CtBankItemShare();
		}
		return this.domainToDTO(itemShare);
	}

	/**
	 * @param itemId 题目号
	 * @return GuidanceItemShareDTO
	 * @throws net.xuele.common.exceptions.CloudteachException 根据题目号获取处于审核中或已分享的记录，
	 *                                                         有可能抛出异常信息【运行时异常，上层调用可以不用捕获】
	 */
	@Override
	public BankItemShareDTO querySharedRecordByItemid(String itemId) throws CloudteachException {
		//根据原题id查询分享的题目
		CtBankItemShare itemShare = ctBankItemShareMapper.selectSharedRecordByItemid(itemId);
		if (null == itemShare){
			itemShare = new CtBankItemShare();
		}
		return this.domainToDTO(itemShare);
	}

/************************************private方法**********************************************************/
	/**
	 * 获取附件详情
	 * @param shareId
	 * @param schoolId
	 * @return
	 */
 	private List<BankItemShareFileViewDTO> getDetailFiles(String shareId,String schoolId){
		//获取附件信息
		List<CtBankItemShareFiles> itemShareFileses = ctBankItemShareFilesMapper.selectAttachment(shareId);
		if (null != itemShareFileses){//如果附件不为null
			List<BankItemShareFileViewDTO> itemShareFileViewDTOs = new ArrayList<>();//附件详情列表
			BankItemShareFileViewDTO viewDTO;
			CtCloudDisk cloudDisk;//云盘domain
			for (CtBankItemShareFiles file:itemShareFileses){
				//获取附件对应的云盘文件
				cloudDisk = cloudDiskMapper.selectByPrimaryKey(schoolId,file.getDiskId());
				if (null != cloudDisk){
					viewDTO = new BankItemShareFileViewDTO();
					BeanUtils.copyProperties(file, viewDTO);
					viewDTO.setExtension(cloudDisk.getExtension());//扩展名
					viewDTO.setName(cloudDisk.getName());//文件名
					viewDTO.setExtIconType(cloudDisk.getExtIconType());//扩展名对应图标
					viewDTO.setFileUri(cloudDisk.getFileUri());//HDFS文件uri
					itemShareFileViewDTOs.add(viewDTO);//附件详情
				}
			}
			return itemShareFileViewDTOs;//返回附件详情
		}
		return null;//没有附件返回null
	}

	/**
	 * 题目domain转dto
	 * @param itemShare
	 * @return
	 */
	private BankItemShareDTO domainToDTO(CtBankItemShare itemShare){
		BankItemShareDTO dto = new BankItemShareDTO();
		BeanUtils.copyProperties(itemShare,dto);
		return dto;
	}
	/**
	 * 题目domain转dto
	 * @param itemShareView
	 * @return
	 */
	private BankItemShareViewDTO viewToDTO(BankItemShareView itemShareView){
		BankItemShareViewDTO dto = new BankItemShareViewDTO();
		BeanUtils.copyProperties(itemShareView,dto);
		return dto;
	}

	/**
	 * 更改预习项分享统计表数量
	 * @param shareId
	 * @param num
	 * @return
	 */
	private int modifyStatisticsTimes(String shareId,int num,int type){
		CtBankItemShareStatistics itemShareStatistics = new CtBankItemShareStatistics();
		itemShareStatistics.setShareId(shareId);
		if(type==1){//收藏数
			itemShareStatistics.setCollectTimes(num);
		}else if(type ==2){//点赞数
			itemShareStatistics.setPraiseTimes(num);
		}else if (type == 3){//浏览数
			itemShareStatistics.setVewingTimes(num);
		}
		return ctBankItemShareStatisticsMapper.updateCount(itemShareStatistics);
	}

	/**
	 * 题目附件copy
	 * @autor panglx
	 * @param sourceId
	 * @param targetId
	 */
	private void copyItemFile(String sourceId,String targetId,String schoolId){
		//题目附件列表
		List<CtBankItemShareFiles> itemShareFilesList = ctBankItemShareFilesMapper.selectAttachment(sourceId);
		//题目附件
		CtBankItemFiles files;
		for(CtBankItemShareFiles f:itemShareFilesList){
			files = new CtBankItemFiles();
			files.setFileId(UUID.randomUUID().toString().replace("-", ""));
			files.setfType(1);//1教师题目
			files.setRelationId(targetId);//题目id
			files.setDiskId(f.getDiskId());//云盘id
			files.setSchoolId(schoolId);
			ctBankItemFilesMapper.insert(files);
		}
	}
}
