package com.yuanye.t12_cit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuanye.t12_cit.system.MyApplication;

public class TFActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TF卡测试");
        setContentView(R.layout.activity_tf);
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
                break;
        }

    }
}
