package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.AfterClassWorkViewDTO;
import net.xuele.cloudteach.dto.EffectiveWorkViewDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * AfterClassWorkViewWrapper
 * 课后作业
 * @author duzg
 * @date 2015/7/8 0002
 */
public class AfterClassWorkViewWrapper extends EffectiveWorkViewWrapper {
	/**
	 * 初始化
	 * @param afterClassWorkViewDTO
	 */
	public AfterClassWorkViewWrapper(AfterClassWorkViewDTO afterClassWorkViewDTO) {
		BeanUtils.copyProperties(afterClassWorkViewDTO, this);
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
