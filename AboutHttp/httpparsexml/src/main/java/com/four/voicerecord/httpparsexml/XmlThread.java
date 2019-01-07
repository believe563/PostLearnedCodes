package com.four.voicerecord.httpparsexml;

import android.os.Handler;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengjianghui on 2015/11/30.
 */
public class XmlThread extends Thread {
    private String url;
    private Handler handler;
    private TextView textView;
    private List<Girl> girls;

    public XmlThread(String url, Handler handler, TextView textView) {
        this.url = url;
        this.handler = handler;
        this.textView = textView;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            InputStream in = conn.getInputStream();
            //android原生的pull解析方式
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            //拿到parser之后就可以对服务器返回的数据进行解析了
            parser.setInput(in, "UTF-8");

            //pull解析是基于事件的，所以要先定义一个eventType类型
            int eventType = parser.getEventType();
            //定义一个集合
            girls = new ArrayList<Girl>();
            Girl girl = null;
            //通过while循环对eventType进行解析，如果不为 XmlPullParser.END_DOCUMENT则认为是有数据的
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //拿到所在的那一行标签的名字
                String data = parser.getName();

                //根据标签的名字做不同的处理
                switch (eventType) {
                    case XmlPullParser.START_TAG: {//即类似于<girl>的标签
                        if ("girl".equals(data)) {
                            girl = new Girl();
                        }
                        if ("name".equals(data)) {
                            girl.setName(parser.nextText());//parser.nextText()可以返回两个<>之间的文字
                        }
                        if ("age".equals(data)) {
                            girl.setAge(Integer.parseInt(parser.nextText()));
                        }
                        if ("school".equals(data)) {
                            girl.setSchool(parser.nextText());
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {//即类似于</girl>的标签
                        if ("girl".equals(data)&&girl!=null) {
                            girls.add(girl);
                        }
                        break;
                    }

                }
                //调用下一行!!!
                eventType = parser.next();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(girls.toString());
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }
}
