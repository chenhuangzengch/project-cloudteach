package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class GuidanceWorkItemAnswerPraiseInfoDTO extends GuidanceWorkItemAnswerPraiseDTO implements Serializable {
    private static final long serialVersionUID = 1361477162949328124L;

    private String pariseName;

    public String getPariseName() {
        return pariseName;
    }

    public void setPariseName(String pariseName) {
        this.pariseName = pariseName;
    }

    @Override
    public String toString() {
        return "GuidanceWorkItemAnswerPraiseInfoDTO{" +
                "pariseName='" + pariseName + '\'' +
                '}';
    }
}
