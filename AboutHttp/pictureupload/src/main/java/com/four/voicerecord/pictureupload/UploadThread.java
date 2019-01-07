package com.four.voicerecord.pictureupload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 参考http://topmanopensource.iteye.com/blog/1605238这个网页
 * Created by fengjianghui on 2015-12-11.
 */
public class UploadThread extends Thread {
    private String fileName;
    private String url;

    /**
     * @param fileName 需要上传的文件
     * @param url      请求的url
     */
    public UploadThread(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public void run() {
        //分割线
        String boundary = "---------------------------7df3d12020566";
        //实际向服务器请求的时候前缀要再加2个--
        String prefix = "--";
        String end = "\r\n";
        try {
            //访问网络请求
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

            //请求头的设置
            conn.setRequestMethod("POST");
            conn.setReadTimeout(60*1000);
            //允许向服务器读取数据
            conn.setDoInput(true);
            //允许向服务器写数据
            conn.setDoOutput(true);
            //由于要上传附件，所以一定要设置enctype为multipart/form-data
            //设置请求方式,指定用multipart协议向服务器上传数据，还有bundary--分隔线的描述符
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            //向服务器进行写数据,末尾要加回车换行符
            outputStream.writeBytes(prefix + boundary + end);
            //文件描述信息
            //所要上传的文件在本地的位置信息和name值
            /**
             * 这里重点注意：
             * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
        //首先是HTTP中的扩展头部分“Content-Disposition: form-data;”，表示上传的是表单数据。
            //“name=”name1″”参数的名称。
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + "a3.jpg" + "\"" + end);
            outputStream.writeBytes(end);
            //这些注释掉的都不用写
//            sb.append("Content-Type: application/octet-stream; charset="
//                    •                         + CHARSET + LINE_END);
//            outputStream.writeBytes("Content-Type: image/jpeg");
//            outputStream.writeBytes(end);
            //向服务器写实体数据,首先从手机目录中读数据到file中
            FileInputStream inputStream = new FileInputStream(fileName);
            byte[] b = new byte[1024 * 4];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            outputStream.writeBytes(end);
            outputStream.writeBytes(prefix + boundary + prefix + end);

            //刷新数据让他们都放到指定的流中
            outputStream.flush();

            /**
             * 获取响应码 200=成功
             * 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            System.out.println("response code:" + res);
//            Log.e("System.out", "response code:"+res);
            if (res == 200) {
                System.out.println("成功");
            }


            //定义一个输入流对象
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            //readLine返回值为string类型，read()返回值为int类型
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }
            System.out.print("response=" + sb.toString());
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
