package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * 分享审核DTO
 * Created by panglx on 2015/7/4 0004.
 */
public class ShareAuditDTO implements Serializable {

	private static final long serialVersionUID = 3978013566743170500L;

	/**
	 * 云盘编号
	 */
	private String diskId;

	/**
	 *  分享状态
	 ShareFail = 3,    分享审核不通过
	 Nomal = 0,              正常可分享
	 Share = 1,                审核中
	 Success = 2            审核成功（分享成功）
	 */
	private Integer status;

	/**
	 * 审核原因
	 */
	private String auditInstructions;

	public String getAuditInstructions() {
		return auditInstructions;
	}

	public void setAuditInstructions(String auditInstructions) {
		this.auditInstructions = auditInstructions;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ShareAuditDTO{" +
				"diskId='" + diskId + '\'' +
				", status=" + status +
				", auditInstructions='" + auditInstructions + '\'' +
				'}';
	}
}
