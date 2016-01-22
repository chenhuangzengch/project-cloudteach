package net.xuele.cloudteach.service.util;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2015/10/21 0021.
 */
public class FileZipUtil {
	/**
	 * 压缩
	 * @param inputFile file
	 * @param zipFileName c:/ziptest.zip
	 * @throws Exception
	 */
	public static void zip(File inputFile, String zipFileName) throws Exception {
		ZipOutputStream zOut=null;
		try {
			//创建文件输出对象out,提示:注意中文支持
			FileOutputStream out = new FileOutputStream(new String(zipFileName.getBytes("UTF-8")));
			//將文件輸出ZIP输出流接起来
			zOut = new ZipOutputStream(out);
			zip(zOut, inputFile, "");
			zOut.close();
		} catch (Exception e) {
			throw e;
		}finally {
			if (zOut!=null)zOut.close();
		}
	}


	public static void zip(ZipOutputStream zOut, File file, String base) throws Exception {
		try {
			// 如果文件句柄是目录
			if (file.isDirectory()) {
				//获取目录下的文件
				File[] listFiles = file.listFiles();
				// 建立ZIP条目
				zOut.putNextEntry(new ZipEntry(base + "/"));
				base =( base.length() == 0 ? "" : base + "/" );

				// 遍历目录下文件
				for (int i = 0; i < listFiles.length; i++) {
					// 递归进入本方法
					zip(zOut, listFiles[i], base + listFiles[i].getName());
				}
			}
			// 如果文件句柄是文件
			else {
				if (base == "") {
					base = file.getName();
				}
				// 填入文件句柄
				zOut.putNextEntry(new ZipEntry(base));
				// 开始压缩
				// 从文件入流读,写入ZIP 出流
				writeFile(zOut,file);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public static void writeFile(ZipOutputStream zOut,File file) throws Exception {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			int len;
			while ((len = in.read()) != -1)
				zOut.write(len);
			in.close();
		} catch (Exception e) {
			throw e;
		}

	}

	public static void main(String[] args) {
		/*String zipFilePath = "F:/testZip/Demo7.zip";
		File[] files = {new File("F:/testZip/applicationContext-cas.xml"), new File("F:/testZip/applicationContext-controller.xml"),
//				new File("F:/testZip/students-icons")};
//				new File("F:/testZip/students-icons/11.jpg"), new File("F:/testZip/students-icons/22.jpg"), new File("F:/testZip/students-icons/33.jpg")};
				new File("F:/testZip/11.jpg"), new File("F:/testZip/22.jpg"), new File("F:/testZip/33.jpg")};
		try {
			compressFiles2Zip(files, zipFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * 把文件压缩成zip格式
	 *
	 * @param files       需要压缩的文件
	 * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip";
	 */
	public static void compressFiles2Zip(File[] files, String zipFilePath) throws Exception {
		if (files == null || files.length == 0) return;
		if (!isEndsWithZip(zipFilePath)) return;
		ZipArchiveOutputStream zaos = null;
		try {
			final OutputStream outFile = new FileOutputStream(zipFilePath);
			zaos = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, outFile);
			ZipArchiveEntry ze;
			for (File file : files) {
				if (!file.exists()) continue;
				ze = new ZipArchiveEntry(getEntryName(file, zipFilePath));//获取每个文件相对路径,作为在ZIP中路径
				zaos.putArchiveEntry(ze);
				//folder
				if (ze.isDirectory()) {//TODO 目录需要递归处理,根据业务做适当扩展
					zaos.closeArchiveEntry();
					continue;
				}
				//file
				FileInputStream fis = new FileInputStream(file);
				IOUtils.copy(fis, zaos);
				fis.close();
				zaos.closeArchiveEntry();
			}
			zaos.finish();
		} finally {
			if (zaos != null) zaos.close();
		}
	}

	/**
	 * 判断文件名是否以.zip为后缀
	 *
	 * @param fileName 需要判断的文件名
	 * @return 是zip文件返回true, 否则返回false
	 */
	public static boolean isEndsWithZip(String fileName) {
		boolean flag = false;
		if (StringUtils.isNoneBlank(fileName)) {
			if (fileName.endsWith(".ZIP") || fileName.endsWith(".zip")) {
				flag = true;
			}
		}
		return flag;
	}

	public static String getEntryName(File file, String rootPath) {
		String entryName;
		String fPath = file.getAbsolutePath();
		if (fPath.contains(rootPath)) {
			entryName = fPath.substring(rootPath.length() + 1);
		} else {
			entryName = file.getName();
		}
		if (file.isDirectory()) entryName += "/";//后缀表示entry是文件夹
		return entryName;
	}
	/*public static void compressDir(File dir, ZipOutputStream zos,
	                                String basePath) throws Exception {

		File[] files = dir.listFiles();

		// 构建空目录
		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);

			zos.putNextEntry(entry);
			zos.closeEntry();
		}

		for (File file : files) {

			// 递归压缩
			compress(file, zos, basePath + dir.getName() + PATH);

		}
	}*/


}
