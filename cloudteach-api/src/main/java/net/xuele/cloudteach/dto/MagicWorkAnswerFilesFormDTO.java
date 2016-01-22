package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * MagicWorkAnswerFilesFormDTO
 *
 * @author panglx
 * @date on 2015/8/5 0005.
 */
public class MagicWorkAnswerFilesFormDTO implements Serializable {

	private static final long serialVersionUID = -8970976316805029866L;
	private String uri;

	private String fileName;

	private String extension;
	private Integer size;
	private String type;//附件类型

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "MagicWorkAnswerFilesFormDTO{" +
				"uri='" + uri + '\'' +
				", fileName='" + fileName + '\'' +
				", extension='" + extension + '\'' +
				", size=" + size +
				'}';
	}
}
