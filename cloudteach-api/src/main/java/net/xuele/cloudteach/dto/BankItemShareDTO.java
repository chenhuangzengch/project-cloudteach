package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * BankItemShareDTO
 *
 * @author panglx
 * @date on 2015/7/24 0024.
 */
public class BankItemShareDTO implements Serializable {

	private static final long serialVersionUID = -2660137506333848990L;
	/**
	 * 分享题目ID
	 */
	private String shareId;

	/**
	 * 创建者ID（原作者）
	 */
	private String creator;

	/**
	 * 创建者名称
	 */
	private String creatorName;

	/**
	 * 原题目ID（对应教师提目表题目ID）
	 */
	private String itemId;

	/**
	 * 课程ID
	 */
	private String unitId;

	/**
	 * 学校ID
	 */
	private String schoolId;

	/**
	 * 学校名称
	 */
	private String schoolName;

	/**
	 * 区域ID
	 */
	private String areaId;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 分享范围类型 1学校，2县域，3市域，4省域，0全国
	 */
	private Integer shareType;

	/**
	 * 题目类型：1预习 4电子作业 7口语作业
	 */
	private Integer itemType;

	/**
	 * 提交方式：图片
	 */
	private Integer subImage;

	/**
	 * 提交方式：录音
	 */
	private Integer subTape;

	/**
	 * 提交方式：视频
	 */
	private Integer subVideo;

	/**
	 * 提交方式：其他
	 */
	private Integer subOther;

	/**
	 * 口语作业内容
	 */
	private String voiceContext = "";

	/**
	 * 描述内容
	 */
	private String context;

	/**
	 * 置顶标志
	 */
	private Integer stickStatus;

	/**
	 * 分享时间
	 */
	private Date shareTime;

	/**
	 * 审核意见
	 */
	private String opinion;

	/**
	 * 状态
	 */
	private Integer status;

	private String audit_user_id;

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 分享题目ID
	 */
	public String getShareId() {
		return shareId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性分享题目ID
	 */
	public void setShareId(String shareId) {
		this.shareId = shareId == null ? null : shareId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 创建者ID（原作者）
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性创建者ID（原作者）
	 */
	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 创建者名称
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性创建者名称
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName == null ? null : creatorName.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 原题目ID（对应教师提目表题目ID）
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性原题目ID（对应教师提目表题目ID）
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId == null ? null : itemId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 课程ID
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性课程ID
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 学校ID
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性学校ID
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId == null ? null : schoolId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 学校名称
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性学校名称
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName == null ? null : schoolName.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 区域ID
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性区域ID
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId == null ? null : areaId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 区域名称
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性区域名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 分享范围类型 1学校，2县域，3市域，4省域，0全国
	 */
	public Integer getShareType() {
		return shareType;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性分享范围类型 1学校，2县域，3市域，4省域，0全国
	 */
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 题目类型：1预习 4电子作业 7口语作业
	 */
	public Integer getItemType() {
		return itemType;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性题目类型：1预习 4电子作业 7口语作业
	 */
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 提交方式：图片
	 */
	public Integer getSubImage() {
		return subImage;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性提交方式：图片
	 */
	public void setSubImage(Integer subImage) {
		this.subImage = subImage;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 提交方式：录音
	 */
	public Integer getSubTape() {
		return subTape;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性提交方式：录音
	 */
	public void setSubTape(Integer subTape) {
		this.subTape = subTape;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 提交方式：视频
	 */
	public Integer getSubVideo() {
		return subVideo;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性提交方式：视频
	 */
	public void setSubVideo(Integer subVideo) {
		this.subVideo = subVideo;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 提交方式：其他
	 */
	public Integer getSubOther() {
		return subOther;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性提交方式：其他
	 */
	public void setSubOther(Integer subOther) {
		this.subOther = subOther;
	}

	public String getVoiceContext() {
		return voiceContext;
	}

	public void setVoiceContext(String voiceContext) {
		this.voiceContext = voiceContext;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 描述内容
	 */
	public String getContext() {
		return context;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性描述内容
	 */
	public void setContext(String context) {
		this.context = context == null ? null : context.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 置顶标志
	 */
	public Integer getStickStatus() {
		return stickStatus;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性置顶标志
	 */
	public void setStickStatus(Integer stickStatus) {
		this.stickStatus = stickStatus;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 分享时间
	 */
	public Date getShareTime() {
		return shareTime;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性分享时间
	 */
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 审核意见
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性审核意见
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion == null ? null : opinion.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE] 的属性 状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE]的属性状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(String audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	@Override
	public String toString() {
		return "BankItemShareDTO{" +
				"shareId='" + shareId + '\'' +
				", creator='" + creator + '\'' +
				", creatorName='" + creatorName + '\'' +
				", itemId='" + itemId + '\'' +
				", unitId='" + unitId + '\'' +
				", schoolId='" + schoolId + '\'' +
				", schoolName='" + schoolName + '\'' +
				", areaId='" + areaId + '\'' +
				", areaName='" + areaName + '\'' +
				", shareType=" + shareType +
				", itemType=" + itemType +
				", subImage=" + subImage +
				", subTape=" + subTape +
				", subVideo=" + subVideo +
				", subOther=" + subOther +
				", voiceContext='" + voiceContext + '\'' +
				", context='" + context + '\'' +
				", stickStatus=" + stickStatus +
				", shareTime=" + shareTime +
				", opinion='" + opinion + '\'' +
				", status=" + status +
				'}';
	}
}
