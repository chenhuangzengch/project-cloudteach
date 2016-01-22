package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * AfterClassWorkViewDTO
 * 作业管理-课后作业
 * @author duzg
 * @date 2015/7/12 0002
 */
public class AfterClassWorkViewDTO extends EffectiveWorkViewDTO  implements Serializable {

    private static final long serialVersionUID = -6342951945836205066L;

    /**
     * 作业状态类型 1进行中 2定时未发布 3已归档
     */
    private int workStatusType;

    public int getWorkStatusType() {
        return workStatusType;
    }

    public void setWorkStatusType(int workStatusType) {
        this.workStatusType = workStatusType;
    }

    @Override
    public String toString() {
        return "AfterClassWorkViewDTO{" +
                "workStatusType=" + workStatusType +
                '}';
    }
}