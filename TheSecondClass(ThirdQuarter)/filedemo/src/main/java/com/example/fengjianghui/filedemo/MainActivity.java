package com.example.fengjianghui.filedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //往内存卡中存数据
//        //要给出路径（内存卡中的位置都是mnt文件夹下的sdcard）所以要写成
//        //这个是android的内置目录
//        //要先加挂载文件的权限----mount_unmount_filesystem不加肯定出现问题,传数据的时候要加读写文件数据的权限
////        File file=new File(Environment.getExternalStorageDirectory(),"test");
//        File file = new File("/mnt/sdcard/test");//这个方法都是存到storage的sdcard目录下
//        //如果是外置目录的话sdcard要写成extsdcard
//        if (!file.exists()){//如果文件不存在就要重新创建
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else {
//            Toast.makeText(MainActivity.this, "文件已经存在",Toast.LENGTH_SHORT).show();
//        }
//        //如果删除文件是：
////        file.delete();

//        //得到当前应用程序的文件目录
//        File file=this.getFilesDir();//当前应用程序默认的存储目录
//        //所以可以将这个路径写入new file()以创建文件，在2.8以后还可以将这个路径迁移到外置卡上面
//        Log.i("info", file.toString());//  /data/data/com.example.fengjianghui.filedemo/files

//        File file = this.getCacheDir();//当前应用程序默认的缓存文件的存放位置
//        //可以把一些不是非常重要的文件在此处创建，使用
//        //但是如果手机的内存不足的时候，系统会自动去删除APP的cache目录的数据
//        Log.i("info", file.toString());// /data/data/com.example.fengjianghui.filedemo/cache

//        //指定权限去创建文件夹
//        /**
//         * 参数1 name
//         * 参数2 权限（mode）
//         */
//        File file = getDir("fengjianghui", MODE_PRIVATE);
//        Log.i("info", file.toString());// /data/data/com.example.fengjianghui.filedemo/app_fengjianghui

        //API8以上的手机可以使用外存
//        this.getExternalFilesDir(type);
        File file=this.getExternalCacheDir();
        Log.i("info", file.toString());// /storage/sdcard/Android/data/com.example.fengjianghui.filedemo/cache
        //以上两个方法可以得到外部的存储位置，该位置的数据跟内置的使用是一样的
        //好处是如果APP卸载了 这里面的数据也会自动清除掉
        //如果开发者不遵守这样的规则，不把数据放入data/data/<包名>
        //或/mnt/sdcard/Android/data/<包名>
        //卸载之后数据将不会自动清除掉，将会造成所谓的数据垃圾
    }
}
