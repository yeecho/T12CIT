package com.yuanye.t12_cit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuanye.t12_cit.system.MyApplication;

public class BackLightActivity extends AppCompatActivity{

    Button btnTest;
    TextView textView;
    Handler handler;
    int brightness;
    boolean firstBoot = true;
    Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("背光");
        setContentView(R.layout.activity_backlight);
        btnTest = findViewById(R.id.btnTest);
        textView = findViewById(R.id.textview);
        handler = new MyHandler();
        thread = new Thread(runnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        new Thread(runnable).start();
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setScreenBrightness(200);
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
                new Thread(runnable).start();
                break;
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            btnTest.setClickable(false);
            if (firstBoot){
                firstBoot = false;
                sleep(1200);
            }
            brightness = 10;
            handler.sendEmptyMessage(0);
            sleep(1200);
            brightness = 125;
            handler.sendEmptyMessage(0);
            sleep(1200);
            brightness = 255;
            handler.sendEmptyMessage(0);
            sleep(1200);
            brightness = 125;
            handler.sendEmptyMessage(0);
            sleep(1200);
            brightness = 10;
            handler.sendEmptyMessage(0);
            btnTest.setClickable(true);
        }
    };

    private int getScreenBrightness(){
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return screenBrightness;
    }

    private void setScreenBrightness(int param){
        try{
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sleep(int ms){
        try{
            Thread.sleep(ms);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(""+brightness);
            setScreenBrightness(brightness);
        }
    }

}
