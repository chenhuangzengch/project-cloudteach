package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cm.wang on 2015/7/17 0017.
 */
public class CourseReappearInfoDTO implements Serializable {

    private static final long serialVersionUID = -1692964414271179956L;

    private CourseReappearDTO courseReappearDTO;

    //课件Id
    private List<String> coursewaresIdList;

    private List<String> classIdList;

    private String classJson;

    //根据课堂Id打开的时候有用
    private List<TeachCoursewaresDTO> teachCoursewaresDTOList;

    public CourseReappearDTO getCourseReappearDTO() {
        return courseReappearDTO;
    }

    public void setCourseReappearDTO(CourseReappearDTO courseReappearDTO) {
        this.courseReappearDTO = courseReappearDTO;
    }

    public List<String> getCoursewaresIdList() {
        return coursewaresIdList;
    }

    public void setCoursewaresIdList(List<String> coursewaresIdList) {
        this.coursewaresIdList = coursewaresIdList;
    }

    public List<String> getClassIdList() {
        return classIdList;
    }

    public void setClassIdList(List<String> classIdList) {
        this.classIdList = classIdList;
    }

    public String getClassJson() {
        return classJson;
    }

    public void setClassJson(String classJson) {
        this.classJson = classJson;
    }

    public List<TeachCoursewaresDTO> getTeachCoursewaresDTOList() {
        return teachCoursewaresDTOList;
    }

    public void setTeachCoursewaresDTOList(List<TeachCoursewaresDTO> teachCoursewaresDTOList) {
        this.teachCoursewaresDTOList = teachCoursewaresDTOList;
    }

    @Override
    public String toString() {
        return "CourseReappearInfoDTO{" +
                "courseReappearDTO=" + courseReappearDTO +
                ", coursewaresIdList=" + coursewaresIdList +
                ", classIdList=" + classIdList +
                ", classJson='" + classJson + '\'' +
                ", teachCoursewaresDTOList=" + teachCoursewaresDTOList +
                '}';
    }
}
