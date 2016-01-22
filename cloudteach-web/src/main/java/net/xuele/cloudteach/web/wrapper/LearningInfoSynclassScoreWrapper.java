package net.xuele.cloudteach.web.wrapper;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学情同步课堂，学生得分
 */
public class LearningInfoSynclassScoreWrapper {

    private String studentName;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
