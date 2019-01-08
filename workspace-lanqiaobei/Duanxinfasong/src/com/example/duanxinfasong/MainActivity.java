package com.example.duanxinfasong;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText mNumber;
	private EditText mContent;
	private Button mBtSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNumber = (EditText) findViewById(R.id.number);
		mContent = (EditText) findViewById(R.id.content);
		mBtSend = (Button) findViewById(R.id.button);
		mBtSend.setOnClickListener(new ButtonClickListener());
	}

	/* 内部类 不需要继承 所以用final来终止它 */
	private final class ButtonClickListener implements View.OnClickListener {
		@Override//添加了未实现的方法
		public void onClick(View v) {
			//获得短信号码和短信内容
			String number=mNumber.getText().toString();
			String content=mContent.getText().toString();
			//用到smsManager类，先得到短信管理器类的对象用getdefault方法
		    SmsManager manager=SmsManager.getDefault();
		    //对短信进行拆分
            ArrayList<String> texts=manager.divideMessage(content);
            //对这个集合进行迭代   然后逐条发送
            for(String a:texts){
            	//采用增强for循环
            	//发送短信用sendTextMessage方法
    		    //参数分别为目的地址、短信中心的地址（null就是默认使用手机使用的比如移动网络）、短信内容、发送的状态（可以知道短信有没有成功发送）、得到对方是否收到短信。后两个需要移动网络返回电信号来得知
    		    manager.sendTextMessage(number, null, a, null, null);
            }
            /*提示用户发送成功（三种方式status bar/Dialog/Toast）*/
            //getApplicationContext或者MainActivity.this
		    Toast.makeText(getApplicationContext(),R.string.success,Toast.LENGTH_SHORT).show();
		}
	}

}
