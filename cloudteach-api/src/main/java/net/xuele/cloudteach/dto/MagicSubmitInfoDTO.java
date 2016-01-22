package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * MagicSubmitInfoDTO
 *
 * @author panglx
 * @date on 2015/8/14 0014.
 */
public class MagicSubmitInfoDTO implements Serializable{

	private static final long serialVersionUID = -3374561129338901400L;
	/**
	 * 作业完成时间
	 */
	private Date endTime;

	/**
	 * 作业分数
	 */
	private Integer score;

	/**
	 * 挑战成绩描述
	 */
	private String scorecontext;

	/**
	 * 第几套题目ID
	 */
	private Integer orderNum;

	/**
	 * 作业回答表
	 */
	MagicWorkAnswerDTO stuAnswerInfo;

	/**
	 * 作业回答附件
	 */
	List<MagicWorkAnswerFilesDTO> stuSubmitFilesList;

	/**
	 * 挑战题目详情
	 */
	//List<MagicWorkChallengeQueDTO> queDTOList;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getScorecontext() {
		return scorecontext;
	}

	public void setScorecontext(String scorecontext) {
		this.scorecontext = scorecontext;
	}

	public MagicWorkAnswerDTO getStuAnswerInfo() {
		return stuAnswerInfo;
	}

	public void setStuAnswerInfo(MagicWorkAnswerDTO stuAnswerInfo) {
		this.stuAnswerInfo = stuAnswerInfo;
	}

	public List<MagicWorkAnswerFilesDTO> getStuSubmitFilesList() {
		return stuSubmitFilesList;
	}

	public void setStuSubmitFilesList(List<MagicWorkAnswerFilesDTO> stuSubmitFilesList) {
		this.stuSubmitFilesList = stuSubmitFilesList;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
