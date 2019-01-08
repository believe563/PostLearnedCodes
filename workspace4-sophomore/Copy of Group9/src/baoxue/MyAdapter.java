package baoxue;

import java.util.ArrayList;



import com.example.dluadroid05_07_15.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import entity.BaoXueYouXi;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<BaoXueYouXi> list;

	

	public MyAdapter(Context context, ArrayList<BaoXueYouXi> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {

		View view=LayoutInflater.from(context).inflate(R.layout.item2, null);
		TextView textView=(TextView) view .findViewById(R.id.tv);
		TextView textView1=(TextView) view.findViewById(R.id.tv1);
		TextView textView2=(TextView) view.findViewById(R.id.tv2);
		ImageView imageview=(ImageView) view.findViewById(R.id.iv2);
		textView.setText(list.get(position).getTitle());
		textView2.setText(list.get(position).getImodify());
		textView1.setText(list.get(position).getDigest());
		imageview.setImageBitmap(list.get(position).getBitmap());
		return view;
	}

}
