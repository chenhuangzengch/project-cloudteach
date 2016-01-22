package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.GuidanceItemFilesDetailDTO;
import org.springframework.beans.BeanUtils;

/**
 * 大家分享的预习项附件
 * Created by panglx on 2015/7/16 0016.
 */
public class GuidanceItemFilesDetail {

	/**
	 * 关联的云盘资源id
	 */
	private String diskId;

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

	public GuidanceItemFilesDetail(GuidanceItemFilesDetailDTO filesDetailDTO) {
		BeanUtils.copyProperties(filesDetailDTO, this);
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
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
}
