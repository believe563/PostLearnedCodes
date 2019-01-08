package com.example.registerpageframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class Registe_Passw extends Activity{

	private ImageButton mReturnButton;
	private Button mNextButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_password);
		
		mReturnButton=(ImageButton) findViewById(R.id.ib_return2);
		mNextButton=(Button) findViewById(R.id.ib_next2);
		
		mReturnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Registe_Passw.this, Registe_Checkcode.class);
				startActivity(intent);
			}
		});
		
		
	}
}
