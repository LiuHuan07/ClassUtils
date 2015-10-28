package com.android.liuhuan.utilsclass.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * @author Administrator
 *	根据路径创建文件
 */
/**
 * @author Administrator
 *	列出指定目录下的所有文件和文件夹----以字符串数组的形式返回
 */
/**
 * @author Administrator
 *	返回文件目录的个数和文件的个数
 */
/**
 * @author Administrator
 *	列出指定目录下的文件及其文件夹，包括子目录中的内容
 */
/**
 * @author Administrator
 *	删除文件目录及其子目录中的文件
 */
/**
 * @author Administrator
 *	将指定的文件及其子目录下的文件放入集合中
 */

/**
 * @author Administrator
 *	返回指定后缀结尾的文件
 */
/**
 * @author Administrator
 *	写入到.properties文件
 */
/**
 * @author Administrator
 *	读取.properties文件
 */
/**
 * @author Administrator
 *	将一个文件分割为指定大小的文件
 */
public class FileUtil {
	/**
	 * 根据路径创建文件
	 * @param fileName
	 * @return
	 */
	public static File createFile(String fileName) {
		File f = new File(fileName);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f;
	}

	/**
	 * 列出指定目录下的所有文件和文件夹----以字符串数组的形式返回
	 * @param f
	 */
	public static void getFileName(File f) {
		String[] files = f.list();
		for (String s : files) {
			System.out.println(s);
		}
	}
	
	/**
	 * 返回文件目录的个数和文件的个数
	 * @param f
	 */
	public static void getFileNum(File f) {
		if (!f.exists()) {
			System.out.println("文件路径不存在");
			return;
		}
		int fileNum = 0;
		int dirNum = 0;
		File[] arr = f.listFiles();
		for (File file : arr) {
			if (file.isFile())
				fileNum++;
			if (file.isDirectory())
				dirNum++;

		}
		System.out.println("目录的个数：" + dirNum + ",文件的个数：" + fileNum);
	}

	/**
	 * 列出指定目录下的文件及其文件夹，包括子目录中的内容
	 * @param file
	 */
	public static void getFiles(File file) {
		File[] files = file.listFiles(); // 获得当前目录下所有的目录和文件
		for (File f : files) {
			System.out.println(f.getName());
			if (f.isDirectory()) {// 如果是一个目录
				getFiles(f);
			}
		}
	}

	/**
	 * 删除文件目录及其子目录中的文件
	 * @param file
	 * @return
	 */
	public static boolean del(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				del(f);
			}
		}
		return file.delete();
	}

	/**
	 * 将指定的文件及其子目录下的文件放入集合中
	 * @param file
	 * @param list 文件集合
	 * @param postfix 后缀
	 */
	public static void fileToList(File file, List<File> list, String postfix) {
		File[] arr = file.listFiles();
		for (File ff : arr) {
			if (ff.isDirectory()) {
				fileToList(ff, list, postfix);
			} else {
				if (ff.getName().endsWith(postfix)) {
					list.add(ff);
				}
			}
		}

	}
	/**
	 * @param file
	 * @param postfix1 后缀：text/java/png
	 * @return
	 */
	public static File[] listpostFix(File file, final String postfix1) {
		if (!file.isDirectory()) {
			throw new RuntimeException("指定的路径不是有效的目录");
		}
		// 通过匿名内部类实现过滤文件类型的接口
		File[] files = file.listFiles(new FileFilter() {
			// 如果指定的类型文件符合，返回true，否则返回false
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName();
				if (fileName.endsWith(postfix1)) {
					return true;
				}
				return false;
			}
		});
		return files;
	}
	
	public static File[] listImages(File file, final String postfix1,
			final String postfix2) {
		if (!file.isDirectory()) {
			throw new RuntimeException("指定的路径不是有效的目录");
		}
		// 通过匿名内部类实现过滤文件类型的接口
		File[] files = file.listFiles(new FileFilter() {
			// 如果指定的类型文件符合，返回true，否则返回false
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName(); // a.bc.jpg
				// 获得扩展名
				String exName = fileName.substring(fileName.lastIndexOf(".") + 1);
				// 判断扩展名的类型
				if (exName.equals(postfix1) || exName.equals(postfix2)) {
					return true;
				}
				return false;
			}
		});
		return files;
	}
	
	public static File[] listImages(File file, final String postfix1,
			final String postfix2, final String postfix3) {
		if (!file.isDirectory()) {
			throw new RuntimeException("指定的路径不是有效的目录");
		}
		// 通过匿名内部类实现过滤文件类型的接口
		File[] files = file.listFiles(new FileFilter() {
			// 如果指定的类型文件符合，返回true，否则返回false
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName(); // a.bc.jpg
				// 获得扩展名
				String exName = fileName.substring(fileName.lastIndexOf(".") + 1);
				// 判断扩展名的类型
				if (exName.equals(postfix1) || exName.equals(postfix2)
						|| exName.equals(postfix3)) {
					return true;
				}
				return false;
			}
		});
		return files;
	}

	/**
	 * @param file 写入到.properties文件
	 * @param map 键值对
	 * @throws IOException
	 */
	static void writeProperties(File file, Map<String, String> map)
			throws IOException {
		OutputStream os = new FileOutputStream(file);
		// 创建属性对象
		Properties ps = new Properties();
		// 将Map集合中的数据写入到属性对象
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> en : entrySet) {
			ps.setProperty(en.getKey(), en.getValue());
		}

		// 将属性对象写入到指定的流中
		ps.store(os, "保存日期:" + DateFormat.getInstance().format(new Date()));
		// 将属性及属性值显示到打印流中
		ps.list(System.out);

	}

	/**
	 * @param file 读取properties文件
	 * @throws IOException
	 */
	static void readProperties(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// 创建属性对象
		Properties ps = new Properties();
		// 加载流文件中的属性信息
		ps.load(is);

		// 读取属性文件中的信息
		Enumeration names = ps.propertyNames();
		while (names.hasMoreElements()) {
			Object name = names.nextElement();
			Object property = ps.getProperty(name.toString());
			System.out.println(name + "----" + property);
		}
		is.close();

	}

	/**
	 * 将一个文件分割为指定大小的文件
	 * 1)定义一个固定大小的字节数组
	 * 2）目标位置、名称
	 * 3) 循环读取固定大小的字节，然后写到目标位置
	 * @param f  被分割的文件
	 * @param dirPath 分割后文件的路径
	 * @throws IOException 分割后文件名
	 */
	public static void splitFile(File f, String dirPath) throws IOException {
		InputStream is = new FileInputStream(f);
		byte[] bytes = new byte[1024 * 1024];// 10k
		int len = 0;
		int index = 0;
		FileOutputStream fos = null;
		while ((len = is.read(bytes)) != -1) {
			fos = new FileOutputStream(new File(dirPath, (index++)
					+ f.getName()));
			fos.write(bytes, 0, len);
		}
		fos.close();
		is.close();
	}
}
