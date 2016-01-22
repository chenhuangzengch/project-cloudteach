package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * BankItemShareFilesDTO
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemShareFilesDTO implements Serializable{

	private static final long serialVersionUID = -5887581824698963623L;

	/**
	 * 分享教师题目附件ID
	 */
	private String fileId;

	/**
	 * 分享题目ID（分享教师题目表主键）
	 */
	private String shareId;

	/**
	 * 关联的云盘资源id
	 */
	private String diskId;

	/**
	 * 获取 [CT_BANK_ITEM_SHARE_FILES] 的属性 分享教师题目附件ID
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE_FILES]的属性分享教师题目附件ID
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId == null ? null : fileId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE_FILES] 的属性 分享题目ID（分享教师题目表主键）
	 */
	public String getShareId() {
		return shareId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE_FILES]的属性分享题目ID（分享教师题目表主键）
	 */
	public void setShareId(String shareId) {
		this.shareId = shareId == null ? null : shareId.trim();
	}

	/**
	 * 获取 [CT_BANK_ITEM_SHARE_FILES] 的属性 关联的云盘资源id
	 */
	public String getDiskId() {
		return diskId;
	}

	/**
	 * 设置[CT_BANK_ITEM_SHARE_FILES]的属性关联的云盘资源id
	 */
	public void setDiskId(String diskId) {
		this.diskId = diskId == null ? null : diskId.trim();
	}

	@Override
	public String toString() {
		return "BankItemShareFilesDTO{" +
				"fileId='" + fileId + '\'' +
				", shareId='" + shareId + '\'' +
				", diskId='" + diskId + '\'' +
				'}';
	}
}
