package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * 预习附件表详细信息
 * Created by panglx on 2015/7/15 0015.
 */
public class GuidanceItemFilesDetailDTO extends GuidanceItemFilesDTO implements Serializable {
	private static final long serialVersionUID = 6051248607005663397L;

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
		return "GuidanceItemFilesDetailDTO{" +
				"name='" + name + '\'' +
				", extension='" + extension + '\'' +
				", extIconType='" + extIconType + '\'' +
				'}';
	}
}
