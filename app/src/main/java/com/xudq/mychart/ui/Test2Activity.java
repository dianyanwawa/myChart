package com.xudq.mychart.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xudq.mychart.R;
import com.xudq.mychart.bean.PieDataEntity;
import com.xudq.mychart.utils.Utils;
import com.xudq.mychart.view.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2Activity extends AppCompatActivity {
    PieChart pieChart;
    private int[] mColors2 = {0xFF58A4FF, 0xFFFFDE78, 0xFFCD6FF9, 0xFFFF9E78, 0xFFABE643, 0xFFCD6F78};
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        list = new ArrayList<>();
        pieChart.startAnimation(2000);

        Map<String, Object> mp = new HashMap<>();
        mp.put("total", 17);
        mp.put("name", "澳大利亚元");
        list.add(mp);
        mp = new HashMap<>();
        mp.put("total", 17);
        mp.put("name", "港元");
        list.add(mp);
        mp = new HashMap<>();
        mp.put("total", 256);
        mp.put("name", "人民币");
        list.add(mp);
        mp = new HashMap<>();
        mp.put("total", 94);
        mp.put("name", "美元");
        list.add(mp);

        Log.i("teejo", "size " + list.size());
        List<PieDataEntity> dataEntities1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PieDataEntity entity = new PieDataEntity(Utils.toString(list.get(i).get("name")), Utils.toInt(list.get(i).get("total")), mColors2[i]);
            dataEntities1.add(entity);
        }
//        for (int i = 0; i < 1; i++) {
//            PieDataEntity entity = new PieDataEntity("无数据", 1, mColors2[i]);
//            dataEntities1.add(entity);
//        }
        pieChart.setDataList(dataEntities1);
    }
}
