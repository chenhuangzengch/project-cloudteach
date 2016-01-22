package net.xuele.cloudteach.web.wrapper;

/**
 * Created by hujx on 2015/12/4 0004.
 * 学生总数，已提交数，未提交数
 */
public class LearningInfoSubmitStatisWrapper {

    private int totalStuNum;
    private int submitStuNum;
    private int unsubmitStuNum;

    public int getSubmitStuNum() {
        return submitStuNum;
    }

    public void setSubmitStuNum(int submitStuNum) {
        this.submitStuNum = submitStuNum;
    }

    public int getTotalStuNum() {
        return totalStuNum;
    }

    public void setTotalStuNum(int totalStuNum) {
        this.totalStuNum = totalStuNum;
    }

    public int getUnsubmitStuNum() {
        return unsubmitStuNum;
    }

    public void setUnsubmitStuNum(int unsubmitStuNum) {
        this.unsubmitStuNum = unsubmitStuNum;
    }
}
