package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.BankItemShareFileViewDTO;
import net.xuele.cloudteach.dto.BankItemShareViewDTO;

import java.util.List;

/**
 * BankItemShareWrapper
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemShareWrapper {
	/**
	 * 题库信息
	 */
	BankItemShareViewDTO bankItemShareDTO;

	/**
	 * 题目附件列表
	 */
	List<BankItemShareFileViewDTO> filesDTOs;

	public BankItemShareViewDTO getBankItemShareDTO() {
		return bankItemShareDTO;
	}

	public void setBankItemShareDTO(BankItemShareViewDTO bankItemShareDTO) {
		this.bankItemShareDTO = bankItemShareDTO;
	}

	public List<BankItemShareFileViewDTO> getFilesDTOs() {
		return filesDTOs;
	}

	public void setFilesDTOs(List<BankItemShareFileViewDTO> filesDTOs) {
		this.filesDTOs = filesDTOs;
	}
}
