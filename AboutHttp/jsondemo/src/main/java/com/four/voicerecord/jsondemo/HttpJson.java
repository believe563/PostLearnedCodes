package com.four.voicerecord.jsondemo;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengjianghui on 2015/11/26.
 */
public class HttpJson extends Thread {
    private String url;
    private Handler handler;
    private JsonAdapter adapter;
    private Context context;
    private ListView listView;

    public HttpJson(String url, Handler handler, Context context, ListView listView) {
        this.url = url;
        this.handler = handler;
        this.context = context;
        this.listView = listView;
    }

    @Override
    public void run() {
        InputStream inputStream=null;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
//            conn.setReadTimeout(5000);
            inputStream=conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            final List<Person> personList = parseJson(buffer.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter = new JsonAdapter(personList, context);
                    listView.setAdapter(adapter);
                }
            });


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.run();
    }

    public List<Person> parseJson(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            List<Person> persons = new ArrayList<Person>();
            int result = object.getInt("result");
            JSONArray personArray = object.getJSONArray("personData");
            if (result == 1) {
                for (int i = 0; i < personArray.length(); i++) {
                    Person person = new Person();
                    JSONObject personObject = personArray.getJSONObject(i);
                    String name = personObject.getString("name");
                    person.setName(name);
                    int age = personObject.getInt("age");
                    person.setAge(age);
                    String url = personObject.getString("url");
                    person.setUrl(url);
                    JSONArray schools = personObject.getJSONArray("schoolInfo");
                    List<SchoolInfo> schoolInfos = new ArrayList<SchoolInfo>();
                    for (int j = 0; j < schools.length(); j++) {
                        SchoolInfo schoolInfo = new SchoolInfo();
                        JSONObject schoolInfo1 = schools.getJSONObject(j);
                        String schoolName = schoolInfo1.getString("schoolName");
                        schoolInfo.setSchoolName(schoolName);
                        schoolInfos.add(schoolInfo);
                    }
                    person.setSchoolInfo(schoolInfos);
                    persons.add(person);
                }
                return persons;
            }else {
                Toast.makeText(context, "error",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
