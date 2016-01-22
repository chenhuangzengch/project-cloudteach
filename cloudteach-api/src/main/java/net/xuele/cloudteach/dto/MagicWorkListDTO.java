package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * MagicWorkListDTO
 *
 * @author panglx
 * @date on 2015/7/20 0020.
 */
public class MagicWorkListDTO implements Serializable {

	private static final long serialVersionUID = -6732486502198942153L;

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

	/**
	 * 题目信息
	 */
	List<MagicQuestionDTO> magicQuestionDTO;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public int getReleases() {
		return releases;
	}

	public void setReleases(int releases) {
		this.releases = releases;
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

	public List<MagicQuestionDTO> getMagicQuestionDTO() {
		return magicQuestionDTO;
	}

	public void setMagicQuestionDTO(List<MagicQuestionDTO> magicQuestionDTO) {
		this.magicQuestionDTO = magicQuestionDTO;
	}

	@Override
	public String toString() {
		return "MagicWorkListDTO{" +
				"bankId='" + bankId + '\'' +
				", title='" + title + '\'' +
				", topic='" + topic + '\'' +
				", classes='" + classes + '\'' +
				", releases=" + releases +
				", magicQuestionDTO=" + magicQuestionDTO +
				'}';
	}
}
