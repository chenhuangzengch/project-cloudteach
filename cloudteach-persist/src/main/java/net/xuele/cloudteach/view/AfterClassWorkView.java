package net.xuele.cloudteach.view;

/**
 * AfterClassWorkView
 * 课后作业
 * @author duzg
 * @date 2015/7/12 0002
 */
public class AfterClassWorkView extends EffectiveWorkView {
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
