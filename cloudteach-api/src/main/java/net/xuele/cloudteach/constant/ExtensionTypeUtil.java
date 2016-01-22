package net.xuele.cloudteach.constant;

/**
 * ExtensionTypeUtil
 *
 * @author sunxh
 * @date 2015/7/4 0004
 */
public class ExtensionTypeUtil {

    private static ExtensionTypeUtil instance = new ExtensionTypeUtil();

    private ExtensionTypeUtil() {
    }

    public static ExtensionTypeUtil getInstance() {
        return instance;
    }

    /**
     * 获取类型代码
     *
     * @param extension
     * @return
     */
    public Integer getExtTypeCode(String extension) {
        return ExtensionTypeMapperEnum.getType(extension.toLowerCase()).getExtCode();
    }

    /**
     * 获取类型
     *
     * @param extension
     * @return
     */
    public String getExtType(String extension) {
        return ExtensionTypeMapperEnum.getType(extension.toLowerCase()).getExtString();
    }

    /**
     * 根据文件信息返回url
     *
     * @param extension
     * @param fileKey
     * @param operation 1 下载，2 预览，关联预览
     * @param size      缩略图尺寸
     * @return
     */
    public String getUrl(String extension, String fileKey, DiskFileOperationEnum operation, String size) {
        //扩展名小写
        extension = extension.toLowerCase();
        String url = "/";


        String getPrefix = ExtensionTypeMapperEnum.getPrefix(extension);

        //判断是否图片
        ExtensionTypeEnum extensionTypeEnum = ExtensionTypeMapperEnum.getType(extension);
        Boolean isPicture = (extensionTypeEnum.getExtCode() == ExtensionTypeEnum.JPG.getExtCode());

        //顶层目录，images使用单独的目录
        if (isPicture) {
            url += "images/";
        } else {
            url += "files/";
        }

        //操作类型
        switch (operation) {
            case DOWNLOAD:
                url += "dl/";
                break;
            case VIEW:
                break;
            case RELATEDVIEW:
                //前缀为空时不处理
                if (getPrefix == null) break;
                url += getPrefix;
                url += "_";
                break;
            default:
                //默认为下载
                url += "dl/";
        }

        //是否缩略图预览
        if (isPicture && null != size && "" != size) {
            url += (size + "_");
        }

        //文件名和扩展名
        url += fileKey;
        url += ("." + extension);

        return url;
    }

    /**
     * 取url 不带缩略图大小的情况
     *
     * @param extension
     * @param fileKey
     * @param operation
     * @return
     */
    public String getUrl(String extension, String fileKey, DiskFileOperationEnum operation) {
        return getUrl(extension, fileKey, operation, null);
    }

    /**
     * 取icon名称
     * @param extension
     * @return
     */
    public String getIcon(String extension){
        IconTypeEnum iconTypeEnum = ExtensionTypeMapperEnum.getIcon(extension.toLowerCase());
        return iconTypeEnum!=null?iconTypeEnum.getExtString():"";
    }

    public static void main(String[] args) {
        ExtensionTypeUtil extensionTypeUtil = ExtensionTypeUtil.getInstance();
        System.out.println(extensionTypeUtil.getUrl("doc", "fileKeyDemo", DiskFileOperationEnum.DOWNLOAD));
        System.out.println(extensionTypeUtil.getUrl("jpg", "fileKeyDemo", DiskFileOperationEnum.RELATEDVIEW, "1000x1000"));

    }
}
