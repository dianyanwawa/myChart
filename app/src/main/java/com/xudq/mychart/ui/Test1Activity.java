package com.xudq.mychart.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xudq.mychart.R;
import com.xudq.mychart.bean.PieDataEntity;
import com.xudq.mychart.utils.Utils;
import com.xudq.mychart.view.HollowPieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1Activity extends AppCompatActivity {
    HollowPieChart hollowPieChart;
    private int[] mColors1 = {0xFFFF6259, 0xFFFF867F, 0xFFFFB4B0, 0xFFFF8C00, 0xFFFFDAB9};
    //    private int[] mColors2 = {0xFF58A4FF, 0xFFFFDE78, 0xFFCD6FF9, 0xFFFF9E78, 0xFFABE643, 0xFFCD6F78};
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        hollowPieChart = (HollowPieChart) findViewById(R.id.hollowPieChart);

        list = new ArrayList<>();

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

        List<PieDataEntity> dataEntities1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PieDataEntity entity = new PieDataEntity(Utils.toString(list.get(i).get("name")), Utils.toInt(list.get(i).get("total")), mColors1[i]);
            dataEntities1.add(entity);
        }
        hollowPieChart.setDataList(dataEntities1);
    }

}
