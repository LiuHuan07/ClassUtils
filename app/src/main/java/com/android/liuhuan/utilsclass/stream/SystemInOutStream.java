package com.android.liuhuan.utilsclass.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemInOutStream {
	public void getSysInput() throws IOException {
		// 创建标准输入流
		InputStream is = System.in;

		// int num = is.read();// 阻塞式方法，只要没有输入数据，会一直等待
		//
		// System.out.println((char) num);

		int num = 0;
		StringBuilder sb = new StringBuilder();
		// 循环读取键盘输入的数据,输入over退出
		while (true) {
			num = is.read();
			if (num == '\r')
				continue;
			else if (num == '\n') {
				if ("over".equals(sb.toString()))
					break;
				System.out.println(sb.toString());
				sb.delete(0, sb.length());
			} else {
				sb.append((char) num);
			}
		}
	}
	public static void sysOutput() throws IOException {
		boolean flag = false;
		String name = "";
		String content = "";
		// 准备流
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/chat.txt"));
		while (true) {
			// 确定姓名
			name = flag ? "张三" : "李四";
			String str = name + "  " + getTime();
			System.out.println(str);
			bw.write(str);
			bw.newLine();
			content = br.readLine();
			// 保存聊天记录
			bw.write(content);
			bw.newLine();
			bw.flush();
			flag = !flag;

			if ("88".equals(content)) {
				break;
			}

		}
		// 关闭流
		br.close();
		bw.close();
	}

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		String time = sdf.format(new Date());
		return time;
	}
}
