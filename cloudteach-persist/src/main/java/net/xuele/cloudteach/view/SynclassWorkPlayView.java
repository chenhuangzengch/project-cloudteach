package net.xuele.cloudteach.view;

import net.xuele.cloudteach.domain.CtSynclassWorkPlay;

/**
 * SynclassWorkPlayView
 *
 * @author duzg
 * @date 2015/7/20 0002
 */
public class SynclassWorkPlayView extends CtSynclassWorkPlay {

    /**
     * 游戏附件ID
     */
    private String gameFileId;
    /**
     * 游戏附件名称
     */
    private String gameName;

    /**
     * 学生用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String icon;

    /**
     * 真实游戏编号
     */
    private Integer cReal;

    public String getGameFileId() {
        return gameFileId;
    }

    public void setGameFileId(String gameFileId) {
        this.gameFileId = gameFileId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getcReal() {
        return cReal;
    }

    public void setcReal(Integer cReal) {
        this.cReal = cReal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}