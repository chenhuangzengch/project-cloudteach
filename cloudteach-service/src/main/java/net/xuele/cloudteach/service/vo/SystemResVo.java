package net.xuele.cloudteach.service.vo;

import net.xuele.cloudteach.service.util.JaxbUtil;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/25 0025.
 */
@XmlRootElement(name = "resources")
public class SystemResVo {
	private String code;
	private String ex;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) throws JAXBException {
		List<SystemResVo> systemResVos=new ArrayList<>();
		SystemResVo sys1=new SystemResVo();
		sys1.setCode("code1");
		sys1.setEx("ex1");
		sys1.setName("name1");
		systemResVos.add(sys1);
		SystemResVo sys2=new SystemResVo();
		sys2.setCode("code1");
		sys2.setEx("ex1");
		sys2.setName("name1");
		systemResVos.add(sys2);
		JaxbUtil jaxbUtil=new JaxbUtil(JaxbUtil.CollectionWrapper.class, SystemResVo.class);
		StringWriter stringWriter=(StringWriter)jaxbUtil.toXmlForCollection(systemResVos, "systemresources", "utf-8");
		System.out.println(stringWriter.toString());

	}
}
