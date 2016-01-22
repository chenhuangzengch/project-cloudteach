package net.xuele.cloudteach.web.wrapper;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情同步课堂，学生练习用时
 */
public class LearningInfoSynclassTimeWrapper {

    private String studentName;
    private long playTime;

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
