package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TeacherCloudTeachViewDTO
 * 云教学内容（包括预习作业，课后作业，发布的课件，发布的板书）
 * @author duzg
 * @date 2015/7/13 0002
 */
public class TeacherCloudTeachViewDTO implements Serializable {
	private static final long serialVersionUID = 3483406794500863816L;

	/**
	 * 作业ID
	 */
	private String workId;

	/**
	 * 教师用户ID
	 */
	private String userId;

	/**
	 * 教师用户名字
	 */
	private String userName;

	/**
	 * 用户职务
	 */
	private String positionName;

	/**
	 * 教师用户头像
	 */
	private String userIcon;

	/**
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 作业最晚提交时间
	 */
	private Date endTime;

	/**
	 * 科目名称
	 */
	private String subjectName;

	/**
	 * 课程ID
	 */
	private String unitId;

	/**
	 * 课程名称
	 */
	private String unitName;

	/**
	 * 作业描述
	 */
	private String context;

	/**
	 * 口语作业内容
	 */
	private String voiceContext = "";

	/**
	 * 附件信息：{id:'id',uri:'uri',name:'name',ext:'ext'} json格式信息
	 */
	private String files;

	/**
	 * 1预习作业，2提分宝作业，3同步课堂作业，4电子作业，5课件，6板书，7口语作业
	 */
	private Integer workType;

	/**
	 * 预习作业/电子作业/口语作业：1，提分宝：题目数，同步课堂：游戏附件数
	 */
	private Integer workItemNum;

	/**
	 * 班级数
	 */
	private Integer workClassNum;

	/**
	 * 班级信息(jason格式)包括班级编号，班级名称
	 */
	private String workClassJson;

	/**
	 * 作业对应学生数
	 */
	private Integer workStudentNum;

	/**
	 * 学校ID:用于数据库分片存储
	 */
	private String schoolId;

	/**
	 * 状态:0删除 1有效
	 */
	private Integer status;
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

	public Integer getWorkItemNum() {
		return workItemNum;
	}

	public void setWorkItemNum(Integer workItemNum) {
		this.workItemNum = workItemNum;
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

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@Override
	public String toString() {
		return "TeacherCloudTeachViewDTO{" +
				"workId='" + workId + '\'' +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", positionName='" + positionName + '\'' +
				", userIcon='" + userIcon + '\'' +
				", publishTime=" + publishTime +
				", endTime=" + endTime +
				", subjectName='" + subjectName + '\'' +
				", unitName='" + unitName + '\'' +
				", context='" + context + '\'' +
				", voiceContext='" + voiceContext + '\'' +
				", files='" + files + '\'' +
				", workType=" + workType +
				", workItemNum=" + workItemNum +
				", workClassNum=" + workClassNum +
				", workClassJson='" + workClassJson + '\'' +
				", workStudentNum=" + workStudentNum +
				", schoolId='" + schoolId + '\'' +
				", status=" + status +
				", workSubStudentNum=" + workSubStudentNum +
				", workSubStudentPect=" + workSubStudentPect +
				", workCorrectStudentNum=" + workCorrectStudentNum +
				", finishStatus=" + finishStatus +
				", correctStatus=" + correctStatus +
				", tapeFileUrl='" + tapeFileUrl + '\'' +
				", tapeFileExt='" + tapeFileExt + '\'' +
				'}';
	}
}
