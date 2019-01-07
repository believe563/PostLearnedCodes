package com.example.fengjianghui.progressbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    private ProgressBar twoProgressBar;
    private Button add;
    private Button reduce;
    private Button reset;
    private TextView textView1;
    private ProgressDialog progressDialog;
    private Button buttonProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //启用窗口特征，启用带进度和不带进度的进度条
        //即标题栏的进度条
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        //显示两种进度条
        setProgressBarVisibility(true);
        setProgressBarIndeterminateVisibility(true);
         //max=10000
        setProgress(9000);//设置刻度
        //一个进度条上显示两个进度
        init();


    }

public void init(){
    twoProgressBar= (ProgressBar)    findViewById(R.id.horiz);
    add= (Button) findViewById(R.id.add);
    reduce= (Button) findViewById(R.id.reduce);
    reset= (Button) findViewById(R.id.reset);
    textView1= (TextView) findViewById(R.id.textView2);

    //显示进度条对话框
    buttonProgressDialog= (Button) findViewById(R.id.buttonDialogProgress);
    buttonProgressDialog.setOnClickListener(this);
    //getProgress
    //获取第一进度条的进度
    int first=twoProgressBar.getProgress();
    //获取第二进度条的进度
    int second=twoProgressBar.getSecondaryProgress();
    //获取进度条的最大进度
    int max=twoProgressBar.getMax();
    textView1.setText("第一进度条百分比："+(int)(first/(float)max*100)+"%,第二进度条百分比："+(int)(second/(float)max*100)+"%");
    add.setOnClickListener(this);
    reduce.setOnClickListener(this);
    reset.setOnClickListener(this);
}


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                //增加第一进度和第二进度10个刻度
                twoProgressBar.incrementProgressBy(1000);
                twoProgressBar.incrementSecondaryProgressBy(1000);

                break;
            case R.id.reduce:
                //减少第一二进度10个刻度
                twoProgressBar.incrementProgressBy(-1000);
                twoProgressBar.incrementSecondaryProgressBy(-1000);

                break;
            case R.id.reset:
                twoProgressBar.setProgress(5000);
                twoProgressBar.setSecondaryProgress(6000);
                twoProgressBar.setMax(9000);
                break;
            case R.id.buttonDialogProgress:
                /**
                 * 设置progressdialog的基础显示风格
                 */
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//显示风格
                progressDialog.setTitle("慕课网");//标题
                progressDialog.setMessage("欢迎大家来到慕课网");//对话框里的文字信息
                progressDialog.setIcon(R.mipmap.d);//图标
                /**
                 * 显示一些进度条的属性
                 */
                progressDialog.setMax(100);
                //设定初始化已经增长到的进度
                progressDialog.incrementProgressBy(50);
                //进度条是明确显示进度的
                progressDialog.setIndeterminate(false);

                //设定一个确定按钮
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"My Dream",Toast.LENGTH_SHORT).show();
                    }
                });
                //是否可以通过返回按钮退出对话框
                progressDialog.setCancelable(true);
                //显示progressDialog（）
                progressDialog.show();
            break;
        }
        textView1.setText("第一进度条百分比："+(int)(twoProgressBar.getProgress()/(float)twoProgressBar.getMax()*100)+"%,第二进度条百分比："+(int)(twoProgressBar.getSecondaryProgress()/(float)twoProgressBar.getMax()*100)+"%");
    }
}
