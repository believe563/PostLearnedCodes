package com.example.fengjianghui.asyncloadingdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView listView;
    //网址
    private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取json格式的数据，再把json格式的数据转化为我们所需要的数据，加载到listView里
        listView = (ListView) findViewById(R.id.listView);
        //通过前面学过的asyncTask数据获取json数据
        new NewsAsyncTask().execute(URL);
    }

    /**
     * 将url所对应的json格式数据转化为我们所封装的NewsBean
     * @param url
     * @return
     */
    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        //获取inputStream 通过new URL(URL).openStream()，这里需要tryCatch
        //也可以通过url.openConnection().getInputStream()获取inputStream，
        //可根据URL直接联网获取网络数据，简单粗暴，返回值类型为inputStream
        try {
            //获取到了json格式的字符串
            String jsonString = readStream(new URL(url).openStream());
//            Log.d("fjh",jsonString);
            JSONObject jsonObject;
            NewsBean newsBean;
            //将json格式的字符串转换为jsonObject
            jsonObject=new JSONObject(jsonString);
            //从jsonObject中取出对应的jsonArray
            JSONArray jsonArray = jsonObject.getJSONArray("data");
//            通过遍历jsonArray来取出jsonObject，进而取出里面所对应的值
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsBean = new NewsBean();
                newsBean.newsIconURL = jsonObject.getString("picSmall");
                newsBean.newsTitle = jsonObject.getString("name");
                newsBean.newsContent = jsonObject.getString("description");
                newsBeanList.add(newsBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //通过URL去读取字符串信息,通过inputstream返回的字符串信息
        return newsBeanList;
    }

    //从inputStream中一行一行地读取网络信息
    //inputStream是字节流
    //inputStream字节流通过inputStreamReader并指定字符集格式并转化为字符流
    //再通过bufferedReader将字符流以buffer的形式读取出来，最终拼接到字符串里面
    //这样就完成了整个数据的读取
    private String readStream(InputStream inputStream){
        InputStreamReader isr;
        String result="";
        try {
            String line="";
            isr = new InputStreamReader(inputStream, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line=br.readLine())!=null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 内部类，实现asyncTask，实现网络的异步访问
     */
    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{

        /**
         *
         * @param params URL
         * @return
         * 通过获取到的json数据所请求的网址URL，通过这样一个api来返回一个json格式数据
         * 将json格式的数据封装到list中便于adapter调用
         * 并构造一个newsbean的list
         */
        @Override
        protected List<NewsBean> doInBackground(String... params) {

            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {

            super.onPostExecute(newsBeans);
            NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newsBeans,listView);
            listView.setAdapter(newsAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
