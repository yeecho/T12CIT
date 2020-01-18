package com.yuanye.t12_cit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanye.t12_cit.system.MyApplication;

public class DisplayActivity extends AppCompatActivity{

    private int count = 0;
    private TextView textView;
    private View custom_toolbar;

    private int[] colors = new int[]{R.color.white, R.color.red, R.color.orange, R.color.yellow,
            R.color.green, R.color.cyan, R.color.blue, R.color.purple, R.color.black};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LCD 测试");
        setContentView(R.layout.activity_display);
        textView = findViewById(R.id.textview);
        custom_toolbar = findViewById(R.id.custom_toolbar);
    }

    public void topClick(View v){
        switch (v.getId()){
            case R.id.btnPass:
                MyApplication.setResults(this, true);
                finish();
                break;
            case R.id.btnFail:
                MyApplication.setResults(this, false);
                finish();
                break;
            case R.id.btnTest:
                enterTestModel();
                break;
        }

    }

    private void enterTestModel(){
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
        custom_toolbar.setVisibility(View.GONE);
        textView.setText("");
        textView.setBackground(getDrawable(colors[0]));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < 8){
                    count++;
                }else{
                    quitTestModel();
                }
                textView.setBackground(getDrawable(colors[count]));
            }
        });
    }

    private void quitTestModel() {
        count = 0;
        custom_toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN); // 显示状态栏
        textView.setText(getResources().getText(R.string.test_tips));
        textView.setBackground(getDrawable(colors[0]));
        textView.setOnClickListener(null);
    }
}
