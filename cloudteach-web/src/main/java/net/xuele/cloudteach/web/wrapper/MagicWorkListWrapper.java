package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * MagicWorkListWrapper
 * 提分宝展示Wrapper
 * @author panglx
 * @date on 2015/7/22 0022.
 */
public class MagicWorkListWrapper {
	/**
	 * 题库id
	 */
	private String bankId;

	/**
	 * 题库名称
	 */
	private String title;

	/**
	 * 课题
	 */
	private String topic;

	/**
	 * 课时
	 */
	private String classes;

	/**
	 * 发布次数
	 */
	private int releases;

	List<MagicQuestionDetailWrapper> magicQuestionDetailWrappers;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public int getReleases() {
		return releases;
	}

	public void setReleases(int releases) {
		this.releases = releases;
	}

	public List<MagicQuestionDetailWrapper> getMagicQuestionDetailWrappers() {
		return magicQuestionDetailWrappers;
	}

	public void setMagicQuestionDetailWrappers(List<MagicQuestionDetailWrapper> magicQuestionDetailWrappers) {
		this.magicQuestionDetailWrappers = magicQuestionDetailWrappers;
	}
}
