package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.GuidanceWorkViewDTO;
import org.springframework.beans.BeanUtils;

/**
 * GuidanceWorkViewWrapper
 * 预习作业
 * @author duzg
 * @date 2015/7/12 0002
 */
public class GuidanceWorkViewWrapper extends EffectiveWorkViewWrapper {
	/**
	 * 初始化
	 * @param guidanceWorkViewDTO
	 */
	public GuidanceWorkViewWrapper(GuidanceWorkViewDTO guidanceWorkViewDTO) {
		BeanUtils.copyProperties(guidanceWorkViewDTO, this);
	}

	/**
	 * 作业状态类型 1进行中 2定时未发布 3已归档
	 */
	private int workStatusType;

	public int getWorkStatusType() {
		return workStatusType;
	}

	public void setWorkStatusType(int workStatusType) {
		this.workStatusType = workStatusType;
	}
}
