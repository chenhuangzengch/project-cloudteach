package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * BankItemShareViewDTO
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemShareDetailDTO implements Serializable {

	private static final long serialVersionUID = -2727952967193835345L;

	/**
	 * 题库信息
	 */
	BankItemShareViewDTO bankItemShareViewDTO;

	/**
	 * 题目附件列表
	 */
	List<BankItemShareFileViewDTO> filesDTOs;

	public BankItemShareViewDTO getBankItemShareViewDTO() {
		return bankItemShareViewDTO;
	}

	public void setBankItemShareViewDTO(BankItemShareViewDTO bankItemShareViewDTO) {
		this.bankItemShareViewDTO = bankItemShareViewDTO;
	}

	public List<BankItemShareFileViewDTO> getFilesDTOs() {
		return filesDTOs;
	}

	public void setFilesDTOs(List<BankItemShareFileViewDTO> filesDTOs) {
		this.filesDTOs = filesDTOs;
	}

	@Override
	public String toString() {
		return "BankItemShareDetailDTO{" +
				"bankItemShareViewDTO=" + bankItemShareViewDTO +
				", filesDTOs=" + filesDTOs +
				'}';
	}
}
