package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CloudDiskShareCountDTO
 *
 * @author panglx
 * @date on 2015/9/15 0015.
 */
public class CloudDiskShareCountDTO implements Serializable {

	private static final long serialVersionUID = 8902765852130498636L;
	/**
	 * 文件类型
	 */
	private Integer fileType;

	/**
	 * 文件数量
	 */
	private Integer num;

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
