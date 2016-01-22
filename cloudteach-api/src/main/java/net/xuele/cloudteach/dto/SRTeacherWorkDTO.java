package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * SRTeacherWorkDTO
 * 教师布置作业的统计信息
 * @author duzg
 * @date on 2015/9/29 0017.
 */
public class SRTeacherWorkDTO implements Serializable{

	private static final long serialVersionUID = -9064748825042846906L;

	/**
	 * 作业类型
	 */
	private int workType = 0;

	/**
	 * 班级学生数
	 */
	private int classStuNum = 0;

	/**
	 * 已提交作业学生数
	 */
	private int subStuNum = 0;

	private int finshPect = 0;

	private int scoreAPect = 0;
	private int scoreBPect = 0;
	private int scoreCPect = 0;
	private int scoreDPect = 0;
	private int scoreEPect = 0;
	private int scoreFPect = 0;
	private int unScorePect = 0;

	/**
	 * 得分为A学生列表
	 */
	private List<SRTeacherWorkStuDTO> scoreAStuList;

	/**
	 * 得分为B学生列表
	 */
	private List<SRTeacherWorkStuDTO> scoreBStuList;

	/**
	 * 得分为C学生列表
	 */
	private List<SRTeacherWorkStuDTO> scoreCStuList;

	/**
	 * 得分为D学生列表
	 */
	private List<SRTeacherWorkStuDTO> scoreDStuList;

	/**
	 * 得分为E学生列表
	 */
	private List<SRTeacherWorkStuDTO> scoreEStuList;

	/**
	 * 得分为F学生列表
	 */
	private List<SRTeacherWorkStuDTO> scoreFStuList;

	/**
	 * 未打分学生列表
	 */
	private List<SRTeacherWorkStuDTO> unScoreStuList;

	/**
	 * 已打分学生按成绩排名列表
	 */
	private List<SRTeacherWorkStuDTO> orderStuList;

	public int getWorkType() {
		return workType;
	}

	public void setWorkType(int workType) {
		this.workType = workType;
	}

	public int getClassStuNum() {
		return classStuNum;
	}

	public void setClassStuNum(int classStuNum) {
		this.classStuNum = classStuNum;
	}

	public int getSubStuNum() {
		return subStuNum;
	}

	public void setSubStuNum(int subStuNum) {
		this.subStuNum = subStuNum;
	}

	public List<SRTeacherWorkStuDTO> getScoreAStuList() {
		return scoreAStuList;
	}

	public void setScoreAStuList(List<SRTeacherWorkStuDTO> scoreAStuList) {
		this.scoreAStuList = scoreAStuList;
	}

	public List<SRTeacherWorkStuDTO> getScoreBStuList() {
		return scoreBStuList;
	}

	public void setScoreBStuList(List<SRTeacherWorkStuDTO> scoreBStuList) {
		this.scoreBStuList = scoreBStuList;
	}

	public List<SRTeacherWorkStuDTO> getScoreCStuList() {
		return scoreCStuList;
	}

	public void setScoreCStuList(List<SRTeacherWorkStuDTO> scoreCStuList) {
		this.scoreCStuList = scoreCStuList;
	}

	public List<SRTeacherWorkStuDTO> getScoreDStuList() {
		return scoreDStuList;
	}

	public void setScoreDStuList(List<SRTeacherWorkStuDTO> scoreDStuList) {
		this.scoreDStuList = scoreDStuList;
	}

	public List<SRTeacherWorkStuDTO> getScoreEStuList() {
		return scoreEStuList;
	}

	public void setScoreEStuList(List<SRTeacherWorkStuDTO> scoreEStuList) {
		this.scoreEStuList = scoreEStuList;
	}

	public List<SRTeacherWorkStuDTO> getScoreFStuList() {
		return scoreFStuList;
	}

	public void setScoreFStuList(List<SRTeacherWorkStuDTO> scoreFStuList) {
		this.scoreFStuList = scoreFStuList;
	}

	public List<SRTeacherWorkStuDTO> getUnScoreStuList() {
		return unScoreStuList;
	}

	public void setUnScoreStuList(List<SRTeacherWorkStuDTO> unScoreStuList) {
		this.unScoreStuList = unScoreStuList;
	}

	public List<SRTeacherWorkStuDTO> getOrderStuList() {
		return orderStuList;
	}

	public void setOrderStuList(List<SRTeacherWorkStuDTO> orderStuList) {
		this.orderStuList = orderStuList;
	}

	public int getFinshPect() {
		return finshPect;
	}

	public void setFinshPect(int finshPect) {
		this.finshPect = finshPect;
	}

	public int getScoreAPect() {
		return scoreAPect;
	}

	public void setScoreAPect(int scoreAPect) {
		this.scoreAPect = scoreAPect;
	}

	public int getScoreBPect() {
		return scoreBPect;
	}

	public void setScoreBPect(int scoreBPect) {
		this.scoreBPect = scoreBPect;
	}

	public int getScoreCPect() {
		return scoreCPect;
	}

	public void setScoreCPect(int scoreCPect) {
		this.scoreCPect = scoreCPect;
	}

	public int getScoreDPect() {
		return scoreDPect;
	}

	public void setScoreDPect(int scoreDPect) {
		this.scoreDPect = scoreDPect;
	}

	public int getScoreEPect() {
		return scoreEPect;
	}

	public void setScoreEPect(int scoreEPect) {
		this.scoreEPect = scoreEPect;
	}

	public int getScoreFPect() {
		return scoreFPect;
	}

	public void setScoreFPect(int scoreFPect) {
		this.scoreFPect = scoreFPect;
	}

	public int getUnScorePect() {
		return unScorePect;
	}

	public void setUnScorePect(int unScorePect) {
		this.unScorePect = unScorePect;
	}

	@Override
	public String toString() {
		return "SRTeacherWorkDTO{" +
				"workType=" + workType +
				", classStuNum=" + classStuNum +
				", subStuNum=" + subStuNum +
				", finshPect=" + finshPect +
				", scoreAPect=" + scoreAPect +
				", scoreBPect=" + scoreBPect +
				", scoreCPect=" + scoreCPect +
				", scoreDPect=" + scoreDPect +
				", scoreEPect=" + scoreEPect +
				", scoreFPect=" + scoreFPect +
				", unScorePect=" + unScorePect +
				", scoreAStuList=" + scoreAStuList +
				", scoreBStuList=" + scoreBStuList +
				", scoreCStuList=" + scoreCStuList +
				", scoreDStuList=" + scoreDStuList +
				", scoreEStuList=" + scoreEStuList +
				", scoreFStuList=" + scoreFStuList +
				", unScoreStuList=" + unScoreStuList +
				", orderStuList=" + orderStuList +
				'}';
	}
}
