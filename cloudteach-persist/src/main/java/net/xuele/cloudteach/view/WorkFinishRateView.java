package net.xuele.cloudteach.view;

/**
 * Created by hujx on 2015/9/22 0022.
 */
public class WorkFinishRateView {

    // 作业ID
    private String workId;
    // 作业完成率
    private float finishRate;

    public float getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(float finishRate) {
        this.finishRate = finishRate;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
