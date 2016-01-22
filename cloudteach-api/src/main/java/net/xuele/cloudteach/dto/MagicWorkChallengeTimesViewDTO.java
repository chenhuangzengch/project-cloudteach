package net.xuele.cloudteach.dto;

import java.io.Serializable;

/**
 * Created by hujx on 2015/7/28 0028.
 * 提分宝挑战次数
 */
public class MagicWorkChallengeTimesViewDTO extends MagicWorkChallengeDTO implements Serializable {

    private static final long serialVersionUID = 419034342367047300L;
    // 挑战次数
    private int challengeTimes;

    public int getChallengeTimes() {
        return challengeTimes;
    }

    public void setChallengeTimes(int challengeTimes) {
        this.challengeTimes = challengeTimes;
    }

    @Override
    public String toString() {
        return "MagicWorkChallengeTimesViewDTO{" +
                "challengeTimes=" + challengeTimes +
                '}';
    }
}
