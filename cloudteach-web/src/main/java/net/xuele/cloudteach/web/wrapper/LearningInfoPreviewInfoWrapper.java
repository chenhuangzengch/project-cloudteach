package net.xuele.cloudteach.web.wrapper;

import java.util.List;

/**
 * Created by hujx on 2015/12/8 0008.
 */
public class LearningInfoPreviewInfoWrapper {

    private String unitName;
    private float totalPartakeRate;
    private List<LearningInfoPreviewWorkWrapper> workInfoList;

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

    public List<LearningInfoPreviewWorkWrapper> getWorkInfoList() {
        return workInfoList;
    }

    public void setWorkInfoList(List<LearningInfoPreviewWorkWrapper> workInfoList) {
        this.workInfoList = workInfoList;
    }
}
