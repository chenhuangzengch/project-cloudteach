package net.xuele.cloudteach.constant;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * Created by suxnh on 2015/7/2 0002.
 * {"doc,docx,ppt,txt,pptx,xls,xlsx,pdf,wps,hlp,rtf,html","swf"},
 * {"amr,mmf,aif,au,ram,wav,wma,ogg,ape,acc,aac,flac,vqf,mid","mp3"},
 * {"mp4,3gp,mkv,mov,wmv,navi,mpg,avi,mpeg,rmvb,rm,asf","flv"}
 * { ".gif", ".jpg", ".jpeg", ".GIF", ".JPG", ".JPEG", ".png", ".PNG", ".bmp", ".BMP" };
 */
public enum ExtensionTypeMapperEnum {

    DOC("doc", ExtensionTypeEnum.WORD, IconTypeEnum.WORD,"swf"),
    DOCX("docx", ExtensionTypeEnum.WORD, IconTypeEnum.WORD,"swf"),
    PPT("ppt", ExtensionTypeEnum.PPT, IconTypeEnum.PPT,"swf"),
    PPTX("pptx", ExtensionTypeEnum.PPT, IconTypeEnum.PPT,"swf"),
    TXT("txt", ExtensionTypeEnum.WORD, IconTypeEnum.TXT,"swf"),
    XLS("xls", ExtensionTypeEnum.OTHER, IconTypeEnum.EXCEL,"swf"),
    XLSX("xlsx", ExtensionTypeEnum.OTHER, IconTypeEnum.EXCEL,"swf"),
    PDF("pdf", ExtensionTypeEnum.WORD, IconTypeEnum.PDF,"swf"),
    WPS("wps", ExtensionTypeEnum.WORD, IconTypeEnum.WORD,"swf"),
    //    HLP("hlp",ExtensionTypeEnum.WORD,IconTypeEnum.WORD,"swf"),
    RTF("rtf", ExtensionTypeEnum.WORD, IconTypeEnum.TXT,"swf"),
//    HTML("html",ExtensionTypeEnum.WORD,IconTypeEnum.WORD,"swf"),


    GIF("gif", ExtensionTypeEnum.JPG, IconTypeEnum.IMG,"jpg", false),
    //jpg文件不需要文件系统转换
    JPG("jpg", ExtensionTypeEnum.JPG, IconTypeEnum.IMG,"jpg", false),
    JPEG("jpeg", ExtensionTypeEnum.JPG, IconTypeEnum.IMG,"jpg", false),
    PNG("png", ExtensionTypeEnum.JPG, IconTypeEnum.IMG,"jpg", false),
    BMP("bmp", ExtensionTypeEnum.JPG, IconTypeEnum.IMG,"jpg", false),


    MP3("mp3", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3", false),
    AMR("amr", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    MMF("mmf", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    AIF("aif", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    AU("au", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    RAM("ram", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    WAV("wav", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    WMA("wma", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    OGG("ogg", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    APE("ape", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    ACC("acc", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    AAC("aac", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    FLAC("flac", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    VQF("vqf", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),
    MID("mid", ExtensionTypeEnum.VOICE, IconTypeEnum.AUDIO,"mp3"),


    MP4("mp4", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    THREEGP("3gp", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    MKV("mkv", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    MOV("mov", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    WMV("wmv", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    NAVI("navi", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    MPG("mpg", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    AVI("avi", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    MPEG("mpeg", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    RMVB("rmvb", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    RM("rm", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    ASF("asf", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv"),
    FLV("flv", ExtensionTypeEnum.VIDEO, IconTypeEnum.VIDEO,"flv", false),

    SWF("swf", ExtensionTypeEnum.SWF, IconTypeEnum.SWF,"swf",false);


    private String extension;
    private ExtensionTypeEnum extensionTypeEnum;
    private IconTypeEnum iconTypeEnum;
    private String prefix;
    private Boolean needExchange;


    private static HashMap<String, ExtensionTypeEnum> mapper = new HashMap<>();
    private static HashMap<String, IconTypeEnum> iconMapper = new HashMap<>();
    private static HashMap<String, String> preMapper = new HashMap<>();
    private static HashMap<String, Boolean> exMapper = new HashMap<>();

    static {
        for (ExtensionTypeMapperEnum etm : EnumSet.allOf(ExtensionTypeMapperEnum.class)) {
            mapper.put(etm.extension, etm.extensionTypeEnum);
            iconMapper.put(etm.extension, etm.iconTypeEnum);
            preMapper.put(etm.extension,etm.prefix);
            exMapper.put(etm.extension, etm.needExchange);
        }
    }

    public static ExtensionTypeEnum getType(String extension) {
        if(extension==null){
            return ExtensionTypeEnum.OTHER;
        }
        extension=extension.toLowerCase();
        return mapper.get(extension) != null ? mapper.get(extension) : ExtensionTypeEnum.OTHER;
    }

    public static IconTypeEnum getIcon(String extension) {
        if(extension==null){
            return null;
        }
        extension=extension.toLowerCase();
        return iconMapper.get(extension);
    }

    public static String getPrefix(String extension){
        if(extension==null){
            return null;
        }
        extension=extension.toLowerCase();
        return preMapper.get(extension);
    }

    public static Boolean needExchange(String extension) {
        if(extension==null){
            return null;
        }
        extension=extension.toLowerCase();
        return exMapper.get(extension);
    }

    ExtensionTypeMapperEnum(String extension, ExtensionTypeEnum extensionTypeEnum, IconTypeEnum iconTypeEnum, String prefix) {
        this.extension = extension;
        this.extensionTypeEnum = extensionTypeEnum;
        this.iconTypeEnum = iconTypeEnum;
        this.prefix = prefix;
        this.needExchange = true;   //是否需要文件系统转换  默认为true
    }

    ExtensionTypeMapperEnum(String extension, ExtensionTypeEnum extensionTypeEnum, IconTypeEnum iconTypeEnum, String prefix, Boolean needExchange) {
        this.extension = extension;
        this.extensionTypeEnum = extensionTypeEnum;
        this.iconTypeEnum = iconTypeEnum;
        this.prefix = prefix;
        this.needExchange = needExchange;
    }
}
