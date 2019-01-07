package com.example.fengjianghui.asyncloadingdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fengjianghui on 2015/10/7.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private List<NewsBean> mlist;
    //需要一个layout对象作为一个item
    private LayoutInflater layoutInflater;
    private ImageLoader mImageLoader;
    private int mStart;
    private int mEnd;
    //保存当前获取到的URL的地址加，存放从start到end之间的项
    public static String[] URLS;

    //将newsBean类型的listView传进来
    ListView mListView;
    private boolean isFirstIn;

    public NewsAdapter(Context context, List<NewsBean> mlist,ListView listView) {
        this.mlist = mlist;
        this.mListView=listView;
        layoutInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(listView);
        //对URL的地址进行赋值
        URLS = new String[mlist.size()];

        isFirstIn=true;

        for (int i=0;i<mlist.size();i++) {
            //将newsBean中的url转换成了静态的string数组里
            URLS[i] = mlist.get(i).newsIconURL;
        }
        //要给listView绑定一个滚动监听才可以
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);

        //新的图片加载前会显示一下旧的图片
        //如何解决这个问题呢
        //给imageView增加一个tag，作为它的一个身份标志,将URL作为它的身份验证码
        String url = mlist.get(position).newsIconURL;
        viewHolder.ivIcon.setTag(url);//然后在handler中判断

        //使用异步线程加载图片
//        new ImageLoader().showImageByThread(viewHolder.ivIcon,url);

        //使用AsyncTask加载图片
        //加入lruCache后不能这样写，否则会多次定义缓存，要定义在构造方法中，且写为：
//        new ImageLoader().showImageByAsyncTask(viewHolder.ivIcon,url);
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon, url);


        viewHolder.tvTitle.setText(mlist.get(position).newsTitle);
        viewHolder.tvContent.setText(mlist.get(position).newsContent);
        return convertView;
    }

    /**
     * 滑动的状态切换时
     * @param view
     * @param scrollState
     * 在初始化时是不会被调用的，所以第一次就不加载图片，所以加一个成员变量mFirst判断是不是第一次启动
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //滚动中要取消所有的加载
        //滚动完毕之后根据当前listView中所显示的第一项和最后一项，加载他们之间的所有项目

        //当前状态是停止状态
        if (scrollState==SCROLL_STATE_IDLE){
            //加载可见项
            mImageLoader.loadImages(mStart,mEnd);
        }else{
            //停止任务
            mImageLoader.cancelAllTasks();
        }
    }

    /**
     * 整个滑动过程中都会调用
     * @param view
     * @param firstVisibleItem  第一个可见项
     * @param visibleItemCount 当前可见元素的数量
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //给mStart和mEnd赋值
        mStart=firstVisibleItem;
        mEnd=firstVisibleItem+visibleItemCount;
        //第一次显示时候调用
        if (isFirstIn&&visibleItemCount>0){//确保item被加载出来
            mImageLoader.loadImages(mStart,mEnd);
            isFirstIn=false;
        }
    }

    class ViewHolder {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvContent;
    }
}
