package com.four.voicerecord.multithreaddownload;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by fengjianghui on 2015-12-08.
 *
 * 模仿迅雷多线程下载
 */
public class DownLoad1 {

    private Handler handler;

    public DownLoad1(Handler handler) {
        this.handler=handler;
    }
    //创建一个线程池对象,固定长度的线程，3个线程
    Executor threadPool = Executors.newFixedThreadPool(3);

    /**
     * 下载文件
     *
     * @param url
     */
    public void downLoadFile(String url) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
//            conn.setReadTimeout(5000);
            //获得下载图片的总体的长度
            int count = conn.getContentLength();
            //将长度分成3份，每份是长度的1/3，这样就可以在线程中将图片分成3份下载
            int block = count / 3;
            //拿到文件名
            String fileName = getFileName(url);
            //SD卡中的下载地址
            File parent = Environment.getExternalStorageDirectory();
            //文件下载的完整路径
            File fileDownload = new File(parent, fileName);
            //往线程池提交任务
            /**
             * 假如长度为11，分为3个线程
             * 11/3
             * 第一个线程：0-2
             * 第二个线程：3-5
             * 第三个线程：6-10
             */
            for (int i = 0; i < 3; i++) {
                long start = i * block;
                long end = (i + 1) * block-1;
                if (i == 2) {
                    end = count;//不会丢失它多出来的那个字节的数据
                }

                DownloadRunnable r = new DownloadRunnable(url, fileDownload.getAbsolutePath(), start, end,handler);
                //线程池去提交任务
                threadPool.execute(r);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //给线程池创建一个类
    //进行网络读写的时候要在runnable中读写
    class DownloadRunnable implements Runnable{

        private String url;
        private String fileName;//指的是文件的绝对路径
        private long start;
        private long end;
        private Handler handler;

        public DownloadRunnable(String url, String fileName, long start, long end,Handler handler) {
            this.url=url;
            this.fileName=fileName;
            this.start=start;
            this.end = end;
            this.handler=handler;
        }
        @Override
        public void run() {
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
//                conn.setReadTimeout(5000);

                //通过runnable去发送网络请求
                //设置请求的长度，即从服务器下载的内容在start-end之间的长度之内
                //关于Range详见文档
                //设置一个请求头
                conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
                //创建一个randomAccess往本地文件进行读写,指定要打开的文件名和打开方式
                RandomAccessFile access = new RandomAccessFile(new File(fileName), "rwd");
                //指定位置开始读写
                access.seek(start);

                //开始读写数据,只会读start-end之间的数据
                InputStream in = conn.getInputStream();
                byte[] b = new byte[1024 * 4];
                int len;
                while ((len = in.read(b)) != -1) {
                    access.write(b,0,len);//从0开始，读取len个长度
                }
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
                //读取完之后，将流关掉
                if (access != null) {
                    access.close();
                }
                if (in != null) {
                    in.close();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据url取出图片的名字
     */
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/")+1);
    }

}
