package com.feng.demo.listviewforvoice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/16.
 */
public class SortActivity extends Activity {
    private ListView lv_contents;
    private Spinner spinner;
    private List<String> labelList = new ArrayList<>();
    private List<Map<String, String>> frdList = new ArrayList<>();
    private List<Map<String, String>> stuList = new ArrayList<>();
    private List<Map<String, String>> contents = new ArrayList<>();
    private boolean isLabel = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sortlayout);
        lv_contents = (ListView) findViewById(R.id.lv_contents);
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] details = {"隐约雷鸣，阴霾天空，但盼风雨来，能留你在此",
                "每当吹风，我就随着梦想，放声歌唱，一直一直，朝往天空的彼方",
                "三人行，必有我师焉，择其善者而从之，其不善者而改之",
                "不要错过这街景，马上就到日暮，可以看到夕阳了，就等到太阳落山"};

//        yyyy-MM-dd HH:mm:ss
        String[] dates = {"2016-03-02 12:00:00", "2016-03-01 12:00:00", "2016-03-19 12:00:00", "2016-03-17 12:00:00"};
        float[] timeLong = {13, 20, 43, 20};
        String[] labels = {"朋友", "朋友", "同学", "朋友"};
        for (String str : labels) {
            if (!(labelList.contains(str))) {
                labelList.add(str);
            }
        }


        for (int i = 0; i < dates.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("detail", details[i]);
            map.put("date", dates[i]);
            map.put("timelong", String.valueOf(timeLong[i]));
            map.put("label", labels[i]);
            contents.add(map);
        }
        for (Map<String, String> map : contents) {
            switch (map.get("label")) {
                case "朋友":
                    frdList.add(map);
                    System.out.println("!!!!" + frdList);
                    break;
                case "同学":
                    stuList.add(map);
                    break;
            }
        }
        Collections.sort(contents, new Comparator<Map<String, String>>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,姣旇緝鏁版嵁澶у皬鏃�,杩欓噷姣旂殑鏄椂闂�
             */
            public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                Date date1 = DateUtil.stringToDate(lhs.get("date"));
                Date date2 = DateUtil.stringToDate(rhs.get("date"));

                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        for (Map<String, String> map : contents) {
            System.out.println(map.get("date"));
        }
        final SortAdapterForLabels sortAdapterForLabels = new SortAdapterForLabels(this, contents, isLabel);
        lv_contents.setAdapter(sortAdapterForLabels);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String label = parent.getSelectedItem().toString();
                switch (label) {
                    case "按时间":
                        for (int i = 0; i < contents.size(); i++) {
                            Map<String, String> map = contents.get(i);
                            if (map.containsKey("type")) {
                                contents.remove(i);
//                                Iterator<String> iterator=map.keySet().iterator();
                            }
                        }
                        Collections.sort(contents, new Comparator<Map<String, String>>() {
                            /**
                             *
                             * @param lhs
                             * @param rhs
                             * @return an integer < 0 if lhs is less than rhs, 0 if they are
                             *         equal, and > 0 if lhs is greater than rhs,姣旇緝鏁版嵁澶у皬鏃�,杩欓噷姣旂殑鏄椂闂�
                             */
                            public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                                Date date1 = DateUtil.stringToDate(lhs.get("date"));
                                Date date2 = DateUtil.stringToDate(rhs.get("date"));
                                if (date1.before(date2)) {
                                    return 1;
                                }
                                return -1;
                            }
                        });
                        isLabel = false;
                        sortAdapterForLabels.notifyDataSetChanged();

                        break;
                    case "按时长":
                        for (int i = 0; i < contents.size(); i++) {
                            Map<String, String> map = contents.get(i);
                            if (map.containsKey("type")) {
                                contents.remove(map);
//                                Iterator<String> iterator=map.keySet().iterator();
                            }
                        }
                        Collections.sort(contents, new Comparator<Map<String, String>>() {
                            /**
                             *
                             * @param lhs
                             * @param rhs
                             * @return an integer < 0 if lhs is less than rhs, 0 if they are
                             *         equal, and > 0 if lhs is greater than rhs,姣旇緝鏁版嵁澶у皬鏃�,杩欓噷姣旂殑鏄椂闂�
                             */
                            public int compare(Map<String, String> lhs, Map<String, String> rhs) {
                                float timelong1 = Float.parseFloat(lhs.get("timelong"));
                                float timelong2 = Float.parseFloat(rhs.get("timelong"));
                                if (timelong1 < timelong2) {
                                    return 1;
                                }
                                return -1;
                            }
                        });
                        isLabel = false;
                        sortAdapterForLabels.notifyDataSetChanged();
                        break;
                    case "按标签":
                        contents.clear();
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("type", "label");
                        map1.put("label", "朋友");
                        contents.add(map1);
                        for (Map<String, String> map : frdList) {
                            contents.add(map);
                        }
                        Map<String, String> map2 = new HashMap<String, String>();
                        map2.put("type", "label");
                        map2.put("label", "同学");
                        contents.add(map2);
                        for (Map<String, String> map : stuList) {
                            contents.add(map);
                        }
                        isLabel = true;
                        sortAdapterForLabels.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
