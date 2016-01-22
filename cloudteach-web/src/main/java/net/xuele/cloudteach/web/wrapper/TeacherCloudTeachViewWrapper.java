package net.xuele.cloudteach.web.wrapper;

import net.xuele.cloudteach.dto.TeacherCloudTeachViewDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * TeacherCloudTeachViewWrapper
 * 教师云教学内容（包括预习作业，课后作业，发布的课件，发布的板书）
 * @author duzg
 * @date 2015/7/13 0002
 */
public class TeacherCloudTeachViewWrapper {

	public TeacherCloudTeachViewWrapper(TeacherCloudTeachViewDTO teacherCloudTeachViewDTO) {
		BeanUtils.copyProperties(teacherCloudTeachViewDTO, this);
	}

	/**
	 * 作业号
	 */
	private String workId;
	/**
	 * 布置作业教师用户号
	 */
	private String userId;

	/**
	 * 布置作业教师名字
	 */
	private String userName;

	/**
	 * 用户职务
	 */
	private String positionName;

	/**
	 * 教师头像
	 */
	private String userIcon;

	/**
	 * 课程号
	 */
	private String unitId;
	/**
	 * 课程名
	 */
	private String unitName;
	/**
	 * 课本名
	 */
	private String bookName;
	/**
	 * 科目号
	 */
	private String subjectId;
	/**
	 * 科目名
	 */
	private String subjectName;
	/**
	 * 作业描述内容
	 */
	private String context;

	/**
	 * 口语作业内容
	 */
	private String voiceContext;

	/**
	 * 作业发布时间
	 */
	private Date publishTime;
	/**
	 * 作业最晚提交时间
	 */
	private Date endTime;
	/**
	 * 作业题目数（电子作业的附件数）
	 */
	private Integer workItemNum;
	/**
	 * 作业类型作业类型(1导学，2课后作业，5课件，6板书)
	 */
	private Integer workType;
	/**
	 * 作业对应班级总数
	 */
	private Integer workClassNum;
	/**
	 * 作业对应班级信息
	 */
	private String workClassJson;
	/**
	 * 作业对应学生总数
	 */
	private Integer workStudentNum;
	/**
	 * 作业参与学生数
	 */
	private Integer workSubStudentNum;
	/**
	 * 作业学生数参与百分比
	 */
	private Float workSubStudentPect;
	/**
	 * 已批改学生数
	 */
	private Integer workCorrectStudentNum;
	/**
	 * 0未完成 ，1部分完成，2全部完成
	 */
	private Integer finishStatus;
	/**
	 * 0未批改，1部分批改，2全部批改
	 */
	private Integer correctStatus;
	/**
	 * 作业录音文件url
	 */
	private String tapeFileUrl;

	/**
	 * 作业录音文件后缀
	 */
	private String tapeFileExt;

	/**
	 * 附件数组：{id:'id',uri:'uri',ext:'ext'} json数组
	 */
	private String files;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getWorkItemNum() {
		return workItemNum;
	}

	public void setWorkItemNum(Integer workItemNum) {
		this.workItemNum = workItemNum;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getWorkClassNum() {
		return workClassNum;
	}

	public void setWorkClassNum(Integer workClassNum) {
		this.workClassNum = workClassNum;
	}

	public String getWorkClassJson() {
		return workClassJson;
	}

	public void setWorkClassJson(String workClassJson) {
		this.workClassJson = workClassJson;
	}

	public Integer getWorkStudentNum() {
		return workStudentNum;
	}

	public void setWorkStudentNum(Integer workStudentNum) {
		this.workStudentNum = workStudentNum;
	}

	public Integer getWorkSubStudentNum() {
		return workSubStudentNum;
	}

	public void setWorkSubStudentNum(Integer workSubStudentNum) {
		this.workSubStudentNum = workSubStudentNum;
	}

	public Float getWorkSubStudentPect() {
		return workSubStudentPect;
	}

	public void setWorkSubStudentPect(Float workSubStudentPect) {
		this.workSubStudentPect = workSubStudentPect;
	}

	public Integer getWorkCorrectStudentNum() {
		return workCorrectStudentNum;
	}

	public void setWorkCorrectStudentNum(Integer workCorrectStudentNum) {
		this.workCorrectStudentNum = workCorrectStudentNum;
	}

	public Integer getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(Integer finishStatus) {
		this.finishStatus = finishStatus;
	}

	public Integer getCorrectStatus() {
		return correctStatus;
	}

	public void setCorrectStatus(Integer correctStatus) {
		this.correctStatus = correctStatus;
	}

	public String getTapeFileUrl() {
		return tapeFileUrl;
	}

	public void setTapeFileUrl(String tapeFileUrl) {
		this.tapeFileUrl = tapeFileUrl;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getVoiceContext() {
		return voiceContext;
	}

	public void setVoiceContext(String voiceContext) {
		this.voiceContext = voiceContext;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getTapeFileExt() {
		return tapeFileExt;
	}

	public void setTapeFileExt(String tapeFileExt) {
		this.tapeFileExt = tapeFileExt;
	}
}
