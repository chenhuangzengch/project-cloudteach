package net.xuele.cloudteach.web.wrapper;

/**
 * WorkTapeFiles
 * 教师录音附件
 * @author panglx
 * @date on 2015/7/23 0023.
 */
public class WorkTapeFiles {
	/**
	 * HDFS文件url
	 */
	private String url;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 扩展名
	 */
	private String extension;

	/**
	 * 文件大小
	 */
	private Integer size;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
}
