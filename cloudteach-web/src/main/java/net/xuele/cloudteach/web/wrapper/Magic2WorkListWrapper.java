package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Magic2WorkListWrapper
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2WorkListWrapper {

	int totalNum;//总题目数

	//int maxSort;//最大衍生题sort

	List<Magic2WorkQueWrapper> queWrappers;

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public List<Magic2WorkQueWrapper> getQueWrappers() {
		return queWrappers;
	}

	public void setQueWrappers(List<Magic2WorkQueWrapper> queWrappers) {
		this.queWrappers = queWrappers;
	}
}
