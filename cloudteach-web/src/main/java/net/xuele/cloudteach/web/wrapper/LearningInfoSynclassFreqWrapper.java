package net.xuele.cloudteach.web.wrapper;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情同步课堂，学生练习次数
 */
public class LearningInfoSynclassFreqWrapper {

    private String studentName;
    private int playTimes;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }
}
