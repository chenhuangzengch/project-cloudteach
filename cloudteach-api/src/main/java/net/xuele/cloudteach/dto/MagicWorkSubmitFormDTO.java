package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * MagicWorkSubmitFormDTO
 *
 * @author panglx
 * @date on 2015/7/31 0031.
 */
public class MagicWorkSubmitFormDTO implements Serializable {

	private static final long serialVersionUID = -6844712931415669558L;
	String workId;//作业id
	String userId;//学生id
	String schoolId;//学校id
	String challengeId;//挑战id
	String context;//回答内容

//	List<MagicWorkAnswerFilesDTO> answerFilesDTOs; //回答附件DTO

	String magicWorkAnswerFilesJSON;//学生回答附件json

	public String getMagicWorkAnswerFilesJSON() {
		return magicWorkAnswerFilesJSON;
	}

	public void setMagicWorkAnswerFilesJSON(String magicWorkAnswerFilesJSON) {
		this.magicWorkAnswerFilesJSON = magicWorkAnswerFilesJSON;
	}

/*	public List<MagicWorkAnswerFilesDTO> getAnswerFilesDTOs() {
		return answerFilesDTOs;
	}

	public void setAnswerFilesDTOs(List<MagicWorkAnswerFilesDTO> answerFilesDTOs) {
		this.answerFilesDTOs = answerFilesDTOs;
	}*/

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String challengeId) {
		this.challengeId = challengeId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "MagicWorkSubmitFormDTO{" +
				"workId='" + workId + '\'' +
				", userId='" + userId + '\'' +
				", schoolId='" + schoolId + '\'' +
				", challengeId='" + challengeId + '\'' +
				", context='" + context + '\'' +
				", magicWorkAnswerFilesJSON='" + magicWorkAnswerFilesJSON + '\'' +
				'}';
	}
}
