package com.feng.demo.qrcodedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mTvResult;//解析二维码之后显示的文字

    private EditText mEtInput;//生成二维码所需要的文字
    private ImageView mIvQRCode;
    private CheckBox mCb_addLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mEtInput = (EditText) findViewById(R.id.et_content);
        mIvQRCode = (ImageView) findViewById(R.id.iv_qrCode);
        mCb_addLogo = (CheckBox) findViewById(R.id.cb_addLogo);
    }

    public void scan(View view) {//扫描二维码的功能
        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String string = bundle.getString("result");//扫描的二维码可以是一串字符串，也可以是一个url地址
            mTvResult.setText(string);
        }
    }

    public void click(View view) {
        String input = mEtInput.getText().toString();//先获取一串文字
        if (input.equals("")) {
            Toast.makeText(MainActivity.this, "input is empty", Toast.LENGTH_SHORT).show();
        } else {
            Bitmap qRCodeBitmap = EncodingUtils.createQRCode(input, 500, 500, mCb_addLogo.isChecked() ? BitmapFactory.decodeResource(getResources(), R.mipmap.girl) : null);//将所要转的文字和想要的大小和logo设置好
            mIvQRCode.setImageBitmap(qRCodeBitmap);//将生成的bitmap显示在界面上
        }
    }
}
