package com.innova.reward.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.innova.reward.data.Constant;

/**
 * 涉及到网络的一个操作类
 * 
 * @author guo
 * 
 */
public class NetUtil {
	private static HttpGet get;
	private static HttpPost post;
	private static HttpClient client;
	private static HttpResponse response;
	private static BasicHttpParams httpParams;

	static {
		httpParams = new BasicHttpParams();
		// 设置请求超时20秒钟
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
		// 设置等待数据超时时间35秒钟,防止传回数据量过大
		HttpConnectionParams.setSoTimeout(httpParams, 35000);
		client = new DefaultHttpClient(httpParams);
	}

	/**
	 * * 发起httpGet请求
	 * 
	 * @param params
	 *            要发送的参数，<key,value>
	 * @param params
	 *            要给get配置的header参数，<key,value>
	 * @param isString
	 *            要得到的是否是字符串
	 * @return 状态码
	 */
	public static int getRequest(Map<String, String> params, Map<String, String> headerParams, String _url) {
		StringBuilder url = new StringBuilder(_url);
		url = joinUrl(params, url);
		try {
			get = new HttpGet(url.toString());
			configHeaderGet(headerParams);
			response = client.execute(get);
			return response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			String eString = e.getMessage();
			System.out.println(eString);
			CommonUtil.log("NetUtil getRequest() get请求出错");
		}
		return -1;
	}

	/**
	 * 拼接url
	 * 
	 * @param params
	 *            Map的参数
	 * @param url
	 *            原始url
	 * @return 拼接好参数的url
	 */
	private static StringBuilder joinUrl(Map<String, String> params, StringBuilder url) {
		if (params != null) {
			url.append("?");
			for (String key : params.keySet()) {
				url.append(key).append("=").append(params.get(key)).append("&");
			}
		}
		return url;
	}

	/**
	 * 配置get携带的头参数
	 * 
	 * @param headerParams
	 *            Map的参数对
	 */
	private static void configHeaderGet(Map<String, String> headerParams) {
		if (headerParams != null) {
			for (String key : headerParams.keySet()) {
				get.setHeader(key, headerParams.get(key));
			}
		}
	}

	/**
	 * 发起post请求
	 * 
	 * @param params
	 *            所post参数
	 * @param headerParams
	 *            头信息
	 * @return 返回的html
	 * @throws Exception
	 */
	/**
	 * @param params
	 * @param headerParams
	 */
	public static String postRequest(Map<String, String> params, Map<String, String> headerParams, String charset) {
		post = new HttpPost(Constant.POST_ADDR);
		configPost(params, headerParams, charset);
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 配置post的头参数
	 * 
	 * @param params
	 */
	private static void configPost(Map<String, String> params, Map<String, String> headerParams, String charset) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			list.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(list, charset));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String key : headerParams.keySet()) {
			post.setHeader(key, headerParams.get(key));
		}
	}

	/**
	 * 获取验证码的字节流
	 * 
	 * @return 验证码的字节流
	 */
	public static byte[] getCodeByte() {
		try {
			String cookie = CommonUtil.sp.getString(Constant.COOKIE, null);
			if (cookie == null) {
				cookie = getCookie();
				CommonUtil.sp.edit().putString(Constant.COOKIE, cookie).commit();
			}

			Map<String, String> headerParams = new HashMap<String, String>();
			headerParams.put(Constant.COOKIE, cookie);

			int statusCode = getRequest(null, headerParams, Constant.CODE_ADDR);
			if (statusCode == HttpStatus.SC_OK) {
				return EntityUtils.toByteArray(response.getEntity());
			}
		} catch (Exception e) {
			CommonUtil.log("NetUtil.getCodeByte() 获取验证码字节流失败");
		}
		return null;
	}

	/**
	 * 得到验证码
	 * 
	 * @return 验证码的字符串
	 */
	public static String getCodeStr() {
		String code = null;
		Bitmap bitmap = null;
		while (bitmap == null) {
			byte[] bs = getCodeByte();
			if (bs != null) {
				bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
				if (bitmap != null) {
					code = DistinguishCode.codeToString(bitmap);
				}
			}
		}
		return code;
	}

	/**
	 * 获取cookie
	 * 
	 * @return cookie
	 */
	public static String getCookie() {
		try {
			int status = getRequest(null, null, Constant.MAIN_ADDR);
			if (status == HttpStatus.SC_OK) {
				return response.getFirstHeader("Set-Cookie").getValue();
			}
		} catch (Exception e) {
			CommonUtil.log("NetUtil.getCookie() 获得cookie失败");
		}
		return null;
	}

	/**
	 * 得到发票查询结果
	 * 
	 * @param fpdm
	 *            发票代码
	 * @param fphm
	 *            发票号码
	 * @return 查询结果
	 */
	public static String getQueryResult(String fpdm, String fphm) {
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("CLEAR", "清空");
		postParams.put("fpdm", fpdm);
		postParams.put("fphm", fphm);
		postParams.put("QUERY", "查询");
		postParams.put("yzm", getCodeStr());

		Map<String, String> headerParams = new HashMap<String, String>();
		headerParams.put("__request_type", "AJAX_REQUEST");
		headerParams.put("Cookie", CommonUtil.sp.getString(Constant.COOKIE, null));
		return postRequest(postParams, headerParams, "gbk");
	}

	/**
	 * @return 是否联网网络-->true联网;false没联网
	 */
	public static boolean IsHaveInternet() {
		NetworkInfo info = ((ConnectivityManager) MyAppli.getContext().getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}
}
