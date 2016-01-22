package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Magic2WorkWrapper
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2WorkQueWrapper {
	private String queId;//编号

	private Integer type;//11:单选题 | 12:多选题 | 2:判断题 | 3:填空题 | 4:主观题 | 5:口语（仅英语科目）

	private String solution;//解题思路

	private String kpTag;//知识点

	private String unitId;

	private String parentId;//原题id

	private Float score;//分值

	private Integer sort;//衍生题序号

	private String content;//题目正文

	/**
	 * 0：纯文本|1：音频
	 */
	private Integer ctentType;

	List<QAnswerWrapper> qAnswerDTOs;//题目答案

	public Integer getCtentType() {
		return ctentType;
	}

	public void setCtentType(Integer ctentType) {
		this.ctentType = ctentType;
	}

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getKpTag() {
		return kpTag;
	}

	public void setKpTag(String kpTag) {
		this.kpTag = kpTag;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<QAnswerWrapper> getqAnswerDTOs() {
		return qAnswerDTOs;
	}

	public void setqAnswerDTOs(List<QAnswerWrapper> qAnswerDTOs) {
		this.qAnswerDTOs = qAnswerDTOs;
	}
}
