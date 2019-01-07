package com.example.fengjianghui.dateandtimepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //获取日历对象
        cal=Calendar.getInstance();
        //获取年月日时分秒信息
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH)+1;
        day=cal.get(Calendar.DAY_OF_MONTH);
        hour=cal.get(Calendar.HOUR_OF_DAY);
        minute=cal.get(Calendar.MINUTE);
        second=cal.get(Calendar.SECOND);
        setTitle(year+"-"+month+"-"+day+"-"+hour+"-"+minute);//把它布置到标题栏上
        datePicker= (DatePicker) findViewById(R.id.datePicker);
        timePicker= (TimePicker) findViewById(R.id.timePicker);

        //datePicker初始化:init后面带的参数是初始化的参数，onDatecahnged带的参数是改变后的参数
        //monthOfYear初始值也是0，所以参数应该写成那个样子
        datePicker.init(year,cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setTitle(hourOfDay + "-" + minute);
            }
        });
//        //对话框形式的datePicker监听器：
//        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
//            }
//        },year,cal.get(Calendar.MONTH),day).show();//不要忘了show()

        //对话框形式的timePicker监听器：
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                setTitle(hourOfDay+"-"+minute);
            }
        }, hour, minute,true).show();//最后一个是是否是24小时计时
    }


}
