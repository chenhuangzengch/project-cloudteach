package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/9/22 0022.
 */
public class WorkFinishRateViewDTO implements Serializable {

    private static final long serialVersionUID = 2933542742284693813L;
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

    @Override
    public String toString() {
        return "WorkFInishRateViewDTO{" +
                "finishRate=" + finishRate +
                ", workId='" + workId + '\'' +
                '}';
    }
}
