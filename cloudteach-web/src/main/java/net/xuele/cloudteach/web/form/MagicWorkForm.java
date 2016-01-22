package net.xuele.cloudteach.web.form;

import net.xuele.cloudteach.web.wrapper.WorkTapeFiles;

import java.util.List;

/**
 * MagicWorkForm
 *
 * @author panglx
 * @date on 2015/7/22 0022.
 */
public class MagicWorkForm {

	/**
	 * 题库id
	 */
	private String bankId;

	/**
	 * 课程id
	 */
	private String unitId;

	/**
	 * 作业描述
	 */
	private String context;

	/**
	 * 发布时间
	 */
	private String publishTime;

	/**
	 * 最晚提交时间（1,3,5）
	 */
	private int lastCommitRange;

	/**
	 * 提交方式--图片
	 */
	private Integer subImage =0;

	/**
	 * 提交方式--录音
	 */
	private Integer subTape =0;

	/**
	 * 提交方式--视频
	 */
	private Integer subVideo =0;

	/**
	 * 提交方式--其他
	 */
	private Integer subOther =0;

	/**
	 * 发布班级列表
	 */
	List<String> classList;

	/**
	 * 班级信息(jason格式)包括班级编号，班级名称
	 */
	String classJson;

	/**
	 * 教师录音附件
	 */
	WorkTapeFiles workTapeFiles;

	public String getClassJson() {
		return classJson;
	}

	public void setClassJson(String classJson) {
		this.classJson = classJson;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public WorkTapeFiles getWorkTapeFiles() {
		return workTapeFiles;
	}

	public void setWorkTapeFiles(WorkTapeFiles workTapeFiles) {
		this.workTapeFiles = workTapeFiles;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public int getLastCommitRange() {
		return lastCommitRange;
	}

	public void setLastCommitRange(int lastCommitRange) {
		this.lastCommitRange = lastCommitRange;
	}

	public Integer getSubImage() {
		return subImage;
	}

	public void setSubImage(Integer subImage) {
		this.subImage = subImage;
	}

	public Integer getSubTape() {
		return subTape;
	}

	public void setSubTape(Integer subTape) {
		this.subTape = subTape;
	}

	public Integer getSubVideo() {
		return subVideo;
	}

	public void setSubVideo(Integer subVideo) {
		this.subVideo = subVideo;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getSubOther() {
		return subOther;
	}

	public void setSubOther(Integer subOther) {
		this.subOther = subOther;
	}
}
