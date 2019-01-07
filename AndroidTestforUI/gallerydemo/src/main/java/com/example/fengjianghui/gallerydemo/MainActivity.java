package com.example.fengjianghui.gallerydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener,ViewSwitcher.ViewFactory{

    //准备数据源
    private int[] res={R.mipmap.qq1,R.mipmap.qq2,R.mipmap.qq3,R.mipmap.qq4,R.mipmap.qq5,
            R.mipmap.qq6,R.mipmap.qq7,R.mipmap.qq8,R.mipmap.us};
    private String[] str={"亲爱的群群","生日快乐哦","帅帅的群哥","温暖大气的群姐","温文尔雅的群小姐","学生气质的群群",
            "善解人意的暖群","群男神","Best Friends ！  永远爱你哦~~~"};
    private Gallery gallery;
    private ImageAdapter imageAdapter;
    private TextView textView;
    private ImageSwitcher imageSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery= (Gallery) findViewById(R.id.gallery);
        textView= (TextView) findViewById(R.id.textView);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageAdapter=new ImageAdapter(res,this);
        gallery.setAdapter(imageAdapter);
        gallery.setOnItemSelectedListener(this);
        imageSwitcher.setFactory(this);//imageSwitcher加载工厂  当前对象
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));//AnimationUtils工具类加载动画效果
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        imageView.setImageResource(res[position%res.length]);//当前position的角标是这样计算的

        textView.setText(str[position%res.length]);
        imageSwitcher.setBackgroundResource(res[position%res.length]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public View makeView() {//ViewFactory的方法
        ImageView imageView=new ImageView(this);
        //按比例缩放图片居中显示
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }
}
