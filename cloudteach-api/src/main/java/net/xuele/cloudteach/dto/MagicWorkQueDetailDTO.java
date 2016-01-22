package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * MagicWorkQueDetailDTO
 *
 * @author panglx
 * @date on 2015/8/22 0022.
 */
public class MagicWorkQueDetailDTO implements Serializable{

	private static final long serialVersionUID = -6934047725421693496L;

	/**
	 * 题目数
	 */
	private Integer total;

	/**
	 * 衍生题顺序（从1开始，原题=0）（套数）
	 */
	private Integer orderNum;
	/**
	 * 题目列表
	 */
	private List<MagicWorkChallengeQueViewDTO> magicQueViewDTOs;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<MagicWorkChallengeQueViewDTO> getMagicQueViewDTOs() {
		return magicQueViewDTOs;
	}

	public void setMagicQueViewDTOs(List<MagicWorkChallengeQueViewDTO> magicQueViewDTOs) {
		this.magicQueViewDTOs = magicQueViewDTOs;
	}
}
