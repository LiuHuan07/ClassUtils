package com.android.liuhuan.utilsclass.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

/**
 * 
 * 扩展卡种文件操作的工具类： 以缓存图片为例： 目录：mnt/sdcard
 */
/**
 * @author Administrator
 *
 */

public class StorageUtil {

	// 存储图片的目录
	// Environment.getExternalStorageDirectory() :获取外部存储空间的目录
	public static String IMAGE_URL = ""+Environment.getExternalStorageDirectory();
	public StorageUtil(String dirName){
		IMAGE_URL=IMAGE_URL+dirName;
	}

	/**
	 *	判断扩展卡是否挂载
	 * 
	 * @return
	 */
	public static boolean isMounted() {
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 判断扩展卡的剩余空间够不够用
	 */
	public static boolean  isAble()
	{
		//文件系统状态管理对象
		StatFs fs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
		int count = fs.getFreeBlocks();//空闲的数据块个数
		int size =  fs.getBlockSize();//返回每个数据块的大小
		
		//剩余空间大小
		long  total = count*size;//单位是字节
		int  t = (int) (total/1024/1024);
		if(t>2)
			return true;
		else
			return false;
	}
	

	/**
	 * 保存图片到扩展卡的功能
	 * 
	 * @throws IOException
	 */
	public static void saveImage(String url, byte[] data) throws IOException { // �ж���չ���Ƿ����
		if (!isMounted())
			return;
		// 判断存储目录是否存在
		File dir = new File(IMAGE_URL);
		if (!dir.exists())
			dir.mkdirs();
		// 把图片数据写入到一个图片文件
		FileOutputStream fos = new FileOutputStream(new File(dir,
				getFileName(url)));
		fos.write(data);

		fos.close();

	}

	/**
	 * 保存图片到扩展卡的功能
	 * 
	 * @throws FileNotFoundException
	 */
	public static void saveImage(String url, Bitmap bitmap)
			throws FileNotFoundException {
		if (!isMounted())
			return;
		File dir = new File(IMAGE_URL);
		if (!dir.exists())
			dir.mkdirs();
		// 把图片数据写入到一个图片文件
		FileOutputStream fos = new FileOutputStream(new File(dir,
				getFileName(url)));

		// 图片的压缩 CompressFormat.PNG:压缩之后的格式
		bitmap.compress(getFormat(url),100, fos);
	}
	/**
	 * 获取文件的格式
	 *
	 */
	private static CompressFormat getFormat(String url) {
		// TODO 获取图片的格式
		String fileName=getFileName(url);
		if(fileName.endsWith("png")){
			return CompressFormat.PNG;
		}
		
		return CompressFormat.JPEG;
	}


	/**
	 * 从扩展卡读取图片的功能
	 */
	public static Bitmap readImage(String url) {
		if (!isMounted())
			return null;

		String filename = getFileName(url);
		File file = new File(IMAGE_URL, filename);
		Bitmap bitmap = null;
        if(file.exists())
        	bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		return bitmap;
	}

	/**
	 * 清空扩展卡 缓存目录中的内容的功能
	 */
	public static void clear() {
		if (!isMounted())
			return;
		File dir = new File(IMAGE_URL);
		if (dir.exists()) {
              File[] arr = dir.listFiles();
              for(File f:arr)
              {
            	  f.delete();
              }
		}
	}

	/**
	 * 根据文件的下载路径获取文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}
	public String getName(String url,int end){
		return url.substring(url.lastIndexOf("/")+1,end);
	}

}
