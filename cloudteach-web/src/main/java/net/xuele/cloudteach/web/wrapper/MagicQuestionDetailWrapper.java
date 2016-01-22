package net.xuele.cloudteach.web.wrapper;


/**
 * MagicQuestionDetailWrapper
 *
 * @author panglx
 * @date on 2015/7/22 0022.
 */
public class MagicQuestionDetailWrapper {
	/**
	 * 题目id
	 */
	private String queId;

	/**
	 * 分类号(1选择题 2填空题 3判断题...)
	 */
	private Integer queTypeCode;

	/**
	 * 题目内容
	 */
	private String content;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 习题讲解url
	 */
	private String url;

	/**
	 * 视频描述
	 */
	private String videoDesc;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public Integer getQueTypeCode() {
		return queTypeCode;
	}

	public void setQueTypeCode(Integer queTypeCode) {
		this.queTypeCode = queTypeCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}