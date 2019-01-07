package com.four.voicerecord.jsondemo;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fengjianghui on 2015/11/26.
 */
public class JsonAdapter extends BaseAdapter {
    private List<Person> personList;
    private Context context;
    LayoutInflater inflater;
    private Handler handler = new Handler();
    public JsonAdapter(List<Person> personList,Context context){
        this.personList=personList;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView == null) {
            //先初始化convertView
            convertView = inflater.inflate(R.layout.item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Person person = personList.get(position);
        holder.name.setText(person.getName());
        //如果给TextView传递一个整数，系统就会认为是传进去一个id，所以要写成字符串的形式
        holder.age.setText("" + person.getAge());
        List<SchoolInfo> schools=person.getSchoolInfo();
        SchoolInfo schoolInfo1 = schools.get(0);
        SchoolInfo schoolInfo2 = schools.get(1);
        holder.schoolInfo1.setText(schoolInfo1.getSchoolName());
        holder.schoolInfo2.setText(schoolInfo2.getSchoolName());
        new HttpImageView(holder.imageView,person.getUrl(),handler).start();
        return convertView;
    }
    class Holder{
        private ImageView imageView;
        private TextView name;
        private TextView age;
        private TextView schoolInfo1;
        private TextView schoolInfo2;

        public Holder(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
            name = (TextView) view.findViewById(R.id.name_tv);
            age = (TextView) view.findViewById(R.id.age_tv);
            schoolInfo1 = (TextView) view.findViewById(R.id.schoolInfo1_tv);
            schoolInfo2 = (TextView) view.findViewById(R.id.schoolInfo2_tv);
        }
    }
}
