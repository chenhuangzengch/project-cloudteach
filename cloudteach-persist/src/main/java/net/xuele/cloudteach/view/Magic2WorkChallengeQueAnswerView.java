package net.xuele.cloudteach.view;

/**
 * Magic2WorkChallengeQueAnswerView
 *
 * @author panglx
 * @date on 2015/10/23 0023.
 */
public class Magic2WorkChallengeQueAnswerView {

	private String aId;

	private Integer iscorrect;

	private Integer isstuans;

	/**
	 * 选项排序码
	 */
	private Integer sortid;

	private String aContent;

	private String scontent;//学生回答

	public Integer getSortid() {
		return sortid;
	}

	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}

	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public Integer getIscorrect() {
		return iscorrect;
	}

	public void setIscorrect(Integer iscorrect) {
		this.iscorrect = iscorrect;
	}

	public Integer getIsstuans() {
		return isstuans;
	}

	public void setIsstuans(Integer isstuans) {
		this.isstuans = isstuans;
	}

	public String getaContent() {
		return aContent;
	}

	public void setaContent(String aContent) {
		this.aContent = aContent;
	}

	public String getScontent() {
		return scontent;
	}

	public void setScontent(String scontent) {
		this.scontent = scontent;
	}
}
