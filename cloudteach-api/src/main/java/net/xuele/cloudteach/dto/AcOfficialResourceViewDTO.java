package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * AcOfficialResourceViewDTO
 *
 * @author panglx
 * @date on 2015/8/17 0017.
 */
public class AcOfficialResourceViewDTO extends AcOfficialResourceDTO implements Serializable{

	private static final long serialVersionUID = 6275324726479274335L;
	/**
	 * 收藏状态
	 */
	private Integer collectState;

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}
}
