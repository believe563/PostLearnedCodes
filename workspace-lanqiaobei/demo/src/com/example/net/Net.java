package com.example.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.demo.MainActivity;

public class Net {
	private static HttpResponse response;
	private static HttpGet get;
	private static HttpPost post;
	private static HttpClient client = new DefaultHttpClient();
	private static final String postAddr = "http://59.46.135.250:8004/dl-taxBsptWeb/WA2120Action.do?method=doSelectZjfp";
	private static final String mainAddr = "http://59.46.135.250:8004/dl-taxBsptWeb/WA2120Action.do?method=doInit";
	private static String codeAddr = "http://59.46.135.250:8004/dl-taxBsptWeb/ValidImageServlet.validImage?sessionKey=YZMWA212001&t=Wed%20Mar%2011%2020:45:07%20UTC+0800%202015";
	public static String cookie;

	public static byte[] getCodeByte() {
		try {
			get = new HttpGet(codeAddr);
			if (cookie == null) {
				getCookie();
			}
			get.setHeader("Cookie", cookie);
			MainActivity.sp.edit().putString("cookie", cookie).commit();
			response = client.execute(get);

			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] bs = EntityUtils.toByteArray(response.getEntity());
				return bs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void getCookie() {
		try {
			get = new HttpGet(mainAddr);
			response = client.execute(get);
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				cookie = response.getFirstHeader("Set-Cookie").getValue();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getResult(String fpdm, String fphm, String yzm) {
		// post的数据
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("CLEAR", "清空"));
		params.add(new BasicNameValuePair("fpdm", fpdm));
		params.add(new BasicNameValuePair("fphm", fphm));
		params.add(new BasicNameValuePair("QUERY", "查询"));
		params.add(new BasicNameValuePair("yzm", yzm));
		// post请求
		post = new HttpPost(postAddr);
		// 禁止自动处理重定向
		post.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
		// 设置头信息
		post.setHeader("__request_type", "AJAX_REQUEST");
		post.setHeader("Cookie", cookie);
		// 设置post数据的编码
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "gbk"));

			// 执行，拿到响应
			response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			System.out.println(200);
			if (status == 200) {
				String jsonStr = EntityUtils.toString(response.getEntity());
				return jsonStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// post.abort();
		}
		return null;
	}
}
