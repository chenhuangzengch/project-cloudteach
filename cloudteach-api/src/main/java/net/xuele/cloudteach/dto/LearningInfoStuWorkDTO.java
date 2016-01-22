package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/12/1 0001.
 * 学情学生统一信息
 */
public class LearningInfoStuWorkDTO implements Serializable {
    private static final long serialVersionUID = 7237753380952305822L;

    /* 作业信息 */
    private List<LearningInfoWorkDTO> workInfo;

    public List<LearningInfoWorkDTO> getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(List<LearningInfoWorkDTO> workInfo) {
        this.workInfo = workInfo;
    }
}
