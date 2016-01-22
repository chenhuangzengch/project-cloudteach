package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.QQuest;

import java.util.List;

/**
 * QQuestView
 *
 * @author panglx
 * @date on 2015/10/22 0022.
 */
public class QQuestView extends QQuest {

    /**
     * 衍生题
     */
    List<QQuest> deriveQuests;

    public List<QQuest> getDeriveQuests() {
        return deriveQuests;
    }

    public void setDeriveQuests(List<QQuest> deriveQuests) {
        this.deriveQuests = deriveQuests;
    }
}
