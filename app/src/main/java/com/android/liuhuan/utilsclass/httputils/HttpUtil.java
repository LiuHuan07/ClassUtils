package com.android.liuhuan.utilsclass.httputils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
	
	public static InputStream sendByPost(String path,HashMap<String,String> params,String encode) throws ClientProtocolException, IOException
	{
		//把要提交的数据封装成NameValuePair类型的
		List<NameValuePair> list =new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> en:params.entrySet())
		{
			list.add(new BasicNameValuePair(en.getKey(),en.getValue()));
		}
		//把要提交的数据组织成username=kk&psw=111格式
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
		//创建提交方式对象
		HttpPost post = new HttpPost(path);
		post.setEntity(entity);//设置提交的数据
		//创建执行提交的对象
		HttpClient client = new DefaultHttpClient();
		//执行提交
		HttpResponse response = client.execute(post);
		//判断是否提交成功
		InputStream in = null;
		if(response.getStatusLine().getStatusCode()==200)
		{
			//读取服务器端返回的验证结果
			in = response.getEntity().getContent();
			return in;
		}
		return null;
	}

	private static byte[] getResult(InputStream in) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] arr= new byte[1024];
		int len = 0;
		while((len = in.read(arr))!=-1)
		{
			bos.write(arr,0,len);
		}
		return bos.toByteArray();
	}
	
	public static InputStream getInputStreamByHttpGet(String path) throws ClientProtocolException, IOException
	{
		HttpGet get = new HttpGet(path);
		
		HttpClient client = new DefaultHttpClient();
		
		HttpResponse response = client.execute(get);
		
		InputStream in = null;
		if(response.getStatusLine().getStatusCode()==200)
		{
			in = response.getEntity().getContent();
		}
		return in;
	}

	public static InputStream getInputStreamByHttpUrl(String path) throws IOException
	{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		conn.setDoInput(true);
		
		conn.connect();
		InputStream in = null;
		if(conn.getResponseCode()==200)
		{
			in = conn.getInputStream();
		}
		return in;
	}

}








