package net.xuele.cloudteach.web.form;

import java.util.Date;

/**
 * Created by panglx on 2015/7/3 0003.
 */
public class CloudDiskShareForm {

	/**
	 * 文件编号，老数据库为自增ID，新库可以使用uuid
	 */
	private String diskId;

	/**
	 *  ShareFail = -105,    分享审核不通过
	 *	NotShare = -5,        不可分享
	 *	Nomal = 0,              正常可分享
	 *	Share = 5,                已经分享
	 *	Success = 10            审核成功
	 */
	private Integer shareStatus;

	/**
	 * 最后一次分享的更新
	 */
	private Date shareTime;

	/**
	 * 审核说明
	 */
	private String auditInstructions;

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public Integer getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(Integer shareStatus) {
		this.shareStatus = shareStatus;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public String getAuditInstructions() {
		return auditInstructions;
	}

	public void setAuditInstructions(String auditInstructions) {
		this.auditInstructions = auditInstructions;
	}
}
