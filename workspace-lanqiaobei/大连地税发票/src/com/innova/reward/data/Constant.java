package com.innova.reward.data;

/**
 * 放一些常量
 * 
 * @author guo
 * 
 */
public interface Constant {
	// 爬取查询发票信息
	String HOST = "http://59.46.135.250:8004/dl-taxBsptWeb/";
	String COOKIE = "Cookie";
	String POST_ADDR = HOST + "WA2120Action.do?method=doSelectZjfp";
	String MAIN_ADDR = HOST + "WA2120Action.do?method=doInit";
	String CODE_ADDR = HOST
			+ "ValidImageServlet.validImage?sessionKey=YZMWA212001&t=Wed%20Mar%2011%2020:45:07%20UTC+0800%202015";
	// 发票中奖情况查询的结果
	String RES_MONEY = "摇奖期";
	String RES_YZM_WRONG = "验证码输入错误";
	String RES_FP_NOT_IN_POOL = "未曾进入奖池";
	String RES_FP_IN_NO_MONEY = "未曾中奖";
	// 发票数据库
	String FP_DM = "fpdm";
	String FP_HM = "fphm";
	String DB_NAME = "fp_db.db";
	String TABLE_NAME_FP = "fp_info";
	String[] TITLES = { "消息通知", "数据管理", "个人资料" };
	// 程序中用到的判断量
	String FIRST_USE = "first_use";
	String IS_LOGINED = "is_logined";
	String IS_HAVE_NEW_FP_RECORD = "is_have_new_fp_record";
	String IS_OPEN_RECEIVE_SYSTEM_MSG = "is_open_receive_system_msg";
	String iS_OPEN_QUERY_REWARD_REMIND = "is_open_query_reward_remind";
	String IS_TO_REWARD = "is_to_reward";
	// 短信验证
	String APPKEY = "6d89aa474f63";// 填写从短信SDK应用后台注册得到的APPKEY
	String APPSECRET = "ee9be13158c06774922edc7764c26e42";// 填写从短信SDK应用后台注册得到的APPSECRET
	String AVATARS = "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg";// 短信注册，随机产生头像
	// 自己的服务器
	// String BASE_URL = "http://192.168.137.72:8080/fpService/";
	String BASE_URL = "http://218.24.169.32:8001/fpService/";

	String DEFAULT_LANGUAGE = "eng";

	String SUCCESS = "success";
	String DUPLICATE = "duplicate";
	String EXCEPTION = "exception";
	String FAIL ="fail";
	String NO_THIS_ID ="no_this_id";
	String PWD_WRONG ="pwd_wrong";
}
