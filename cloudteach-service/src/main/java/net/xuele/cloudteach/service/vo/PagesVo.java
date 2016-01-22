package net.xuele.cloudteach.service.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
public class PagesVo {
	private List<PageVo> page;

	@XmlElement(name = "resource")
	public List<PageVo> getPage() {
		return page;
	}

	public void setPage(List<PageVo> page) {
		this.page = page;
	}
}
