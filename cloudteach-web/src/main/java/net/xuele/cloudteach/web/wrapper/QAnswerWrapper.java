package net.xuele.cloudteach.web.wrapper;

/**
 * QAnswerWrapper
 *
 * @author panglx
 * @date on 2015/10/26 0026.
 */
public class QAnswerWrapper {
	private String aId;

	private String queId;

	private Integer sortid;

	private Integer iscorrect;

	private String aContent;

	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public String getQueId() {
		return queId;
	}

	public void setQueId(String queId) {
		this.queId = queId;
	}

	public Integer getSortid() {
		return sortid;
	}

	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}

	public Integer getIscorrect() {
		return iscorrect;
	}

	public void setIscorrect(Integer iscorrect) {
		this.iscorrect = iscorrect;
	}

	public String getaContent() {
		return aContent;
	}

	public void setaContent(String aContent) {
		this.aContent = aContent;
	}
}
