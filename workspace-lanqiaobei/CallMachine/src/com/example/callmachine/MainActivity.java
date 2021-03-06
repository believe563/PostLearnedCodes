package com.example.callmachine;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText etCall;
	private Button btCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一步  找到按钮
        btCall=(Button) findViewById(R.id.btCall);
        etCall=(EditText) findViewById(R.id.etPhone);
        btCall.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String mobile=etCall.getText().toString();
				//激活手机上自带的拨打电话功能的activity
				Intent intent=new Intent();
				//指定动作名称（按照activity中的intent写）
				intent.setAction("android.intent.action.CALL");
				//把intent中的类别加进去
//				intent.addCategory("android.intent.category.DEFAULT");
				//把intent中的数据放进去，数据就是传给对方的电话号码
				intent.setData(Uri.parse("tel:"+mobile));//把 一个字符串转换为uri，schema（模式）为tel：，后面加上要传给对方的号码
				//接下来把intent传给系统  系统来寻找activity的这种类型的主键，寻找主键的时候回根据意图提供的参数来匹配系统中意图过滤器，只要有一个意图过滤器跟它匹配就可以激活该activity
				//通过意图激活activity这种类型的主键
				startActivity(intent);//系统内部会自动为intent添加android.intent.category.DEFAULT这个类别，所以上面加类别的那个代码可以不要
				//然后要在清单文件中声明权限
				
				
			}
		});
    };
    
}
