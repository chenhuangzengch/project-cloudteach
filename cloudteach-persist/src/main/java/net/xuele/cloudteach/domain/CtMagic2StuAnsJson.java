package net.xuele.cloudteach.domain;

/**
 * CtMagic2StuAnsJson
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class CtMagic2StuAnsJson {
	private String queId;//题目id

	private String parentId;//原题id

	private Long queTime;//题目用时

	private Integer rw;//题目对错

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Long getQueTime() {
		return queTime;
	}

	public void setQueTime(Long queTime) {
		this.queTime = queTime;
	}

	public Integer getRw() {
		return rw;
	}

	public void setRw(Integer rw) {
		this.rw = rw;
	}
}
