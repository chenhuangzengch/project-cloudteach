package net.xuele.cloudteach.service.util;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 单例实现jar包文件拷贝
 * @author sunxh
 * @date 15/10/28
 */
public class JarFileUtil {
    private static final JarFileUtil jarFileUtil=new JarFileUtil();
    private JarFileUtil(){}//避免实例化

    public static final JarFileUtil getInstance(){
        return jarFileUtil;
    }
    /** 流缓存大小 */
    private final static int BUFFER_SIZE = 1024;
    /** JAR包相对路径前缀*/
    private static final String PATH_SPLIT="/";

    /**
     * 从jar文件中拷贝目录到目标目录
     * @param srcFolderName
     * @param desBasePath
     * @throws Exception
     */
    public void copyFolderFromJar(String srcFolderName,String desBasePath) throws Exception {
        String jarPath=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(jarPath);
        if (jarFile.isFile()){
            doCopyFolderFromJar(srcFolderName, desBasePath, jarFile);
        }else {//IDE拷贝
            String srcPath=jarPath+srcFolderName;
            FileUtil.copyFolder(srcPath, desBasePath);
        }
    }

    /**
     * jar包拷贝
     * @param srcFolderName
     * @param desBasePath
     * @param jarFile
     * @throws Exception
     */
    private void doCopyFolderFromJar(String srcFolderName, String desBasePath, File jarFile) throws Exception {
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
        if(null==entries) return;
        int baseFolderLen=srcFolderName.length();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry=entries.nextElement();
            String jarEntryName =jarEntry.getName();
            if(!jarEntryName.contains(srcFolderName))continue;
            if (jarEntry.isDirectory()){//目录
                String childFolder=jarEntryName.substring(baseFolderLen);
                if(1==childFolder.length())continue;//根目录
                String tempFolderPath=desBasePath+childFolder;//创建对应的临时目录
                FileUtil.mkdir(tempFolderPath);
            }else {
                String filePath=PATH_SPLIT+jarEntryName;//相对路径
                InputStream inputStream = this.getClass().getResourceAsStream(filePath);
                if (null==inputStream)continue;
                String childFile=jarEntryName.substring(baseFolderLen);
                String tempFilePath=desBasePath+childFile;
                File tempFile = new File(tempFilePath);
                BufferedOutputStream outStream = null;
                try {
                    outStream = new BufferedOutputStream(new FileOutputStream(tempFile));
                    byte[] b = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = inputStream.read(b)) != -1) {
                        outStream.write(b, 0, len);
                    }
                    inputStream.close();
                    outStream.close();
                } catch (Exception e) {
                    throw new Exception(e);
                }finally {
                    if(null!=inputStream)inputStream.close();
                    if (null!=outStream)outStream.close();
                }
            }
        }
        jar.close();
    }

}
