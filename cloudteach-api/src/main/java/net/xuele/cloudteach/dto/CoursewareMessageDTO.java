package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * CoursewareMessageDTO
 * 课件DTO，用于amqp消息，只保存简单的信息
 *
 * @author sunxh
 * @date 15/10/21
 */
public class CoursewareMessageDTO implements Serializable {

    private static final long serialVersionUID = -3873958864461276348L;

    /**
     * 学校ID
     */
    private String schoolId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 课件ID
     */
    private String coursewateId;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCoursewateId() {
        return coursewateId;
    }

    public void setCoursewateId(String coursewateId) {
        this.coursewateId = coursewateId;
    }

    @Override
    public String toString() {
        return "CoursewareMessageDTO{" +
                "schoolId='" + schoolId + '\'' +
                ", userId='" + userId + '\'' +
                ", coursewateId='" + coursewateId + '\'' +
                '}';
    }
}
