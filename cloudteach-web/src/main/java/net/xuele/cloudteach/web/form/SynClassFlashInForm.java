package net.xuele.cloudteach.web.form;

/**
 * Created by Administrator on 2015/8/5 0005.
 */
public class SynClassFlashInForm {

    //练习Id，即gameId或者gameFiledId
    private Integer ucID;

    //用户类型
    private String uType;

    //userId|workId
    private String uID;

    private String returnString;

    private Integer cReal;

    public Integer getUcID() {
        return ucID;
    }

    public void setUcID(Integer ucID) {
        this.ucID = ucID;
    }

    public String getReturnString() {
        return returnString;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public Integer getcReal() {
        return cReal;
    }

    public void setcReal(Integer cReal) {
        this.cReal = cReal;
    }

    @Override
    public String toString() {
        return "SynClassFlashInForm{" +
                "ucID=" + ucID +
                ", uType='" + uType + '\'' +
                ", uID='" + uID + '\'' +
                ", returnString='" + returnString + '\'' +
                ", cReal=" + cReal +
                '}';
    }
}
