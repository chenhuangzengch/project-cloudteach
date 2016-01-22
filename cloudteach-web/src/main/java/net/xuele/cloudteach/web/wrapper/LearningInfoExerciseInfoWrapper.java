package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by hujx on 2015/12/8 0008.
 */
public class LearningInfoExerciseInfoWrapper {

    private String unitName;
    private float totalPartakeRate;
    private List<LearningInfoExerciseWorkWrapper> workInfoList;

    public float getTotalPartakeRate() {
        return totalPartakeRate;
    }

    public void setTotalPartakeRate(float totalPartakeRate) {
        this.totalPartakeRate = totalPartakeRate;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<LearningInfoExerciseWorkWrapper> getWorkInfoList() {
        return workInfoList;
    }

    public void setWorkInfoList(List<LearningInfoExerciseWorkWrapper> workInfoList) {
        this.workInfoList = workInfoList;
    }
}
