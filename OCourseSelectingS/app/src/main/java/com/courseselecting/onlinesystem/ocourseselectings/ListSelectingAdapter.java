package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.Courses;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ListSelectingAdapter extends BaseAdapter {
    private ArrayList<String> listCourseName;
    private ArrayList<String> listCourseDetails;
    private ArrayList<Courses> listCourses;
    private List<String> selectedCourseIds;//该学生已经选上的课程

    private LayoutInflater inflater;
    private Context context;

    public ArrayList<Courses> getListCoursesSelected() {
        return listCoursesSelected;
    }

    public void clearSelectedCourses(){
        listCoursesSelected.clear();
    }

    private ArrayList<Courses> listCoursesSelected = new ArrayList<>();//被选中的课程

    private Courses courses;

    public ListSelectingAdapter(Context context, ArrayList<String> listCourseName, ArrayList<String> listCourseDetails,ArrayList<Courses> listCourses,List<String> selectedCourseIds) {
        this.context=context;
        this.listCourseName = listCourseName;
        this.listCourseDetails = listCourseDetails;
        this.listCourses=listCourses;
        this.selectedCourseIds=selectedCourseIds;
        System.out.println("selectedids="+selectedCourseIds);
        System.out.println("listCoursesSelected="+listCoursesSelected);
        listCoursesSelected.clear();
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listCourseName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        Courses courses = listCourses.get(position);
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_selectingcourse_layout,null);
            viewHolder.tvCourseName = (TextView) convertView.findViewById(R.id.tv_className);
            viewHolder.tvCourseDetail = (TextView) convertView.findViewById(R.id.tv_classDetail_onLine);
            viewHolder.btSelected = (Button) convertView.findViewById(R.id.bt_selected);
            viewHolder.btUnselected = (Button) convertView.findViewById(R.id.bt_unselected);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (selectedCourseIds.size()!=0) {
            System.out.println("selectedCourseIds   "+selectedCourseIds);
            System.out.println("courses"+courses);
            if (selectedCourseIds.contains(courses.getId())) {
                viewHolder.btSelected.setText("已选");
                viewHolder.btSelected.setEnabled(false);
                viewHolder.btUnselected.setText("退选");
                viewHolder.btUnselected.setEnabled(true);
                System.out.println("btSelected.getText()" + viewHolder.btSelected.getText());
                System.out.println("btUnSelected.getText()" + viewHolder.btUnselected.getText());
                if (!(listCoursesSelected.contains(listCourses.get(position)))) {
                    listCoursesSelected.add(listCourses.get(position));
                }
            } else {
                viewHolder.btSelected.setText("选课");
                viewHolder.btSelected.setEnabled(true);
                viewHolder.btUnselected.setText("未选");
                viewHolder.btUnselected.setEnabled(false);
                System.out.println("btSelected.getText()" + viewHolder.btSelected.getText());
                System.out.println("btUnSelected.getText()" + viewHolder.btUnselected.getText());
            }
        }
        viewHolder.tvCourseName.setText(listCourseName.get(position));
        viewHolder.tvCourseDetail.setText(listCourseDetails.get(position));

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("btselected");
                v.setEnabled(false);
                ((Button) v).setText("已选");
                finalViewHolder.btUnselected.setEnabled(true);
                finalViewHolder.btUnselected.setText("退选");
                listCoursesSelected.add(listCourses.get(position));
            }
        });
        viewHolder.btUnselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("btUnselected");
                v.setEnabled(false);
                ((Button) v).setText("退选");
                finalViewHolder.btSelected.setEnabled(true);
                finalViewHolder.btSelected.setText("选课");
                listCoursesSelected.remove(listCourses.get(position));
            }
        });
//        viewHolder.btSelected.setBackgroundResource();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog(position);
            }
        });
        return convertView;
    }

    public void getDialog(int position) {
        Dialog dialog=new Dialog(context);
        dialog.setTitle(listCourseName.get(position));
        dialog.setContentView(R.layout.dialog_coursedetail);
        // TextView title = (TextView) dialog.findViewById(R.id.tv_title_dialog);
        TextView content = (TextView) dialog.findViewById(R.id.tv_content_dialog);
        //title.setText(listCourseName.get(position));
        courses = listCourses.get(position);
        content.setText("课程号：" + courses.getId() + "\n类型:" + courses.getType() + "\n年级:" + courses.getGrade() + "\n上课时间" + courses.getTime() + "\n授课教师:" + courses.getTname() + "\n上课地点:" + courses.getPlace() + "\n可选人数:" + courses.getNumber() + "\n剩余人数:" + courses.getRemainnum() + "\n详细内容:" + listCourseDetails.get(position));
        dialog.show();
    }


}

class ViewHolder{
    TextView tvCourseName;
    TextView tvCourseDetail;
    Button btSelected;
    Button btUnselected;
}
