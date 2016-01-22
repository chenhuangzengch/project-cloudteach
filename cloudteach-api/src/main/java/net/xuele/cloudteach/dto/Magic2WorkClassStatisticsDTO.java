package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Magic2WorkClassStatisticsDTO
 *
 * @author panglx
 * @date on 2015/12/24 0024.
 */
public class Magic2WorkClassStatisticsDTO implements Serializable{

	private static final long serialVersionUID = 1307546067786774812L;

	/**
	 * 评价百分比
	 */
	private List<ScoreRateDTO> scoreRateDTOs;

	/**
	 * 统计学生列表
	 */
	private List<StudentStatisticsDTO> studentStatisticsDTOs;

	public List<ScoreRateDTO> getScoreRateDTOs() {
		return scoreRateDTOs;
	}

	public void setScoreRateDTOs(List<ScoreRateDTO> scoreRateDTOs) {
		this.scoreRateDTOs = scoreRateDTOs;
	}

	public List<StudentStatisticsDTO> getStudentStatisticsDTOs() {
		return studentStatisticsDTOs;
	}

	public void setStudentStatisticsDTOs(List<StudentStatisticsDTO> studentStatisticsDTOs) {
		this.studentStatisticsDTOs = studentStatisticsDTOs;
	}
}
