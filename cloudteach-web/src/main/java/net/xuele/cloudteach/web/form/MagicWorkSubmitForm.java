package net.xuele.cloudteach.web.form;

/**
 * MagicWorkSubmitForm
 *
 * @author panglx
 * @date on 2015/8/4 0004.
 */
public class MagicWorkSubmitForm {
	String workId;//作业id
	String challengeId;//挑战id
	String context;//回答内容

	String magicWorkAnswerFilesJSON;//学生回答附件json

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
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

	public String getMagicWorkAnswerFilesJSON() {
		return magicWorkAnswerFilesJSON;
	}

	public void setMagicWorkAnswerFilesJSON(String magicWorkAnswerFilesJSON) {
		this.magicWorkAnswerFilesJSON = magicWorkAnswerFilesJSON;
	}
}
