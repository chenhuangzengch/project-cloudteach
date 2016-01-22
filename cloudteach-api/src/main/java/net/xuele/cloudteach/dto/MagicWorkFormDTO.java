package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * MagicWorkFormDTO
 *
 * @author panglx
 * @date on 2015/7/30 0030.
 */
public class MagicWorkFormDTO implements Serializable {

	private static final long serialVersionUID = 2348828679689769314L;
	/**
	 * 提分宝作业信息
	 */
	MagicWorkDTO magicWorkDTO;

	/**
	 * 发布班级列表
	 */
	List<String> classList;

	/**
	 * 班级信息(jason格式)包括班级编号，班级名称
	 */
	String classJosn;

	/**
	 * 教师录音附件
	 */
	WorkTapeFilesDTO tapeFilesDTO;

	public MagicWorkDTO getMagicWorkDTO() {
		return magicWorkDTO;
	}

	public void setMagicWorkDTO(MagicWorkDTO magicWorkDTO) {
		this.magicWorkDTO = magicWorkDTO;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public String getClassJosn() {
		return classJosn;
	}

	public void setClassJosn(String classJosn) {
		this.classJosn = classJosn;
	}

	public WorkTapeFilesDTO getTapeFilesDTO() {
		return tapeFilesDTO;
	}

	public void setTapeFilesDTO(WorkTapeFilesDTO tapeFilesDTO) {
		this.tapeFilesDTO = tapeFilesDTO;
	}

	@Override
	public String toString() {
		return "MagicWorkFormDTO{" +
				"magicWorkDTO=" + magicWorkDTO +
				", classList=" + classList +
				", classJosn='" + classJosn + '\'' +
				", tapeFilesDTO=" + tapeFilesDTO +
				'}';
	}
}
