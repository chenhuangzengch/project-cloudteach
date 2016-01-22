package net.xuele.cloudteach.service.util;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;

/**
 * *使用Jaxb2.0实现XML<->Java Object的Binder.
 * <p/>
 * 特别支持Root对象是List的情形.
 */
public class JaxbUtil {
	// 多线程安全的Context.
	private JAXBContext jaxbContext;

	/**
	 * @param types 所有需要序列化的Root对象的类型.
	 */
	public JaxbUtil(Class<?>... types) throws JAXBException {
		jaxbContext = JAXBContext.newInstance(types);
	}

	/**
	 * Java Object->Xml,除集合类
	 *
	 * @param root
	 * @param encoding
	 * @param writer
	 * @return 返回 Writer,默认:StringWriter
	 * @throws JAXBException
	 */
	public Writer toXml(Object root, String encoding, Writer writer) throws JAXBException {
		if (null == writer) writer = new StringWriter();
		createMarshaller(encoding).marshal(root, writer);
		return writer;
	}

	/**
	 * Java Object->Xml.
	 * 默认:StringWriter
	 */
	public Writer toXml(Object root, String encoding) throws JAXBException {
		return toXml(root, encoding, null);
	}

	/**
	 * Java Object->Xml, 特别支持对Root Element是Collection的情形.
	 * 注:context需要设置JaxbUtil.CollectionWrapper.class
	 */
	@SuppressWarnings("unchecked")
	public Writer toXmlForCollection(Collection root, String rootName, String encoding, Writer writer) throws JAXBException {
		CollectionWrapper wrapper = new CollectionWrapper();
		wrapper.collection = root;
		JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(
				new QName(rootName), CollectionWrapper.class, wrapper);
		if (null == writer) writer = new StringWriter();
		toXml(wrapperElement, encoding, writer);
		return writer;
	}

	/**
	 * 默认处理
	 *
	 * @param root
	 * @param rootName
	 * @param encoding
	 * @return StringWriter
	 * @throws JAXBException
	 */
	public Writer toXmlForCollection(Collection root, String rootName, String encoding) throws JAXBException {
		return toXmlForCollection(root, rootName, encoding, null);
	}

	/**
	 * Xml->Java Object.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) throws JAXBException {
		StringReader reader = new StringReader(xml);
		return (T) createUnmarshaller().unmarshal(reader);
	}

	/**
	 * Xml->Java Object, 支持大小写敏感或不敏感.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml, boolean caseSensitive) throws JAXBException {
		String fromXml = xml;
		if (!caseSensitive) fromXml = xml.toLowerCase();
		StringReader reader = new StringReader(fromXml);
		return (T) createUnmarshaller().unmarshal(reader);
	}

	/**
	 * 创建Marshaller, 设定encoding(可为Null).
	 */
	public Marshaller createMarshaller(String encoding) throws JAXBException {
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		if (StringUtils.isNotBlank(encoding)) {
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
		}
		return marshaller;
	}

	/**
	 * 创建UnMarshaller.
	 */
	public Unmarshaller createUnmarshaller() throws JAXBException {
		return jaxbContext.createUnmarshaller();
	}

	/**
	 * 封装Root Element 是 Collection的情况.\
	 */
	public static class CollectionWrapper {
		@SuppressWarnings("unchecked")
		@XmlAnyElement
		protected Collection collection;
	}

}

