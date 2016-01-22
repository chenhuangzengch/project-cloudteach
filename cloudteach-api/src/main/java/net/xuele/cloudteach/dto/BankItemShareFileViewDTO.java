package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * BankItemShareFileViewDTO
 *
 * @author panglx
 * @date on 2015/7/25 0025.
 */
public class BankItemShareFileViewDTO extends BankItemShareFilesDTO implements Serializable{

	private static final long serialVersionUID = -953156607422703176L;
	/**
	 * 文件名
	 */
	private String name;

	/**
	 * 扩展名
	 */
	private String extension;

	/**
	 * 扩展名图标类型
	 */
	private String extIconType;

	/**
	 * HDFS文件uri
	 */
	private String fileUri;

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtIconType() {
		return extIconType;
	}

	public void setExtIconType(String extIconType) {
		this.extIconType = extIconType;
	}

	@Override
	public String toString() {
		return "BankItemShareFileViewDTO{" +
				"name='" + name + '\'' +
				", extension='" + extension + '\'' +
				", extIconType='" + extIconType + '\'' +
				'}';
	}
}
