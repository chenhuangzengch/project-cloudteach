package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Magic2SubmitJsonDTO
 * 提分宝提交答卷返回json
 * @author panglx
 * @date on 2015/12/29 0029.
 */
public class Magic2SubmitJsonDTO implements Serializable {
	private static final long serialVersionUID = -8825404522086125382L;
	private String queId;//题目id

	private int type;//题目类型

	private int rw;//回答对错

	List<String> aIds;//正确答案id

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRw() {
		return rw;
	}

	public void setRw(int rw) {
		this.rw = rw;
	}

	public List<String> getaIds() {
		return aIds;
	}

	public void setaIds(List<String> aIds) {
		this.aIds = aIds;
	}
}
