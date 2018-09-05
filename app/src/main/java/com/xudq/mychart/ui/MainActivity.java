package com.xudq.mychart.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xudq.mychart.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        ((LinearLayout) findViewById(R.id.test1)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.test2)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.test3)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.test4)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.test5)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.test6)).setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.test7)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test1:
                startActivity(new Intent(context, Test1Activity.class));
                break;
            case R.id.test2:
                startActivity(new Intent(context, Test2Activity.class));
                break;
            case R.id.test3:
                startActivity(new Intent(context, Test3Activity.class));
                break;
            case R.id.test4:
                startActivity(new Intent(context, Test4Activity.class));
                break;
            case R.id.test5:
                startActivity(new Intent(context, Test5Activity.class));
                break;
            case R.id.test6:
                startActivity(new Intent(context, Test6Activity.class));
                break;
            case R.id.test7:
                startActivity(new Intent(context, Test7Activity.class));
                break;

        }
    }
}
