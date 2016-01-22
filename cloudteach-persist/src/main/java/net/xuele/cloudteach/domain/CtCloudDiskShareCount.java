package net.xuele.cloudteach.domain;

/**
 * CtCloudDiskShareCount
 * 根据文件分类计数domain对象
 * @author panglx
 * @date on 2015/9/15 0015.
 */
public class CtCloudDiskShareCount {
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
