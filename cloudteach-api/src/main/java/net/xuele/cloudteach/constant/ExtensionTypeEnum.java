package net.xuele.cloudteach.constant;

/**
 * Created by panglx on 2015/7/1 0001.
 * ziy
 * {"doc,docx,ppt,txt,pptx,xls,xlsx,pdf,wps,hlp,rtf,html","swf"},
 * {"amr,mmf,aif,au,ram,wav,wma,ogg,ape,acc,aac,flac,vqf,mid","mp3"},
 * {"mp4,3gp,mkv,mov,wmv,navi,mpg,avi,mpeg,rmvb,rm,asf","flv"}
 */
public enum ExtensionTypeEnum {

    ALL(0, "ALL", "所有文件"),
    OTHER(1, "OTHER", "其他"),
    PPT(2, "PPT", "PPT"),
    WORD(3, "WORD", "WORD"),
    VIDEO(4, "VIDEO", "视频"),
    VOICE(5, "VOICE", "声音"),
    JPG(6, "JPG", "图片"),

    SWF(4, "VIDEO", "动画归到视频分类"),;

    private int extCode;//类型代码
    private String extString;//类型名称
    private String extDesc;//描述：对应页面上显示的筛选类型

    /**
     * 构造方法
     *
     * @param extCode
     * @param extString
     * @param extDesc
     */
    ExtensionTypeEnum(int extCode, String extString, String extDesc) {
        this.extCode = extCode;
        this.extString = extString;
        this.extDesc = extDesc;
    }

    public int getExtCode() {
        return extCode;
    }

    public void setExtCode(int extCode) {
        this.extCode = extCode;
    }

    public String getExtString() {
        return extString;
    }

    public void setExtString(String extString) {
        this.extString = extString;
    }

    public String getExtDesc() {
        return extDesc;
    }

    public void setExtDesc(String extDesc) {
        this.extDesc = extDesc;
    }

}
