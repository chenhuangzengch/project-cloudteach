package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * MagicQuestionViewDTO
 *
 * @author panglx
 * @date on 2015/7/31 0031.
 */
public class MagicQuestionViewDTO implements Serializable {

	private static final long serialVersionUID = 8294511978698496177L;
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
	private List<MagicQuestionDTO> magicQuestionDTOs;

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

	public List<MagicQuestionDTO> getMagicQuestionDTOs() {
		return magicQuestionDTOs;
	}

	public void setMagicQuestionDTOs(List<MagicQuestionDTO> magicQuestionDTOs) {
		this.magicQuestionDTOs = magicQuestionDTOs;
	}

	@Override
	public String toString() {
		return "MagicQuestionViewDTO{" +
				"total=" + total +
				", orderNum=" + orderNum +
				", magicQuestionDTOs=" + magicQuestionDTOs +
				'}';
	}
}
