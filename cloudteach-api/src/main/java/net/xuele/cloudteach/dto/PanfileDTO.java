package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/6/8 0008.
 * 云盘文件dto
 * 云盘文件数据传递对象的抽象
 *
 */
public class PanfileDTO implements Serializable {

    private int userFileId;
    private String userId;
    private String fcode;
    private int fid;
    private int ufid;
    private String ufUserId;
    private String unitCode;
    private int ufType;
    private String ufName;
    private String ufDesc;
    private String ufExtension;
    private int ufSize;
    private Timestamp ufAddTime;
    private Timestamp ufUpdateTime;
    private int ufShareState;
    private Timestamp ufShareTime;
    private int ufReaded;
    private int ufSaved;
    private int ufDownloaded;
    private String ufMsg;
    private int ufState;
    private String fileKey;

    public int getUserFileId() {
        return userFileId;
    }

    public void setUserFileId(int userFileId) {
        this.userFileId = userFileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getUfid() {
        return ufid;
    }

    public void setUfid(int ufid) {
        this.ufid = ufid;
    }

    public String getUfUserId() {
        return ufUserId;
    }

    public void setUfUserId(String ufUserId) {
        this.ufUserId = ufUserId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getUfType() {
        return ufType;
    }

    public void setUfType(int ufType) {
        this.ufType = ufType;
    }

    public String getUfName() {
        return ufName;
    }

    public void setUfName(String ufName) {
        this.ufName = ufName;
    }

    public String getUfDesc() {
        return ufDesc;
    }

    public void setUfDesc(String ufDesc) {
        this.ufDesc = ufDesc;
    }

    public String getUfExtension() {
        return ufExtension;
    }

    public void setUfExtension(String ufExtension) {
        this.ufExtension = ufExtension;
    }

    public int getUfSize() {
        return ufSize;
    }

    public void setUfSize(int ufSize) {
        this.ufSize = ufSize;
    }

    public Timestamp getUfAddTime() {
        return ufAddTime;
    }

    public void setUfAddTime(Timestamp ufAddTime) {
        this.ufAddTime = ufAddTime;
    }

    public Timestamp getUfUpdateTime() {
        return ufUpdateTime;
    }

    public void setUfUpdateTime(Timestamp ufUpdateTime) {
        this.ufUpdateTime = ufUpdateTime;
    }

    public int getUfShareState() {
        return ufShareState;
    }

    public void setUfShareState(int ufShareState) {
        this.ufShareState = ufShareState;
    }

    public Timestamp getUfShareTime() {
        return ufShareTime;
    }

    public void setUfShareTime(Timestamp ufShareTime) {
        this.ufShareTime = ufShareTime;
    }

    public int getUfReaded() {
        return ufReaded;
    }

    public void setUfReaded(int ufReaded) {
        this.ufReaded = ufReaded;
    }

    public int getUfSaved() {
        return ufSaved;
    }

    public void setUfSaved(int ufSaved) {
        this.ufSaved = ufSaved;
    }

    public int getUfDownloaded() {
        return ufDownloaded;
    }

    public void setUfDownloaded(int ufDownloaded) {
        this.ufDownloaded = ufDownloaded;
    }

    public String getUfMsg() {
        return ufMsg;
    }

    public void setUfMsg(String ufMsg) {
        this.ufMsg = ufMsg;
    }

    public int getUfState() {
        return ufState;
    }

    public void setUfState(int ufState) {
        this.ufState = ufState;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public String toString() {
        return "PanfileDTO{" +
                "userFileId=" + userFileId +
                ", userId='" + userId + '\'' +
                ", fcode='" + fcode + '\'' +
                ", fid=" + fid +
                ", ufid=" + ufid +
                ", ufUserId='" + ufUserId + '\'' +
                ", unitCode='" + unitCode + '\'' +
                ", ufType=" + ufType +
                ", ufName='" + ufName + '\'' +
                ", ufDesc='" + ufDesc + '\'' +
                ", ufExtension='" + ufExtension + '\'' +
                ", ufSize=" + ufSize +
                ", ufAddTime=" + ufAddTime +
                ", ufUpdateTime=" + ufUpdateTime +
                ", ufShareState=" + ufShareState +
                ", ufShareTime=" + ufShareTime +
                ", ufReaded=" + ufReaded +
                ", ufSaved=" + ufSaved +
                ", ufDownloaded=" + ufDownloaded +
                ", ufMsg='" + ufMsg + '\'' +
                ", ufState=" + ufState +
                ", fileKey='" + fileKey + '\'' +
                '}';
    }
}
