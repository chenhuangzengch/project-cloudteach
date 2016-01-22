package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * ClassFeedbackViewDTO
 *
 * @author panglx
 * @date on 2015/10/19 0019.
 */
public class ClassFeedbackViewDTO extends ClassFeedbackDTO implements Serializable{

	private static final long serialVersionUID = -7890283990241905160L;
	private String icon;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
