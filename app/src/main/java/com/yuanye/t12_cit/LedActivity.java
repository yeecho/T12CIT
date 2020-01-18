package com.yuanye.t12_cit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ebensz.internal.FunctionManager;
import com.yuanye.t12_cit.system.MyApplication;


public class LedActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    Switch red,red2,green,green2,blue,blue2;
    FunctionManager fm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LED指示灯");
        setContentView(R.layout.activity_led);
        findViewById(R.id.btnTest).setVisibility(View.GONE);
        red = findViewById(R.id.red);
        red2 = findViewById(R.id.red2);
        green = findViewById(R.id.green);
        green2 = findViewById(R.id.green2);
        blue = findViewById(R.id.blue);
        blue2 = findViewById(R.id.blue2);
        fm = (FunctionManager) getSystemService(FunctionManager.SERVICE_NAME);
        initListener();
    }

    private void initListener() {
        red.setOnCheckedChangeListener(this);
        red2.setOnCheckedChangeListener(this);
        green.setOnCheckedChangeListener(this);
        green2.setOnCheckedChangeListener(this);
        blue.setOnCheckedChangeListener(this);
        blue2.setOnCheckedChangeListener(this);
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (fm == null) return;
        switch (compoundButton.getId()){
            case R.id.red:
                fm.setLed(FunctionManager.LED_RED, b ? FunctionManager.LED_OPEN_BLINK : FunctionManager.LED_CLOSE);
                break;
            case R.id.red2:
                fm.setLed(FunctionManager.LED_RED_BLINK, b ? "255" : FunctionManager.LED_CLOSE);
                break;
            case R.id.green:
                fm.setLed(FunctionManager.LED_GREEN, b ? FunctionManager.LED_OPEN_BLINK : FunctionManager.LED_CLOSE);
                break;
            case R.id.green2:
                fm.setLed(FunctionManager.LED_GREEN_BLINK, b ? FunctionManager.LED_OPEN : FunctionManager.LED_CLOSE);
                break;
            case R.id.blue:
                fm.setLed(FunctionManager.LED_BLUE, b ? FunctionManager.LED_OPEN_BLINK : FunctionManager.LED_CLOSE);
                break;
            case R.id.blue2:
                fm.setLed(FunctionManager.LED_BLUE_BLINK, b ? FunctionManager.LED_OPEN : FunctionManager.LED_CLOSE);
                break;
        }
    }
}
