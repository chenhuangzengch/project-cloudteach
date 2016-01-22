package net.xuele.cloudteach.constant;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * CoursewarePackPrefixEnum
 * 课件打包文件格式转换枚举
 *
 * @author sunxh
 * @date 15/10/23
 */
public enum CoursewarePackPrefixEnum {


    _DOC("doc", true, "swf"),
    _DOCX("docx", true, "swf"),
    _PPT("ppt", false, "swf"),
    _PPTX("pptx", false, "swf"),
    _TXT("txt", true, "swf"),
    _XLS("xls", true, "swf"),
    _XLSX("xlsx", true, "swf"),
    _PDF("pdf", true, "swf"),
    _WPS("wps", true, "swf"),
    _RTF("rtf", true, "swf"),


    _GIF("gif", true, "jpg"),
    //jpg文件不需要文件系统转换
    _JPG("jpg", false, "jpg"),
    _JPEG("jpeg", true, "jpg"),
    _PNG("png", true, "jpg"),
    _BMP("bmp", true, "jpg"),


    _MP3("mp3", false, "mp3"),
    _AMR("amr", true, "mp3"),
    _MMF("mmf", true, "mp3"),
    _AIF("aif", true, "mp3"),
    _AU("au", true, "mp3"),
    _RAM("ram", true, "mp3"),
    _WAV("wav", true, "mp3"),
    _WMA("wma", true, "mp3"),
    _OGG("ogg", true, "mp3"),
    _APE("ape", true, "mp3"),
    _ACC("acc", true, "mp3"),
    _AAC("aac", true, "mp3"),
    _FLAC("flac", true, "mp3"),
    _VQF("vqf", true, "mp3"),
    _MID("mid", true, "mp3"),


    _MP4("mp4", true, "flv"),
    _3GP("3gp", true, "flv"),
    _MKV("mkv", true, "flv"),
    _MOV("mov", true, "flv"),
    _WMV("wmv", true, "flv"),
    _NAVI("navi", true, "flv"),
    _MPG("mpg", true, "flv"),
    _AVI("avi", true, "flv"),
    _MPEG("mpeg", true, "flv"),
    _RMVB("rmvb", true, "flv"),
    _RM("rm", true, "flv"),
    _ASF("asf", false, "flv"),
    _FLV("flv", false, "flv"),

    _SWF("swf", false, "swf");

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 是否需要转换
     */
    private Boolean needExchange;

    /**
     * 要转换成的前缀
     */
    private String prefix;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getNeedExchange() {
        return needExchange;
    }

    public void setNeedExchange(Boolean needExchange) {
        this.needExchange = needExchange;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    CoursewarePackPrefixEnum(String extension, Boolean needExchange, String prefix) {
        this.extension = extension;
        this.needExchange = needExchange;
        this.prefix = prefix;
    }

    public static CoursewarePackPrefixEnum getPrefix(String extension) {
        return preMapper.get(extension);
    }

    private static HashMap<String, CoursewarePackPrefixEnum> preMapper = new HashMap<>();

    static {
        for (CoursewarePackPrefixEnum etm : EnumSet.allOf(CoursewarePackPrefixEnum.class)) {
            preMapper.put(etm.extension, etm);
        }
    }
}
