package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtWorkGather;

import java.util.Date;
import java.util.List;

/**
 * TeacherCloudTeachView
 * 云教学内容（包括预习，课后作业，发布的课件，发布的板书）
 * @author duzg
 * @date 2015/7/13 0002
 */
public class TeacherCloudTeachView extends CtWorkGather {
	/**
	 * 教师用户名字
	 */
	private String userName;

	/**
	 * 教师用户头像
	 */
	private String userIcon;
	/**
	 * 用户职务
	 */
	private String positionName;
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
	 * 作业录音文件fileKey
	 */
	private String tapeFileUrl;
	/**
	 * 作业录音文件后缀
	 */
	private String tapeFileExt;

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
