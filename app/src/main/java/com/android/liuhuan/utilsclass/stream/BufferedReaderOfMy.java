package com.android.liuhuan.utilsclass.stream;

import java.io.IOException;
import java.io.Reader;

public class BufferedReaderOfMy {
	private Reader r; // 真正读取功能的类
	private char[] arr = new char[512];// 相当于缓冲区
	private int index; // 数组下标
	private int count; // 统计缓冲区中字符个数

	public BufferedReaderOfMy(Reader r) {
		this.r = r;
	}

	// 实现一次读取一个的功能
	public int myRead() throws IOException {
		// 缓冲区中是否有数据
		if (count == 0) {
			// 从文件中读取数据到缓冲区，返回值读取的字符数
			count = r.read(arr);
			index = 0; // 下标为0
		}
		if (count < 0) // 文件末尾
			return -1;
		// 从缓冲区中读取一个字符
		int num = arr[index];
		index++;// 下标+1
		// 数量-1
		count--;
		return num;

	}

	// 一次读取一行
	public String myReadLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		int num;
		while ((num = myRead()) != -1) {
			if (num == '\r')
				continue;
			else if (num == '\n')
				return sb.toString();
			else
				sb.append((char) num);
		}
		return null;
	}

	// 关闭流
	public void myClose() throws IOException {
		r.close();
	}
}
