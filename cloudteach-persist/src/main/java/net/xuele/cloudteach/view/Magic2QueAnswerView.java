package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.QAnswer;

import java.util.List;

/**
 * Magic2QueAnswer
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2QueAnswerView {
	private String cqId;//挑战题目id

	private String queId;//题目id

	private String parentId;//原题id

	private Long queTime;//题目用时

	List<QAnswer> qAnswers;//答案id

	public String getCqId() {
		return cqId;
	}

	public void setCqId(String cqId) {
		this.cqId = cqId;
	}

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

	public List<QAnswer> getqAnswers() {
		return qAnswers;
	}

	public void setqAnswers(List<QAnswer> qAnswers) {
		this.qAnswers = qAnswers;
	}
}
