package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hujx on 2015/10/22 0022.
 * 题库原题+衍生题
 */
public class QQuestViewDTO extends QQuestDTO implements Serializable {

    private static final long serialVersionUID = 4082200012272822595L;

    List<QQuestDTO> deriveQuests;

    public List<QQuestDTO> getDeriveQuests() {
        return deriveQuests;
    }

    public void setDeriveQuests(List<QQuestDTO> deriveQuests) {
        this.deriveQuests = deriveQuests;
    }

    @Override
    public String toString() {
        return "QQuestViewDTO{" +
                "deriveQuests=" + deriveQuests +
                '}';
    }
}
