package net.xuele.cloudteach.domain;

import java.util.List;

/**
 * CtMagic2QuestInfo
 *
 * @author panglx
 * @date on 2015/11/12 0012.
 */
public class CtMagic2QuestInfo extends QQuest {


	/**
	 * 题目对应选项
	 */
	List<QAnswer> qAnswers;

	public List<QAnswer> getqAnswers() {
		return qAnswers;
	}

	public void setqAnswers(List<QAnswer> qAnswers) {
		this.qAnswers = qAnswers;
	}
}
